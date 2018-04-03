package com.lingfei.android.business.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lingfei.android.business.cmd.receive.BaseReceive;
import com.lingfei.android.business.cmd.receive.air.AirUtil;
import com.lingfei.android.business.cmd.receive.dashboard.DashboardUtil;
import com.lingfei.android.business.cmd.receive.door.DoorUtil;
import com.lingfei.android.business.service.AirIntentService;
import com.lingfei.android.business.service.DashboardIntentService;
import com.lingfei.android.business.service.MainIntentService;
import com.lingfei.android.business.service.WarningIntentService;
import com.lingfei.android.uilib.Config;
import com.lingfei.android.uilib.util.DateUtil;
import com.lingfei.android.uilib.util.FileUtil;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* MCU的广播接收器
	* 需要在具体应用的AndroidManifest.xml里进行声明注册
	*/
public class MainMCUReceiver extends BroadcastReceiver{
				private static final String MCU_ACTION = "com.android.mcudata";
				public static final String EXTRA_DATA = "mcudata";
				public static final String EXTRA_DATAS = "mcudatas";

				@Override
				public void onReceive(Context context, Intent intent){
								// 接收广播

								String action = intent.getAction();
								if(MCU_ACTION.equals(action)){
												String data = intent.getStringExtra(EXTRA_DATA);
												if(Config.IS_DEBUG){
																// 将mcu的发来的数据保存到本地
																FileUtil.saveFile(DateUtil.getCurrent(DateUtil.FORMAT_YYYYMMDDHHMMSS) + " : " + data + "\n", FileUtil.MCU_DATA_FILE);
												}

												// 将String转成数组
												String datas[] = BaseReceive.getDataArray(data);
												Intent intentService = null;
												if(null != datas){
																if(DashboardUtil.isDashboardCmd(datas)){ // 仪表数据
																				boolean isStartDashboard = SysSharePres.getInstance().getBoolean(SysSharePres.START_DASHBOARD_ACTIVITY);
																				if(isStartDashboard){
																								intentService = new Intent(context, DashboardIntentService.class);
																				}
																				else{
																								return;
																				}
																}
																else if(AirUtil.isAirCmd(datas)){ // 空调数据
																				intentService = new Intent(context, AirIntentService.class);
																}
																else if(DoorUtil.isDoorInfo(datas)){ // 车门信息
																				intentService = new Intent(context, WarningIntentService.class);
																}
/*																else if(CarPhoneUtil.isWarningInfo(datas, CarPhoneUtil.SEND_FROM_MCU)){ // 来电、去电
//																				intentService = new Intent(context, CarPhoneWarningIntentService.class); // 弹出警告框
																				intentService = new Intent(context, CarPhonePushIntentService.class); // 不弹警告框
																}*/
																else{
																				intentService = new Intent(context, MainIntentService.class);
																}
												}

												if(null != intentService){
																intentService.putExtra(EXTRA_DATAS, datas);
																context.startService(intentService);
												}
								}
				}
}
