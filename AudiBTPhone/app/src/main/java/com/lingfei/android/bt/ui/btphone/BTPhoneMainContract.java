package com.lingfei.android.bt.ui.btphone;

import com.lingfei.android.bt.ui.BasePresenter;
import com.lingfei.android.bt.ui.BaseView;
import com.lingfei.android.bt.ui.btphone.bean.MenuBean;

import java.util.List;

/**
	* BTPhoneMainContract
	*
	* @author heyu
	* @date 2017/6/15.
	*/
public interface BTPhoneMainContract{
				interface View extends BaseView{
								void updateMenuList(List<MenuBean> musicMenus);
				}

				interface Presenter extends BasePresenter<View>{
								void initMenuList();
				}
}
