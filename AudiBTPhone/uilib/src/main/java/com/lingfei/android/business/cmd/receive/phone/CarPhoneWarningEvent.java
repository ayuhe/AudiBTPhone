package com.lingfei.android.business.cmd.receive.phone;

import de.greenrobot.event.EventBus;

/**
	* Created by Administrator on 2016/9/27.
	*/
public class CarPhoneWarningEvent{

				private String warningMode;//警告模式
				private String warningInfo;//警告内容

				public static class WarningMode{
								public static final String MODE_WARNING_PHONE_INCOMING = "CarPhoneFragmentEvent.MODE_WARNING_PHONE_INCOMING";
								public static final String MODE_WARNING_PHONE_CALLOUT = "CarPhoneFragmentEvent.MODE_WARNING_PHONE_CALLOUT";
								public static final String MODE_WARNING_PHONE_HANGUP = "CarPhoneFragmentEvent.MODE_WARNING_PHONE_HANGUP";
								public static final String MODE_WARNING_PHONE_TALKING = "CarPhoneFragmentEvent.MODE_WARNING_PHONE_TALKING";
				}

				public void send_WarningMode(String msg){
								CarPhoneWarningEvent event = new CarPhoneWarningEvent();
								event.setWarningMode(msg);

								EventBus.getDefault().post(event);
				}

				//============================== get set ====================================
				public String getWarningMode(){
								return warningMode;
				}

				public void setWarningMode(String warningMode){
								this.warningMode = warningMode;
				}

				public String getWarningInfo(){
								return warningInfo;
				}

				public void setWarningInfo(String warningInfo){
								this.warningInfo = warningInfo;
				}
}
