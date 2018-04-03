package com.lingfei.android.business.event;

import com.lingfei.android.business.bean.guide.GuideInfo;

/**
	* 刷新导向信息事件
	* Created by heyu on 2016/9/9.
	*/
public class NaviGuideEvent{
				private GuideInfo guideInfo; // 导向信息

				public NaviGuideEvent(GuideInfo guideInfo){
								this.guideInfo = guideInfo;
				}

				public GuideInfo getGuideInfo(){
								return guideInfo;
				}

				public void setGuideInfo(GuideInfo guideInfo){
								this.guideInfo = guideInfo;
				}
}
