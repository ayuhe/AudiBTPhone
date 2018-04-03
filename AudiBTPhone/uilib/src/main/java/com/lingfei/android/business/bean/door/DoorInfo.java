package com.lingfei.android.business.bean.door;

/**
	* 车门信息实体类
	* Created by heyu on 2016/8/5.
	*/
public class DoorInfo implements Cloneable{
				public static final String CAR_TYPE = "DoorInfo.CAR_TYPE";
				//===============================================
				private boolean openLeftBefore;// 左前
				private boolean openLeftAfter; // 左后
				private boolean openRightBefore; // 右前
				private boolean openRightAfter; // 右后
				private boolean openHead; // 引擎盖
				private boolean openTail; // 车尾箱
				private int carType;//车型

				public static class CarType{

								public static final int NEW_C = 0;
								public static final int GLC = 1;
				}

				public boolean isOpenLeftBefore() {
								return openLeftBefore;
				}

				public void setOpenLeftBefore(boolean openLeftBefore) {
								this.openLeftBefore = openLeftBefore;
				}

				public boolean isOpenLeftAfter() {
								return openLeftAfter;
				}

				public void setOpenLeftAfter(boolean openLeftAfter) {
								this.openLeftAfter = openLeftAfter;
				}

				public boolean isOpenRightBefore() {
								return openRightBefore;
				}

				public void setOpenRightBefore(boolean openRightBefore) {
								this.openRightBefore = openRightBefore;
				}

				public boolean isOpenRightAfter() {
								return openRightAfter;
				}

				public void setOpenRightAfter(boolean openRightAfter) {
								this.openRightAfter = openRightAfter;
				}

				public boolean isOpenHead() {
								return openHead;
				}

				public void setOpenHead(boolean openHead) {
								this.openHead = openHead;
				}

				public boolean isOpenTail() {
								return openTail;
				}

				public void setOpenTail(boolean openTail) {
								this.openTail = openTail;
				}

				public void setCarType(int carType){
								this.carType = carType;
				}

				public int getCarType(){
								return carType;
				}

				/**
					* 是否车门打开
					*
					* @return
					*/
				public boolean isDoorOpen() {
								if (isOpenLeftBefore() || isOpenLeftAfter() || isOpenRightBefore() || isOpenRightAfter()) {
												return true;
								}
								return false;
				}

				/**
					* 是否引擎盖或者尾箱打开
					*
					* @return
					*/
				public boolean isHeadOrTailOpen() {
								if (isOpenHead() || isOpenTail()) {
												return true;
								}
								return false;
				}

				/**
					* 复位数据
					*/
				public void reset() {
								setOpenLeftBefore(false);
								setOpenLeftAfter(false);
								setOpenRightBefore(false);
								setOpenRightAfter(false);
								setOpenHead(false);
								setOpenTail(false);
				}

				/**
					* 判断两个对象的数据是否相等
					* @param o
					* @return
					*/
				public boolean equals(Object o) {
								if (!(o instanceof DoorInfo)) {
												return false;
								}

								final DoorInfo other = (DoorInfo) o;
								if (isOpenLeftBefore() == other.isOpenLeftBefore() &&
																isOpenLeftAfter() == other.isOpenLeftAfter() &&
																isOpenRightBefore() == other.isOpenRightBefore() &&
																isOpenRightAfter() == other.isOpenRightAfter() &&
																isOpenHead() == other.isOpenHead() &&
																isOpenTail() == other.isOpenTail()) {
												return true;
								} else {
												return false;
								}
				}

				public DoorInfo clone() {
								DoorInfo info = new DoorInfo();
								info.setOpenLeftBefore(isOpenLeftBefore());
								info.setOpenLeftAfter(isOpenLeftAfter());
								info.setOpenRightBefore(isOpenRightBefore());
								info.setOpenRightAfter(isOpenRightAfter());
								info.setOpenHead(isOpenHead());
								info.setOpenTail(isOpenTail());
								return info;
				}
}
