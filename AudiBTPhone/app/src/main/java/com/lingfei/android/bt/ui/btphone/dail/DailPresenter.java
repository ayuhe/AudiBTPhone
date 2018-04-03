package com.lingfei.android.bt.ui.btphone.dail;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.injector.PerActivity;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;
import com.lingfei.android.bt.ui.btphone.event.BTContactEvent;
import com.lingfei.android.bt.ui.btphone.event.BTStatuEvent;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.uilib.util.ResourceUtils;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.ToastUtils;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
	* DailPresenter
	*
	* @author heyu
	* @date 2017/6/19.
	*/
@PerActivity
public class DailPresenter implements DailContract.Presenter{
				public final static int TYPE_CALL_OUT = 0;// 拨号
				public final static int TYPE_TAIKING = 1; // 通话中
				public final static int TYPE_CALL_IN = 2; // 来电
				public final static int TYPE_HANG_UP = 3; // 挂断

				private DailContract.View mView;

				private int mCurrentType = TYPE_HANG_UP;

				private BTPhonePeople mTaikingPeople;

				private String mCurrentNumber;

				@Inject
				public DailPresenter(){
				}

				@Override
				public void attachView(@NonNull DailContract.View view){
								this.mView = view;
								EventBus.getDefault().register(this);
				}

				@Override
				public void detachView(){
								EventBus.getDefault().unregister(this);
								this.mView = null;
				}

				@Override
				public void initData(){
								boolean isConnected = BTControler.getInstance().isBTHfpConnected();
								if(isConnected){ // 已连接蓝牙
												BTPhonePeople phonePeople = BTControler.getInstance().getPeopleTalking();
												if(null != phonePeople){
																mTaikingPeople = phonePeople;
																if(StringUtil.isNotEmpty(phonePeople.getPhoneNumber())){
																				String type = phonePeople.getType();
																				if(BTPhonePeople.Type.TALKING.equals(type)){
																								// 正在通话
																								mCurrentType = TYPE_TAIKING;
																				}
																				else if(BTPhonePeople.Type.Callin.equals(type)){
																								mCurrentType = TYPE_CALL_IN;
																				}
																				else if(BTPhonePeople.Type.CALL_OUT.equals(type)){
																								mCurrentType = TYPE_CALL_OUT;
																				}else {
																								mCurrentType = TYPE_HANG_UP;
																				}
																}
												}
								}

								onPrepareUpdateView();
				}

				/**
					* 处理拨号或者挂断操作
					*
					* @param phoneNum
					*/
				@Override
				public void onHandleDailAndHangup(String phoneNum){
								if(TYPE_HANG_UP != mCurrentType){
												mCurrentType = TYPE_HANG_UP;
												BTControler.getInstance().toHangup(); // 挂断电话
								}
								else{
												if(!TextUtils.isEmpty(phoneNum)){
																mCurrentNumber = phoneNum;
																mCurrentType = TYPE_CALL_OUT;
																BTControler.getInstance().toCall(phoneNum); // 拨打电话
												}
												else{
																ToastUtils.showToast(ResourceUtils.getString(R.string.btphone_input_phone_number));
												}
								}
								onPrepareUpdateView();
				}

				@Override
				public void onHandleClear(){
								if(TYPE_CALL_IN == mCurrentType){
												// 正在来电
												BTControler.getInstance().toAnswer(); // 接听
												mCurrentType = TYPE_TAIKING;
												// 接通后需要复位状态
												BTControler.getInstance().getPeopleTalking().setType(BTPhonePeople.Type.TALKING);
												onPrepareUpdateView();
								}
								else if(TYPE_HANG_UP == mCurrentType){
												mView.onClearOnce();
								}
				}

				public int getCurrentType(){
								return mCurrentType;
				}

				// 准备更新UI界面
				private void onPrepareUpdateView(){
								String phoneNum = "";
								if(null != mTaikingPeople){
												phoneNum = mTaikingPeople.getPhoneNumber();
												String phoneName = mTaikingPeople.getPeopleName();
												if(StringUtil.isNotEmpty(phoneName)){
																// 如果姓名不为空，则显示姓名
																phoneNum = phoneName;
												}
								}

								String callNowStr = "";
								int keyClearResId = R.drawable.ic_key_normal_clear;
								int keyEnterResId = R.drawable.ic_bt_hangup;
								switch(mCurrentType){
												case TYPE_CALL_OUT:{
																callNowStr = ResourceUtils.getString(R.string.callingnow);
																if(StringUtil.isEmpty(phoneNum)){
																				callNowStr = callNowStr + mCurrentNumber;
																}
																else{
																				callNowStr = callNowStr + phoneNum;
																}
												}
												break;
												case TYPE_TAIKING:{
																callNowStr = phoneNum + "-" + ResourceUtils.getString(R.string.btphone_taiking);
												}
												break;
												case TYPE_CALL_IN:{
																callNowStr = phoneNum + "-" + ResourceUtils.getString(R.string.btphone_in_coming_phone);
																keyClearResId = R.drawable.ic_bt_dail;
												}
												break;
												case TYPE_HANG_UP:
																keyEnterResId = R.drawable.ic_bt_dail;
																break;
												default:
																break;
								}

								// 更新UI界面
								mView.onUpdateView(callNowStr, keyClearResId, keyEnterResId, mCurrentType);
				}

				/**
					* 处理更新蓝牙状态的相关事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTStatuChange(BTStatuEvent event){
								if(null != event){
												boolean isHandle = false; // 标识Switch后是否要处理相关逻辑
												switch(event.getStatu()){
																case CALL_OUT:
																				isHandle = true;
																				mCurrentType = TYPE_CALL_OUT;
																				break;
																case ON_TALKING:
																case TALKING:{
																				isHandle = true;
																				mCurrentType = TYPE_TAIKING;
																				break;
																}
																case DISCONNECTED:
																case OFF_LINE:
																case HANG_UP:{
																				isHandle = true;
																				mCurrentType = TYPE_HANG_UP;
																				break;
																}
																default:
																				break;
												}

												if(isHandle){
																onPrepareUpdateView();
												}
								}
				}

				/**
					* 处理蓝牙模块联系人相关事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTContactChange(BTContactEvent event){
								if(null != event){
												boolean isHandle = false; // 标识Switch后是否要处理相关逻辑
												switch(event.getStatu()){
																case CALL_OUT_NEXT:{
																				isHandle = true;
																				mCurrentType = TYPE_CALL_OUT;
																				break;
																}
																case CALL_IN_NEXT:{
																				isHandle = true;
																				ShowLogUtil.show("来电");
																				mCurrentType = TYPE_CALL_IN;
																				break;
																}
																default:
																				break;
												}

												if(isHandle){
																BTPhonePeople phonePeople = event.getPhonePeople();
																if(null != phonePeople){
																				mTaikingPeople = phonePeople;
																				onPrepareUpdateView();
																}
												}
								}
				}
}
