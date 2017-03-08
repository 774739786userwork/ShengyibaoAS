package com.bangware.shengyibao.main.fragment.fundmanager;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.debtowed.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class FundReportActivity extends AppCompatActivity {
    private ViewPager mReport_PurchaseVp;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    /**
     * 切换的Fragment页面
     */
    private DayFundReportFragment mDayReportFg;
    private WeekFundReportFragment mWeekReportFg;
    private MonthFundReportFragment mMonthReportFg;
    /**
     * Tab显示内容TextView
     */
    private TextView mTabDayTv,mTabWeekTv,mTabMonthTv;
    private LinearLayout mId_tab_day_ll,mId_tab_week_ll,mId_tab_month_ll;

    /**
     * tab 导航线
     */
    private ImageView mTabLineIv,mBackIv;

    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_report);

        init();
        setListener();
        initTabLineWidth();
    }

    private void init(){
        mReport_PurchaseVp = (ViewPager) findViewById(R.id.id_purchasereport_vp);
        mTabDayTv = (TextView) findViewById(R.id.id_dayreport_tv);
        mTabWeekTv = (TextView) findViewById(R.id.id_weekreport_tv);
        mTabMonthTv = (TextView) findViewById(R.id.id_monthreport_tv);

        mId_tab_day_ll = (LinearLayout) findViewById(R.id.id_tab_day_ll);
        mId_tab_week_ll = (LinearLayout) findViewById(R.id.id_tab_week_ll);
        mId_tab_month_ll = (LinearLayout) findViewById(R.id.id_tab_month_ll);

        mTabLineIv = (ImageView) findViewById(R.id.id_tab_reportline_iv);
        mBackIv = (ImageView) findViewById(R.id.id_fund_imageview);

        mDayReportFg = new DayFundReportFragment();
        mFragmentList.add(mDayReportFg);

        mWeekReportFg = new WeekFundReportFragment();
        mFragmentList.add(mWeekReportFg);

        mMonthReportFg = new MonthFundReportFragment();
        mFragmentList.add(mMonthReportFg);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),mFragmentList);
        mReport_PurchaseVp.setAdapter(mFragmentAdapter);
    }

    private void setListener() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mId_tab_day_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReport_PurchaseVp.setCurrentItem(0);
            }
        });
        mId_tab_week_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReport_PurchaseVp.setCurrentItem(1);
            }
        });

        mId_tab_month_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReport_PurchaseVp.setCurrentItem(2);
            }
        });

        scrollItem();
    }

    private void scrollItem(){
        //监听viewpager触屏滑动改变事件
        mReport_PurchaseVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTabDayTv.setTextColor(Color.WHITE);
                        break;
                    case 1:
                        mTabWeekTv.setTextColor(Color.WHITE);
                        break;
                    case 2:
                        mTabMonthTv.setTextColor(Color.WHITE);
                        break;
                    default:
                        break;
                }
                currentIndex = position;
            }
        });
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }
}
