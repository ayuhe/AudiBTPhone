package com.lingfei.android.business.cmd.receive.setting;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.business.cmd.send.setting.BackLightCmd;
import com.lingfei.android.business.event.BackLightEvent;
import com.lingfei.android.uilib.util.EventPostUtils;

/**
	* 解析背光开关指令
	* Created by heyu on 2017/2/5.
	*/
public class BackLightUtil extends BaseReceive{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x09;

				/**
					* 判断是否为背光开关指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isBackLightCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data_type = Integer.valueOf(datas[Pos.DATA_TYPE]);
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				CMD_TYPE == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				DATA_TYPE == data_type &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 解析背光开关的数据
					*
					* @param datas
					*/
				public static void parseBackLightCmd(String[] datas){
								printStringArrayLog("BackLight : ", datas);
								int value = Integer.valueOf(datas[5]);
								switch(value){
												case BackLightCmd.OPEN:
																EventPostUtils.Post(new BackLightEvent(true));
																break;
												case BackLightCmd.CLOSE:
																break;
												default:
																break;

								}
				}

}
