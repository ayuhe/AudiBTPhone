package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.serial.SerioUtils;

/**
	* 蓝牙电话相关指令
	* 蓝牙来电去电挂断
	* Created by heyu on 2016/9/14.
	*/
public class BTPhoneCmd extends BaseCmd{

				public static byte[] bt_phone_cmd; // 蓝牙电话
				public static int IN_COMING = 0;
				public static int CALL_OUT = 1;
				public static int HANG_UP = 2;

				//				length	4
				//				CMD type	0x80
				//				Data type	0x0D
				//				Sendfrom	0x00
				//				Data 0	蓝牙	0：蓝牙来电；1：蓝牙去电；2：蓝牙挂断电话
				//
				//				checksum		见约定部分

				/**
					* 发送蓝牙来电去电挂断相关指令
					*
					* @param data
					*/
				public static void sendBTPhoneCmd(int data){
								byte[] arrayOfByteBTPhone = new byte[7];
								arrayOfByteBTPhone[0] = HEAD;
								arrayOfByteBTPhone[1] = (byte) 4;
								arrayOfByteBTPhone[2] = (byte) 0x80;
								arrayOfByteBTPhone[3] = 0x0D;
								arrayOfByteBTPhone[5] = (byte) data;
								arrayOfByteBTPhone[6] = getCheckSum(arrayOfByteBTPhone);
								bt_phone_cmd = arrayOfByteBTPhone;

								SerioUtils.getInstance().send_command_to_serio(bt_phone_cmd);
				}
}
