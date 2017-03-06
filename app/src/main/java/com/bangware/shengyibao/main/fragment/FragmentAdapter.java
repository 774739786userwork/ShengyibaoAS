package com.bangware.shengyibao.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter对viewpager进行数据适配
 * Created by bangware on 2016/12/28.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();
//    FragmentManager fm;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
//        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    //getItem()返回的是要显示的fragent对象
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    //返回的是viewpager页面的数量
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    /*@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragmentList.get(position);
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }*/
}
