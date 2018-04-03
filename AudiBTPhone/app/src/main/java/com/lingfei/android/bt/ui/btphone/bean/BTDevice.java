package com.lingfei.android.bt.ui.btphone.bean;

import java.io.Serializable;

public class BTDevice implements Serializable{

			public static class Status{
						public static final String Online = "BTDevice.Status.Online";
						public static final String Offline = "BTDevice.Status.offline";
						public static final String Connected = "BTDevice.Status.connected";//已连接
						public static final String Disconnected = "BTDevice.Status.disconnected";//已断开
						public static final String Connecting = "BTDevice.Status.connecting";//正在连接
			}

			private String status;//状态
			private Boolean isConnected;//是否连接
			private String deviceName;//名称
			private String deviceId;//id
			private String deviceAddr;//地址
			private String password;

			public BTDevice(){
						setStatus(Status.Disconnected);
			}

			public void setStatus(String status){
						this.status = status;
			}

			public String getStatus(){
						return status;
			}

			public Boolean getConnected(){
						return isConnected;
			}

			public void setConnected(Boolean connected){
						isConnected = connected;
			}

			public String getDeviceName(){
						return deviceName;
			}

			public void setDeviceName(String deviceName){
						this.deviceName = deviceName;
			}

			public String getDeviceId(){
						return deviceId;
			}

			public void setDeviceId(String deviceId){
						this.deviceId = deviceId;
			}

			public String getDeviceAddr(){
						return deviceAddr;
			}

			public void setDeviceAddr(String deviceAddr){
						this.deviceAddr = deviceAddr;
			}

			public String getPassword(){
						return password;
			}

			public void setPassword(String password){
						this.password = password;
			}
}
