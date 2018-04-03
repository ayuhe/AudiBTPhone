package com.lingfei.android.uilib.util;

import android.content.Context;
import android.widget.Toast;

import com.lingfei.android.uilib.Config;

/**
	* Created by sll on 2016/1/11.
	*/
public class ToastUtils{

				public static Context mContext;

				private ToastUtils(){
				}

				public static void register(Context context){
								mContext = context.getApplicationContext();
				}

				public static void showToast(int resId){
								Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_SHORT).show();
				}

				public static void showToast(String msg){
								Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
				}

				public static void showToastDebug(String msg){
								if(Config.IS_DEBUG){
												Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
								}
				}
}
