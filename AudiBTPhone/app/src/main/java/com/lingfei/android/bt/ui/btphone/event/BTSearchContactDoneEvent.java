package com.lingfei.android.bt.ui.btphone.event;

import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;

import java.util.List;

/**
	* BTSearchContactDoneEvent
	*
	* @author heyu
	* @date 2017/6/24.
	*/
public class BTSearchContactDoneEvent{
				private List<BTPhonePeople> btPhonePeoples;

				public BTSearchContactDoneEvent(List<BTPhonePeople> btPhonePeoples){
								this.btPhonePeoples = btPhonePeoples;
				}

				public List<BTPhonePeople> getBtPhonePeoples(){
								return btPhonePeoples;
				}

				public void setBtPhonePeoples(List<BTPhonePeople> btPhonePeoples){
								this.btPhonePeoples = btPhonePeoples;
				}
}
