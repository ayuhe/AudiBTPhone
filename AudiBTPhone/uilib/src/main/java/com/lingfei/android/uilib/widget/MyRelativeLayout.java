package com.lingfei.android.uilib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class MyRelativeLayout extends RelativeLayout{
				private boolean isCanTouch = true;
				public MyRelativeLayout(Context context){
								super(context);
				}

				public MyRelativeLayout(Context context, AttributeSet attrs){
								super(context, attrs);

				}

				public void setCanTouch(boolean canTouch){
								isCanTouch = canTouch;
				}

				@Override
				public boolean dispatchTouchEvent(MotionEvent arg0){
								if(!isCanTouch){
												return true;
								}
								return super.dispatchTouchEvent(arg0);
				}

				@Override
				public boolean onTouchEvent(MotionEvent event){
								if(event.getAction() == MotionEvent.ACTION_DOWN){
												this.requestFocus();
								}
								return super.onTouchEvent(event);
				}

}
