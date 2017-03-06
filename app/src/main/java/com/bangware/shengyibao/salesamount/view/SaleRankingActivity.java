package com.bangware.shengyibao.salesamount.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.salesamount.adapter.SaleRankingAdapter;
import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;
import com.bangware.shengyibao.salesamount.presenter.SaleRankingPresenter;
import com.bangware.shengyibao.salesamount.presenter.impl.SaleRankingPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SaleRankingActivity extends BaseActivity implements SaleRankingView{
    private ImageView backimage;
    private TextView starttimetext,endtimetext,group_ranking_textview;
    private ListView saleRankingListView;
    private LinearLayout topLinearLayout;
    private SaleRankingAdapter rankingAdapter;
    private List<SaleRankingBean> saleRankingBeanList = new ArrayList<SaleRankingBean>();
    private String begin_date;
    private String end_date;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    private String year=String.valueOf(c.get(Calendar.YEAR));
    private String month=String.valueOf(c.get(Calendar.MONTH));

    private SaleRankingPresenter rankingPresenter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sale_ranking);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMinimum(Calendar.DAY_OF_MONTH));
        begin_date=sdf.format(c.getTime());
        c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMaximum(Calendar.DAY_OF_MONTH));
        end_date = sdf.format(c.getTime());

        findView();
        setListener();
    }

    public void findView(){
        backimage = (ImageView) findViewById(R.id.saleRanking_backImg);
        starttimetext = (TextView) findViewById(R.id.ranking_start_date);
        endtimetext = (TextView) findViewById(R.id.ranking_end_date);
        group_ranking_textview = (TextView) findViewById(R.id.group_ranking_textview);
        topLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);
        saleRankingListView = (ListView)findViewById(R.id.saleRankingListView);

        rankingPresenter = new SaleRankingPresenterImpl(this);
        rankingPresenter.loadSaleRankingData(user,begin_date,end_date);
        starttimetext.setText(begin_date);
        endtimetext.setText(end_date);

        rankingAdapter = new SaleRankingAdapter(this,saleRankingBeanList);
        saleRankingListView.setAdapter(rankingAdapter);
    }

    public void setListener(){
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        backimage.setOnClickListener(myOnClickListener);
        topLinearLayout.setOnClickListener(myOnClickListener);
        group_ranking_textview.setOnClickListener(myOnClickListener);
    }


    //请求加载下来的数据
    @Override
    public void doSaleRankingLoadSuccess(List<SaleRankingBean> rankingBeanList) {
        if (rankingBeanList.size() > 0){
            saleRankingBeanList.clear();
            saleRankingBeanList.addAll(rankingBeanList);
            rankingAdapter.notifyDataSetChanged();
        }else{
            showMessage("暂无排名");
            rankingAdapter.notifyDataSetChanged();
        }
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.saleRanking_backImg){
                finish();
            }
            if (v.getId() == R.id.group_ranking_textview){
                Intent intent = new Intent(SaleRankingActivity.this,GroupRankingActivity.class);
                startActivity(intent);
            }
            if (v.getId() == R.id.topLinearLayout){
                new DoubleDatePickerDialog(SaleRankingActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {

                        begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1,1);
                        end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1,endDayOfMonth);
                        starttimetext.setText(begin_date);
                        endtimetext.setText(end_date);

                        saleRankingBeanList.clear();
                        rankingPresenter.loadSaleRankingData(user,begin_date,end_date);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
            }
        }
    }

}
