package com.bangware.shengyibao.ladingbilling.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
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
import com.bangware.shengyibao.ladingbilling.adapter.LadingBillingQueryAdapter;
import com.bangware.shengyibao.ladingbilling.model.entity.LadingbillingQuery;
import com.bangware.shengyibao.ladingbilling.presenter.LadingbillingPresenter;
import com.bangware.shengyibao.ladingbilling.presenter.impl.LadingbillingPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * 提货单页面主窗口
 * @author ccssll
 *
 */
public class LadingbillingQueryActivity extends BaseActivity implements OnRefreshListener,LadingbillingQueryView{
	private ImageView ladbilling_query_back;
	private LinearLayout ladingbilling_layout;
	private TextView ladingbilling_time,ladingbilling_end_time,ladingbilling_total_sum,stock_query;
	private RefreshListView ladbilling_queryListView;
	List<LadingbillingQuery> ladingquery_list = new ArrayList<LadingbillingQuery>();
	private LadingBillingQueryAdapter billingadapter;
	private int nPage = 1;
	private int nSpage = 10;
	public int totalSize = 0;
	private int MaxDateNum;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	String currenttime = sdf.format(new Date());
	String begin_date;
	String end_date;

	private LadingbillingPresenter billingPresenter;
	private User user;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ladingbilling_query);

		sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		init();
		initview();
	}
	
	private void init() {
		ladbilling_query_back = (ImageView) findViewById(R.id.ladbilling_query_back);
		ladingbilling_layout = (LinearLayout) findViewById(R.id.ladingbilling_layout);
		ladingbilling_time = (TextView) findViewById(R.id.ladingbilling_starttime);
		ladingbilling_end_time = (TextView) findViewById(R.id.ladingbilling_endtime);
		ladingbilling_total_sum =  (TextView) findViewById(R.id.ladingbilling_total_sum);
		ladbilling_queryListView = (RefreshListView) findViewById(R.id.ladbilling_queryListView);
		stock_query = (TextView) findViewById(R.id.stock_textview);
		
		begin_date=currenttime;
		end_date=currenttime;
		billingPresenter = new LadingbillingPresenterImpl(this);
		billingPresenter.loadLadingBilling(user,begin_date, end_date, nPage, nSpage);
		ladingbilling_time.setText(begin_date);
		ladingbilling_end_time.setText(end_date);
		ladingbilling_total_sum.setText("0次");
		billingadapter = new LadingBillingQueryAdapter(this, ladingquery_list);
		ladbilling_queryListView.setDividerHeight(0);
		ladbilling_queryListView.setAdapter(billingadapter);
	}
	
	private void initview() {
		// TODO Auto-generated method stub
		MyOnClickLinstener clickLinstener = new MyOnClickLinstener();
		ladbilling_query_back.setOnClickListener(clickLinstener);
		ladingbilling_layout.setOnClickListener(clickLinstener);
		stock_query.setOnClickListener(clickLinstener);
	}


	private class MyOnClickLinstener implements OnClickListener{
		Calendar c = Calendar.getInstance();
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.ladbilling_query_back){
				loadingdialog.dismiss();
				LadingbillingQueryActivity.this.finish();
			}
			if(v.getId() == R.id.ladingbilling_layout){
				new DoubleDatePickerDialog(LadingbillingQueryActivity.this, THEME_HOLO_LIGHT, new DoubleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
							int endDayOfMonth) {
						
						begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1, startDayOfMonth);
						end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
						
						ladingbilling_time.setText(begin_date);
						ladingbilling_end_time.setText(end_date);

						if(java.sql.Date.valueOf(begin_date).after(java.sql.Date.valueOf(end_date))){
							ladingbilling_total_sum.setText("0次");
//							showToast("开始日期不能大于结束日期！");
						}
						else if(java.sql.Date.valueOf(end_date).after(java.sql.Date.valueOf(begin_date))){
							ladingbilling_total_sum.setText("0次");
//							showToast("结束日期不能大于开始日期！");
						}
						
						ladingquery_list.clear();
						nPage = 1;
						totalSize = nSpage;
						billingPresenter.loadLadingBilling(user,begin_date, end_date, nPage, nSpage);
						billingadapter.notifyDataSetInvalidated();
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
			}
			if (v.getId() == R.id.stock_textview){
				//余货查询
				Intent intent = new Intent(LadingbillingQueryActivity.this,StockQueryActivity.class);
				startActivity(intent);
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
			ladbilling_queryListView.hideFooterView();
			showToast("暂无更多数据！请不要重复刷新！");
			return;
		}else{
			billingPresenter.loadLadingBilling(user,begin_date, end_date, nPage, nSpage);
		}
		totalSize += nSpage;
	}

	@Override
	public void addLadingbillingData(
			List<LadingbillingQuery> ladingbillingList) {
		// TODO Auto-generated method stub
		if(ladingbillingList.size() > 0){
			ladingquery_list.addAll(ladingbillingList);
			for (int i = 0; i < ladingquery_list.size(); i++) {
				ladingbilling_total_sum.setText(ladingquery_list.get(i).getLadingbilling_total_count());
				String total_record = ladingbilling_total_sum.getText().toString();
				int record_page = Integer.valueOf(total_record);
				MaxDateNum = record_page;
			}
			billingadapter.notifyDataSetChanged();
		}else{
			showInfoDialog();
			billingadapter.notifyDataSetChanged();
		}
		ladbilling_queryListView.hideFooterView();
		ladbilling_queryListView.setOnRefreshListener(LadingbillingQueryActivity.this);
	}

	public void showInfoDialog(){
		showDialog(this,"暂无提货单数据！",1,new Handler(){
			@Override
			public void dispatchMessage(Message msg) {
				super.dispatchMessage(msg);
				if (msg.what == 1){
					LadingbillingQueryActivity.this.finish();
				}
			}
		});
	}


	@Override
	public void showFailureMsg(String errorMessage) {
		// TODO Auto-generated method stub
		showToast(errorMessage);
	}

	public void onDestroy(){
		if(billingPresenter!=null){
			billingPresenter.destroy();
		}
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	loadingdialog.dismiss();
        	finish();
        }
		return true;
	}
}
