package com.lingfei.android.bt.ui.btphone.bean;

import java.io.Serializable;

/**
	* Created by Administrator on 2016/7/7.
	*/
public class BTPhonePeople implements Serializable{
			//	public int t;
			private String type;

			public static class Type{
						public static final String Missed = "BTPhonePeople.Missed";//未接
						public static final String Answered = "BTPhonePeople.Answered";//已接
						public static final String Called = "BTPhonePeople.Called";//已拨
						public static final String Book = "BTPhonePeople.Book";//电话本
						public static final String Callin = "BTPhonePeople.Callin";//来电
						public static final String CALL_OUT = "BTPhonePeople.CALL_OUT";//去电
						public static final String TALKING = "BTPhonePeople.TALKING";//去电
			}

			public void setType(String type){
						this.type = type;
			}

			public String getType(){
						return type;
			}

			private String peopleName;
			private String phoneNumber;
				private String pinyin;

			public BTPhonePeople(){

						this.peopleName = "";
						this.phoneNumber = "";
			}

			public BTPhonePeople(String peopleName, String phoneNumber){

						this.peopleName = peopleName;
						this.phoneNumber = phoneNumber;
			}

			public String getPeopleName(){
						return peopleName;
			}

			public void setPeopleName(String peopleName){
						this.peopleName = peopleName;
			}

			public String getPhoneNumber(){
						return phoneNumber;
			}

			public void setPhoneNumber(String phoneNumber){
						this.phoneNumber = phoneNumber;
			}

				public String getPinyin(){
								return pinyin;
				}

				public void setPinyin(String pinyin){
								this.pinyin = pinyin;
				}
}
