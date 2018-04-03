package com.lingfei.android.bt.ui.btphone.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.BTPhoneMainActivity;
import com.lingfei.android.bt.ui.btphone.BTPhoneMainComponent;
import com.lingfei.android.bt.ui.btphone.adapter.BTPhonePeopleListAdapter;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;
import com.lingfei.android.bt.ui.btphone.event.BTSearchContactDoneEvent;
import com.lingfei.android.bt.ui.btphone.event.BTSearchContactStartEvent;
import com.lingfei.android.bt.ui.btphone.event.RequestFocusEvent;
import com.lingfei.android.bt.ui.btphone.event.UpdateItemBgEvent;
import com.lingfei.android.bt.ui.btphone.widget.CallOutDialogActivity;
import com.lingfei.android.bt.ui.util.ListViewUtil;
import com.lingfei.android.uilib.base.BaseSupportFragment;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.StringUtil;
import com.lingfei.android.uilib.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
	* ContactFragment
	*
	* @author heyu
	* @date 2017/6/19.
	*/
public class ContactFragment extends BaseSupportFragment implements ContactContract.View,
								AdapterView.OnItemClickListener,
								AdapterView.OnItemSelectedListener,
								AbsListView.OnScrollListener{
				@Bind(R.id.lv_phone_book)
				ListView lv_phone_book;

				@Inject
				ContactPresenter mPresenter;

				BTPhonePeopleListAdapter btListAdapter;

				private int mCurrentFragment; // 记录当前的Fragment

				private boolean isListScroll = false; // 标识列表是否滚动
				private int mFirstVisibleItem = 0; // 记录当前可见的item

				public static ContactFragment newInstance(){
								ContactFragment fragment = new ContactFragment();
								return fragment;
				}

				public void initListView(){
								ArrayList<BTPhonePeople> btPhoneBookList = new ArrayList<>();
								btListAdapter = new BTPhonePeopleListAdapter(getActivity(), btPhoneBookList, BTPhonePeople.Type.Book);
								lv_phone_book.setAdapter(btListAdapter);
								lv_phone_book.setOnItemClickListener(this);
								lv_phone_book.setOnItemSelectedListener(this);
								lv_phone_book.setOnScrollListener(this);
				}

				private void requestFocus(){
								lv_phone_book.setFocusable(true);
								lv_phone_book.requestFocus();
				}

				private void onUpdateItemBg(View view){
								if(!isListScroll){
												int pos = ListViewUtil.getPositionOnClickView(view);
												EventPostUtils.Post(new UpdateItemBgEvent(pos));
								}
				}

				@Subscribe
				public void onEventRequstFocus(RequestFocusEvent event){
								if(null != event){
												mCurrentFragment = event.getFragmentId();
												if(BTPhoneMainActivity.FRAGMENT_CONTACT == event.getFragmentId()){
																mPresenter.initData();
																requestFocus();
												}
								}
				}

				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTSearchContactStart(BTSearchContactStartEvent event){
								if(null != event){
												setLoadingText(R.string.btphone_read_contact);
												showProgress(true);
								}
				}

				@Subscribe(threadMode = ThreadMode.MainThread)
				public void onEventBTSearchContactDone(BTSearchContactDoneEvent event){
								if(null != event){
												List<BTPhonePeople> phonePeoples = event.getBtPhonePeoples();
												updateView(phonePeoples);
								}
				}

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
								if(btListAdapter != null){
												BTPhonePeople phonePeople = btListAdapter.getItem(position);
												String phoneName = phonePeople.getPeopleName();
												String phoneNumber = phonePeople.getPhoneNumber();
												if(StringUtil.isNotEmpty(phoneNumber)){
																onUpdateItemBg(view);

																Intent intent = new Intent(getContext(), CallOutDialogActivity.class);
																intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
																intent.putExtra("phonePeople", phonePeople);
																startActivity(intent);
												}

												ToastUtils.showToastDebug(phoneName);
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
																lv_phone_book.setSelection(mFirstVisibleItem);
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
								}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
								mFirstVisibleItem = firstVisibleItem;
				}

				@Override
				public void updateView(List<BTPhonePeople> phonePeoples){
								showContent(true);
								btListAdapter.setList(phonePeoples);
								if(mCurrentFragment == BTPhoneMainActivity.FRAGMENT_CONTACT){
												requestFocus();
								}
				}

				@Override
				public void initInjector(){
								getComponent(BTPhoneMainComponent.class).inject(this);
				}

				@Override
				public int initContentView(){
								return R.layout.btphone_contact_fragment;
				}

				@Override
				public void getBundle(Bundle bundle){

				}

				@Override
				public void initUI(View view){
								ButterKnife.bind(this, view);
								mPresenter.attachView(this);
								initListView();
								EventBus.getDefault().register(this);
				}

				@Override
				public void initData(){
								mPresenter.initData();
								requestFocus();
				}

				@Override
				public void onDestroyView(){
								super.onDestroyView();
								EventBus.getDefault().unregister(this);
								mPresenter.detachView();
								ButterKnife.unbind(this);
				}
}
