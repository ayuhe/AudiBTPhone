package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.serial.SerioUtils;

/**
	* 查询蓝牙音频状态
	* Created by heyu on 2016/9/29.
	*/
public class BTSourceCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x83;
				private final static int DATA_TYPE = 0x00;

				public final static int REQUEST_SEND = 0xFF; // 0xFF: 为查询蓝牙音频状态

				public static void sendRequestBTSourceStateCmd(){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[5] = (byte) REQUEST_SEND;
								arrayOfByte[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByte);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}
}
