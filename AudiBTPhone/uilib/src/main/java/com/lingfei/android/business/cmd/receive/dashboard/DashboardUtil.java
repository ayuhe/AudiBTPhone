package com.lingfei.android.business.cmd.receive.dashboard;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* 解析仪表盘信息
	* Created by heyu on 2016/9/18.
	*/
public class DashboardUtil extends BaseReceive{

				private static final int LENGHT = 8; // 有效数据的长度
				private static final int TOTAL_LENGHT = 3 + LENGHT; // 数据的总长度

				private final static int WARNING_OIL_VALUE = 30; // 油量低于该值时发出警告
				private final static int WARNING_WATER_TEMPERATURE = 105; // 水温高于该值时发出警告

				/**
					* 判断是否为仪表盘信息
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isDashboardCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				LENGHT == Integer.valueOf(datas[Pos.LENGHT]) &&
																				0x81 == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				0x08 == Integer.valueOf(datas[Pos.DATA_TYPE]) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 获取表盘信息
					* 0x00:data 1为当前时速；
					* 0x01:data 1为安全带状态
					* 0x02:data 1为手刹状态
					* 0x03:data 1为当前油量
					* 0x04:data 1为油耗
					* 0x05:data 1为里程高位  data2为里程低位，可根据情况再定data3
					* 0x06:data 1为可达里程高位  data2为可达里程低位，可根据情况再定data3
					* 0x07:data 1为发动机转速高位  data2为发动机转速低位，可根据情况再定data3
					* 0x08:data 1为当前档位
					* 0x09:data 1为水箱温度
					*
					* @param datas
					* @return
					*/
				public static DashboardInfo getDashboardInfo(String[] datas){
								printStringArrayLog("Dashboard : ", datas);
								// 拿到当前的仪表数据
								DashboardInfo info = DashboardDB.getInstance().getDashboardInfo();
								if(null != datas && TOTAL_LENGHT == datas.length){
												if(null == info){
																info = new DashboardInfo();
												}
												final int data0 = Integer.parseInt(datas[5]);
												final int data1 = Integer.parseInt(datas[6]);
												final int data2 = Integer.parseInt(datas[7]);

												byte values[] = new byte[4];
												values[0] = 0;
												values[1] = 0;
												values[2] = (byte) data1;
												values[3] = (byte) data2;
												int value = bytes2int(values);

												switch(data0){
																case 0x00:
																				info.setDataType(DashboardDataType.CURRENT_SPEED);
																				info.setCurrentSpeed(value);
																				break;
																case 0x01:
																				info.setDataType(DashboardDataType.SAFETY_BELT);
																				// 安全带D1高4位是左，低4位是右 ，1代表带，0代表无带
																				boolean isWarningSafetyBelt = isWarningSafetyBelt(data1, data2);
																				SysSharePres.getInstance().putBoolean("is_warning_safety_belt", isWarningSafetyBelt);
																				info.setWarningSafetyBelt(isWarningSafetyBelt);
																				break;
																case 0x02:
																				info.setDataType(DashboardDataType.HANDBRAKE);
																				int iHandbrake = data1;
																				boolean isWarningHandbrake = iHandbrake != 0 ? true : false;
																				info.setWarningHandbrake(isWarningHandbrake);
																				break;
																case 0x03:
																				info.setDataType(DashboardDataType.OIL_MASS);
																				int oil = data1;
																				int isWarningOilValue = data2;
																				boolean isWarningOil = 1==isWarningOilValue ? true : false;
																				info.setOilMass(oil);
																				info.setWarningOil(isWarningOil);
																				break;
																case 0x04:
																				info.setDataType(DashboardDataType.OIL_WEAR);
																				info.setOilWear(data1);
																				break;
																case 0x05:
																				info.setDataType(DashboardDataType.MILEAGE);
																				int data3 = Integer.parseInt(datas[8]);
																				int data4 = Integer.parseInt(datas[9]);
																				values[0] = (byte) data1;
																				values[1] = (byte) data2;
																				values[2] = (byte) data3;
																				values[3] = (byte) data4;
																				float mileageValue = 0.1f * bytes2int(values);
																				info.setMileage(mileageValue);
																				break;
																case 0x06:
																				info.setDataType(DashboardDataType.TRAVEL_MILEAGE);
																				info.setTravelMileage(value);
																				break;
																case 0x07:
																				info.setDataType(DashboardDataType.ENGINE_REVOLUTION);
																				float revolution = (float) value / 1000f;
																				info.setEngineRevolution(revolution);
																				break;
																case 0x08:
																				info.setDataType(DashboardDataType.GEAR);
																				info.setGear(data1);
																				break;
																case 0x09:
																				info.setDataType(DashboardDataType.WATER_TEMPERATURE);
																				int waterTemp = data1;
																				boolean isWarningWaterTemp = waterTemp > WARNING_WATER_TEMPERATURE ? true : false;
																				info.setWaterTemp(waterTemp);
																				info.setWarningWaterTemp(isWarningWaterTemp);
																				break;
												}

												return info;
								}

								return info;
				}

				/**
					* 解析安全带的数据
					*
					* @param data1 是否已经戴上安全带：安全带D1高4位是左，低4位是右 ，1代表戴，0代表无戴
					* @param data2 是否有副驾驶
					* @return 是否警告没戴上安全带
					*/
				private static boolean isWarningSafetyBelt(int data1, int data2){
								byte[] safetyValues = intToByteArray(data1);

								int safetyMain = getHeight4(safetyValues[0]); // 左(主驾驶)
								int safetyCopilot = getLow4(safetyValues[0]); // 右(副驾驶)
								boolean isHasCopilot = false; // 是否有副驾驶
								if(data2 > 0){
												// 有副驾驶
												isHasCopilot = true;
								}

								boolean isWarningMain = false;// 是否主驾没带安全带，默认她戴了
								boolean isWarningCopilot = false; // 是否副驾驶没带安全带, 默认她戴了
								if(0 == safetyMain){
												// 主驾驶没戴
												isWarningMain = true;
								}
								if(isHasCopilot){ // 有副驾驶
												if(0 == safetyCopilot){
																// 副驾驶没戴
																isWarningCopilot = true;
												}
								}

								// 只要有一人没戴就警告
								return isWarningMain || isWarningCopilot;
				}
}
