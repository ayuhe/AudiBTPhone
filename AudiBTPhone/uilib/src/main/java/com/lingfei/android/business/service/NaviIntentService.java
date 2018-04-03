package com.lingfei.android.business.service;

import android.app.IntentService;
import android.content.Intent;

import com.lingfei.android.business.bean.guide.GuideInfo;
import com.lingfei.android.business.cmd.send.NaviGuideInfoCmd;
import com.lingfei.android.business.cmd.send.NaviStateCmd;
import com.lingfei.android.business.event.NaviGuideEvent;
import com.lingfei.android.business.receiver.NaviReceiver;
import com.lingfei.android.uilib.util.EventPostUtils;

/**
	* 将地图导向信息转发给MCU的服务
	* Created by heyu on 2016/9/22.
	*/
public class NaviIntentService extends IntentService{

				public NaviIntentService(String name){
								super(name);
				}

				public NaviIntentService(){
								super("NaviIntentService");
				}

				@Override
				protected void onHandleIntent(Intent intent){
								if(null != intent){
												GuideInfo guideInfo = (GuideInfo) intent.getSerializableExtra(NaviReceiver.GUIDE_INFO);
												int extra_state = intent.getIntExtra(NaviReceiver.EXTRA_STATE, -1);
												if(null != guideInfo){ // 导向信息
																// 将数据转发给MCU
																NaviGuideInfoCmd.sendGuideInfoCmd(guideInfo);

																// 更新导向信息
																EventPostUtils.Post(new NaviGuideEvent(guideInfo));
												}
												else if(extra_state > -1 &&
																				NaviStateCmd.START_TTS_VOICE != extra_state &&
																				NaviStateCmd.STOP_TTS_VOICE != extra_state){ // 导航状态
																NaviStateCmd.handleNaviVoiceState(extra_state);
												}
								}
				}
}
