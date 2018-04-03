package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* Created by heyu on 2016/9/23.
	*/
public class LanguageCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x03;

				public final static int LANGUAGE_ZHCN = 0x00; // 中文简体
				public final static int LANGUAGE_ZHTW = 0x01; // 中文繁体
				public final static int LANGUAGE_ES = 0x02; // 英语

				/**
					* 语言选择
					* length	4
					* CMD type	0x80
					* Data type	0x03
					* Sendfrom	0x00
					* Data 0	语言类型	0x00：简体中文；0x01:繁体中文；0x02：英文
					*
					* @param data
					*/
				public static void sendSetLanguageCmd(int data){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[5] = (byte) data;
								arrayOfByte[6] = getCheckSum(arrayOfByte);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}

				/**
					* 查询语言设置
					*/
				public static void sendRequestLanguageCmd(){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}

}
