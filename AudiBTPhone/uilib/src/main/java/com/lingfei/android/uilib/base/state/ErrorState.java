package com.lingfei.android.uilib.base.state;


import com.lingfei.android.uilib.R;

/**
 * Fragment显示错误的状态
 */
public class ErrorState extends AbstractShowState {
    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_error, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_error, animate);
    }
}
