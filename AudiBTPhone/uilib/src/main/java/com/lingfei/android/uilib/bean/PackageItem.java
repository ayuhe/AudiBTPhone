
package com.lingfei.android.uilib.bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * @author yhe
 * @ClassName: PackageItem
 * @Description: 安装包的信息实体类
 * @date 2014年12月9日 下午8:39:13
 */
public class PackageItem implements Serializable {
    private static final long serialVersionUID = -4740800540912462888L;

    private int iconId; // 应用的本地图标

    private int launchType; // 启动类型

    private Drawable icon; // 应用图标

    private String name; // 应用名称

    private String packageName; // 包名

    private String className; // 启动类名

    private Fragment fragment; // Fragment的对象

    private Intent intent;

    private byte[] cmd;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName(){
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getLaunchType() {
        return launchType;
    }

    public void setLaunchType(int launchType) {
        this.launchType = launchType;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public byte[] getCmd(){
        return cmd;
    }

    public void setCmd(byte[] cmd){
        this.cmd = cmd;
    }
}
