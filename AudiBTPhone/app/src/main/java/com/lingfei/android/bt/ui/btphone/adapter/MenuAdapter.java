package com.lingfei.android.bt.ui.btphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.bean.MenuBean;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
	* Created by heyu on 2017/2/16.
	*/
public class MenuAdapter extends BaseAdapter{
				private LayoutInflater mInflater;

				private List<MenuBean> dataList;

				private int clickItemPos = -1;

				@Inject
				public MenuAdapter(Context context){
								mInflater = LayoutInflater.from(context);
				}

				public void setList(List<MenuBean> dates){
								this.dataList = dates;
								notifyDataSetChanged();
				}

				@Override
				public int getCount(){
								if(null == dataList){
												return 0;
								}
								return dataList.size();
				}

				@Override
				public MenuBean getItem(int position){
								if(null == dataList){
												return null;
								}
								return dataList.get(position);
				}

				@Override
				public long getItemId(int position){
								return position;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent){
								ViewHolder holder = null;
								if(convertView == null){
												convertView = mInflater.inflate(R.layout.left_menu_title_item, parent, false);
												holder = new ViewHolder(convertView);
												convertView.setTag(holder);
								}
								else{
												holder = (ViewHolder) convertView.getTag();
								}

								if (position == getClickItemPos()){
												convertView.setBackgroundResource(R.drawable.bg_item_focus);
								}else{
												convertView.setBackgroundResource(R.drawable.selector_menu);
								}

								MenuBean bean = getItem(position);
								if(null != bean){
												holder.tv_title.setText(bean.getTitle());
								}

								return convertView;
				}

				static class ViewHolder{
								@Bind(R.id.tv_title)
								TextView tv_title;

								public ViewHolder(View itemView){
												ButterKnife.bind(this, itemView);
								}
				}

				public int getClickItemPos(){
								return clickItemPos;
				}

				public void setClickItemPos(int clickItemPos){
								this.clickItemPos = clickItemPos;
								notifyDataSetChanged();
				}
}
