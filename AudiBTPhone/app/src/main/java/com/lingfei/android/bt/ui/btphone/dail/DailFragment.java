package com.lingfei.android.bt.ui.btphone.dail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.BTPhoneMainActivity;
import com.lingfei.android.bt.ui.btphone.BTPhoneMainComponent;
import com.lingfei.android.bt.ui.btphone.event.RequestFocusEvent;
import com.lingfei.android.uilib.base.BaseSupportFragment;
import com.lingfei.android.uilib.util.StringUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
	* DailFragment
	*
	* @author heyu
	* @date 2017/6/19.
	*/
public class DailFragment extends BaseSupportFragment implements DailContract.View,
								View.OnClickListener{

				@Bind(R.id.tv_dail_num)
				TextView tv_dail_num;

				@Bind(R.id.key1)
				ImageButton key1;

				@Bind(R.id.key2)
				ImageButton key2;

				@Bind(R.id.key3)
				ImageButton key3;

				@Bind(R.id.key4)
				ImageButton key4;

				@Bind(R.id.key5)
				ImageButton key5;

				@Bind(R.id.key6)
				ImageButton key6;

				@Bind(R.id.key7)
				ImageButton key7;

				@Bind(R.id.key8)
				ImageButton key8;

				@Bind(R.id.key9)
				ImageButton key9;

				@Bind(R.id.key0)
				ImageButton key0;

				@Bind(R.id.key_clear)
				ImageButton key_clear;

				@Bind(R.id.key_enter)
				ImageButton key_enter;

				@Inject
				DailPresenter mPresenter;

				private StringBuffer phoneStr = new StringBuffer();

				public static DailFragment newInstance(){
								DailFragment fragment = new DailFragment();
								return fragment;
				}

				private void setOnClickLisener(){
								key1.setOnClickListener(this);
								key2.setOnClickListener(this);
								key3.setOnClickListener(this);
								key4.setOnClickListener(this);
								key5.setOnClickListener(this);
								key6.setOnClickListener(this);
								key7.setOnClickListener(this);
								key8.setOnClickListener(this);
								key9.setOnClickListener(this);
								key0.setOnClickListener(this);
								key_clear.setOnLongClickListener(new View.OnLongClickListener(){
												@Override
												public boolean onLongClick(View v){
																onClearPhoneText();
																return false;
												}
								});
				}

				private void requestFocus(){
								key1.setFocusable(true);
								key1.requestFocus();
				}

				@Subscribe
				public void onEventRequstFocus(RequestFocusEvent event){
								if(null != event){
												if(BTPhoneMainActivity.FRAGMENT_DAIL == event.getFragmentId()){
																requestFocus();
												}
								}
				}

				@OnClick(R.id.key_enter)
				public void onClickOK(View v){
								switch(v.getId()){
												case R.id.key_enter:
																// 拨号、挂断
																mPresenter.onHandleDailAndHangup(phoneStr.toString().trim());
																break;
												default:
																break;
								}
				}

				@OnClick(R.id.key_clear)
				public void onClickClear(View v){
								switch(v.getId()){
												case R.id.key_clear:
																// 清除、接听
																mPresenter.onHandleClear();
																break;
												default:
																break;
								}
				}

				@Override
				public void onClick(View v){
								if(DailPresenter.TYPE_HANG_UP != mPresenter.getCurrentType()){
												// 正在通话中，按键无响应
												return;
								}

								if(phoneStr.length() > 13){
												// 限制输入长度13位
												return;
								}
								switch(v.getId()){
												case R.id.key1:
																phoneStr.append("1");
																break;
												case R.id.key2:
																phoneStr.append("2");
																break;
												case R.id.key3:
																phoneStr.append("3");
																break;
												case R.id.key4:
																phoneStr.append("4");
																break;
												case R.id.key5:
																phoneStr.append("5");
																break;
												case R.id.key6:
																phoneStr.append("6");
																break;
												case R.id.key0:
																phoneStr.append("0");
																break;
												case R.id.key7:
																phoneStr.append("7");
																break;
												case R.id.key8:
																phoneStr.append("8");
																break;
												case R.id.key9:
																phoneStr.append("9");
																break;
												default:
																break;
								}
								tv_dail_num.setText(phoneStr.toString());
				}

				// 清除号码
				private void onClearPhoneText(){
								if(!TextUtils.isEmpty(tv_dail_num.getText())){
												tv_dail_num.setText("");
												phoneStr.replace(0, phoneStr.length(), "");
								}
				}

				/**
					* 刷新UI
					*
					* @param callNowStr
					* @param clearResId
					* @param okResId
					* @param type
					*/
				@Override
				public void onUpdateView(String callNowStr, int clearResId, int okResId, int type){
								if(StringUtil.isNotEmpty(callNowStr)){
												tv_dail_num.setText(callNowStr);
								}
								else if(DailPresenter.TYPE_HANG_UP == type){
												onClearPhoneText();
								}

								key_clear.setImageResource(clearResId);
								key_enter.setImageResource(okResId);
								key_enter.requestFocus();
				}

				@Override
				public void onClearOnce(){
								String str = phoneStr.toString();
								if(str.length() > 0){
												phoneStr.replace(str.length() - 1, str.length(), "");
												tv_dail_num.setText(phoneStr);
								}
				}

				@Override
				public void initInjector(){
								getComponent(BTPhoneMainComponent.class).inject(this);
				}

				@Override
				public int initContentView(){
								return R.layout.btphone_dail_fragment;
				}

				@Override
				public void getBundle(Bundle bundle){

				}

				@Override
				public void initUI(View view){
								ButterKnife.bind(this, view);
								EventBus.getDefault().register(this);
								mPresenter.attachView(this);
								setOnClickLisener();
								requestFocus();
				}

				@Override
				public void initData(){
								mPresenter.initData();
				}

				@Override
				public void onDestroyView(){
								super.onDestroyView();
								EventBus.getDefault().unregister(this);
								mPresenter.detachView();
								ButterKnife.unbind(this);
				}
}
