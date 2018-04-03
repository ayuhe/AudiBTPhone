package com.lingfei.android.bt.ui.btphone.event;

/**
	* RequestFocusEvent
	*
	* @author heyu
	* @date 2017/6/20.
	*/
public class RequestFocusEvent{
				private int fragmentId;

				public RequestFocusEvent(int fragmentId){
								this.fragmentId = fragmentId;
				}

				public int getFragmentId(){
								return fragmentId;
				}

				public void setFragmentId(int fragmentId){
								this.fragmentId = fragmentId;
				}
}
