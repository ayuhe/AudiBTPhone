package com.lingfei.android.business.cmd.send.setting;

import com.lingfei.android.business.cmd.send.BaseCmd;
import com.lingfei.android.business.serial.SerioUtils;

/**
	* 设置相关指令
	* Created by heyu on 2016/8/29.
	*/
public class SettingCmd extends BaseCmd{
				private final static int LENGHT = 5;
				private final static int TOTAL_LENGHT = LENGHT + 3;//总长度 协议数组总长度
				private final static int CMD_TYPE = 0x80;
				private final static int DATA_TYPE01 = 0x01;
				private final static int DATA_TYPE02 = 0x02;

				public final static int DATA0_ALL_APP_SWITCH = 0x0B; // 主菜单（全部应用）开关

				// 车型选择
				public final static int CAR_TYPE_00 = 0x00; // 宝马1系
				public final static int CAR_TYPE_01 = 0x01; // 宝马3系
				public final static int CAR_TYPE_02 = 0x02; // 宝马5系
				public final static int CAR_TYPE_03 = 0x03; // 宝马X1
				public final static int CAR_TYPE_04 = 0x04; // 宝马X3
				public final static int CAR_TYPE_05 = 0x05; // 宝马X5
				public final static int BENZ_XIN_C = 0x11; // 0x11 奔驰新C
				public final static int BENZ_GLC = 0x12; // 0x12 奔驰GLC

				//	后视选择
				public final static int VIEW_SELECTED_00 = 0x00;	// 0x00 原车后视
				public final static int VIEW_SELECTED_01 = 0x01;//	 0x01 原车后视+前视
				public final static int VIEW_SELECTED_02 = 0x02;//	 0x02 加装后视
				public final static int VIEW_SELECTED_03 = 0x03;//	 0x03 加装后视+前视
				public final static int VIEW_SELECTED_04 = 0x04;// 	0x04 360°全景
				public final static int VIEW_SELECTED_05 = 0x05;//	 0x05 无
				public final static int VIEW_SELECTED_06 = 0x06;//	 0x06 定制360

				// 输入选择
				public final static int INPUT_00 = 0x00;//	 0x00 800*480
				public final static int INPUT_01 = 0x01;//	 0x01 1280*480
				public final static int INPUT_02 = 0x02;//	 0x02 高配

				// 导航设置
				public final static int ORIGINAL_CAR_NAVI = 0x00; // 原车导航
				public final static int INSTALL_NAVI = 0x01; // 加装导航

				// 低速雷达
				public final static int LOW_RADAR_OPEN = 0x01; // 开
				public final static int LOW_RADAR_CLOSE = 0x00; // 关

				// 空调设置
				public final static int AIR_SINGLE_ZONE = 0x00; // 单区
				public final static int AIR_DOUBLE_ZONE = 0x01; // 双区

				// 开机logo
				public final static int BOOT_LOGO_ANDROID = 0x01; // 安卓开机logo
				public final static int BOOT_LOGO_ORIGINAL_CAR = 0x00; // 原车开机logo

				// 分辨率选择 0x00 低配(800x480)  0x01 高配(960x540)
				public final static int RESOLUTION_LOW = 0x00;
				public final static int RESOLUTION_HIGHT = 0x01;

				// 主菜单开关
				public final static int ALL_APP_CLOSE = 0x01; // 关闭
				public final static int ALL_APP_OPEN = 0x00; // 打开

				/**
					* 封装数据，并发给MCU
					*
					* @param dataType
					* @param data0
					* @param data1
					*/
				private static void sendCmdToMcu(int dataType, int data0, int data1){
								byte[] arrayOfByteCmd = new byte[TOTAL_LENGHT];
								arrayOfByteCmd[0] = HEAD;
								arrayOfByteCmd[1] = (byte) LENGHT;
								arrayOfByteCmd[2] = (byte) CMD_TYPE;
								arrayOfByteCmd[3] = (byte) dataType;
								arrayOfByteCmd[5] = (byte) data0;
								arrayOfByteCmd[6] = (byte) data1;
								arrayOfByteCmd[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteCmd);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCmd);
				}

				/**
					* 舒适功能
					* @param data1
					* @param data2
					* @param data3
					* @param data4
					*/
				public static void sendComfortCmd(int data1, int data2, int data3, int data4){
								byte[] arrayOfByteCarType = new byte[11];
								arrayOfByteCarType[0] = HEAD;
								arrayOfByteCarType[1] = (byte) 8;
								arrayOfByteCarType[2] = (byte) CMD_TYPE;
								arrayOfByteCarType[3] = (byte) DATA_TYPE02;
								arrayOfByteCarType[5] = 0x00;
								arrayOfByteCarType[6] = (byte) data1;
								arrayOfByteCarType[7] = (byte) data2;
								arrayOfByteCarType[8] = (byte) data3;
								arrayOfByteCarType[9] = (byte) data4;
								arrayOfByteCarType[10] = getCheckSum(arrayOfByteCarType);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCarType);
				}

				/**
					* 车型选择指令
					*
					* @param data
					*/
				public static void sendSetCarTypeCmd(int data){
								byte[] arrayOfByteCarType = new byte[TOTAL_LENGHT];
								arrayOfByteCarType[0] = HEAD;
								arrayOfByteCarType[1] = (byte) LENGHT;
								arrayOfByteCarType[2] = (byte) CMD_TYPE;
								arrayOfByteCarType[3] = (byte) DATA_TYPE02;
								arrayOfByteCarType[5] = 0x01;
								arrayOfByteCarType[6] = (byte) data;
								arrayOfByteCarType[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteCarType);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCarType);
				}

				/**
					* 设置后视选择指令
					*
					* @param data
					*/
				public static void sendSetViewSelectedCmd(int data){
								byte[] arrayOfByteViewSelected = new byte[TOTAL_LENGHT];
								arrayOfByteViewSelected[0] = HEAD;
								arrayOfByteViewSelected[1] = (byte) LENGHT;
								arrayOfByteViewSelected[2] = (byte) CMD_TYPE;
								arrayOfByteViewSelected[3] = (byte) DATA_TYPE02;
								arrayOfByteViewSelected[5] = 0x02;
								arrayOfByteViewSelected[6] = (byte) data;
								arrayOfByteViewSelected[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteViewSelected);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByteViewSelected);
				}

				/**
					* 输入选择
					* @param data
					*/
				public static void sendInputCmd(int data){
								byte[] arrayOfByteViewSelected = new byte[TOTAL_LENGHT];
								arrayOfByteViewSelected[0] = HEAD;
								arrayOfByteViewSelected[1] = (byte) LENGHT;
								arrayOfByteViewSelected[2] = (byte) CMD_TYPE;
								arrayOfByteViewSelected[3] = (byte) DATA_TYPE02;
								arrayOfByteViewSelected[5] = 0x03;
								arrayOfByteViewSelected[6] = (byte) data;
								arrayOfByteViewSelected[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteViewSelected);

								SerioUtils.getInstance().send_command_to_serio(arrayOfByteViewSelected);
				}

				/**
					* 设置导航
					* length	5
					* CMD type	0x80
					* Data type	0x01
					* Sendfrom	0x00
					* Data 0	类型	0x00：触摸按键；0x01:音量调整；0x02:按键设置
					* Data 1	参数	（导航：00表示原车导航，0x01表示加装导航）（data0==0x02时有效）
					*
					* @param data
					*/
				public static void sendNaviCmd(int data){
								byte[] arrayOfByteNavi = new byte[TOTAL_LENGHT];
								arrayOfByteNavi[0] = HEAD;
								arrayOfByteNavi[1] = (byte) LENGHT;
								arrayOfByteNavi[2] = (byte) CMD_TYPE;
								arrayOfByteNavi[3] = (byte) DATA_TYPE01;
								arrayOfByteNavi[5] = 0x02;
								arrayOfByteNavi[6] = (byte) data;
								arrayOfByteNavi[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteNavi);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteNavi);
				}

				/**
					* 音量设置
					* @param data
					*/
				public static void sendVolumeCmd(int data){
								byte[] arrayOfByteNavi = new byte[TOTAL_LENGHT];
								arrayOfByteNavi[0] = HEAD;
								arrayOfByteNavi[1] = (byte) LENGHT;
								arrayOfByteNavi[2] = (byte) CMD_TYPE;
								arrayOfByteNavi[3] = (byte) DATA_TYPE01;
								arrayOfByteNavi[5] = 0x01;
								arrayOfByteNavi[6] = (byte) data;
								arrayOfByteNavi[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteNavi);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteNavi);
				}

				/**
					* 低速雷达设置
					* length	5
					* CMD type	0x80
					* Data type	0x02
					* Sendfrom	0x00 or 0x01
					* Data 0	0x09	低速雷达设置
					* Data 1	2种状态：0x00 关			0x01 开
					* checksum		见约定部分
					*
					* @param data
					*/
				public static void sendLowRadarCmd(int data){
								byte[] arrayOfByteCmd = new byte[TOTAL_LENGHT];
								arrayOfByteCmd[0] = HEAD;
								arrayOfByteCmd[1] = (byte) LENGHT;
								arrayOfByteCmd[2] = (byte) CMD_TYPE;
								arrayOfByteCmd[3] = (byte) DATA_TYPE02;
								arrayOfByteCmd[5] = 0x09;
								arrayOfByteCmd[6] = (byte) data;
								arrayOfByteCmd[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteCmd);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCmd);
				}

				/**
					* 空调设置
					* length	5
					* CMD type	0x80
					* Data type	0x02
					* Sendfrom	0x00 or 0x01
					* Data 0	0x07	单区，双区空调选择
					* Data 1	选择：0x00 单区空调			0x01 双区空调
					* checksum		见约定部分
					*
					* @param data
					*/
				public static void sendAirSettingCmd(int data){
								byte[] arrayOfByteCmd = new byte[TOTAL_LENGHT];
								arrayOfByteCmd[0] = HEAD;
								arrayOfByteCmd[1] = (byte) LENGHT;
								arrayOfByteCmd[2] = (byte) CMD_TYPE;
								arrayOfByteCmd[3] = (byte) DATA_TYPE02;
								arrayOfByteCmd[5] = 0x07;
								arrayOfByteCmd[6] = (byte) data;
								arrayOfByteCmd[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteCmd);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCmd);
				}

				/**
					* 开机Logo设置
					* length	5
					* CMD type	0x80
					* Data type	0x02
					* Sendfrom	0x00 or 0x01
					* Data 0	0x08	开机logo选择
					* Data 1	选择：0x00 安卓logo		0x01 原车logo
					* checksum		见约定部分
					*
					* @param data
					*/
				public static void sendBootLogoCmd(int data){
								byte[] arrayOfByteCmd = new byte[TOTAL_LENGHT];
								arrayOfByteCmd[0] = HEAD;
								arrayOfByteCmd[1] = (byte) LENGHT;
								arrayOfByteCmd[2] = (byte) CMD_TYPE;
								arrayOfByteCmd[3] = (byte) DATA_TYPE02;
								arrayOfByteCmd[5] = 0x08;
								arrayOfByteCmd[6] = (byte) data;
								arrayOfByteCmd[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteCmd);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCmd);
				}

				/**
					*  分辨率选择
					*  0x80
					*  0x02
					*  0x0A：为分辨率选择
					*  0x00 低配(800x480)  0x01 高配(960x540)
					* @param data
					*/
				public static void sendResolutionCmd(int data){
								byte[] arrayOfByteCmd = new byte[TOTAL_LENGHT];
								arrayOfByteCmd[0] = HEAD;
								arrayOfByteCmd[1] = (byte) LENGHT;
								arrayOfByteCmd[2] = (byte) CMD_TYPE;
								arrayOfByteCmd[3] = (byte) DATA_TYPE02;
								arrayOfByteCmd[5] = 0x0A;
								arrayOfByteCmd[6] = (byte) data;
								arrayOfByteCmd[TOTAL_LENGHT - 1] = getCheckSum(arrayOfByteCmd);

								// 发送指令给MCU
								SerioUtils.getInstance().send_command_to_serio(arrayOfByteCmd);
				}

				/**
					* 主菜单开关
					*
					* @param data
					*/
				public static void sendAllAppSwitchCmd(int data){
								sendCmdToMcu(DATA_TYPE02, DATA0_ALL_APP_SWITCH, data);
				}

}
