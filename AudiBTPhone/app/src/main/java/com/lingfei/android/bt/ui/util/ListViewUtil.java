package com.lingfei.android.bt.ui.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.lingfei.android.bt.R;
import com.lingfei.android.uilib.util.BitmapUtil;
import com.lingfei.android.uilib.util.LoggerUtil;

/**
	* 对ListView进行操作的工具类
	* Created by heyu on 2017/2/11.
	*/
public class ListViewUtil{
				private static int mSexItemSelectedIndex = -1; // 记录6Item的当前选择的位置
				private static int mFourItemSelectedIndex = -1; // 记录4Item的当前选择的位置

				public static void setmSexItemSelectedIndex(int mSexItemSelectedIndex){
								ListViewUtil.mSexItemSelectedIndex = mSexItemSelectedIndex;
				}

				public static void setmFourItemSelectedIndex(int mFourItemSelectedIndex){
								ListViewUtil.mFourItemSelectedIndex = mFourItemSelectedIndex;
				}

				/**
					* 回收Bitmap的内存
					*
					* @param iv
					*/
				public static void recycleBitmap(ImageView iv){
								if(null != iv){
												if(null != iv.getDrawable()){
																Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
																iv.setImageDrawable(null);
																if(bitmap != null){
																				bitmap = null;
																}
												}
								}
				}

				public static void onNotifyChangeSixItemSelectedBg(ImageView imageView, View view){
								if(null != imageView && null != view){
												Rect rect = new Rect();
												view.getGlobalVisibleRect(rect);
												int top = rect.top;
												int bottom = rect.bottom;

												LoggerUtil.d("top = " + top + " &  bottom = " + bottom);
												int index = getSelectedItemIndexForSixItem(top, bottom);
												if(mSexItemSelectedIndex == index){
																imageView.setVisibility(View.VISIBLE);
																return;
												}
												else{
																mSexItemSelectedIndex = index;
												}
												updateListViewItemSelected(imageView, index);
								}
				}

				/**
					* 更新界面上显示6项item的选中item背景
					*/
				public static void updateListViewItemSelected(ImageView iv, int position){
								if(null != iv){
												int resId = R.drawable.ic_list_item_selected_0;
												switch(position){
																case 0:
																				resId = R.drawable.ic_list_item_selected_0;
																				break;
																case 1:
																				resId = R.drawable.ic_list_item_selected_1;
																				break;
																case 2:
																				resId = R.drawable.ic_list_item_selected_2;
																				break;
																case 3:
																				resId = R.drawable.ic_list_item_selected_3;
																				break;
																case 4:
																				resId = R.drawable.ic_list_item_selected_4;
																				break;
																case 5:
																				resId = R.drawable.ic_list_item_selected_5;
																				break;
																default:
																				iv.setVisibility(View.GONE);
																				return;
												}
												Bitmap bitmap = BitmapUtil.readBitMap(iv.getContext(), resId);
												if(null != bitmap){
																BitmapDrawable bd = new BitmapDrawable(iv.getContext().getResources(), bitmap);
																iv.setImageDrawable(bd);
												}
												iv.setVisibility(View.VISIBLE);
								}
				}

				// 得到选中view的位置
				public static int getPositionOnClickView(View view){
								if(null != view){
												Rect rect = new Rect();
												view.getGlobalVisibleRect(rect);
												int top = rect.top;
												int bottom = rect.bottom;

												LoggerUtil.d("top = " + top + " &  bottom = " + bottom);
												int index = getSelectedItemIndexForSixItem(top, bottom);
												return index;
								}
								return 0;
				}

				// 获取界面显示6项item的当前选中item背景的index
				public static int getSelectedItemIndexForSixItem(int top, int bottom){
								int index = 0;
								if(top >= 50 && bottom < 138){
												index = 0;
								}
								else if(top >= 120 && bottom < 207){
												index = 1;
								}
								else if(top >= 190 && bottom < 276){
												index = 2;
								}
								else if(top >= 260 && bottom < 345){
												index = 3;
								}
								else if(top >= 330 && bottom < 414){
												index = 4;
								}
								else if(top >= 390 && bottom < 480){
												index = 5;
								}
								LoggerUtil.e("index = " + index);

								return index;
				}

				/**
					* 更新音乐播放界面选中item的背景
					*
					* @param imageView
					* @param view
					*/
				public static void onNotifyChangeMusicItemSelectedBg(ImageView imageView, View view){
								if(null != imageView && null != view){
												Rect rect = new Rect();
												view.getGlobalVisibleRect(rect);
												int top = rect.top;
												int bottom = rect.bottom;

												LoggerUtil.d("top = " + top + " &  bottom = " + bottom);
												int index = getSelectedItemIndexForFourItem(top, bottom);

												if(mFourItemSelectedIndex == index){
																imageView.setVisibility(View.VISIBLE);
																return;
												}
												else{
																mFourItemSelectedIndex = index;
												}
												updateMusicPlayItemSelected(imageView, index);
								}
				}

				/**
					* 更新音乐选中item的背景
					*/
				public static void updateMusicPlayItemSelected(ImageView iv, int position){
								if(null != iv){
												int resId = R.drawable.ic_music_item_selected_0;
												switch(position){
																case 0:
																				resId = R.drawable.ic_music_item_selected_0;
																				break;
																case 1:
																				resId = R.drawable.ic_music_item_selected_1;
																				break;
																case 2:
																				resId = R.drawable.ic_music_item_selected_2;
																				break;
																case 3:
																				resId = R.drawable.ic_music_item_selected_3;
																				break;
																default:
																				break;
												}
												Bitmap bitmap = BitmapUtil.readBitMap(iv.getContext(), resId);
												if(null != bitmap){
																BitmapDrawable bd = new BitmapDrawable(iv.getContext().getResources(), bitmap);
																iv.setImageDrawable(bd);
												}
												iv.setVisibility(View.VISIBLE);
								}
				}

				// 获取界面显示4项item的当前选中item背景的index
				public static int getSelectedItemIndexForFourItem(int top, int bottom){
								int index = 0;
								if(top >= 167 && bottom < 263){
												index = 0;
								}
								else if(top >= 242 && bottom < 338){
												index = 1;
								}
								else if(top >= 317 && bottom < 413){
												index = 2;
								}
								else if(top >= 390 && bottom < 480){
												index = 3;
								}
								LoggerUtil.e("index = " + index);

								return index;
				}

}
