package com.lingfei.android.business.service;

import android.app.IntentService;
import android.content.Intent;

import com.lingfei.android.business.bean.VoiceBean;
import com.lingfei.android.business.bean.door.DoorInfo;
import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.business.cmd.receive.dashboard.DashboardDB;
import com.lingfei.android.business.cmd.receive.dashboard.DashboardInfo;
import com.lingfei.android.business.cmd.receive.door.DoorUtil;
import com.lingfei.android.business.cmd.receive.switchview.SwitchUtil;
import com.lingfei.android.business.receiver.MainMCUReceiver;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* 解析警告信息
	* Created by heyu on 2016/9/21.
	*/
public class WarningIntentService extends IntentService{

				public WarningIntentService(String name){
								super(name);
				}

				public WarningIntentService(){
								super("WarningIntentService");
				}

				@Override
				protected void onHandleIntent(Intent intent){
								if(null != intent){
												String[] datas = intent.getStringArrayExtra(MainMCUReceiver.EXTRA_DATAS);
												parseDoorInfo(datas);
								}
				}

				/**
					* 解析车门信息
					*
					* @param datas
					*/
				private void parseDoorInfo(String[] datas){
								if(DoorUtil.isDoorInfo(datas)){
												DoorInfo doorInfo = DoorUtil.parseDoorInfo(datas);
												if(DoorUtil.isOpenDoors(doorInfo)){
																// 若左前门或者右前门开了
																if(doorInfo.isOpenLeftBefore() || doorInfo.isOpenRightBefore()){
																				setWarningSafetyBelt(false); // 开门不显示安全带
																}
																else{
																				// 关门后要复位安全带状态
																				boolean isWarningSafetyBelt = SysSharePres.getInstance().getBoolean("is_warning_safety_belt");
																				setWarningSafetyBelt(isWarningSafetyBelt);
																}
																// 通知语音播报
																VoiceBean bean = new VoiceBean();
																bean.setContent("请注意安全，请把车门关上，谢谢合作");
																SwitchUtil.doStartLingFeiCarService(Constant.Voice.DOOR_BROADCAST, bean);
												}
												else{
																// 关门后要复位安全带状态
																boolean isWarningSafetyBelt = SysSharePres.getInstance().getBoolean("is_warning_safety_belt");
																setWarningSafetyBelt(isWarningSafetyBelt);
												}
								}
				}

				private void setWarningSafetyBelt(boolean isWarningSafetyBelt){
								DashboardInfo dashboardInfo = DashboardDB.getInstance().getDashboardInfo();
								if(null != dashboardInfo){
												// 更新安全带状态
												dashboardInfo.setWarningSafetyBelt(isWarningSafetyBelt);
								}
				}
}
