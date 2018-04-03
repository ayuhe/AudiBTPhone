package com.lingfei.android.business.cmd.receive.setting;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* 外部输入指令
	*/
public class ExternalInputUtil extends BaseReceive{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x12;


				/**
					* 判断是否为外部输入指令
					* @param datas
					* @return
					*/
				public static boolean isExternalInputCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												int data_type = Integer.valueOf(datas[Pos.DATA_TYPE]);
												LoggerUtil.e("zll data_type-- "+ data_type);
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
					* 外部输入
					* @param datas
					*/
				public static void parseExternalInput(String[] datas){
								printStringArrayLog("ExternalInput : ", datas);
								int data0 = Integer.valueOf(datas[5]);
								LoggerUtil.e("zll MCU 外部输入 " + data0);
								SysSharePres.getInstance().setAuxSetId(data0);
				}

}
