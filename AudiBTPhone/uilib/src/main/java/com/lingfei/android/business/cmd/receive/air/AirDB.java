package com.lingfei.android.business.cmd.receive.air;

import com.lingfei.android.business.bean.air.AirInfo;

/**
	* 保存空调数据
	* Created by heyu on 2016/9/26.
	*/
public class AirDB{

				private static AirDB instance = null;
				private AirInfo airInfo; // 空调数据

				public static AirDB getInstance(){
								if(null == instance){
												instance = new AirDB();
								}
								return instance;
				}

				public AirInfo getAirInfo(){
								return airInfo;
				}

				public void setAirInfo(AirInfo airInfo){
								this.airInfo = airInfo;
				}
}
