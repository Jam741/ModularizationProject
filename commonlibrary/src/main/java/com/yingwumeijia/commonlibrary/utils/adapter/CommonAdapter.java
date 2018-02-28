package com.yingwumeijia.commonlibrary.utils.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jam on 2016/3/10.
 * 万能适配器
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected ArrayList<T> mDatas;
    private int mItemLayoutId;

    public CommonAdapter(Context mContext, ArrayList<T> mDatas, int mItemLayoutId) {
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        conver(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    public abstract void conver(ViewHolder helper, T item, int position);

    public List<T> getmDatas() {
        return mDatas;
    }

    public void refresh( ArrayList<T> data){
        if (mDatas == null)mDatas = new ArrayList<T>();
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

}
