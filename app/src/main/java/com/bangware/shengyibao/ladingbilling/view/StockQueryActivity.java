package com.bangware.shengyibao.ladingbilling.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.ladingbilling.adapter.StockQueryAdapter;
import com.bangware.shengyibao.ladingbilling.presenter.StockPresenter;
import com.bangware.shengyibao.ladingbilling.presenter.impl.StockPresenterImpl;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 余货查询
 */
public class StockQueryActivity extends BaseActivity implements StockQueryView{
    private ImageView backImg;
    private ListView stocklistview;
    private StockQueryAdapter stockQueryAdapter;
    private List<Product> stocklist = new ArrayList<Product>();
    private StockPresenter stockPresenter;
    private User user;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_query);

        sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findViews();
        setListener();
    }


    public void findViews(){
        backImg = (ImageView) findViewById(R.id.backImg);
        stocklistview = (ListView) findViewById(R.id.stockListView);

        stockPresenter = new StockPresenterImpl(this);
        stockPresenter.onLoadStock(user);
        stockQueryAdapter = new StockQueryAdapter(this,stocklist);
        stocklistview.setAdapter(stockQueryAdapter);
    }

    public void setListener(){
        MyOnClickListener listener = new MyOnClickListener();
        backImg.setOnClickListener(listener);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.backImg){
                finish();
            }
        }
    }

    /**
     * 加载库存
     * @param productstockList
     */
    @Override
    public void loadProductStockData(List<Product> productstockList) {
        if (productstockList.size() < 0){
            showStockDialog();
        }else{
            stocklist.addAll(productstockList);
            stockQueryAdapter.notifyDataSetChanged();
        }
    }


    private void  showStockDialog(){
        showDialog(this,"当日无余货记录!",2,new Handler(){
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                if (msg.what == 2){
                    finish();
                }
            }
        });
    }

    public void onDestroy(){
        super.onDestroy();
        if(stockPresenter!=null)
            stockPresenter.destroy();
    }
}
