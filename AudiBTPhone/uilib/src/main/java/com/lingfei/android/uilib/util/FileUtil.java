
package com.lingfei.android.uilib.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.lingfei.android.uilib.LibApplication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

/**
	* 文件操作工具类
	*/
public final class FileUtil{
				private static final String TAG = FileUtil.class.getSimpleName();

				// 本地保存MCU数据的文件路径
				public static final String MCU_DATA_FILE = FileUtil.getRootFilePath() + "/LingFei/mcudata.txt";

				private FileUtil(){
				}

				/**
					* 获取包名的最后一个字段
					*
					* @return 包名的最后一个字段
					*/
				public static String getSimplePackage(){
								String packageName = LibApplication.getContext().getPackageName();
								int idx = packageName.lastIndexOf(".");
								return idx == -1 ? packageName : packageName.substring(idx + 1);
				}

				/**
					* 取得文件的大小
					*
					* @param file 本地文件
					* @return 文件的大小, 如果找不到文件或读取失败则返回-1
					*/
				public static long getFileSize(File file){
								return (file.exists() && file.isFile() ? file.length() : -1L);
				}

				/**
					* @param bytes
					* @return
					* @Description long转文件大小M单位方法
					* @author temdy
					*/
				public static float bytes2mb(long bytes){
								BigDecimal filesize = new BigDecimal(bytes);
								BigDecimal megabyte = new BigDecimal(1024 * 1024);
								float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
								return returnValue;
				}

				/**
					* 根据uri获取文件路径
					*
					* @param uri     使用URI表示的文件路径
					* @param context 上下文
					* @return 实际的文件路径
					*/
				public static String getFilePath(Uri uri, Context context){
								if(null == uri){
												return "";
								}
								String filePath = null;
								String scheme = uri.getScheme();

								if(StringUtil.isEmpty(scheme)){
												return filePath;
								}

								if("file".equals(scheme)){ // 直接从Uri获取
												filePath = uri.getPath();
								}
								else if("content".equals(scheme)){ // 从contentprovider获取
												String[] proj = {
																				MediaStore.Images.Media.DATA
												};
												android.database.Cursor cursor = context.getContentResolver().query(uri, proj, null,
												                                                                    null, null);
												if(cursor != null){
																int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
																if(cursor.getCount() > 0 && cursor.moveToFirst() && columnIndex != -1){
																				filePath = cursor.getString(columnIndex);
																}
																cursor.close();
												}
								}

								return filePath;
				}

				/**
					* 拷贝文件到某文件夹
					*
					* @param source      源文件
					* @param destination 目标文件夹
					* @return 拷贝成功则返回true, 否则返回false
					*/
				public static boolean copyFile(File source, File destination){
								if(!source.exists() || !source.isFile())
												return false;
								if(!destination.exists())
												destination.mkdirs();
								if(!destination.exists() || !destination.isDirectory())
												return false;

								FileInputStream fis = null;
								FileOutputStream fos = null;
								try{
												fis = new FileInputStream(source);
												fos = new FileOutputStream(new File(destination, source.getName()));
												byte[] buf = new byte[1024];
												int len;
												while((len = fis.read(buf)) != -1){
																fos.write(buf, 0, len);
												}
												return true;
								}
								catch(FileNotFoundException e){
												LogUtil.e(TAG, e.toString());
								}
								catch(Exception e){
												LogUtil.e(TAG, e.toString());
								}
								finally{
												closeQuietly(fis);
												closeQuietly(fos);
								}
								return false;
				}

				/**
					* 拷贝Assets下的文件到程序私有空间
					*
					* @param context 上下文
					* @param source  原文件
					* @return 保存到程序私有空间后的文件路径
					* @throws IOException 原文件在assets目录中不存在的时候
					*/
				public static String copyAssetFileToInternal(Context context, String source) throws IOException{
								InputStream in = null;
								OutputStream out = null;
								try{
												in = context.getAssets().open(source);
												out = context.openFileOutput(source, Context.MODE_WORLD_READABLE);
												byte[] buf = new byte[1024];
												int len;
												while((len = in.read(buf)) != -1){
																out.write(buf, 0, len);
												}
												return (context.getFileStreamPath(source)).getAbsolutePath();
								}
								finally{
												closeQuietly(in);
												closeQuietly(out);
								}
				}


				/**
					* 检查sdcard是否存在
					*
					* @return 如果sdcard存在返回true, 否则返回false
					*/
				public static boolean isSDCardExist(){
								return SystemManage.externalMemoryAvailable();
				}

				/**
					* 调用系统的工具，浏览某个文件
					*
					* @param path 文件路径
					*/
				public static Intent openFile(String path){
								Intent intent = new Intent("android.intent.action.VIEW");
								intent.addCategory("android.intent.category.DEFAULT");
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								File file = new File(path);
								Uri uri = Uri.fromFile(file);
								intent.setDataAndType(uri, getMIMEType(file));
								return intent;
				}

				/**
					* 判断文件MimeType audio:.amr,.m4a,.mp3,.mid,.xmf,.ogg,.wav,.3gpp,.3ga,.wma
					* video:.3gp,.mp4,.m4v image:.jpg,.gif,.png,.jpeg,.bmp word:.doc apk:.apk
					*
					* @param file 文件名
					* @return 文件MimeType
					*/
				public static String getMIMEType(File file){
								String fName = file.getName();
				    /* 取得扩展名 */
								String[] names = fName.split("\\.");
								String end = names[names.length - 1];

        /* 依扩展名的类型决定MimeType */
								// 根据需要扩展解析文件类型
								String type = "";
								if(end.equals("amr") || end.equals("m4a") || end.equals("mp3") || end.equals("mid")
																|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")
																|| end.equals("3gpp") || end.equals("3ga") || end.equals("wma")){
												type = "audio/*";
								}
								else if(end.equals("3gp") || end.equals("mp4") || end.equals("m4v")){
												type = "video/*";
								}
								else if(end.equals("jpg") || end.equals("gif") || end.equals("png")
																|| end.equals("jpeg") || end.equals("bmp")){
												type = "image/*";
								}
								else if(end.endsWith("apk")){
												type = "application/vnd.android.package-archive";
								}
								else if(end.endsWith("doc")){
												type = "application/msword";
								}
								else{
												type = "*/*";
								}
								return type;
				}

				/**
					* 判断文件类型 audio:.amr,.m4a,.mp3,.mid,.xmf,.ogg,.wav,.3gpp,.3ga,.wma
					* video:.3gp,.mp4,.m4v image:.jpg,.gif,.png,.jpeg,.bmp
					* word:.xls,doc,docx,xlsx
					*
					* @param filename 文件名
					* @return 文件类型
					*/
				public static String getMIMEType(String filename){

								if(StringUtil.isEmpty(filename)){
												return "";
								}

        /* 取得扩展名 */
								String[] texts = filename.split("\\.");
								String end = texts[texts.length - 1].toLowerCase();

        /* 依扩展名的类型决定MimeType */
								// 根据需要扩展解析文件类型
								String type = "";
								if(end.equals("amr") || end.equals("m4a") || end.equals("mp3") || end.equals("mid")
																|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")
																|| end.equals("3gpp") || end.equals("3ga") || end.equals("wma")){
												type = "audio";
								}
								else if(end.equals("3gp") || end.equals("mp4") || end.equals("m4v")){
												type = "video";
								}
								else if(end.equals("jpg") || end.equals("gif") || end.equals("png")
																|| end.equals("jpeg") || end.equals("bmp")){
												type = "image";
								}
								else if(end.equals("xls") || end.equals("doc") || end.equals("docx")
																|| end.equals("xlsx")){
												type = "word";
								}
								else{
												type = "other";
								}
								return type;
				}

				/***
					* 计算文件夹大小
					*
					* @param mFile 目录或文件
					* @return 文件或目录的大小
					*/
				public static long calculateFolderSize(File mFile){
								// 判断文件是否存在
								if(!mFile.exists()){
												return 0;
								}

								// 如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
								if(mFile.isDirectory()){
												File[] files = mFile.listFiles();
												long size = 0;
												if(null != files){
																for(File f : files){
																				size += calculateFolderSize(f);
																}
												}
												return size;
								}
								else{
												return mFile.length();
								}

				}

				/***
					* 清空指定文件夹/文件
					*
					* @return 清空成功的话返回true, 否则返回false
					*/

				public static boolean deleteFile(File file){
								if(!file.exists()){
												return true;
								}
								if(file.isDirectory()){
												File[] childs = file.listFiles();
												if(childs == null || childs.length <= 0){
																// 空文件夹删掉
																return file.delete();
												}
												else{
																// 非空，遍历删除子文件
																for(int i = 0; i < childs.length; i++){
																				deleteFile(childs[i]);
																}
																return deleteFile(file);
												}
								}
								else{
												return file.delete();
								}

				}

				/***
					* 清空指定文件夹下所有文件
					*
					* @return 清空成功的话返回true, 否则返回false
					*/
				public static boolean cleanDirectory(File directory){
								return deleteFile(directory);
				}

				/***
					* 删除指定文件或文件夹
					*
					* @return 删除成功的话返回true, 否则返回false
					*/
				public static boolean forceDelete(File file){
								return cleanDirectory(file);
				}

				/**
					* 删除文件
					*
					* @param path
					*/
				public static void delFile(String path){
								if(StringUtil.isEmpty(path)){
												return;
								}
								File file = new File(path);
								deleteFile(file);
				}

				/**
					* 获取asset目录中文件的流
					*
					* @param fileName asserts目录中的文件名
					* @return 文件流, 如果读取失败则返回null
					*/
				public static InputStream getAssetsInputStream(Context context, String fileName){
								InputStream is = null;
								try{
												is = context.getAssets().open(fileName);
								}
								catch(IOException e){
												LogUtil.e(TAG, e.toString());
								}
								return is;
				}

				/**
					* 获取asset目录中文件中的字符串
					*
					* @param fileName asserts目录中的文件名
					* @return 文件内容, 如果读取失败则返回空字符串
					*/
				public static String getAssets(String fileName){

								return stream2String(getAssetsInputStream(LibApplication.getContext(), fileName));

				}

				/**
					* 判断路径(文件或目录)是否存在
					*
					* @param path 文件或目录路径
					* @return 如果存在返回true, 否则返回false
					*/
				public static boolean isFileExist(String path){
								return new File(path).exists();
				}

				/**
					* 关闭输入输出流
					*
					* @param c 输入输出流
					*/
				public static void closeQuietly(Closeable c){
								if(c != null){
												try{
																c.close();
												}
												catch(IOException e){
																LogUtil.i(TAG, e.toString());
												}
								}
				}

				/**
					* 流转为string
					*
					* @param is
					* @return String [返回类型说明]
					* @throws throws [违例类型] [违例说明]
					* @see [类、类#方法、类#成员]
					*/
				public static String stream2String(InputStream is){
								if(is == null){
												return "";
								}

								BufferedReader reader = new BufferedReader(new InputStreamReader(is));
								StringBuffer strBuffer = new StringBuffer("");

								String line = null;
								try{
												while((line = reader.readLine()) != null){
																if(line.trim().equals("")){
																				continue;
																}
																strBuffer.append(line).append("\r\n");  // 添加回车\换行
												}
								}
								catch(IOException e){
												LogUtil.e(TAG, e.toString());
								}
								finally{
												closeQuietly(reader);
								}
								return strBuffer.toString();
				}

				/**
					* 获取文件中的字符串
					*
					* @param path
					* @return
					*/
				public static String getStringOfFile(String path){
								LogUtil.d(TAG, "file path = " + path);
								try{
												InputStream inputStream = new FileInputStream(path);
												String Result = stream2String(inputStream);
												return Result;
								}
								catch(FileNotFoundException e){
												e.printStackTrace();
								}
								catch(Exception e){
												e.printStackTrace();
								}
								return "";
				}


				/**
					* 把输入流拷贝到输出流
					*
					* @param input  输入流
					* @param output 输出流
					* @return 拷贝的字节数，如果失败返回-1
					*/
				public static int copyStream(InputStream input, OutputStream output){
								byte[] buffer = new byte[1024];
								int count = 0;
								int n = 0;
								try{
												while(-1 != (n = input.read(buffer))){
																output.write(buffer, 0, n);
																count += n;
												}
								}
								catch(Exception ex){
												return -1;
								}
								return count;
				}

				/**
					* <查询指定目录下所有指定一种后缀名的文件放到一个文件队列中> <功能详细描述>
					*
					* @param dirName  指定的目录
					* @param endName  指定的后缀名
					* @param fileList [文件List]
					* @return void [返回类型说明]
					* @throws throws [违例类型] [违例说明]
					* @see [类、类#方法、类#成员]
					*/
				public static void getFiles(File dirName, String endName, List<File> fileList){

								File[] files = dirName.listFiles();
								if(files != null){
												for(File f : files){
																if(f.isDirectory()){
																				getFiles(f, endName, fileList);
																}
																else if(f.isFile()){
																				try{
																								if(f.getName().endsWith(endName.toLowerCase())
																																|| f.getName().endsWith(endName.toUpperCase())){
																												fileList.add(f);
																								}
																				}
																				catch(Exception e){
																								e.toString();
																				}
																}
												}
								}
				}

				/**
					* <查询指定目录下所有指定多种后缀名的文件放到一个文件队列中> <功能详细描述>
					*
					* @param dirName
					* @param endNames [后缀名数组]
					* @param fileList [参数说明]
					* @return void [返回类型说明]
					* @throws throws [违例类型] [违例说明]
					* @see [类、类#方法、类#成员]
					*/
				public static void getFiles(File dirName, String[] endNames, List<File> fileList){
								for(String endName : endNames){
												getFiles(dirName, endName, fileList);
								}
				}

				public static String getDirectoryName(String path){
								if(!StringUtil.isEmpty(path)){
												File file = new File(path);
												if(file.exists()){
																String parentPath = file.getParent();
																return parentPath.substring(parentPath.lastIndexOf(File.separator) + 1);
												}
								}
								return "";

				}

				/**
					* <判断SD卡上的图片是否存在> <功能详细描述>
					*
					* @param filePath
					* @return boolean [返回类型说明]
					* @throws throws [违例类型] [违例说明]
					* @see [类、类#方法、类#成员]
					*/
				public static boolean isImageExist(String filePath){
								if(!StringUtil.isEmpty(filePath)){
												File file = new File(filePath);
												return file.exists() && file.length() > 0;
								}
								return false;
				}

				/**
					* 读取图片属性：旋转的角度
					*
					* @param path 图片绝对路径
					* @return degree旋转的角度
					*/

				public static int readPictureDegree(String path){
								int degree = 0;
								try{
												ExifInterface exifInterface = new ExifInterface(path);

												int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
												                                                ExifInterface.ORIENTATION_NORMAL);

												switch(orientation){

																case ExifInterface.ORIENTATION_ROTATE_90:

																				degree = 90;

																				break;

																case ExifInterface.ORIENTATION_ROTATE_180:

																				degree = 180;

																				break;

																case ExifInterface.ORIENTATION_ROTATE_270:

																				degree = 270;

																				break;
												}

								}
								catch(IOException e){
												LogUtil.e(TAG, e.toString());

								}
								return degree;
				}

				/**
					* 字节数组保存到文件
					*
					* @param data
					* @param path [参数说明]
					* @return void [返回类型说明]
					* @throws throws [违例类型] [违例说明]
					* @see [类、类#方法、类#成员]
					*/
				public static void saveFile(byte[] data, String path){
								File file = new File(new File(path).getParent());
								if(!file.exists()){
												file.mkdirs();
								}
								BufferedOutputStream stream = null;
								try{
												file = new File(path);
												FileOutputStream fstream = new FileOutputStream(file);
												stream = new BufferedOutputStream(fstream);
												stream.write(data);
								}
								catch(Exception e){
												LogUtil.e(TAG, e.toString());
								}
								finally{
												closeQuietly(stream);
								}
				}

				/**
					* 　　* 保存文件
					* 　　* @param toSaveString
					* 　　* @param filePath
					*/
				public static void saveFile(String toSaveString, String filePath){
								try{
												File saveFile = new File(filePath);
												if(!saveFile.exists()){
																// 文件不存在，则创建文件
																File dir = new File(saveFile.getParent());
																dir.mkdirs();
																saveFile.createNewFile();
												}

												FileOutputStream outStream = new FileOutputStream(saveFile, true);
												outStream.write(toSaveString.getBytes());
												outStream.close();
								}
								catch(FileNotFoundException e){
												e.printStackTrace();
								}
								catch(IOException e){
												e.printStackTrace();
								}
				}

				/**
					* 　读取文件内容
					* 　　@param filePath
					* 　　@return 文件内容
					*/
				public static String readFile(String filePath){
								String str = "";
								try{
												File readFile = new File(filePath);
												if(!readFile.exists()){
																return "";
												}
												FileInputStream inStream = new FileInputStream(readFile);
												ByteArrayOutputStream stream = new ByteArrayOutputStream();
												byte[] buffer = new byte[1024];
												int length = -1;
												while((length = inStream.read(buffer)) != -1){
																stream.write(buffer, 0, length);
												}
												str = stream.toString();
												stream.close();
												inStream.close();
												return str;
								}
								catch(FileNotFoundException e){
												e.printStackTrace();
												return "";
								}
								catch(IOException e){
												e.printStackTrace();
												return "";
								}
				}

				/**
					* 获取文件的字节
					*
					* @param file
					* @return [参数说明]
					*/
				public static byte[] getFileContent(File file){
								FileInputStream fis = null;
								ByteArrayOutputStream output = null;
								try{
												fis = new FileInputStream(file);
												output = new ByteArrayOutputStream();
												byte[] buffer = new byte[1024];
												int n = 0;
												while(-1 != (n = fis.read(buffer))){
																output.write(buffer, 0, n);
												}
												return output.toByteArray();
								}
								catch(FileNotFoundException e){
												LogUtil.e(TAG, e.toString());
								}
								catch(IOException e){
												LogUtil.e(TAG, e.toString());
								}
								finally{
												FileUtil.closeQuietly(output);
												FileUtil.closeQuietly(fis);
								}
								return null;
				}

				public static String getRootFilePath(){
								if(hasSDCard()){
												return Environment.getExternalStorageDirectory().getAbsolutePath();
								}
								else{
												return Environment.getDataDirectory().getAbsolutePath();
								}
				}

				/**
					* 得到根目录文件
					*
					* @return
					*/
				public static File getRootFile(){
								if(hasSDCard()){
												return Environment.getExternalStorageDirectory();
								}
								else{
												return Environment.getDataDirectory();
								}
				}

				public static boolean hasSDCard(){
								String status = Environment.getExternalStorageState();
								if(!status.equals(Environment.MEDIA_MOUNTED)){
												return false;
								}
								return true;
				}

				/**
					* 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
					*
					* @param context
					* @param imageUri
					* @author yaoxing
					* @date 2014-10-12
					*/
				@TargetApi(19)
				public static String getImageAbsolutePath(Context context, Uri imageUri){
								if(context == null || imageUri == null)
												return null;
								if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)){
												if(isExternalStorageDocument(imageUri)){
																String docId = DocumentsContract.getDocumentId(imageUri);
																String[] split = docId.split(":");
																String type = split[0];
																if("primary".equalsIgnoreCase(type)){
																				return Environment.getExternalStorageDirectory() + "/" + split[1];
																}
												}
												else if(isDownloadsDocument(imageUri)){
																String id = DocumentsContract.getDocumentId(imageUri);
																Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
																return getDataColumn(context, contentUri, null, null);
												}
												else if(isMediaDocument(imageUri)){
																String docId = DocumentsContract.getDocumentId(imageUri);
																String[] split = docId.split(":");
																String type = split[0];
																Uri contentUri = null;
																if("image".equals(type)){
																				contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
																}
																else if("video".equals(type)){
																				contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
																}
																else if("audio".equals(type)){
																				contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
																}
																String selection = MediaStore.Images.Media._ID + "=?";
																String[] selectionArgs = new String[]{split[1]};
																return getDataColumn(context, contentUri, selection, selectionArgs);
												}
								} // MediaStore (and general)
								else if("content".equalsIgnoreCase(imageUri.getScheme())){
												// Return the remote address
												if(isGooglePhotosUri(imageUri))
																return imageUri.getLastPathSegment();
												return getDataColumn(context, imageUri, null, null);
								}
								// File
								else if("file".equalsIgnoreCase(imageUri.getScheme())){
												return imageUri.getPath();
								}
								return null;
				}

				public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs){
								Cursor cursor = null;
								String column = MediaStore.Images.Media.DATA;
								String[] projection = {column};
								try{
												cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
												if(cursor != null && cursor.moveToFirst()){
																int index = cursor.getColumnIndexOrThrow(column);
																return cursor.getString(index);
												}
								}
								finally{
												if(cursor != null)
																cursor.close();
								}
								return null;
				}

				/**
					* @param uri The Uri to check.
					* @return Whether the Uri authority is ExternalStorageProvider.
					*/
				public static boolean isExternalStorageDocument(Uri uri){
								return "com.android.externalstorage.documents".equals(uri.getAuthority());
				}

				/**
					* @param uri The Uri to check.
					* @return Whether the Uri authority is DownloadsProvider.
					*/
				public static boolean isDownloadsDocument(Uri uri){
								return "com.android.providers.downloads.documents".equals(uri.getAuthority());
				}

				/**
					* @param uri The Uri to check.
					* @return Whether the Uri authority is MediaProvider.
					*/
				public static boolean isMediaDocument(Uri uri){
								return "com.android.providers.media.documents".equals(uri.getAuthority());
				}

				/**
					* @param uri The Uri to check.
					* @return Whether the Uri authority is Google Photos.
					*/
				public static boolean isGooglePhotosUri(Uri uri){
								return "com.google.android.apps.photos.content".equals(uri.getAuthority());
				}


				/**
					* 获取SD卡上的指定某个文件
					*
					* @param name
					* @return
					*/
				public static File getFileByFileName(File root, String name){
								if(TextUtils.isEmpty(name)){
												return null;
								}

								File file = null;
								if(null != root){
												file = searchSpecifiedFile(root, name);
								}
								else{
												file = searchSpecifiedFile(getRootFile(), name);
								}
								return file;
				}

				/**
					* 遍历接收一个文件路径和一个文件名称，然后把文件子目录中对应文件名的文件返回
					*
					* @param root
					* @param name
					*/
				public static File searchSpecifiedFile(File root, String name){
								File files[] = root.listFiles();
								if(files != null){
												for(File f : files){
																if(f.isDirectory()){
																				//                    searchSpecifiedFile(f, name); // 只查询当前目录下的文件，不查文件夹
																				continue;
																}
																else{
																				String fileName = f.getName();
																				String preFileName = fileName;
																				String preName = name;
																				String prefix = "";
																				String prefix2 = "";
																				int index = fileName.lastIndexOf(".");
																				int index2 = name.lastIndexOf(".");

																				if(index > 0){ // 必须要有后缀名
																								preFileName = fileName.substring(0, index);
																								preName = name.substring(0, index2);
																								if(preFileName.equals(preName)){
																												if(fileName.length() > index){
																																// 当前检索到文件的后缀名
																																prefix = fileName.substring(index + 1);
																												}

																												if(name.length() > index2){
																																prefix2 = name.substring(index2 + 1); // 拿到文件的后缀名
																												}

																												if(!TextUtils.isEmpty(prefix) && prefix.equals(prefix2)){
																																return f;  // 找到文件了，不要再遍历了
																												}
																								}
																				}
																}
												}
								}

								return null;
				}
}
