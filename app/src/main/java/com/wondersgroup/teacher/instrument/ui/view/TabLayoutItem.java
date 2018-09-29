package com.wondersgroup.teacher.instrument.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.teacher.instrument.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zhangwentao on 16/10/31.
 * Description :tabitem
 * Version :1.0
 */
public class TabLayoutItem extends LinearLayout {
    @Bind(R.id.text_view_tab)
    TextView tabView;
     @Bind(R.id.image_view_tab)
    ImageView tabIcon;
    @Bind(R.id.image_read_point)
    ImageView imageRedPoint;

    public TabLayoutItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, this, true);
        ButterKnife.bind(this,view);
    }

    public TabLayoutItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabLayoutItem(Context context) {
        this(context,null);
    }

    public TextView getTabView() {
        return tabView;
    }

    public void setTabView(TextView tabView) {
        this.tabView = tabView;
    }

    public ImageView getTabIcon() {
        return tabIcon;
    }

    public void setTabIcon(ImageView tabIcon) {
        this.tabIcon = tabIcon;
    }

    public ImageView getImageRedPoint() {
        return imageRedPoint;
    }
}
