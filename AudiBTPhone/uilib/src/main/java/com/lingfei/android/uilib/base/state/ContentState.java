package com.lingfei.android.uilib.base.state;


import com.lingfei.android.uilib.R;

/**
 * 显示具体的内容
 */
public class ContentState extends AbstractShowState implements ShowState {

    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_content, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_content, animate);
    }
}
