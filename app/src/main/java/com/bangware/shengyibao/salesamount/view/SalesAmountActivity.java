package com.bangware.shengyibao.salesamount.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.salesamount.adapter.SalesAmountListAdapter;
import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;
import com.bangware.shengyibao.salesamount.presenter.SalesAmountPresenter;
import com.bangware.shengyibao.salesamount.presenter.impl.SalesAmountPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;


/**
 * 销售金额界面
 * @author zcb
 *
 */

public class SalesAmountActivity extends BaseActivity implements OnRefreshListener,SalesAmountView{
	private ImageView sales_amount_back;//回退按钮
	private RefreshListView SalesAmountListView;//listView
	private TextView saleAmount_starttime,saleAmount_endtime,sales_amount_total_sum,sales_amount_unpaid_sum;//时间，总销售额，未付金额
	private TextView sales_amount_coutomer = null;//开单客户总数
	private LinearLayout sales_amount_date_layout;//时间控件
	private SalesAmountPresenter presenter;
	String begin_date;
	String end_date;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	List<MonthProductAmount> list=new ArrayList<MonthProductAmount>();
	Calendar c = Calendar.getInstance();
	private int nPage = 0;
	private int nSpage = 10;
	public int totalSize = 0;
	private int MaxDateNum;  
	private SalesAmountListAdapter adapter;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_amount);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);

		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH));
		 c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 begin_date=sdf.format(c.getTime());
	     c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMaximum(Calendar.DAY_OF_MONTH));  
	     end_date = sdf.format(c.getTime());

		findView();//初始化控件
		setListener();//设置点击事件
	}


	private void findView() {
		sales_amount_back=(ImageView) findViewById(R.id.sales_amount_back);
		SalesAmountListView=(RefreshListView) findViewById(R.id.SalesAmountListView);
		sales_amount_total_sum=(TextView) findViewById(R.id.sales_amount_total_sum);
		sales_amount_unpaid_sum=(TextView) findViewById(R.id.sales_amount_unpaid_sum);
		sales_amount_coutomer=(TextView) findViewById(R.id.sales_amount_totalcustomer);
		sales_amount_date_layout=(LinearLayout) findViewById(R.id.sales_amount_date_layout);
		saleAmount_starttime=(TextView) findViewById(R.id.salesAmount_start_time);
		saleAmount_endtime=(TextView) findViewById(R.id.salesAmount_end_time);
		presenter=new SalesAmountPresenterImpl(this);
		presenter.loadSalesAmount(user, begin_date, end_date, nPage, nSpage);

		saleAmount_starttime.setText(begin_date);
		saleAmount_endtime.setText(end_date);
		adapter = new SalesAmountListAdapter(list, this);
		SalesAmountListView.setAdapter(adapter);
	}
	private void setListener() {
		MyOnClickLinstener clickLinstener=new MyOnClickLinstener();
		sales_amount_back.setOnClickListener(clickLinstener);
		sales_amount_date_layout.setOnClickListener(clickLinstener);
	}
	
	private class MyOnClickLinstener implements OnClickListener{
		 
		@Override
		public void onClick(View v) {
			// 返回键
			if(v.getId() == R.id.sales_amount_back){
				loadingdialog.dismiss();
				SalesAmountActivity.this.finish();
			}
			//时间选择控件
			if(v.getId() == R.id.sales_amount_date_layout){
				new DoubleDatePickerDialog(SalesAmountActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
							int endDayOfMonth) {
						
						begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1,1);
						end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1,endDayOfMonth);
						saleAmount_starttime.setText(begin_date);
						saleAmount_endtime.setText(end_date);

						list.clear();
						nPage = 1;
						totalSize = nSpage;
						presenter.loadSalesAmount(user, begin_date, end_date, nPage, nSpage);
						adapter.notifyDataSetChanged();
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
			}
		}
		
	}

	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		
	}

   //下拉刷新
	@Override
	public void onLoadingMore() {
		nPage+=1;
		if(totalSize >= MaxDateNum){
//			showToast("暂无更多数据！请不要重复刷新！");
			SalesAmountListView.hideFooterView();
			return;
		}else{
			//下拉加载更多时设置请求参数
			presenter.loadSalesAmount(user, begin_date, end_date, nPage, nSpage);
		}
		totalSize += nSpage;
		
	}

	//请求数据成功后设置数据
	@Override
	public void doSalesAmountLoadSuccess(
		List<MonthProductAmount> monthProductAmountslist) {
		if (monthProductAmountslist.size() > 0) {
			list.addAll(monthProductAmountslist);
			Collections.sort(monthProductAmountslist,new Comparator<MonthProductAmount>(){
				@Override
				public int compare(MonthProductAmount lhs,
						MonthProductAmount rhs) {
					return Double.valueOf(rhs.getSalesAomuntSum()).compareTo(Double.valueOf(lhs.getSalesAomuntSum()));
				}
			});
			for (int i = 0; i < list.size();i++){
				sales_amount_total_sum.setText("¥"+list.get(i).getSales_amount_total_sum()+"元");
				sales_amount_unpaid_sum.setText("¥"+list.get(i).getSales_amount_unpaid_sum()+"元");
				sales_amount_coutomer.setText(list.get(i).getSales_amount_coutomer()+"家");
				MaxDateNum = list.get(i).getTotal_record();
			}
			adapter.notifyDataSetChanged();
		}else{
			adapter.notifyDataSetChanged();
			showToast("暂无本月销售记录数据！");
		}
		SalesAmountListView.hideFooterView();
		SalesAmountListView.setOnRefreshListener(SalesAmountActivity.this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(presenter!=null)
			presenter.destroy();
		super.onDestroy();
	}
}
