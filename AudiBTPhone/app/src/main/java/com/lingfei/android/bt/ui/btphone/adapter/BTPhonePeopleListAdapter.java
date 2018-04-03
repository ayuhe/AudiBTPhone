package com.lingfei.android.bt.ui.btphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.bean.BTPhonePeople;

import java.util.ArrayList;
import java.util.List;

/**
	* Created by Administrator on 2016/7/15.
	*/
public class BTPhonePeopleListAdapter extends BaseAdapter{

				List<BTPhonePeople> list;
				Context context;
				String type;

				public BTPhonePeopleListAdapter(Context context, ArrayList<BTPhonePeople> list, String type){
								this.context = context;
								this.list = list;
								this.type = type;
				}

				public void setList(List<BTPhonePeople> list){
								if(list != null){
												this.list = list;
												notifyDataChanged();
								}
				}

				private void notifyDataChanged(){
								try{
												notifyDataSetChanged();
								}
								catch(IllegalStateException ex){
												ex.printStackTrace();
								}
				}

				public List<BTPhonePeople> getList(){
								return list;
				}

				/**
					* 清除列表数据
					*/
				public void clearList(){
								if(null != this.list && !this.list.isEmpty()){
												this.list.clear();
												notifyDataChanged();
								}
				}

				public void addItem(BTPhonePeople item){
								if(item != null && this.list != null){
												this.list.add(item);
												notifyDataChanged();
								}
				}

				@Override
				public int getCount(){
								return list.size();
				}

				@Override
				public BTPhonePeople getItem(int position){
								return list.get(position);
				}

				@Override
				public long getItemId(int position){
								return position;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent){
								ViewHolder viewHolder;
								if(convertView == null){
												convertView =
																				LayoutInflater.from(context).inflate(R.layout.phone_book_forlist_layout, null);
												ImageView iv_first_column = (ImageView) convertView.findViewById(R.id.iv_first_column);
												TextView tv_second_column = (TextView) convertView.findViewById(R.id.tv_second_column);
												TextView tv_third_column = (TextView) convertView.findViewById(R.id.tv_third_column);

												viewHolder = new ViewHolder(iv_first_column, tv_second_column, tv_third_column);
												convertView.setTag(viewHolder);
								}
								else{
												viewHolder = (ViewHolder) convertView.getTag();
								}
								BTPhonePeople btPhoneBook = list.get(position);
								switch(type){
												case BTPhonePeople.Type.Missed:{

																break;
												}
												case BTPhonePeople.Type.Called:{

																break;
												}
												case BTPhonePeople.Type.Answered:{

																break;
												}
												case BTPhonePeople.Type.Book:{
																viewHolder.iv_first_column.setImageResource(R.drawable.icon_book_people);
																break;
												}
								}

								viewHolder.tv_second_column.setText(btPhoneBook.getPeopleName());
								viewHolder.tv_third_column.setText(btPhoneBook.getPhoneNumber());
								return convertView;
				}

				static class ViewHolder{
								ImageView iv_first_column;
								TextView tv_second_column;
								TextView tv_third_column;

								public ViewHolder(ImageView iv_first_column, TextView tv_second_column, TextView tv_third_column){

												this.iv_first_column = iv_first_column;
												this.tv_second_column = tv_second_column;
												this.tv_third_column = tv_third_column;
								}
				}
}
