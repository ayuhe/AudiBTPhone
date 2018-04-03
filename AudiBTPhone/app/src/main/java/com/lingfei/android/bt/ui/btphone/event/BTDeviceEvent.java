package com.lingfei.android.bt.ui.btphone.event;

import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.bt.ui.btphone.util.BTStatu;

/**
	* BTDeviceEvent
	*
	* @author heyu
	* @date 2017/6/23.
	*/
public class BTDeviceEvent extends BTStatuEvent{
				public BTDeviceEvent(BTStatu statu, BTDevice device){
								super(statu, device);
				}
}
