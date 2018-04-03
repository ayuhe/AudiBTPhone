package com.lingfei.android.business.cmd.receive.door;

import com.lingfei.android.business.bean.door.DoorInfo;
import com.lingfei.android.business.cmd.receive.BaseReceive;

/**
	* 解析车门信息
	* Created by heyu on 2016/8/30.
	*/
public class DoorUtil extends BaseReceive{

				//==============================================================
				public static final int DATA_LIST_SIZE = 7;//有效数据list size
				public static final int POS_DATA_START = 5;//中间有效数据 开始位 从地址固定头开始
				public static final int POS_DATA_END = 11;//中间有效数据 结束位 从0开始
				//=================================见协议文件============================
				public static final int LEGHT = 10;//
				public static final int CMD_TOTAL_LENGHT = LEGHT + 3;//总长度 协议数组总长度
				public static final int CMD_TYPE = 0x81;//
				public static final int DATA_TYPE = 0x00;//

				public static class DataPOS{  //信息在list中位置
								public static final int POS_CAR_TYPE = 11;
				}
				//==============================================================

				/**
					* 判断是否为车门信息
					*
					* @param datas
					* @return
					*/
				public static boolean isDoorInfo(String datas[]){
								if(null != datas && datas.length == CMD_TOTAL_LENGHT){
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				LEGHT == Integer.valueOf(datas[Pos.LENGHT]) &&
																				CMD_TYPE == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				DATA_TYPE == Integer.valueOf(datas[Pos.DATA_TYPE]) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																// 是车门信息
																return true;
												}
								}

								return false;
				}

				/**
					* 判断是否有车门开启
					*
					* @param doorInfo
					* @return
					*/
				public static boolean isOpenDoors(DoorInfo doorInfo){
								if(null != doorInfo){
												if(doorInfo.isHeadOrTailOpen() || doorInfo.isDoorOpen()){
																// 有车门未关闭了
																return true;
												}
								}

								return false;
				}

				/**
					* 解析车门信息
					*
					* @param datas
					*/
				public static DoorInfo parseDoorInfo(String[] datas){
								DoorInfo doorInfo = new DoorInfo();
								for(int i = 5; i < datas.length - 1; i++){
												if(0 != Integer.valueOf(datas[i])){
																switch(i){
																				case 5:
																								// 左前门开
																								doorInfo.setOpenLeftBefore(true);
																								break;
																				case 6:
																								// 左后门开
																								doorInfo.setOpenLeftAfter(true);
																								break;
																				case 7:
																								// 右前门开
																								doorInfo.setOpenRightBefore(true);
																								break;
																				case 8:
																								// 右后门开
																								doorInfo.setOpenRightAfter(true);
																								break;
																				case 9:
																								// 前盖开
																								doorInfo.setOpenHead(true);
																								break;
																				case 10:
																								// 尾箱开
																								doorInfo.setOpenTail(true);
																								break;
																				case DataPOS.POS_CAR_TYPE:{
																								doorInfo.setCarType(DoorInfo.CarType.GLC);
																								break;
																				}
																				default:
																								break;
																}
												}
								}

								return doorInfo;
				}

				public static int getCarDoorType(String[] datas){// 获取车型
								int type = 0;
								if(isDoorInfo(datas)){
												type = Integer.valueOf(datas[DataPOS.POS_CAR_TYPE]);
								}
								return type;
				}
}
