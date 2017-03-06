package com.bangware.shengyibao.debtowed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *FragmentPagerAdapter对viewpager进行数据适配
 * Created by bangware on 2016/8/23.
 */
public class FragmentAdapter extends FragmentPagerAdapter{
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
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
}
