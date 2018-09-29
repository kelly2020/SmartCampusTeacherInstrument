package com.johnson.commonlibs.common_utils.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by zhangwt
 * date on 15-6-8.
 * description common Adapter
 */
public abstract class CommonAdapter<T> extends ArrayAdapter<T> {

    private int mItemLayoutId;
    private List<T> items;
    private Context mContext;


    public CommonAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.items = objects;
        this.mItemLayoutId = resource;
        this.mContext = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position),position);
        return viewHolder.getConvertView();

    }



    /**
     * 最核心的回调方法
     *
     * @param viewHolder
     * @param item
     */
    public abstract void convert(ViewHolder viewHolder, T item ,int position);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }
}
