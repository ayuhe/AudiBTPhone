package com.lingfei.android.uilib.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.lingfei.android.uilib.LibApplication;

import java.util.HashMap;
import java.util.Map;


/**
	* @author ayuhe
	* @ClassName: SysSharePres
	* @Description: TODO 保存数据
	* @date 2015年2月15日 下午2:15:03
	*/
public class SysSharePres{

				/**
					* 保存信息
					**/
				private static final String PRES_NAME = "system_share";

				// 当前播放歌曲Id
				private static final String PLAYING_SONG_ID = "playing_song_id";

				// 当前播放歌曲的歌手Id
				private static final String PLAYING_SINGER_ID = "playing_singer_id";

				// 当前播放歌曲的专辑Id
				private static final String PLAYING_ALBUM_ID = "playing_album_id";

				// 车型
				private static final String CAR_TYPE_ID = "car_type_id";

				// 原车导航
				private static final String ORIGINAL_CAR_NAVI_ID = "original_car_navi_id";

				// 后视选择
				private static final String BEHIND_VIEW_SELECTED_ID = "behind_view_selected_id";

				// 空调设置
				private static final String AIR_SETTING_ID = "air_setting_id";

				// 开机/Logo
				private static final String BOOT_LOGO_ID = "boot_logo_id";

				// 分辨率选择
				private static final String RESOLUTION_ID = "resolution_id";

				// 外部输入选择
				private static final String AUX_SET_ID = "aux_set_id";

				// 分屏设置
				private static final String SPLIT_SCREEN_ID = "split_screen_id";

				// 地图选择
				private static final String MAP_SELECTED_ID = "map_selected_id";

				// 语言设置
				private static final String LANGUAGE_SELECTED_ID = "language_selected_id";

				// 低速雷达
				private static final String LOW_RADAR_ID = "low_radar_id";

				// MCU 版本号
				private static final String MCU_VERSION = "mcu_version";

				// 原车时间
				public static final String ORIGINAL_CAR_TIME = "original_car_time";

				// 启动仪表盘界面
				public static final String START_DASHBOARD_ACTIVITY = "start_dashboard_activity";

				// 是否有导向信息
				public final static String HAD_GUIDE_INFO = "had_guide_info";

				// 是否正在播放视频
				public final static String IS_PLAYING_VIDEO = "is_playing_video";

				// 最近播放视频的Id
				public final static String LATELY_PLAY_VIDEO_ID = "lately_play_video_id";

				// 是否正在播放音乐
				public final static String IS_PLAYING_MUSIC = "is_playing_music";

				// 是否打开显示更多应用
				public final static String IS_OPEN_ALL_APP = "is_open_all_app";

				// 当前选择地图的包名
				public final static String CUR_SELECT_MAP_PKG = "cur_select_map_pkg";

				// 当前选择地图的类名
				public final static String CUR_SELECT_MAP_CLASS = "cur_select_map_class";

				//显示设置
				public final static String DISP_BRIGHTNESS = "disp_brightness";//亮度
				public final static String DISP_CONTRAST = "disp_contrast";//对比度
				public final static String DISP_BACKLIGHT = "disp_backlight";//背光

				//导航设置
				public final static String NAV_VOLUME = "nav_volume"; // 声音
				public final static String NAV_KEY = "nav_key"; // 按键设置

				//高级设置--舒适功能
				public final static String SENIOR_COMFORT_F1 = "comfort_f1";
				public final static String SENIOR_COMFORT_F2 = "comfort_f2";
				public final static String SENIOR_COMFORT_F3 = "comfort_f3";
				public final static String SENIOR_COMFORT_F4 = "comfort_f4";

				protected SharedPreferences mSharePres;

				private Map<Integer, Boolean> splitScreenMap;

				/**
					* <默认构造函数>
					*/
				protected SysSharePres(){
								mSharePres = LibApplication.getContext().getSharedPreferences(
																PRES_NAME, Context.MODE_PRIVATE);
				}

				private static class SysSharePresHolder{

								static final SysSharePres INSTANCE = new SysSharePres();
				}

				public static SysSharePres getInstance(){
								if(null != SysSharePresHolder.INSTANCE){
												return SysSharePresHolder.INSTANCE;
								}
								else{
												return new SysSharePres();
								}
				}

				/**
					* 初始化系统的配置信息
					*/
				public void initSystemConfig(){
								SysSharePres.getInstance().putBoolean(HAD_GUIDE_INFO, false);
								SysSharePres.getInstance().putBoolean(START_DASHBOARD_ACTIVITY, false);
								setPlayingSongId(-1);
								setPlayingSingerId(-1);
								setPlayingAlbumId(-1);
								setIsPlayingVideo(false);
								setIsPlayingMusic(false);
								// 清除本地保存版本号
								setMcuVersion("");
				}

				// 音乐播放器相关
				public void setPlayingSongId(long songId){
								putLong(PLAYING_SONG_ID, songId);
				}

				public long getPlayingSongId(){
								return getLong(PLAYING_SONG_ID);
				}

				public void setPlayingSingerId(long singerId){
								putLong(PLAYING_SINGER_ID, singerId);
				}

				public long getPlayingSingerId(){
								return getLong(PLAYING_SINGER_ID);
				}

				public void setPlayingAlbumId(long albumId){
								putLong(PLAYING_ALBUM_ID, albumId);
				}

				public long getPlayingAlbumId(){
								return getLong(PLAYING_ALBUM_ID);
				}

				// 设置相关
				public void setCarTypeId(int carTypeId){
								putIntPref(CAR_TYPE_ID, carTypeId);
				}

				public int getCarTypeId(){
								return getIntPref(CAR_TYPE_ID);
				}

				public void setOriginalCarNaviId(boolean isOriginal){
								putBoolean(ORIGINAL_CAR_NAVI_ID, isOriginal);
				}

				public boolean getOriginalCarNaviId(){
								return getBoolean(ORIGINAL_CAR_NAVI_ID);
				}

				public void setBehindViewSelectedId(int behindViewSelectedId){
								putIntPref(BEHIND_VIEW_SELECTED_ID, behindViewSelectedId);
				}

				public int getBehindViewSelectedId(){
								return getIntPref(BEHIND_VIEW_SELECTED_ID);
				}

				public void setAirSettingId(int airSettingId){
								putIntPref(AIR_SETTING_ID, airSettingId);
				}

				public int getAirSettingId(){
								return getIntPref(AIR_SETTING_ID);
				}

				public void setBootLogoId(int bootLogoId){
								putIntPref(BOOT_LOGO_ID, bootLogoId);
				}

				public int getBootLogoId(){
								return getIntPref(BOOT_LOGO_ID);
				}

				public int getResolutionId(){
								return getIntPref(RESOLUTION_ID);
				}

				public void setResolutionId(int resolutionId){
								putIntPref(RESOLUTION_ID, resolutionId);
				}

				public int getAuxSetId(){
								return getIntPref(AUX_SET_ID);
				}

				public void setAuxSetId(int auxId){
								putIntPref(AUX_SET_ID, auxId);
				}

				public int getSplitScreenId(){
								return getIntPref(SPLIT_SCREEN_ID);
				}

				public void setSplitScreenId(int splitScreenId){
								putIntPref(SPLIT_SCREEN_ID, splitScreenId);
				}

				public int getMapSelectedId(){
								return getIntPref(MAP_SELECTED_ID);
				}

				public void setMapSelectedId(int mapSelectedId){
								putIntPref(MAP_SELECTED_ID, mapSelectedId);
				}

				public int getLanguageSelectedId(){
								return getIntPref(LANGUAGE_SELECTED_ID);
				}

				public void setLanguageSelectedId(int languageSelectedId){
								putIntPref(LANGUAGE_SELECTED_ID, languageSelectedId);
				}

				public int getLowRadarId(){
								return getIntPref(LOW_RADAR_ID);
				}

				public void setLowRadarId(int lowRadarId){
								putIntPref(LOW_RADAR_ID, lowRadarId);
				}

				public boolean getSplitScreenStatus(int index){
								if(null == splitScreenMap){
												// 初始化分屏信息
												for(int i = 0; i < 4; i++){
																setSplitScreenMap(i, true);
												}
								}
								return splitScreenMap.get(index);
				}

				public void setSplitScreenMap(int index, boolean isSplitScreen){
								if(null == splitScreenMap){
												splitScreenMap = new HashMap<>();
								}

								splitScreenMap.put(index, isSplitScreen);
				}

				public String getMcuVersion(){
								return getString(MCU_VERSION);
				}

				public void setMcuVersion(String mcuVersion){
								putString(MCU_VERSION, mcuVersion);
				}

				// 视频播放
				public void setIsPlayingVideo(boolean isPlaying){
								putBoolean(IS_PLAYING_VIDEO, isPlaying);
				}

				public boolean getIsPlayingVideo(){
								return getBoolean(IS_PLAYING_VIDEO);
				}

				// 视频音乐
				public void setIsPlayingMusic(boolean isPlaying){
								putBoolean(IS_PLAYING_MUSIC, isPlaying);
				}

				public boolean getIsPlayingMusic(){
								return getBoolean(IS_PLAYING_MUSIC);
				}

				// 是否打开更多应用
				public void setIsOpenAllApp(boolean isOpen){
								putBoolean(IS_OPEN_ALL_APP, isOpen);
				}

				public boolean getIsOpenAllApp(){
								return getBoolean(IS_OPEN_ALL_APP);
				}

				public void putIntPref(String name, int value){
								mSharePres.edit().putInt(name, value).commit();
				}

				public int getIntPref(String name){
								return getIntPref(name, 0);
				}

				public int getIntPref(String name, int def){
								return mSharePres.getInt(name, def);
				}

				public void putLong(String key, long value){
								mSharePres.edit().putLong(key, value).commit();
				}

				public long getLong(String key){
								return mSharePres.getLong(key, 0);
				}

				public void putString(String key, String value){
								mSharePres.edit().putString(key, value).commit();
				}

				public String getString(String key){
								return mSharePres.getString(key, "");
				}

				public String getString(String key, String defaultValue){
								return mSharePres.getString(key, defaultValue);
				}

				public void putBoolean(String key, Boolean value){
								mSharePres.edit().putBoolean(key, value).commit();
				}

				public Boolean getBoolean(String key){
								return mSharePres.getBoolean(key, false);
				}

				public Boolean getBoolean(String key, Boolean def){
								return mSharePres.getBoolean(key, def);
				}

				public float getFloat(String key){
								return mSharePres.getFloat(key, 0f);
				}

				public void putFloat(String key, float value){
								mSharePres.edit().putFloat(key, value).commit();
				}

				public void remove(String key){
								mSharePres.edit().remove(key);
								mSharePres.edit().commit();
				}

}
