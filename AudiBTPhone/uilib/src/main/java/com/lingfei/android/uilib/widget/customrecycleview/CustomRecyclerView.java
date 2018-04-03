package com.lingfei.android.uilib.widget.customrecycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
	* Created by heyu on 2016/11/8.
	*/
public class CustomRecyclerView extends RecyclerView{

				private ItemListener mItemListener;

				private int mSaveFocusPosition = -1;

				public CustomRecyclerView(Context context){
								this(context, null);
				}

				public CustomRecyclerView(Context context, @Nullable AttributeSet attrs){
								this(context, attrs, 0);
				}

				public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle){
								super(context, attrs, defStyle);
								init();
				}

				private void init(){
								setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
								setChildrenDrawingOrderEnabled(true);
								setWillNotDraw(true);
								setHasFixedSize(false);
								setOverScrollMode(View.OVER_SCROLL_NEVER);
								setClipChildren(false);
								setClipToPadding(false);
								setClickable(false);
								setFocusable(true);
								setFocusableInTouchMode(true);

								mItemListener = new ItemListener(){
												@Override
												public boolean onLongClick(View view){
																if(mOnItemListener != null){
																				return mOnItemListener.onItemLongClick(CustomRecyclerView.this, view, getChildLayoutPosition(view));
																}
																return false;
												}

												@Override
												public void onFocusChange(View view, boolean focus){
																if(null != mOnItemListener){
																				if(view != null){
																								view.setSelected(focus);
																								if(focus){
																												mSaveFocusPosition = getChildLayoutPosition(view);
																												mOnItemListener.onItemSelected(CustomRecyclerView.this, view, getChildLayoutPosition(view));
																								}
																								else{
																												mOnItemListener.onItemPreSelected(CustomRecyclerView.this, view, getChildLayoutPosition(view));
																								}
																				}
																}
												}

												@Override
												public void onClick(View itemView){
																if(null != mOnItemListener){
																				mOnItemListener.onItemClick(CustomRecyclerView.this, itemView, getChildLayoutPosition(itemView));
																}
												}
								};
				}

				int position = 0;

				@Override
				protected int getChildDrawingOrder(int childCount, int i){
								View view = getFocusedChild();
								if(null != view){
												position = getChildAdapterPosition(view) - getFirstVisiblePosition();
												if(position < 0){
																return i;
												}
												else{
																if(i == childCount - 1){ // 这是最后一个需要刷新的item
																				if(position > i){
																								position = i;
																				}
																				return position;
																}

																if(i == position){ // 这是原本要在最后一个刷新的item
																				return childCount - 1;
																}
												}
								}
								return i;
				}

				@Override
				public void onChildAttachedToWindow(View child){
								if(!ViewCompat.hasOnClickListeners(child)){
												child.setOnClickListener(mItemListener);
								}

								child.setOnLongClickListener(mItemListener);

								if(child.getOnFocusChangeListener() == null){
												child.setOnFocusChangeListener(mItemListener);
								}
				}

				public interface OnItemListener{
								/**
									* 焦点离开
									*
									* @param parent   CustomRecyclerView
									* @param itemView 失去焦点的itemView
									* @param position 失去焦点item的位置
									* @return true表示用户处理焦点移动
									*/
								boolean onItemPreSelected(CustomRecyclerView parent, View itemView, int position);

								/**
									* 获取焦点
									*
									* @param parent   CustomRecyclerView
									* @param itemView 获取焦点的itemView
									* @param position 获取焦点item的位置
									* @return true表示用户处理焦点移动
									*/
								boolean onItemSelected(CustomRecyclerView parent, View itemView, int position);

								void onItemClick(CustomRecyclerView parent, View itemView, int position);

								boolean onItemLongClick(CustomRecyclerView parent, View itemView, int position);
				}

				private interface ItemListener extends OnClickListener, OnFocusChangeListener, OnLongClickListener{
				}

				private OnItemListener mOnItemListener;

				public void setOnItemListener(OnItemListener mOnItemListener){
								this.mOnItemListener = mOnItemListener;
				}

				public int getFirstVisiblePosition(){
								if(getChildCount() == 0)
												return 0;
								else
												return getChildLayoutPosition(getChildAt(0));
				}

				@Override
				public void onWindowFocusChanged(boolean hasWindowFocus){
								if(hasWindowFocus){
												recoverFocus();
								}
				}

				private void recoverFocus(){
								if (null != getLayoutManager()){
												View saveFocusView = getLayoutManager().findViewByPosition(mSaveFocusPosition);
												if(saveFocusView != null){
																saveFocusView.requestFocus();
																saveFocusView.requestFocusFromTouch();
												}
								}
				}

}
