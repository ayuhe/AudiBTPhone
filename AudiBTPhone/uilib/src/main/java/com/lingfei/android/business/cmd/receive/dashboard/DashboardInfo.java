package com.lingfei.android.business.cmd.receive.dashboard;

/**
	* Created by heyu on 2016/9/18.
	*/
public class DashboardInfo{
				private DashboardDataType dataType; // 数据类型
				private float currentSpeed;
				private boolean isWarningSafetyBelt;
				private boolean isWarningHandbrake;
				private boolean isWarningOil;
				private int oilMass;
				private float oilWear;
				private float mileage;
				private float travelMileage;
				private float engineRevolution;
				private int gear;
				private boolean isWarningWaterTemp;
				private int waterTemp;

				public DashboardDataType getDataType(){
								return dataType;
				}

				public void setDataType(DashboardDataType dataType){
								this.dataType = dataType;
				}

				public float getCurrentSpeed(){
								return currentSpeed;
				}

				public void setCurrentSpeed(float currentSpeed){
								this.currentSpeed = currentSpeed;
				}

				public boolean isWarningSafetyBelt(){
								return isWarningSafetyBelt;
				}

				public void setWarningSafetyBelt(boolean warningSafetyBelt){
								isWarningSafetyBelt = warningSafetyBelt;
				}

				public boolean isWarningHandbrake(){
								return isWarningHandbrake;
				}

				public void setWarningHandbrake(boolean warningHandbrake){
								isWarningHandbrake = warningHandbrake;
				}

				public boolean isWarningOil(){
								return isWarningOil;
				}

				public void setWarningOil(boolean warningOil){
								isWarningOil = warningOil;
				}

				public int getOilMass(){
								return oilMass;
				}

				public void setOilMass(int oilMass){
								this.oilMass = oilMass;
				}

				public float getOilWear(){
								return oilWear;
				}

				public void setOilWear(float oilWear){
								this.oilWear = oilWear;
				}

				public float getMileage(){
								return mileage;
				}

				public void setMileage(float mileage){
								this.mileage = mileage;
				}

				public float getTravelMileage(){
								return travelMileage;
				}

				public void setTravelMileage(float travelMileage){
								this.travelMileage = travelMileage;
				}

				public float getEngineRevolution(){
								return engineRevolution;
				}

				public void setEngineRevolution(float engineRevolution){
								this.engineRevolution = engineRevolution;
				}

				public int getGear(){
								return gear;
				}

				public void setGear(int gear){
								this.gear = gear;
				}

				public boolean isWarningWaterTemp(){
								return isWarningWaterTemp;
				}

				public void setWarningWaterTemp(boolean warningWaterTemp){
								isWarningWaterTemp = warningWaterTemp;
				}

				public int getWaterTemp(){
								return waterTemp;
				}

				public void setWaterTemp(int waterTemp){
								this.waterTemp = waterTemp;
				}
}
