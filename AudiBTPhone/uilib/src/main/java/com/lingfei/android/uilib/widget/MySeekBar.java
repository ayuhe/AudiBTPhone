package com.lingfei.android.uilib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MySeekBar extends SeekBar{

				public MySeekBar(Context context){
								super(context);
				}

				public MySeekBar(Context context, AttributeSet attrs){
								super(context, attrs);
				}

				@Override
				public boolean dispatchTouchEvent(MotionEvent arg0){
								if(!isSelected()){
												return true;
								}
								return super.dispatchTouchEvent(arg0);
				}
}
