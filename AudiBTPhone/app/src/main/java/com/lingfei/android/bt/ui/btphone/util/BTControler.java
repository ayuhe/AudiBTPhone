package com.lingfei.android.bt.ui.btphone.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;

import com.goodocom.gocsdk.IGocsdkService;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;
import com.lingfei.android.bt.ui.btphone.callback.GocsdkCallback;
import com.lingfei.android.bt.ui.btphone.event.BTSearchContactStartEvent;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
	* 对蓝牙电话操作的相关api
	* Created by Administrator on 2016/7/13.
	*/
public class BTControler{
				private static BTControler instance;

				public static BTControler getInstance(){
								if(null == instance){
												instance = new BTControler();
								}
								return instance;
				}

				private GocsdkCallback gocCallback;
				private IGocsdkService iGocService = null;
				private static HashMap<Context, ServiceBinder> sConnectionMap = new HashMap<>();
				private List<BTPhonePeople> phonePeoples = new ArrayList<>(); // 保存当前蓝牙设备的联系人信息
				private boolean isBTHfpConnected = false; // 标识当前蓝夜设备的连接状态
				private int mHfpStatus = 1; // 记录连接状态，默认未连接
				private BTPhonePeople peopleTalking = null;// 用于保存拨出号码信息
				/**
					* 保存当前连接的蓝牙设备地址
					*/
				private String currentAddr = "";

				/**
					* 保存本机蓝牙设备名称
					*/
				private String localBtName = "";

				public boolean isBTHfpConnected(){
								return isBTHfpConnected;
				}

				public void setBTHfpConnected(boolean BTHfpConnected){
								isBTHfpConnected = BTHfpConnected;
				}

				public int getHfpStatus(){
								return mHfpStatus;
				}

				public void setHfpStatus(int mHfpStatus){
								this.mHfpStatus = mHfpStatus;
				}

				public IGocsdkService getiGocService(){
								return iGocService;
				}

				public List<BTPhonePeople> getPhonePeoples(){
								return phonePeoples;
				}

				public void setPhonePeoples(List<BTPhonePeople> phonePeoples){
								if(null != this.phonePeoples){
												if(!this.phonePeoples.isEmpty()){
																this.phonePeoples.clear();
												}
												this.phonePeoples.addAll(phonePeoples);
								}
				}

				/**
					* 清空联系人
					*/
				public void clearPhonePeoples(){
								if(null != this.phonePeoples){
												this.phonePeoples.clear();
								}
				}
				/**
					* 设置正在通话的信息
					*
					* @param name   姓名
					* @param number 电话
					*/
				public void setTalkingInfo(String name, String number, String type){
								if(null == peopleTalking){
												peopleTalking = new BTPhonePeople();
								}
								peopleTalking.setType(type);
								if(StringUtil.isNotEmpty(name)){
												peopleTalking.setPeopleName(name);
								}

								if(StringUtil.isNotEmpty(number)){
												peopleTalking.setPhoneNumber(number);
								}
				}

				public BTPhonePeople getPeopleTalking(){
								return peopleTalking;
				}

				/**
					* 重置正在通话的信息
					*/
				public void onResetPeopleTaiking(){
								if(null != peopleTalking){
												peopleTalking.setPeopleName("");
												peopleTalking.setPhoneNumber("");
												peopleTalking.setType("");
								}
				}

				public String getCurrentAddr(){
								return currentAddr;
				}

				public void setCurrentAddr(String currentAddr){
								this.currentAddr = currentAddr;
				}

				public String getLocalBtName(){
								return localBtName;
				}

				public void saveLocalBtName(String localBtName){
								this.localBtName = localBtName;
				}

				public static class ServiceToken{
								ContextWrapper mWrappedContext;
								ServiceToken(ContextWrapper context){
												mWrappedContext = context;
								}
				}

				public ServiceToken bindToService(Context context){
								if(gocCallback == null){
												gocCallback = new GocsdkCallback();
								}
								return bindToService(context, null);
				}

				public ServiceToken bindToService(Context context, ServiceConnection callback){
								if(null != context){
												Intent intent = new Intent("com.goodocom.gocsdk.service.GocsdkService");
												ContextWrapper cw = new ContextWrapper(context);
												ServiceBinder sb = new ServiceBinder(callback);
												if(cw.bindService(intent, sb, Context.BIND_AUTO_CREATE)){
																sConnectionMap.put(cw, sb);
																return new ServiceToken(cw);
												}
								}
								LoggerUtil.e("Failed to bind to service");
								return null;
				}

				public void unBindFromService(ServiceToken token){
								if(token == null){
												LoggerUtil.e("Trying to unbind with null token");
												return;
								}
								ContextWrapper cw = token.mWrappedContext;
								ServiceBinder sb = sConnectionMap.remove(cw);
								if(sb == null){
												LoggerUtil.e("Trying to unbind for unknown Context");
												return;
								}
								cw.unbindService(sb);

								if(sConnectionMap.isEmpty()){
												iGocService = null;
								}
				}

				private class ServiceBinder implements ServiceConnection{
								ServiceConnection mCallback;

								ServiceBinder(ServiceConnection callback){
												mCallback = callback;
								}

								@Override
								public void onServiceConnected(ComponentName className, android.os.IBinder binder){
												iGocService = IGocsdkService.Stub.asInterface(binder);
												if(iGocService != null){
																try{
																				ShowLogUtil.show("onServiceConnected");
																				iGocService.GOCSDK_registerCallback(gocCallback);
																				checkBTHfpConnectStatus();
																				// 自动连接最近的蓝牙设备
																				connectLastAddr();
																				// 获取当前蓝牙设备名称
																				getCurrentBTDeviceLocalName();
																}
																catch(RemoteException e){
																				e.printStackTrace();
																}
												}

												if(mCallback != null){
																mCallback.onServiceConnected(className, binder);
												}
								}

								@Override
								public void onServiceDisconnected(ComponentName className){
												if(iGocService != null){
																try{
																				ShowLogUtil.show("GOCSDK_unregisterCallback");
																				iGocService.GOCSDK_unregisterCallback(gocCallback);
																}
																catch(RemoteException e){
																				e.printStackTrace();
																}
												}

												if(mCallback != null){
																mCallback.onServiceDisconnected(className);
												}
								}
				}

				//==================================  通话 电话本 记录 =============================
				// 拨电话
				public void toCall(String number){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_phoneDail(number);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				public void toAnswer(){//接电话
								if(iGocService != null){
												try{
																iGocService.GOCSDK_phoneAnswer();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				public void toHangup(){//挂断电话
								if(iGocService != null){
												try{
																iGocService.GOCSDK_phoneHangUp();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 查询电话本
				public void openPhoneBook(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_phoneBookStartUpdate();
																EventPostUtils.Post(new BTSearchContactStartEvent());
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 查询通话记录
				public void openCallRecord(int type){
								if(iGocService != null){
												//  读取通话记录
												//	  3 = 已拨号码
												//	  4 = 未接号码
												//	  5 = 已接号码
												try{
																iGocService.GOCSDK_callLogstartUpdate(type);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				//================================== 音乐  =============================

				/**
					* 查询蓝牙音乐信息
					*/
				public void searchBTMusicInfo(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_InquiryMusicInfo();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				/**
					* 下一首
					*/
				public void setMusicNext(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_musicNext();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				/**
					* 上一首
					*/
				public void setMusicPrevious(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_musicPrevious();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 停止音乐
				public void setMusicStop(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_musicStop();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 暂停 音乐
				public void setMusicPause(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_PauseMusic();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 暂停/播放 音乐
				public void setMusicPlayOrPause(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_musicPlayOrPause();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 音量
				public void setMusicVal(int val){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_SetMusicVal(val);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				//================================== 设置 连接  =============================
				// 检查蓝牙连接状态
				public void checkBTHfpConnectStatus(){
								if(iGocService != null){
												try{
																ShowLogUtil.show("checkBTHfpConnectStatus");
																iGocService.GOCSDK_InquiryHfpStatus();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 根据地址连接hfp设备
				public void connectHfpAddr(String addr){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_connectHFP(addr);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				/**
					* 连接最后个已配对设备
					*/
				public void connectLastAddr(){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_connectLast");
																iGocService.GOCSDK_connectLast();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 断开蓝牙设备设备
				public void disConnect(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_disconnect();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 断开当前hfp设备
				public void disconnectHfp(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_disconnectHFP();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 获取当前已连接bt设备名称
				public void getCurConnectedBtName(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_InquiryCurBtName();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 获取当前已连接bt设备地址
				public void getCurConnectedBtAddr(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_InquiryCurBtAddr();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 本地蓝牙名字
				public void getCurrentBTDeviceLocalName(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_getLocalName();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 获取本地蓝牙地址
				public void getCurrentBTDeviceLocalAddr(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_InquiryLocalAddr();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 获取本地蓝牙密码
				public void getCurrentBTDeviceLocalPassword(){
								if(iGocService != null){
												try{
																iGocService.GOCSDK_getPinCode();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 修改本地蓝牙名称
				public void setLocalBTName(String name){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_setLocalName");
																iGocService.GOCSDK_setLocalName(name);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				// 改本地蓝牙密码
				public void setLocalBTPassword(String password){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_setPinCode");
																iGocService.GOCSDK_setPinCode(password);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				/**
					* 获取已知的蓝牙设备列表
					*/
				public void openPairList(){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_getPairList");
																iGocService.GOCSDK_getPairList();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				/**
					* 搜索蓝牙设备
					*/
				public void searchNewBTDevice_Start(){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_startDiscovery");
																iGocService.GOCSDK_startDiscovery();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				public void searchNewBTDevice_Stop(){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_stopDiscovery");
																iGocService.GOCSDK_stopDiscovery();
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

				/**
					* 删除指定已配对蓝牙设备, isEmpty删除所有蓝牙设备
					*/
				public void deletePair(String addr){
								if(iGocService != null){
												try{
																ShowLogUtil.show("GOCSDK_deletePair addr : " + addr);
																iGocService.GOCSDK_deletePair(addr);
												}
												catch(RemoteException e){
																e.printStackTrace();
												}
								}
				}

}
