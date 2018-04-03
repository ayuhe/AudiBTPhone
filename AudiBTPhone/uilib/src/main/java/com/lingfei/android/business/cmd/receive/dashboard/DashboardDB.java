package com.lingfei.android.business.cmd.receive.dashboard;

/**
	* 保存仪表盘数据
	* Created by heyu on 2016/9/26.
	*/
public class DashboardDB{

				private static DashboardDB instance = null;
				private DashboardInfo dashboardInfo; // 仪表的数据

				public static DashboardDB getInstance(){
								if(null == instance){
												instance = new DashboardDB();
								}
								return instance;
				}

				public DashboardInfo getDashboardInfo(){
								return dashboardInfo;
				}

				public void setDashboardInfo(DashboardInfo dashboardInfo){
								this.dashboardInfo = dashboardInfo;
				}

}
