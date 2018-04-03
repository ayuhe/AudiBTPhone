package com.lingfei.android.uilib.util;

import com.lingfei.android.uilib.Config;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.orhanobut.logger.Settings;

/**
	* 打印log的工具类
	*/
public class LoggerUtil{
				public static boolean isDebug = Config.IS_DEBUG; // 调试时，打印报文日志的开关。发布版本时要把开关设为false。

				/**
					* It is used to get the settings object in order to change settings
					*
					* @return the settings object
					*/
				public static Settings init(){
								return Logger.init();
				}

				/**
					* It is used to change the tag
					*
					* @param tag is the given string which will be used in Logger as TAG
					*/
				public static Settings init(String tag){
								return Logger.init(tag);
				}

				public static Printer t(String tag){
								return Logger.t(tag);
				}

				public static Printer t(int methodCount){
								return Logger.t(null, methodCount);
				}

				public static Printer t(String tag, int methodCount){
								return Logger.t(tag, methodCount);
				}

				public static void d(String message, Object... args){
								if(isDebug)
												Logger.d(message, args);
				}

				public static void e(String message, Object... args){
								if(isDebug)
												Logger.e(null, message, args);
				}

				public static void e(Throwable throwable, String message, Object... args){
								if(isDebug)
												Logger.e(throwable, message, args);
				}

				public static void i(String message, Object... args){
								if(isDebug)
												Logger.i(message, args);
				}

				public static void v(String message, Object... args){
								if(isDebug)
												Logger.v(message, args);
				}

				public static void w(String message, Object... args){
								if(isDebug)
												Logger.w(message, args);
				}

				public static void wtf(String message, Object... args){
								if(isDebug)
												Logger.wtf(message, args);
				}

				/**
					* Formats the json content and print it
					*
					* @param json the json content
					*/
				public static void json(String json){
								if(isDebug)
												Logger.json(json);
				}

				/**
					* Formats the json content and print it
					*
					* @param xml the xml content
					*/
				public static void xml(String xml){
								if(isDebug)
												Logger.xml(xml);
				}
}
