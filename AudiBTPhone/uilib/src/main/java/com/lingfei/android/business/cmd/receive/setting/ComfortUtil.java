package com.lingfei.android.business.cmd.receive.setting;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* 解析舒适功能指令
	* Created by heyu on 2017/2/5.
	*/
public class ComfortUtil extends BaseReceive{
				private final static int LENGHT = 8;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x02;

				/**
					* 判断是否为舒适功能指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isComfortCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data_type = Integer.valueOf(datas[Pos.DATA_TYPE]);
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				CMD_TYPE == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				DATA_TYPE == data_type &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM]) &&
																				0x00 == Integer.valueOf(datas[5])){
																return true;
												}
								}
								return false;
				}

				/**
					* 解析舒适功能的数据
					*
					* @param datas
					*/
				public static void parseComfortCmd(String[] datas){
								printStringArrayLog("Comfort : ", datas);
								int value1 = Integer.valueOf(datas[6]);
								int value2 = Integer.valueOf(datas[7]);
								int value3 = Integer.valueOf(datas[8]);
								int value4 = Integer.valueOf(datas[9]);

								SysSharePres.getInstance().putIntPref(SysSharePres.SENIOR_COMFORT_F1, value1);
								SysSharePres.getInstance().putIntPref(SysSharePres.SENIOR_COMFORT_F2, value2);
								SysSharePres.getInstance().putIntPref(SysSharePres.SENIOR_COMFORT_F3, value3);
								SysSharePres.getInstance().putIntPref(SysSharePres.SENIOR_COMFORT_F4, value4);
				}

}
