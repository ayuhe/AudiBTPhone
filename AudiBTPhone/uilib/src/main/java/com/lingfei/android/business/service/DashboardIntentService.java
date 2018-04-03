package com.lingfei.android.business.service;

import android.app.IntentService;
import android.content.Intent;

import com.lingfei.android.business.bean.VoiceBean;
import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.business.cmd.receive.dashboard.DashboardDB;
import com.lingfei.android.business.cmd.receive.dashboard.DashboardInfo;
import com.lingfei.android.business.cmd.receive.dashboard.DashboardUtil;
import com.lingfei.android.business.cmd.receive.switchview.SwitchUtil;
import com.lingfei.android.business.receiver.MainMCUReceiver;

/**
	* 处理仪表盘数据的服务
	* Created by heyu on 2016/9/21.
	*/
public class DashboardIntentService extends IntentService{

				public DashboardIntentService(String name){
								super(name);
				}

				public DashboardIntentService(){
								super("DashboardIntentService");
				}

				@Override
				protected void onHandleIntent(Intent intent){
								if(null != intent){
												String[] datas = intent.getStringArrayExtra(MainMCUReceiver.EXTRA_DATAS);
												if(DashboardUtil.isDashboardCmd(datas)){
																DashboardInfo info = DashboardUtil.getDashboardInfo(datas);
																// 将仪表数据暂存本地
																DashboardDB.getInstance().setDashboardInfo(info);
																// 将仪表数据转发给界面显示
																//																EventPostUtils.Post(info);
																if (info.isWarningOil()){ // 油量发出警告
																				VoiceBean bean = new VoiceBean();
																				bean.setContent("主人，当前油量预警，是否找加油站，确定规划路线去加油");
																				SwitchUtil.doStartLingFeiCarService(Constant.Voice.OIL_BROADCAST, bean);
																}
												}
								}
				}
}
