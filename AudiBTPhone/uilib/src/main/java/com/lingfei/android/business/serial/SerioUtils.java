package com.lingfei.android.business.serial;


import android.content.Context;

import com.lingfei.android.uilib.Config;
import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.util.LoggerUtil;

public class SerioUtils {
				// 安卓导航板为607
				private final static int BOARD = 607;
				private static SerioUtils instance;
				private Context mContext;
				private SerioService mySerio2mcu;

				/**
					* 获取当前对象
					*
					* @return
					*/
				public static SerioUtils getInstance() {
								if (null == instance) {
												instance = new SerioUtils(LibApplication.getContext());
								}
								return instance;
				}

				public SerioUtils(Context context) {
								mContext = context;
								createserioobj();
				}

				public void send_command_to_serio(byte[] data) {
								if (null != data){
												try{
																if(mySerio2mcu.openserio()){
																				//																ToastUtils.showToast("openserio success");
																				mySerio2mcu.armtomcu(data);
																				mySerio2mcu.closeserio();
																}
																else{
																				mySerio2mcu.closeserio();
																}
												}
												catch(Exception e){
																LoggerUtil.e("send_command_to_serio  e = " + e.toString());
																//												ToastUtils.showToast(e.toString());
												}
								}
				}

				public void closeserio() {
								try {
												if (mySerio2mcu != null) {
																mySerio2mcu.closeserio();
												}
								} catch (Exception e) {

								}
				}

				private void createserioobj() {
								if(BOARD == Config.ANROID_BOARD){
												mySerio2mcu = new Serio(mContext);
								}else{
												mySerio2mcu = new Serio2mcu(mContext);
								}
				}
}
