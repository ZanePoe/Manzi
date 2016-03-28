package com.izp.manzifinal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by izp on 2016/2/22.
 */
public class ManziPagerAdapter extends FragmentStatePagerAdapter {
    //定义需要传进来的fragment标题名和fragment实例
    private String[] titles;
    private List<Fragment> fragments;
    public ManziPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
