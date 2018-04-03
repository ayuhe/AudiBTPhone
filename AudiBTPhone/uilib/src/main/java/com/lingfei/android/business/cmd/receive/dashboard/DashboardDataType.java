package com.lingfei.android.business.cmd.receive.dashboard;

/**
	* 仪表盘数据类型
	* （1）当前时速
	* （2）安全带状态：是否系上
	* （3）手刹状态：是否拉手刹
	* （4）当前油量（和总油量，当前油量少于多少时，发出油量少的警告）
	* （5）油耗
	* （6）里程
	* （7）可达里程
	* （8）当前发动机转速
	* （9）当前档位
	* （10）当前水箱水温（高于多少度，发出高温警告）
	* Created by heyu on 2016/9/18.
	*/
public enum DashboardDataType{
				CURRENT_SPEED, // 当前时速
				SAFETY_BELT, // 安全带
				HANDBRAKE, // 手刹
				OIL_MASS,// 油量
				OIL_WEAR, // 油耗
				MILEAGE, // 里程
				TRAVEL_MILEAGE, // 可达里程
				ENGINE_REVOLUTION, // 发动机转速
				GEAR, // 档位
				WATER_TEMPERATURE // 水箱水温
}
