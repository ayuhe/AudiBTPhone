package com.lingfei.android.business.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lingfei.android.business.bean.guide.GuideInfo;
import com.lingfei.android.business.bean.guide.GuideInfoExtraKey;
import com.lingfei.android.business.service.NaviIntentService;

/**
	* 导航的广播接收器
	* 需要在具体应用的AndroidManifest.xml里进行声明注册*
	*/
public class NaviReceiver extends BroadcastReceiver{
				private static final String ACTION = "AUTONAVI_STANDARD_BROADCAST_SEND";
				private static final String KEY_TYPE = "KEY_TYPE";

				public static final String EXTRA_STATE = "EXTRA_STATE";
				public static final String GUIDE_INFO = "guide_info";

				private static final int KEY_TYPE_GUIDE_INFO = 10001; // 引导信息接口
				private static final int KEY_TYPE_STATE = 10019; // 运行状态接口

				@Override
				public void onReceive(Context context, Intent intent){
								// 接收广播
								String action = intent.getAction();
								if(ACTION.equals(action)){
												int key_type = intent.getIntExtra(KEY_TYPE, 0);
												switch(key_type){
																case KEY_TYPE_GUIDE_INFO:
																				handleGuideInfo(context, intent);
																				break;
																case KEY_TYPE_STATE:
																				handleNaviState(context, intent);
																				break;
																default:
																				break;
												}
								}
				}

				/**
					* 导航状态
					*
					* @param context
					* @param intent
					*/
				private void handleNaviState(Context context, Intent intent){
								int extra_state = intent.getIntExtra(EXTRA_STATE, -1);
								Intent intentNavi = new Intent(context, NaviIntentService.class);
								intentNavi.putExtra(EXTRA_STATE, extra_state);
								context.startService(intentNavi);
				}

				/**
					* 导向信息
					*
					* @param context
					* @param intent
					*/
				private void handleGuideInfo(Context context, Intent intent){
								/**
									* 导航类型， 对应的值为int类型<br>
									* 0： GPS导航<br>
									* 1： 模拟导航
									*/
								int type = intent.getIntExtra(GuideInfoExtraKey.TYPE, 0);
								/**
									* 当前道路名称， 对应的值为String类型
									*/
								String cur_road_name = intent.getStringExtra(GuideInfoExtraKey.CUR_ROAD_NAME);
								/**
									* 下一道路名， 对应的值为String类型
									*/
								String next_road_name = intent.getStringExtra(GuideInfoExtraKey.NEXT_ROAD_NAME);
								/**
									* 距离最近服务区的距离， 对应的值为int类型， 单位： 米
									*/
								int sapa_dist = intent.getIntExtra(GuideInfoExtraKey.SAPA_DIST, 0);
								/**
									* 服务区类型， 对应的值为int类型<br>
									* 0： 高速服务区<br>
									* 1： 其他服务器
									*/
								int sapa_type = intent.getIntExtra(GuideInfoExtraKey.SAPA_TYPE, 0);
								/**
									* 服务区个数， 对应的值为int类型
									*/
								int sapa_num = intent.getIntExtra(GuideInfoExtraKey.SAPA_NUM, 0);
								/**
									* 下一个服务区名称， 对应的值为String类型
									*/
								String sapa_name = intent.getStringExtra(GuideInfoExtraKey.SAPA_NAME);
								/**
									* 距离最近的电子眼距离， 对应的值为int类型， 单位： 米<br>
									*/
								int camera_dist = intent.getIntExtra(GuideInfoExtraKey.CAMERA_DIST, 0);
								/**
									* 电子眼类型， 对应的值为int类型<br>
									* 0 测速摄像头， <br>
									* 1为监控摄像头， <br>
									* 2为闯红灯拍照， <br>
									* 3为违章拍照， <br>
									* 4为公交专用道摄像头
									*/
								int camera_type = intent.getIntExtra(GuideInfoExtraKey.CAMERA_TYPE, 0);
								/**
									* 电子眼限速度， 对应的值为int类型， 无限速则为0， 单位： 公里/小时
									*/
								int camera_speed = intent.getIntExtra(GuideInfoExtraKey.CAMERA_SPEED, 0);
								/**
									* 下一个将要路过的电子眼编号， 若为-1则对应的道路上没有电子眼， 对应的值为int类型
									*/
								int camera_index = intent.getIntExtra(GuideInfoExtraKey.CAMERA_INDEX, 0);
								/**
									* 导航转向图标， 对应的值为int类型
									*/
								int icon = intent.getIntExtra(GuideInfoExtraKey.ICON, 0);
								/**
									* 路径剩余距离， 对应的值为int类型， 单位： 米
									*/
								int route_remain_dis = intent.getIntExtra(GuideInfoExtraKey.ROUTE_REMAIN_DIS, 0);
								/**
									* 路径剩余时间， 对应的值为int类型， 单位： 秒
									*/
								int route_remain_time = intent.getIntExtra(GuideInfoExtraKey.ROUTE_REMAIN_TIME, 0);
								/**
									* 当前导航段剩余距离， 对应的值为int类型， 单位： 米
									*/
								int seg_remain_dis = intent.getIntExtra(GuideInfoExtraKey.SEG_REMAIN_DIS, 0);
								/**
									* 当前导航段剩余时间， 对应的值为int类型， 单位： 秒
									*/
								int seg_remain_time = intent.getIntExtra(GuideInfoExtraKey.SEG_REMAIN_TIME, 0);
								/**
									* 自车方向， 对应的值为int类型， 单位： 度， 以正北为基准， 顺时针增加
									*/
								int car_direction = intent.getIntExtra(GuideInfoExtraKey.CAR_DIRECTION, 0);
								/**
									* 自车纬度， 对应的值为double类型
									*/
								double car_latitude = intent.getDoubleExtra(GuideInfoExtraKey.CAR_LATITUDE, 0);
								/**
									* 自车经度， 对应的值为double类型
									*/
								double car_longitude = intent.getDoubleExtra(GuideInfoExtraKey.CAR_LONGITUDE, 0);
								/**
									* 当前道路速度限制， 对应的值为int类型， 单位： 公里/小时
									*/
								int limited_speed = intent.getIntExtra(GuideInfoExtraKey.LIMITED_SPEED, 0);
								/**
									* 当前自车所在Link， 对应的值为int类型， 从0开始
									*/
								int cur_seg_num = intent.getIntExtra(GuideInfoExtraKey.CUR_SEG_NUM, 0);
								/**
									* 当前位置的前一个形状点号， 对应的值为int类型， 从0开始
									*/
								int cur_point_num = intent.getIntExtra(GuideInfoExtraKey.CUR_POINT_NUM, 0);
								/**
									* 环岛出口序号， 对应的值为int类型， 从0开始， 只有在icon为11和12时有效， 其余为无效值0
									*/
								int round_about_num = intent.getIntExtra(GuideInfoExtraKey.ROUND_ABOUT_NUM, 0);
								/**
									* 环岛出口个数， 对应的值为int类型， 只有在icon为11和12时有效， 其余为无效值0
									*/
								int round_all_num = intent.getIntExtra(GuideInfoExtraKey.ROUND_ALL_NUM, 0);
								/**
									* 路径总距离， 对应的值为int类型， 单位： 米
									*/
								int route_all_dis = intent.getIntExtra(GuideInfoExtraKey.ROUTE_ALL_DIS, 0);
								/**
									* 路径总时间， 对应的值为int类型， 单位： 秒
									*/
								int route_all_time = intent.getIntExtra(GuideInfoExtraKey.ROUTE_ALL_TIME, 0);
								/**
									* 当前车速， 对应的值为int类型， 单位： 公里/小时
									*/
								int cur_speed = intent.getIntExtra(GuideInfoExtraKey.CUR_SPEED, 0);
								/**
									* 红绿灯个数， 对应的值为int类型
									*/
								int traffic_light_num = intent.getIntExtra(GuideInfoExtraKey.TRAFFIC_LIGHT_NUM, 0);

								/**
									* 当前道路类型， 对应的值为int类型
									* 0： 高速公路
									* 1： 国道
									* 2： 省道
									* 3： 县道
									* 4： 乡公路
									* 5： 县乡村内部道路
									* 6： 主要大街、 城市快速道
									* 7： 主要道路
									* 8： 次要道路
									* 9： 普通道路
									* 10： 非导航道路
									*/
								int road_type = intent.getIntExtra(GuideInfoExtraKey.ROAD_TYPE, 0);

								GuideInfo guideInfo = new GuideInfo();
								guideInfo.setType(type);
								guideInfo.setCamera_dist(camera_dist);
								guideInfo.setCamera_index(camera_index);
								guideInfo.setCamera_speed(camera_speed);
								guideInfo.setCamera_type(camera_type);
								guideInfo.setCar_direction(car_direction);
								guideInfo.setCar_latitude(car_latitude);
								guideInfo.setCar_longitude(car_longitude);
								guideInfo.setCur_point_num(cur_point_num);
								guideInfo.setCur_road_name(cur_road_name);
								guideInfo.setCur_seg_num(cur_seg_num);
								guideInfo.setCur_speed(cur_speed);
								guideInfo.setIcon(icon);
								guideInfo.setLimited_speed(limited_speed);
								guideInfo.setNext_road_name(next_road_name);
								guideInfo.setRoad_type(road_type);
								guideInfo.setRound_about_num(round_about_num);
								guideInfo.setRound_all_num(round_all_num);
								guideInfo.setRoute_all_dis(route_all_dis);
								guideInfo.setRoute_all_time(route_all_time);
								guideInfo.setRoute_remain_dis(route_remain_dis);
								guideInfo.setRoute_remain_time(route_remain_time);
								guideInfo.setSapa_dist(sapa_dist);
								guideInfo.setSapa_name(sapa_name);
								guideInfo.setSapa_num(sapa_num);
								guideInfo.setSapa_type(sapa_type);
								guideInfo.setSeg_remain_dis(seg_remain_dis);
								guideInfo.setSeg_remain_time(seg_remain_time);
								guideInfo.setTraffic_light_num(traffic_light_num);

								Intent intentNavi = new Intent(context, NaviIntentService.class);
								intentNavi.putExtra(GUIDE_INFO, guideInfo);
								context.startService(intentNavi);
				}
}
