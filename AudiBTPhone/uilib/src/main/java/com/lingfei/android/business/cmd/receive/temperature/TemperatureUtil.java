package com.lingfei.android.business.cmd.receive.temperature;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.StringUtil;

/**
	* Created by heyu on 2016/9/29.
	*/
public class TemperatureUtil extends BaseReceive{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x81;
				private final static int DATA_TYPE = 0x09;

				/**
					* 判断是否为温度指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isTemperatureCmd(String[] datas){
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
					* 获取温度字符串
					*
					* @param datas
					* @return
					*/
				public static String getTemperatureString(String[] datas){
								printStringArrayLog("Temperature : ", datas);
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data5 = Integer.valueOf(datas[5]);
												//	D2:0x00= -40℃，0x50= 0℃，0x73= +17.5℃，0x80= +24.0℃，0xA0= +40℃，0xB4= +50℃，每±0.5℃=±0x01
												float temperature = 0f;
												if(data5 >= 0 && data5 <= 180){
																temperature = 0.5f * data5 - 40;
												}

												return StringUtil.formatOneDot(String.valueOf(temperature)) + " ℃"; // 返回温度的String
								}

								return " ";
				}

}
