package com.lingfei.android.uilib.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * 媒体页面中旋转菜单空间，类似椭圆旋转菜单空间
 * 使用：在布局里引用，把菜单子控件包含在该控件里面
 * Created by heyu on 2016/7/8.
 */
public class OvalMenuViewGroup extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "OvalMenuViewGroup";

    //上半弧度最大值,可以根据菜单数目自由调整，180度除以X
    private static final float TOTAL_ANGLE = (float) (Math.PI / 8);
    //动画持续时长
    private static final int ANIMATION_DURING = 300;
    //最小放大倍数
    private static final float MIN = 0.5f;
    //最大放大倍数
    private static final float MAX = 1.5f;
    //转动方向
    private boolean isClockWise = true;
    //上半部分的子view的偏移平衡位置的角度
    private float angleOffset = 0;
    //上半部分3个view基础偏移
    private float baseTopAngleOffset = (float) (Math.PI / 2 - TOTAL_ANGLE / 2);
    //下半部分子view的基础偏移
    private float baseBottomAngleOffset = (float) (-Math.PI / 2 - TOTAL_ANGLE / 2);
    //记录上半部分子view中间的子view的index
    private int topMiddleIndex = -1;
    //记录上半部分子view左边的子view的index
    private int topLeftIndex;
    //记录上半部分子view右边的子view的index
    private int topRightIndex;
    //当前选中的子view的index
    private int curSelectChildViewIndex;
    //viewgroup的动画
    private ValueAnimator valueAnimator;
    //点击子view的时间监听
    private OnChildClickListener onChildClickListener;
    //切换选中子view的事件监听
    private OnZoomChildChangedListener onZoomChildChangedListener;
    //记录touch事件Down时间的x坐标
    private int downX;
    //记录touch事件Down时间的y坐标
    private int downY;
    //是否是第一次布局
    private boolean isFirstLayout = true;
    //画椭圆弧的画笔
    private Paint paint;
    //椭圆弧背景
    private RectF ovalRectF;

    //是否Debug，是则绘制椭圆弧
    private boolean isDebug = false;

    public OvalMenuViewGroup(Context context) {
        this(context, null, 0);
    }

    public OvalMenuViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OvalMenuViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isDebug) {
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
        }
    }

    public void setOnZoomChildChangedListener(OnZoomChildChangedListener onZoomChildChangedListener) {
        this.onZoomChildChangedListener = onZoomChildChangedListener;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() < 2) {
            throw new IllegalArgumentException("布局不支持少于三个子view");
        }

        // groupView控件的padding值
        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();
        int right = r - getPaddingRight();
        int bottom = b - getPaddingBottom();

        int displayWidth = right - left; // 显示的宽度
        int displayHeight = bottom - top; // 显示的高度
        int centX = getPaddingLeft() + displayWidth / 2; // 中心点的X坐标
        int centY = getPaddingTop() + displayHeight / 2; // 中心店的Y坐标
        int ovalh = displayWidth / 2;  // 短轴的一般
        int ovalv = displayHeight / 2; // 长轴的一般

        float radius = (float) (ovalh / Math.sin(TOTAL_ANGLE / 2));
        int topCentY = (int) (centY - ovalh / Math.tan(TOTAL_ANGLE / 2));
        int bottomCentY = (int) (centY + ovalh / Math.tan(TOTAL_ANGLE / 2));

        if (topMiddleIndex == -1) {
            topLeftIndex = 2;
            topMiddleIndex = 1;
            topRightIndex = 0;
        }

        if (isDebug) {
            ovalRectF = new RectF((int) (centX - ovalh * 0.6), (int) (centY - ovalv * 0.35), (int) (centX + ovalh * 0.6), (int) (centY + ovalv * 0.35));
        }

        layoutChildrenAtAngle(centX, topCentY, bottomCentY, radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDebug) {
            if (ovalRectF != null) {
                canvas.drawArc(ovalRectF, 120, 300, false, paint);
            }
        }
        super.onDraw(canvas);
    }

    /**
     * @param centX
     * @param topCentY
     * @param bottomCentY
     * @param radius
     */
    private void layoutChildrenAtAngle(int centX, int topCentY, int bottomCentY, float radius) {
        if (isFirstLayout) { // 第一次需要调整布局
            curSelectChildViewIndex = 2;
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).startAnimation(buildAnimation(i, true, 1));
            }
            curSelectChildViewIndex = 1;
            isFirstLayout = false;
        }

        float baseTopIncrease = TOTAL_ANGLE / 2; // 度角
        float baseBottomIncrease = TOTAL_ANGLE / (getChildCount() - 2);

        float topOffsetOfChild = baseTopAngleOffset + angleOffset - baseTopIncrease;
        float bottomOffsetOfChild = baseBottomAngleOffset + angleOffset * 2 / (getChildCount() - 2) - baseBottomIncrease;

        int childCenterX;
        int childCenterY;
        int curIndex = (topMiddleIndex - 1 + getChildCount()) % getChildCount();
        if (!isClockWise) {
            topOffsetOfChild += baseTopIncrease;
            bottomOffsetOfChild += baseBottomIncrease;
            curIndex = (curIndex + 1) % getChildCount();
        }
//        Log.v(TAG, "angleOffset" + angleOffset);
//        Log.v(TAG, "clockWise" + isClockWise);
//        Log.v(TAG, "curIndex" + curIndex);

        for (int i = 0; i < getChildCount(); i++) {
            if (ckeckIsTop(isClockWise, curIndex)) {
                topOffsetOfChild += baseTopIncrease;
                childCenterX = polarToX(radius, topOffsetOfChild);
                childCenterY = polarToY(radius, topOffsetOfChild);
                layoutFromCenter(getChildAt(curIndex), centX + childCenterX, bottomCentY - childCenterY);
            } else {
                bottomOffsetOfChild += baseBottomIncrease;
                childCenterX = polarToX(radius, bottomOffsetOfChild);
                childCenterY = polarToY(radius, bottomOffsetOfChild);
                layoutFromCenter(getChildAt(curIndex), centX + childCenterX, topCentY - childCenterY);
            }

            if (curIndex == curSelectChildViewIndex) {
                getChildAt(curIndex).setOnClickListener(this);
            } else {
                getChildAt(curIndex).setClickable(false);
            }

            curIndex = (curIndex + 1) % getChildCount();
        }
    }

    // 是否是转动角度大的view
    private boolean ckeckIsTop(boolean clockWise, int curIndex) {
        return (clockWise && (curIndex == topRightIndex || curIndex == topMiddleIndex))
                || (!clockWise && (curIndex == topLeftIndex || curIndex == topMiddleIndex));
    }

    /**
     * 计算子view布局位置的中心点x坐标
     *
     * @param r     圆的半径
     * @param angle 顺时针旋转弧度
     * @return
     */
    private int polarToX(float r, float angle) {
        return (int) (r * Math.cos(angle));
    }

    /**
     * 计算子view布局位置的中心点y坐标
     *
     * @param r     圆的半径
     * @param angle 顺时针旋转弧度
     * @return
     */
    private int polarToY(float r, float angle) {
        return (int) (r * Math.sin(angle));
    }

    /**
     * 将子view以其中心点位置进行布局，如果超出边界，已边界为准
     *
     * @param view 子view
     * @param cx   子view的中心点位置x坐标
     * @param cy   子view的中心点位置y坐标
     */
    private void layoutFromCenter(View view, int cx, int cy) {
        int leftEdge = getPaddingLeft();
        int topEdge = getPaddingTop();
        int rightEdge = getWidth() - getPaddingRight();
        int bottomEdge = getHeight() - getPaddingBottom();

        int left = cx - view.getMeasuredWidth() / 2;
        int top = cy - view.getMeasuredHeight() / 2;
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (left < leftEdge) {
            right += leftEdge - left;
            left = leftEdge;
        }

        if (right > rightEdge) {
            left -= right - rightEdge;
            right = rightEdge;
        }

        if (top < topEdge) {
            bottom += topEdge - top;
            top = topEdge;
        }

        if (bottom > bottomEdge) {
            top -= bottom - bottomEdge;
            bottom = bottomEdge;
        }

        view.layout(left, top - 10, right + 10, bottom);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            if (v == getChildAt(i)) {
                if ((null == valueAnimator) || (null != valueAnimator && !valueAnimator.isRunning())) {
                    if (onChildClickListener != null) {
                        onChildClickListener.onChildClick(v, i);
                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                super.onTouchEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int distance = (int) Math.pow(Math.pow(event.getX() - downX, 2) + Math.pow(event.getY() - downY, 2), 0.5);
                if (distance < ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()))) {
                    updateChildViewPos(event.getX() > getWidth() / 2, ANIMATION_DURING);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 更新子view的位置
     *
     * @param clockWise 旋转方向
     * @param time      旋转时间
     */
    private void updateChildViewPos(boolean clockWise, long time) {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat();
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    angleOffset = (float) animation.getAnimatedValue();
                    requestLayout();
                }
            });
        }

        if (!valueAnimator.isRunning()) {
            isClockWise = clockWise;

            updateTopIndex();

            startValueAnimation(clockWise, time);

            updateSelectIndex(clockWise);
        }
    }

    /**
     * 更新上半部分子views的index
     */
    private void updateTopIndex() {
        topLeftIndex = (curSelectChildViewIndex + 1) % getChildCount();
        topMiddleIndex = curSelectChildViewIndex;
        topRightIndex = (curSelectChildViewIndex - 1 + getChildCount()) % getChildCount();
    }


    /**
     * 开始属性动画
     *
     * @param clockWise 是否是顺时针转动
     * @param time      动画时长
     */
    private void startValueAnimation(boolean clockWise, long time) {
        if (clockWise) {
            valueAnimator.setFloatValues(0, TOTAL_ANGLE / 2);
        } else {
            valueAnimator.setFloatValues(0, -TOTAL_ANGLE / 2);
        }
        valueAnimator.setDuration(time);
        valueAnimator.start();
    }

    /**
     * 更新选中菜单的index
     *
     * @param clockWise 是否是顺时针转动
     */
    private void updateSelectIndex(boolean clockWise) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).startAnimation(buildAnimation(i, clockWise, ANIMATION_DURING));
        }

        if (clockWise) {
            curSelectChildViewIndex = (curSelectChildViewIndex - 1 + getChildCount()) % getChildCount();
        } else {
            curSelectChildViewIndex = (curSelectChildViewIndex + 1 + getChildCount()) % getChildCount();
        }

        if (onZoomChildChangedListener != null) {
            onZoomChildChangedListener.onZoomChildChanged(curSelectChildViewIndex, clockWise);
        }
    }

    /**
     * 创建ziview的动画
     *
     * @param index       子view的index
     * @param isClockWise 是否是顺时针转动
     * @return
     */
    private Animation buildAnimation(int index, boolean isClockWise, long time) {
        float total = MAX - MIN;
        float start = (float) (MAX - total * Math.pow(((getDif(Math.abs(index - curSelectChildViewIndex))) / (getChildCount() / 2.0f)), 0.8));
        float stop = (float) (MAX - total * Math.pow(((getDif(Math.abs(index - curSelectChildViewIndex + (isClockWise ? 1 : -1)))) / (getChildCount() / 2.0f)), 0.8));
//        Log.v(TAG, "curSelectChildViewIndex" + curSelectChildViewIndex);
//        Log.v(TAG, "pos" + index);
//        Log.v(TAG, "start" + start);
//        Log.v(TAG, "stop" + stop);
        ScaleAnimation scaleAnimation = new ScaleAnimation(start, stop, start, stop, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.8f);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnimation.setFillEnabled(true);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(time);
        return scaleAnimation;
    }

    /**
     * 获取当前位置距离选中子view的index差值，超过子view总数一半的取子view总数和它的差值
     *
     * @param result 子view的index原始差值
     * @return 子view的index用于计算比例的差值
     */
    private float getDif(int result) {
        if (result > getChildCount() / 2.0f) {
            return getChildCount() - result;
        } else {
            return result;
        }
    }


    /**
     * 自定义onKeyDown， 对左右按键做处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            updateChildViewPos(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT, ANIMATION_DURING);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 子view点击事件处理的接口
     */
    public interface OnChildClickListener {
        void onChildClick(View view, int i);
    }

    /**
     * 选中的子view发生变化的监听接口
     */
    public interface OnZoomChildChangedListener {
        void onZoomChildChanged(int childIndex, boolean isCloseWise);
    }

}
