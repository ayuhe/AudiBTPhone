package com.lingfei.android.business.cmd.send;

/**
	* 切源指令
	* Created by heyu on 2016/8/4.
	*/
public class MenuCmd extends BaseCmd{
				private final static int LENGHT = 5;
				private final static int TOTAL_LENGHT = LENGHT + 3;// 总长度 协议数组总长度
				private final static int LAST_POSITION = TOTAL_LENGHT - 1;// 最后一位
				private final static int CMD_TYPE = 0x82;

				// 媒体相关
				public static byte[] media_cd; // 光盘
				public static byte[] media_video; // 视频
				public static byte[] media_tv; // 电视
				public static byte[] media_music; // 音乐
				public static byte[] media_interface; // 多媒体接口1
				public static byte[] media_ui; // 媒体界面
				public static byte[] media_bt; // 蓝牙音频
				public static byte[] media_phone_link; // 手机互联
				public static byte[] media_radio; // 收音机
				public static byte[] memu_navi; // 导航
				public static byte[] memu_car; // 原车
				public static byte[] menu_phone; // 电话
				public static byte[] menu_media; // 安卓媒体
				public static byte[] menu_start_voice; // 启动语音
				public static byte[] menu_aux; // 外部输入

				public static byte[] main_menu; // 主菜单界面

				static{
								byte[] arrayOfByteMediaCD = new byte[TOTAL_LENGHT];
								arrayOfByteMediaCD[0] = HEAD;
								arrayOfByteMediaCD[1] = (byte) LENGHT;
								arrayOfByteMediaCD[2] = (byte) CMD_TYPE;
								arrayOfByteMediaCD[5] = (byte) 0x5;
								arrayOfByteMediaCD[6] = (byte) 0x02;
								arrayOfByteMediaCD[LAST_POSITION] = getCheckSum(arrayOfByteMediaCD);
								media_cd = arrayOfByteMediaCD;

								byte[] arrayOfByteMediaVideo = new byte[TOTAL_LENGHT];
								arrayOfByteMediaVideo[0] = HEAD;
								arrayOfByteMediaVideo[1] = (byte) LENGHT;
								arrayOfByteMediaVideo[2] = (byte) CMD_TYPE;
								arrayOfByteMediaVideo[5] = (byte) 0x08;
								arrayOfByteMediaVideo[6] = (byte) 0x06;
								arrayOfByteMediaVideo[LAST_POSITION] = getCheckSum(arrayOfByteMediaVideo);
								media_video = arrayOfByteMediaVideo;

								byte[] arrayOfByteMediaTV = new byte[TOTAL_LENGHT];
								arrayOfByteMediaTV[0] = HEAD;
								arrayOfByteMediaTV[1] = (byte) LENGHT;
								arrayOfByteMediaTV[2] = (byte) CMD_TYPE;
								arrayOfByteMediaTV[5] = (byte) 0x06;
								arrayOfByteMediaTV[6] = (byte) 0x06;
								arrayOfByteMediaTV[LAST_POSITION] = getCheckSum(arrayOfByteMediaTV);
								media_tv = arrayOfByteMediaTV;

								byte[] arrayOfByteMediaMusic = new byte[TOTAL_LENGHT];
								arrayOfByteMediaMusic[0] = HEAD;
								arrayOfByteMediaMusic[1] = (byte) LENGHT;
								arrayOfByteMediaMusic[2] = (byte) CMD_TYPE;
								arrayOfByteMediaMusic[5] = (byte) 0x0E;
								arrayOfByteMediaMusic[6] = (byte) 0x06;
								arrayOfByteMediaMusic[LAST_POSITION] = getCheckSum(arrayOfByteMediaMusic);
								media_music = arrayOfByteMediaMusic;

								byte[] arrayOfByteMediaInterface = new byte[TOTAL_LENGHT];
								arrayOfByteMediaInterface[0] = HEAD;
								arrayOfByteMediaInterface[1] = (byte) LENGHT;
								arrayOfByteMediaInterface[2] = (byte) CMD_TYPE;
								arrayOfByteMediaInterface[5] = (byte) 0x05;
								arrayOfByteMediaInterface[6] = (byte) 0x08;
								arrayOfByteMediaInterface[LAST_POSITION] = getCheckSum(arrayOfByteMediaInterface);
								media_interface = arrayOfByteMediaInterface;

								byte[] arrayOfByteMediaUI = new byte[TOTAL_LENGHT];
								arrayOfByteMediaUI[0] = HEAD;
								arrayOfByteMediaUI[1] = (byte) LENGHT;
								arrayOfByteMediaUI[2] = (byte) CMD_TYPE;
								arrayOfByteMediaUI[5] = (byte) 0x05;
								arrayOfByteMediaUI[6] = (byte) 0x0B;
								arrayOfByteMediaUI[LAST_POSITION] = getCheckSum(arrayOfByteMediaUI);
								media_ui = arrayOfByteMediaUI;

								byte[] arrayOfByteMediaBT = new byte[TOTAL_LENGHT];
								arrayOfByteMediaBT[0] = HEAD;
								arrayOfByteMediaBT[1] = (byte) LENGHT;
								arrayOfByteMediaBT[2] = (byte) CMD_TYPE;
								arrayOfByteMediaBT[5] = (byte) 0x05;
								arrayOfByteMediaBT[6] = (byte) 0x0D;
								arrayOfByteMediaBT[LAST_POSITION] = getCheckSum(arrayOfByteMediaBT);
								media_bt = arrayOfByteMediaBT;

								byte[] arrayOfByteMediaPhoneLink = new byte[TOTAL_LENGHT];
								arrayOfByteMediaPhoneLink[0] = HEAD;
								arrayOfByteMediaPhoneLink[1] = (byte) LENGHT;
								arrayOfByteMediaPhoneLink[2] = (byte) CMD_TYPE;
								arrayOfByteMediaPhoneLink[5] = (byte) 0x10;
								arrayOfByteMediaPhoneLink[6] = (byte) 0x06;
								arrayOfByteMediaPhoneLink[LAST_POSITION] = getCheckSum(arrayOfByteMediaPhoneLink);
								media_phone_link = arrayOfByteMediaPhoneLink;

								byte[] arrayOfByteMediaRadio = new byte[TOTAL_LENGHT];
								arrayOfByteMediaRadio[0] = HEAD;
								arrayOfByteMediaRadio[1] = (byte) LENGHT;
								arrayOfByteMediaRadio[2] = (byte) CMD_TYPE;
								arrayOfByteMediaRadio[5] = (byte) 0x05;
								arrayOfByteMediaRadio[6] = (byte) 0x01;
								arrayOfByteMediaRadio[LAST_POSITION] = getCheckSum(arrayOfByteMediaRadio);
								media_radio = arrayOfByteMediaRadio;

								byte[] arrayOfByteNavi = new byte[TOTAL_LENGHT];
								arrayOfByteNavi[0] = HEAD;
								arrayOfByteNavi[1] = (byte) LENGHT;
								arrayOfByteNavi[2] = (byte) CMD_TYPE;
								arrayOfByteNavi[5] = (byte) 0x04;
								arrayOfByteNavi[6] = (byte) 0x09;
								arrayOfByteNavi[LAST_POSITION] = getCheckSum(arrayOfByteNavi);
								memu_navi = arrayOfByteNavi;

								byte[] arrayOfByteCar = new byte[TOTAL_LENGHT];
								arrayOfByteCar[0] = HEAD;
								arrayOfByteCar[1] = (byte) LENGHT;
								arrayOfByteCar[2] = (byte) CMD_TYPE;
								arrayOfByteCar[5] = (byte) 0x05;
								arrayOfByteCar[6] = (byte) 0x07;
								arrayOfByteCar[LAST_POSITION] = getCheckSum(arrayOfByteCar);
								memu_car = arrayOfByteCar;

								byte[] arrayOfBytePhone = new byte[TOTAL_LENGHT];
								arrayOfBytePhone[0] = HEAD;
								arrayOfBytePhone[1] = (byte) LENGHT;
								arrayOfBytePhone[2] = (byte) CMD_TYPE;
								arrayOfBytePhone[5] = (byte) 0x05;
								arrayOfBytePhone[6] = (byte) 0x0C;
								arrayOfBytePhone[LAST_POSITION] = getCheckSum(arrayOfBytePhone);
								menu_phone = arrayOfBytePhone;

								byte[] arrayOfByteMedia = new byte[TOTAL_LENGHT];
								arrayOfByteMedia[0] = HEAD;
								arrayOfByteMedia[1] = (byte) LENGHT;
								arrayOfByteMedia[2] = (byte) CMD_TYPE;
								arrayOfByteMedia[5] = (byte) 0x13;
								arrayOfByteMedia[6] = (byte) 0x06;
								arrayOfByteMedia[LAST_POSITION] = getCheckSum(arrayOfByteMedia);
								menu_media = arrayOfByteMedia;

								byte[] arrayOfByteMainMenu = new byte[TOTAL_LENGHT];
								arrayOfByteMainMenu[0] = HEAD;
								arrayOfByteMainMenu[1] = (byte) LENGHT;
								arrayOfByteMainMenu[2] = (byte) CMD_TYPE;
								arrayOfByteMainMenu[5] = (byte) 0x11;
								arrayOfByteMainMenu[6] = (byte) 0x05;
								arrayOfByteMainMenu[LAST_POSITION] = getCheckSum(arrayOfByteMainMenu);
								main_menu = arrayOfByteMainMenu;

								byte[] arrayOfByteStartVoice = new byte[TOTAL_LENGHT];
								arrayOfByteStartVoice[0] = HEAD;
								arrayOfByteStartVoice[1] = (byte) LENGHT;
								arrayOfByteStartVoice[2] = (byte) CMD_TYPE;
								arrayOfByteStartVoice[5] = (byte) 0x14;
								arrayOfByteStartVoice[6] = (byte) 0x06;
								arrayOfByteStartVoice[LAST_POSITION] = getCheckSum(arrayOfByteStartVoice);
								menu_start_voice = arrayOfByteStartVoice;

								byte[] arrayOfByteAux = new byte[7];
								arrayOfByteAux[0] = HEAD;
								arrayOfByteAux[1] = (byte) 4;
								arrayOfByteAux[2] = (byte) 0x80;
								arrayOfByteAux[3] = (byte) 0x0A;
								arrayOfByteAux[5] = (byte) 12;
								arrayOfByteAux[6] = getCheckSum(arrayOfByteAux);
								menu_aux = arrayOfByteAux;
				}
}
