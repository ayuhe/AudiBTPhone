package com.lingfei.android.business.cmd.receive.phone;

import android.content.Intent;

import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.SysSharePres;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
	* Created by Administrator on 2016/10/12.
	*/
public class CarPhoneWarningIntentService extends BaseCarPhoneIntentService{//原车蓝牙警告弹窗

				//================================================================
				//===========================sharePreference======================================
				public static final String IS_WARNING = "CarPhoneIntentService.IS_WARNING";
				//	public static final String ENABLE_RESTART_WARNING_TASK = "CarPhoneIntentService.ENABLE_RESTART_WARNING_TASK";//是否允许重启并接收新电话信息
				//======================================================================
				public static final String ACTIVITY_START_ACTION_INCOMING = "com.lingfei.android.safetytips.CarPhoneInComingActivity";
				public static final String ACTIVITY_START_ACTION_HANGUP = "com.lingfei.android.safetytips.CarPhoneHangUpActivity";
				public static final String CAR_PHONE_STATUS = "com.lingfei.android.safetytips.CAR_PHONE_STATUS";
				public static final String CAR_PHONE_INFO = "com.lingfei.android.safetytips.CAR_PHONE_INFO";
				public static final String SERVICE_START_ACTION_INCOMING = "com.lingfei.android.safetytips.phone.service.CarPhoneInComingService";
				public static final String SERVICE_START_ACTION_HANGUP = "com.lingfei.android.safetytips.phone.service.CarPhoneHangUpService";

				//================================================================
				protected void startWarning(int status, String info){
								Intent intent = new Intent();
								switch(status){
												case CarPhoneUtil.BTStatus.IN_COMING:{
																intent.setAction(ACTIVITY_START_ACTION_INCOMING);
																intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																intent.putExtra(CAR_PHONE_STATUS, status);
																intent.putExtra(CAR_PHONE_INFO, info);
																LibApplication.getContext().startActivity(intent);
																break;
												}
												case CarPhoneUtil.BTStatus.HANGUP:{
																intent.setAction(SERVICE_START_ACTION_HANGUP);
																LibApplication.getContext().startService(intent);
																break;
												}
								}
				}

				protected String createInfo(ArrayList<Integer> list){
								String info = "";

								if(list != null && list.isEmpty() == false){
												String uni = "";
												byte[] utf = new byte[list.size()];
												ShowLogUtil.show("list.size()" + list.size());
												for(int i = 0; i < list.size(); i++){
																int k = list.get(i);
																utf[i] = (byte) k;
												}

												try{
																uni = new String(utf, "unicode");
												}
												catch(UnsupportedEncodingException e){
																e.printStackTrace();
												}
												info = uni;
								}
								return info;
				}

				//==========================override================================

				@Override
				protected void btStatusOnIncoming(){
								super.btStatusOnIncoming();
								if(isNeedPopUp()){
												boolean isStarted = SysSharePres.getInstance().getBoolean(IS_WARNING, false);
												if(isStarted == false){ // 还没有启动警告页面
																String info = "";
																ArrayList<Integer> infoList = new ArrayList<>();
																ShowLogUtil.show("receiveList size" + getReceiveList().size());
																infoList.addAll(getReceiveList());
																for(int i = CarPhoneUtil.DataPOS.POS_NAME_OR_NUMBER; i > 0; i--){
																				infoList.remove(0);
																}
																info = createInfo(infoList);
																ShowLogUtil.show("启动来电弹窗" + !isStarted);
																startWarning(getBtStatus(), info);
												}
								}
				}

				@Override
				protected void btStatusOnTalking(){
								super.btStatusOnTalking();
								sendCarPhoneWarningEvent(CarPhoneWarningEvent.WarningMode.MODE_WARNING_PHONE_TALKING, "");
				}

				@Override
				protected void btStatusOnHangup(){
								super.btStatusOnHangup();
								sendCarPhoneWarningEvent(CarPhoneWarningEvent.WarningMode.MODE_WARNING_PHONE_HANGUP, "");
								if(isNeedPopUp()){

												startWarning(CarPhoneUtil.BTStatus.HANGUP, "");
								}
				}

				@Override
				protected void btStatusOnCallout(){
								super.btStatusOnCallout();
				}
				//================================================================
				//================================================================
}
