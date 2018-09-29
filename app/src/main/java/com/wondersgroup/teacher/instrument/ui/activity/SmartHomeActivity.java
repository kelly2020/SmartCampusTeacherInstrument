package com.wondersgroup.teacher.instrument.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnson.commonlibs.common_utils.utils.LogUtil;
import com.wondersgroup.teacher.instrument.R;
import com.wondersgroup.teacher.instrument.http.ConfigUtil;
import com.wondersgroup.teacher.instrument.model.HomeResourceModel;
import com.wondersgroup.teacher.instrument.ui.fragment.tab.AppreciationServiceFragment;
import com.wondersgroup.teacher.instrument.ui.fragment.tab.CareerDevelopmentFragment;
import com.wondersgroup.teacher.instrument.ui.fragment.tab.HomePageFragment;
import com.wondersgroup.teacher.instrument.ui.fragment.tab.IndividualFragment;
import com.wondersgroup.teacher.instrument.ui.view.TabLayoutItem;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 18/9/19.
 * Description : 主页面
 * Version :1.0
 */
public class SmartHomeActivity extends SmartBaseActivity {
    @Bind(R.id.tab_layout_home)
    public TabLayout tabLayout;

    private int[] icons = {R.mipmap.home_normal,
            R.mipmap.career_normal,
            R.mipmap.service_normal,
            R.mipmap.individual_normal};

    //四个fragment 的tag
    private String[] TAGS = {"HOME", "SCHEDULE", "ADDRESSBOOK", "INDIVIDUAL"};
    //内容framelayout
    private int CONTENT_ID = R.id.frame_fragment_content;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager mFragmentManager;
    private int currentPage = 0;

    private HomePageFragment homePageFragment;
    private IndividualFragment individualFragment;
    private CareerDevelopmentFragment careerDevelopmentFragment;
    private AppreciationServiceFragment appreciationServiceFragment;

    private boolean isAddSelect = false;


    private HashMap<String, HomeResourceModel> homeModelMap;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SmartHomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);

        ButterKnife.bind(this);
        initView();
        initListener();
        initData(savedInstanceState);
        LogUtil.e("onCreate", "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initData(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            currentPage = 0;
        } else {
            currentPage = savedInstanceState.getInt(ConfigUtil.FRAGMENT_INDEX);
            isAddSelect = savedInstanceState.getBoolean(ConfigUtil.IS_SELECT);

            homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(TAGS[0]);
            careerDevelopmentFragment = (CareerDevelopmentFragment) mFragmentManager.findFragmentByTag(TAGS[1]);
            appreciationServiceFragment = (AppreciationServiceFragment) mFragmentManager.findFragmentByTag(TAGS[2]);
            individualFragment = (IndividualFragment) mFragmentManager.findFragmentByTag(TAGS[3]);
        }

        //默认显示第一个tab
        isSelected(tabLayout.getTabAt(0), true);
        changeTab(0);

        if (homeModelMap == null) {
            homeModelMap = new HashMap<>();
        }
    }


    private void initView() {
        tabLayout.setSelectedTabIndicatorHeight(0);//指示器不显示

        if (icons != null) {
            for (int i = 0; i < icons.length; i++) {
                TabLayoutItem tabItem = new TabLayoutItem(this);
                tabItem.getTabIcon().setImageResource(icons[i]);
                tabItem.getTabView().setText(getResources().getStringArray(R.array.tab_title)[i]);
                tabLayout.addTab(tabLayout.newTab().setCustomView(tabItem));
            }
        }
    }

    public void changeTab(int tabIndex) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (tabIndex != -1) {
            switch (tabIndex) {
                case 0:
                    if (homePageFragment == null) {
                        homePageFragment = new HomePageFragment();
                        transaction.add(CONTENT_ID, homePageFragment, TAGS[0]);
                    } else {
                        transaction.show(homePageFragment);
                    }
                    if (careerDevelopmentFragment != null) {
                        transaction.hide(careerDevelopmentFragment);
                    }
                    if (appreciationServiceFragment != null) {
                        transaction.hide(appreciationServiceFragment);
                    }
                    if (individualFragment != null) {
                        transaction.hide(individualFragment);
                    }
                    break;
                case 1:
                    if (careerDevelopmentFragment == null) {
                        careerDevelopmentFragment = new CareerDevelopmentFragment();
                        transaction.add(CONTENT_ID, careerDevelopmentFragment, TAGS[1]);
                    } else {
                        transaction.show(careerDevelopmentFragment);
                    }
                    if (homePageFragment != null) {
                        transaction.hide(homePageFragment);
                    }
                    if (appreciationServiceFragment != null) {
                        transaction.hide(appreciationServiceFragment);
                    }
                    if (individualFragment != null) {
                        transaction.hide(individualFragment);
                    }
                    break;
                case 2:
                    if (appreciationServiceFragment == null) {
                        appreciationServiceFragment = new AppreciationServiceFragment();
                        transaction.add(CONTENT_ID, appreciationServiceFragment, TAGS[2]);
                    } else {
                        transaction.show(appreciationServiceFragment);
                    }
                    if (homePageFragment != null) {
                        transaction.hide(homePageFragment);
                    }
                    if (careerDevelopmentFragment != null) {
                        transaction.hide(careerDevelopmentFragment);
                    }
                    if (individualFragment != null) {
                        transaction.hide(individualFragment);
                    }
                    break;
                case 3:
                    if (individualFragment == null) {
                        individualFragment = new IndividualFragment();
                        transaction.add(CONTENT_ID, individualFragment, TAGS[3]);
                    } else {
                        transaction.show(individualFragment);
                    }

                    if (appreciationServiceFragment != null) {
                        transaction.hide(appreciationServiceFragment);
                    }
                    if (careerDevelopmentFragment != null) {
                        transaction.hide(careerDevelopmentFragment);
                    }
                    if (individualFragment != null) {
                        transaction.hide(homePageFragment);
                    }
                    break;
            }
        }
        transaction.commit();
    }

    private void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //tab 选中
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //因为消息通知LinearLayout点击不会走此监听方法，所以在OnTabSelected第一行清空消息tab的状态
                isSelected(tabLayout.getTabAt(1), false);
                isSelected(tab, true);
                changeTab(tab.getPosition());
            }

            //tab 未选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                isSelected(tab, false);
            }

            //tab重新选中
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                /*
                 *因为消息通知LinearLayout点击不会走此监听方法，导致fragment切换了，但是重新点击首页时
                 *只会直接走此方法，所以在这里加了判断，如果点击的是首页时，需要执行以下方法
                 */
                if (tab.getPosition() == 0) {
                    isSelected(tabLayout.getTabAt(1), false);
                    changeTab(tab.getPosition());
                }
                isSelected(tab, true);

            }
        });
    }

    /**
     * 选择tabitem 状态
     *
     * @param tab
     * @param isSelected
     */
    public void isSelected(TabLayout.Tab tab, boolean isSelected) {
        View view = tab.getCustomView();
        if (view == null) {
            return;
        }

        TextView textView = null;
        ImageView icon = null;

        icon = (ImageView) view.findViewById(R.id.image_view_tab);
        textView = (TextView) view.findViewById(R.id.text_view_tab);

        // 根据 tab 选择状态图标和文字对应变化
        if (null != icon && null != textView) {
            //选中tab 和未选中 tab 文字颜色 变化
            textView.setTextColor(isSelected ? getResources().getColor(R.color.tab_focus) : getResources().getColor(R.color.color_6666));
            if (tab.getPosition() == 0) {
                //选中tab 和未选中tab icon 变化
                icon.setImageResource(isSelected ? R.mipmap.home_focus : R.mipmap.home_normal);
            } else if (tab.getPosition() == 1) {
                icon.setImageResource(isSelected ? R.mipmap.career_focus : R.mipmap.career_normal);
            } else if (tab.getPosition() == 2) {
                icon.setImageResource(isSelected ? R.mipmap.service_focus : R.mipmap.service_normal);
            } else if (tab.getPosition() == 3) {
                icon.setImageResource(isSelected ? R.mipmap.individual_focus : R.mipmap.individual_normal);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ConfigUtil.FRAGMENT_INDEX, currentPage);
        outState.putBoolean(ConfigUtil.IS_SELECT, isAddSelect);

        super.onSaveInstanceState(outState);
    }


    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }
}
