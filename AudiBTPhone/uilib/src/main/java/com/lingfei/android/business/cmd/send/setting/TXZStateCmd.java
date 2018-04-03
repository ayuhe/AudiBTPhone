package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* 同行者语音状态
	* Created by heyu on 2017/4/8.
	*/
public class TXZStateCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x10;

				public final static int START_VOICE = 0x01; // 开始语音
				public final static int END_VOICE = 0x02; // 结束语音
				public final static int CANCEL_VOICE = 0x03; // 3 -- 没有监听到说话，自动退出语音界面
				public final static int START_OTHER_VIEW = 0x04; // 4 -- 跳转到其他界面
				public final static int OPEN_APP = 0x05; // 5 -- 打开应用
				public final static int WAKE_UP_VOICE_VIEW = 0x06; // 6 -- 唤醒语音界面

				/**
					* 发送语音状态
					* @param value
					*/
				public static void sendTXZStateCmd(int value){
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
