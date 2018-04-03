package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.cmd.receive.phone.CarPhoneUtil;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* Created by Administrator on 2016/9/27.
	*/
public class CarPhoneControlCmd extends BaseCmd{  //控制原车蓝牙

				public static byte[] searchCmd; //

				/**
					* 低速雷达查询mcu 请求重发最新数据
					*/
				public static class ControlAction{
								public static final int ANSWER = 0x01;
								public static final int HANGUP = 0x00;
				}

				public static void sendCmd(int action){
								byte[] array = new byte[CarPhoneUtil.CMD_TOTAL_LENGHT];
								array[Pos.HEAD] = HEAD;
								array[Pos.LENGHT] = (byte) CarPhoneUtil.LEGHT;
								array[Pos.CMD_TYPE] = (byte) CarPhoneUtil.CMD_TYPE;
								array[Pos.DATA_TYPE] = (byte) CarPhoneUtil.DATA_TYPE_CONTROL_CAR_BT;
								array[Pos.SEND_FROM] = SEND_FROM_ARM;
								array[5] = (byte) action;
								array[CarPhoneUtil.CMD_TOTAL_LENGHT - 1] = getCheckSum(array);
								searchCmd = array;

								SerioUtils.getInstance().send_command_to_serio(searchCmd);
				}
}
