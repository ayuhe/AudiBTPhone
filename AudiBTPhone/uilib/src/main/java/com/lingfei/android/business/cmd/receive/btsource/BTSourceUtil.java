package com.lingfei.android.business.cmd.receive.btsource;

import com.lingfei.android.business.cmd.receive.BaseReceive;

/**
	* 蓝牙连接状态
	* Created by heyu on 2016/12/02.
	*/
public class BTSourceUtil extends BaseReceive{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x83;
				private final static int DATA_TYPE = 0x00;

				/**
					* 判断是否为蓝牙连接状态指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isBTSourceCmd(String[] datas){
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
					* 获取状态
					*
					* @param datas
					* @return
					*/
				public static int getBTSourceState(String[] datas){
								printStringArrayLog("BTSourceState : ", datas);
								int state = 0;
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data5 = Integer.valueOf(datas[5]);
												state = data5;
								}

								return state;
				}

}
