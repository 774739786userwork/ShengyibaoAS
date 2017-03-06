package com.bangware.shengyibao.customer.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.adapter.QueryNearByCustomerAdapter;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.presenter.CustomerNearByPresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerNearByPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

import java.util.ArrayList;
import java.util.List;

public class NearbyCustomerActivity extends BaseActivity implements CustomerNearByView,OnRefreshListener {
    private ImageView nearby_backimg;//返回键
    private TextView nearby_customer_quantity,metresTextview;//附近客户数量
    private RefreshListView nearbyListView;
    private QueryNearByCustomerAdapter queryNearByCustomerAdapter;
    private int nPage = 1;
    private int nSpage = 10;
    private int pageSize;
    public int totalSize = 0;
    private List<Customer> customerlist = new ArrayList<Customer>();
    private CustomerNearByPresenter nearByPresenter = null;
    private Customer customer = null;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_customer);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findViews();
        setListener();
    }

    //绑定控件
   public void findViews(){
        nearby_backimg = (ImageView) findViewById(R.id.nearby_backimg);
        nearby_customer_quantity = (TextView) findViewById(R.id.nearby_customer_quantity);
        metresTextview = (TextView) findViewById(R.id.metresTextview);
        nearbyListView = (RefreshListView) findViewById(R.id.nearbyListView);

       customer = (Customer) getIntent().getExtras().getSerializable("customer");

       nearByPresenter = new CustomerNearByPresenterImpl(this);
       nearByPresenter.loadNearByCustomerData(user,nPage,nSpage,customer.getLatitude(),customer.getLongitude());
       queryNearByCustomerAdapter = new QueryNearByCustomerAdapter(this,customerlist);
       nearbyListView.setAdapter(queryNearByCustomerAdapter);
    }

    //绑定控件点击监听事件
    public void setListener(){
        MyOnClickListener clickListener = new MyOnClickListener();
        nearby_backimg.setOnClickListener(clickListener);

        //给listview条目设置选择项事件客户列表数据
        nearbyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(NearbyCustomerActivity.this, CustomerInfoActivity.class);
                //用Bundle传递数据
                Customer customer = (Customer) adapterView.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer", customer);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void queryNearByCustomer(List<Customer> customers) {
        if (customers.size() > 0) {
            customerlist.addAll(customers);
            pageSize = customerlist.get(0).getTotal_record_sum();
            metresTextview.setText("仅显示3公里内的客户");
            nearby_customer_quantity.setText(customer.getName()+"附近共"+pageSize+"个客户");
            queryNearByCustomerAdapter.notifyDataSetChanged();
        } else {
            showToast("暂无更多数据！");
            queryNearByCustomerAdapter.notifyDataSetChanged();
        }
        nearbyListView.hideFooterView();
        nearbyListView.setOnRefreshListener(NearbyCustomerActivity.this);
    }

    @Override
    public void showLoadFailureMsg(String errorMessage) {

    }

    @Override
    public void onDownPullRefresh() {

    }

    @Override
    public void onLoadingMore() {
        nPage += 1;
        if (totalSize >= pageSize) {
            nearbyListView.hideFooterView();
            return;
        }else {
            nearByPresenter.loadNearByCustomerData(user,nPage,nSpage,customer.getLatitude(),customer.getLongitude());
        }
        totalSize += nSpage;
    }

    //点击事件触发
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.nearby_backimg){
                finish();
            }
        }
    }
}
