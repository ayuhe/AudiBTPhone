package com.lingfei.android.bt.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingfei.android.uilib.util.Constant;
import com.lingfei.android.bt.BaseApplication;
import com.lingfei.android.bt.R;
import com.lingfei.android.bt.injector.Module.ActivityModule;
import com.lingfei.android.bt.injector.component.ApplicationComponent;
import com.lingfei.android.bt.ui.btphone.callback.GocsdkCallback;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.uilib.AppManager;
import com.lingfei.android.uilib.util.DateUtil;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.SystemManage;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;


/**
	* Activity的父类
	*/
public abstract class BaseSupportActivity extends SupportActivity{
				protected FrameLayout parentFrameLayout;//把父类activity和子类activity的view都add到这里
				protected LinearLayout ll_toolbar;
				protected ImageButton ib_home;
				protected TextView tv_time;
				protected ImageView iv_taiking;
				protected ImageView iv_bt;
				protected ImageView iv_wifi;

				private WifiManager wifiManager;
				private BaseReceiver baseReceiver;

				private ArrayList<Integer> wifiDrawableList; // 保存wifi信号的图标

				private final static int MSG_UPDATE_TIME = 1; // 刷新日期消息
				private final static int DELAY_TIME = 1000 * 2; // 刷新日期的频率

				private Handler mTimeHandler = new Handler(){
								@Override
								public void handleMessage(Message msg){
												switch(msg.what){
																case MSG_UPDATE_TIME:
																				removeMessages(MSG_UPDATE_TIME);
																				String time = DateUtil.getCurrentTimeHHmm();
																				if(null != tv_time){
																								String current = tv_time.getText().toString();
																								if(!time.equals(current)){
																												tv_time.setText(time);
																								}
																								sendEmptyMessageDelayed(MSG_UPDATE_TIME, DELAY_TIME);
																				}
																				break;

																default:
																				break;
												}
								}
				};

				@Override
				protected void onCreate(Bundle savedInstanceState){
								getApplicationComponent().inject(this);
								super.onCreate(savedInstanceState);
								/* set it to be full screen */
								getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
								                     WindowManager.LayoutParams.FLAG_FULLSCREEN);

								//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置activity为竖屏显示
								setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 设置activity为横屏显示
								initContentView(R.layout.base_activity);
								setContentView(initContentView());
								initTopToolBar();
								initTime();
								initWifiDrawableList();
								checkStatusWifi();
								initBTState();
								initBTStateTaiking();
								registerReceiver();
								initInjector();
								initUiAndListener(savedInstanceState);
								AppManager.getAppManager().addActivity(this);
				}

				protected ApplicationComponent getApplicationComponent(){
								return ((BaseApplication) getApplication()).getApplicationComponent();
				}

				protected ActivityModule getActivityModule(){
								return new ActivityModule(this);
				}

				/**
					* 初始化contentview
					*/
				private void initContentView(int layoutResID){
								ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
								viewGroup.removeAllViews();
								parentFrameLayout = new FrameLayout(this);
								viewGroup.addView(parentFrameLayout);
								LayoutInflater.from(this).inflate(layoutResID, parentFrameLayout, true);
				}

				@Override
				public void setContentView(int layoutResID){
								LayoutInflater.from(this).inflate(layoutResID, parentFrameLayout, true);
				}

				@Override
				public void setContentView(View view){
								parentFrameLayout.addView(view);
				}

				@Override
				public void setContentView(View view, ViewGroup.LayoutParams params){
								parentFrameLayout.addView(view, params);
				}

				private void initTopToolBar(){
								ll_toolbar = (LinearLayout) findViewById(R.id.ll_toolbar);
								ib_home = (ImageButton) findViewById(R.id.ib_home);
								tv_time = (TextView) findViewById(R.id.tv_time);
								iv_taiking = (ImageView) findViewById(R.id.iv_taiking);
								iv_bt = (ImageView) findViewById(R.id.iv_bt);
								iv_wifi = (ImageView) findViewById(R.id.iv_wifi);
								ib_home.setFocusable(false);
								ib_home.setFocusableInTouchMode(false);
								ib_home.setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View v){
																SystemManage.goHome();
												}
								});
				}

				private void initTime(){
								String time = DateUtil.getCurrentTimeHHmm();
								tv_time.setText(time);
								mTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, DELAY_TIME);
				}

				private void initWifiDrawableList(){
								wifiDrawableList = new ArrayList<>();
								wifiDrawableList.add(R.drawable.ic_wifi_lv0);
								wifiDrawableList.add(R.drawable.ic_wifi_lv1);
								wifiDrawableList.add(R.drawable.ic_wifi_lv2);
								wifiDrawableList.add(R.drawable.ic_wifi_lv3);
								wifiDrawableList.add(R.drawable.ic_wifi_lv4);
				}

				private void initBTState(){
								int state = BTControler.getInstance().isBTHfpConnected() ? 1 : 0;
								updateBTState(state);
				}

				private void initBTStateTaiking(){
								int state = 0;
								int hfpStatus = BTControler.getInstance().getHfpStatus();
								switch(hfpStatus){
												case GocsdkCallback.HfpStatus_callin:
												case GocsdkCallback.HfpStatus_callout:
												case GocsdkCallback.HfpStatus_talking:
																state = 1;
																break;
												default:
																break;
								}
								updateBTStateTaiking(state);
				}

				protected WifiManager getWifiManager(){
								if(null == wifiManager){
												wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
								}
								return wifiManager;
				}

				protected void checkStatusWifi(){
								LoggerUtil.d("BaseSupportActivity   checkStatusWifi");
								int rssi = -200;
								if(getWifiManager().getWifiState() == WifiManager.WIFI_STATE_ENABLED){
												rssi = getWifiManager().getConnectionInfo().getRssi();
												LoggerUtil.d("BaseSupportActivity   checkStatusWifi rssi = " + rssi);
								}
								updateWifiRssi(rssi);
				}

				/**
					* 更新WIFI状态
					*
					* @param rssi
					*/
				private void updateWifiRssi(int rssi){
								LoggerUtil.d("BaseSupportActivity   updateWifiRssi rssi = " + rssi);
								if(null != wifiDrawableList && !wifiDrawableList.isEmpty()){
												int level = WifiManager.calculateSignalLevel(rssi, wifiDrawableList.size());
												LoggerUtil.d("BaseSupportActivity   updateWifiRssi level = " + level);
												iv_wifi.setImageResource(wifiDrawableList.get(level));
												if(0 == level){
																iv_wifi.setVisibility(View.GONE);
																updateBTState(0);
																updateBTStateTaiking(0);
												}
												else{
																iv_wifi.setVisibility(View.VISIBLE);
												}
								}
				}

				/**
					* 更新蓝牙状态
					*
					* @param state
					*/
				protected void updateBTState(int state){
								switch(state){
												case 0:
																iv_bt.setVisibility(View.GONE);
																break;
												case 1:
																iv_bt.setVisibility(View.VISIBLE);
																break;
												default:
																break;
								}
				}

				/**
					* 更新通话状态
					*
					* @param state
					*/
				protected void updateBTStateTaiking(int state){
								switch(state){
												case 0:
																iv_taiking.setVisibility(View.GONE);
																break;
												case 1:
																iv_taiking.setVisibility(View.VISIBLE);
																break;
												default:
																break;
								}
				}

				protected void registerReceiver(){
								baseReceiver = new BaseReceiver();
								IntentFilter intentFilter = new IntentFilter();
								intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
								intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
								intentFilter.addAction(Constant.Action.ACTION_LF_TXZ_BT_STATE);
								intentFilter.addAction(Constant.Action.ACTION_BT_STATE_TAIKING);
								registerReceiver(baseReceiver, intentFilter);
				}

				protected void unregisterReceiver(){
								if(null != baseReceiver){
												unregisterReceiver(baseReceiver);
								}
				}

				public class BaseReceiver extends BroadcastReceiver{
								@Override
								public void onReceive(Context context, Intent intent){
												String action = intent.getAction();
												LoggerUtil.d("BaseSupportActivity   BaseReceiver action = " + action);
												if(StringUtil.isNotEmpty(action)){
																switch(action){
																				case WifiManager.RSSI_CHANGED_ACTION:{
																								int rssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200);
																								updateWifiRssi(rssi);
																								break;
																				}
																				case WifiManager.WIFI_STATE_CHANGED_ACTION:{
																								checkStatusWifi();
																								break;
																				}
																				case Constant.Action.ACTION_LF_TXZ_BT_STATE:{
																								int state = intent.getIntExtra("btphone", 1001);
																								if (1001 == state){
																												updateBTState(0);
																								}else {
																												updateBTState(1);
																								}

																								break;
																				}
																				case Constant.Action.ACTION_BT_STATE_TAIKING:{
																								int state = intent.getIntExtra("state", 0);
																								updateBTStateTaiking(state);
																								break;
																				}
																				default:
																								break;
																}
												}
								}
				}

				public void openActivity(Class<?> pClass){
								openActivity(pClass, null);
				}

				public void openActivity(Class<?> pClass, Bundle pBundle){
								Intent intent = new Intent(this, pClass);
								if(pBundle != null){
												intent.putExtras(pBundle);
								}
								startActivity(intent);
								//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}

				public void openActivityForResult(Class<?> pClass, Bundle pBundle, int requestCode){
								Intent intent = new Intent(this, pClass);
								if(pBundle != null){
												intent.putExtras(pBundle);
								}
								startActivityForResult(intent, requestCode);
								overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}

				protected void openActivity(String pAction){
								openActivity(pAction, null);
				}

				protected void openActivity(String pAction, Bundle pBundle){
								Intent intent = new Intent(pAction);
								if(pBundle != null){
												intent.putExtras(pBundle);
								}
								startActivity(intent);
								overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}

				protected void openActivityForResult(String pAction, Bundle pBundle, int requestCode){
								Intent intent = new Intent(pAction);
								if(pBundle != null){
												intent.putExtras(pBundle);
								}
								startActivityForResult(intent, requestCode);
								overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}

				public void hideKeyboard(View view){
								InputMethodManager imm = (InputMethodManager) this
																.getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(view.getWindowToken(),
								                            InputMethodManager.HIDE_NOT_ALWAYS);
				}

				/**
					* @param @param  layoutResID
					* @param @param  root
					* @param @return 设定文件
					* @return View    返回类型
					* @throws
					* @Title: inflate
					* @Description: 加载布局的view
					*/
				protected View inflate(int layoutResID, ViewGroup root){
								LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								View view = layoutInflater.inflate(layoutResID, root, false);
								return view;
				}

				/**
					* 设置view
					*/
				public abstract int initContentView();

				/**
					* 注入Injector
					*/
				public abstract void initInjector();

				/**
					* init UI && Listener
					*/
				public abstract void initUiAndListener(Bundle savedInstanceState);

				public void finishActivity(){
								finish();
								overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				}

				public void reload(){
								Intent intent = getIntent();
								overridePendingTransition(0, 0);
								intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								finish();
								overridePendingTransition(0, 0);
								startActivity(intent);
				}

				@Override
				public boolean onOptionsItemSelected(MenuItem item){
								if(item.getItemId() == android.R.id.home){
												finish();
												return true;
								}
								return super.onOptionsItemSelected(item);
				}

				@Override
				protected void onDestroy(){
								super.onDestroy();
								unregisterReceiver();
								AppManager.getAppManager().finishActivity(this);
				}
}
