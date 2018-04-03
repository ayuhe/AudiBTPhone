package com.lingfei.android.bt.ui.btphone.service;

import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemProperties;

import com.lingfei.android.bt.ui.btmusic.bean.BTMusic;
import com.lingfei.android.bt.ui.btmusic.event.BTMusicEvent;
import com.lingfei.android.bt.ui.btmusic.event.FinishBTMusicEvent;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;
import com.lingfei.android.bt.ui.btphone.callback.GocsdkCallback;
import com.lingfei.android.bt.ui.btphone.event.BTContactEvent;
import com.lingfei.android.bt.ui.btphone.event.BTDeviceEvent;
import com.lingfei.android.bt.ui.btphone.event.BTSearchContactDoneEvent;
import com.lingfei.android.bt.ui.btphone.event.BTStatuEvent;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.bt.ui.btphone.util.BTStatu;
import com.lingfei.android.business.cmd.receive.switchview.SwitchUtil;
import com.lingfei.android.business.cmd.send.BTPhoneCmd;
import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.PinYinUtils;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class BTPhoneService extends Service{
				private BTControler.ServiceToken mToken = null;
				private AudioManager audioManager;
				private Map<String, BTPhonePeople> phoneBookMap = new HashMap<>();
				private Map<String, String> contactMap = new HashMap<>(); // 用于保存联系人更新给同行者

				private boolean isPhoneBookDone = false; // 标识电话本是否更新完成

				public void setToast(final String tip){
								if(StringUtil.isNotEmpty(tip)){
												Handler handler = new Handler(Looper.getMainLooper());
												handler.post(new Runnable(){
																@Override
																public void run(){
																				ToastUtils.showToast(tip);
																}
												});
								}
				}

				public void checkCurrentBTDeviceAddr(BTDevice btDevice){
								String currentAddr = BTControler.getInstance().getCurrentAddr();
								if(!currentAddr.equals(btDevice.getDeviceAddr())){
												BTControler.getInstance().setCurrentAddr(btDevice.getDeviceAddr());
												BTControler.getInstance().openPhoneBook(); // 查询电话本联系人
								}
								else{
												if(phoneBookMap != null && phoneBookMap.isEmpty()){
																BTControler.getInstance().openPhoneBook(); // 查询电话本联系人
												}
								}
				}

				// 当蓝牙连接 或者 断开
				public void initServcieData(boolean isConnected){
								BTControler.getInstance().setBTHfpConnected(isConnected);
								int btState = 0;
								if(isConnected){ // 已连接
												// 查询电话本联系人
												BTControler.getInstance().openPhoneBook();
												btState = 1;
								}
								else{
												isPhoneBookDone = false;
												BTControler.getInstance().setCurrentAddr("");
												BTControler.getInstance().clearPhonePeoples();
												if(null != phoneBookMap){
																phoneBookMap.clear();
												}
												if(null != contactMap){
																contactMap.clear();
												}
												notifyUpdatePhoneBook();
								}
				}

				/**
					* 通知更新通话状态
					*
					* @param state 0为没通话，1为通话中
					*/
				private void notifyBTStateTaiking(int state){
								Intent intent = new Intent();
								intent.setAction(Constant.Action.ACTION_BT_STATE_TAIKING);
								intent.putExtra("state", state);
								intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
								sendBroadcast(intent); // 发送广播
				}

				// 更新电话本
				private void notifyUpdatePhoneBook(){
								List<BTPhonePeople> phonePeoples = new ArrayList<>(phoneBookMap.values());
								EventPostUtils.Post(new BTSearchContactDoneEvent(phonePeoples));
				}

				/* 以广播方式同步蓝牙状态给同行者，解决同行者语音和蓝牙电话不能共存的问题
				  蓝牙状态value对应关系如下：
		    1000：蓝牙连接
		    1001：蓝牙断开
		    1002：蓝牙空闲且可用（已连接）
		    1003：蓝牙电话来电
		    1004：蓝牙电话拒接
		    1005：蓝牙电话接听（通话中）
		    1006：蓝牙电话挂断
		    1007: 蓝牙主动拨号*/
				private void sendBtPhoneBroadcast(int value, String name, String num){
								// 保存蓝牙状态
								SystemProperties.set(Constant.KEY_BT_STATE, String.valueOf(value));

								Intent intent = new Intent(Constant.Action.ACTION_LF_TXZ_BT_STATE);
								intent.putExtra("btphone", value); // value为当前蓝牙状态
								if(StringUtil.isNotEmpty(name)){
												intent.putExtra("name", name);
								}
								if(StringUtil.isNotEmpty(num)){
												intent.putExtra("num", num);
								}
								sendBroadcast(intent);
				}

				/**
					* 根据号码检索姓名
					*
					* @param number
					* @return
					*/
				private String getPhoneNameByNumber(String number){
								String name = number;
								if(phoneBookMap != null && !phoneBookMap.isEmpty()){
												ShowLogUtil.show("电话本不为空");
												BTPhonePeople btPhonePeople = phoneBookMap.get(number);
												if(null != btPhonePeople){
																name = btPhonePeople.getPeopleName();
																ShowLogUtil.show("匹配成功");
												}
								}
								else{
												ShowLogUtil.show("电话本为空,无法匹配");
								}
								return name;
				}

				//======================音频焦点======================
				public void initAudioManage(){
								audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
								int index = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
								LoggerUtil.e("LingFei  voice call index = " + index);
								// 设置通话的音量
								audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, index, 0);
				}

				// 请求焦点
				public void requestAudioFocus(){
								if(audioManager != null){
												int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
								}
				}

				// 放弃音频焦点
				public void abandonAudioFocus(){
								if(audioManager != null){
												audioManager.abandonAudioFocus(afChangeListener);
								}
				}

				AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener(){
								@Override
								public void onAudioFocusChange(int focusChange){
								}
				};

				/**
					* 保存电话本联系人的数据
					*
					* @param btPeople
					*/
				public void onSaveBTPhonePeople(BTPhonePeople btPeople){
								if(btPeople != null && StringUtil.isNotEmpty(btPeople.getPhoneNumber())){
												String name = btPeople.getPeopleName();
												if(StringUtil.isNotEmpty(name)){
																// 设置拼音
																btPeople.setPinyin(PinYinUtils.getPinyin(name));
												}
												else{
																btPeople.setPinyin("z");
												}

												// 保存联系人
												phoneBookMap.put(btPeople.getPhoneNumber(), btPeople);
												contactMap.put(btPeople.getPhoneNumber(), btPeople.getPeopleName());
								}
				}

				/**
					* 处理来电
					*
					* @param phonePeople
					*/
				private void onHandleCallIn(BTPhonePeople phonePeople){
								if(StringUtil.isNotEmpty(phonePeople.getPhoneNumber())){
												String number = phonePeople.getPhoneNumber();
												String name = getPhoneNameByNumber(number);
												sendBtPhoneBroadcast(GocsdkCallback.BT_CALL_IN, name, number);
												BTControler.getInstance().setTalkingInfo(name, number, BTPhonePeople.Type.Callin);
												BTPhonePeople phonePeo = BTControler.getInstance().getPeopleTalking();
												EventPostUtils.Post(new BTContactEvent(BTStatu.CALL_IN_NEXT, phonePeo));

												ShowLogUtil.show("显示接听电话界面");
												Intent intent = new Intent("com.lingfei.android.PhoneInComingActivity");
												intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												intent.putExtra("comingPhoneName", name);
												intent.putExtra("comingPhoneNumber", number);
												startActivity(intent);
								}
				}

				/**
					* 启动蓝牙电话界面
					*/
				public void startBTPhoneActivty(){
								try{
												ShowLogUtil.show("startBTPhoneActivty");
												Intent intent = new Intent(Constant.Action.ACTION_LF_BT_PHONE);
												intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												intent.putExtra("fragment_type", 0);
												intent.putExtra("call_type", "callout");
												startActivity(intent);
								}
								catch(ActivityNotFoundException e){
												LoggerUtil.e(e.toString());
								}
								catch(Exception ex){
												ex.printStackTrace();
								}
				}

				/**
					* 将联系人同步给同行者
					*/
				public void startResponseContacts(){
								if(null != contactMap && !contactMap.isEmpty()){
												LoggerUtil.e("BTPhoneService  同步联系人 size = " + contactMap.size());
												try{
																Intent intent = new Intent(Constant.Action.ACTION_LF_CAR_SERVICE);
																intent.setPackage("com.lingfei.bmw.idfive");
																intent.putExtra("cmd_type", Constant.Voice.RESPONSE_CONTACTS);
																intent.putExtra("contacts", (Serializable) contactMap);
																startService(intent);
												}
												catch(Exception ex){
																ex.printStackTrace();
												}
								}
				}

				/**
					* 处理更新蓝牙状态的相关事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.BackgroundThread)
				public void onEventBTStatuChange(BTStatuEvent event){
								if(null != event){
												switch(event.getStatu()){
																case CONNECTED:{
																				ShowLogUtil.show("蓝牙连接");
																				setToast("蓝牙设备连接");
																				initServcieData(true);
																				break;
																}
																case DISCONNECTED:{
																				ShowLogUtil.show(" 蓝牙断开");
																				initServcieData(false);
																				BTControler.getInstance().setHfpStatus(GocsdkCallback.HfpStatus_offline);
																				break;
																}
																case ON_LINE:{ // 多次触发
																				ShowLogUtil.show("BT HFP 连接");
																				BTControler.getInstance().setBTHfpConnected(true);
																				break;
																}
																case OFF_LINE:{ // 多次触发
																				ShowLogUtil.show("BT HFP 断开");
																				BTControler.getInstance().setBTHfpConnected(false);
																				break;
																}
																case CALL_IN:
																case CALL_OUT:{
																				BTControler.getInstance().setHfpStatus(GocsdkCallback.HfpStatus_callout);
																				notifyBTStateTaiking(1);
																				break;
																}
																case TALKING:{
																				BTControler.getInstance().getPeopleTalking().setType(BTPhonePeople.Type.TALKING);
																				BTControler.getInstance().setHfpStatus(GocsdkCallback.HfpStatus_talking);
																				notifyBTStateTaiking(1);
																				break;
																}
																case HANG_UP:{
																				BTControler.getInstance().onResetPeopleTaiking();
																				Handler handler = new Handler(Looper.getMainLooper());
																				handler.postDelayed(new Runnable(){
																								@Override
																								public void run(){
																												notifyBTStateTaiking(0);
																								}
																				}, 2000);

																				BTControler.getInstance().setHfpStatus(GocsdkCallback.HfpStatus_online);
																				abandonAudioFocus();
																				break;
																}
																case ON_TALKING:
																				break;
																case ON_RING_START:{
																				break;
																}
																case ON_RING_STOP:{
																				break;
																}
																default:
																				break;
												}
								}
				}

				/**
					* 处理蓝牙模块联系人相关事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.BackgroundThread)
				public void onEventBTContactChange(BTContactEvent event){
								if(null != event){
												switch(event.getStatu()){
																case PHONE_BOOK:{
																				BTPhonePeople phonePeople = event.getPhonePeople();
																				onSaveBTPhonePeople(phonePeople);
																				break;
																}
																case CALL_OUT:{
																				requestAudioFocus();
																				// 告诉mcu要拨打电话
																				BTPhoneCmd.sendBTPhoneCmd(BTPhoneCmd.CALL_OUT);
																				BTPhonePeople phonePeople = event.getPhonePeople();
																				if(null != phonePeople){
																								String number = phonePeople.getPhoneNumber();
																								String name = getPhoneNameByNumber(number);
																								// 进行拨打电话
																								sendBtPhoneBroadcast(GocsdkCallback.BT_CALL_OUT, name, number);
																								BTControler.getInstance().setTalkingInfo(name, number, BTPhonePeople.Type.CALL_OUT);
																								EventPostUtils.Post(new BTContactEvent(BTStatu.CALL_OUT_NEXT, BTControler.getInstance().getPeopleTalking()));
																								startBTPhoneActivty();

																								// 正在通话中,解决拨号时没有拨号铃声的问题
																								sendBtPhoneBroadcast(GocsdkCallback.BT_TALKING, "", "");
																				}
																				break;
																}
																case PHONE_BOOK_DONE:{
																				List<BTPhonePeople> phonePeoples = new ArrayList<>(phoneBookMap.values());
																				// 对集合排序
																				Collections.sort(phonePeoples, new Comparator<BTPhonePeople>(){
																								@Override
																								public int compare(BTPhonePeople lhs, BTPhonePeople rhs){
																												// 根据拼音进行排序
																												return lhs.getPinyin().compareTo(rhs.getPinyin());
																								}
																				});

																				BTControler.getInstance().setPhonePeoples(phonePeoples);
																				EventPostUtils.Post(new BTSearchContactDoneEvent(phonePeoples));
																				isPhoneBookDone = true;
																				startResponseContacts(); // 将联系人同步给同行者
																				break;
																}
																case CALL_IN:{
																				requestAudioFocus();
																				ShowLogUtil.show("来电");
																				BTPhonePeople phonePeople = event.getPhonePeople();
																				onHandleCallIn(phonePeople);
																				break;
																}
																default:
																				break;
												}
								}
				}

				/**
					* 处理蓝牙设备相关的事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.BackgroundThread)
				public void onEventBTDeviceChange(BTDeviceEvent event){
								if(null != event){
												switch(event.getStatu()){
																case CURRENT_ADDR:{
																				BTDevice device = event.getDevice();
																				if(StringUtil.isNotEmpty(device.getDeviceAddr())){
																								checkCurrentBTDeviceAddr(device);
																				}
																				break;
																}
																case CURRENT_DEVICE_NAME:{
																				BTDevice device = event.getDevice();
																				if(null != device && StringUtil.isNotEmpty(device.getDeviceName())){
																								// 本机蓝牙设备的名称
																								BTControler.getInstance().saveLocalBtName(device.getDeviceName());
																				}
																				break;
																}
																default:
																				break;
												}
								}
				}

				/**
					* 处理蓝牙音乐相关事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.BackgroundThread)
				public void onEventBTMusicInfo(BTMusicEvent event){
								if(null != event){
												ShowLogUtil.show(" 蓝牙音乐 " + event.getStatu().name());
												switch(event.getStatu()){
																case MUSIC_INFO:{
																				BTMusic music = event.getMusic();
																				if(null != music){
																								ShowLogUtil.show(" music name = " + music.musicName + " + artist = " + music.artist);
																				}
																				break;
																}
																case MUSIC_PLAY:{
																				// 暂停同听
																				SwitchUtil.doStartLingFeiCarService(Constant.Voice.PAUSE_TXT_MUSIC, null);
																				SystemProperties.set("net.btmusice_status", "true");
																				break;
																}
																case MUSIC_STOP:{
																				SystemProperties.set("net.btmusice_status", "false");
																}
																default:
																				break;

												}
								}
				}

				@Override
				public void onCreate(){
								super.onCreate();
								EventBus.getDefault().register(this);
								initAudioManage();
								ShowLogUtil.show("  BTPhoneService  onCreate");
								mToken = BTControler.getInstance().bindToService(this);
				}

				@Override
				public IBinder onBind(Intent intent){
								return null;
				}

				@Override
				public int onStartCommand(Intent intent, int flags, int startId){
								if(null != intent){
												String command = intent.getStringExtra("command");
												ShowLogUtil.show("  BTPhoneService  command = " + command);
												if(StringUtil.isNotEmpty(command)){
																switch(command){
																				case Constant.CMD_PAUSE_BTMUSIC:
																								// 暂停音乐
																								BTControler.getInstance().setMusicPause();
																								EventPostUtils.Post(new FinishBTMusicEvent()); // 同时退出蓝牙音乐界面
																								break;
																				case Constant.CMD_PLAY_BTMUSIC:
																								BTControler.getInstance().setMusicPlayOrPause();
																								break;
																				case Constant.Voice.CMD_BT_REQUEST_CONTACTS:
																								if(isPhoneBookDone){
																												startResponseContacts();
																								}
																								break;
																				case Constant.Voice.CMD_BT_DIAL:
																								String number = intent.getStringExtra("number");
																								String name = intent.getStringExtra("name");
																								if(StringUtil.isNotEmpty(number)){
																												BTControler.getInstance().toCall(number);
																								}
																								break;
																				case Constant.Voice.CMD_BT_ANSWER:
																								BTControler.getInstance().toAnswer();
																								break;
																				case Constant.Voice.CMD_BT_REJECT:
																				case Constant.Voice.CMD_BT_HANG_UP:
																								BTControler.getInstance().toHangup();
																								break;
																				default:
																								break;
																}
												}
								}

								return super.onStartCommand(intent, flags, startId);
				}

				@Override
				public void onDestroy(){
								super.onDestroy();
								BTControler.getInstance().unBindFromService(mToken);
								// 发广播重启该Service
								sendBroadcast(new Intent(Constant.Action.ACTION_RESET_BT_PHONE_SERVICE));
								EventBus.getDefault().unregister(this);
				}
}
