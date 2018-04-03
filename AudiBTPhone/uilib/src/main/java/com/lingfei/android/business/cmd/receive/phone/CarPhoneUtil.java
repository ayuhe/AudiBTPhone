package com.lingfei.android.business.cmd.receive.phone;

import com.lingfei.android.business.cmd.receive.BaseReceive;

import java.util.ArrayList;

/**
	* Created by Administrator on 2016/9/27.
	*/
public class CarPhoneUtil extends BaseReceive{

				public static final int DATA_LIST_SIZE = 2;//有效数据个数 list size
				public static final int POS_DATA_START = 5;//中间有效数据 开始位 从地址固定头开始

				public static final int POS_DATA_END = POS_DATA_START + DATA_LIST_SIZE - 1;//中间有效数据 结束位 从0开始
				//=================================见协议文件============================
				public static final int LEGHT = 4;//MCU发送长度不定,不用判断长度 ,arm发送需要判断长度4
				public static final int CMD_TOTAL_LENGHT = LEGHT + 3;//总长度 协议数组总长度
				public static final int CMD_LENGHT_MIN = 8;//最少长度
				public static final int CMD_TYPE = 0x80;//
				public static final int DATA_TYPE = 0x0E;
				public static final int DATA_TYPE_CONTROL_CAR_BT = 0x0F;

				public static class Mode{
								public static final int STATUS = 0x00;//不弹窗 纯蓝牙状态
								public static final int WARNING = 0x01;//弹窗
				}

				public static class DataPOS{  //信息在list中位置
								public static final int MODE = 0;
								public static final int POS_BT_STATUS = 1;
								public static final int POS_NAME_OR_NUMBER = 2;
				}

				public static class BTStatus{
								public static final int CALL_OUT = 0x01;
								public static final int HANGUP = 0x02;
								public static final int TALKING = 0x03;
								public static final int IN_COMING = 0x04;
				}
				//==================================================

				public static ArrayList<Integer> getIntegerDataListFromMCU(String[] datas){
								if(checkCmdIsTrue(datas, SEND_FROM_MCU)){
												ArrayList<Integer> dataList;
												dataList = createIntegerDataList(datas);
												return dataList;
								}
								return null;
				}

				public static ArrayList<String> getStringDataListFromMCU(String[] datas){
								if(checkCmdIsTrue(datas, SEND_FROM_MCU)){
												ArrayList<String> dataList;
												dataList = createStringDataList(datas);
												return dataList;
								}
								return null;
				}

				private static ArrayList<Integer> createIntegerDataList(String[] datas){
								if(checkDatasLength(datas)){
												ArrayList<Integer> dataList = new ArrayList<>();
												for(int i = POS_DATA_START; i <= datas.length - 2; i++){
																dataList.add(Integer.valueOf(datas[i]));
												}
												return dataList;
								}
								return null;
				}

				private static ArrayList<String> createStringDataList(String[] datas){
								printStringArrayLog("CarPhoneCmd Main : ", datas);
								if(checkDatasLength(datas)){
												ArrayList<String> dataList = new ArrayList<>();
												for(int i = POS_DATA_START; i <= datas.length - 2; i++){
																dataList.add(datas[i]);
												}
												return dataList;
								}
								return null;
				}
				//====================================================================

				private static boolean checkDatasLength(String[] datas){  //电话长度不定
								boolean isTure = false;
								if(datas != null && datas.length >= CMD_LENGHT_MIN){
												isTure = true;
								}
								return isTure;
				}

				private static boolean checkCmdIsTrue(String[] datas, int comeFrom){  //接收

								if(checkDatasLength(datas)){

												if(Integer.valueOf(datas[Pos.HEAD]) == SEND_HEAD &&
																				//			Integer.valueOf(datas[Pos.LENGHT]) == LEGHT &&
																				Integer.valueOf(datas[Pos.CMD_TYPE]) == CMD_TYPE &&
																				Integer.valueOf(datas[Pos.DATA_TYPE]) == DATA_TYPE &&
																				Integer.valueOf(datas[Pos.SEND_FROM]) == comeFrom){
																return true;
												}
												else if(comeFrom == SEND_FROM_ARM){
																if(Integer.valueOf(datas[Pos.HEAD]) == SEND_HEAD &&
																								Integer.valueOf(datas[Pos.LENGHT]) == LEGHT &&
																								Integer.valueOf(datas[Pos.CMD_TYPE]) == CMD_TYPE &&
																								Integer.valueOf(datas[Pos.DATA_TYPE]) == DATA_TYPE_CONTROL_CAR_BT &&
																								Integer.valueOf(datas[Pos.SEND_FROM]) == comeFrom){
																				return true;
																}
												}
								}
								return false;
				}

				private static boolean isRequest(String[] datas){//检查数据是否需要触发警告
							/*	for(int i = POS_DATA_START; i <= POS_DATA_END; i++){
												if(Integer.valueOf(datas[i]) > warningLevel){
																return true;
												}
								}*/
								return false;
				}

				public static boolean isNeedPopUp(int mode){//需要弹窗
								boolean isNeed = false;
								switch(mode){
												case Mode.STATUS:{

																break;
												}
												case Mode.WARNING:{
																isNeed = true;
																break;
												}
								}
								return isNeed;
				}

				//================================外部调用===========================================
				public static boolean checkDataListSize(ArrayList<Integer> dataList){
								if(dataList != null && dataList.size() > DataPOS.POS_BT_STATUS){
												return true;
								}
								return false;
				}

				public static boolean isRequestWarning(String[] datas, int comeFrom){ //是否需要警告

								return checkCmdIsTrue(datas, comeFrom) && isRequest(datas);
				}

				public static boolean isWarningInfo(String[] datas, int comeFrom){//是否属于该警告协议
								return checkCmdIsTrue(datas, comeFrom);
				}
}
