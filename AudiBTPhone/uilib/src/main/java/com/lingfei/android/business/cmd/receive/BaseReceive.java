package com.lingfei.android.business.cmd.receive;

import com.lingfei.android.uilib.Config;
import com.lingfei.android.uilib.util.DateUtil;
import com.lingfei.android.uilib.util.FileUtil;
import com.lingfei.android.uilib.util.StringUtil;

/**
	* Mcu信息处理工具类
	* Created by heyu on 2016/8/6.
	*/
public class BaseReceive{
				public final static int SEND_HEAD = 0xA0; // 协议头
				public final static int SEND_FROM_MCU = 0x01; // Mcu发送
				public final static int SEND_FROM_ARM = 0x00; // ARM发送

				public static class Pos{ //mcu协议规则
								public static final int HEAD = 0;
								public static final int LENGHT = 1; //
								public static final int CMD_TYPE = 2; //
								public static final int DATA_TYPE = 3; //
								public static final int SEND_FROM = 4; //
				}

				// 获取高四位
				public static int getHeight4(byte data){
								int height;
								height = ((data & 0xf0) >> 4);
								return height;
				}

				// 获取低四位
				public static int getLow4(byte data){
								int low;
								low = (data & 0x0f);
								return low;
				}

				/**
					* 将byte转换为一个长度为8的byte数组，数组每个值代表bit
					*/
				public static byte[] byte2BitArray(byte b){
								byte[] array = new byte[8];
								for(int i = 7; i >= 0; i--){
												array[i] = (byte) (b & 1);
												b = (byte) (b >> 1);
								}
								return array;
				}

				// 高位在前，低位在后
				public static int bytes2int(byte[] bytes){
								int result = 0;
								if(bytes.length == 4){
												int a = (bytes[0] & 0xff) << 24;
												int b = (bytes[1] & 0xff) << 16;
												int c = (bytes[2] & 0xff) << 8;
												int d = (bytes[3] & 0xff);
												result = a | b | c | d;
								}
								return result;
				}

				/**
					* int类型数据转成Byte数组
					*
					* @param res int的数值
					* @return
					*/
				public static byte[] intToByteArray(int res){
								byte[] targets = new byte[4];
								targets[0] = (byte) (res & 0xff);// 最低位
								targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
								targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
								targets[3] = (byte) ((res >> 24) & 0xff);// 最高位,无符号右移。
								return targets;
				}

				/**
					* 转换成字符串数组
					*
					* @param data
					* @return
					*/
				public static String[] getDataArray(String data){
								if(StringUtil.isNotEmpty(data)){
												String[] datas = data.split(",");
												return datas;
								}

								return null;
				}

				public static void printStringArrayLog(String tag, String[] datas){
								if(Config.IS_DEBUG && null != datas){
												StringBuffer strbuf = new StringBuffer();
												for(int i = 0; i < datas.length; i++){
																strbuf.append(Integer.valueOf(datas[i])).append(", ");
												}
												// 将mcu的发来的数据保存到本地
												FileUtil.saveFile(DateUtil.getCurrent(DateUtil.FORMAT_YYYYMMDDHHMMSS)
																				                  + " [MCU --> Android] : " + tag
																				                  + strbuf.toString() + "\n", FileUtil.MCU_DATA_FILE);
								}
				}

}
