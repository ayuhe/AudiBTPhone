package com.lingfei.android.uilib.widget.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;

import com.lingfei.android.uilib.util.BitmapUtil;

/**
	* Created by Administrator on 2016/9/18.
	*/
public abstract class BaseDrawCircleView{
				private Context context;
				//	private Canvas canvas;
				private View parentView;//创建该view实例的父view
				//=======================================================================
				private int centerX;
				private int centerY;
				private float maxValue;
				private float minValue;
				private float curPointerValue;// maxValue>=   >=minValue
				//==========================指针==========================================
				private float minAngle;
				private float startAngle;//起始角  12点钟方向开始顺时针算
				private float curRangeAngle; // 当前幅度角度 >=0  <=maxRangeAngle
				private float maxRangeAngle; // 最大幅度角度 >=0
				private float orientation = 1; // 指针要旋转的方向 正为顺时针,负为逆时针
				private BaseDrawCircleViewParameter parameter;//必要参数
				//===============================bitmap=======================================
				private Bitmap bmpPointer;
				private Bitmap bmpBackground;

				private Paint paintTrajectory;
				//	private Paint paintPointer;
				private RectF rectFTrajectory;

				//=========================================================================
				public BaseDrawCircleView(Context context, View parentView, BaseDrawCircleViewParameter parameter){

								this.context = context;
								this.parentView = parentView;

								this.parameter = parameter;

								initBaseView();
								initBaseData();
				}

				public void initBaseView(){

				}

				public void initBaseData(){
								bmpPointer = BitmapUtil.readBitMap(getContext(), getPointerBitmap());
								bmpBackground = BitmapUtil.readBitMap(getContext(), getBackgroundBitmap());
								initParameter();
				}

				public void initParameter(){
								if(parameter != null){
												this.centerX = parameter.getCenterX();
												this.centerY = parameter.getCenterY();
												this.maxValue = parameter.getMaxValue();
												this.minValue = parameter.getMinValue();
												this.minAngle = parameter.getMinAngle();
												this.startAngle = parameter.getStartAngle();
												this.curRangeAngle = parameter.getCurRangeAngle();
												this.maxRangeAngle = parameter.getMaxRangeAngle();
												this.orientation = parameter.getOrientation();
								}
								checkOrientation();
								checkMaxRangeAngle();
				}

				//==========================================================================
				public void drawPointer(Canvas canvas){
								if(canvas != null && bmpPointer != null){
												canvas.save();
												float rotateAngle = startAngle + (curRangeAngle * orientation) + 90;
												canvas.rotate(rotateAngle, centerX, centerY);

												canvas.drawBitmap(bmpPointer, centerX - bmpPointer.getWidth() / 2,
																				centerY - bmpPointer.getHeight() / 2, null);

												canvas.restore();
								}
				}

				public void drawTrajectory(Canvas canvas){
								paintTrajectory = getPaintTrajectory();
								if(rectFTrajectory == null){
												rectFTrajectory = getRectFTrajectory();
								}
								if(canvas != null && paintTrajectory != null && rectFTrajectory != null){
												float rotateAngle = curRangeAngle * orientation;
												canvas.drawArc(rectFTrajectory, startAngle, rotateAngle, false, paintTrajectory);
								}
				}

				public void drawBackground(Canvas canvas){
								if(canvas != null && bmpBackground != null){
												canvas.drawBitmap(bmpBackground, centerX - bmpBackground.getWidth() / 2,
																				centerY - bmpBackground.getHeight() / 2, null);
								}
				}

				//==========================================================================
				public void startDrawView(Canvas canvas){
								drawBackground(canvas); // 绘制表盘背景
								drawTrajectory(canvas); // 绘制指针扫描轨迹
								//		drawAllMeasure(canvas); // 绘制刻度上的数字
								drawOtherView(canvas);
								drawPointer(canvas); // 绘制指针
				}

				//===============================check ======================================
				private void updateCurRangeAngle(){
								curRangeAngle = ((curPointerValue - minValue) / (maxValue - minValue)) * maxRangeAngle;
								checkCurRangeAngle();
				}

				private void checkOrientation(){
								if(orientation >= 0){
												orientation = 1;
								}
								else{
												orientation = -1;
								}
				}

				private void checkCurRangeAngle(){
								curRangeAngle %= 360;
								if(curRangeAngle > maxRangeAngle){
												curRangeAngle = maxRangeAngle;
								}
								if(curRangeAngle < 0){
												curRangeAngle = 0;
								}
				}

				private void checkMaxRangeAngle(){
								maxRangeAngle %= 360;
								if(maxRangeAngle < 0){
												maxRangeAngle = 0;
								}
				}

				//==============================tools====================================
				protected int dpToPx(int dp){
								if(context != null){
												return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
																				context.getResources().getDisplayMetrics());
								}
								return dp;
				}

				protected float spToPx(int sp){
								if(context != null){
												return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
								}
								return sp;
				}

				//==============================子类继承====================================
				public abstract int getBackgroundBitmap();//设置背景

				public abstract int getPointerBitmap();//设置指针

				public void drawOtherView(Canvas canvas){

				}

				// 设置轨迹画笔
				public Paint getPaintTrajectory(){
								return null;
				}

				// 设置轨迹画笔
				public RectF getRectFTrajectory(){
								return null;
				}

				//==============================外部调用========================================
				public void prepareDrawView(Canvas canvas){
								updateCurRangeAngle();
								startDrawView(canvas);
				}

				public void updateCurrentPointerValue(float pointerValue){
								this.curPointerValue = pointerValue;
				}

				public float getCurPointerValue(){
								return curPointerValue;
				}

				public void recycle(){//释放资源 bitmap  子类需继承
								recycleBitmap(bmpBackground);
								recycleBitmap(bmpPointer);
				}

				public void onDestroyRecycleBitmap(){//activiy destroy 时候调用
								recycle();
								System.gc();
				}

				protected void recycleBitmap(Bitmap bmp){
								if(bmp != null && bmp.isRecycled() == false){
												bmp.recycle();
												bmp = null;
								}
				}
				//=================================get set ======================================

				public Context getContext(){
								return context;
				}

				public void setContext(Context context){
								this.context = context;
				}

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

				public View getParentView(){
								return parentView;
				}

				public int getParentViewWidth(){
								if(getParentView() != null){
												int width = getParentView().getWidth();
												return width;
								}
								return 0;
				}

				public int getParentViewHeight(){
								if(getParentView() != null){
												int height = getParentView().getHeight();
												return height;
								}
								return 0;
				}
}
