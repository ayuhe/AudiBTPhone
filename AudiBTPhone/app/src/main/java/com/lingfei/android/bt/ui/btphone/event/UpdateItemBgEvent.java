package com.lingfei.android.bt.ui.btphone.event;

/**
	* UpdateItemBgEvent
	*
	* @author heyu
	* @date 2017/6/19.
	*/
public class UpdateItemBgEvent{
				private int position;

				public UpdateItemBgEvent(int position){
								this.position = position;
				}

				public int getPosition(){
								return position;
				}

				public void setPosition(int position){
								this.position = position;
				}
}
