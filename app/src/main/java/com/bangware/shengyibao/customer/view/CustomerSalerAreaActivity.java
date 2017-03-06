package com.bangware.shengyibao.customer.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.adapter.CustomerSalerAreaAdapter;
import com.bangware.shengyibao.customer.model.entity.CustomerSalerArea;
import com.bangware.shengyibao.customer.presenter.CustomerSalerAreaPresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerSalerAreaPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

/**
 * 客户营销区域
 */


public class CustomerSalerAreaActivity extends BaseActivity implements CustomerSalerAreaView {
	private CustomerSalerAreaPresenter salerAreaPresenter;
	private List<CustomerSalerArea> salerArealList = new ArrayList<CustomerSalerArea>();
	private GridView saler_area_gridview;
	private CustomerSalerAreaAdapter salerAreaAdapter;
	private ImageView customer_salerArea_back;

	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_saler_area);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findViews();
		setListener();
	}
	
	private void findViews() {
		// TODO Auto-generated method stub
		saler_area_gridview = (GridView) findViewById(R.id.saler_area_gridview);
		customer_salerArea_back = (ImageView) findViewById(R.id.customer_salerArea_back);
		//营销区域数据请求
		salerAreaPresenter = new CustomerSalerAreaPresenterImpl(this);
		salerAreaPresenter.loadSalerAreaData(user);
		
		salerAreaAdapter = new CustomerSalerAreaAdapter(this, salerArealList);
		saler_area_gridview.setAdapter(salerAreaAdapter);
		
		//listview点击条目事件
		saler_area_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				//回传值到CustomerEditAvtivity的营销区域
				Intent intent = new Intent();
				intent.putExtra("salerAreaId", salerArealList.get(position).getId());
				intent.putExtra("salerArea", salerArealList.get(position).getName());
				setResult(1001,intent);
				finish();
			}
		});
	}
	
	private void setListener() {
		// TODO Auto-generated method stub
		MyOnClickListener clickListener = new MyOnClickListener();
		customer_salerArea_back.setOnClickListener(clickListener);
	}
	
	//返回按钮的点击事件
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.customer_salerArea_back){
				finish();
			}
		}
		
	}

	//数据请求入口
	@Override
	public void loadSalerAreaData(List<CustomerSalerArea> salerAreas) {
		// TODO Auto-generated method stub
		if(salerAreas.size() > 0 ){
			salerArealList.clear();
			salerArealList.addAll(salerAreas);
			salerAreaAdapter.notifyDataSetChanged();
		}else{
			showToast("暂无数据");
			salerAreaAdapter.notifyDataSetChanged();
		}
	}
}
