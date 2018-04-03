package com.lingfei.android.uilib.widget.customrecycleview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;


/**
	* 做了一些焦点记录处理，焦点记录处理只在一下四个方法做了处理
	* 若是需要更新数据，请使用notifyItemRangeXXX()的方法，
	* 否则更新后，可能会出现焦点框位置错乱.
	*/
public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
				protected RecyclerView.AdapterDataObserver mDataObserver;
				protected CustomRecyclerView mRecyclerView;

				public RecyclerAdapter(){
								mDataObserver = new RecyclerView.AdapterDataObserver(){
												@Override
												public void onChanged(){
																super.onChanged();
												}

												@Override
												public void onItemRangeInserted(final int positionStart, int itemCount){
																registerRecoverFocus(positionStart);
																super.onItemRangeInserted(positionStart, itemCount);
												}

												@Override
												public void onItemRangeRemoved(int positionStart, int itemCount){
																registerRecoverFocus(positionStart - itemCount);
																super.onItemRangeRemoved(positionStart, itemCount);
												}

												@Override
												public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount){
																registerRecoverFocus(toPosition);
																super.onItemRangeMoved(fromPosition, toPosition, itemCount);
												}

												@Override
												public void onItemRangeChanged(int positionStart, int itemCount){
																registerRecoverFocus(positionStart);
																super.onItemRangeChanged(positionStart, itemCount);
												}
								};

								registerAdapterDataObserver(mDataObserver);
				}

				/**
					* 注册事件，更新完毕RecyclerView重新获取焦点
					*
					* @param focusPosition 恢复焦点的位置
					*/
				private void registerRecoverFocus(final int focusPosition){
								mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
												@Override
												public void onGlobalLayout(){
																View addView = mRecyclerView.getLayoutManager().findViewByPosition(focusPosition);
																if(addView != null){
																				addView.requestFocus();
																				mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
																}
												}
								});
				}

				@Override
				public void onAttachedToRecyclerView(RecyclerView recyclerView){
								super.onAttachedToRecyclerView(recyclerView);
								mRecyclerView = (CustomRecyclerView) recyclerView;
				}

				@Override
				public void onDetachedFromRecyclerView(RecyclerView recyclerView){
								super.onDetachedFromRecyclerView(recyclerView);
								if(mDataObserver != null){
												unregisterAdapterDataObserver(mDataObserver);
								}
				}

				/**
					* 由于在StaggeredGridLayout布局中，更新完数据后RecyclerView有时候会发生滑动，导致焦点框错位，所以使用这个方法更新数据
					*
					* @param start   开始位置
					* @param count   更新数据的数目
					* @param payLoad 是否全部更新
					*/
				public void notifyItemRangeChangedWrapper(int start, int count, Object payLoad){
								if(mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
												for(int i = start; i < start + count; i++){
																RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(i);
																if(holder != null){
																				Object data = getData(i);
																				if(data != null){
																								// RecyclerViewHolder的子类，重写setData(data)方法,进行相应的数据更新
																								((RecyclerViewHolder) holder).setData(data);
																				}
																}
												}
								}
								else{
												notifyItemRangeChanged(start, count, payLoad);
								}
				}

				public void notifyItemRangeChangedWrapper(int start, int count){
								notifyItemRangeChangedWrapper(start, count, null);
				}

				public void notifyItemChangedWrapper(int position, Object payLoad){
								notifyItemRangeChangedWrapper(position, 1, payLoad);
				}

				public void notifyItemChangedWrapper(int position){
								notifyItemRangeChangedWrapper(position, 1, null);
				}

				/**
					* 用来获取需要更新的数据
					*
					* @param start 当前更新的位置
					* @return 当前更新的数据
					*/
				protected abstract Object getData(int start);
}
