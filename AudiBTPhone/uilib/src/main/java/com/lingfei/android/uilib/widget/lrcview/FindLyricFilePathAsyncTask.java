package com.lingfei.android.uilib.widget.lrcview;

import android.os.AsyncTask;

import com.lingfei.android.uilib.util.FileUtil;
import com.lingfei.android.uilib.util.LoggerUtil;

import java.io.File;

/**
	* 异步搜索歌词文件
	* Created by heyu on 2016/8/24.
	*/
public class FindLyricFilePathAsyncTask extends AsyncTask<Void, Void, File>{
				private File rootFile; // 搜索范围，即搜索的根目录
				private String fileName; // 搜索文件名
				private ILrcCallback mCallback; // 回调接口

				public FindLyricFilePathAsyncTask(ILrcCallback callback, File rootFile, String fileName){
								this.mCallback = callback;
								this.rootFile = rootFile;
								this.fileName = fileName;
				}

				@Override
				protected File doInBackground(Void... params){
								return FileUtil.getFileByFileName(rootFile, fileName); // 在后台搜索file
				}

				@Override
				protected void onPostExecute(File file){
								super.onPostExecute(file);
								String filePath = null;
								if(null != file){
												LoggerUtil.d("file path =  " + file.getPath());
												filePath = file.getPath();
								}
								else if(null != rootFile && null == file){
												// 当前路径没有，再查全局 ——查全局非常耗时的，不可取
												//                new SearchFileTask(null, fileName).execute(); // 根据歌曲名称查找歌词文件
								}

								if(null != mCallback){
												mCallback.updateLrcFilePath(filePath); // 设置歌词文件路径
								}
				}

				public interface ILrcCallback{
								// 更新歌词文件路径
								void updateLrcFilePath(String path);
				}

}
