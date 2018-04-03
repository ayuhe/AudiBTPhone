package com.lingfei.android.business.cmd.receive.time;

import android.os.SystemClock;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.util.DateUtil;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.SysSharePres;

import java.util.Calendar;

/**
	* 解析时间指令
	* Created by heyu on 2016/8/30.
	*/
public class TimeUtil extends BaseReceive{

				private static final int LENGHT = 0x0A; // 有效数据的长度
				private static final int TOTAL_LENGHT = 3 + LENGHT; // 数据的总长度

				/**
					* 判断是否为时间指令
					*
					* @param datas
					* @return 是返回true，反之
					*/
				public static boolean isTimeCmd(String[] datas){
								if(null != datas && TOTAL_LENGHT == datas.length){
												if(SEND_HEAD == Integer.valueOf(datas[Pos.HEAD]) &&
																				LENGHT == Integer.valueOf(datas[Pos.LENGHT]) &&
																				0x80 == Integer.valueOf(datas[Pos.CMD_TYPE]) &&
																				0x0E == Integer.valueOf(datas[Pos.DATA_TYPE]) &&
																				SEND_FROM_MCU == Integer.valueOf(datas[Pos.SEND_FROM])){
																return true;
												}
								}
								return false;
				}

				/**
					* 获取时间字符串
					* Data 0	年高字节
					* Data 1	年低字节
					* Data 2	月
					* Data 3	日
					* Data 4	时
					* Data 5	分
					* Data 6	秒
					*
					* @param datas
					* @return
					*/
				public static String getTimeString(String[] datas){
								printStringArrayLog("Time : ", datas);
								if(null != datas && TOTAL_LENGHT == datas.length){
												StringBuffer timeStrBuf = new StringBuffer();
												// 年
												int data5 = Integer.valueOf(datas[5]);
												int data6 = Integer.valueOf(datas[6]);
												byte years[] = new byte[4];
												years[0] = 0;
												years[1] = 0;
												years[2] = (byte) data5;
												years[3] = (byte) data6;
												int year = bytes2int(years);
												timeStrBuf.append(year);

												// 月
												int data7 = Integer.valueOf(datas[7]);
												timeStrBuf.append("-").append(StringUtil.zeroPad(data7, 2));
												// 日
												int data8 = Integer.valueOf(datas[8]);
												timeStrBuf.append("-").append(StringUtil.zeroPad(data8, 2));
												// 时
												int data9 = Integer.valueOf(datas[9]);
												timeStrBuf.append(" ").append(StringUtil.zeroPad(data9, 2));
												// 分
												int data10 = Integer.valueOf(datas[10]);
												timeStrBuf.append(":").append(StringUtil.zeroPad(data10, 2));
												// 秒
												int data11 = Integer.valueOf(datas[11]);
												timeStrBuf.append(":").append(StringUtil.zeroPad(data11, 2));

												// 修改系统时间
												onUpdateDateSet(year, data7, data8, data9, data10, data11);

												return timeStrBuf.toString(); // 得到MCU发来的时间
								}

								return DateUtil.getCurrentTime();// 返回系统时间
				}

				public static void setTimeString(String[] datas){
								if(isTimeCmd(datas)){
												String time = getTimeString(datas);
												if(StringUtil.isNotEmpty(time)){
																SysSharePres.getInstance().putString(SysSharePres.ORIGINAL_CAR_TIME, time);
												}
								}
				}

				/**
					* 修改系统时间：需要系统签名来实现，并在AndroidManifest.xml里设置：android:sharedUserId="android.uid.system"
					* @param year
					* @param month
					* @param day
					* @param hour
					* @param minute
					* @param second
					*/
				public static void onUpdateDateSet(int year, int month, int day, int hour, int minute, int second){
								Calendar c = Calendar.getInstance();
								c.set(Calendar.YEAR, year);
								c.set(Calendar.MONTH, month-1);
								c.set(Calendar.DAY_OF_MONTH, day);
								c.set(Calendar.HOUR_OF_DAY, hour);
								c.set(Calendar.MINUTE, minute);
								c.set(Calendar.SECOND, second);

								long when = c.getTimeInMillis();
								if(when / 1000 < Integer.MAX_VALUE){
												SystemClock.setCurrentTimeMillis(when);
								}
				}

}
