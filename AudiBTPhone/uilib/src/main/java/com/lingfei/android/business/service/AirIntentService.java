package com.lingfei.android.business.service;

import android.app.IntentService;
import android.content.Intent;

import com.lingfei.android.business.bean.air.AirInfo;
import com.lingfei.android.business.cmd.receive.air.AirUtil;
import com.lingfei.android.business.event.AirInfoEvent;
import com.lingfei.android.business.receiver.MainMCUReceiver;
import com.lingfei.android.uilib.util.EventPostUtils;

/**
	* 处理空调数据的服务
	* Created by heyu on 2016/9/21.
	*/
public class AirIntentService extends IntentService{

				public AirIntentService(String name){
								super(name);
				}

				public AirIntentService(){
								super("AirIntentService");
				}

				@Override
				protected void onHandleIntent(Intent intent){
								if(null != intent){
												String[] datas = intent.getStringArrayExtra(MainMCUReceiver.EXTRA_DATAS);
												if(AirUtil.isAirCmd(datas)){
																AirInfo airInfo = AirUtil.getAirInfo(AirUtil.getDataListFromMCU(datas));
																// 转发空调信息给界面刷新
																EventPostUtils.Post(new AirInfoEvent(airInfo));
												}
								}
				}
}
