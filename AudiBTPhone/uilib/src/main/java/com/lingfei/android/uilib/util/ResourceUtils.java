package com.lingfei.android.uilib.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import com.lingfei.android.uilib.LibApplication;
import com.lingfei.android.uilib.R;


/**
	* Created by sll on 2015/9/6 0006.
	*/
public class ResourceUtils{

				public static int getThemeColor(@NonNull Context context){
								return getThemeAttrColor(context, R.attr.colorPrimary);
				}

				public static int getThemeAttrColor(@NonNull Context context, @AttrRes int attr){
								TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
								try{
												return a.getColor(0, 0);
								}
								finally{
												a.recycle();
								}
				}

				public static int getStatusBarHeight(Context mContext){
								int result = 0;
								int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen",
								                                                       "android");
								if(resourceId > 0){
												result = mContext.getResources().getDimensionPixelSize(resourceId);
								}
								return result;
				}

				/**
					* 根据资源Id获取字符串
					*
					* @param id
					* @return
					*/
				public static String getString(int id){
								String result = "";
								try{
												result = LibApplication.getContext().getResources().getString(id);
								}
								catch(Resources.NotFoundException ex){
												ex.printStackTrace();
								}

								return result;
				}

				/**
					* 获取资源数组
					*
					* @param arrayId
					* @return
					*/
				public static String[] getStringArray(int arrayId){
								String[] resource = null;
								try{
												resource = LibApplication.getContext().getResources().getStringArray(arrayId);
								}
								catch(Resources.NotFoundException ex){
												ex.printStackTrace();
								}

								return resource;
				}

				public static int dpToPx(int dp){
								return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
								                                       LibApplication.getContext().getResources().getDisplayMetrics());
				}

				public static float spToPx(int sp){
								return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
								                                 LibApplication.getContext().getResources().getDisplayMetrics());
				}
}
