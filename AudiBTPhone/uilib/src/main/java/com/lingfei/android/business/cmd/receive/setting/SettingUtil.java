package com.lingfei.android.business.cmd.receive.setting;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.business.cmd.send.setting.SettingCmd;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* 解析设置相关指令
	* Created by heyu on 2016/9/27.
	*/
public class SettingUtil extends BaseReceive{
				private final static int LENGHT = 5;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE00 = 0x00;
				private final static int DATA_TYPE01 = 0x01;
				private final static int DATA_TYPE02 = 0x02;
				private final static int DATA_TYPE03 = 0x03;

				/**
					* 判断是否为设置指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isSettingCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data_type = Integer.valueOf(datas[Pos.DATA_TYPE]);
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				CMD_TYPE == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				(DATA_TYPE00 == data_type || DATA_TYPE01 == data_type || DATA_TYPE02 == data_type || DATA_TYPE03 == data_type) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 解析设置的数据
					*
					* @param datas
					*/
				public static void parseSettingCmd(String[] datas){
								printStringArrayLog("Setting : ", datas);
								int dataType = Integer.valueOf(datas[3]);
								switch(dataType){
												case DATA_TYPE00:
																parseDataType00(datas);
																break;

												case DATA_TYPE01:
																parseDataType01(datas);
																break;

												case DATA_TYPE02:
																parseDataType02(datas);
																break;

												case DATA_TYPE03:
																parseDataType03(datas);
																break;
												default:
																break;

								}
				}

				private static void parseDataType00(String[] datas){
								int data0 = Integer.valueOf(datas[5]);
								int data1 = Integer.valueOf(datas[6]);
								SysSharePres.getInstance().putIntPref(SysSharePres.DISP_BRIGHTNESS, data0);
								SysSharePres.getInstance().putIntPref(SysSharePres.DISP_CONTRAST, data1);
				}

				private static void parseDataType01(String[] datas){
								int data0 = Integer.valueOf(datas[5]);
								//	0x00：触摸按键；0x01:音量调整；0x02:按键设置
								switch(data0){
												case 0x00:
																break;
												case 0x01:
																int valueVolume = Integer.valueOf(datas[6]);
																SysSharePres.getInstance().putIntPref(SysSharePres.NAV_VOLUME, valueVolume);
																break;
												case 0x02:{
																int data1 = Integer.valueOf(datas[6]);
																//	（导航：00表示原车导航，0x01表示加装导航）
																switch(data1){
																				case 0x00:
																								SysSharePres.getInstance().setOriginalCarNaviId(true);
																								break;
																				case 0x01:
																								SysSharePres.getInstance().setOriginalCarNaviId(false);
																								break;
																				default:
																								break;
																}
																break;
												}
												default:
																break;
								}
				}

				/**
					* //	0x01	车型选择
					* // 0x02	后视选择
					* // 0x07	空调设置
					* // 0x08	开机logo选择
					* // 0x09	低速雷达设置
					*
					* @param datas
					*/
				private static void parseDataType02(String[] datas){
								int data0 = Integer.valueOf(datas[5]);
								int data1 = Integer.valueOf(datas[6]);
								switch(data0){
												case 0x01:{
																//	0x01	车型选择
																parseCarType(data1);
																break;
												}
												case 0x02:{
																// 0x02	后视选择
																parseBehindViewSelected(data1);
																break;
												}
												case 0x07:{
																// 0x07	空调设置
																SysSharePres.getInstance().setAirSettingId(data1);
																break;
												}
												case 0x08:{
																// 0x08	开机logo选择
																int bootLogoId = 0;
																if(SettingCmd.BOOT_LOGO_ORIGINAL_CAR == data1){
																				bootLogoId = 1;
																}
																SysSharePres.getInstance().setBootLogoId(bootLogoId);
																break;
												}
												case 0x09:{
																// 低速雷达设置
																int lowRadarId = 0;
																if(SettingCmd.LOW_RADAR_CLOSE == data1){
																				lowRadarId = 1;
																}
																SysSharePres.getInstance().setLowRadarId(lowRadarId);
																break;
												}
												case 0x03:{
																// 分辨率选择
																SysSharePres.getInstance().setResolutionId(data1);
																break;
												}
												default:
																break;
								}
				}

				/**
					* 后视选择：0x00 原车后视
					* 0x01 原车后视+前视
					* 0x02 加装后视
					* 0x03 加装后视+前视
					* 0x04 360°全景
					* 0x05 无
					* 0x06 定制360
					*
					* @param data1
					*/
				private static void parseBehindViewSelected(int data1){
								int viewSelectedId = data1;
								SysSharePres.getInstance().setBehindViewSelectedId(viewSelectedId);
				}

				/**
					* @param data1
					*/
				private static void parseCarType(int data1){
								SysSharePres.getInstance().setCarTypeId(data1);
				}

				/**
					* 语言选择
					* 0x00：简体中文；0x01:繁体中文；0x02：英文
					*
					* @param datas
					*/
				private static void parseDataType03(String[] datas){
								int data0 = Integer.valueOf(datas[5]);
								SysSharePres.getInstance().setLanguageSelectedId(data0);
				}

}
