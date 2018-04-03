package com.lingfei.android.business.util;

public class CommonVar{
				public final static String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";

				public static String sp_save_mcu_global_status = "com.zxcar.launcher.mcu.globalstatus";
				//mcu版本号
				public static String mcu_version_key = "mcu_version";
				//功能设置
				//public static String mcu_arm_communication="mcu_arm_communication";//
				public static String reversetrack_status_key = "reversetrack";//倒车轨迹

				public static String brakedetection_status_key = "brakedetection";//刹车检测
				public static String bluetoothmusic_status_key = "bluetoothmusic";//蓝牙音乐
				public static String automap_status_key = "automap";//自动开启地图
				public static String hdmi_status_key = "hdmi";//hdmi  true表示设置成导航，false设置成原车
				public static String camera_status_key = "camera";//
				public static String carcamera_status_key = "carcamera";
				public static String video_signal_status_key = "video_signal";//true or false
				public static String cur_play_source_status_key = "cur_play_source";// isnull isfm isdisc
				public static String video_fullscreen_status_key = "video_fullscreen";
				public static String onekey_upwindow_status_key = "onekey_upwindow";//一键升窗
				public static String hardwarereversing_status_key = "hardwarereversing";//硬件倒车开关
				public static String phone_net_status_key = "phone_net";//手机互联
				public static String bmw_car_select_status_key = " bmw_car_select";//宝马原厂选择

				public static String tachograph_status_key = "tachograph";//行车记录仪
				public static String quanjing360_status_key = "quanjing360";//全景360


				public static String jcdvr_status_key = "jcdvr";//行车记录仪
				public static String jc360_status_key = "jc360";//全景360
				public static String ill_status_key = "ill_car_select";//ill 灯线检测
				public static String backcamera_line_status_key = "backcamera_line";//倒车线
				public static String ipod_status_key = "ipod";//ipod检测
				public static String dvd_status_key = "dvd";//dvd检测
				public static String cmmb_status_key = "cmmb";//cmmb检测
				public static String mhl_status_key = "mhl";//cmmb检测
				//当前选择地图的包名，和cpt
				public static String curselectmappkg_key = "curselectmappkgname";//当前选择地图的包名
				public static String curselectmapcpt_key = "curselectmapcptname";//当前选择地图的activity name

				public static String isinstallklink_key = "isinstallklink_key";//当前选择地图的activity name

				public static String flag_incdtvdvr = "flag_incdtvdvr";

				public static String voice_pressed = "voice_pressed";
				public static String voice_value = "voice_value";

				public static String touch_menu_gogps = "touch_menu_gogps";

				/* -----功能设置----------*/
				//显示设置
				public static String disp_brightness = "brightness";//亮度
				public static String disp_contrast = "disp_contrast";//对比度
				public static String disp_backlight = "disp_backlight";//背光
				//导航设置
				public static String nav_volume = "volume";
				public static String nav_key = "key";
				//高级设置
				public static String senior_comfort_f1 = "comfort_f1";
				public static String senior_comfort_f2 = "comfort_f2";
				public static String senior_comfort_f3 = "comfort_f3";
				public static String senior_comfort_f4 = "comfort_f4";

				public static String senior_car_mode = "car_mode";
				public static String senior_back_view = "back_view";
				public static String senior_input = "input";
				public static String senior_dvd = "dvd";
				public static String senior_cmmb = "cmmb";
				public static String senior_grapher = "grapher";

				public static String language = "language";

				/*
					* 计算两点间距离
					**/
				public static float calculateDistance(int mPointX, int mPointY, int x, int y){
								float distance = (float) Math
																.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY)
																								* (y - mPointY)));
								return distance;
				}

				/**
					* 计算某点的角度
					*
					* @param x
					* @param y
					* @return
					*/
				public static float computeCurrentAngle(int mPointX, int mPointY, int x, int y){
								float distance = calculateDistance(mPointX, mPointY, x, y);
								float degree = (float) (Math.acos((x - mPointX) / distance) * 180 / Math.PI);
								if(y < mPointY){
												degree = -degree;
								}
								return degree;
				}

}
