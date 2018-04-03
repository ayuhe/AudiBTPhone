package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* 亮度、对比度
	* Created by heyu on 2017/2/5.
	*/
public class BrightContrastCmd extends BaseCmd{
				private final static int LENGHT = 5;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x00;

				/**
					* 亮度、对比度
					* @param bright
					* @param contrast
					*/
				public static void sendBrightContrastCmd(int bright, int contrast){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[5] = (byte) bright;
								arrayOfByte[6] = (byte) contrast;
								arrayOfByte[7] = getCheckSum(arrayOfByte);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}

}
