package com.lingfei.android.uilib.util;


import com.lingfei.android.uilib.Config;

public class LogUtil {
	public final static boolean isDebug = Config.IS_DEBUG; // 调试时，打印报文日志的开关。发布版本时要把开关设为false。

	public static void v(String tag, String msg) {
		if (isDebug)
			android.util.Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.v(tag, msg, t);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			android.util.Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.d(tag, msg, t);
	}

	public static void i(String tag, String msg) {
		if (isDebug)
			android.util.Log.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.i(tag, msg, t);
	}

	public static void w(String tag, String msg) {
		if (isDebug) {
			if (msg.length() <= 1000) {
				android.util.Log.w(tag, msg);
			} else {
				int maxLogSize = 1000;
				for (int i = 0; i <= msg.length() / maxLogSize; i++) {
					int start = i * maxLogSize;
					int end = (i + 1) * maxLogSize;
					end = end > msg.length() ? msg.length() : end;
					android.util.Log.w(tag, msg.substring(start, end));
				}
			}
		}
	}

	public static void w(String tag, String msg, Throwable t) {
		if (isDebug) {
			if (msg.length() <= 1000) {
				android.util.Log.w(tag, msg, t);
			} else {
				int maxLogSize = 1000;
				for (int i = 0; i <= msg.length() / maxLogSize; i++) {
					int start = i * maxLogSize;
					int end = (i + 1) * maxLogSize;
					end = end > msg.length() ? msg.length() : end;
					android.util.Log.w(tag, msg.substring(start, end));
				}
			}
		}
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			android.util.Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.e(tag, msg, t);
	}
}
