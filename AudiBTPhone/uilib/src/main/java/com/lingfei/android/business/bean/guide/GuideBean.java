package com.lingfei.android.business.bean.guide;

/**
 * 导向信息的实体类
 * Created by heyu on 2016/7/23.
 */
public class GuideBean {
    // 自车方向
    private String carDirection;
    // 路段剩余距离
    private String segRemainDis;
    // 当前路名
    private String curRoadName;
    // 下一段路名
    private String nextRoadName;
    // 路径剩余距离
    private String routeRemainDis;
    // 转向图片Id
    private int turnAroundIconId;
    // 电子眼图片Id
    private int cameraTypeIconId;

    public String getCarDirection() {
        return carDirection;
    }

    public void setCarDirection(String carDirection) {
        this.carDirection = carDirection;
    }

    public String getSegRemainDis() {
        return segRemainDis;
    }

    public void setSegRemainDis(String segRemainDis) {
        this.segRemainDis = segRemainDis;
    }

    public String getCurRoadName() {
        return curRoadName;
    }

    public void setCurRoadName(String curRoadName) {
        this.curRoadName = curRoadName;
    }

    public String getNextRoadName() {
        return nextRoadName;
    }

    public void setNextRoadName(String nextRoadName) {
        this.nextRoadName = nextRoadName;
    }

    public String getRouteRemainDis() {
        return routeRemainDis;
    }

    public void setRouteRemainDis(String routeRemainDis) {
        this.routeRemainDis = routeRemainDis;
    }

    public int getTurnAroundIconId() {
        return turnAroundIconId;
    }

    public void setTurnAroundIconId(int turnAroundIconId) {
        this.turnAroundIconId = turnAroundIconId;
    }

    public int getCameraTypeIconId() {
        return cameraTypeIconId;
    }

    public void setCameraTypeIconId(int cameraTypeIconId) {
        this.cameraTypeIconId = cameraTypeIconId;
    }
}
