package com.crossbridgericenoodle.partycenter.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.adapter.MainAdapter;
import com.crossbridgericenoodle.partycenter.base.BaseActivity;
import com.crossbridgericenoodle.partycenter.fragment.AroundPartyFragment;
import com.crossbridgericenoodle.partycenter.fragment.LatestPartyFragment;
import com.crossbridgericenoodle.partycenter.fragment.UserInfoFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends BaseActivity {

    private BottomBar bottomBar = null;
    private ViewPager viewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        viewPager = (ViewPager) findViewById(R.id.vp_main);

        bottomBar.setDefaultTab(R.id.tab_latest);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_latest:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_around:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_info:
                        viewPager.setCurrentItem(2);

                        break;

                }
            }
        });


        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), new Fragment[]{new LatestPartyFragment(), new AroundPartyFragment(), new UserInfoFragment()}));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
