package com.lingfei.android.bt.ui.btphone.contact;

import com.lingfei.android.bt.ui.BasePresenter;
import com.lingfei.android.bt.ui.BaseView;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;

import java.util.List;

/**
	* ContactContract
	*
	* @author heyu
	* @date 2017/6/19.
	*/
public interface ContactContract{
				interface View extends BaseView{
								void updateView(List<BTPhonePeople> phonePeoples);
				}

				interface Presenter extends BasePresenter<View>{
								void initData();
				}
}
