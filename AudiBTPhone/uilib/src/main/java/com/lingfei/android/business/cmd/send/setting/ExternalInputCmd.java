package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;


/**
	* 外部输入
	*/
public class ExternalInputCmd extends BaseCmd{
				private final static int LENGHT = 4;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE = 0x12;

				public final static int INPUT_FF = 0xFF;//	 0xFF 无
				public final static int INPUT_00 = 0x00;//	 0x00 TV
				public final static int INPUT_01 = 0x01;//	 0x01 夜视仪
				public final static int INPUT_02 = 0x02;//	 0x02 记录仪

				/**
					* 发送外部输入协议
					* @param value
					*/
				public static void sendExternalInputCmd(int value){
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
