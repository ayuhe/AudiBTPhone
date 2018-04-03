package com.lingfei.android.uilib.base.state;


import com.lingfei.android.uilib.R;

/**
 * Fragment显示空：没有数据
 */
public class EmptyState extends AbstractShowState {
    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_empty, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_empty, animate);
    }
}
