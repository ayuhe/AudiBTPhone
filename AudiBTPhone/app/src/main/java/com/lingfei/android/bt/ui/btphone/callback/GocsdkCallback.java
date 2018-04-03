package com.lingfei.android.bt.ui.btphone.callback;

import android.content.Intent;
import android.media.AudioManager;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;

import com.goodocom.gocsdk.IGocsdkCallback;
import com.lingfei.android.bt.BaseApplication;
import com.lingfei.android.bt.ui.btmusic.bean.BTMusic;
import com.lingfei.android.bt.ui.btmusic.event.BTMusicEvent;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;
import com.lingfei.android.bt.ui.btphone.event.BTContactEvent;
import com.lingfei.android.bt.ui.btphone.event.BTDeviceEvent;
import com.lingfei.android.bt.ui.btphone.event.BTStatuEvent;
import com.lingfei.android.bt.ui.btphone.util.BTStatu;
import com.lingfei.android.business.cmd.send.BTPhoneCmd;
import com.lingfei.android.uilib.Config;
import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.StringUtil;

public class GocsdkCallback extends IGocsdkCallback.Stub{ //服务返回调用
				public static final int HfpStatus_offline = 1;
				public static final int HfpStatus_online = 3;
				public static final int HfpStatus_callout = 4;
				public static final int HfpStatus_callin = 5;
				public static final int HfpStatus_talking = 6;

				/**
					* 蓝牙连接
					*/
				public static final int BT_ON_LINE = 1000;

				/**
					* 蓝牙断开
					*/
				public static final int BT_OFF_LINE = 1001;

				/**
					* 蓝牙空闲且可用（已连接）
					*/
				public static final int BT_ON_LINE_USE = 1002;

				/**
					* 蓝牙电话来电
					*/
				public static final int BT_CALL_IN = 1003;

				/**
					* 蓝牙电话拒接\挂断
					*/
				public static final int BT_HANG_UP = 1006;

				/**
					* 蓝牙电话接听（通话中）
					*/
				public static final int BT_TALKING = 1005;

				/**
					* 蓝牙电话挂断
					*/
				public static final int BT_CALL_OUT = 1007;


				static final int callLog_Called = 4;
				static final int callLog_Missed = 6;
				static final int callLog_Answered = 5;

				@Override
				public void onHfpDisconnected() throws RemoteException{
								showlog("onHfpDisconnected  1001");
								EventPostUtils.Post(new BTStatuEvent(BTStatu.DISCONNECTED));
								sendBtPhoneBroadcast(BT_OFF_LINE);
				}

				@Override
				public void onHfpConnected() throws RemoteException{
								showlog("onHfpConnected  1000");
								EventPostUtils.Post(new BTStatuEvent(BTStatu.CONNECTED));
								sendBtPhoneBroadcast(BT_ON_LINE);
				}

				// 拨打电话成功
				@Override
				public void onCallSucceed(String number) throws RemoteException{
								showlog("onCallSucceed " + number);
				}

				/**
					* 来电
					*
					* @param number
					* @throws RemoteException
					*/
				@Override
				public void onIncoming(String number) throws RemoteException{
								showlog("onIncoming  number = " + number);
								BTPhonePeople btPhonePeople = new BTPhonePeople();
								btPhonePeople.setType(BTPhonePeople.Type.Callin);
								btPhonePeople.setPhoneNumber(number);
								EventPostUtils.Post(new BTContactEvent(BTStatu.CALL_IN, btPhonePeople));

								// 告知MCU有来电
								BTPhoneCmd.sendBTPhoneCmd(BTPhoneCmd.IN_COMING);
				}

				@Override
				public void onHangUp() throws RemoteException{
								showlog("onHangUp  1006");
								EventPostUtils.Post(new BTStatuEvent(BTStatu.HANG_UP));
								BTPhoneCmd.sendBTPhoneCmd(BTPhoneCmd.HANG_UP);
								sendBtPhoneBroadcast(BT_HANG_UP);
				}

				@Override
				public void onTalking() throws RemoteException{
								showlog("onTalking ");
								EventPostUtils.Post(new BTStatuEvent(BTStatu.ON_TALKING));
				}

				@Override
				public void onRingStart() throws RemoteException{
								showlog("onRingStart ");
								EventPostUtils.Post(new BTStatuEvent(BTStatu.ON_RING_START));
				}

				@Override
				public void onRingStop() throws RemoteException{
								showlog("onRingStop ");
								EventPostUtils.Post(new BTStatuEvent(BTStatu.ON_RING_STOP));
				}

				@Override
				public void onHfpLocal() throws RemoteException{
								showlog("onHfpLocal ");
				}

				@Override
				public void onHfpRemote() throws RemoteException{
								showlog("onHfpRemote ");
				}

				@Override
				public void onInPairMode() throws RemoteException{
								showlog("onInPairMode ");
				}

				@Override
				public void onExitPairMode() throws RemoteException{

				}

				@Override
				public void onOutGoingOrTalkingNumber(String number) throws RemoteException{
								showlog("onOutGoingOrTalkingNumber: " + number);
								BTPhonePeople btPhonePeople = new BTPhonePeople();
								btPhonePeople.setPhoneNumber(number);
								btPhonePeople.setType(BTPhonePeople.Type.CALL_OUT);
								EventPostUtils.Post(new BTContactEvent(BTStatu.CALL_OUT, btPhonePeople));
				}

				@Override
				public void onInitSucceed() throws RemoteException{
								showlog("onInitSucceed ");
				}

				@Override
				public void onConnecting() throws RemoteException{
								showlog("onConnecting ");
				}

				@Override
				public void onMusicInfo(String MusicName, String artist) throws RemoteException{
								showlog("onMusicInfo name = " + MusicName + "  artist = " + artist);
								if(StringUtil.isNotEmpty(MusicName)){
												BTMusic music = new BTMusic();
												music.musicName = MusicName;
												music.artist = artist;
												EventPostUtils.Post(new BTMusicEvent(BTStatu.MUSIC_INFO, music));
								}
				}

				@Override
				public void onMusicPlaying() throws RemoteException{
								showlog("onMusicPlaying ");
								EventPostUtils.Post(new BTMusicEvent(BTStatu.MUSIC_PLAY, null));
				}

				@Override
				public void onMusicStopped() throws RemoteException{
								showlog("onMusicStopped ");
								EventPostUtils.Post(new BTMusicEvent(BTStatu.MUSIC_STOP, null));
				}

				@Override
				public void onAutoConnectAccept(boolean autoConnect, boolean autoAccept) throws RemoteException{
								showlog("onAutoConnectAccept ");
				}

				/**
					* 得到当前已连接设备的地址
					*
					* @param addr
					* @throws RemoteException
					*/
				@Override
				public void onCurrentAddr(String addr) throws RemoteException{
								showlog("onCurrentAddr  " + addr);
								BTDevice device = new BTDevice();
								device.setDeviceAddr(addr);
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.CURRENT_ADDR, device));
				}

				/**
					* 得到当前已连接设备的名称
					*
					* @param name
					* @throws RemoteException
					*/
				@Override
				public void onCurrentName(String name) throws RemoteException{
								showlog("onCurrentName  " + name);
								BTDevice device = new BTDevice();
								device.setDeviceName(name);
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.CURRENT_NAME, device));
				}

				@Override
				public void onHfpStatus(int status) throws RemoteException{
								showlog("onHfpStatus  " + status);
								int btValue = BT_OFF_LINE;
								BTStatu statu = BTStatu.OFF_LINE;
								//1未连接; 3已连接; 4播出; 5播进; 6通话;
								switch(status){
												case HfpStatus_offline:{
																statu = BTStatu.OFF_LINE;
																break;
												}
												case HfpStatus_online:{
																btValue = BT_ON_LINE_USE;
																statu = BTStatu.ON_LINE;
																break;
												}
												case HfpStatus_callin:{
																btValue = BT_CALL_IN;
																statu = BTStatu.CALL_IN;
																break;
												}
												case HfpStatus_callout:{
																btValue = BT_CALL_OUT;
																statu = BTStatu.CALL_OUT;
																BTPhoneCmd.sendBTPhoneCmd(BTPhoneCmd.CALL_OUT);
																break;
												}
												case HfpStatus_talking:{
																btValue = BT_TALKING;
																statu = BTStatu.TALKING;
																break;
												}
												default:
																break;
								}

								showlog("onHfpStatus  " + status + "  btValue = " + btValue);

								String hfp_status = status >= 3 ? "true" : "false";
								SystemProperties.set(Constant.BT_CONNECTED_STATUS, hfp_status);
								EventPostUtils.Post(new BTStatuEvent(statu));

								if(BT_CALL_IN != btValue
																&& BT_CALL_OUT != btValue){
												sendBtPhoneBroadcast(btValue); // 将蓝牙状态同步给同行者
								}
				}

				@Override
				public void onAvStatus(int status) throws RemoteException{
								showlog("onAvStatus  status : " + status);
				}

				@Override
				public void onVersionDate(String version) throws RemoteException{
								showlog("onVersionDate  version : " + version);
				}

				/**
					* 当前设备自己的名称
					*
					* @param name
					* @throws RemoteException
					*/
				@Override
				public void onCurrentDeviceName(String name) throws RemoteException{
								showlog("onCurrentDeviceName  name : " + name);
								BTDevice device = new BTDevice();
								device.setDeviceName(name);
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.CURRENT_DEVICE_NAME, device));
				}

				/**
					* 当前设备自己的PinCode
					*
					* @param code
					* @throws RemoteException
					*/
				@Override
				public void onCurrentPinCode(String code) throws RemoteException{
								showlog("onCurrentPinCode  code : " + code);
								BTDevice device = new BTDevice();
								device.setPassword(code);
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.CURRENT_PINCODE, device));
				}

				@Override
				public void onA2dpConnected() throws RemoteException{
								showlog("onA2dpConnected ");
				}

				/**
					* 返回已匹配的蓝牙设备列表
					*
					* @param index
					* @param name
					* @param addr
					* @throws RemoteException
					*/
				@Override
				public void onCurrentAndPairList(int index, String name, String addr) throws RemoteException{
								showlog("onCurrentAndPairList  index: " + index + " name: " + name + " addr: " + addr);
								BTDevice device = new BTDevice();
								device.setDeviceName(name);
								device.setDeviceAddr(addr);
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.CURRENT_PAIRLIST, device));
				}

				@Override
				public void onA2dpDisconnected() throws RemoteException{
								showlog("onA2dpDisconnected ");
				}

				@Override
				public void onPhoneBook(String name, String number) throws RemoteException{
								if(StringUtil.isEmpty(number)){
												return;
								}

								showlog("onPhoneBook  " + name + "  : " + number);
								BTPhonePeople btPeople = new BTPhonePeople();
								btPeople.setType(BTPhonePeople.Type.Book);
								btPeople.setPeopleName(name);
								btPeople.setPhoneNumber(number);
								EventPostUtils.Post(new BTContactEvent(BTStatu.PHONE_BOOK, btPeople));
				}

				@Override
				public void onSimBook(String name, String number) throws RemoteException{

				}

				@Override
				public void onPhoneBookDone() throws RemoteException{
								showlog("onPhoneBookDone ");
								EventPostUtils.Post(new BTContactEvent(BTStatu.PHONE_BOOK_DONE, null));
				}

				@Override
				public void onSimDone() throws RemoteException{
								showlog("onSimDone ");
				}

				@Override
				public void onCalllogDone() throws RemoteException{
								showlog("onCalllog  done");
								EventPostUtils.Post(new BTContactEvent(BTStatu.CALL_LOG_DONE, null));
				}

				@Override
				public void onCalllog(int type, String number) throws RemoteException{
								if(StringUtil.isEmpty(number)){
												return;
								}
								String[] textNumber;
								textNumber = number.split("�");
								showlog(type + " :" + textNumber.length);
								if(textNumber.length < 2){
												return;
								}
								BTPhonePeople btPeople = new BTPhonePeople();
								btPeople.setPeopleName(textNumber[0].toString());
								btPeople.setPhoneNumber(textNumber[1].toString());

								switch(type){
												case callLog_Called:{//called
																btPeople.setType(BTPhonePeople.Type.Called);
																break;
												}
												case callLog_Answered:{//answered
																btPeople.setType(BTPhonePeople.Type.Answered);
																break;
												}
												case callLog_Missed:{//missed
																btPeople.setType(BTPhonePeople.Type.Missed);
																break;
												}
								}
								EventPostUtils.Post(new BTContactEvent(BTStatu.CALL_LOG, btPeople));
				}

				@Override
				public void onDiscovery(String name, String addr) throws RemoteException{
								showlog("onDiscovery  " + name + " , " + addr);
								BTDevice device = new BTDevice();
								device.setDeviceName(name);
								device.setDeviceAddr(addr);
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.DISCOVERY, device));
				}

				@Override
				public void onDiscoveryDone() throws RemoteException{
								showlog("DiscoveryDone");
								EventPostUtils.Post(new BTDeviceEvent(BTStatu.DISCOVERY_DONE, null));
				}

				@Override
				public void onLocalAddress(String addr) throws RemoteException{
								showlog("LocalAddress  " + addr);
				}

				@Override
				public void onSppData(int index, String data) throws RemoteException{

				}

				@Override
				public void onSppConnect(int index) throws RemoteException{

				}

				@Override
				public void onSppDisconnect(int index) throws RemoteException{

				}

				@Override
				public void onSppStatus(int status) throws RemoteException{

				}

				@Override
				public void onOppReceivedFile(String path) throws RemoteException{

				}

				@Override
				public void onOppPushSuccess() throws RemoteException{

				}

				@Override
				public void onOppPushFailed() throws RemoteException{

				}

				@Override
				public void onHidConnected() throws RemoteException{

				}

				@Override
				public void onHidDisconnected() throws RemoteException{

				}

				@Override
				public void onHidStatus(int status) throws RemoteException{
								showlog("onHidStatus" + status);
				}

				@Override
				public void onPanConnect() throws RemoteException{

				}

				@Override
				public void onPanDisconnect() throws RemoteException{

				}

				@Override
				public void onPanStatus(int status) throws RemoteException{

				}

				@Override
				public void onDtmfNumber(String number) throws RemoteException{
								showlog("onDtmfNumber" + number);
				}

				/**
					* 修改通话音量
					* @param value
					* @throws RemoteException
					*/
				@Override
				public void onSpeakerSwitch(int value) throws RemoteException{
								showlog("  onSpeakerSwitch  value = " + value);
								try{
												int index = value;
												if (value > 3){
																index = value / 3;
												}else{
																index = 0;
												}
												AudioManager	audioManager = (AudioManager) BaseApplication.getContext().getSystemService(BaseApplication.getContext().AUDIO_SERVICE);
												showlog("  onSpeakerSwitch  index = " + index);
												// 设置通话的音量
												audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, index, 0);
								}
								catch(Exception ex){
												showlog("  onSpeakerSwitch ex : " + ex.toString());
								}
				}

				@Override
				public void onSignalBatteryVal(int signal, int battery) throws RemoteException{

				}

				public void showlog(String text){
								boolean k = Config.IS_DEBUG;
								if(k){
												Log.v("LFBT Goc", "@@@@@  --> " + text);
								}
				}

				/* 以广播方式同步蓝牙状态给同行者，解决同行者语音和蓝牙电话不能共存的问题
				  蓝牙状态value对应关系如下：
						1000：蓝牙连接
						1001：蓝牙断开
						1002：蓝牙空闲且可用（已连接）
						1003：蓝牙电话来电
						1004：蓝牙电话拒接
						1005：蓝牙电话接听（通话中）
						1006：蓝牙电话挂断 	*/
				private void sendBtPhoneBroadcast(int value){
								// 保存蓝牙状态
								SystemProperties.set(Constant.KEY_BT_STATE, String.valueOf(value));
								Intent intent = new Intent(Constant.Action.ACTION_LF_TXZ_BT_STATE);
								intent.putExtra("btphone", value); // value为当前蓝牙状态
								BaseApplication.getContext().sendBroadcast(intent);
				}
}
