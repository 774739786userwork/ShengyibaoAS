package com.bangware.shengyibao.main.fragment.storemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.main.fragment.storemanager.entity.StockProductBean;

import java.util.ArrayList;
import java.util.List;

public class StoreReportActivity extends BaseActivity {
    private ImageView storeImageViewId;
    private ListView storeListviewId;
    private ArrayList<StockProductBean> goodlist = null;
    private StoreReportAdapter storeReportAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_report);

        init();
        setListener();
    }

    private void init(){
        storeImageViewId = (ImageView) findViewById(R.id.store_back_iv);
        storeListviewId = (ListView) findViewById(R.id.storeListview);

        goodlist = getData();
        storeReportAdapter = new StoreReportAdapter(this,goodlist);
        storeListviewId.setAdapter(storeReportAdapter);
    }

    private void setListener(){
        storeImageViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private ArrayList<StockProductBean> getData(){
        goodlist = new ArrayList<StockProductBean>();
        for (int i = 0; i < 20;i++){
            StockProductBean productBean = new StockProductBean();
            productBean.setGoodName("钢管");
            productBean.setGoodCount("1000000.00万");
            productBean.setGoodMoney(Double.valueOf(185533.00));
            productBean.setGoodPercent((float) 33.00);
            goodlist.add(productBean);
        }
        return goodlist;
    }

}
