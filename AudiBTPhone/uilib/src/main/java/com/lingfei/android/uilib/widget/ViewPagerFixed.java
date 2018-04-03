package com.lingfei.android.uilib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
	* 禁止ViewPager滑动
	* Created by heyu on 2015/8/12.
	*/
public class ViewPagerFixed extends android.support.v4.view.ViewPager{
				private boolean scrollble = true;
				private boolean touchable = true;

				public ViewPagerFixed(Context context){
								super(context);
				}

				public ViewPagerFixed(Context context, AttributeSet attrs){
								super(context, attrs);
				}

				@Override
				public void scrollTo(int x, int y){
								super.scrollTo(x, y);
				}

				@Override
				public boolean onTouchEvent(MotionEvent arg0){
								if(!isScrollble())
												return false;
								else
												return super.onTouchEvent(arg0);
				}

				@Override
				public boolean onInterceptTouchEvent(MotionEvent arg0){
								if(!isTouchable()){
												return true;
								}

								if(!isScrollble())
												return false;
								else
												return super.onInterceptTouchEvent(arg0);
				}

				@Override
				public void setCurrentItem(int item, boolean smoothScroll){
								super.setCurrentItem(item, smoothScroll);
				}

				@Override
				public void setCurrentItem(int item){
								super.setCurrentItem(item);
				}

				public boolean isScrollble(){
								return scrollble;
				}

				public void setScrollble(boolean scrollble){
								this.scrollble = scrollble;
				}

				public boolean isTouchable(){
								return touchable;
				}

				public void setTouchable(boolean touchable){
								this.touchable = touchable;
				}
}