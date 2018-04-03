package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* 背光开关
	* Created by heyu on 2017/2/5.
	*/
public class BackLightCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x09;

				public final static int OPEN = 0x01; // 打开
				public final static int CLOSE = 0x00; // 关闭

				/**
					* 发送背光开关
					* @param value
					*/
				public static void sendBrightContrastCmd(int value){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[5] = (byte) value;
								arrayOfByte[6] = getCheckSum(arrayOfByte);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}

}
