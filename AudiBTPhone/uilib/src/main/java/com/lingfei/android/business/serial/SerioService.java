package com.lingfei.android.business.serial;

/**
	* SerioService
	* 与串口通信的接口
	* @author heyu
	* @date 2017/10/26.
	*/
public interface SerioService{
				boolean openserio();
				void armtomcu(byte[] data);
				void closeserio();
				void printByteArrayLog(byte[] datas);
}

