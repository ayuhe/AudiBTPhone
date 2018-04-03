package com.lingfei.android.bt.ui.btphone.device;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.BTPhoneMainActivity;
import com.lingfei.android.bt.ui.btphone.BTPhoneMainComponent;
import com.lingfei.android.bt.ui.btphone.adapter.BTDeviceListAdapter;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.event.BTDeviceEvent;
import com.lingfei.android.bt.ui.btphone.event.BTStatuEvent;
import com.lingfei.android.bt.ui.btphone.event.RequestFocusEvent;
import com.lingfei.android.bt.ui.btphone.event.UpdateItemBgEvent;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.bt.ui.btphone.widget.DeleteDeviceDialogActivity;
import com.lingfei.android.bt.ui.util.ListViewUtil;
import com.lingfei.android.uilib.base.BaseSupportFragment;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.StringUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
	* DeviceFragment
	*
	* @author heyu
	* @date 2017/6/20.
	*/
public class DeviceFragment extends BaseSupportFragment implements DeviceContract.View,
								AdapterView.OnItemClickListener,
								AdapterView.OnItemSelectedListener,
								AbsListView.OnScrollListener,
								AbsListView.OnItemLongClickListener,
								View.OnKeyListener,
								View.OnFocusChangeListener{

				@Bind(R.id.tv_seach_devices)
				TextView tv_seach_devices;

				@Bind(R.id.tv_tip)
				TextView tv_tip;

				@Bind(R.id.lv_phone_device)
				ListView lv_phone_device;

				@Inject
				DevicePresenter mPresenter;

				BTDeviceListAdapter btListAdapter;

				private int mCurrentFragment; // 记录当前的Fragment

				private boolean isListScroll = false; // 标识列表是否滚动
				private int mFirstVisibleItem = 0; // 记录当前可见的item

				public static DeviceFragment newInstance(){
								DeviceFragment fragment = new DeviceFragment();
								return fragment;
				}

				private void initView(){
								tv_seach_devices.setOnFocusChangeListener(this);
								lv_phone_device.setOnFocusChangeListener(this);
								tv_seach_devices.setOnKeyListener(this);
								lv_phone_device.setOnKeyListener(this);
								updateTvTipStr();
				}

				private void initListView(){
								ArrayList<BTDevice> btDeviceList = new ArrayList<>();
								btListAdapter = new BTDeviceListAdapter(getActivity(), btDeviceList);
								lv_phone_device.setAdapter(btListAdapter);
								lv_phone_device.setOnItemClickListener(this);
								lv_phone_device.setOnItemSelectedListener(this);
								lv_phone_device.setOnItemLongClickListener(this);
								setNextFucosDown();
				}

				private void requestFocus(){
								tv_seach_devices.setFocusableInTouchMode(true);
								tv_seach_devices.requestFocusFromTouch();
								tv_seach_devices.setFocusable(true);
								tv_seach_devices.requestFocus();
								EventPostUtils.Post(new UpdateItemBgEvent(0));
				}

				/**
					* 刷新选中背景
					*
					* @param view
					*/
				private void onUpdateItemBg(View view){
								if(!isListScroll){
												Rect rect = new Rect();
												view.getGlobalVisibleRect(rect);
												int top = rect.top;
												int bottom = rect.bottom;
												int pos = ListViewUtil.getSelectedItemIndexForFourItem(top, bottom);
												EventPostUtils.Post(new UpdateItemBgEvent(pos + 2));
								}
				}

				private void setNextFucosDown(){
								ShowLogUtil.show("setNextFucosDown");
								if(btListAdapter.getCount() > 0){
												tv_seach_devices.setNextFocusDownId(R.id.lv_phone_device);
								}
								else{
												requestFocus();
												tv_seach_devices.setNextFocusDownId(tv_seach_devices.getId());
								}
				}

				@OnClick(R.id.tv_seach_devices)
				public void onSeachDeviceClick(){
								tv_seach_devices.setFocusableInTouchMode(true);
								tv_seach_devices.setFocusable(true);
								tv_seach_devices.requestFocusFromTouch();
								tv_seach_devices.requestFocus();
								EventPostUtils.Post(new UpdateItemBgEvent(0));

								String cancelSearch = getResources().getString(R.string.btphone_search_stop);
								if(!tv_seach_devices.getText().equals(cancelSearch)){
												BTControler.getInstance().searchNewBTDevice_Start(); // 搜索蓝牙设备
												String searching = getResources().getString(R.string.btphone_searching);
												tv_seach_devices.setText(cancelSearch);
												tv_tip.setText(searching);
								}
								else{
												BTControler.getInstance().searchNewBTDevice_Stop(); // 停止搜索蓝牙设备
												String startSearch = getResources().getString(R.string.btphone_search_start);
												tv_seach_devices.setText(startSearch);

												updateTvTipStr();
								}
				}

				private void updateTvTipStr(){
								String devices = getResources().getString(R.string.btphone_devices);
								String connect = getResources().getString(R.string.btphone_connect);
								String tipStr = devices + connect;
								//创建一个 SpannableString对象
								SpannableString msp = new SpannableString(tipStr);
								//设置字体大小（绝对值,单位：像素）第二个参数boolean，如果为true，表示前面的字体大小单位为dip，否则为像素。
								msp.setSpan(new AbsoluteSizeSpan(18, true), devices.length(), tipStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								tv_tip.setText(msp);
				}

				@Subscribe
				public void onEventRequstFocus(RequestFocusEvent event){
								if(null != event){
												mCurrentFragment = event.getFragmentId();
												if(BTPhoneMainActivity.FRAGMENT_DEVICE == event.getFragmentId()){
																requestFocus();
												}
								}
				}

				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTStatuChange(BTStatuEvent event){
								if(null != event){
												switch(event.getStatu()){
																case DISCONNECTED:
																case OFF_LINE:{// 断开
																				btListAdapter.onResetDevicesDisconnect();
																				requestFocus();
																				setNextFucosDown();
																				break;
																}
																case CONNECTED:{ // 连接
																				break;
																}
																default:
																				break;
												}
								}
				}

				/**
					* 处理蓝牙设备相关的事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTDeviceChange(BTDeviceEvent event){
								if(null != event){
												switch(event.getStatu()){
																case DISCOVERY:{
																				BTDevice device = event.getDevice();
																				if(null != device){
																								// 发现新蓝牙设备
																								btListAdapter.addItemAndCheckRepeat(device);
																								setNextFucosDown();
																				}
																				break;
																}
																case CURRENT_PAIRLIST:{
																				BTDevice device = event.getDevice();
																				if(null != device){
																								// 获取蓝牙配对列表
																								String currentAddr = BTControler.getInstance().getCurrentAddr();
																								if(StringUtil.isNotEmpty(currentAddr)){
																												if(currentAddr.equals(device.getDeviceAddr())){
																																device.setStatus(BTDevice.Status.Connected);
																												}
																								}
																								btListAdapter.addItemAndCheckRepeat(device);
																								setNextFucosDown();
																				}
																				break;
																}
																case DISCOVERY_DONE:{
																				String searchAgain = getResources().getString(R.string.btphone_search_startagain);
																				tv_seach_devices.setText(searchAgain);
																				updateTvTipStr();
																				break;
																}
																case CURRENT_ADDR:{
																				BTDevice device = event.getDevice();
																				if(null != device && StringUtil.isNotEmpty(device.getDeviceAddr())){
																								// 当前连接的蓝牙设备
																								btListAdapter.setConnectedStatus(device);
																				}
																				break;
																}
																case CURRENT_NAME:{
																				break;
																}
																case CURRENT_DEVICE_NAME:{
																				BTDevice device = event.getDevice();
																				if(null != device && StringUtil.isNotEmpty(device.getDeviceName())){
																								// 当前连接蓝牙设备的名称
																								ShowLogUtil.show("蓝牙设备名称 ： " + device.getDeviceName());
																				}
																				break;
																}
																case CURRENT_PINCODE:{
																				BTDevice device = event.getDevice();
																				if(null != device && StringUtil.isNotEmpty(device.getPassword())){
																								// 当前连接蓝牙设备的密码
																								ShowLogUtil.show("蓝牙设备密码 ： " + device.getDeviceName());
																				}
																				break;
																}
																case DELETE_DEVICE:{
																				BTDevice device = event.getDevice();
																				btListAdapter.removeItem(device);
																				setNextFucosDown();
																				break;
																}
																default:
																				break;
												}
								}
				}

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
								if(btListAdapter != null){
												BTDevice btDevice = btListAdapter.getItem(position);
												switch(btDevice.getStatus()){
																case BTDevice.Status.Disconnected:{
																				// 长按未连接的蓝牙设备提示是否删除这个设备
																				Intent intent = new Intent(getContext(), DeleteDeviceDialogActivity.class);
																				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																				intent.putExtra("bt_device", btDevice);
																				startActivity(intent);
																				EventPostUtils.Post(new UpdateItemBgEvent(-1));
																				break;
																}
																default:
																				break;
												}
								}
								return true;
				}

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
								onUpdateItemBg(view);

								if(btListAdapter != null){
												BTDevice btDevice = btListAdapter.getItem(position);
												switch(btDevice.getStatus()){
																case BTDevice.Status.Connected:{
																				BTControler.getInstance().disConnect(); // 断开蓝牙连接
																				break;
																}
																case BTDevice.Status.Connecting:{
																				BTControler.getInstance().disConnect(); // 断开蓝牙连接
																				break;
																}
																case BTDevice.Status.Disconnected:{
																				if(BTControler.getInstance().isBTHfpConnected()){
																								BTControler.getInstance().disConnect(); // 如果有已连接的，先执行断开蓝牙连接
																				}
																				else{
																								BTControler.getInstance().connectHfpAddr(btDevice.getDeviceAddr()); // 连接蓝牙
																								btDevice.setStatus(BTDevice.Status.Connecting);
																								btListAdapter.setConnectedStatus(btDevice); // 更新列表中的状态
																				}
																				break;
																}
																default:
																				break;
												}
								}
				}

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
								onUpdateItemBg(view);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){

				}

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState){
								switch(scrollState){
												case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:{
																// 停止滚动
																isListScroll = false;
																if(0 != mFirstVisibleItem){
																				mFirstVisibleItem = mFirstVisibleItem + 1;
																}
																lv_phone_device.setSelection(mFirstVisibleItem);
																break;
												}
												case AbsListView.OnScrollListener.SCROLL_STATE_FLING:{
																// 滚动做出了抛的动作
																break;
												}
												case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:{
																// 正在滚动
																isListScroll = true;
																EventPostUtils.Post(new UpdateItemBgEvent(-1));
																break;
												}
												default:
																break;
								}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
								mFirstVisibleItem = firstVisibleItem;
				}

				@Override
				public void updateView(ArrayList<BTDevice> list){
								if(null != list && !list.isEmpty()){
												btListAdapter.setList(list);
												if(mCurrentFragment == BTPhoneMainActivity.FRAGMENT_DEVICE){
																requestFocus();
												}
								}
				}

				@Override
				public void onFocusChange(View v, boolean hasFocus){
								ShowLogUtil.show("onFocusChange ");
								if(hasFocus){
												int position = -1;
												switch(v.getId()){
																case R.id.tv_seach_devices:
																				position = 0;
																				ShowLogUtil.show("onFocusChange 0 ");
																				break;
																case R.id.lv_phone_device:
																				position = 2;
																				ShowLogUtil.show("onFocusChange 2 ");
																				lv_phone_device.setNextFocusDownId(lv_phone_device.getId());
																default:
																				lv_phone_device.setNextFocusDownId(lv_phone_device.getId());
																				ShowLogUtil.show("onFocusChange defaut " + v.getId() + "  down Id = " + v.getNextFocusDownId());
																				break;
												}

												EventPostUtils.Post(new UpdateItemBgEvent(position));
								}
				}

				@Override
				public boolean onKey(View view, int keyCode, KeyEvent event){
								return false;
				}

				@Override
				public void initInjector(){
								getComponent(BTPhoneMainComponent.class).inject(this);
				}

				@Override
				public int initContentView(){
								return R.layout.btphone_device_fragment;
				}

				@Override
				public void getBundle(Bundle bundle){

				}

				@Override
				public void initUI(View view){
								ButterKnife.bind(this, view);
								mPresenter.attachView(this);
								EventBus.getDefault().register(this);
								initView();
								initListView();
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
