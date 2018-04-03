package com.lingfei.android.business.cmd.receive.switchview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.lingfei.android.business.bean.VoiceBean;
import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.business.cmd.send.MenuCmd;
import com.lingfei.android.business.event.BackHomeEvent;
import com.lingfei.android.business.event.MediaUIFinishEvent;
import com.lingfei.android.business.serial.SerioUtils;
import com.lingfei.android.uilib.AppManager;
import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.PackageUtil;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.SysSharePres;
import com.lingfei.android.uilib.util.ToastUtils;

/**
	* 解析切源指令
	* Created by heyu on 2016/8/30.
	*/
public class SwitchUtil extends BaseReceive{

				private static final int LENGHT = 5; // 有效数据的长度
				private static final int TOTAL_LENGHT = 3 + LENGHT; // 数据的总长度

				public static boolean isNotFirstSwitch = false; // 标记是否第一次切源

				/**
					* 判断是否为切源指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isSwitchCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				LENGHT == Integer.valueOf(datas[Pos.LENGHT]) &&
																				0x82 == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				0x00 == Integer.valueOf(datas[Pos.DATA_TYPE]) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 获取切源指令
					* 自己的源  原车的源
					* CD：        0x05  0x02
					* 车辆：      0x05  0x07
					* 多媒体接口1 0x05  0x08
					* 媒体界面2   0x05  0x0B
					* TV          0x06  0x06
					* 手机互联    0x10  0x06
					* 音乐        0x0e  0x06
					* 视频        0x08  0x06
					* 蓝牙音频    0x12  0x06
					* 蓝牙电话    0x07  0x06
					* 导航        0x4   0x05
					* 菜单        0x11  0x05
					* 唤醒语音    0x14  0x06
					*
					* @param datas
					* @return 标识要切换到哪个界面
					*/
				public static SwitchCmd getSwitchCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data5 = Integer.valueOf(datas[5]);
												int data6 = Integer.valueOf(datas[6]);
												if(0x05 == data5 &&
																				0x01 == data6){
																return SwitchCmd.CMD_MENU_RADIO;
												}
												else if(0x05 == data5 &&
																				0x02 == data6){
																return SwitchCmd.CMD_MENU_CD;
												}
												else if(0x05 == data5 &&
																				0x07 == data6){
																return SwitchCmd.CMD_MENU_CAR;
												}
												else if(0x05 == data5 &&
																				0x08 == data6){
																return SwitchCmd.CMD_MENU_MEDIA_INTERFACE;
												}
												else if(0x05 == data5 &&
																				0x0B == data6){
																return SwitchCmd.CMD_MENU_MEDIA_UI;
												}
												else if(0x06 == data5 &&
																				0x06 == data6){
																return SwitchCmd.CMD_MENU_TV;
												}
												else if(0x10 == data5 &&
																				0x06 == data6){
																return SwitchCmd.CMD_MENU_PHONE_LINK;
												}
												else if(0x0E == data5 &&
																				0x06 == data6){
																return SwitchCmd.CMD_MENU_MUSIC;
												}
												else if(0x08 == data5 &&
																				0x06 == data6){
																return SwitchCmd.CMD_MENU_VIDEO;
												}
												else if(0x05 == data5 &&
																				0x0D == data6){
																return SwitchCmd.CMD_MENU_BT_MUSIC;
												}
												else if(0x05 == data5 &&
																				0x0C == data6){
																return SwitchCmd.CMD_MENU_BT;
												}
												else if(0x13 == data5 &&
																				0x06 == data6){
																return SwitchCmd.CMD_MENU_MEDIA;
												}
												else if(0x04 == data5 &&
																				0x09 == data6){
																return SwitchCmd.CMD_MENU_NAVI;
												}
												else if(0x11 == data5 &&
																				0x05 == data6){
																return SwitchCmd.CMD_MENU_MAIN;
												}
												else if(0x14 == data5 &&
																				0x06 == data6){
																return SwitchCmd.CMD_MENU_START_VOICE;
												}
								}

								return null;
				}

				/**
					* 打印log
					*
					* @param datas
					*/
				private static void showStringArrayLog(String[] datas){
								StringBuffer strbuf = new StringBuffer();
								for(int i = 0; i < datas.length; i++){
												strbuf.append(datas[i]).append(",");
								}
								ToastUtils.showToastDebug("datas = " + strbuf.toString());
				}

				/**
					* 获取切源指令并切换操作
					*
					* @param datas
					*/
				public static void switchActivity(String[] datas){
								if(!isSwitchCmd(datas)){
												return;
								}
								printStringArrayLog("SwitchCmd : ", datas);
								// 获取切源指令
								SwitchCmd switchCmd = SwitchUtil.getSwitchCmd(datas);
								if(null != switchCmd){
												byte cmd[] = null;
												Intent mIntent = new Intent(Intent.ACTION_PICK);
												switch(switchCmd){
																case CMD_MENU_MAIN:
																				if(isNotFirstSwitch){
																								// 如果不是第一次按下星号键，则返回到主菜单界面
																								AppManager.getAppManager().gotoHomeActivity();
																								EventPostUtils.Post(new BackHomeEvent());

																								// 返回home界面
																								Intent intent = new Intent(Intent.ACTION_MAIN);
																								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																								intent.addCategory(Intent.CATEGORY_HOME);
																								LibApplication.getContext().startActivity(intent);
																				}
																				else{
																								isNotFirstSwitch = true;
																				}

																				cmd = MenuCmd.main_menu;
																				break;
																case CMD_MENU_RADIO:
																				cmd = MenuCmd.media_radio;
																				EventPostUtils.Post(new MediaUIFinishEvent());
																				break;
																case CMD_MENU_CD:
																				cmd = MenuCmd.media_cd;
																				EventPostUtils.Post(new MediaUIFinishEvent());
																				break;
																case CMD_MENU_BT:
																				mIntent.setAction(Constant.Action.ACTION_LF_BT_PHONE);
																				mIntent.setDataAndType(Uri.EMPTY, SwitchIntentType.BT_PHONE);
																				cmd = MenuCmd.menu_phone;
																				break;
																case CMD_MENU_BT_MUSIC:
																				mIntent.setDataAndType(Uri.EMPTY, SwitchIntentType.BT_MUSIC);
																				cmd = MenuCmd.media_bt;
																				EventPostUtils.Post(new MediaUIFinishEvent());
																				break;
																case CMD_MENU_CAR:
																				cmd = MenuCmd.memu_car;
																				break;
																case CMD_MENU_MEDIA_INTERFACE:
																				cmd = MenuCmd.media_interface;
																				break;
																case CMD_MENU_MEDIA_UI:
																				cmd = MenuCmd.media_ui;
																				break;
																case CMD_MENU_MUSIC:
																				mIntent.setDataAndType(Uri.EMPTY, SwitchIntentType.MUSIC);
																				cmd = MenuCmd.media_music;
																				break;
																case CMD_MENU_NAVI:
																				cmd = MenuCmd.memu_navi;
																				if(!SysSharePres.getInstance().getOriginalCarNaviId()){
																								PackageUtil.gotoOtherApp(LibApplication.getContext(), SwitchPackageName.NAVI, "无法打开此应用");
																				}
																				break;
																case CMD_MENU_PHONE_LINK:
																				cmd = MenuCmd.media_phone_link;
																				break;
																case CMD_MENU_TV:
																				cmd = MenuCmd.media_tv;
																				break;
																case CMD_MENU_VIDEO:
																				mIntent.setDataAndType(Uri.EMPTY, SwitchIntentType.VIDEO);
																				cmd = MenuCmd.media_video;
																				break;
																case CMD_MENU_MEDIA:
																				mIntent.setDataAndType(Uri.EMPTY, SwitchIntentType.MEDIA);
																				cmd = MenuCmd.menu_media;
																				break;
																case CMD_MENU_START_VOICE:
																				startLaunchVoiceActivity();
																				return;
																default:
																				break;
												}

												// 发指令给mcu
												SerioUtils.getInstance().send_command_to_serio(cmd);

												if(StringUtil.isEmpty(mIntent.getType())){
																return;
												}
												try{
																mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																LibApplication.getContext().startActivity(mIntent);
												}
												catch(ActivityNotFoundException e){
																LoggerUtil.e(e.toString());
												}
								}
				}

				private static void startLaunchVoiceActivity(){
								SerioUtils.getInstance().send_command_to_serio(MenuCmd.menu_start_voice);
								try{
												doStartLingFeiCarService(Constant.Voice.WAKE_UP_VOICE, null);
								}
								catch(ActivityNotFoundException e){
												LoggerUtil.e(e.toString());
								}
								catch(Exception e){
												LoggerUtil.e(e.toString());
								}
				}

				/**
					* 启动凌飞车机服务
					*
					* @param type
					*/
				public static void doStartLingFeiCarService(int type, VoiceBean voice){
								Intent intent = new Intent(Constant.Action.ACTION_LF_CAR_SERVICE);
								if(null != intent){
//												intent.setPackage("com.lingfei.bmw.idfive");
												intent.putExtra("cmd_type", type);
												intent.putExtra("voice_bean", voice);
												LibApplication.getContext().startService(intent);
								}
				}

}
