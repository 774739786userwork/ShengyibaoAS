package com.bangware.shengyibao.daysaleaccount.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.daysaleaccount.adapter.SaleAccountListAdapter;
import com.bangware.shengyibao.daysaleaccount.adapter.SaleAccountProductAdapter;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;
import com.bangware.shengyibao.daysaleaccount.presenter.SaleAccountPresenter;
import com.bangware.shengyibao.daysaleaccount.presenter.impl.SaleAccountPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 日销售清单列表查询
 * Created by ccssll on 2016/8/9.
 */
public class SaleAccountListActivity extends BaseActivity implements OnRefreshListener,SaleAccountView{
    private ImageView backimage;
    private RefreshListView daySaleQueryListView;
    private SaleAccountListAdapter accountListAdapter;

    private List<SaleAccountListBean> datalist = new ArrayList<SaleAccountListBean>();
    private SaleAccountPresenter saleAccountPresenter;
    private LinearLayout topLinearLayout;
    private TextView daySale_start_date,daySale_end_date;
//    private TextView add_sale_acount;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    String currenttime = sdf.format(new Date());
    private String begin_date;
    private String end_date;
    private int nPage = 1;
    private int nSpage = 10;
    public int totalSize = 0;
    private int pagesize;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sale_account);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findViews();
        setListener();
    }

    public void findViews(){
        backimage = (ImageView) findViewById(R.id.day_saleaccount_backImg);
        daySaleQueryListView = (RefreshListView) findViewById(R.id.daySaleQueryListView);
        topLinearLayout = (LinearLayout)findViewById(R.id.topLinearLayout);
        daySale_start_date = (TextView)findViewById(R.id.daySale_start_date);
        daySale_end_date = (TextView)findViewById(R.id.daySale_end_date);
//        add_sale_acount = (TextView)findViewById(R.id.new_saleaccount_text);

        begin_date=currenttime;
        end_date=currenttime;
        daySale_start_date.setText(currenttime);
        daySale_end_date.setText(currenttime);

        saleAccountPresenter = new SaleAccountPresenterImpl(this);
        saleAccountPresenter.loadSalesAccountData(user,begin_date,end_date,nPage,nSpage);

        accountListAdapter = new SaleAccountListAdapter(this,datalist);
        daySaleQueryListView.setAdapter(accountListAdapter);
    }

    public void setListener(){
        MySaleOnClickListener saleOnClickListener = new MySaleOnClickListener();
        backimage.setOnClickListener(saleOnClickListener);
//        add_sale_acount.setOnClickListener(saleOnClickListener);
        topLinearLayout.setOnClickListener(saleOnClickListener);

        daySaleQueryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SaleAccountListActivity.this,SaleAccountProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", datalist.get(position-1).getId());
                bundle.putSerializable("sale_date", datalist.get(position-1).getSaledate());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void doLoadSaleAccountData(List<SaleAccountListBean> saleAccountList) {
        if(saleAccountList.size() > 0){
            datalist.addAll(saleAccountList);
            for (int i = 0; i < datalist.size(); i++) {
                int total_record = datalist.get(0).getTotal_pagerecord();
                pagesize = total_record;
            }
            accountListAdapter.notifyDataSetChanged();
        }else{
            accountListAdapter.notifyDataSetChanged();
            showToast("当日暂无记录！");
        }
        daySaleQueryListView.hideFooterView();
        daySaleQueryListView.setOnRefreshListener(SaleAccountListActivity.this);
    }

    @Override
    public void onDownPullRefresh() {

    }

    @Override
    public void onLoadingMore() {
        nPage+=1;
        if(totalSize >= pagesize){
            daySaleQueryListView.hideFooterView();
            return;
        }else{
            saleAccountPresenter.loadSalesAccountData(user,begin_date,end_date,nPage,nSpage);
        }
        totalSize += nSpage;
    }

    protected class MySaleOnClickListener implements View.OnClickListener{
        Calendar c = Calendar.getInstance();
        @Override
        public void onClick(View v) {
            //返回键
            if (v.getId() == R.id.day_saleaccount_backImg){
                finish();
            }
            //新建日销售清单
            /*if (v.getId() == R.id.new_saleaccount_text){
                Intent intent = new Intent(SaleAccountListActivity.this,AddSaleAccountActivity.class);
                startActivity(intent);
            }*/
            //时间点击事件
            if (v.getId() == R.id.topLinearLayout) {
                new DoubleDatePickerDialog(SaleAccountListActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {

                        begin_date = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);

                        daySale_start_date.setText(begin_date);
                        daySale_end_date.setText(end_date);

                        datalist.clear();
                        nPage = 1;
                        totalSize = nSpage;
                        saleAccountPresenter.loadSalesAccountData(user,begin_date,end_date,nPage,nSpage);
                        accountListAdapter.notifyDataSetInvalidated();

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
            }
        }
    }

}
