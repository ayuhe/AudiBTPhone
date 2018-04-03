package com.lingfei.android.uilib;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lingfei.android.uilib.service.LogService;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.SysSharePres;
import com.lingfei.android.uilib.util.ToastUtils;

import java.io.InputStream;

/**
	*
	*/
public class LibApplication extends Application{

				protected static LibApplication instance = null;

				protected static Context context; // 上下文

				protected int screenHeight = 800;
				protected int screenWidth = 1280;

				@Override
				public void onCreate(){
								super.onCreate();
								instance = this;
								context = getApplicationContext();
								initConfig();  // 初始化配置文件
								SysSharePres.getInstance().initSystemConfig();

								initScreenWidthHeight(this);
								ToastUtils.register(this);  // 初始化ToastUtils
								//        LoggerUtil.init(getPackageName()); // 初始化Log
								LoggerUtil.init("APP_Logger")  // default PRETTYLOGGER or use just init()
																.setMethodCount(0)     // 显示方法数量
																.hideThreadInfo();     // default shown

								if(Config.IS_DEBUG){ // 是否要打印log
												startLogService();
												// 启动时先删除之前本地保存的MCU数据
												//												FileUtil.delFile(FileUtil.MCU_DATA_FILE);
								}
				}

				/**
					* 初始化配置文件，配置文件要放在项目资源文件的raw中
					*/
				private void initConfig(){
								int id = getResources().getIdentifier("config", "raw", getPackageName());

								InputStream iStream = getResources().openRawResource(id);

								Config.init(iStream);
				}

				public static LibApplication getInstance(){
								if(null == instance){
												instance = new LibApplication();
								}
								return instance;
				}

				public static Context getContext(){
								return context;
				}

				/**
					* 得到屏幕的寬高
					*
					* @param context
					*/
				public void initScreenWidthHeight(Context context){
								WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

								DisplayMetrics metric = new DisplayMetrics();
								manager.getDefaultDisplay().getMetrics(metric);
								screenWidth = metric.widthPixels; // 屏幕宽度（像素）
								screenHeight = metric.heightPixels; // 屏幕高度（像素）
								float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
								int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

								LoggerUtil.d("screenWidth = " + screenWidth + " || screenHeight = " + screenHeight + " || density = " + density + " || densityDpi = " + densityDpi);
				}

				public int getScreenHeight(){
								return screenHeight;
				}

				public int getScreenWidth(){
								return screenWidth;
				}

				/**
					* 启动打印log的服务
					*/
				public void startLogService(){
								Intent intent = new Intent(this, LogService.class);// 启动日志服务
								startService(intent);
				}
}
