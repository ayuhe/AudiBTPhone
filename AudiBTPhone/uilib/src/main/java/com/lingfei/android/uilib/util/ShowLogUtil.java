package com.lingfei.android.uilib.util;

import android.util.Log;

import com.lingfei.android.uilib.Config;

/**
	* Created by Administrator on 2016/7/19.
	*/
public class ShowLogUtil{
				public static void show(String log){
								boolean flag = Config.IS_DEBUG;
								if(flag){
												Log.w("LFBT","@@@@@ --> " + log);
								}
				}
}
