package com.lingfei.android.business.serial;


import android.content.Context;
import android.hardware.SerialManager;
import android.hardware.SerialPort;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.Config;
import com.lingfei.android.uilib.util.DateUtil;
import com.lingfei.android.uilib.util.FileUtil;
import com.lingfei.android.uilib.util.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
	* 直接写串口的方式与MCU进行通信，适用605安卓导航板子
	*/
public class Serio2mcu implements SerioService{
				private Context mContext;
				private SerialManager mSerialManager;
				private final static String path = "/dev/ttyS2";
				private static final String SERIAL_SERVICE = "serial";
				private ByteBuffer mOutputBuffer;
				private SerialPort mSerialPort = null;
				public File ttyS2 = new File(path);

				public Serio2mcu(Context context){
								mContext = context;
								mOutputBuffer = ByteBuffer.allocate(1024);
								mSerialManager = (SerialManager) mContext.getSystemService(SERIAL_SERVICE);
				}

				@Override
				public synchronized boolean openserio(){
								if(ttyS2.exists()){
												try{
																if(mSerialManager != null){
																				mSerialPort = mSerialManager.openSerialPort(path, Config.BAUD_RATE);
																}
																else{
																				mSerialManager = (SerialManager) mContext.getSystemService(SERIAL_SERVICE);
																				mSerialPort = mSerialManager.openSerialPort(path, Config.BAUD_RATE);
																}
												}
												catch(IOException e){
																LoggerUtil.e("openserio e = " + e.toString());
																return false;
												}
								}
								else{
												return false;
								}
								return true;
				}

				@Override
				public void armtomcu(byte[] data){
								if(mSerialPort != null){
												try{
																LoggerUtil.v("armtomcu data = " + data.toString());
																printByteArrayLog(data);
																mOutputBuffer.clear();
																mOutputBuffer.put(data);
																mSerialPort.write(mOutputBuffer, data.length);
												}
												catch(IOException e){
																LoggerUtil.e("arm to mcu e = " + e.toString());
												}
								}
				}

				@Override
				public void closeserio(){
								if(mSerialPort != null){
												try{
																mSerialPort.close();
																LoggerUtil.d("ttyS2 is closed");
												}
												catch(IOException e){

												}
												mSerialPort = null;
								}
				}

				@Override
				public void printByteArrayLog(byte[] datas){
								if(Config.IS_DEBUG){
												StringBuffer strbuf = new StringBuffer();
												for(int i = 0; i < datas.length; i++){
																byte[] bytes = new byte[4];
																bytes[3] = datas[i];
																int data = BaseReceive.bytes2int(bytes);
																strbuf.append(data).append(", ");
												}
												// 将mcu的发来的数据保存到本地
												FileUtil.saveFile(DateUtil.getCurrent(DateUtil.FORMAT_YYYYMMDDHHMMSS)
																				                  + " [Android --> MCU] : "
																				                  + strbuf.toString() + "\n", FileUtil.MCU_DATA_FILE);

												LoggerUtil.w(DateUtil.getCurrent(DateUtil.FORMAT_YYYYMMDDHHMMSS)
																				             + " [Android --> MCU] : "
																				             + strbuf.toString() + "\n");
								}
				}
}
