package com.lingfei.android.bt;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.lingfei.android.bt.injector.Module.ApplicationModule;
import com.lingfei.android.bt.injector.component.ApplicationComponent;
import com.lingfei.android.bt.injector.component.DaggerApplicationComponent;
import com.lingfei.android.bt.ui.btphone.service.BTPhoneService;
import com.lingfei.android.uilib.LibApplication;

public class BaseApplication extends LibApplication{

				private ApplicationComponent mApplicationComponent;

				@Override
				public void onCreate(){
								super.onCreate();
								initComponent();
								initBTService();
				}

				@Override
				protected void attachBaseContext(Context base){
								super.attachBaseContext(base);
								MultiDex.install(this);
				}

				private void initComponent(){
								mApplicationComponent =
																DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
								mApplicationComponent.inject(this);
				}

				public ApplicationComponent getApplicationComponent(){
								return mApplicationComponent;
				}

				public void initBTService(){
								Intent intent = new Intent(this, BTPhoneService.class);//启动蓝牙服务
								startService(intent);
				}
}
