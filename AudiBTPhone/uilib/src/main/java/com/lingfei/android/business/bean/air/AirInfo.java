package com.lingfei.android.business.bean.air;

/**
	* Created by Administrator on 2016/8/1.
	*/
public class AirInfo{

				private int ACswitch;//制冷
				private int windMode;//风 模式

				private int windLevel;//风力等级
				private int tempLeft;//空调左边温度
				private int tempRight;//空调左边温度
				private int airSwitch;//空调开关
				private int destination;
				private int isWindModeAuto; // 是否为扫风自动
				private int isWindSpeedAuto; // 是否为风力自动

				static class Destination{
								public static final String MCU_TO_ARM = "AirInfo.ComeFrom.MCU_TO_ARM";
								public static final String ARM_TO_MCU = "AirInfo.ComeFrom.ARM_TO_MCU";
				}

				public AirInfo(){

				}

				//====================================get set =========================================

				public int getACswitch(){
								return ACswitch;
				}

				public void setACswitch(int ACswitch){
								this.ACswitch = ACswitch;
				}

				public int getWindMode(){
								return windMode;
				}

				public void setWindMode(int windMode){
								this.windMode = windMode;
				}

				public int getWindLevel(){
								return windLevel;
				}

				public void setWindLevel(int windLevel){
								this.windLevel = windLevel;
				}

				public int getTempLeft(){
								return tempLeft;
				}

				public void setTempLeft(int tempLeft){
								this.tempLeft = tempLeft;
				}

				public int getTempRight(){
								return tempRight;
				}

				public void setTempRight(int tempRight){
								this.tempRight = tempRight;
				}

				public int getAirSwitch(){
								return airSwitch;
				}

				public void setAirSwitch(int airSwitch){
								this.airSwitch = airSwitch;
				}

				public int getDestination(){
								return destination;
				}

				public void setDestination(int destination){
								this.destination = destination;
				}

				public int getIsWindModeAuto(){
								return isWindModeAuto;
				}

				public void setIsWindModeAuto(int isWindModeAuto){
								this.isWindModeAuto = isWindModeAuto;
				}

				public int getIsWindSpeedAuto(){
								return isWindSpeedAuto;
				}

				public void setIsWindSpeedAuto(int isWindSpeedAuto){
								this.isWindSpeedAuto = isWindSpeedAuto;
				}
}
