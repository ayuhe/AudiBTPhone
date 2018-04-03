package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* 同行者音量控制
	* Created by heyu on 2017/4/10.
	*/
public class VoiceCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x11;

				public final static int MUTE = 0x00; // 0 -- 静音
				public final static int MIN_VOLUME = 0x01; // 1 -- 最小音量
				public final static int INCREASE_VOLUME = 0x02; // 2 -- 增大音量
				public final static int DECREASE_VOLUME = 0x03; // 3 -- 减小音量
				public final static int MAX_VOLUME = 0x04; // 4 -- 最大音量
				public final static int UN_MUTE = 0x05; // 5 -- 取消静音

				/**
					* 发送音量控制
					* @param value
					*/
				public static void sendVoiceCmd(int value){
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
