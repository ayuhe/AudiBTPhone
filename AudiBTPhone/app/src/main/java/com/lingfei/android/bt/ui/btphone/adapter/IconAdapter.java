package com.lingfei.android.bt.ui.btphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.bean.MenuBean;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
	* Created by heyu on 2017/2/16.
	*/
public class IconAdapter extends BaseAdapter{
				private LayoutInflater mInflater;
				private List<MenuBean> dataList;

				@Inject
				public IconAdapter(Context context){
								mInflater = LayoutInflater.from(context);
				}

				public void setList(List<MenuBean> datas){
								this.dataList = datas;
								notifyDataSetChanged();
				}

				public void notifyChangeSelected(int pos, int resId){
								for (int i = 0; i < getCount(); i++){
												switch(i){
																case 0:
																				getItem(i).setIconId(R.drawable.ic_bt_dial_normal);
																				break;
																case 1:
																				getItem(i).setIconId(R.drawable.ic_bt_contact_normal);
																				break;
																case 2:
																				getItem(i).setIconId(R.drawable.ic_bt_device_normal);
																				break;
																default:
																				break;
												}
								}

								getItem(pos).setIconId(resId);
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
												convertView = mInflater.inflate(R.layout.left_menu_icon_item, parent, false);
												holder = new ViewHolder(convertView);
												convertView.setTag(holder);
								}
								else{
												holder = (ViewHolder) convertView.getTag();
								}

								MenuBean bean = getItem(position);
								if(null != bean){
												holder.iv_icon.setImageResource(bean.getIconId());
								}

								return convertView;
				}

				static class ViewHolder{
								@Bind(R.id.iv_icon)
								ImageView iv_icon;

								public ViewHolder(View itemView){
												ButterKnife.bind(this, itemView);
								}
				}
}
