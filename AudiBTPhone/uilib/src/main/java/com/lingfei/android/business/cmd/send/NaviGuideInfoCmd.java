package com.lingfei.android.business.cmd.send;

import com.lingfei.android.business.bean.guide.GuideInfo;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* 导航导向信息数据与MCU同步指令
	* Created by heyu on 2016/8/20.
	*/
public class NaviGuideInfoCmd extends BaseCmd{
				private final static int LENGHT = 12;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x81;
				private final static int DATA_TYPE = 0x03;

				private final static int BYTE_LENGTH = 4;

				/**
					* 发送导向信息
					* @param guideInfo
					*/
				public static void sendGuideInfoCmd(GuideInfo guideInfo){
								// 导向信息
								int icon = guideInfo.getIcon();
								// 路段剩余距离
								int segRemainDis = guideInfo.getSeg_remain_dis();
								// 路径剩余距离
								int routeRemainDis = guideInfo.getRoute_remain_dis();

								byte seg[] = intToByteArray(segRemainDis);
								byte route[] = intToByteArray(routeRemainDis);

								if(null != seg && BYTE_LENGTH == seg.length &&
																null != route && BYTE_LENGTH == route.length){
												byte[] arrayOfByte = new byte[TOTAL_LENGHT];
												arrayOfByte[0] = HEAD;
												arrayOfByte[1] = (byte) LENGHT;
												arrayOfByte[2] = (byte) CMD_TYPE;
												arrayOfByte[3] = (byte) DATA_TYPE;
												arrayOfByte[5] = (byte) icon;
												arrayOfByte[6] = seg[3];
												arrayOfByte[7] = seg[2];
												arrayOfByte[8] = seg[1];
												arrayOfByte[9] = seg[0];
												arrayOfByte[10] = route[3];
												arrayOfByte[11] = route[2];
												arrayOfByte[12] = route[1];
												arrayOfByte[13] = route[0];
												arrayOfByte[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByte);

												// 导向信息
												SerioUtils.getInstance().send_command_to_serio(arrayOfByte);
								}
				}
}
