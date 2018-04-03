package com.lingfei.android.bt.ui.btphone;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.injector.PerActivity;
import com.lingfei.android.bt.ui.btphone.bean.MenuBean;
import com.lingfei.android.uilib.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
	* BTPhoneMainPresenter
	*
	* @author heyu
	* @date 2017/6/15.
	*/
@PerActivity
public class BTPhoneMainPresenter implements BTPhoneMainContract.Presenter{
				private int[] menuIconArray = new int[]{
												R.drawable.ic_bt_dial_normal,
												R.drawable.ic_bt_contact_normal,
												R.drawable.ic_bt_device_normal
				};

				private String[] menuTitleArray = ResourceUtils.getStringArray(R.array.bt_phone_array);

				private List<MenuBean> musicMenus;

				private BTPhoneMainActivity mContext;
				private BTPhoneMainContract.View mView;

				@Inject
				public BTPhoneMainPresenter(Activity activity){
								if(activity instanceof BTPhoneMainActivity){
												this.mContext = (BTPhoneMainActivity) activity;
								}
				}

				private void initMusicMenu(){
								musicMenus = new ArrayList<>();
								for(int i = 0; i < menuIconArray.length; i++){
												MenuBean musicMenu = new MenuBean();
												musicMenu.setId(i);
												musicMenu.setIconId(menuIconArray[i]);
												musicMenu.setTitle(menuTitleArray[i]);
												musicMenus.add(musicMenu);
								}
				}

				private List<MenuBean> getMusicMenus(){
								if(null != musicMenus && !musicMenus.isEmpty()){
												return musicMenus;
								}
								else{
												initMusicMenu();
												return musicMenus;
								}
				}

				@Override
				public void initMenuList(){
								if(null != mView){
												mView.updateMenuList(getMusicMenus());
								}
				}

				@Override
				public void attachView(@NonNull BTPhoneMainContract.View view){
								mView = view;
				}

				@Override
				public void detachView(){
								mView = null;
				}

}
