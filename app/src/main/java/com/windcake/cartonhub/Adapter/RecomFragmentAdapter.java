package com.windcake.cartonhub.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by windcake on 16/6/6.
 */
public class RecomFragmentAdapter extends FragmentPagerAdapter
{
    private List<Fragment> list = null;
    private String[] arrTabTitle = null;

    public RecomFragmentAdapter(FragmentManager fm , List<Fragment> list , String[] arrTabTitle) {
        super(fm);
        this.list = list;
        this.arrTabTitle = arrTabTitle;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return arrTabTitle[position];

    }
}
