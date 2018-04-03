package com.lingfei.android.bt.ui.btmusic;

import android.os.Bundle;
import android.os.SystemProperties;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.BaseSupportActivity;
import com.lingfei.android.bt.ui.btmusic.bean.MusicMenu;
import com.lingfei.android.bt.ui.btmusic.event.FinishBTMusicEvent;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.bt.ui.util.ListViewUtil;
import com.lingfei.android.business.cmd.send.GPSStateCmd;
import com.lingfei.android.uilib.base.inteface.HasComponent;
import com.lingfei.android.uilib.util.LoggerUtil;
import com.lingfei.android.uilib.util.StringUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
	* BTMusicActivity
	* 蓝牙音乐
	*
	* @author heyu
	* @date 2017/7/22.
	*/
public class BTMusicActivity extends BaseSupportActivity implements
								AdapterView.OnItemClickListener,
								AdapterView.OnItemSelectedListener,
								AbsListView.OnScrollListener,
								BTMusicContract.View,
								HasComponent<BTMusicComponent>{

				@Bind(R.id.listview_content)
				ListView mListview;

				@Bind(R.id.rl_control_button)
				RelativeLayout rlControlButton;

				@Bind(R.id.ib_menu_button)
				ImageButton ib_menu_button;

				@Bind(R.id.ib_menu_button_left)
				ImageButton ib_menu_button_left;

				@Bind(R.id.ib_menu_button_right)
				ImageButton ib_menu_button_right;

				@Bind(R.id.iv_music_item_selected)
				ImageView iv_music_item_selected;

				@Bind(R.id.tv_title)
				TextView tv_title;

				@Inject
				BTMusicPresenter mPresenter;

				@Inject
				BTMusicAdapter mAdapter;

				private BTMusicComponent mComponent = null;

				private boolean isListScroll = false; // 标识音乐列表是否滚动

				private int mFirstVisibleItem = 0; // 记录当前可见的item
				private int mCurItemPosition = 0; // 记录当前选中的item位置

				private BTControler.ServiceToken mToken;

				@OnClick(R.id.ib_menu_button_right)
				public void controlButtonRightOnClick(){
								if(mCurItemPosition >= 0 && mCurItemPosition < mAdapter.getCount()){
												MusicMenu item = mAdapter.getItem(mCurItemPosition);
												if(null != item){
																mPresenter.onClickMenuItem(item);
												}
								}
				}

				@OnClick(R.id.ib_menu_button_left)
				public void controlButtonLeftOnClick(){
								finishActivity();
				}

				private void initListView(){
								mListview.setAdapter(mAdapter);
								mListview.setOnItemClickListener(this);
								mListview.setOnItemSelectedListener(this);
								mListview.setOnScrollListener(this);
				}

				private void onNotifyChangeItemBg(View view){
								if(!isListScroll){
												ListViewUtil.onNotifyChangeSixItemSelectedBg(iv_music_item_selected, view);
								}
				}

				@Override
				public void updateMenuList(List<MusicMenu> items){
								if(null != items){
												mAdapter.setList(items);
								}
				}

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
								mCurItemPosition = position;
								onNotifyChangeItemBg(view);

								if(null != mAdapter){
												mAdapter.setmSelectedPosition(position);
												mAdapter.notifyDataSetChanged();

												MusicMenu item = mAdapter.getItem(position);
												if(null != item){
																mPresenter.onClickMenuItem(item);
												}
												return;
								}
				}

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
								mCurItemPosition = position;
								onNotifyChangeItemBg(view);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){
								mCurItemPosition = -1;
								iv_music_item_selected.setVisibility(View.INVISIBLE);
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
																mListview.setSelection(mFirstVisibleItem);
																break;
												}

												case AbsListView.OnScrollListener.SCROLL_STATE_FLING:{
																// 滚动做出了抛的动作
																break;
												}

												case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:{
																// 正在滚动
																isListScroll = true;
																iv_music_item_selected.setVisibility(View.INVISIBLE);
																break;
												}
								}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
								mFirstVisibleItem = firstVisibleItem;
				}

				@Override
				public int initContentView(){
								return R.layout.btmusic_activity;
				}

				@Override
				public void initInjector(){
								mComponent = DaggerBTMusicComponent.builder()
																.applicationComponent(getApplicationComponent())
																.activityModule(getActivityModule())
																.build();

								mComponent.inject(this);
				}

				@Override
				public BTMusicComponent getComponent(){
								return mComponent;
				}

				@Override
				public void initUiAndListener(Bundle savedInstanceState){
								ButterKnife.bind(this);
								EventBus.getDefault().register(this);
								parentFrameLayout.setBackgroundResource(R.drawable.bg_common);
								mPresenter.attachView(this);
								initListView();
								mPresenter.initMenuList();

								boolean isNoBindGocService = (null == BTControler.getInstance().getiGocService());
								if(isNoBindGocService){
												// 如果没有绑定GocService，则进行绑定
												mToken = BTControler.getInstance().bindToService(this);
								}
								else{
												String isPlayStr = SystemProperties.get("net.btmusice_status", "false");
												LoggerUtil.e("isPlayStr = " + isPlayStr);
												if(StringUtil.isNotEmpty(isPlayStr) && "false".equals(isPlayStr)){
																BTControler.getInstance().setMusicPlayOrPause();
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
				public void onWindowFocusChanged(boolean hasFocus){
								super.onWindowFocusChanged(hasFocus);
								if(hasFocus){
												mListview.setFocusable(true);
												mListview.setFocusableInTouchMode(true);
												mListview.requestFocus();
												mListview.requestFocusFromTouch();
								}
				}

				@Override
				protected void onResume(){
								super.onResume();
								GPSStateCmd.sendGPSStateCmd(GPSStateCmd.MUSIC);
				}

				@Override
				protected void onDestroy(){
								ListViewUtil.setmSexItemSelectedIndex(-1);
								ListViewUtil.recycleBitmap(iv_music_item_selected);
								// 如果绑定GocService，则进行解绑
								BTControler.getInstance().unBindFromService(mToken);
								mPresenter.detachView();
								EventBus.getDefault().unregister(this);
								ButterKnife.unbind(this);
								super.onDestroy();
				}

				@Subscribe(threadMode = ThreadMode.BackgroundThread)
				public void onEventFinishBTMusic(FinishBTMusicEvent event){
								if(null != event){
				         finish();
								}
				}
}
