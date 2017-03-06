package com.bangware.shengyibao.returngood.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.returngood.adapter.ReturnGoodAdapter;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.presenter.ReturngoodPresenter;
import com.bangware.shengyibao.returngood.presenter.impl.ReturngoodPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

/**
 * 退货单查询展示
 * @author ccssll
 *
 */
public class ReturngoodQueryActivity extends BaseActivity implements OnRefreshListener,ReturnGoodView{
	private ImageView back_image;
	private LinearLayout returngood_layout;
	private TextView returngood_Starttime,returngood_Endtime,returngood_total_sum;
	private RefreshListView returngood_queryListView;
	private String begin_date,end_date;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	String currenttime = sdf.format(new Date());
	private ReturnGoodAdapter goodAdapter;
	private ReturngoodPresenter goodPresenter;
	private int nPage = 1;
	private int nSpage = 10;
	private int pageSize;
	public int totalSize = 0;
	private List<ReturnNote> goodsList = new ArrayList<ReturnNote>();
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_return_good);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findViews();
		setListener();
	}
	
	//绑定控件并初始化
	private void findViews() {
		// TODO Auto-generated method stub
		back_image = (ImageView) findViewById(R.id.returngood_query_back);
		returngood_layout = (LinearLayout) findViewById(R.id.returngood_layout);
		returngood_Starttime = (TextView) findViewById(R.id.returngood_starttime);
		returngood_Endtime = (TextView) findViewById(R.id.returngood_endtime);
		returngood_total_sum = (TextView) findViewById(R.id.returngood_total_sum);
		returngood_queryListView = (RefreshListView) findViewById(R.id.returngood_queryListView);
		
		goodPresenter = new ReturngoodPresenterImpl(this);
		begin_date=currenttime;
		end_date=currenttime;
		goodPresenter.loadreturnGood(user,begin_date, end_date, nPage, nSpage);
		returngood_Starttime.setText(begin_date);
		returngood_Endtime.setText(end_date);
		returngood_total_sum.setText("¥0.0元");
		goodAdapter = new ReturnGoodAdapter(this, goodsList);
		returngood_queryListView.setAdapter(goodAdapter);
		returngood_queryListView.setDividerHeight(0);
		
		//listview条目点击
		returngood_queryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ReturngoodQueryActivity.this, ReturnGoodProductActivity.class);
				Bundle bundle = new Bundle();
				ReturnNote returnNote = (ReturnNote) parent.getItemAtPosition(position);
				bundle.putString("currenttime",currenttime);
				bundle.putSerializable("returnNote", returnNote);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	//绑定控件点击事件监听
	private void setListener() {
		// TODO Auto-generated method stub
		MyOnClickListener clickListener = new MyOnClickListener();
		back_image.setOnClickListener(clickListener);
		returngood_layout.setOnClickListener(clickListener);
	}
	
	private class MyOnClickListener implements OnClickListener{
		Calendar c = Calendar.getInstance();
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//返回键
			if(v.getId() == R.id.returngood_query_back){
				ReturngoodQueryActivity.this.finish();
			}
			//时间选择
			if(v.getId() == R.id.returngood_layout){
				new DoubleDatePickerDialog(ReturngoodQueryActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker startDatePicker,int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker,
							int endYear, int endMonthOfYear, int endDayOfMonth) {
						// TODO Auto-generated method stub
						begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1, startDayOfMonth);
						end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
						
						returngood_Starttime.setText(begin_date);
						returngood_Endtime.setText(end_date);
						goodsList.clear();
						nPage = 1;
						totalSize = nSpage;
						goodPresenter.loadreturnGood(user,begin_date, end_date, nPage, nSpage);
					}
					
				},c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
			}
		}
	}

	/**
	 * 刷新加载
	 */
	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		nPage+=1;
		if(totalSize >= pageSize){
			returngood_queryListView.hideFooterView();
			return;
		}else{
			goodPresenter.loadreturnGood(user,begin_date, end_date, nPage, nSpage);
		}
		totalSize += nSpage;
	}
	
	public void onDestroy(){
		if(goodPresenter!=null){
			goodPresenter.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void loadReturnGoodData(List<ReturnNote> goodsBeanList) {
		// TODO Auto-generated method stub
		if(goodsBeanList.size() > 0){
			goodsList.addAll(goodsBeanList);
			returngood_total_sum.setText("¥"+goodsList.get(0).getReturn_total_sum());
			pageSize = goodsList.get(0).getTotal_record();
			goodAdapter.notifyDataSetChanged();
		}else{
			goodAdapter.notifyDataSetChanged();
			showToast("当前无退货");
		}
		returngood_queryListView.hideFooterView();
		returngood_queryListView.setOnRefreshListener(ReturngoodQueryActivity.this);
	}

	@Override
	public void showFailureMsg(String errorMessage) {
		// TODO Auto-generated method stub
		showToast(errorMessage);
	}
}
