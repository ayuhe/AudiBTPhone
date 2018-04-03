package com.lingfei.android.bt.ui.btphone.bean;

/**
	* 菜单实体类
	* Created by heyu on 2016/12/30.
	*/
public class MenuBean{
				private int id;
				private int iconId;
				private String title;
				private boolean isSelected;

				public int getId(){
								return id;
				}

				public void setId(int id){
								this.id = id;
				}

				public int getIconId(){
								return iconId;
				}

				public void setIconId(int iconId){
								this.iconId = iconId;
				}

				public String getTitle(){
								return title;
				}

				public void setTitle(String title){
								this.title = title;
				}

				public boolean isSelected(){
								return isSelected;
				}

				public void setSelected(boolean selected){
								isSelected = selected;
				}
}
