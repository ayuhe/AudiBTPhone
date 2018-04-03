package com.lingfei.android.business.event;

import com.lingfei.android.business.bean.air.AirInfo;

/**
	* Created by heyu on 2016/9/26.
	*/
public class AirInfoEvent{
				private AirInfo airInfo; // 空调数据

				public AirInfoEvent(AirInfo airInfo){
								this.airInfo = airInfo;
				}

				public AirInfo getAirInfo(){
								return airInfo;
				}

				public void setAirInfo(AirInfo airInfo){
								this.airInfo = airInfo;
				}
}
