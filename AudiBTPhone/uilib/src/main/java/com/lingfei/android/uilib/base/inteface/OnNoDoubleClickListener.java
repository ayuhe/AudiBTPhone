package com.lingfei.android.uilib.base.inteface;

import android.view.View;

import java.util.Calendar;

/**
 * 防止快速点击连续响应事件
 * Created by heyu on 2016/7/28.
 */
public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 700;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (Math.abs(currentTime - lastClickTime) > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
