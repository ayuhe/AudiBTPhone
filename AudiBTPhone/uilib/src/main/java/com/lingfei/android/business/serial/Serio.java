package com.lingfei.android.business.serial;

import android.content.Context;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.uilib.Config;
import com.lingfei.android.uilib.util.DateUtil;
import com.lingfei.android.uilib.util.FileUtil;
import com.lingfei.android.uilib.util.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
	* 直接写文件的方式与MCU进行通信，适用607安卓导航板子
	*/
public class Serio implements SerioService{
				public String portPath = "/dev/ttyS1";
				public File portFile = new File(portPath);
				public final File writenode = new File("/sys/class/serio/writemcu");

				Context mContext;
				RandomAccessFile raf;

				public Serio(Context context){
								mContext = context;
				}

				@Override
				public synchronized boolean openserio(){
								if(portFile.exists() && writenode.exists()){
												try{
																raf = new RandomAccessFile(writenode, "rw");
												}
												catch(IOException e){
																LoggerUtil.e("openserio error =  " + e.toString());
																return false;
												}
								}
								else{
												LoggerUtil.e("openserio portFile not exist or writenode not exist ");
												return false;
								}

								return true;
				}

				@Override
				public synchronized void armtomcu(byte[] data){
								if(raf != null){
												try{
																raf.write(data);
																printByteArrayLog(data);
												}
												catch(IOException e){
																LoggerUtil.e("armtomcu error =  " + e.toString());
												}
								}
				}

				@Override
				public synchronized void closeserio(){
								if(raf != null){
												try{
																raf.close();
												}
												catch(IOException e){
																LoggerUtil.e("closeserio error =  " + e.toString());
												}
												raf = null;
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
