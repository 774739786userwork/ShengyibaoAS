package com.bangware.shengyibao.customercontacts.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customercontacts.CustomerContactAdapter;
import com.bangware.shengyibao.customercontacts.presenter.CustomerContactsPresenter;
import com.bangware.shengyibao.customercontacts.presenter.impl.CustomerContactsPresenterImpl;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

/**
 * 客户联系人界面
 * @author zcb
 *
 */
public class CustomerContactsActivity extends BaseActivity implements OnRefreshListener,CustomerContactsView {
  private RefreshListView mRLview;
  private ImageView mCuslist_back;//回退按钮
  private Button mQuery_Btn;//搜索按钮
  private CustomerContactsPresenter presenter;
  private int nPage = 1;
  private int nSpage = 50;
  String phone="";
  String contactName="";
  private List<Contacts> contactslist = new ArrayList<Contacts>();
  private CustomerContactAdapter adapter;
	private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_customer_contacts);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
    	findViews();//控件的初始化
    	setListeners();//点击事件的初始化

    }
	private void setListeners() {
		MyOnclickListener mOnclickListener = new MyOnclickListener();//声明实例化点击事件
		mCuslist_back.setOnClickListener(mOnclickListener);
		mQuery_Btn.setOnClickListener(mOnclickListener);
		//Adapter条目的点击事件
		mRLview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,long arg3) {
				Intent intent = new Intent(CustomerContactsActivity.this, ContactDetailsActivity.class);
				//用Bundle传递数据
				Contacts contact = (Contacts)adapterView.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("contact", contact);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	private void findViews() {
		mRLview=(RefreshListView) findViewById(R.id.CustomerContactListView);
		mCuslist_back=(ImageView) findViewById(R.id.CusConlist_back);
		mQuery_Btn=(Button) findViewById(R.id.Query_Btn);
    	presenter=new CustomerContactsPresenterImpl(this);
    	presenter.loadCustomerContacts(user, nPage, nSpage, phone, contactName);

		adapter = new CustomerContactAdapter(this,contactslist);
		mRLview.setAdapter(adapter);
	}
	private class MyOnclickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			int cid = v.getId();
			//返回键处理
			if(cid == R.id.CusConlist_back){
				loadingdialog.dismiss();
				Intent intent=new Intent(CustomerContactsActivity.this, MainActivity.class);
				startActivity(intent);
				CustomerContactsActivity.this.finish();
			}
			//联系人搜索
			if(cid == R.id.Query_Btn){
				Intent intent=new Intent(CustomerContactsActivity.this,QueryCustomerContactsActivity.class);
			    startActivity(intent);
			}
			
		}
		}
	@Override
	public void onDownPullRefresh() {
		
	}
	//下拉加载
	@Override
	public void onLoadingMore() {
		nPage+=1;
		presenter.loadCustomerContacts(user, nPage, nSpage, phone,contactName);
		mRLview.hideFooterView();
	}
	//退出键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        CustomerContactsActivity.this.finish();
	    }
	    return true;
	}
	@Override
	public void doCustomerContactsLoadSuccess(List<Contacts> contacts) {
		// TODO Auto-generated method stub
		if(contacts.size() > 0){
//			customerlist.clear();
			contactslist.addAll(contacts);
			adapter.notifyDataSetChanged();
		}else{
			showToast("暂无联系人数据！");
		}
		mRLview.hideFooterView();
		mRLview.setOnRefreshListener(CustomerContactsActivity.this);
	}
	public void onDestroy(){
		super.onDestroy();
		if(this.presenter!=null)
			this.presenter.onDestroy();
	}
}
