package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.serial.SerioUtils;

/**
	* 导航板状态的相关指令
	* Created by heyu on 2017/2/13.
	*/
public class GPSStateCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x0A;
				private final static int SEND_FROM = 0x00;

				public final static int HOME = 0; // 主界面；
				public final static int MEDIA = 1; // 多媒体界面
				public final static int VIDEO = 2; // 视频播放器
				public final static int MUSIC = 3; // 音频播放器
				public final static int NAVI = 4; // 地图
				public final static int DVD = 5; // dvd
				public final static int TV = 6; // tv
				public final static int PHONE = 7; // 电话
				public final static int INTENET = 8; // 互联网
				public final static int BLUETOOTH_MUSIC = 9; // 蓝牙音乐
				public final static int APP = 10; // 应用程序
				public final static int SETTING = 11; // 设置
				public final static int AUX = 12; // 外部输入


				/**
					* 0：主界面；
					1：多媒体界面
					2：视频播放器
					3：音频播放器
					4：地图
					5：dvd
					6：tv
					7：电话
					8：互联网
					9：蓝牙音乐
					10：应用程序
					11：设置
					*/
				public static void sendGPSStateCmd(int data){
								byte[] arrayOfByte = new byte[TOTAL_LENGHT];
								arrayOfByte[0] = HEAD;
								arrayOfByte[1] = (byte) LENGHT;
								arrayOfByte[2] = (byte) CMD_TYPE;
								arrayOfByte[3] = (byte) DATA_TYPE;
								arrayOfByte[4] = (byte) SEND_FROM;
								arrayOfByte[5] = (byte) data;
								arrayOfByte[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByte);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
				}
}
