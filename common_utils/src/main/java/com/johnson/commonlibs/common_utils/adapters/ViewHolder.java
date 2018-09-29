package com.johnson.commonlibs.common_utils.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with IntelliJ IDEA.
 * Author: zhangwt
 * Date: 14-12-10
 * Time: 下午2:01
 * Description:通用的ViewHolder用于优化Adapter
 */
public class ViewHolder {
    /**
     * 存放view的视图，SparseArray的性能要好过hasMap的
     */
    private final SparseArray<View> mViews;
    /**
     * 通过布局获取到的views
     */
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过view的id可以获取该组件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 供将来的getView返回
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }
}
