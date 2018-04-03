package com.lingfei.android.business.cmd.receive.air;

import com.lingfei.android.business.bean.air.AirInfo;
import com.lingfei.android.business.cmd.receive.BaseReceive;

import java.util.ArrayList;

/**
	* Created by Administrator on 2016/8/19.
	*/
public class AirUtil extends BaseReceive{
				private static int DATA_LIST_SIZE = 7;//实际使用有效数据长度
				private static int POS_DATA_START = 5;//中间有效数据 开始位 从0开始
				private static int POS_DATA_END = 11;//中间有效数据 结束位 从0开始

				private static int AIR_LEGHT = 10;//
				private static int AIR_TOTAL_LEGHT = AIR_LEGHT + 3;// 总长度 协议数组总长度
				private static int AIR_CMD_TYPE = 0x81;//
				private static int AIR_DATA_TYPE = 0x02;//

				public static class AirDataPOS{  //空调信息在list中位置
								public static final int POS_AC = 0;
								public static final int POS_WIND_MODE = 1; //
								public static final int POS_WIND_LEVEL = 2; //
								public static final int POS_LEFT_TEMP = 3; //
								public static final int POS_RIGHT_TEMP = 4; //
								public static final int POS_MODE_AUTO = 5; //
								public static final int POS_SPEED_AUTO = 6; //
				}

				//==================================================

				public static ArrayList<Integer> getDataListFromMCU(String[] datas){
								if(checkCmdIsTrue(datas, SEND_FROM_MCU)){
												return createDataList(datas);
								}
								return null;
				}

				private static boolean checkCmdIsTrue(String[] datas, int comeFrom){  //接收

								if(datas != null && datas.length == AIR_TOTAL_LEGHT){

												if(Integer.valueOf(datas[Pos.HEAD]) == SEND_HEAD &&
																				Integer.valueOf(datas[Pos.LENGHT]) == AIR_LEGHT &&
																				Integer.valueOf(datas[Pos.CMD_TYPE]) == AIR_CMD_TYPE &&
																				Integer.valueOf(datas[Pos.DATA_TYPE]) == AIR_DATA_TYPE &&
																				Integer.valueOf(datas[Pos.SEND_FROM]) == comeFrom){
																return true;
												}
								}
								return false;
				}

				private static ArrayList<Integer> createDataList(String[] datas){
								if(datas != null && datas.length == AIR_TOTAL_LEGHT){
												printStringArrayLog("Air : ", datas);
												ArrayList<Integer> dataList = new ArrayList<>();
												for(int i = POS_DATA_START; i <= POS_DATA_END; i++){
																dataList.add(Integer.valueOf(datas[i]));
												}
												return dataList;
								}
								return null;
				}

				public static AirInfo getAirInfo(ArrayList<Integer> dataList){
								AirInfo airInfo = AirDB.getInstance().getAirInfo();
								if(dataList != null && AirUtil.checkDataListSize(dataList)){
												if(null == airInfo){
																airInfo = new AirInfo();
												}
												airInfo.setACswitch(dataList.get(AirUtil.AirDataPOS.POS_AC));
												airInfo.setWindMode(dataList.get(AirUtil.AirDataPOS.POS_WIND_MODE));
												airInfo.setWindLevel(dataList.get(AirUtil.AirDataPOS.POS_WIND_LEVEL));
												airInfo.setTempLeft(dataList.get(AirUtil.AirDataPOS.POS_LEFT_TEMP));
												airInfo.setTempRight(dataList.get(AirUtil.AirDataPOS.POS_RIGHT_TEMP));
												airInfo.setIsWindModeAuto(dataList.get(AirDataPOS.POS_MODE_AUTO));
												airInfo.setIsWindSpeedAuto(dataList.get(AirDataPOS.POS_SPEED_AUTO));

												AirDB.getInstance().setAirInfo(airInfo);
								}
								return airInfo;
				}

				public static boolean checkDataListSize(ArrayList<Integer> dataList){
								if(dataList != null && dataList.size() == DATA_LIST_SIZE){
												return true;
								}
								return false;
				}

				public static boolean isAirCmd(String[] datas){
								return checkCmdIsTrue(datas, SEND_FROM_MCU);
				}
}
