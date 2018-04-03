package com.lingfei.android.bt.ui.btphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lingfei.android.bt.R;
import com.lingfei.android.bt.ui.btphone.bean.BTDevice;
import com.lingfei.android.uilib.util.ResourceUtils;
import com.lingfei.android.uilib.util.ShowLogUtil;
import com.lingfei.android.uilib.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
	* Created by Administrator on 2016/7/15.
	*/
public class BTDeviceListAdapter extends BaseAdapter{

				ArrayList<BTDevice> list;
				Context context;

				public BTDeviceListAdapter(Context context, ArrayList<BTDevice> list){
								this.context = context;
								this.list = list;
				}

				public void setList(ArrayList<BTDevice> list){
								this.list = list;
				}

				public ArrayList<BTDevice> getList(){
								return list;
				}

				/**
					* 清除列表数据
					*/
				public void clearList(){
								if(null != this.list && !this.list.isEmpty()){
												this.list.clear();
												notifyDataSetChanged();
								}
				}

				public void addItem(BTDevice item){
								if(null != item && null != this.list){
												this.list.add(item);
												notifyDataSetChanged();
								}
				}

				public void removeItem(BTDevice item){
								if(null != item && null != this.list){
												ShowLogUtil.show("removeItem");
												String addr = item.getDeviceAddr();
												int pos = -1;
												for(int i = 0; i < getCount(); i++){
																BTDevice device = list.get(i);
																if(addr.equals(device.getDeviceAddr())){
																				pos = i;
																				break;
																}
												}
												if(-1 != pos){
																ShowLogUtil.show("remove");
																this.list.remove(pos);
																notifyDataSetChanged();
												}
								}
				}

				@Override
				public int getCount(){
								return list.size();
				}

				@Override
				public BTDevice getItem(int position){
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
												convertView = LayoutInflater.from(context).inflate(R.layout.phone_btdevice_forlist_layout, null);
												viewHolder = new ViewHolder(convertView);
												convertView.setTag(viewHolder);
								}
								else{
												viewHolder = (ViewHolder) convertView.getTag();
								}
								BTDevice btDevice = list.get(position);
								viewHolder.tv_btdevice_name.setText(btDevice.getDeviceName());
								String statusStr = ResourceUtils.getString(R.string.btphone_disconnected);
								switch(btDevice.getStatus()){
												case BTDevice.Status.Connected:{
																statusStr = ResourceUtils.getString(R.string.btphone_connected);
																break;
												}
												case BTDevice.Status.Connecting:{
																statusStr = ResourceUtils.getString(R.string.btphone_connecting);
																break;
												}
												case BTDevice.Status.Disconnected:{
																break;
												}
								}
								viewHolder.tv_device_status.setText(statusStr);
								return convertView;
				}

				static class ViewHolder{
								@Bind(R.id.tv_btdevice_name)
								TextView tv_btdevice_name;

								@Bind(R.id.tv_device_status)
								TextView tv_device_status;

								public ViewHolder(View view){
												ButterKnife.bind(this, view);
								}
				}

				/**
					* 防重复添加
					*
					* @param btDevice
					*/
				public void addItemAndCheckRepeat(BTDevice btDevice){
								if(btDevice != null){
												String name = btDevice.getDeviceName();
												String addr = btDevice.getDeviceAddr();
												if(StringUtil.isNotEmpty(name) &&
																				StringUtil.isNotEmpty(addr)){
																List<BTDevice> devices = getList();
																if(null != devices && !devices.isEmpty()){
																				for(BTDevice bt_i : devices){
																								if(bt_i.getDeviceAddr().equals(addr)){
																												return;
																								}
																				}
																}
																addItem(btDevice);
												}
								}
				}

				/**
					* 设置连接状态
					*
					* @param connectedDevice
					*/
				public void setConnectedStatus(BTDevice connectedDevice){
								List<BTDevice> devices = getList();
								if(!devices.isEmpty() && StringUtil.isNotEmpty(connectedDevice.getDeviceAddr())){
												String addr = connectedDevice.getDeviceAddr();
												for(BTDevice bt_i : devices){
																if(bt_i.getDeviceAddr().equals(addr)){
																				if(connectedDevice.getStatus().equals(BTDevice.Status.Connecting)){
																				}
																				else {
																								bt_i.setStatus(BTDevice.Status.Connected);
																				}
																}
																else{
																				bt_i.setStatus(BTDevice.Status.Disconnected);
																}
												}
												notifyDataSetChanged();
								}
				}

				public void onResetDevicesDisconnect(){
								List<BTDevice> devices = getList();
								if(!devices.isEmpty()){
												for(BTDevice bt_i : devices){
																bt_i.setStatus(BTDevice.Status.Disconnected);
												}
												notifyDataSetChanged();
								}
				}
}
