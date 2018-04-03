package com.lingfei.android.uilib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @ClassName: BaseListAdapter 
 * @Description: 列表适配器的基础类
 * @author yhe
 * @date 2015年1月10日 下午12:50:12 
 * 
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
	protected Context context;
	
	private List<T> mList = new LinkedList<T>();
	
	public List<T> getList(){
		return mList;
	}
	
    public void setList(List<T> list) {
        this.mList = list;
    }

    public BaseListAdapter(Context context) {
        init(context, new ArrayList<T>());
    }

    public BaseListAdapter(Context context, List<T> list) {
        init(context, list);
    }

    private void init(Context context, List<T> list) {
        this.mList = list;
        this.context = context;
    }
	
	public void appendToList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
	}

	public void appendToTopList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(0, list);
		notifyDataSetChanged();
	}

	public void clear() {
		mList.clear();
	}
	@Override
	public int getCount() {
		if (null != mList) {
			return mList.size();
		}
		
		return 0;
	}

	@Override
	public T getItem(int position) {
		if(position > mList.size()-1) {
			return null;
		}
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 
	 * @Title: inflate 
	 * @Description: 加载item布局的view
	 * @param @param layoutResID
	 * @param @param root
	 * @param @return    设定文件 
	 * @return View    返回类型 
	 * @throws
	 */
    protected View inflate(int layoutResID, ViewGroup root) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutResID, root, false);
        return view;
    }
    
}
