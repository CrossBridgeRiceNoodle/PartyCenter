package com.crossbridgericenoodle.partycenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qianzise on 2016/10/15 0015.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public MainAdapter(FragmentManager fm, Fragment[] fs) {
        super(fm);
        fragments = fs;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
