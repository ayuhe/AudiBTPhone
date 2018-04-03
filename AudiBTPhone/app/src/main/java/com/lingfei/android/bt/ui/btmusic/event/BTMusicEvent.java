package com.lingfei.android.bt.ui.btmusic.event;

import com.lingfei.android.bt.ui.btmusic.bean.BTMusic;
import com.lingfei.android.bt.ui.btphone.event.BTStatuEvent;
import com.lingfei.android.bt.ui.btphone.util.BTStatu;

/**
	* BTMusicEvent
	*
	* @author heyu
	* @date 2017/7/22.
	*/
public class BTMusicEvent extends BTStatuEvent{

				public BTMusicEvent(BTStatu statu, BTMusic music){
								super(statu, music);
				}
}
