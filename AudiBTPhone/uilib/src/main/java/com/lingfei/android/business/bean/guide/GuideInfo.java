package com.lingfei.android.business.bean.guide;

import java.io.Serializable;

/**
	* 高德地图的导向信息实体类
	* Created by heyu on 2016/7/22.
	*/
public class GuideInfo implements Serializable{
				/**
					* 导航类型， 对应的值为int类型<br>
					* 0： GPS导航<br>
					* 1： 模拟导航
					*/
				private int type;
				/**
					* 当前道路名称， 对应的值为String类型
					*/
				private String cur_road_name;
				/**
					* 下一道路名， 对应的值为String类型
					*/
				private String next_road_name;
				/**
					* 距离最近服务区的距离， 对应的值为int类型， 单位： 米
					*/
				private int sapa_dist;
				/**
					* 服务区类型， 对应的值为int类型<br>
					* 0： 高速服务区<br>
					* 1： 其他服务器
					*/
				private int sapa_type;
				/**
					* 距离最近的电子眼距离， 对应的值为int类型， 单位： 米<br>
					*/
				private int camera_dist;
				/**
					* 电子眼类型， 对应的值为int类型<br>
					* 0 测速摄像头， <br>
					* 1为监控摄像头， <br>
					* 2为闯红灯拍照， <br>
					* 3为违章拍照， <br>
					* 4为公交专用道摄像头
					*/
				private int camera_type;
				/**
					* 电子眼限速度， 对应的值为int类型， 无限速则为0， 单位： 公里/小时
					*/
				private int camera_speed;
				/**
					* 下一个将要路过的电子眼编号， 若为-1则对应的道路上没有电子眼， 对应的值为int类型
					*/
				private int camera_index;
				/**
					* 导航转向图标， 对应的值为int类型
					*/
				private int icon;
				/**
					* 路径剩余距离， 对应的值为int类型， 单位： 米
					*/
				private int route_remain_dis;
				/**
					* 路径剩余时间， 对应的值为int类型， 单位： 秒
					*/
				private int route_remain_time;
				/**
					* 当前导航段剩余距离， 对应的值为int类型， 单位： 米
					*/
				private int seg_remain_dis;
				/**
					* 当前导航段剩余时间， 对应的值为int类型， 单位： 秒
					*/
				private int seg_remain_time;
				/**
					* 自车方向， 对应的值为int类型， 单位： 度， 以正北为基准， 顺时针增加
					*/
				private int car_direction;
				/**
					* 自车纬度， 对应的值为double类型
					*/
				private double car_latitude;
				/**
					* 自车经度， 对应的值为double类型
					*/
				private double car_longitude;
				/**
					* 当前道路速度限制， 对应的值为int类型， 单位： 公里/小时
					*/
				private int limited_speed;
				/**
					* 当前自车所在Link， 对应的值为int类型， 从0开始
					*/
				private int cur_seg_num;
				/**
					* 当前位置的前一个形状点号， 对应的值为int类型， 从0开始
					*/
				private int cur_point_num;
				/**
					* 环岛出口序号， 对应的值为int类型， 从0开始， 只有在icon为11和12时有效， 其余为无效值0
					*/
				private int round_about_num;
				/**
					* 环岛出口个数， 对应的值为int类型， 只有在icon为11和12时有效， 其余为无效值0
					*/
				private int round_all_num;
				/**
					* 路径总距离， 对应的值为int类型， 单位： 米
					*/
				private int route_all_dis;
				/**
					* 路径总时间， 对应的值为int类型， 单位： 秒
					*/
				private int route_all_time;
				/**
					* 当前车速， 对应的值为int类型， 单位： 公里/小时
					*/
				private int cur_speed;
				/**
					* 红绿灯个数， 对应的值为int类型
					*/
				private int traffic_light_num;
				/**
					* 服务区个数， 对应的值为int类型
					*/
				private int sapa_num;
				/**
					* 下一个服务区名称， 对应的值为String类型
					*/
				private String sapa_name;

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
				private int road_type;

				public int getType(){
								return type;
				}

				public void setType(int type){
								this.type = type;
				}

				public String getCur_road_name(){
								return cur_road_name;
				}

				public void setCur_road_name(String cur_road_name){
								this.cur_road_name = cur_road_name;
				}

				public String getNext_road_name(){
								return next_road_name;
				}

				public void setNext_road_name(String next_road_name){
								this.next_road_name = next_road_name;
				}

				public int getSapa_dist(){
								return sapa_dist;
				}

				public void setSapa_dist(int sapa_dist){
								this.sapa_dist = sapa_dist;
				}

				public int getSapa_type(){
								return sapa_type;
				}

				public void setSapa_type(int sapa_type){
								this.sapa_type = sapa_type;
				}

				public int getCamera_dist(){
								return camera_dist;
				}

				public void setCamera_dist(int camera_dist){
								this.camera_dist = camera_dist;
				}

				public int getCamera_type(){
								return camera_type;
				}

				public void setCamera_type(int camera_type){
								this.camera_type = camera_type;
				}

				public int getCamera_speed(){
								return camera_speed;
				}

				public void setCamera_speed(int camera_speed){
								this.camera_speed = camera_speed;
				}

				public int getCamera_index(){
								return camera_index;
				}

				public void setCamera_index(int camera_index){
								this.camera_index = camera_index;
				}

				public int getIcon(){
								return icon;
				}

				public void setIcon(int icon){
								this.icon = icon;
				}

				public int getRoute_remain_dis(){
								return route_remain_dis;
				}

				public void setRoute_remain_dis(int route_remain_dis){
								this.route_remain_dis = route_remain_dis;
				}

				public int getRoute_remain_time(){
								return route_remain_time;
				}

				public void setRoute_remain_time(int route_remain_time){
								this.route_remain_time = route_remain_time;
				}

				public int getSeg_remain_dis(){
								return seg_remain_dis;
				}

				public void setSeg_remain_dis(int seg_remain_dis){
								this.seg_remain_dis = seg_remain_dis;
				}

				public int getSeg_remain_time(){
								return seg_remain_time;
				}

				public void setSeg_remain_time(int seg_remain_time){
								this.seg_remain_time = seg_remain_time;
				}

				public int getCar_direction(){
								return car_direction;
				}

				public void setCar_direction(int car_direction){
								this.car_direction = car_direction;
				}

				public double getCar_latitude(){
								return car_latitude;
				}

				public void setCar_latitude(double car_latitude){
								this.car_latitude = car_latitude;
				}

				public double getCar_longitude(){
								return car_longitude;
				}

				public void setCar_longitude(double car_longitude){
								this.car_longitude = car_longitude;
				}

				public int getLimited_speed(){
								return limited_speed;
				}

				public void setLimited_speed(int limited_speed){
								this.limited_speed = limited_speed;
				}

				public int getCur_seg_num(){
								return cur_seg_num;
				}

				public void setCur_seg_num(int cur_seg_num){
								this.cur_seg_num = cur_seg_num;
				}

				public int getCur_point_num(){
								return cur_point_num;
				}

				public void setCur_point_num(int cur_point_num){
								this.cur_point_num = cur_point_num;
				}

				public int getRound_about_num(){
								return round_about_num;
				}

				public void setRound_about_num(int round_about_num){
								this.round_about_num = round_about_num;
				}

				public int getRound_all_num(){
								return round_all_num;
				}

				public void setRound_all_num(int round_all_num){
								this.round_all_num = round_all_num;
				}

				public int getRoute_all_dis(){
								return route_all_dis;
				}

				public void setRoute_all_dis(int route_all_dis){
								this.route_all_dis = route_all_dis;
				}

				public int getRoute_all_time(){
								return route_all_time;
				}

				public void setRoute_all_time(int route_all_time){
								this.route_all_time = route_all_time;
				}

				public int getCur_speed(){
								return cur_speed;
				}

				public void setCur_speed(int cur_speed){
								this.cur_speed = cur_speed;
				}

				public int getTraffic_light_num(){
								return traffic_light_num;
				}

				public void setTraffic_light_num(int traffic_light_num){
								this.traffic_light_num = traffic_light_num;
				}

				public int getSapa_num(){
								return sapa_num;
				}

				public void setSapa_num(int sapa_num){
								this.sapa_num = sapa_num;
				}

				public String getSapa_name(){
								return sapa_name;
				}

				public void setSapa_name(String sapa_name){
								this.sapa_name = sapa_name;
				}

				public int getRoad_type(){
								return road_type;
				}

				public void setRoad_type(int road_type){
								this.road_type = road_type;
				}

				@Override
				public String toString(){
								return "GuideInfo{" +
																"type=" + type +
																", cur_road_name='" + cur_road_name + '\'' +
																", next_road_name='" + next_road_name + '\'' +
																", sapa_dist=" + sapa_dist +
																", sapa_type=" + sapa_type +
																", camera_dist=" + camera_dist +
																", camera_type=" + camera_type +
																", camera_speed=" + camera_speed +
																", camera_index=" + camera_index +
																", icon=" + icon +
																", route_remain_dis=" + route_remain_dis +
																", route_remain_time=" + route_remain_time +
																", seg_remain_time=" + seg_remain_time +
																", car_direction=" + car_direction +
																", car_latitude=" + car_latitude +
																", car_longitude=" + car_longitude +
																", limited_speed=" + limited_speed +
																", cur_seg_num=" + cur_seg_num +
																", cur_point_num=" + cur_point_num +
																", round_about_num=" + round_about_num +
																", round_all_num=" + round_all_num +
																", route_all_dis=" + route_all_dis +
																", route_all_time=" + route_all_time +
																", cur_speed=" + cur_speed +
																", traffic_light_num=" + traffic_light_num +
																", sapa_num=" + sapa_num +
																", sapa_name='" + sapa_name + '\'' +
																", road_type=" + road_type +
																'}';
				}
}
