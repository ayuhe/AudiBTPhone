package com.lingfei.android.bt.ui.btphone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.BaseSupportActivity;
import com.lingfei.android.bt.ui.btphone.adapter.IconAdapter;
import com.lingfei.android.bt.ui.btphone.adapter.MenuAdapter;
import com.lingfei.android.bt.ui.btphone.adapter.PhonePagerAdapter;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.bean.MenuBean;
import com.lingfei.android.bt.ui.btphone.contact.ContactFragment;
import com.lingfei.android.bt.ui.btphone.dail.DailFragment;
import com.lingfei.android.bt.ui.btphone.device.DeviceFragment;
import com.lingfei.android.bt.ui.btphone.event.BTContactEvent;
import com.lingfei.android.bt.ui.btphone.event.BTDeviceEvent;
import com.lingfei.android.bt.ui.btphone.event.BTStatuEvent;
import com.lingfei.android.bt.ui.btphone.event.RequestFocusEvent;
import com.lingfei.android.bt.ui.btphone.event.ShowDailFlagmentEvent;
import com.lingfei.android.bt.ui.btphone.event.UpdateItemBgEvent;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.bt.ui.util.ListViewUtil;
import com.lingfei.android.business.cmd.send.GPSStateCmd;
import com.lingfei.android.uilib.base.inteface.HasComponent;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.widget.ViewPagerFixed;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
	* 蓝牙电话
	* Created by heyu on 2017/6/15.
	*/
public class BTPhoneMainActivity extends BaseSupportActivity implements BTPhoneMainContract.View,
								HasComponent<BTPhoneMainComponent>{

				private final String TAG = this.getClass().getSimpleName();
				public final static int FRAGMENT_DAIL = 0;
				public final static int FRAGMENT_CONTACT = 1;
				public final static int FRAGMENT_DEVICE = 2;

				private int mCurrentFragment = FRAGMENT_DAIL; // 记录当前的Fragment
				private String mCallType = "";

				private final static int ANIMATOR_TIME = 500;

				@Bind(R.id.rl_music_left)
				RelativeLayout rl_music_left;

				@Bind(R.id.iv_menu_icon_selected)
				ImageView iv_menu_icon_selected;

				@Bind(R.id.menu_icon_listview)
				ListView menu_icon_listview;

				@Bind(R.id.rl_music)
				RelativeLayout rl_music;

				@Bind(R.id.tv_title)
				TextView tv_title;

				@Bind(R.id.et_device_name)
				EditText et_device_name;

				@Bind(R.id.menu_title_listview)
				ListView menu_title_listview;

				@Bind(R.id.rl_popuwindow)
				RelativeLayout rl_popuwindow;

				@Bind(R.id.rl_control_button)
				RelativeLayout rlControlButton;

				@Bind(R.id.ib_menu_button)
				ImageButton ib_menu_button;

				@Bind(R.id.ib_menu_button_left)
				ImageButton ib_menu_button_left;

				@Bind(R.id.ib_menu_button_right)
				ImageButton ib_menu_button_right;

				@Bind(R.id.ib_menu_button_icon)
				ImageButton ib_menu_button_icon;

				@Bind(R.id.ib_menu_button_music)
				ImageButton ib_menu_button_music;

				@Bind(R.id.rl_music_right)
				RelativeLayout rl_music_right;

				@Bind(R.id.iv_device_bg)
				ImageView iv_device_bg;

				@Bind(R.id.iv_music_item_selected)
				ImageView iv_music_item_selected;

				@Bind(R.id.iv_translucent)
				ImageView iv_translucent;

				@Bind(R.id.iv_default_cover)
				ImageView iv_default_cover;

				@Bind(R.id.viewPager)
				ViewPagerFixed viewPager;

				BTPhoneMainComponent mComponent;
				@Inject
				BTPhoneMainPresenter mPresenter;

				private static final int CHECK_HAVED_MUSIC = 0x03;  // 检查是否有曲目
				private static final int HIDE_MENU_TITLE_MSG = 5; // 是否隐藏菜单标题弹框
				private static final int HIDE_MENU_ICON_MSG = 6; // 是否隐藏菜单图标的View
				private static final int DELAY_TIME = 10000; // xx秒后菜单按钮自动隐藏

				@Inject
				IconAdapter menuIconAdapter;

				@Inject
				MenuAdapter menuTitleAdapter;

				private boolean isShowControlMenu = false; // 标识是否显示操作菜单列表
				private boolean isPlayingAnim = false; // 标识是否正在播放动画

				private int mMenuCurrentPosition = 0; // 记录当前菜单位置

				private String mCurrentDeviceName = ""; // 记录当前的

				private BTControler.ServiceToken mToken;

				public static void startActivity(Context mContext){
								Intent intent = new Intent(mContext, BTPhoneMainActivity.class);
								mContext.startActivity(intent);
				}

				@OnLongClick(R.id.et_device_name)
				public boolean OnLongClickEditDevice(){
								et_device_name.setCursorVisible(true);
								et_device_name.setFocusableInTouchMode(true);
								et_device_name.requestFocusFromTouch();
								et_device_name.setFocusable(true);
								et_device_name.requestFocus();
								return true;
				}

				@OnClick(R.id.ib_menu_button_right)
				public void controlButtonRightOnClick(){
								if(isShowControlMenu){
												if(!isPlayingAnim && !isFinishing()){
																ObjectAnimator rlMusicLeftAnim = getRLMusicLeftAnimator();
																ObjectAnimator rlMusicRightAnim = getRLMusicRightAnimator();
																ObjectAnimator ibControlButtonAnim = getRLControlButtonAnimator();
																ObjectAnimator ibControlButtonRotationAnim = getRLControlButtonRotationAnimator();
																ObjectAnimator ibControlButtonScaleAnim = getRLControlButtonScaleAnimator();
																ObjectAnimator rlMusicAnim = getRLMusicAnimator();

																AnimatorSet animSet = new AnimatorSet();
																animSet.play(rlMusicLeftAnim)
																								.with(rlMusicRightAnim)
																								.with(ibControlButtonAnim)
																								.with(ibControlButtonRotationAnim)
																								.with(ibControlButtonScaleAnim)
																								.with(rlMusicAnim);
																animSet.start();
												}
								}
				}

				@OnClick(R.id.ib_menu_button_left)
				public void controlButtonLeftOnClick(){
								if(isShowControlMenu){
												finishActivity();
								}
								else{
												if(!isPlayingAnim && !isFinishing()){
																ObjectAnimator rlMusicLeftAnim = getRLMusicLeftAnimator();
																ObjectAnimator rlMusicRightAnim = getRLMusicRightAnimator();
																ObjectAnimator ibControlButtonAnim = getRLControlButtonAnimator();
																ObjectAnimator ibControlButtonRotationAnim = getRLControlButtonRotationAnimator();
																ObjectAnimator ibControlButtonScaleAnim = getRLControlButtonScaleAnimator();
																ObjectAnimator rlMusicAnim = getRLMusicAnimator();

																AnimatorSet animSet = new AnimatorSet();
																animSet.play(rlMusicLeftAnim)
																								.with(rlMusicRightAnim)
																								.with(ibControlButtonAnim)
																								.with(ibControlButtonRotationAnim)
																								.with(ibControlButtonScaleAnim)
																								.with(rlMusicAnim);
																animSet.start();
												}
								}
				}

				@NonNull
				private ObjectAnimator getRLMusicAnimator(){
								float rlMusicNowX = rl_music.getTranslationX();
								float rlMusicNextX = 0f;
								if(rlMusicNowX == rlMusicNextX){
												rlMusicNextX = 20f;
								}
								ObjectAnimator rlMusicAnim = ObjectAnimator.ofFloat(rl_music, "translationX", rlMusicNowX, rlMusicNextX);
								rlMusicAnim.setDuration(ANIMATOR_TIME);
								return rlMusicAnim;
				}

				@NonNull
				private ObjectAnimator getRLControlButtonScaleAnimator(){
								ObjectAnimator ibAnim = ObjectAnimator.ofFloat(rlControlButton, "scaleY", 1f, 1.1f, 1f);
								ibAnim.setDuration(ANIMATOR_TIME);
								return ibAnim;
				}

				@NonNull
				private ObjectAnimator getRLControlButtonRotationAnimator(){
								float ibControlButtonNowX = rlControlButton.getRotationY();
								float ibControlButtonNextX = 0f;
								if(ibControlButtonNowX == ibControlButtonNextX){
												ibControlButtonNextX = 25f;
								}
								ObjectAnimator ibAnim = ObjectAnimator.ofFloat(rlControlButton, "rotationY", ibControlButtonNowX, ibControlButtonNextX, 0);
								ibAnim.setDuration(ANIMATOR_TIME);
								return ibAnim;
				}

				@NonNull
				private ObjectAnimator getRLControlButtonAnimator(){
								float ibControlButtonNowX = rlControlButton.getTranslationX();
								float ibControlButtonNextX = 0f;
								if(ibControlButtonNowX == ibControlButtonNextX){
												isShowControlMenu = false;
												ibControlButtonNextX = 35f;
								}
								else{
												isShowControlMenu = true;
								}
								ObjectAnimator ibControlButtonAnim = ObjectAnimator.ofFloat(rlControlButton, "translationX", ibControlButtonNowX, ibControlButtonNextX);
								ibControlButtonAnim.setDuration(ANIMATOR_TIME);
								ibControlButtonAnim.addListener(new Animator.AnimatorListener(){
												@Override
												public void onAnimationStart(Animator animation){
																isPlayingAnim = true;
																ib_menu_button.setVisibility(View.VISIBLE);

																ib_menu_button_icon.setVisibility(View.INVISIBLE);
																ib_menu_button_music.setVisibility(View.INVISIBLE);
																iv_menu_icon_selected.setVisibility(View.INVISIBLE);
																iv_music_item_selected.setVisibility(View.INVISIBLE);
																rl_popuwindow.setVisibility(View.INVISIBLE);
																iv_translucent.setVisibility(View.INVISIBLE);
												}

												@Override
												public void onAnimationEnd(Animator animation){
																isPlayingAnim = false;
																showMenuIconLayout();
												}

												@Override
												public void onAnimationCancel(Animator animation){
																isPlayingAnim = false;
																showMenuIconLayout();
												}

												@Override
												public void onAnimationRepeat(Animator animation){

												}
								});
								return ibControlButtonAnim;
				}

				private void showMenuIconLayout(){
								ib_menu_button.setVisibility(View.INVISIBLE);
								if(isShowControlMenu){
												ib_menu_button_icon.setVisibility(View.VISIBLE);
												iv_music_item_selected.setVisibility(View.INVISIBLE);
												iv_menu_icon_selected.setVisibility(View.VISIBLE);
												ib_menu_button_right.setVisibility(View.VISIBLE);
												iv_translucent.setVisibility(View.VISIBLE);
												showMenuTitlePopupWindow();
								}
								else{
												ib_menu_button_music.setVisibility(View.VISIBLE);
												iv_menu_icon_selected.setVisibility(View.INVISIBLE);
												rl_popuwindow.setVisibility(View.INVISIBLE);
												ib_menu_button_right.setVisibility(View.INVISIBLE);
												iv_translucent.setVisibility(View.INVISIBLE);
												EventPostUtils.Post(new RequestFocusEvent(mCurrentFragment));
								}
				}

				@NonNull
				private ObjectAnimator getRLMusicLeftAnimator(){
								float rlMusicLeftNowX = rl_music_left.getTranslationX();
								float rlMusicLeftNextX = 0f;
								if(rlMusicLeftNowX == rlMusicLeftNextX){
												rlMusicLeftNextX = -195f;
								}

								ObjectAnimator rlMusicLeftAnim = ObjectAnimator.ofFloat(rl_music_left, "translationX", rlMusicLeftNowX, rlMusicLeftNextX);
								rlMusicLeftAnim.setDuration(ANIMATOR_TIME);

								return rlMusicLeftAnim;
				}

				@NonNull
				private ObjectAnimator getRLMusicRightAnimator(){
								float rlNowX = rl_music_right.getTranslationX();
								float rlNextX = 0f;
								if(rlNowX == rlNextX){
												rlNextX = 180f;
								}
								else{
												rlNextX = 0f;
								}

								ObjectAnimator rlAnim = ObjectAnimator.ofFloat(rl_music_right, "translationX", rlNowX, rlNextX);
								rlAnim.setDuration(ANIMATOR_TIME);

								return rlAnim;
				}

				@Subscribe
				public void onEventNotifyChangeItemBg(UpdateItemBgEvent event){
								if(null != event){
												if(mCurrentFragment == FRAGMENT_DAIL){
																return;
												}
												ListViewUtil.updateListViewItemSelected(iv_music_item_selected, event.getPosition());
								}
				}

				@Subscribe
				public void onEventShowDailFlagment(ShowDailFlagmentEvent event){
								LoggerUtil.e("onEventShowDailFlagment");
								onMenuClick(0);
				}

				/**
					* 处理更新蓝牙状态的相关事件
					*
					* @param event
					*/
				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTStatuChange(BTStatuEvent event){
								if(null != event){
												switch(event.getStatu()){
																case HANG_UP:{
																				if("callout".equals(mCallType)){
																								finish(); // 去电弹出拨号界面，当挂断后要恢复之前界面
																				}
																				break;
																}
																case DISCONNECTED:
//																				ToastUtils.showToast("蓝牙设备断开");
																				break;
																default:
																				break;
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
												switch(event.getStatu()){
																case CALL_OUT_NEXT:
																case CALL_IN_NEXT:{
																				// 切换到拨号界面
																				onMenuClick(FRAGMENT_DAIL);
																				ListViewUtil.updateListViewItemSelected(iv_music_item_selected, -1);
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
																case CURRENT_DEVICE_NAME:{
																				BTDevice device = event.getDevice();
																				if(null != device && StringUtil.isNotEmpty(device.getDeviceName())){
																								// 当前连接蓝牙设备的名称
																								mCurrentDeviceName = device.getDeviceName();
																								ShowLogUtil.show("蓝牙设备名称 ： " + mCurrentDeviceName);
																								et_device_name.setText(mCurrentDeviceName);
																								et_device_name.setFocusable(false);
																				}
																				break;
																}
																default:
																				break;
												}
								}
				}

				/**
					* 刷新菜单列表
					*
					* @param musicMenus
					*/
				@Override
				public void updateMenuList(List<MenuBean> musicMenus){
								menuIconAdapter.setList(musicMenus);
								// Title
								menuTitleAdapter.setList(musicMenus);
				}

				private void initViewPager(){
								List<Fragment> fragments = getFragmentList();
								PhonePagerAdapter pagerAdapter = new PhonePagerAdapter(getSupportFragmentManager(), fragments);
								viewPager.setScrollble(false);
								viewPager.setAdapter(pagerAdapter);
								viewPager.setFocusable(true);
								viewPager.setFocusableInTouchMode(true);
								viewPager.setTouchable(true);
								viewPager.setOnKeyListener(new View.OnKeyListener(){
												@Override
												public boolean onKey(View v, int keyCode, KeyEvent event){
																return true;
												}
								});
								viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
												@Override
												public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

												}

												@Override
												public void onPageSelected(int position){
																mCurrentFragment = position;
												}

												@Override
												public void onPageScrollStateChanged(int state){

												}
								});
				}

				/**
					* 初始化的Fragment
					*
					* @return
					*/
				@NonNull
				private List<Fragment> getFragmentList(){
								LinkedList<Fragment> fragments = new LinkedList<>();
								fragments.add(DailFragment.newInstance());
								fragments.add(ContactFragment.newInstance());
								fragments.add(DeviceFragment.newInstance());
								return fragments;
				}

				private void initView(){
								iv_music_item_selected.setVisibility(View.VISIBLE);
								iv_menu_icon_selected.setVisibility(View.INVISIBLE);
								String localBtName = BTControler.getInstance().getLocalBtName();
								if(StringUtil.isNotEmpty(localBtName)){
												et_device_name.setText(localBtName);
								}
								et_device_name.setOnFocusChangeListener(new View.OnFocusChangeListener(){
												@Override
												public void onFocusChange(View v, boolean hasFocus){
																if(!hasFocus){
																				hideKeyboard(v); // 隐藏键盘
																				et_device_name.setCursorVisible(false);
																				et_device_name.setFocusable(false);
																				String newDeviceName = et_device_name.getText().toString().trim();
																				if(StringUtil.isNotEmpty(newDeviceName) && !newDeviceName.equals(mCurrentDeviceName)){
																								BTControler.getInstance().setLocalBTName(newDeviceName);
																				}
																}
												}
								});
				}

				private void initMusicListView(){
								// Icon
								menu_icon_listview.setAdapter(menuIconAdapter);
/*								menu_icon_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
												@Override
												public void onItemClick(AdapterView<?> parent, View view, int position, long id){
																onMenuClick(position);
																mMenuCurrentPosition = position;
												}
								});*/

								// Title
								menu_title_listview.setAdapter(menuTitleAdapter);
								menu_title_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
												@Override
												public void onItemClick(AdapterView<?> parent, View view, int position, long id){
																onMenuClick(position);
																mMenuCurrentPosition = position;
																showMenuTitlePopupWindow();
																if(position != menuTitleAdapter.getClickItemPos()){
																				menuTitleAdapter.setClickItemPos(position);
																}
												}
								});
								menu_title_listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
												@Override
												public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
																mMenuCurrentPosition = position;
																if(isShowControlMenu){
																				showMenuTitlePopupWindow();
																}
																if(-1 != menuTitleAdapter.getClickItemPos()){
																				menuTitleAdapter.setClickItemPos(-1);
																}
												}

												@Override
												public void onNothingSelected(AdapterView<?> parent){
																iv_menu_icon_selected.setVisibility(View.INVISIBLE);
												}
								});
				}

				private void menuIconListViewRequestFocus(){
								if(null != menu_icon_listview && isShowControlMenu){
												menu_icon_listview.setFocusableInTouchMode(true);
												menu_icon_listview.requestFocusFromTouch();
												menu_icon_listview.setFocusable(true);
												menu_icon_listview.requestFocus();
								}
				}

				// 菜单列表获取焦点
				private void menuTitleListViewRequestFocus(){
								if(null != menu_title_listview && isShowControlMenu){
												menu_title_listview.setFocusableInTouchMode(true);
												menu_title_listview.requestFocusFromTouch();
												menu_title_listview.setFocusable(true);
												menu_title_listview.requestFocus();
								}
				}

				private void onMenuClick(int position){
								iv_device_bg.setVisibility(View.GONE);
								et_device_name.setVisibility(View.GONE);
								int fragmentId = FRAGMENT_DAIL;
								switch(position){
												case 0:
																break;
												case 1:
																fragmentId = FRAGMENT_CONTACT;
																break;
												case 2:
																fragmentId = FRAGMENT_DEVICE;
																break;
												default:
																break;
								}

								viewPager.setCurrentItem(fragmentId);

								// 保持当前Fragment的Id
								mCurrentFragment = fragmentId;
								updateCurrenMune(fragmentId);
								if(isShowControlMenu){
												controlButtonRightOnClick();
								}

								if(mCurrentFragment == FRAGMENT_DEVICE){
												// 显示装置底部的背景
												iv_device_bg.setVisibility(View.VISIBLE);
												et_device_name.setVisibility(View.VISIBLE);
												et_device_name.setFocusable(false);
								}
				}

				/**
					* 更新当前选中的菜单图标
					*
					* @param id
					*/
				private void updateCurrenMune(int id){
								int resId = R.drawable.ic_bt_dial_focus;
								switch(id){
												case FRAGMENT_DAIL:{
																break;
												}
												case FRAGMENT_CONTACT:{
																resId = R.drawable.ic_bt_contact_focus;
																break;
												}
												case FRAGMENT_DEVICE:{
																resId = R.drawable.ic_bt_device_focus;
																break;
												}
												default:{
																break;
												}
								}

								menuIconAdapter.notifyChangeSelected(id, resId);
								// 刷新当前标题
								if(id >= 0 && id < menuTitleAdapter.getCount()){
												String titleName = menuTitleAdapter.getItem(id).getTitle();
												if(StringUtil.isNotEmpty(titleName)){
																tv_title.setText(titleName);
												}
								}
				}

				/**
					* 更新菜单选中背景
					*
					* @param iv
					* @param position
					*/
				private void updateMenuIconSelected(ImageView iv, int position){
								if(null != iv){
												switch(position){
																case 0:
																				iv.setImageResource(R.drawable.ic_music_menu_selected_0);
																				break;
																case 1:
																				iv.setImageResource(R.drawable.ic_music_menu_selected_1);
																				break;
																case 2:
																				iv.setImageResource(R.drawable.ic_music_menu_selected_2);
																				break;
																case 3:
																				iv.setImageResource(R.drawable.ic_music_menu_selected_3);
																				break;
																case 4:
																				iv.setImageResource(R.drawable.ic_music_menu_selected_4);
																				break;
																case 5:
																				iv.setImageResource(R.drawable.ic_music_menu_selected_5);
																				break;
																default:
																				break;
												}
												iv.setVisibility(View.VISIBLE);
								}
				}

				// 显示菜单标题弹框界面
				private void showMenuTitlePopupWindow(){
								if(rl_popuwindow.getVisibility() != View.VISIBLE){
												rl_popuwindow.setVisibility(View.VISIBLE);
								}
								menuTitleListViewRequestFocus();
								updateMenuIconSelected(iv_menu_icon_selected, mMenuCurrentPosition);
								mHandler.removeMessages(HIDE_MENU_ICON_MSG);
								sendDelayHideMenuTitleView();
				}

				private void sendDelayHideMenuTitleView(){
								mHandler.removeMessages(HIDE_MENU_TITLE_MSG);
								mHandler.sendEmptyMessageDelayed(HIDE_MENU_TITLE_MSG, DELAY_TIME / 2);
				}

				// 延时发送收回菜单栏View的事件
				private void sendDelayHideMenuIconView(){
								mHandler.removeMessages(HIDE_MENU_ICON_MSG);
								mHandler.sendEmptyMessageDelayed(HIDE_MENU_ICON_MSG, DELAY_TIME / 4);
				}

				private Handler mHandler = new Handler(){
								@Override
								public void handleMessage(Message msg){
												switch(msg.what){
																case CHECK_HAVED_MUSIC:
																				removeMessages(CHECK_HAVED_MUSIC);
																				break;
																case HIDE_MENU_TITLE_MSG:
																				// 隐藏菜单
																				removeMessages(HIDE_MENU_ICON_MSG);
																				if(!isFinishing()){
																								iv_menu_icon_selected.setVisibility(View.INVISIBLE);
																								rl_popuwindow.setVisibility(View.INVISIBLE);
																								menuIconListViewRequestFocus();
																								sendDelayHideMenuIconView();
																				}
																				break;

																case HIDE_MENU_ICON_MSG:
																				controlButtonRightOnClick();
																				break;

																default:
																				break;
												}
								}
				};

				@Override
				public int initContentView(){
								return R.layout.btphone_main_activity;
				}

				@Override
				public void initInjector(){
								mComponent = DaggerBTPhoneMainComponent.builder()
																.applicationComponent(getApplicationComponent())
																.activityModule(getActivityModule())
																.build();
								mComponent.inject(this);
				}

				@Override
				public BTPhoneMainComponent getComponent(){
								return mComponent;
				}

				@Override
				public void initUiAndListener(Bundle savedInstanceState){
								parentFrameLayout.setBackgroundResource(R.drawable.bg_common);
								ButterKnife.bind(this);
								mPresenter.attachView(this);
								EventBus.getDefault().register(this);
								initView();
								initMusicListView();
								mPresenter.initMenuList();
								ObjectAnimator rlMusicLeftAnim = getRLMusicLeftAnimator();
								ObjectAnimator ibControlButtonAnim = getRLControlButtonAnimator();
								ObjectAnimator ibControlButtonRotationAnim = getRLControlButtonRotationAnimator();
								ObjectAnimator ibControlButtonScaleAnim = getRLControlButtonScaleAnimator();

								AnimatorSet animSet = new AnimatorSet();
								animSet.play(rlMusicLeftAnim)
																.with(ibControlButtonAnim)
																.with(ibControlButtonRotationAnim)
																.with(ibControlButtonScaleAnim);
								animSet.start();

								mCurrentFragment = getIntent().getIntExtra("fragment_type", FRAGMENT_DAIL);
								mCallType = getIntent().getStringExtra("call_type");

								initViewPager();
								boolean isNoBindGocService = (null == BTControler.getInstance().getiGocService());
								if(isNoBindGocService){
												// 如果没有绑定GocService，则进行绑定
												mToken = BTControler.getInstance().bindToService(this);
								}
				}

				@Override
				public boolean dispatchKeyEvent(KeyEvent event){
								switch(event.getKeyCode()){
												case KeyEvent.KEYCODE_BACK:
																if(event.getAction() == KeyEvent.ACTION_UP){
																				if(isShowControlMenu){
																								controlButtonRightOnClick();
																								return true;
																				}
																}
																break;
												case KeyEvent.KEYCODE_DPAD_UP:
												case KeyEvent.KEYCODE_DPAD_DOWN:
																if(event.getAction() == KeyEvent.ACTION_UP && isShowControlMenu){
																				showMenuTitlePopupWindow();
																}
																break;
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
																if(isShowControlMenu){
																				if(event.getAction() == KeyEvent.ACTION_DOWN){
																								ib_menu_button_right.setImageResource(R.drawable.ic_control_menu_right_focus);
																				}
																				else if(event.getAction() == KeyEvent.ACTION_UP || event.getAction() == KeyEvent.FLAG_CANCELED){
																								ib_menu_button_right.setImageResource(R.drawable.ic_control_menu_right_normal);
																								controlButtonRightOnClick();
																				}
																}
																return true;
												case KeyEvent.KEYCODE_ENTER:
												case KeyEvent.KEYCODE_DPAD_CENTER:{
																if(isShowControlMenu){
																				if(event.getAction() == KeyEvent.ACTION_DOWN){
																								ib_menu_button_icon.setImageResource(R.drawable.ic_control_menu_fucos);
																				}
																				else if(event.getAction() == KeyEvent.ACTION_UP || event.getAction() == KeyEvent.FLAG_CANCELED){
																								ib_menu_button_icon.setImageResource(R.drawable.ic_control_menu_normal);
																				}
																}
																else{
																				if(event.getAction() == KeyEvent.ACTION_DOWN){
																								ib_menu_button_music.setImageResource(R.drawable.ic_control_menu_fucos);
																				}
																				else if(event.getAction() == KeyEvent.ACTION_UP || event.getAction() == KeyEvent.FLAG_CANCELED){
																								ib_menu_button_music.setImageResource(R.drawable.ic_control_menu_normal);
																				}
																}
																break;
												}
												default:
																break;
								}
								return super.dispatchKeyEvent(event);
				}

				@Override
				public boolean dispatchTouchEvent(MotionEvent ev){
								if(ev.getX() > 700 && ev.getAction() == MotionEvent.ACTION_UP && isShowControlMenu){
												controlButtonRightOnClick();
								}
								return super.dispatchTouchEvent(ev);
				}

				@Override
				public void onResume(){
								super.onResume();
								GPSStateCmd.sendGPSStateCmd(GPSStateCmd.PHONE);
								if(isShowControlMenu){
												menuTitleListViewRequestFocus();
								}
								else{
								}
				}

				@Override
				protected void onDestroy(){
								ListViewUtil.setmFourItemSelectedIndex(-1);
								ListViewUtil.recycleBitmap(iv_music_item_selected);
								ListViewUtil.recycleBitmap(iv_menu_icon_selected);
								if(null != mToken){
												// 如果绑定GocService，则进行解绑
												BTControler.getInstance().unBindFromService(mToken);
								}
								EventBus.getDefault().unregister(this);
								mPresenter.detachView();
								ButterKnife.unbind(this);
								super.onDestroy();
				}
}
