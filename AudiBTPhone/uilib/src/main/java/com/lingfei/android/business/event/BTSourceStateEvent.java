package com.lingfei.android.business.event;

/**
	* Created by heyu on 2016/12/2.
	*/
public class BTSourceStateEvent{
				private int state;

				public BTSourceStateEvent(int state){
								this.state = state;
				}

				public int getState(){
								return state;
				}

				public void setState(int state){
								this.state = state;
				}
}
