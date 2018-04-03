package com.lingfei.android.bt.ui.btmusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingfei.android.bt.BaseApplication;
import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btmusic.bean.MusicMenu;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
	* 蓝牙音乐
	* Created by heyu on 2017/7/22.
	*/
public class BTMusicAdapter extends BaseAdapter{
				private List<MusicMenu> dataList;
				private int currentColor = BaseApplication.getContext().getResources().getColor(R.color.white);
				private int normalColor = BaseApplication.getContext().getResources().getColor(R.color.white);

				private int mSelectedPosition = 0; // 选中item的position

				@Inject
				public BTMusicAdapter(){

				}

				public void setList(List<MusicMenu> datas){
								this.dataList = datas;
								notifyDataSetChanged();
				}

				public List<MusicMenu> getDataList(){
								return dataList;
				}

				public void setmSelectedPosition(int mSelectedPosition){
								this.mSelectedPosition = mSelectedPosition;
				}

				@Override
				public int getCount(){
								if(null == dataList){
												return 0;
								}
								return dataList.size();
				}

				@Override
				public MusicMenu getItem(int position){
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
												convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.btmusic_menu_list_item, parent, false);
												convertView.setBackgroundResource(R.drawable.bg_menu_normal);
												holder = new ViewHolder(convertView);
												convertView.setTag(holder);
								}
								else{
												holder = (ViewHolder) convertView.getTag();
								}

								MusicMenu bean = getItem(position);
								if(null != bean){
												holder.tvTitle.setText(bean.name);

												if(mSelectedPosition == position){
																// 标示当前正在播放的
																holder.imgPlayIndicator.setVisibility(View.VISIBLE);
																holder.tvTitle.setTextColor(currentColor);
												}
												else{
																holder.imgPlayIndicator.setVisibility(View.INVISIBLE);
																holder.tvTitle.setTextColor(normalColor);
												}
								}

								return convertView;
				}

				class ViewHolder{
								@Bind(R.id.iv_play_indicator)
								ImageView imgPlayIndicator;

								@Bind(R.id.tv_title)
								TextView tvTitle;

								public ViewHolder(View itemView){
												ButterKnife.bind(this, itemView);
								}
				}
}
