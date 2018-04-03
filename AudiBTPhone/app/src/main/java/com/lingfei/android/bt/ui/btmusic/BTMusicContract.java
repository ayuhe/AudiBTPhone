package com.lingfei.android.bt.ui.btmusic;

import com.lingfei.android.bt.ui.BasePresenter;
import com.lingfei.android.bt.ui.BaseView;
import com.lingfei.android.bt.ui.btmusic.bean.MusicMenu;

import java.util.List;

/**
	* Created by heyu on 2016/7/22.
	*/
public interface BTMusicContract{

				interface View extends BaseView{
								void updateMenuList(List<MusicMenu> items);

				}

				interface Presenter extends BasePresenter<View>{
								void initMenuList();

								void onClickMenuItem(MusicMenu item);
				}
}
