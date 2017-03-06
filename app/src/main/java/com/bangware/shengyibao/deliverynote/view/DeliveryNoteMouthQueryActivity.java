package com.bangware.shengyibao.deliverynote.view;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.deliverynote.adapter.DeliveryNoteMonthQueryAdapter;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteMonthQueryPresenter;
import com.bangware.shengyibao.deliverynote.presenter.impl.DeliveryNoteMonthQueryPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

/**
 * 销售记录查询
 * @author zcb
 *
 */
public class DeliveryNoteMouthQueryActivity extends BaseActivity implements OnRefreshListener,DeliveryNoteMonthQueryView{
	private ImageView deliverynote_query_back;
	private RefreshListView DeliveryMonthQueryListView;
	private TextView salerRecord_starttime,salerRecord_endtime,total_sum,unpaid_total_sum;
	private TextView billing_customer = null;
	private LinearLayout billing=null;
	private LinearLayout date_layout;
	List<DeliveryNoteMonthQuery> querylist = new ArrayList<DeliveryNoteMonthQuery>();
	private DeliveryNoteMonthQueryAdapter deliverynotemonthqueryAdapter;
	private DeliveryNoteMonthQueryPresenter presenter;
	String begin_date;
	String end_date;
	private int show_type = 1;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar c = Calendar.getInstance();
	private int nPage = 1;
	private int nSpage = 10;
	public int totalSize = 0;
	private int MaxDateNum;  
	String year=String.valueOf(c.get(Calendar.YEAR));
	String month=String.valueOf(c.get(Calendar.MONTH));

	private User user;
	//刚进入界面时的时间初始化
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deliverynotemouthquery);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		 c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 begin_date=sdf.format(c.getTime());
	     c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMaximum(Calendar.DAY_OF_MONTH));
	     end_date = sdf.format(c.getTime());
	     findView();
		setListener();
		
	}
	private void findView() {
		// TODO Auto-generated method stub
		date_layout = (LinearLayout) findViewById(R.id.date_layout);
		salerRecord_starttime = (TextView) findViewById(R.id.salerRecord_start_time);
		salerRecord_endtime = (TextView) findViewById(R.id.salerRecord_end_time);
		total_sum = (TextView) findViewById(R.id.total_sum);
		unpaid_total_sum = (TextView) findViewById(R.id.unpaid_total_sum);
		billing_customer=(TextView) findViewById(R.id.customer_total_sum);
		billing=(LinearLayout) findViewById(R.id.billing_customers);
		deliverynote_query_back = (ImageView) findViewById(R.id.deliverynote_query_back);
		DeliveryMonthQueryListView = (RefreshListView) this.findViewById(R.id.DeliveryMonthQueryListView);
		DeliveryMonthQueryListView.setDividerHeight(0);
	    
		//初始化Presenter
		presenter=new DeliveryNoteMonthQueryPresenterImpl(this);
		presenter.loadDeliveryNoteMonthQuery(user, begin_date, end_date, nPage, nSpage,show_type);
		salerRecord_starttime.setText(begin_date);
		salerRecord_endtime.setText(end_date);
		deliverynotemonthqueryAdapter = new DeliveryNoteMonthQueryAdapter(this, querylist);
		DeliveryMonthQueryListView.setAdapter(deliverynotemonthqueryAdapter);
	}
	
	
	private void setListener() {
		// TODO Auto-generated method stub
		MyOnClickLinstener clickLinstener = new MyOnClickLinstener();
		deliverynote_query_back.setOnClickListener(clickLinstener);
		date_layout.setOnClickListener(clickLinstener);
		billing.setOnClickListener(clickLinstener);
	}
	
	private class MyOnClickLinstener implements OnClickListener{
		 
		@Override
		public void onClick(View v) {
			// 返回键
			if(v.getId() == R.id.deliverynote_query_back){
				loadingdialog.dismiss();
				DeliveryNoteMouthQueryActivity.this.finish();
			}
			//跳转到开单客户数详情
			if (v.getId()==R.id.billing_customers) {
				Intent intent=new Intent(DeliveryNoteMouthQueryActivity.this, BillingCustomersActivity.class);
				startActivity(intent);
			}
			//时间选择控件
			if(v.getId() == R.id.date_layout){
				new DoubleDatePickerDialog(DeliveryNoteMouthQueryActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
							int endDayOfMonth) {
						
						begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1,startDayOfMonth);
						end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1,endDayOfMonth);

						salerRecord_starttime.setText(begin_date);
						salerRecord_endtime.setText(end_date);
						querylist.clear();
						nPage = 1;
						totalSize = nSpage;
						presenter.loadDeliveryNoteMonthQuery(user, begin_date, end_date, nPage, nSpage,show_type);
						deliverynotemonthqueryAdapter.notifyDataSetChanged();

					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();

			}
		}
		
	}
	
	
	
	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onLoadingMore() {
		nPage+=1;
		if(totalSize >= MaxDateNum){
//				showToast("暂无更多数据！请不要重复刷新！");
			DeliveryMonthQueryListView.hideFooterView();
			return;
		}else{
			presenter.loadDeliveryNoteMonthQuery(user, begin_date, end_date, nPage, nSpage,show_type);
		}
		totalSize += nSpage;
	}


	@Override
	public void showError(String errorMessage) {
		// TODO Auto-generated method stub
		showToast(errorMessage);
	}

	@Override
	public void doDeliveryNoteMonthQuerySuccess(
			List<DeliveryNoteMonthQuery> list) {
		// TODO Auto-generated method stub
		if (list.size() > 0) {
			querylist.addAll(list);
			for (DeliveryNoteMonthQuery deliveryNoteMonthQuery : list) {
				total_sum.setText("¥"+deliveryNoteMonthQuery.getD_total_sum()+"元");
				unpaid_total_sum.setText("¥"+deliveryNoteMonthQuery.getD_unpaid_total_sum()+"元");
				billing_customer.setText(deliveryNoteMonthQuery.getCustomer_number());
				MaxDateNum = querylist.get(0).getTotal_record();
			}
			deliverynotemonthqueryAdapter.notifyDataSetChanged();
		}else{
			deliverynotemonthqueryAdapter.notifyDataSetChanged();
			showToast("暂无本月销售记录数据！");
		}
		DeliveryMonthQueryListView.hideFooterView();
		DeliveryMonthQueryListView.setOnRefreshListener(DeliveryNoteMouthQueryActivity.this);
	}
	
	public void onDestroy(){
		if(presenter!=null)
			presenter.destroy();
		super.onDestroy();
	}
}
