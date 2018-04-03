package com.lingfei.android.bt.ui.btphone.device;

import com.lingfei.android.bt.ui.BasePresenter;
import com.lingfei.android.bt.ui.BaseView;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;

import java.util.ArrayList;

/**
	* DeviceContract
	*
	* @author heyu
	* @date 2017/6/20.
	*/
public interface DeviceContract{
				interface View extends BaseView{
								void updateView(ArrayList<BTDevice> list);
				}

				interface Presenter extends BasePresenter<View>{
								void initData();
				}
}
