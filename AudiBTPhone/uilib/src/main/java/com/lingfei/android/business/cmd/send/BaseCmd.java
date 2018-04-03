package com.lingfei.android.business.cmd.send;

/**
	* Created by heyu on 2016/8/20.
	*/
public class BaseCmd{
				public final static byte HEAD = (byte) 0xA0; // 协议头
				public final static byte SEND_FROM_ARM = (byte) 0x00; // 安卓发送
				public final static byte SEND_FROM_MCU = (byte) 0x01; // Mcu发送

				// 系统初始化指令
				public static byte[] init_system_cmd = {HEAD, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x05};

				public static class Pos{ //mcu协议规则
								public static final int HEAD = 0;
								public static final int LENGHT = 1; //
								public static final int CMD_TYPE = 2; //
								public static final int DATA_TYPE = 3; //
								public static final int SEND_FROM = 4; //
				}

				/**
					* 计算CheckSum的值
					*
					* @param cmd
					* @return
					*/
				protected static byte getCheckSum(byte[] cmd){
								byte checkSum = 0;
								if(null != cmd && cmd.length > 1){
												for(int i = 1; i < cmd.length - 1; i++){
																checkSum += cmd[i];
												}
								}

								return checkSum;
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
}
