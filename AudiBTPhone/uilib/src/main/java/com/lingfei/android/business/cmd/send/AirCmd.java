package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.serial.SerioUtils;

/**
	* 查询空调信息的相关指令
	* Created by heyu on 2016/9/26.
	*/
public class AirCmd extends BaseCmd{
				private final static int LENGHT = 8;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x81;
				private final static int DATA_TYPE = 0x02;
				private final static int SEND_FROM = 0x00;

				/**
					* 查询空调信息
					* length	8
					* CMD type	0x81
					* Data type	0x02
					* Sendfrom	0x00 or 0x01
					* Data 0	AC
					* Data 1	Wind mode
					* Data 2	Wind speed
					* Data 3	Left temp
					* Data 4	Right temp
					*/
				public static void sendRequestAirInfoCmd(){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[4] = (byte) SEND_FROM;
								arrayOfByte[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByte);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}
}
