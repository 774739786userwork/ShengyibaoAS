package com.bangware.shengyibao.customer.view;

import java.util.ArrayList;
import java.util.List;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.customer.adapter.CustomerPurchaseAdapter;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;
import com.bangware.shengyibao.customer.presenter.CustomerInfoPresenter;
import com.bangware.shengyibao.customer.presenter.CustomerPurchasePresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerInfoPresenterImpl;
import com.bangware.shengyibao.customer.presenter.impl.CustomerPurchaseImpl;
import com.bangware.shengyibao.net.NetWork;
import com.bangware.shengyibao.shopcart.view.ShopCartAcitivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bumptech.glide.Glide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 客户详情页面
 * @author ccssll
 *
 */
public class CustomerInfoActivity extends BaseActivity implements CustomerPurchaseView{
	private TextView mLinear_billing;
	private ImageView customer_detail_back,customer_image;
	private TextView name_tv,text_code,store_type,contact_textview,addr_tv,City_text;
	private TextView no_record_text,query_more_text,nearby_textview_customer;
	private LinearLayout top_linearLayout;
	private RelativeLayout contact_relativeLayout_click,map_relative_layout;
	private CustomerPurchasePresenter purchasePresenter;
	private Customer customer;
	private ListView purchaseRecord_listview;
	private CustomerPurchaseAdapter purchaseAdapter;
	private Contacts contact;
	private List<CustomerPurchase> purchaseList = new ArrayList<CustomerPurchase>();
	private List<Customer> customerList = new ArrayList<Customer>();
	private CustomerInfoPresenter infoPresenter;
	private Bundle bundle;
	private int nPage = 1;
	private int nSpage = 5;
	NetWork netWork = NetWork.getInstance();
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_customerinfo);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findViews();
		setListeners();
	}
	
	//控件初始化
	private void findViews(){
		customer_image = (ImageView) findViewById(R.id.customer_image);
		nearby_textview_customer = (TextView) findViewById(R.id.nearby_textview_customer);
		name_tv = (TextView) findViewById(R.id.name_tv);
		text_code = (TextView) findViewById(R.id.text_code);
		store_type = (TextView) findViewById(R.id.store_type);
		contact_textview = (TextView) findViewById(R.id.contact_textview);
		addr_tv = (TextView) findViewById(R.id.addr_tv);
		no_record_text = (TextView) findViewById(R.id.no_record_text);
		query_more_text = (TextView) findViewById(R.id.query_more_text);
		top_linearLayout = (LinearLayout) findViewById(R.id.top_linearLayout);
		contact_relativeLayout_click = (RelativeLayout) findViewById(R.id.contact_relativeLayout_click);
		map_relative_layout = (RelativeLayout) findViewById(R.id.map_relative_layout);
		mLinear_billing = (TextView) findViewById(R.id.billing);
		customer_detail_back = (ImageView) findViewById(R.id.customer_detail_back);
		purchaseRecord_listview = (ListView) findViewById(R.id.purchaseRecord_listview);
		City_text= (TextView) findViewById(R.id.City_text);
		try {
			bundle = this.getIntent().getExtras();

			customer = (Customer)bundle.getSerializable("customer");
			//接收contacts
			contact=(Contacts) bundle.getSerializable("contacts");
			if (contact==null) {
				if (customer.getContacts().size() > 0) {
					contact=customer.getContacts().get(0);
				}
			}

			//进货记录请求
			purchasePresenter = new CustomerPurchaseImpl(this);
			purchasePresenter.queryCustomerPurchaseData(user,customer.getId(), nPage, nSpage, "", "");

			//客户信息资料请求
			infoPresenter = new CustomerInfoPresenterImpl(this);
			infoPresenter.queryCustomerInfoData(user,customer.getId());

			purchaseAdapter = new CustomerPurchaseAdapter(this,purchaseList);
			purchaseRecord_listview.setAdapter(purchaseAdapter);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	//初始化点击事件
	private void setListeners(){
		MyOnClickListener clickListener = new MyOnClickListener();
		customer_detail_back.setOnClickListener(clickListener);
		//判断网络状态
		if(netWork.IsConnect(CustomerInfoActivity.this)){
			mLinear_billing.setOnClickListener(clickListener);
			top_linearLayout.setOnClickListener(clickListener);
			contact_relativeLayout_click.setOnClickListener(clickListener);
			map_relative_layout.setOnClickListener(clickListener);
			query_more_text.setOnClickListener(clickListener);
			nearby_textview_customer.setOnClickListener(clickListener);
		}else{
			mLinear_billing.setClickable(false);
			top_linearLayout.setClickable(false);
			contact_relativeLayout_click.setClickable(false);
			map_relative_layout.setClickable(false);
			query_more_text.setClickable(false);
			nearby_textview_customer.setClickable(false);
		}
	}
	
	//自定义类实现点击事件
	private class MyOnClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			//返回键处理
			if(v.getId() == R.id.customer_detail_back){
				CustomerInfoActivity.this.finish();
				loadingdialog.dismiss();
			}
			if (v.getId() == R.id.nearby_textview_customer){
				Intent intent = new Intent(CustomerInfoActivity.this,NearbyCustomerActivity.class);
				Bundle bundle = new Bundle();
				//接收bundle传递过来的参数
				Customer customer = customerList.get(0);
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			//顶部客户信息点击跳转
			if(v.getId() == R.id.top_linearLayout){
				Intent intent = new Intent(CustomerInfoActivity.this, CustomerEditActivity.class);
				Bundle bundle = new Bundle();
				//接收bundle传递过来的参数
				Customer customer = customerList.get(0);
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			//联系人跳转
			if (v.getId() == R.id.contact_relativeLayout_click) {
				Intent intent = new Intent(CustomerInfoActivity.this, ContactActivity.class);
				Bundle bundle = new Bundle();
//				bundle.putString("customer_id", customer.getId());
				Customer customer = customerList.get(0);
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			//地理定位跳转
			if (v.getId() == R.id.map_relative_layout) {
				Intent intent = new Intent(CustomerInfoActivity.this, MapViewActivity.class);
				Bundle bundle = new Bundle();
				Customer customer = customerList.get(0);
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			//点击查看更多销售记录
			if(v.getId() == R.id.query_more_text){
				Intent intent = new Intent(CustomerInfoActivity.this, CustomerPurchaseMoreActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("customer_id", customer.getId());
				bundle.putString("customer_name", customer.getName());
				intent.putExtras(bundle);
				startActivity(intent);
			}
			//开单点击事件
			if(v.getId() == R.id.billing){
				Intent intent = new Intent(CustomerInfoActivity.this, ShopCartAcitivity.class);
				Bundle bundle = new Bundle();
				Customer customer = customerList.get(0);
				bundle.putSerializable("customer",  customer);
				//发送contacts_id
				bundle.putSerializable("contacts", contact);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}
	

	public void onDestroy(){
		if(purchasePresenter!=null)
			purchasePresenter.destory();
		super.onDestroy();
	}

	@Override
	public void loadPurchaseData(List<CustomerPurchase> purchases) {
		// TODO Auto-generated method stub
		if(purchases.size() > 0){
			purchaseList.clear();
			purchaseList.addAll(purchases);
			purchaseAdapter.notifyDataSetChanged();
		}else{
			no_record_text.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void loadCustomerInfoData(List<Customer> customers) {
		// TODO Auto-generated method stub
		if(customers.size() > 0){
			customerList.addAll(customers);
			Customer customer = customerList.get(0);
			name_tv.setText(customer.getName());
			text_code.setText(customer.getCode());
			String customerStr = "";
			for (int i = 0; i < customer.getContacts().size(); i++) {
				customerStr += customer.getContacts().get(i).getName()+" ";
			}
			contact_textview.setText(customerStr);
			store_type.setText(customer.getKinds().get(0).getName());
			addr_tv.setText(customer.getAddress());
			City_text.setText(customer.getCity());

			customer_image.setImageResource(R.drawable.no_pic_300);
			if(customer.getImages().size() > 0){
				String customer_imgae_url = customer.getImages().get(0).getImg_url();
				if(customer_imgae_url!=null && !"".equals(customer_imgae_url)){
					Glide.with(getApplicationContext()).load(Model.HTTPURL+ customer_imgae_url).placeholder(R.drawable.no_pic_300).error(R.drawable.no_pic_300)
					.into(customer_image);
			    }
			}
		}else{
			showToast("暂无客户资料");
		}
	}
}
