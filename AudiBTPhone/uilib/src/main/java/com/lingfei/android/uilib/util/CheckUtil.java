package com.lingfei.android.uilib.util;

import java.util.ArrayList;

/**
	* Created by Administrator on 2016/8/22.
	*/
public class CheckUtil{
				public static int checkFixIntListIndex(ArrayList<Integer> list, int index){//修正index在list范围

								if(list != null && list.isEmpty() == false){
												if(index < 0){
																index = 0;
												}
												else if(index >= list.size()){
																index = list.size() - 1;
												}
								}
								return index;
				}

				public static int checkFixStringListIndex(ArrayList<String> list, int index){//修正index在list范围

								if(list != null && list.isEmpty() == false){
												if(index < 0){
																index = 0;
												}
												else if(index >= list.size()){
																index = list.size() - 1;
												}
								}
								return index;
				}
}
