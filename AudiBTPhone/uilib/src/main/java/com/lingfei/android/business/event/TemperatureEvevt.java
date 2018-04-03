package com.lingfei.android.business.event;

/**
	* 温度事件
	* Created by heyu on 2016/9/29.
	*/
public class TemperatureEvevt{
				private String temperature;

				public TemperatureEvevt(String temperature){
								this.temperature = temperature;
				}

				public String getTemperature(){
								return temperature;
				}

				public void setTemperature(String temperature){
								this.temperature = temperature;
				}
}
