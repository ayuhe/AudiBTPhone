package com.lingfei.android.uilib.util;

/**
	* Constant
	* 常量
	* @author heyu
	* @date 2017/3/29.
	*/
public class Constant{

				// 暂停蓝牙音乐
				public static final String CMD_PAUSE_BTMUSIC = "pause";
				// 播放蓝牙音乐
				public static final String CMD_PLAY_BTMUSIC = "play";

				/**
					* 标识蓝牙连接状态
					*/
				public static final String BT_CONNECTED_STATUS = "net.btphone_status";

				// 当前蓝牙状态
				public static final String KEY_BT_STATE = "net.lf_btphone_state";

				// 语音相关常量
				public static class Voice{
								// 唤醒语音
								public final static int WAKE_UP_VOICE = 1;
								// 油量播报
								public final static int OIL_BROADCAST = 2;
								// 车门播报
								public final static int DOOR_BROADCAST = 3;
								// 暂停同听的播放
								public final static int PAUSE_TXT_MUSIC = 4;

								// 同步联系人给同行者
								public final static int RESPONSE_CONTACTS = 5;

								// 查询联系人
								public static final String CMD_BT_REQUEST_CONTACTS = "request_contacts";
								// 拨打电话
								public static final String CMD_BT_DIAL = "dial";
								// 接听电话
								public static final String CMD_BT_ANSWER = "answer";
								// 拒接电话
								public static final String CMD_BT_REJECT = "reject";
								// 挂断电话
								public static final String CMD_BT_HANG_UP = "hangup";
				}

				public static class Action{
								public final static String ACTION_LF_TXZ_BT_STATE = "com.lingfei.android.txz.adapter.btphone";
								public final static String ACTION_BT_STATE_TAIKING = "com.lingfei.android.bt.state.TAIKING";
								public final static String ACTION_LF_BT_PHONE = "android.intent.action.lf.BTPHONE";
								public static final String ACTION_RESET_BT_PHONE_SERVICE = "com.android.restartbtphoneservice";
								public static final String ACTION_BT_MUSIC = "com.android.lf.bt.music.broadcastreceiver";
								public static final String ACTION_LF_CAR_SERVICE = "com.lingfei.car.ILingFeiCarService";
								public static final String ACTION_BT_PHONE_SERVICE = "com.lingfei.android.main.ui.btphone.service.BTPhoneService";

				}

}
