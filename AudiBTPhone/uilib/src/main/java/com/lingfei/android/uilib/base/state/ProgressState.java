package com.lingfei.android.uilib.base.state;


import com.lingfei.android.uilib.R;

/**
 * 显示Fragment加载进度条
 */
public class ProgressState extends AbstractShowState {

    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_progress, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_progress, animate);
    }
}
