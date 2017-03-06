package com.bangware.shengyibao.customer.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.adapter.CustomerPurchaseMoreAdapter;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;
import com.bangware.shengyibao.customer.presenter.CustomerPurchasePresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerPurchaseImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

/**
 * 查看更多客户的进货记录
 * @author ccssll
 *
 */
public class CustomerPurchaseMoreActivity extends BaseActivity implements OnRefreshListener,CustomerPurchaseView{
	private ImageView morepurchase_query_back;
	private TextView customer_name,purchase_total_sum,unpaid_total_sum,customerBilling_total_sum,start_time,end_time;
	private LinearLayout date_layout;
	private RefreshListView morePurchaseListView;
	private int nPage = 1;
	private int nSpage = 10;
	public int totalSize = 0;
	private int MaxDateNum;
	private String begin_date;
	private String end_date;
	private String customerId;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	Calendar c = Calendar.getInstance();
	String year=String.valueOf(c.get(Calendar.YEAR));
	String month=String.valueOf(c.get(Calendar.MONTH));
	
	private List<CustomerPurchase> purchaseList = new ArrayList<CustomerPurchase>();
	private CustomerPurchaseMoreAdapter moreAdapter;
	private CustomerPurchasePresenter purchasePresenter;

	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_morepurchase);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 begin_date=sdf.format(c.getTime());
	     c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMaximum(Calendar.DAY_OF_MONTH));  
	     end_date = sdf.format(c.getTime());
		findViews();
		setLinstener();
	}
	
	private void findViews() {
		// TODO Auto-generated method stub
		morepurchase_query_back = (ImageView) findViewById(R.id.morepurchase_query_back);
		customer_name = (TextView) findViewById(R.id.customer_name);
		purchase_total_sum = (TextView) findViewById(R.id.purchase_total_sum);
		unpaid_total_sum = (TextView) findViewById(R.id.unpaid_total_sum);
		customerBilling_total_sum = (TextView) findViewById(R.id.customerSaler_total_sum);
		date_layout = (LinearLayout) findViewById(R.id.date_layout);
		start_time = (TextView) findViewById(R.id.start_time_textview);
		end_time = (TextView) findViewById(R.id.end_time_textview);
		morePurchaseListView = (RefreshListView) findViewById(R.id.morePurchaseListView);
		
		Bundle bundle = this.getIntent().getExtras();
		customerId = bundle.getString("customer_id");
		customer_name.setText(bundle.getString("customer_name"));
		purchase_total_sum.setText("0");
		unpaid_total_sum.setText("0");
		customerBilling_total_sum.setText("0");
		
		//进货记录请求
		purchasePresenter = new CustomerPurchaseImpl(this);
		purchasePresenter.queryCustomerPurchaseData(user,customerId, nPage, nSpage, begin_date, end_date);
		start_time.setText(begin_date);
		end_time.setText(end_date);
		moreAdapter = new CustomerPurchaseMoreAdapter(this,purchaseList);
		morePurchaseListView.setAdapter(moreAdapter);
	}
	
	private void setLinstener() {
		// TODO Auto-generated method stub
		MyOnClickListener clickListener = new MyOnClickListener();
		morepurchase_query_back.setOnClickListener(clickListener);
		date_layout.setOnClickListener(clickListener);
	}
	
	private class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.morepurchase_query_back){
				finish();
			}
			if(v.getId() == R.id.date_layout){
				new DoubleDatePickerDialog(CustomerPurchaseMoreActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
							int endDayOfMonth) {
						
						begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1, startDayOfMonth);
						end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);

						start_time.setText(begin_date);
						end_time.setText(end_date);
						
						purchaseList.clear();
						nPage = 1;
						totalSize = nSpage;
						purchasePresenter.queryCustomerPurchaseData(user,customerId, nPage, nSpage, begin_date, end_date);

					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
			}
		}
	}

	//下拉刷新
	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		
	}

	//上拉加载
	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		nPage+=1;
		if(totalSize >= MaxDateNum){
//			showToast("暂无更多数据！请不要重复刷新！");
			morePurchaseListView.hideFooterView();
			return;
		}else{
			purchasePresenter.queryCustomerPurchaseData(user,customerId, nPage, nSpage, begin_date, end_date);
		}
		totalSize += nSpage;
	}

	@Override
	public void loadPurchaseData(List<CustomerPurchase> purchases) {
		// TODO Auto-generated method stub
		if(purchases.size() > 0){
			purchaseList.addAll(purchases);
			purchase_total_sum.setText("¥"+purchaseList.get(0).getPurchase_total_sum()+"元");
			unpaid_total_sum.setText("¥"+purchaseList.get(0).getPurchase_untotal_sum()+"元");
			customerBilling_total_sum.setText(purchaseList.get(0).getTotal_record()+"次");
			
			MaxDateNum = purchaseList.get(0).getTotal_record();
			moreAdapter.notifyDataSetChanged();
		}else{
			showToast("暂无记录！");
			moreAdapter.notifyDataSetChanged();
		}
		morePurchaseListView.hideFooterView();
		morePurchaseListView.setOnRefreshListener(CustomerPurchaseMoreActivity.this);
	}

	@Override
	public void loadCustomerInfoData(List<Customer> customers) {
		// TODO Auto-generated method stub
		
	}
	
	public void onDestroy(){
		if(purchasePresenter!=null){
			purchasePresenter.destory();
		}
		super.onDestroy();
	}
}
