package com.lingfei.android.uilib.widget.dashboard;

/**
	* Created by Administrator on 2016/9/18.
	*/
public class BaseDrawCircleViewParameter{
				private int centerX;
				private int centerY;
				private float maxValue;
				private float minValue;
				//==========================指针==========================================
				private float minAngle;
				private float startAngle;//起始角
				private float curRangeAngle; // 当前幅度角度 >=0  <=maxRangeAngle
				private float maxRangeAngle; // 最大幅度角度 >=0
				private float orientation = 1; // 指针要旋转的方向 正为顺时针,负为逆时针

				public BaseDrawCircleViewParameter(){

				}

				//======================get set =========================

				public int getCenterX(){
								return centerX;
				}

				public void setCenterX(int centerX){
								this.centerX = centerX;
				}

				public int getCenterY(){
								return centerY;
				}

				public void setCenterY(int centerY){
								this.centerY = centerY;
				}

				public float getMaxValue(){
								return maxValue;
				}

				public void setMaxValue(float maxValue){
								this.maxValue = maxValue;
				}

				public float getMinValue(){
								return minValue;
				}

				public void setMinValue(float minValue){
								this.minValue = minValue;
				}

				public float getMinAngle(){
								return minAngle;
				}

				public void setMinAngle(float minAngle){
								this.minAngle = minAngle;
				}

				public float getStartAngle(){
								return startAngle;
				}

				public void setStartAngle(float startAngle){
								this.startAngle = startAngle;
				}

				public float getCurRangeAngle(){
								return curRangeAngle;
				}

				public void setCurRangeAngle(float curRangeAngle){
								this.curRangeAngle = curRangeAngle;
				}

				public float getMaxRangeAngle(){
								return maxRangeAngle;
				}

				public void setMaxRangeAngle(float maxRangeAngle){
								this.maxRangeAngle = maxRangeAngle;
				}

				public float getOrientation(){
								return orientation;
				}

				public void setOrientation(float orientation){
								this.orientation = orientation;
				}
}
