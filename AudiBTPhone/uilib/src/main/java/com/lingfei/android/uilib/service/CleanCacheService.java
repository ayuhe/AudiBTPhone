package com.lingfei.android.uilib.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;

import com.lingfei.android.uilib.event.CleanCacheEvent;
import com.lingfei.android.uilib.util.EventPostUtils;
import com.lingfei.android.uilib.util.LoggerUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
	* 清除第三方APP缓存的服务
	*/
public class CleanCacheService extends Service{
				// 部分系统级的应用不要清数据，否则可能报错
				private final static String ANDROID = "android";
				private final static String CONTACTS = "com.android.contacts";
				private final static String DEFCONTAINER = "com.android.defcontainer";
				private final static String INPUTMETHOD_LATIN = "com.android.inputmethod.latin";
				private final static String INPUTDEVICES = "com.android.inputdevices";
				private final static String INPUTMETHOD_PINYIN = "com.android.inputmethod.pinyin";
				private final static String SHAREDSTORAGEBACKUP = "com.android.sharedstoragebackup";
				private final static String PROVISION = "com.android.provision";
				private final static String PACPROCESSOR = "com.android.pacprocessor";
				private final static String SETTINGS = "com.android.settings";
				private final static String PROVIDERS_SETTINGS = "com.android.providers.settings";
				private final static String PROVIDERS_MEDIA = "com.android.providers.media";
				private final static String PROVIDERS_CALENDAR = "com.android.providers.calendar";
				private final static String USERDICTIONARY = "com.android.providers.userdictionary";
				private final static String DESKCLOCK = "com.android.deskclock";
				private final static String CERTINSTALLER = "com.android.certinstaller";
				private final static String EXTERNALSTORAGE = "com.android.externalstorage";
				private final static String SYSTEMUI = "com.android.systemui";
				private final static String KEYCHAIN = "com.android.keychain";
				private final static String EXCHANGE = "com.android.exchange";
				private final static String SHELL = "com.android.shell";
				private final static String PHASEBEAM = "com.android.phasebeam";
				private final static String WALLPAPER = "com.android.wallpaper";
				private final static String INPUTMETHOD_SOGOU = "com.sohu.inputmethod.sogou";
				private final static String BACKUPTRANSPORT = "com.google.android.backuptransport";
				private final static String CONFIGUPDATER = "com.google.android.configupdater";
				private final static String ONETIMEINITIALIZER = "com.google.android.onetimeinitializer";
				private final static String GSF = "com.google.android.gsf";
				private final static String GSF_LOGIN = "com.google.android.gsf.login";
				private final static String FEEDBACK = "com.google.android.feedback";
				private final static String PARTNERSETUP = "com.google.android.partnersetup";
				private final static String GMS = "com.google.android.gms";
				private final static String GM = "com.google.android.gm";
				private final static String TSCALIBRATION = "com.rockchip.tscalibration";
				private final static String ZXCAR_MAPCOPY = "com.zxcar.mapcopy";
				private final static String GOODOCOM_RK = "com.goodocom.rk";
				private final static String GOOGLE_APPS_MAPS = "com.google.android.apps.maps";
				private final static String WALLPAPERCROPPER = "com.android.wallpapercropper";
				private final static String LOCATION_FUSED = "com.android.location.fused";
				private final static String BACKUPCONFIRM = "com.android.backupconfirm";
				private final static String MAGICSMOKE = "com.android.magicsmoke";
				private final static String RK_UPDATE_SERVICE = "android.rockchip.update.service";
				private final static String PROVIDERS_DOWNLOADS = "com.android.providers.downloads";
				private final static String MCUPDTADIALOG = "com.zxsd.mcupdtadialog";
				private final static String GOCSDK = "com.goodocom.gocsdk";

				private Map<String, Boolean> keepPackageNameMap = new HashMap<>();

				private ActivityManager am;
				private PackageManager pm;

				@Override
				public IBinder onBind(Intent intent){
								return null;
				}

				@Override
				public void onCreate(){
								super.onCreate();
								initNoCleanCachePackageNames();
								pm = getPackageManager();
								am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
				}

				@Override
				public int onStartCommand(Intent intent, int flags, int startId){
								fillData();
								return super.onStartCommand(intent, flags, startId);
				}

				/**
					* 初始化不可以清除数据的包名
					*/
				private void initNoCleanCachePackageNames() {
								if (null == keepPackageNameMap){
												keepPackageNameMap = new HashMap<>();
								}
								else{
												keepPackageNameMap.clear();
								}
								keepPackageNameMap.put(getPackageName(), true);
								keepPackageNameMap.put(ANDROID, true);
								keepPackageNameMap.put(CONTACTS, true);
								keepPackageNameMap.put(DEFCONTAINER, true);
								keepPackageNameMap.put(INPUTMETHOD_LATIN, true);
								keepPackageNameMap.put(INPUTDEVICES, true);
								keepPackageNameMap.put(INPUTMETHOD_PINYIN, true);
								keepPackageNameMap.put(SHAREDSTORAGEBACKUP, true);
								keepPackageNameMap.put(PROVISION, true);
								keepPackageNameMap.put(PACPROCESSOR, true);
								keepPackageNameMap.put(SETTINGS, true);
								keepPackageNameMap.put(PROVIDERS_SETTINGS, true);
								keepPackageNameMap.put(PROVIDERS_MEDIA, true);
								keepPackageNameMap.put(PROVIDERS_CALENDAR, true);
								keepPackageNameMap.put(USERDICTIONARY, true);
								keepPackageNameMap.put(DESKCLOCK, true);
								keepPackageNameMap.put(CERTINSTALLER, true);
								keepPackageNameMap.put(EXTERNALSTORAGE, true);
								keepPackageNameMap.put(SYSTEMUI, true);
								keepPackageNameMap.put(KEYCHAIN, true);
								keepPackageNameMap.put(EXCHANGE, true);
								keepPackageNameMap.put(SHELL, true);
								keepPackageNameMap.put(PHASEBEAM, true);
								keepPackageNameMap.put(WALLPAPER, true);
								keepPackageNameMap.put(INPUTMETHOD_SOGOU, true);
								keepPackageNameMap.put(BACKUPTRANSPORT, true);
								keepPackageNameMap.put(CONFIGUPDATER, true);
								keepPackageNameMap.put(ONETIMEINITIALIZER, true);
								keepPackageNameMap.put(GSF, true);
								keepPackageNameMap.put(GSF_LOGIN, true);
								keepPackageNameMap.put(FEEDBACK, true);
								keepPackageNameMap.put(PARTNERSETUP, true);
								keepPackageNameMap.put(GMS, true);
								keepPackageNameMap.put(GM, true);
								keepPackageNameMap.put(TSCALIBRATION, true);
								keepPackageNameMap.put(ZXCAR_MAPCOPY, true);
								keepPackageNameMap.put(GOODOCOM_RK, true);
								keepPackageNameMap.put(GOOGLE_APPS_MAPS, true);
								keepPackageNameMap.put(WALLPAPERCROPPER, true);
								keepPackageNameMap.put(LOCATION_FUSED, true);
								keepPackageNameMap.put(BACKUPCONFIRM, true);
								keepPackageNameMap.put(MAGICSMOKE, true);
								keepPackageNameMap.put(RK_UPDATE_SERVICE, true);
								keepPackageNameMap.put(PROVIDERS_DOWNLOADS, true);
								keepPackageNameMap.put(MCUPDTADIALOG, true);
								keepPackageNameMap.put(GOCSDK, true);
				}

				/**
					* 检查包名是否可以清除数据
					* @param packageName
					* @return true 可以清除，false 不可清除
					*/
				private boolean checkCanCleanCachePackageName(String packageName) {
								if (null != keepPackageNameMap){
												Boolean isKeep = keepPackageNameMap.get(packageName);
												if ((null != isKeep && isKeep) || packageName.contains("com.android.") || packageName.contains("com.google.android.")){
																return false;
												}
								}

								return true;
				}

				private void fillData(){
								new Thread(){
												public void run(){
																List<PackageInfo> infos = pm.getInstalledPackages(0);
																int max = infos.size();
																int min = 0;
																for(PackageInfo info : infos){
																				String packname = info.packageName;
																				min++;
																				if(checkCanCleanCachePackageName(packname)){
																								cleanCache(packname);
																								EventPostUtils.Post(new CleanCacheEvent(max, min, packname, false));
																				}
																}

																// 清理完成
																EventPostUtils.Post(new CleanCacheEvent(max, max, "", true));
												}
								}.start();
				}

				private void cleanCache(String packname){
								// 利用反射机制清理用户数据
								try{
												Class<?> amClass = Class.forName(am.getClass().getName());
												Method clearApp = amClass.getMethod("clearApplicationUserData", String.class, IPackageDataObserver.class);
												LoggerUtil.d("CleanCacheService clearApp: " + packname);
												clearApp.invoke(am, packname, new IPackageDataObserver(){
																@Override
																public IBinder asBinder(){
																				return null;
																}

																@Override
																public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException{
																				LoggerUtil.e("CleanCacheService clearApp: " + packageName + "  " +succeeded);
																}
												});
								}
								catch(Exception e){
												e.printStackTrace();
								}
				}
}
