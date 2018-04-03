package com.lingfei.android.business.bean;

import java.io.Serializable;

/**
	* VoiceBean
	* 语音播报的实体类
	* @author heyu
	* @date 2017/3/29.
	*/
public class VoiceBean implements Serializable{
				private String content;

				public VoiceBean(){
				}

				public VoiceBean(String content){
								this.content = content;
				}

				public String getContent(){
								return content;
				}

				public void setContent(String content){
								this.content = content;
				}
}
