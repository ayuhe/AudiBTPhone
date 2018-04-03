
package com.lingfei.android.uilib.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


import com.lingfei.android.business.event.BackHomeEvent;
import com.lingfei.android.uilib.AppManager;
import com.lingfei.android.uilib.LibApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * 手机管理类. 手机事件信息管理;电话、短信管理;获取版本代号;检测可用网络等
 */
public final class SystemManage {
    private static final String TAG = "SystemManage";

    /**
     * 拨打电话
     */
    private static final String TEL_PRE = "tel:";

    /**
     * 发送短信
     */
    private static final String SMS_PRE = "smsto";

    /**
     * 短信消息体
     */
    private static final String EXTRA_SMS_BODY = "sms_body";

    /**
     * APK文件的MIME类型
     */
    private static final String MIME_APK = "application/vnd.android.package-archive";

    private SystemManage() {
    }

    public static void goHome(){
        // 返回到主菜单界面
        AppManager.getAppManager().gotoHomeActivity();

        // 返回home界面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        LibApplication.getContext().startActivity(intent);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNo [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void call(Context context, String phoneNo) {
        Uri uri = Uri.parse(TEL_PRE + phoneNo.trim());
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        context.startActivity(intent);
    }

    /**
     * 发送短信
     *
     * @param context
     * @param phoneNo
     * @param content [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void sms(Context context, String phoneNo, String content) {
        Uri uri = Uri.fromParts(SMS_PRE, phoneNo.trim(), null);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(EXTRA_SMS_BODY, content.trim());
        context.startActivity(intent);
    }

    /**
     * 获取手机IMS> <br/>
     * 如果IMSI正常获取,则必须要是15位.(the IMEI for GSM and the MEID or ESN for CDMA
     * phones.)
     *
     * @return String [返回IMSI,有可能返回null]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei != null) {
            if (imei.length() > 15) {
                imei = imei.substring(0, 15);
            }
        } else {
            // 有些平板没有IMEI码，没有就把IMEI设为15个0
            imei = "000000000000000";
        }

        return imei;
    }

    /**
     * 获取手机号码 <br/>
     * 如果检测到SIM卡,则正常获取手机号码,有可能获取不到
     *
     * @return String [返回手机号码]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 获取手机卡IMSI号 如果检测到SIM卡,则正常获取。
     *
     * @return String 单卡返回一个，双卡返回两个
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String[] getIMSI(Context context) {
        String[] imsi = new String[2];
        try {
            imsi[0] = getDeviceIdBySlot(context, "getSubscriberIdGemini", 0);
            imsi[1] = getDeviceIdBySlot(context, "getSubscriberIdGemini", 1);
        } catch (Exception e) {
            try {
                imsi[0] = getMTKDeviceIdBySlot(context, "getSubscriberId", 0);
                imsi[1] = getMTKDeviceIdBySlot(context, "getSubscriberId", 1);
            } catch (Exception e2) {
                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                imsi[0] = telephonyManager.getSubscriberId();
            }
        }
        return imsi;
    }

    /**
     * 通过类型获取运营商名称
     *
     * @param type
     * @return [参数说明]
     */
    public static String getCarrierName(int type) {
        return type == 0 ? "中国移动" : (type == 1 ? "中国联通" : (type == 2 ? "中国电信" : "无法识别"));

    }

    /**
     * 通过IMSI号判断运营商
     *
     * @param imsi
     * @return 运营商类型，0：中国移动，1：中国联通，2：中国电信,999：无法识别
     */
    public static int getCarrierTypeByIMSI(String imsi) {
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")
                    || imsi.startsWith("46020")) {
                // 中国移动
                return 0;
            } else if (imsi.startsWith("46001")) {
                // 中国联通
                return 1;
            } else if (imsi.startsWith("46003") || imsi.startsWith("46005")) {
                // 中国电信
                return 2;
            }
        }
        return 999;
    }

    /**
     * 获取手机MAC地址
     *
     * @return String [返回MAC]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

    /**
     * 获取手机本地IP地址
     *
     * @return 本机IP地址, 如果获取不到网络信息则返回127.0.0.1
     */
    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface info = en.nextElement();
                Enumeration<InetAddress> enAddr = info.getInetAddresses();
                while (enAddr.hasMoreElements()) {
                    InetAddress addr = enAddr.nextElement();
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LoggerUtil.e("获取网络信息失败!");
        }
        return "127.0.0.1";
    }

    /**
     * 跳转到网络设置页面
     *
     * @param context [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void gotoNetworkSetting(Context context) {
        Intent i = new Intent();
        i.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 获取G3ESOP客户端的版本代号
     *
     * @param context
     * @return int [VersionCode]
     * @throws throws [NameNotFoundException]
     * @author lKF46824/龙云芳
     * @see [getVersion]
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getApplicationContext().getPackageName());
    }

    /**
     * 获取包名packagename的版本代号
     *
     * @param context
     * @param packagename
     * @return int [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static int getVersionCode(Context context, String packagename) {

        PackageManager pm = context.getPackageManager();
        int versionCode;
        try {
            PackageInfo info = pm.getPackageInfo(packagename, 0);
            versionCode = info.versionCode;

        } catch (NameNotFoundException e) {
            versionCode = 0;
        }
        return versionCode;

    }

    /**
     * 获取客户端的版本名称
     *
     * @param context
     * @return int [VersionCode]
     * @throws throws [NameNotFoundException]
     * @see [getVersion]
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        String versionName;
        try {
            PackageInfo info = pm.getPackageInfo(context.getApplicationContext().getPackageName(),
                    0);
            versionName = info.versionName;

        } catch (NameNotFoundException e) {
            versionName = "--";
        }
        return versionName;
    }

    /**
     * 检查是否有可用网络
     *
     * @param context
     * @return boolean [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkNetWorkStatue(Context context) {
        boolean netSataus = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        cwjManager.getActiveNetworkInfo();
        if (cwjManager.getActiveNetworkInfo() != null) {
            netSataus = cwjManager.getActiveNetworkInfo().isConnected();
        }
        return netSataus;
    }

    /**
     * 检查SD卡是否可用
     *
     * @return 如果SD卡可用返回true, 否则返回false
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内存可用空间
     *
     * @return long [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blocksize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return blocksize * availableBlocks;
    }

    /**
     * 获取SD卡的可用空间
     *
     * @return long [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(sdcardDir.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getAvailableBlocks();
            return blockSize * availableBlocks;
        } else {
            return -1;
        }
    }

    /**
     * 通过Intent安装apk
     *
     * @param path [apk文件路径]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void installPackageViaIntent(Context context, String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, MIME_APK);
        context.startActivity(intent);
    }

    /**
     * 判断某个程序是否已经安装
     *
     * @param context
     * @param pkg
     * @return boolean [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAppInstall(Context context, String pkg) {
        PackageInfo packageInfo;
        boolean isInstall = false;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkg, 0);
        } catch (NameNotFoundException e) {
            return isInstall;
        }
        if (packageInfo != null) {
            isInstall = true;
        }
        return isInstall;
    }

    /**
     * 结束进程，不起作用了，不尝试为好
     *
     * @param context [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public static void killAndExit(Context context) {
        Process.killProcess(Process.myPid()); // 获取PID
        System.exit(10);
    }

    /**
     * 拼装设备信息
     *
     * @param doString
     * @return String [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getDeviecInfo(String doString) {
        String brand = Build.BRAND;// 这个是获得品牌
        String device_model = Build.MODEL; // 设备型号
        String version_release = Build.VERSION.RELEASE; // 设备的系统版本
        return "使用Android " + version_release + "  " + brand + "手机 " + device_model + "  " + "在"
                + DateUtil.getCurrentTime() + doString;
    }

    /**
     * 获取cpu编码
     *
     * @return [参数说明]
     */
    public static String getCpuInfo() {
        String cpuFile = "/proc/cpuinfo";
        String serial = " ";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(cpuFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf("Serial") >= 0) {
                    serial = line.split(":")[1].trim();
                }
            }
        } catch (IOException e) {
            LoggerUtil.e(TAG, e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LoggerUtil.i(TAG, e.toString());
                }
            }
        }
        return serial;
    }


    public static String[] getCpuInformation() {
        String str1 = "/proc/cpuinfo";
        String str2="";
        String[] cpuInfo={"",""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }

    /**
     * 判断定位服务是否可用
     *
     * @param context 上下文
     * @return 可用返回true, 否则返回false
     */
    public static boolean isLocationServiceAvailable(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean netEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            // ignore
        }
        try {
            netEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            // ignore
        }
        return gpsEnabled || netEnabled;
    }

    /**
     * 开启定位服务设置页面
     *
     * @param context 上下文
     */
    public static void gotoLocationSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取application中配置的meta-data值
     * meta-data在activity、service等地方都可以配置，不过暂时没有遇到过，暂忽略
     *
     * @param context 上下文
     * @param key     meta-data的name
     * @return meta-data的值,没有获取到返回null
     */
    public static String getApplicationMetaData(Context context, String key) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (NameNotFoundException e) {
            LoggerUtil.e(TAG, "Could not found Application!");
        }
        return null;
    }

    /**
     * dip转换为px
     *
     * @return int [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转换为dip
     *
     * @return int [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断sdk版本在11之上
     *
     * @return boolean [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAboveHoneycomb() {
        return android.os.Build.VERSION.SDK_INT >= 11;
    }

    /**
     * 获取R资源的id
     *
     * @param name      资源名称
     * @param type      资源类型。详见R.class内：anim 、drawable...
     * @param defaulRes 默认的返回资源id
     */
    public static int getIdentifier(Context context, String name, String type, int defaulRes) {
        Resources res = context.getResources();
        int resid = res.getIdentifier(name, type, context.getPackageName());
        if (resid == 0) {
            resid = defaulRes;
        }

        return resid;
    }

    /**
     * 获取drawable资源id,找不到返回defaulRes
     *
     * @param name      资源名称
     * @param defaulRes 默认返回资源
     */
    public static int getIdentifierDrawable(String name, int defaulRes) {
        return getIdentifier(LibApplication.getContext(), name, "drawable", defaulRes);
    }

    /**
     * 获取drawable资源id,找不到返回0
     *
     * @param name 资源名称
     */
    public static int getIdentifierDrawable(String name) {
        return getIdentifierDrawable(name, 0);
    }

    /**
     * 获取屏幕材质信息
     */
    public static DisplayMetrics getDisplayMetrics() {
        return LibApplication.getContext().getResources().getDisplayMetrics();
    }

    /**
     * 映射方式获取调用TelephonyManagerEx中的方法（MTK平台）
     *
     * @param predictedMethodName 方法名
     * @param slotID              输入参数
     * @return 方法调用返回
     */
    private static String getMTKDeviceIdBySlot(Context context, String predictedMethodName,
                                               int slotID) throws Exception {

        String methodReturn = null;
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {

            Class<?> telephonyClass = Class.forName("com.mediatek.telephony.TelephonyManagerEx");

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);

            if (ob_phone != null) {
                methodReturn = ob_phone.toString();

            }
        } catch (Exception e) {
            LoggerUtil.e(TAG, e.toString());
            throw e;
        }

        return methodReturn;
    }

    /**
     * 映射方式获取调用指定的方法（通用）
     *
     * @param predictedMethodName 方法名
     * @param slotID              输入参数
     * @return 方法调用返回
     */
    private static String getDeviceIdBySlot(Context context, String predictedMethodName, int slotID)
            throws Exception {

        String methodReturn = null;
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);

            if (ob_phone != null) {
                methodReturn = ob_phone.toString();

            }
        } catch (Exception e) {
            LoggerUtil.e(TAG, e.toString());
            throw e;
        }
        return methodReturn;
    }

    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
        }
        return version;
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取系统语言类型
     * @param context
     * @return
     */
    public static String getLanguageEnv(Context context) {
        if (null == context) {
            return "";
        }

        Locale l = context.getResources().getConfiguration().locale;
        String language = l.getLanguage();
        String country = l.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(country)) {
                language = "zh-CN";
            } else if ("tw".equals(country)) {
                language = "zh-TW";
            }
        } else if ("pt".equals(language)) {
            if ("br".equals(country)) {
                language = "pt-BR";
            } else if ("pt".equals(country)) {
                language = "pt-PT";
            }
        }

        return language;
    }

    public static String getLanguageString(Context context) {
        if (null == context) {
            return "";
        }
        String language = "中文";

        if ("zh-CN".equals(getLanguageEnv(context))) {
            language = "中文简体";
        } else if ("zh-TW".equals(getLanguageEnv(context))) {
            language = "中文繁体";
        } else {
            language = "其他";
        }

        return language;
    }

}
