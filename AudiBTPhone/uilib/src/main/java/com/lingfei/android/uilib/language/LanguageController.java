package com.lingfei.android.uilib.language;

import com.lingfei.android.uilib.LibApplication;

import java.util.Locale;


/**
	* 多国语言的控制器
	*/
public class LanguageController{

				public final static int STATUS_CN = 0; // 简体中文
				public final static int STATUS_ES = 1; // English
				public final static int STATUS_TW = 2; // 繁体

				private static LanguageController instance;// 单列模式

				public LanguageController(){
				}

				/**
					* 单列模式
					*
					* @return UpdateController mUpdateController
					*/
				public static LanguageController getInstance(){
								if(null == instance){
												instance = new LanguageController();
								}
								return instance;
				}

				/**
					* 更换语言
					*
					* @param local
					*/
				public void updateLanguage(final Locale local){
								ThreadPoolManager.getInstance().executeTask(new Runnable(){
												@Override
												public void run(){
																// 更改系统语言，扔到线程池里更改
																LanguageUtils.updateLanguage(local);
												}
								});
				}

				/**
					* 获取当前系统语言
					*
					* @return
					*/
				public Locale getCurrentLocale(){
								Locale locale = LibApplication.getContext().getResources().getConfiguration().locale;
								return locale;
				}

				/**
					* 获取当前语言映射自己定义的Id
					* @return
					*/
				public int getCurrentLanguageId(){
								Locale locale = getCurrentLocale();
								if(Locale.SIMPLIFIED_CHINESE.equals(locale)){
												return STATUS_CN;
								}
								else if(Locale.ENGLISH.equals(locale)){
												return STATUS_ES;
								}
								else if(Locale.TRADITIONAL_CHINESE.equals(locale)){
												return STATUS_TW;
								}

								return STATUS_CN;
				}

}
