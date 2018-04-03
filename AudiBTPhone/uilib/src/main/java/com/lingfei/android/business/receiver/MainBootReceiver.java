package com.lingfei.android.business.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.Log;

import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.uilib.util.LoggerUtil;

/**
	* 广播接收器
	* 需要在具体应用的AndroidManifest.xml里进行声明注册
	*/
public class MainBootReceiver extends BroadcastReceiver{

				private static final String TAG = "MainBootReceiver";

				private Context mContext;

				public MainBootReceiver(){
								Log.i(TAG, "MainBootReceiver");
				}

				@Override
				public void onReceive(Context context, Intent intent){
								// 接收广播
								LoggerUtil.i("MainBootReceiver  onReceive action = " + intent.getAction());
								mContext = context;

								String action = intent.getAction();
								switch(action){
												case Intent.ACTION_BOOT_COMPLETED: // 开机
												case Constant.Action.ACTION_RESET_BT_PHONE_SERVICE:
																// 重新启动监听服务
																startBTPhoneservice(context, "");
																break;

												case Constant.Action.ACTION_BT_MUSIC:
																String status = SystemProperties.get(Constant.BT_CONNECTED_STATUS, "false");
																if(status != null && status.equals("true")){
																				// 暂停蓝牙音乐
																				String command = intent.getStringExtra("command");
																				startBTPhoneservice(context, command);
																}
																break;
												default:
																break;

								}
				}

				private void startBTPhoneservice(Context context, String cmd){
								Intent intent = new Intent(Constant.Action.ACTION_BT_PHONE_SERVICE);
								intent.setPackage("com.lingfei.bt");
								intent.putExtra("command", cmd);
								context.startService(intent);
				}
}
