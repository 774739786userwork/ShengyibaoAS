package com.bangware.shengyibao.customer.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Customer;

public class CustomerDetailActivity extends BaseActivity {
	
	private ImageView customer_detail_back,customer_detail_img;
	private TextView edit_textview;
	private TextView customerName,customerCode,customerType,contactName,customerArea,customerAddress,customerMobile;
	private Customer customer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_customer_detail);
		
		findview();
		setListener();
	}
	
	private void findview() {
		// TODO Auto-generated method stub
		customer_detail_back = (ImageView) findViewById(R.id.customer_detail_back);
		customer_detail_img =  (ImageView) findViewById(R.id.customer_detail_img);
		edit_textview = (TextView) findViewById(R.id.edit_textview);
		customerName = (TextView) findViewById(R.id.customerName);
		customerCode = (TextView) findViewById(R.id.customerCode);
		
		customerType = (TextView) findViewById(R.id.customerType);
		contactName = (TextView) findViewById(R.id.contactName);
		customerArea = (TextView) findViewById(R.id.customerArea);
		customerAddress = (TextView) findViewById(R.id.customerAddress);
		customerMobile = (TextView) findViewById(R.id.customerMobile);
		
		customer = (Customer)getIntent().getExtras().getSerializable("customer");
		customerName.setText(customer.getName());
		customerCode.setText(customer.getCode());
		customerType.setText(customer.getKinds().get(0).getName());
		contactName.setText(customer.getContacts().get(0).getName());
//		customerAddress.setText(customer.getAddress());
		customerMobile.setText(customer.getContacts().get(0).getMobile1());
		customerArea.setText(customer.getDistrict());
		
		customerAddress.setText((CharSequence) getIntent().getExtras().getSerializable("marker_address"));
		
		//加载客户图片
		/*ImageRequester imageRequester = new ImageRequester(getApplicationContext(), true);
		customer_detail_img.setImageResource(R.drawable.no_pic_300);
	    String customer_imgae_url = customer.getImg_url();
	    if(customer_imgae_url!=null && !"".equals(customer_imgae_url)){
	    	imageRequester.load(customer_detail_img, Model.HTTPURL+ customer_imgae_url, R.drawable.no_pic_300, R.drawable.no_pic_300);
	    }*/
	}
	
	private void setListener() {
		// TODO Auto-generated method stub
		MyOnClickListener clicklistener = new MyOnClickListener();
		customer_detail_back.setOnClickListener(clicklistener);
		edit_textview.setOnClickListener(clicklistener);
		customer_detail_img.setOnClickListener(clicklistener);
	}
	
	private class MyOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//返回键处理
			if (v.getId() == R.id.customer_detail_back) {
				CustomerDetailActivity.this.finish();
			}
			//编辑处理
			if(v.getId() == R.id.edit_textview){
				Intent intent = new Intent(CustomerDetailActivity.this, null);
				Bundle bundle = new Bundle();
				bundle.putSerializable("customer", customer);
				bundle.putSerializable("remarker_address", customerAddress.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
			//图片点击查看
			if(v.getId() == R.id.customer_detail_img){
				/*Intent intent = new Intent(CustomerDetailActivity.this,ShowImageActivity.class);
				intent.putExtra("ImageUrl", Model.HTTPURL+ customer.getImg_url());
				startActivity(intent);*/
			}
		}
		
	}
}
