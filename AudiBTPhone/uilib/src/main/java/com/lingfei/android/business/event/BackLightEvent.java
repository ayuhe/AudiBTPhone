package com.lingfei.android.business.event;

/**
	* 背光开关
	* Created by heyu on 2017/2/5.
	*/
public class BackLightEvent{
				private boolean isBackLight;

				public BackLightEvent(boolean isBackLight){
								this.isBackLight = isBackLight;
				}

				public boolean isBackLight(){
								return isBackLight;
				}

				public void setBackLight(boolean backLight){
								isBackLight = backLight;
				}
}
