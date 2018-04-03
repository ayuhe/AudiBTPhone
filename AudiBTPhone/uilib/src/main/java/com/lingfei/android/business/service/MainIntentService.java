package com.lingfei.android.business.service;

import android.app.IntentService;
import android.content.Intent;

import com.lingfei.android.business.cmd.receive.SystemSwitchUtil;
import com.lingfei.android.business.cmd.receive.btsource.BTSourceUtil;
import com.lingfei.android.business.cmd.receive.mcuversion.McuVersionUtil;
import com.lingfei.android.business.cmd.receive.setting.BackLightUtil;
import com.lingfei.android.business.cmd.receive.setting.ComfortUtil;
import com.lingfei.android.business.cmd.receive.setting.ExternalInputUtil;
import com.lingfei.android.business.cmd.receive.setting.SettingUtil;
import com.lingfei.android.business.cmd.receive.switchview.SwitchUtil;
import com.lingfei.android.business.cmd.receive.temperature.TemperatureUtil;
import com.lingfei.android.business.cmd.receive.time.TimeUtil;
import com.lingfei.android.business.event.BTSourceStateEvent;
import com.lingfei.android.business.event.TemperatureEvevt;
import com.lingfei.android.business.receiver.MainMCUReceiver;
import com.lingfei.android.uilib.util.EventPostUtils;

/**
	* Created by heyu on 2016/9/21.
	*/
public class MainIntentService extends IntentService{

				public MainIntentService(String name){
								super(name);
				}

				public MainIntentService(){
								super("BroadcastIntentService");
				}

				@Override
				protected void onHandleIntent(Intent intent){
								if(null != intent){
												String[] datas = intent.getStringArrayExtra(MainMCUReceiver.EXTRA_DATAS);

												if(SwitchUtil.isSwitchCmd(datas)){
																SwitchUtil.switchActivity(datas); // 获取切源指令并切换操作
												}
												else if(SystemSwitchUtil.isSystemSwitchCmd(datas)){
																SystemSwitchUtil.parseSystemSwitchCmd(datas); // 系统切源指令
												}
												else if(McuVersionUtil.isMcuVersionCmd(datas)){
																McuVersionUtil.getMcuVersion(datas); // 获取mcu版本名称
												}
												else if(TimeUtil.isTimeCmd(datas)){
																String time = TimeUtil.getTimeString(datas);
																EventPostUtils.Post(time);
												}
												else if(SettingUtil.isSettingCmd(datas)){
																SettingUtil.parseSettingCmd(datas);
												}
												else if(ExternalInputUtil.isExternalInputCmd(datas)){//外部输入
																ExternalInputUtil.parseExternalInput(datas);
												}
												else if(BackLightUtil.isBackLightCmd(datas)){
																BackLightUtil.parseBackLightCmd(datas);
												}
												else if(ComfortUtil.isComfortCmd(datas)){
																ComfortUtil.parseComfortCmd(datas);
												}
												else if(TemperatureUtil.isTemperatureCmd(datas)){
																String temperature = TemperatureUtil.getTemperatureString(datas);
																EventPostUtils.Post(new TemperatureEvevt(temperature));
												}
												else if(BTSourceUtil.isBTSourceCmd(datas)){
																int state = BTSourceUtil.getBTSourceState(datas);
																EventPostUtils.Post(new BTSourceStateEvent(state));
												}
								}
				}
}
