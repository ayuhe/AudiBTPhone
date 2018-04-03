package com.lingfei.android.uilib.widget.dashboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lingfei.android.uilib.R;
import com.lingfei.android.uilib.util.BitmapUtil;

/**
	* 仪表盘控件父类，需要在子类里实现其抽象方法
	* 设置属性说明：
	* （1）bmpBackground 背景图
	* （2）bmpSpeedPointer 指针
	* （3）mStartAngle 开始角度
	* （4）mMaxAngle 最大角度（需要旋转的最大角度范围）
	* （5）mMinValue 最小值（表盘上显示的最小值）
	* （6）mMaxValue 最大值（表盘上显示的最大值）
	* （7）mBigSliceCount 刻度数（表盘上显示多少刻度）
	* （8）mPointerInitAngle 指针初始指向的角度（用于指针旋转校验）
	* （9）mDailScaleRadius 刻度半径（刻度在表盘上显示的位置）
	* （10）mFontRadius 刻度数字的半径（刻度数字在表盘上显示的位置）
	* <p/>
	* 设置画笔：
	* （1）绘制刻度字体正常显示的画笔
	* （2）绘制刻度字体变大显示的画笔
	* （3）绘制指针扫描区域的画笔
	* （4）指针扫描区域的矩形
	*/
public abstract class BaseDashboardView extends SurfaceView{
				private Bitmap bmpSpeedPointer; // 仪表指针图片
				private Bitmap bmpBackground; // 仪表背景图
				private SurfaceHolder surfaceHolder;
				private MainDrawThread mainDrawThread;

				//=================================
				private int mRadius; // 圆弧半径
				private float mStartAngle; // 指针 0刻度 起始角度(3点钟方向为0 顺时针)
				private float mMaxAngle; // 指针 最大刻度 与0刻度相差的角度
				private int mBigSliceCount; // 刻度数
				private int mMinValue; // 刻度数最小值
				private int mMaxValue; // 刻度数最大值
				private float mCurPointerValue = 0;// 当前指针值
				private int mFontRadius;// 数字刻度圈半径
				private int mDailScaleRadius; // 刻度半径
				private int mPointerInitAngle = 150; // 指针初始指向的角度

				private float mDialScaleAngle; // 刻度等分角度
				private String[] mGraduations; // 等分的刻度值

				//================数字 刻度 信息===============

				//==============  画笔  当画笔为空 不执行画笔功能===========
				private Paint mPaintArc; // 刻度画笔
				private Paint mPaintNormalFont; // 数字画笔
				private Paint mPaintChangeFont; // 数字画笔
				private Paint mPaintPointer; // 指针画笔
				private Paint mPaintTrajectory;// 轨迹画笔
				private RectF mRectFTrajectory;// 轨迹区域

				//===============指针 ===============

				private float orientation = 1; // 指针要旋转的角度
				private float mRotateAngle = 0; // 指针要旋转的角度
				private float mSweepAngle = 0; // 轨迹扫描的角度

				//=============== 当前信息 动态更新===============
				private float mCurrentAngle; // 角度幅度

				//=================================
				public BaseDashboardView(Context context, AttributeSet attrs){
								this(context, attrs, 0);
				}

				public BaseDashboardView(Context context, AttributeSet attrs, int defStyle){
								super(context, attrs, defStyle);
								initTypedArray(context, attrs, defStyle);
								initViewData();
								initSizes();
				}

				private void initViewData(){
								surfaceHolder = getHolder();
								surfaceHolder.addCallback(sfhCallback);
								setZOrderOnTop(true);
								surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
								//====================

								bmpSpeedPointer = BitmapUtil.readBitMap(getContext(), getSpeedPointerBitmap());
								bmpBackground = BitmapUtil.readBitMap(getContext(), getBackgroundBitmap());

								// 绘制指针画笔
								mPaintPointer = new Paint();
								mPaintPointer.setAntiAlias(true);

								// 绘制刻度画笔
								mPaintArc = getPaintArc();

								// 绘制正常显示字体的画笔
								mPaintNormalFont = getPaintFontNormal();

								// 绘制变化字体的画笔
								mPaintChangeFont = getPaintFontChange();

								//=====指针 轨迹 方向
								initOrientation();
				}

				public void initTypedArray(Context context, AttributeSet attrs, int defStyle){
								TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashboardView, defStyle, 0);

								mRadius = a.getDimensionPixelSize(R.styleable.DashboardView_radius, dpToPx(80));
								mStartAngle = a.getFloat(R.styleable.DashboardView_startAngle, 180);
								mMaxAngle = a.getFloat(R.styleable.DashboardView_maxAngle, 180f);
								mBigSliceCount = a.getInteger(R.styleable.DashboardView_bigSliceCount, 10);
								mMinValue = a.getInteger(R.styleable.DashboardView_minValue, 0);
								mMaxValue = a.getInteger(R.styleable.DashboardView_maxValue, 100);
								mCurPointerValue = a.getFloat(R.styleable.DashboardView_currentValue, mMinValue);
								mFontRadius = a.getInteger(R.styleable.DashboardView_fontRadius, 90);
								mDailScaleRadius = a.getInteger(R.styleable.DashboardView_dailScaleRadius, 100);
								mPointerInitAngle = a.getInteger(R.styleable.DashboardView_pointerInitAngle, 270);
								orientation = a.getInteger(R.styleable.DashboardView_orientation, 1);
								a.recycle();
				}

				/**
					* 初始化部分数值的大小
					*/
				public void initSizes(){
								// 大刻度等分角度
								mDialScaleAngle = mMaxAngle / (float) mBigSliceCount;
								// 计算刻度盘上的数字
								mGraduations = getMeasureNumbers();

								mCurrentAngle = getAngleFromResult(mCurPointerValue);

								mRotateAngle = mCurrentAngle - mPointerInitAngle;
				}

				//===================================
				class MainDrawThread extends Thread{
								SurfaceHolder sfHolder;

								public MainDrawThread(SurfaceHolder surfaceHolder){
												sfHolder = surfaceHolder;
								}

								//--------------------------
								@Override
								public void run(){
												prepareDrawView();
								}
				}

				private void prepareDrawView(){
								if(surfaceHolder != null){
												Canvas canvas = surfaceHolder.lockCanvas();
												if(canvas != null){
																cleanBuffer(canvas); // 擦除画布
																canvas = canvasAddANTI(canvas);
																startDrawView(canvas);
												}
												unlockCanvas(surfaceHolder, canvas); // 显示绘制好的图像
								}
				}

				public void startDrawView(Canvas canvas){
								drawBackground(canvas); // 绘制表盘背景
								drawTrajectory(canvas); // 绘制指针扫描轨迹
								drawAllMeasure(canvas); // 绘制刻度上的数字
								drawOtherView(canvas);
								drawPointer(canvas); // 绘制指针
				}

				//================  Draw main  =============

				/**
					* 根据旋转角度绘制指针
					*
					* @param canvas
					*/
				public void drawPointer(Canvas canvas){
								if(canvas != null && bmpSpeedPointer != null){
												canvas.save();
												canvas.rotate(mRotateAngle, getWidth() / 2, getHeight() / 2);

										/*		Matrix matrix = new Matrix();
												Bitmap bitmap = Bitmap.createBitmap(bmpSpeedPointer, 0, 0, bmpSpeedPointer.getWidth(),
																				bmpSpeedPointer.getHeight(), matrix, true);*/
												//		Bitmap bitmap = bmpSpeedPointer;
												//	canvas = canvasAddANTI ( canvas );
												canvas.drawBitmap(bmpSpeedPointer, getWidth() / 2 - bmpSpeedPointer.getWidth() / 2,
																				getHeight() / 2 - bmpSpeedPointer.getHeight() / 2, mPaintPointer);

												canvas.restore();
								}
				}

				/*
					*  画轨迹
					*/
				public void drawTrajectory(Canvas canvas){
								//	if ( mPaintTrajectory == null ) {
								mPaintTrajectory = getPaintTrajectory();
								//	}
								if(mRectFTrajectory == null){
												mRectFTrajectory = getRectFTrajectory();
								}
								if(canvas != null && mPaintTrajectory != null && mRectFTrajectory != null){
												//	canvas = canvasAddANTI ( canvas );
												canvas.drawArc(mRectFTrajectory, mStartAngle, mSweepAngle, false, mPaintTrajectory);
								}
				}

				/*
					* 画背景
					*/
				public void drawBackground(Canvas canvas){
								if(canvas != null && bmpBackground != null){
												//	canvas = canvasAddANTI ( canvas );
												canvas.drawBitmap(bmpBackground, getWidth() / 2 - bmpBackground.getWidth() / 2,
																				getHeight() / 2 - bmpBackground.getHeight() / 2, null);
								}
				}

				//================   画字   =============

				/**
					* 计算刻度数组
					* 根据最小值、最大值、刻度数，计算出刻度数组
					*
					* @return
					*/
				private String[] getMeasureNumbers(){
								String[] strings = new String[mBigSliceCount + 1];
								for(int i = 0; i <= mBigSliceCount; i++){
												if(i == 0){
																strings[i] = String.valueOf(mMinValue);
												}
												else if(i == mBigSliceCount){
																strings[i] = String.valueOf(mMaxValue);
												}
												else{
																strings[i] = String.valueOf(((mMaxValue - mMinValue) / mBigSliceCount) * i);
												}
								}

								return strings;
				}

				/**
					* 绘制刻度盘及刻度上的字
					*/
				private void drawAllMeasure(Canvas canvas){
								if(canvas != null){
												drawMeasure(canvas);
												drawMeasureNumber(canvas);
								}
				}

				/**
					* 画刻度
					*
					* @param canvas
					*/
				private void drawMeasure(Canvas canvas){
								if(canvas != null && null != mPaintArc){
												for(int i = 0; i <= mBigSliceCount; i++){
																// 绘制大刻度
																float angle = i * mDialScaleAngle + mStartAngle;

																float[] point1 = getCoordinatePoint(mDailScaleRadius, angle);
																float[] point2 = getCoordinatePoint(mDailScaleRadius - 8, angle);

																// 绘制刻度
																canvas.drawLine(point1[0], point1[1], point2[0], point2[1], mPaintArc);
												}
								}
				}

				/**
					* 刻度数 这里实现画数字 使用子类定制的 大和小画笔 父类尽量不要修改子类画笔
					*
					* @param canvas
					*/
				private void drawMeasureNumber(Canvas canvas){
								if(canvas != null && mPaintNormalFont != null && mPaintChangeFont != null){
												for(int i = 0; i <= mBigSliceCount; i++){
																// 获取刻度位置
																float angle = i * mDialScaleAngle + mStartAngle;
																float[] point1 = getCoordinatePoint(mDailScaleRadius, angle);
																// 得到指针当前所在的位置
																float[] mCurrentPointer = getCoordinatePoint(mDailScaleRadius, mCurrentAngle);

																// 绘制数字的画笔
																Paint paintFont = mPaintNormalFont;

																// 计算指针当前位置与刻度的距离
																int dis = (int) calculationTwoPointDistance(mCurrentPointer, point1);
																if(dis < 30){ // 如果指针与刻度的距离小于设定值，则显示大字
																				paintFont = mPaintChangeFont;
																}

																// 得到当前要绘制的数字
																String number = mGraduations[i];

																// 获取字体的宽高
																Rect rectMeasures = new Rect();
																paintFont.getTextBounds(number, 0, number.length(), rectMeasures);
																float[] numberPoint = getNumberPoint(angle, paintFont, rectMeasures);

																// 绘制圆盘上的数字
																if(i == 0){
																				canvas.drawText(number, numberPoint[0] + (rectMeasures.width() / 2), numberPoint[1] + (rectMeasures.height() / 2),
																												paintFont);
																}
																else if(i == mBigSliceCount){
																				canvas.drawText(number, numberPoint[0] - 5, numberPoint[1] + (rectMeasures.height() / 2),
																												paintFont);
																}
																else{
																				canvas.drawText(number, numberPoint[0], numberPoint[1] + rectMeasures.height(),
																												paintFont);
																}
												}
								}
				}

				/**
					* 计算圆盘上数字显示的位置（x, y）
					*
					* @param angle
					* @param paintFont
					* @return
					*/
				private float[] getNumberPoint(float angle, Paint paintFont, Rect rectMeasures){
								float[] numberPoint = getCoordinatePoint(mFontRadius, angle);
								if(angle % 360 > 135 && angle % 360 < 225){
												paintFont.setTextAlign(Paint.Align.LEFT);
												numberPoint[0] -= 5;
												numberPoint[1] -= rectMeasures.height() / 2;
								}
								else if((angle % 360 >= 0 && angle % 360 < 45) || (angle % 360 > 310 && angle % 360 <= 360)){
												paintFont.setTextAlign(Paint.Align.RIGHT);
												numberPoint[0] += 2;
												numberPoint[1] -= rectMeasures.height() / 2;
								}
								else{
												paintFont.setTextAlign(Paint.Align.CENTER);
												numberPoint[0] -= 3;
												numberPoint[1] -= 2;
								}
								return numberPoint;
				}

				/**
					* 依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
					*/
				public float[] getCoordinatePoint(int radius, float cirAngle){
								float[] point = new float[2];
								float mCenterX = getWidth() * 0.5f; // 中心点X
								float mCenterY = getHeight() * 0.5f; // 中心点Y
								double arcAngle = Math.toRadians(cirAngle); //将角度转换为弧度
								if(cirAngle < 90){
												point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
												point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
								}
								else if(cirAngle == 90){
												point[0] = mCenterX;
												point[1] = mCenterY + radius;
								}
								else if(cirAngle > 90 && cirAngle < 180){
												arcAngle = Math.PI * (180 - cirAngle) / 180.0;
												point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
												point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
								}
								else if(cirAngle == 180){
												point[0] = mCenterX - radius;
												point[1] = mCenterY;
								}
								else if(cirAngle > 180 && cirAngle < 270){
												arcAngle = Math.PI * (cirAngle - 180) / 180.0;
												point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
												point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
								}
								else if(cirAngle == 270){
												point[0] = mCenterX;
												point[1] = mCenterY - radius;
								}
								else{
												arcAngle = Math.PI * (360 - cirAngle) / 180.0;
												point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
												point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
								}

								return point;
				}

				/**
					* 更新指针当前旋转的角度
					*/
				private void updateRotateAngle(float curSpeed){
								mCurrentAngle = getAngleFromResult(curSpeed);
								// 起始角度和初始角度的差值
								float tempAngle = mStartAngle - mPointerInitAngle;
								// 当前角度减去起始角度得到指针要旋转的角度
								mRotateAngle = ((float) mCurrentAngle - mStartAngle + tempAngle);
								if(mRotateAngle > 360){
												mRotateAngle = mRotateAngle % 360;
								}

								// 校准指针扫描的角度
								if(0 != tempAngle){
												mSweepAngle = mRotateAngle - tempAngle;
								}
								else{
												mSweepAngle = mRotateAngle;
								}

								mSweepAngle *= orientation;
				}

				/**
					* 通过数值得到角度位置
					*
					* @param result
					* @return
					*/
				private float getAngleFromResult(float result){
								if(result > mMaxValue){
												result = mMaxValue;
								}

								if(result < mMinValue){
												result = mMinValue;
								}
								return mMaxAngle * ((result - mMinValue) / (mMaxValue - mMinValue)) + mStartAngle;
				}

				/**
					* 计算两点间距离
					*
					* @param point1
					* @param point2
					* @return
					*/
				private double calculationTwoPointDistance(float[] point1, float[] point2){
								double temp = Math.sqrt(
																(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2)));
								return Math.abs(temp);
				}

				//===================================
				SurfaceHolder.Callback sfhCallback = new SurfaceHolder.Callback(){

								@Override
								public void surfaceCreated(SurfaceHolder holder){
												mainDrawThread = new MainDrawThread(surfaceHolder);
												mainDrawThread.start();
								}

								@Override
								public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

								}

								@Override
								public void surfaceDestroyed(SurfaceHolder holder){
												//		destroyRecycleBitmap();
								}
				};

				//===================================
				private void cleanBuffer(Canvas canvas){ //重画时需要清空
								if(null != canvas){
												canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
								}
				}

				private void unlockCanvas(SurfaceHolder surfaceHolder, Canvas canvas){ //解锁画布
								if(canvas != null && surfaceHolder != null){
												// 结束锁定
												surfaceHolder.unlockCanvasAndPost(canvas);
								}
				}

				public Canvas canvasAddANTI(Canvas canvas){ //图片抗锯齿
								PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
								canvas.setDrawFilter(pfd);
								return canvas;
				}

				protected int dpToPx(int dp){
								return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
																getResources().getDisplayMetrics());
				}

				protected float spToPx(int sp){
								return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
				}

				protected void initOrientation(){
								if(orientation >= 0){
												orientation = 1;
								}
								else{
												orientation = -1;
								}
				}
				//=================外部调用=============

				/**
					* 更新时速
					*/
				public void updateCurrentPointerValue(float curValue){
								setCurPointerValue(curValue);
								reDraw();
				}

				public void reDraw(){ //重绘所有view
								if(mainDrawThread == null){
												mainDrawThread = new MainDrawThread(surfaceHolder);
								}
								if(!mainDrawThread.isInterrupted()){
												mainDrawThread.start();
								}
				}

				//==============  继承实现 ==================
				public abstract int getBackgroundBitmap();//设置背景

				public abstract int getSpeedPointerBitmap();//设置指针

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

				// 设置正常字体画笔(颜色 字体库 大小 斜体 等等)
				public Paint getPaintFontNormal(){
								return null;
				}

				// 设置变化后字体画笔(默认和正常字体一样,可选择重写)
				public Paint getPaintFontChange(){
								return getPaintFontNormal();
				}

				// 绘制刻度画笔
				public Paint getPaintArc(){
								return null;
				}

				public void recycle(){//释放资源 bitmap  子类需继承
								recycleBitmap(bmpBackground);
								recycleBitmap(bmpSpeedPointer);
				}

				public void destroyRecycleBitmap(){//activiy destroy 时候调用
								recycle();
								System.gc();
				}

				protected void recycleBitmap(Bitmap bmp){
								if(bmp != null && bmp.isRecycled() == false){
												bmp.recycle();
												bmp = null;
								}
				}

				//==============get set ================
				private void setCurPointerValue(float curValue){
								this.mCurPointerValue = curValue;
								updateRotateAngle(curValue); // 更新当前角度
				}

				public float getCurPointerValue(){
								return mCurPointerValue;
				}
}
