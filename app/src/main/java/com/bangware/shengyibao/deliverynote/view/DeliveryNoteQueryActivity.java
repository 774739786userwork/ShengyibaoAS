package com.bangware.shengyibao.deliverynote.view;

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
import com.bangware.shengyibao.deliverynote.adapter.DeliveryNoteQueryAdapter;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNotePresenter;
import com.bangware.shengyibao.deliverynote.presenter.impl.DeliveryNotePresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * 送货单查询
 * @author ccssll
 *
 */
public class DeliveryNoteQueryActivity extends BaseActivity implements OnRefreshListener,DeliveryNoteQueryView{
	private ImageView deliverynote_query_back;
	private RefreshListView DeliveryQueryListView;
	private TextView dateStart_time,dateEnd_time,total_sum,unpaid_total_sum,deliverys_sum;//查询时间控件，总销售额，未付，送货单数
	private LinearLayout date_layout;
	private List<DeliveryNote> querylist = new ArrayList<DeliveryNote>();
	private DeliveryNoteQueryAdapter deliverynotequeryAdapter;
	String begin_date;
	String end_date;
	private int show_type = 1;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	String currenttime = sdf.format(new Date());
	private int nPage = 1;
	private int nSpage = 10;
	public int totalSize = 0;
	private int MaxDateNum;  
	private DeliveryNotePresenter notePresenter;

	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_query_deliverynote);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		init();
		initview();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		date_layout = (LinearLayout) findViewById(R.id.date_layout);
		dateStart_time = (TextView) findViewById(R.id.date_start_time);
		dateEnd_time = (TextView) findViewById(R.id.date_end_time);
		total_sum = (TextView) findViewById(R.id.total_sum);
		unpaid_total_sum = (TextView) findViewById(R.id.unpaid_total_sum);
		deliverys_sum= (TextView) findViewById(R.id.deliverys_sum);
		deliverynote_query_back = (ImageView) findViewById(R.id.deliverynote_query_back);
		DeliveryQueryListView = (RefreshListView) this.findViewById(R.id.DeliveryQueryListView);
		begin_date=currenttime;
		end_date=currenttime;
		notePresenter = new DeliveryNotePresenterImpl(this);
		notePresenter.doLoad(user,begin_date, end_date, nPage, nSpage,show_type);
		dateStart_time.setText(begin_date);
		dateEnd_time.setText(end_date);
		total_sum.setText("¥0");
		unpaid_total_sum.setText("¥0");
		deliverys_sum.setText("0次");
		deliverynotequeryAdapter = new DeliveryNoteQueryAdapter(this, querylist);
		DeliveryQueryListView.setDividerHeight(0);
		DeliveryQueryListView.setAdapter(deliverynotequeryAdapter);
	}
	
	private void initview() {
		// TODO Auto-generated method stub
		MyOnClickLinstener clickLinstener = new MyOnClickLinstener();
		deliverynote_query_back.setOnClickListener(clickLinstener);
		date_layout.setOnClickListener(clickLinstener);
		
		//listview条目点击事件
		DeliveryQueryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeliveryNoteQueryActivity.this, DeliveryNoteDetailActivity.class);
				Bundle bundle = new Bundle();
				DeliveryNote deliveryNote = (DeliveryNote) adapterView.getItemAtPosition(position);
				bundle.putString("currenttime",currenttime);
				bundle.putSerializable("deliveryNote", deliveryNote);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	private class MyOnClickLinstener implements OnClickListener{
		Calendar c = Calendar.getInstance();
		@Override
		public void onClick(View v) {
			// 返回键
			if(v.getId() == R.id.deliverynote_query_back){
				DeliveryNoteQueryActivity.this.finish();
			}
			//时间选择控件
			if(v.getId() == R.id.date_layout){
				new DoubleDatePickerDialog(DeliveryNoteQueryActivity.this,  THEME_HOLO_LIGHT, new DoubleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
							int endDayOfMonth) {
						
						begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1, startDayOfMonth);
						end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
						
						dateStart_time.setText(begin_date);
						dateEnd_time.setText(end_date);

						if(java.sql.Date.valueOf(begin_date).after(java.sql.Date.valueOf(end_date))){
							total_sum.setText("¥0");
							unpaid_total_sum.setText("¥0");
							deliverys_sum.setText("0次");
							showToast("开始日期不能大于结束日期！");
						}
						else if(java.sql.Date.valueOf(end_date).after(java.sql.Date.valueOf(begin_date))){
							total_sum.setText("¥0");
							unpaid_total_sum.setText("¥0");
							deliverys_sum.setText("0次");
//							showToast("结束日期不能大于开始日期！！");
						}
						querylist.clear();
						nPage = 1;
						totalSize = nSpage;
						notePresenter.doLoad(user,begin_date, end_date, nPage, nSpage,show_type);
						deliverynotequeryAdapter.notifyDataSetInvalidated();
						
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
			DeliveryQueryListView.hideFooterView();
			showToast("暂无更多数据！请不要重复刷新！");
			return;
		}else{
			notePresenter.doLoad(user,begin_date, end_date, nPage, nSpage,show_type);
		}
		totalSize += nSpage;
	}

	@Override
	protected void onDestroy() {
		if(notePresenter!=null){
			notePresenter.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void loadDeliveryNoteData(List<DeliveryNote> noteList) {
		// TODO Auto-generated method stub
		if(noteList.size() > 0){
			querylist.addAll(noteList);
			for (int i = 0; i < querylist.size(); i++) {
				total_sum.setText("¥"+querylist.get(i).getD_total_sum()+"元");
				unpaid_total_sum.setText("¥"+querylist.get(i).getD_unpaid_total_sum()+"元");
				deliverys_sum.setText(String.valueOf(querylist.get(i).getTotal_record())+"张");
				MaxDateNum = querylist.get(i).getTotal_record();
			}
			deliverynotequeryAdapter.notifyDataSetChanged();
		}else{
			deliverynotequeryAdapter.notifyDataSetChanged();
			showDeliveryDialog();
		}
		DeliveryQueryListView.hideFooterView();
		DeliveryQueryListView.setOnRefreshListener(DeliveryNoteQueryActivity.this);
	}

	private void showDeliveryDialog(){
		showDialog(this,"暂无送货单数据！",1,new Handler(){
			@Override
			public void dispatchMessage(Message msg) {
				super.dispatchMessage(msg);
				if (msg.what == 1){
					finish();
				}
			}
		});
	}

}
