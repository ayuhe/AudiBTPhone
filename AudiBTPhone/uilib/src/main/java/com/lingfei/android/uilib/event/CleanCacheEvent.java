package com.lingfei.android.uilib.event;

/**
	* Created by heyu on 2016/12/16.
	*/
public class CleanCacheEvent{
				private int progressMax;
				private int progress;
				private String packageName;
				private boolean success;

				public CleanCacheEvent(int progressMax, int progress, String packageName, boolean success){
								this.progressMax = progressMax;
								this.progress = progress;
								this.packageName = packageName;
								this.success = success;
				}

				public int getProgressMax(){
								return progressMax;
				}

				public void setProgressMax(int progressMax){
								this.progressMax = progressMax;
				}

				public int getProgress(){
								return progress;
				}

				public void setProgress(int progress){
								this.progress = progress;
				}

				public String getPackageName(){
								return packageName;
				}

				public void setPackageName(String packageName){
								this.packageName = packageName;
				}

				public boolean isSuccess(){
								return success;
				}

				public void setSuccess(boolean success){
								this.success = success;
				}
}
