package com.lingfei.android.bt.ui.btphone.event;

import com.lingfei.android.bt.ui.btmusic.bean.BTMusic;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;
import com.lingfei.android.bt.ui.btphone.util.BTStatu;

/**
	* BTStatuEvent
	* 更新蓝牙状态的事件
	*
	* @author heyu
	* @date 2017/6/22.
	*/
public class BTStatuEvent{
				private BTStatu statu;
				private BTPhonePeople phonePeople;
				private BTDevice device;
				private BTMusic music;

				public BTStatuEvent(){
				}

				public BTStatuEvent(BTStatu statu){
								this.statu = statu;
				}

				public BTStatuEvent(BTStatu statu, BTPhonePeople phonePeople){
								this.statu = statu;
								this.phonePeople = phonePeople;
				}

				public BTStatuEvent(BTStatu statu, BTDevice device){
								this.statu = statu;
								this.device = device;
				}

				public BTStatuEvent(BTStatu statu, BTMusic music){
								this.statu = statu;
								this.music = music;
				}

				public BTStatu getStatu(){
								return statu;
				}

				public void setStatu(BTStatu statu){
								this.statu = statu;
				}

				public BTPhonePeople getPhonePeople(){
								return phonePeople;
				}

				public void setPhonePeople(BTPhonePeople phonePeople){
								this.phonePeople = phonePeople;
				}

				public BTDevice getDevice(){
								return device;
				}

				public void setDevice(BTDevice device){
								this.device = device;
				}

				public BTMusic getMusic(){
								return music;
				}

				public void setMusic(BTMusic music){
								this.music = music;
				}
}
