package com.lingfei.android.uilib.widget.customrecycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
	* Created by heyu on 2016/11/8.
	*/
public class RecyclerViewHolder extends RecyclerView.ViewHolder{
				protected View mContainer;

				public RecyclerViewHolder(View itemView) {
								super(itemView);
								mContainer = itemView;
				}

				public void setData(Object obj) {

				}

				@SuppressWarnings("unchecked")
				public <K extends View> K getView(int resId) {
								return (K) mContainer.findViewById(resId);
				}
}