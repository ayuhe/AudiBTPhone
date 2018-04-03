package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.serial.SerioUtils;

/**
	* 查询仪表数据的指令
	* Created by heyu on 2016/9/22.
	*/
public class DashboardCmd extends BaseCmd{
				private final static int LENGHT = 8;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x81;
				private final static int DATA_TYPE = 0x08;
				private final static int SEND_FROM = 0x00;

				public final static int START_SEND_ALL = 0xFE; // 0xFE: 请求开始发送仪表所有数据（初始化）
				public final static int START_SEND = 0xFD; // 0xFD: 请求开始发送仪表数据（数据有变化就发）
				public final static int STOP_SEND = 0xFF; // 0xFF: 请求停止发送仪表数据

				/**
					* 请求仪表数据
					* length	待定
					* CMD type	0x81
					* Data type	0x08
					* Sendfrom	0x00 or 0x01
					*/
				public static void sendRequestDashboardCmd(int cmd){
								byte[] arrayOfByteBTPhone = new byte[TOTAL_LENGHT];
								arrayOfByteBTPhone[0] = HEAD;
								arrayOfByteBTPhone[1] = (byte) LENGHT;
								arrayOfByteBTPhone[2] = (byte) CMD_TYPE;
								arrayOfByteBTPhone[3] = (byte) DATA_TYPE;
								arrayOfByteBTPhone[4] = (byte) SEND_FROM;
								arrayOfByteBTPhone[5] = (byte) cmd;
								arrayOfByteBTPhone[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteBTPhone);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByteBTPhone);
				}

}
