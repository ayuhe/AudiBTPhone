package com.lingfei.android.business.cmd.receive.mcuversion;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* Created by heyu on 2016/9/20.
	*/
public class McuVersionUtil extends BaseReceive{

				private static final int LENGHT = 0x13 + 8 + 1; // 有效数据的长度
				private static final int TOTAL_LENGHT = 3 + LENGHT; // 数据的总长度

				public static boolean isMcuVersionCmd(String[] datas){
								if(null != datas){
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				0x00 == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				0x04 == Integer.valueOf(datas[Pos.DATA_TYPE]) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 获取MCU的版本名称
					*
					* @param datas
					* @return
					*/
				public static String getMcuVersion(String[] datas){
								if(isMcuVersionCmd(datas)){
												printStringArrayLog("McuVersion : ", datas);
												// 得到Mcu版本信息
												StringBuffer versionStrBuf = new StringBuffer();
												for(int i = 0; i < datas.length - 6; i++){
																// 把ascii码转换成字符
																versionStrBuf.append((char) Integer.parseInt(datas[5 + i]));
												}

												String mcuVersion = versionStrBuf.toString();
												if(StringUtil.isNotEmpty(mcuVersion)){
																// 本地保存版本号
																SysSharePres.getInstance().setMcuVersion(mcuVersion);
												}

												return mcuVersion;
								}
								return "";
				}
}
