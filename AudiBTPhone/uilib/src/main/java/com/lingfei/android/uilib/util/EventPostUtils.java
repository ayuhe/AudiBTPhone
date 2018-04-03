package com.lingfei.android.uilib.util;

import de.greenrobot.event.EventBus;

/**
	* Created by Administrator on 2016/7/13.
	*/
public class EventPostUtils{
				public static void Post(Object event){
								EventBus.getDefault().post(event);
				}

				public static void PostSticky(Object event){
								EventBus.getDefault().postSticky(event);
				}
}
