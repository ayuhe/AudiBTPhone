package com.lingfei.android.bt.ui.btmusic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.injector.PerActivity;
import com.lingfei.android.bt.ui.btmusic.bean.MusicMenu;
import com.lingfei.android.bt.ui.btphone.util.BTControler;
import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
	* Created by heyu on 2017/7/22.
	*/
@PerActivity
public class BTMusicPresenter implements BTMusicContract.Presenter{
				private List<MusicMenu> mMenuItems;

				private Context mContext;
				private BTMusicContract.View mContractView;

				@Inject
				public BTMusicPresenter(Context context){
								this.mContext = context;
				}

				@Override
				public void attachView(@NonNull BTMusicContract.View view){
								mContractView = view;
								playPauseMusic(CMDPAUSE);
								initMediaMenu();
				}

				@Override
				public void detachView(){
								mContractView = null;
				}

				@Override
				public void initMenuList(){
								mContractView.updateMenuList(getmMenuItems());
				}

				private void initMediaMenu(){
								mMenuItems = new ArrayList<>();
								// 应用名称
								String mAppNames[] = ResourceUtils.getStringArray(R.array.bt_music_array);
								for(int i = 0; i < mAppNames.length; i++){
												MusicMenu item = new MusicMenu();
												item.id = i;
												item.name = (mAppNames[i]);
												mMenuItems.add(item);
								}
				}

				private List<MusicMenu> getmMenuItems(){
								if(null == mMenuItems || mMenuItems.isEmpty()){
												initMediaMenu();
								}
								return mMenuItems;
				}

				@Override
				public void onClickMenuItem(MusicMenu item){
								if(null == item){
												return;
								}
								switch(item.id){
												case 0:
																BTControler.getInstance().setMusicPlayOrPause();
																break;
												case 1:
																BTControler.getInstance().setMusicPause();
																break;
												case 2:
																BTControler.getInstance().setMusicStop();
																break;
												case 3:
																BTControler.getInstance().setMusicPrevious();
																break;
												case 4:
																BTControler.getInstance().setMusicNext();
																break;
												default:
																BTControler.getInstance().setMusicPlayOrPause();
																break;
								}
				}

				//=========================   暂停其他播放器   ===================
				private static final String SERVICE_ACTION_ANDROID = "com.android.music.musicservicecommand";
				private static final String CMDNAME = "command";
				private static final String CMDPAUSE = "pause";
				private static final String CMDPLAY = "play";

				private void playPauseMusic(String command){
								Intent musicBroadcastIntent = new Intent();
								musicBroadcastIntent.setAction(SERVICE_ACTION_ANDROID);
								musicBroadcastIntent.putExtra(CMDNAME, command);
								LibApplication.getContext().sendBroadcast(musicBroadcastIntent);
				}

}
