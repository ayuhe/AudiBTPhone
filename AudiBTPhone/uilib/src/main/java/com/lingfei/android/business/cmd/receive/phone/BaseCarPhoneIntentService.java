package com.lingfei.android.business.cmd.receive.phone;

import android.app.IntentService;
import android.content.Intent;

import com.lingfei.android.business.receiver.MainMCUReceiver;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.SysSharePres;

import java.util.ArrayList;

/**
	* Created by Administrator on 2016/9/27.
	*/
public class BaseCarPhoneIntentService extends IntentService{

				CarPhoneWarningEvent carPhoneWarningEvent = new CarPhoneWarningEvent();
				//===========================   ======================================
				private ArrayList<Integer> receiveList;
				private int btStatus = 0;
				private boolean isNeedPopUp = false;//是否需要弹窗
				protected boolean isPlayingMusic;

				public BaseCarPhoneIntentService(String name){
								super(name);
				}

				public BaseCarPhoneIntentService(){
								super("CarPhoneIntentService");
				}

				@Override
				protected void onHandleIntent(Intent intent){
								isPlayingMusic = SysSharePres.getInstance().getIsPlayingMusic();
								if(null != intent){
												if(intent.hasExtra(MainMCUReceiver.EXTRA_DATAS)){
																String[] datas = intent.getStringArrayExtra(MainMCUReceiver.EXTRA_DATAS);
																checkCMDWarning(datas);
												}
								}
				}

				private void checkCMDWarning(String[] datas){
								if(datas != null && CarPhoneUtil.isWarningInfo(datas, CarPhoneUtil.SEND_FROM_MCU)){
												ShowLogUtil.show("datas != null");

												receiveList = CarPhoneUtil.getIntegerDataListFromMCU(datas);
												if(receiveList != null && CarPhoneUtil.checkDataListSize(receiveList)){

																isNeedPopUp = CarPhoneUtil.isNeedPopUp(receiveList.get(CarPhoneUtil.DataPOS.MODE));
																btStatus = receiveList.get(CarPhoneUtil.DataPOS.POS_BT_STATUS);
																switch(btStatus){
																				case CarPhoneUtil.BTStatus.IN_COMING:{
																								btStatusOnIncoming();

																								break;
																				}
																				case CarPhoneUtil.BTStatus.CALL_OUT:{
																								btStatusOnCallout();
																								break;
																				}
																				case CarPhoneUtil.BTStatus.HANGUP:{
																								btStatusOnHangup();

																								break;
																				}
																				case CarPhoneUtil.BTStatus.TALKING:{
																								btStatusOnTalking();
																								break;
																				}
																}
												}
								}
				}

				protected void sendCarPhoneWarningEvent(String msg, String info){
								if(carPhoneWarningEvent != null){
												carPhoneWarningEvent.setWarningInfo(info);
												carPhoneWarningEvent.send_WarningMode(msg);
								}
				}

				//=======================================================================
				protected void btStatusOnIncoming(){

				}

				protected void btStatusOnHangup(){

				}

				protected void btStatusOnTalking(){

				}

				protected void btStatusOnCallout(){

				}
				//==============================get set ===================================

				public ArrayList<Integer> getReceiveList(){
								return receiveList;
				}

				public int getBtStatus(){
								return btStatus;
				}

				public boolean isNeedPopUp(){
								return isNeedPopUp;
				}

				public void setNeedPopUp(boolean needPopUp){
								isNeedPopUp = needPopUp;
				}
}
