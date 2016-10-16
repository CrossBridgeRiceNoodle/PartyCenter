package com.crossbridgericenoodle.partycenter.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crossbridgericenoodle.partycenter.Api;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.adapter.MainAdapter;
import com.crossbridgericenoodle.partycenter.fragment.AroundPartyFragment;
import com.crossbridgericenoodle.partycenter.fragment.LatestPartyFragment;
import com.crossbridgericenoodle.partycenter.fragment.UserInfoFragment;
import com.crossbridgericenoodle.partycenter.model.Party;
import com.crossbridgericenoodle.partycenter.model.Position;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

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
                switch (tabId){
                    case R.id.tab_latest:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_around:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_info:
                        viewPager.setCurrentItem(2);
                        Api.getInstance().getNearbyParties(new Position("104.067852,30.680346"), 99999, "歌舞", new Date(), new Api.OnResultListener<Party>() {
                            @Override
                            public void getResult(Party party) {

                            }
                        });
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