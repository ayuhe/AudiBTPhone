package com.lingfei.android.business.cmd.receive.phone;

import android.content.Intent;

import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.util.SysSharePres;

/**
	* Created by Administrator on 2016/10/12.
	*/
public class CarPhonePushIntentService extends BaseCarPhoneIntentService{//原车蓝牙电话推送状态

				//================================================================
				//===========================sharePreference======================================

				//================================================================
				//=========================   暂停其他播放器   ===================
				private static final String SERVICE_ACTION_ANDROID = "com.android.music.musicservicecommand";
				private static final String CMDNAME = "command";
				private static final String CMDPAUSE = "pause";
				private static final String CMDPLAY = "play";
				private static final String IS_PLAYING_MUSIC_WHEN_PHONE = "IS_PLAYING_MUSIC_WHEN_PHONE"; // 当有来电和去电时，是否正在播放音乐，如果正在播放，则在挂断时再恢复播放

				private void playPauseMusic(String command){
								Intent musicBroadcastIntent = new Intent();
								musicBroadcastIntent.setAction(SERVICE_ACTION_ANDROID);
								musicBroadcastIntent.putExtra(CMDNAME, command);
								LibApplication.getContext().sendBroadcast(musicBroadcastIntent);
				}

				/**
					* 是否要暂停/播放音乐
					*/
				private void playPauseMusicOrNot(String cmd){
								if(!SysSharePres.getInstance().getIsPlayingVideo()){
												playPauseMusic(cmd);
								}
				}

				private void pauseMusic(){
								if(isPlayingMusic){
												SysSharePres.getInstance().putBoolean(IS_PLAYING_MUSIC_WHEN_PHONE, true);
												playPauseMusicOrNot(CMDPAUSE);
								}
				}

				//==========================override================================

				@Override
				protected void btStatusOnIncoming(){
								super.btStatusOnIncoming();
								sendCarPhoneWarningEvent(CarPhoneWarningEvent.WarningMode.MODE_WARNING_PHONE_INCOMING, "");
								pauseMusic();
				}

				@Override
				protected void btStatusOnTalking(){
								super.btStatusOnTalking();
								sendCarPhoneWarningEvent(CarPhoneWarningEvent.WarningMode.MODE_WARNING_PHONE_TALKING, "");
								pauseMusic();
				}

				@Override
				protected void btStatusOnHangup(){
								super.btStatusOnHangup();
								sendCarPhoneWarningEvent(CarPhoneWarningEvent.WarningMode.MODE_WARNING_PHONE_HANGUP, "");
								boolean isPlayingMusicWhenPhone = SysSharePres.getInstance().getBoolean(IS_PLAYING_MUSIC_WHEN_PHONE, true);
								if(isPlayingMusicWhenPhone){
												SysSharePres.getInstance().putBoolean(IS_PLAYING_MUSIC_WHEN_PHONE, false);
												playPauseMusicOrNot(CMDPLAY);
								}
				}

				@Override
				protected void btStatusOnCallout(){
								super.btStatusOnCallout();
								sendCarPhoneWarningEvent(CarPhoneWarningEvent.WarningMode.MODE_WARNING_PHONE_CALLOUT, "");
								pauseMusic();
				}
				//================================================================
				//================================================================
}