package com.bangware.shengyibao.deliverynote.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.view.CustomerInfoActivity;
import com.bangware.shengyibao.customer.view.CustomerView;
import com.bangware.shengyibao.deliverynote.adapter.BillingCustomerAdapter;
import com.bangware.shengyibao.deliverynote.presenter.BillingCustomerPresenter;
import com.bangware.shengyibao.deliverynote.presenter.impl.BillingCustomerPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;


/**
 * 开单客户数
 * @author zcb
 *
 */
public class BillingCustomersActivity extends BaseActivity implements OnRefreshListener,CustomerView {
	private ImageView mBilling_customer_back;
	private RefreshListView mRefreshListView;
	private BillingCustomerAdapter adapter;
	private int nPage = 1;
	private int nSpage = 10;
	private int pageSize;
	public int totalSize = 0;
	private Bundle bundle;	
	private List<Customer> customerlist = new ArrayList<Customer>();
	String phone = "";
	String shopName = "";
	String employee_id= AppContext.getInstance().getInstance().getUser().getEmployee_id();
	private User user=AppContext.getInstance().getUser();
	private BillingCustomerPresenter presenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_billing_customer);
		findView();
		adapter =new BillingCustomerAdapter(this, customerlist);
		mRefreshListView.setAdapter(adapter);
		presenter=new BillingCustomerPresenterImpl(this);
		presenter.loadBilingCustomerData(nPage, nSpage, phone, shopName,employee_id,user);
	}

	private void findView() {
		mBilling_customer_back=(ImageView) findViewById(R.id.Billing_customer_back);
		mRefreshListView=(RefreshListView) findViewById(R.id.Billing_customerListView);
		//退出事件
		mBilling_customer_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadingdialog.dismiss();
				finish();
			}
		});
		//listview的条目点击事件
		mRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BillingCustomersActivity.this, CustomerInfoActivity.class);
				//用Bundle传递数据
				Customer customer = (Customer)adapterView.getItemAtPosition(position);
				bundle = new Bundle();
				bundle.putString("customer_id", customer.getId());
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	@Override
	public void addCustomers(List<Customer> customers) {
		// TODO Auto-generated method stub
		if(customers.size() > 0){
			customerlist.addAll(customers);
			pageSize = customerlist.get(0).getTotal_record_sum();
			adapter.notifyDataSetChanged();
		}else{
			showToast("暂无客户数据！");
			adapter.notifyDataSetChanged();
		}
		mRefreshListView.hideFooterView();
		mRefreshListView.setOnRefreshListener(BillingCustomersActivity.this);
	}

	@Override
	public void showLoadFailureMsg(String errorMessage) {
		// TODO Auto-generated method stub
		showToast(errorMessage);
	}

	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		nPage+=1;
		if(totalSize >= pageSize){
			mRefreshListView.hideFooterView();
			return;
		}else{
			presenter.loadBilingCustomerData(nPage, nSpage, phone, shopName, employee_id,user);
		}
		totalSize += nSpage;
	}
	public void onDestroy(){
		super.onDestroy();
		if(this.presenter!=null)
			this.presenter.destroy();
	}
}
