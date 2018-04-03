package com.lingfei.android.uilib.util;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.lingfei.android.business.cmd.receive.switchview.SwitchPackageName;
import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.bean.PackageItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
	* @author yhe
	* @ClassName: PackageUtil
	* @Description: 应用信息的工具类：获取、缓存APK包信息
	* @date 2014年12月10日 下午9:20:18
	*/
public class PackageUtil{

				public final static String PACKAGE_SHARED = "package_shared";
				public final static String PACKAGE_NAMES = "package_names";

				public static SharedPreferences packageShare = LibApplication.getInstance()
												.getContext().getSharedPreferences(PACKAGE_SHARED,
												                                   Context.MODE_APPEND);

				public static String getPackageNames(){
								return packageShare.getString(PACKAGE_NAMES, "");
				}

				public static void putPackageNames(String packageName){
								if(getPackageNames().contains(packageName)
																|| TextUtils.isEmpty(packageName)){
												return;
								}
								Editor packageEditor = packageShare.edit();
								packageEditor.putString(PACKAGE_NAMES, getPackageNames() + packageName
																+ ";");
								packageEditor.commit();
				}

				public static List<String> getPackageNameList(){
								List<String> packageList = new ArrayList<String>();

								String packageNameStr = PackageUtil.getPackageNames(); // 得到选择过的所有APP
								if(!TextUtils.isEmpty(packageNameStr)){
												String packageNames[] = packageNameStr.split(";");
												if(null != packageNames && packageNames.length > 0){
																packageList = Arrays.asList(packageNames);
												}
								}
								return packageList;
				}

				public static List<PackageItem> getSelectedPackageItemList(){
								List<PackageItem> itemList = new ArrayList<PackageItem>();

								List<String> packageNames = PackageUtil.getPackageNameList();
								for(String pkg : packageNames){
												PackageItem item = PackageUtil.getPackageInfoByPackageName(pkg);
												if(null != item){
																itemList.add(item);
												}
								}

								return itemList;
				}

				public static void deletePackageName(String packageName){
								String packageNames = PackageUtil.getPackageNames().replace(
																packageName + ";", "");
								Editor packageEditor = packageShare.edit();
								packageEditor.putString(PACKAGE_NAMES, packageNames);
								packageEditor.commit();
				}

				/**
					* @param @param  packageName
					* @param @return 设定文件
					* @return PackageItem    返回类型
					* @throws
					* @Title: getPackageInfoByPackageName
					* @Description: 根据apk包名得到相关信息
					*/
				public static PackageItem getPackageInfoByPackageName(String packageName){
								PackageItem item = new PackageItem();

								PackageManager pm = LibApplication.getContext().getPackageManager();
								String name = "";
								Drawable icon = null;
								try{
												name = pm.getApplicationLabel(
																				pm.getApplicationInfo(packageName,
																				                      PackageManager.GET_META_DATA)).toString();
												icon = pm.getApplicationIcon(pm.getApplicationInfo(packageName,
												                                                   PackageManager.GET_META_DATA));
								}
								catch(NameNotFoundException e){
												e.printStackTrace();
								}

								item.setIcon(icon);
								item.setName(name);
								item.setPackageName(packageName);

								return item;
				}

				// 获得所有启动Activity的信息，类似于Launch界面
				public static List<PackageItem> queryAppInfo(){
								PackageManager pm = LibApplication.getContext().getPackageManager(); // 获得PackageManager对象
								Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
								mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
								// 通过查询，获得所有ResolveInfo对象.
								List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
								// 调用系统排序 ， 根据name排序
								// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
								Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
								List<PackageItem> mlistAppInfo = new ArrayList<>();

								if(mlistAppInfo != null){
												for(ResolveInfo reInfo : resolveInfos){
																String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
																String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
																String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
																Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
																// 为应用程序的启动Activity 准备Intent
																Intent launchIntent = new Intent();
																launchIntent.setComponent(new ComponentName(pkgName, activityName));

																PackageItem item = new PackageItem();
																item.setName(appLabel);
																item.setPackageName(pkgName);
																item.setClassName(activityName);
																item.setIcon(icon);
																item.setIntent(launchIntent);

																// 过滤掉部分应用
																//                蓝牙电话 activityName---com.goodocom.rk.MainActivity pkgName---com.goodocom.rk
																//                视频播放器 activityName---android.rk.RockVideoPlayer.VideoPlayActivity pkgName---android.rk.RockVideoPlayer
																//                音乐 activityName---com.android.music.MusicBrowserActivity pkgName---com.android.music
																if(!("com.goodocom.rk.MainActivity".equals(activityName) ||
																								"android.rk.RockVideoPlayer.VideoPlayActivity".equals(activityName) ||
																								SwitchPackageName.SETTING.equals(pkgName) ||
																								SwitchPackageName.BROWSER.equals(pkgName) ||
/*																								SwitchPackageName.PHONE_LINK.equals(pkgName) ||
																								SwitchPackageName.NAVI.equals(pkgName) ||
																								SwitchPackageName.GOOGLE_MAP.equals(pkgName) ||
																								SwitchPackageName.SOGOU.equals(pkgName) ||
																								SwitchPackageName.QQ_MUSIC.equals(pkgName) ||
																								SwitchPackageName.KU_WO_SING.equals(pkgName) ||
																								SwitchPackageName.QI_YI_VIDEO.equals(pkgName) ||
																								SwitchPackageName.UC_BROWSER.equals(pkgName) ||*/
																								"com.android.music.MusicBrowserActivity".equals(activityName))){
																				mlistAppInfo.add(item); // 添加至列表中
																				System.out.println(appLabel + " activityName---" + activityName + " pkgName---" + pkgName);
																}
												}
								}

								return mlistAppInfo;
				}

				/**
					* @param @return 设定文件
					* @return List<PackageItem>    返回类型
					* @throws
					* @Title: doSearchApplicationInfo
					* @Description: 查询所有的应用信息
					*/
				public static List<PackageItem> getAllAppPackageInfo(){

								PackageManager appInfo = LibApplication.getContext()
																.getPackageManager();
								List<ApplicationInfo> listInfo = appInfo.getInstalledApplications(0);
								Collections.sort(listInfo, new ApplicationInfo.DisplayNameComparator(
																appInfo));

								List<PackageItem> data = new ArrayList<PackageItem>();

								for(int index = 0; index < listInfo.size(); index++){
												try{
																ApplicationInfo content = listInfo.get(index);
																if((content.flags != ApplicationInfo.FLAG_SYSTEM)
																								&& content.enabled){
																				if(content.icon != 0){
																								PackageItem item = new PackageItem();
																								item.setName(LibApplication.getContext().getPackageManager().getApplicationLabel(content).toString());
																								item.setPackageName(content.packageName);
																								item.setIcon(LibApplication
																																             .getContext()
																																             .getPackageManager()
																																             .getDrawable(content.packageName, content.icon,
																																                          content));

																								Intent intent = LibApplication.getContext().getPackageManager().getLaunchIntentForPackage(item.getPackageName());
																								if(null != intent){
																												data.add(item);
																								}
																				}
																}
												}
												catch(Exception e){

												}
								}

								return data;
				}

				/*
					* check the app is installed
					*/
				public static boolean isAppInstalled(String packagename){
								PackageInfo packageInfo;
								try{
												packageInfo = LibApplication.getContext().getPackageManager()
																				.getPackageInfo(packagename, 0);
								}
								catch(NameNotFoundException e){
												packageInfo = null;
												e.printStackTrace();
								}

								if(packageInfo == null){
												return false;
								}
								else{
												return true;
								}
				}

				/**
					* 跳转到第三方App
					*
					* @param context
					* @param packageName
					* @param errorTip
					*/
				public static void gotoOtherApp(Context context, String packageName, String errorTip){
								if(null == context || StringUtil.isEmpty(packageName)){
												return;
								}

								Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
								if(null != intent){
												try{
																context.startActivity(intent);
												}
												catch(ActivityNotFoundException ex){
																ex.printStackTrace();
												}
												catch(Exception ex){
																ex.printStackTrace();
												}
								}
								else{
												ToastUtils.showToast(errorTip);
								}
				}
}
