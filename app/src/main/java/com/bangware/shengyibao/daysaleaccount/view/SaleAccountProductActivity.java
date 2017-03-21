package com.bangware.shengyibao.daysaleaccount.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.daysaleaccount.adapter.SaleAccountProductAdapter;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;
import com.bangware.shengyibao.daysaleaccount.presenter.SaleProductPresenter;
import com.bangware.shengyibao.daysaleaccount.presenter.impl.SaleProductPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleAccountProductActivity extends BaseActivity implements SaleProductView{
    private ImageView backimage;
    private TextView date_producttext;
    private TextView saletotalsum_textview,unpaidmoney_textview,small_change_textview;
    private ListView daySaleProductListView;

    private List<SaleAccountProductBean> productList = new ArrayList<SaleAccountProductBean>();
    private SaleAccountProductAdapter productAdapter;
    private SaleProductPresenter productPresenter = null;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_account_product);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findView();
        setListener();
    }

    //控件绑定初始化
    public void findView(){
        backimage = (ImageView) findViewById(R.id.backimage);
        date_producttext = (TextView) findViewById(R.id.date_producttext);
        saletotalsum_textview = (TextView) findViewById(R.id.saletotalsum_textview);
        unpaidmoney_textview = (TextView) findViewById(R.id.unpaidmoney_textview);
        small_change_textview = (TextView) findViewById(R.id.small_change_textview);
        daySaleProductListView = (ListView) findViewById(R.id.daySaleProductListView);

        Bundle bundle = this.getIntent().getExtras();
        String saler_journals_id = (String) bundle.getSerializable("id");
        String date = (String) bundle.getSerializable("sale_date");
        date_producttext.setText(date+"产品清单");
        productPresenter = new SaleProductPresenterImpl(this);
        productPresenter.loadSalesAccountData(user,saler_journals_id);

        productAdapter = new SaleAccountProductAdapter(this,productList);
        daySaleProductListView.setAdapter(productAdapter);
    }

    //绑定控件点击事件初始化
    public void setListener(){
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doLoadSaleProductData(List<SaleAccountProductBean> productBeanList) {
        if(productBeanList.size() > 0){
            productList.clear();
            productList.addAll(productBeanList);

            saletotalsum_textview.setText("¥"+String.valueOf(productList.get(0).getTotalmoney()));
            unpaidmoney_textview.setText("¥"+String.valueOf(productList.get(0).getUnpaidmoney()));
            small_change_textview.setText("¥"+String.valueOf(productList.get(0).getSmallchange()));

            productAdapter.notifyDataSetChanged();
        }else{
            showToast("无产品数据");
            productAdapter.notifyDataSetChanged();
        }
    }
}
