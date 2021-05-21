package com.liicon.adapter;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;

/**
 * Adapter适配器基类
 * @author King
 * @param <T> 集合数据类型
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter {
	protected final List<T> data;
	
	public AbsBaseAdapter() {
		this(null);
	}
	public AbsBaseAdapter(final List<T> data) {
		super();

        this.data = data == null ? new ArrayList<T>() : data;
	}
	
	
	public void add(T elem) {
        this.data.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
		if (elem == null) {
			return;
		}
    	this.data.addAll(elem);
        notifyDataSetChanged();
    }
    
    public void addAllTop(List<T> elem) {
    	if (elem == null) {
    		return;
    	}
    	this.data.addAll(0, elem);
    	notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
    	set(this.data.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
    	this.data.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
    	this.data.remove(elem);
    	notifyDataSetChanged();
    }

    public void remove(int index) {
    	this.data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
    	this.data.clear();
    	this.data.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return this.data.contains(elem);
    }

    /** Clear data list */
    public void clear() {
    	this.data.clear();
        notifyDataSetChanged();
    }
    
    /**
     * 获取数据集合
     * @return
     */
	public List<T> getData(){
		return data;
	}

	
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public T getItem(int position) {
		if(position > data.size()-1){
			return null;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
