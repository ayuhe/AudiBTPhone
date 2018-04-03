package com.lingfei.android.business.cmd.receive;

import android.content.Intent;

import com.lingfei.android.business.cmd.receive.switchview.SwitchUtil;
import com.lingfei.android.business.event.MediaUIFinishEvent;
import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.util.EventPostUtils;

/**
	* 系统切源指令
	* Created by heyu on 2017/2/27.
	*/
public class SystemSwitchUtil extends BaseReceive{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x0C;

				public final static int ORIGINAL_CAR = 0x00; // 原车
				public final static int ANDROID = 0x01; // 安卓

				/**
					* 判断是否为系统切源指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isSystemSwitchCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				LENGHT == Integer.valueOf(datas[Pos.LENGHT]) &&
																				CMD_TYPE == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				DATA_TYPE == Integer.valueOf(datas[Pos.DATA_TYPE]) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 解析系统切源指令
					*
					* @param datas
					* @return
					*/
				public static void parseSystemSwitchCmd(String[] datas){
								printStringArrayLog("SystemSwitch : ", datas);
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data5 = Integer.valueOf(datas[5]);
												switch(data5){
																case ORIGINAL_CAR:
																				EventPostUtils.Post(new MediaUIFinishEvent());
																				// 暂停同听的播放
																				SwitchUtil.doStartLingFeiCarService(Constant.Voice.PAUSE_TXT_MUSIC, null);
																				// 返回home界面
																				Intent intent = new Intent(Intent.ACTION_MAIN);
																				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																				intent.addCategory(Intent.CATEGORY_HOME);
																				LibApplication.getContext().startActivity(intent);
																				break;
																case ANDROID:
																				break;
																default:
																				break;
												}
								}
				}

}
