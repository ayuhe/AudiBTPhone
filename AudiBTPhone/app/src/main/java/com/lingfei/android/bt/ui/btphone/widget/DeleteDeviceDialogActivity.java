package com.lingfei.android.bt.ui.btphone.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.BaseSupportActivity;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.event.BTDeviceEvent;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.bt.ui.btphone.util.BTStatu;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
	* 删除设备的弹框界面
	*/
public class DeleteDeviceDialogActivity extends BaseSupportActivity implements View.OnFocusChangeListener{

				@Bind(R.id.ib_menu_button)
				ImageButton ib_menu_button;

				@Bind(R.id.ib_menu_button_left)
				ImageButton ib_menu_button_left;

				@Bind(R.id.ib_menu_button_right)
				ImageButton ib_menu_button_right;

				@Bind(R.id.iv_item_selected)
				ImageView iv_item_selected;

				@Bind(R.id.tv_title)
				TextView tv_title;

				@Bind(R.id.tv_people_name)
				TextView tv_people_name;

				@Bind(R.id.btn_to_answer)
				Button btn_to_answer;

				@Bind(R.id.btn_to_hangup)
				Button btn_to_hangup;

				private BTDevice mBTDevice;

				@OnClick(R.id.ib_menu_button_right)
				public void controlButtonRightOnClick(){

				}

				@OnClick(R.id.ib_menu_button_left)
				public void controlButtonLeftOnClick(){
								finish();
				}

				@OnClick({R.id.btn_to_answer, R.id.btn_to_hangup})
				public void onclick(View v){
								v.requestFocusFromTouch();
								switch(v.getId()){
												case R.id.btn_to_answer:{
																if(null != mBTDevice){
																				String addr = mBTDevice.getDeviceAddr();
																				if(StringUtil.isNotEmpty(addr)){
																								BTControler.getInstance().deletePair(addr);
																								EventPostUtils.Post(new BTDeviceEvent(BTStatu.DELETE_DEVICE, mBTDevice));
																				}
																}
																break;
												}
												case R.id.btn_to_hangup:{
																break;
												}
												default:
																break;
								}
								finish();
				}

				public void initPhoneInComingView(){
								Intent intent = getIntent();
								mBTDevice = (BTDevice) intent.getSerializableExtra("bt_device");
								if(null != mBTDevice){
												String name = mBTDevice.getDeviceName();
												String addr = mBTDevice.getDeviceAddr();
												if(StringUtil.isNotEmpty(name)){
																addr = name; // 姓名不为空，显示姓名
												}
												tv_people_name.setText(" " + addr + " ");
								}
								else{
												finish();
								}

								String title = getResources().getString(R.string.delete_delete) + getResources().getString(R.string.btphone_devices);
								tv_title.setText(title);
								btn_to_answer.setText(getResources().getString(R.string.ok));
								btn_to_hangup.setText(getResources().getString(R.string.cancel));
								btn_to_answer.setOnFocusChangeListener(this);
								btn_to_hangup.setOnFocusChangeListener(this);
				}

				@Override
				public void onFocusChange(View v, boolean hasFocus){
								if(hasFocus){
												switch(v.getId()){
																case R.id.btn_to_answer:
																				iv_item_selected.setImageResource(R.drawable.ic_bt_callin_item_selected_0);
																				break;
																case R.id.btn_to_hangup:
																				iv_item_selected.setImageResource(R.drawable.ic_bt_callin_item_selected_1);
																				break;
																default:
																				break;
												}
								}
				}

				@Override
				public boolean dispatchKeyEvent(KeyEvent event){
								switch(event.getKeyCode()){
												case KeyEvent.KEYCODE_DPAD_LEFT:
																if(event.getAction() == KeyEvent.ACTION_DOWN){
																				ib_menu_button_left.setImageResource(R.drawable.ic_control_menu_left_focus);
																}
																else if(event.getAction() == KeyEvent.ACTION_UP || event.getAction() == KeyEvent.FLAG_CANCELED){
																				ib_menu_button_left.setImageResource(R.drawable.ic_control_menu_left_normal);
																				controlButtonLeftOnClick();
																}
																return true;
												case KeyEvent.KEYCODE_DPAD_RIGHT:
																if(event.getAction() == KeyEvent.ACTION_DOWN){
																				ib_menu_button_right.setImageResource(R.drawable.ic_control_menu_right_focus);
																}
																else if(event.getAction() == KeyEvent.ACTION_UP){
																				ib_menu_button_right.setImageResource(R.drawable.ic_control_menu_right_normal);
																				controlButtonRightOnClick();
																}
																return true;
												case KeyEvent.KEYCODE_ENTER:
												case KeyEvent.KEYCODE_DPAD_CENTER:{
																if(event.getAction() == KeyEvent.ACTION_DOWN){
																				ib_menu_button.setImageResource(R.drawable.ic_control_menu_fucos);
																}
																else if(event.getAction() == KeyEvent.ACTION_UP || event.getAction() == KeyEvent.FLAG_CANCELED){
																				ib_menu_button.setImageResource(R.drawable.ic_control_menu_normal);
																}
																break;
												}
												default:
																break;
								}
								return super.dispatchKeyEvent(event);
				}

				@Override
				public int initContentView(){
								return R.layout.phone_in_coming_activity;
				}

				@Override
				public void initInjector(){

				}

				@Override
				public void initUiAndListener(Bundle savedInstanceState){
								ll_toolbar.setVisibility(View.GONE);
								ib_home.setVisibility(View.GONE);
								ButterKnife.bind(this);
								initPhoneInComingView();
								setFinishOnTouchOutside(false);
				}

				@Override
				public void onWindowFocusChanged(boolean hasFocus){
								super.onWindowFocusChanged(hasFocus);
								if(hasFocus){
												btn_to_answer.requestFocusFromTouch();
												btn_to_answer.requestFocus();
								}
				}

				@Override
				protected void onDestroy(){
								super.onDestroy();
								ButterKnife.unbind(this);
				}
}
