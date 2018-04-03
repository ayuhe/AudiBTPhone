package com.lingfei.android.bt.ui.btphone.dail;

import com.lingfei.android.bt.ui.BasePresenter;
import com.lingfei.android.bt.ui.BaseView;

/**
	* DailContract
	*
	* @author heyu
	* @date 2017/6/19.
	*/
public interface DailContract{
				interface View extends BaseView{
								// 刷新UI界面
								void onUpdateView(String callNowStr, int clearResId, int okResId, int type);

								// 删除一次
								void onClearOnce();
				}

				interface Presenter extends BasePresenter<View>{
								// 初始化数据
								void initData();

								// 处理拨号或者挂断
								void onHandleDailAndHangup(String phoneNum);

								// 处理清除按键
								void onHandleClear();
				}
}
