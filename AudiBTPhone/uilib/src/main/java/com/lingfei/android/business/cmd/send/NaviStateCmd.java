package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.bean.guide.NaviState;
import com.lingfei.android.business.serial.SerioUtils;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* 转发导航状态指令
	* Created by heyu on 2016/8/24.
	*/
public class NaviStateCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x0B;

				/*		开始导航 8
								结束导航 9
								开始模拟导航 10
								暂停模拟导航 11
								停止模拟导航 12*/
				public final static int START_NAVI = 8;
				public final static int STOP_NAVI = 9;
				private final static int START_NAVI_SIMULATION = 10;
				private final static int STOP_NAVI_SIMULATION = 12;

				/*				开始TTS播报 13
								  停止TTS播报 14*/
				public final static int START_TTS_VOICE = 13;
				public final static int STOP_TTS_VOICE = 14;

				/**
					* 导航声音	0：无；1：有
					*/
				private final static int START_TTS_VOICE_CUSTOM = 1;
				private final static int STOP_TTS_VOICE_CUSTOM = 0;
				private final static int START_NAVI_CUSTOM = 2;
				private final static int STOP_NAVI_CUSTOM = 3;

				/**
					* 发送导航声音状态
					* length	4
					* CMD type	0x80
					* Data type	0x0B	Navi voice
					* Sendfrom	0x00
					* Data 0	导航声音	0：无；1：有
					*
					* @param state
					*/
				private static void sendNaviVoiceCmd(int state){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[5] = (byte) state;
								arrayOfByte[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByte);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}

				/**
					* 处理导航声音
					*
					* @param state
					*/
				public static void handleNaviVoiceState(int state){
								int voiceState = -1;
								switch(state){
												case START_TTS_VOICE:
																voiceState = START_TTS_VOICE_CUSTOM;
																break;
												case STOP_TTS_VOICE:
																voiceState = STOP_TTS_VOICE_CUSTOM;
																break;
												case START_NAVI:
												case START_NAVI_SIMULATION:
																// 开始导航
																voiceState = START_NAVI_CUSTOM;
																SysSharePres.getInstance().putBoolean(SysSharePres.HAD_GUIDE_INFO, true);
																EventPostUtils.Post(new NaviState(START_NAVI));
																break;
												case STOP_NAVI:
												case STOP_NAVI_SIMULATION:
																// 结束导航
																voiceState = STOP_NAVI_CUSTOM;
																SysSharePres.getInstance().putBoolean(SysSharePres.HAD_GUIDE_INFO, false);
																EventPostUtils.Post(new NaviState(STOP_NAVI));
																break;
												default:
																break;
								}

								if(-1 != voiceState){
												// 发送导航声音状态
												sendNaviVoiceCmd(voiceState);
								}
				}

}
