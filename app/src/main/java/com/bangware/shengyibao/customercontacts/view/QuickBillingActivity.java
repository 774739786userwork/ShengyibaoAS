package com.bangware.shengyibao.customercontacts.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customercontacts.QuickBillingAdapter;
import com.bangware.shengyibao.customercontacts.presenter.CustomerContactsPresenter;
import com.bangware.shengyibao.customercontacts.presenter.impl.CustomerContactsPresenterImpl;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.shopcart.view.ShopCartAcitivity;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

/**
 * 快速开单选择客户联系人
 * @author zcb
 *
 */
public class QuickBillingActivity extends BaseActivity implements OnRefreshListener,CustomerContactsView {
  private RefreshListView mRLview;
  private ImageView mCuslist_back;//回退按钮
  private Button mQuery_Btn;//搜索按钮
  private CustomerContactsPresenter presenter;
  private int nPage = 1;
  private int nSpage = 10;
  String phone="";
  String contactName="";
  private List<Contacts> contactslist = new ArrayList<Contacts>();
  private QuickBillingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_quickbilling);
    	findViews();//控件的初始化
    	setListeners();//点击事件的初始化

    	adapter = new QuickBillingAdapter(this,contactslist);
    	mRLview.setAdapter(adapter);
    }
	private void setListeners() {
		MyOnclickListener mOnclickListener = new MyOnclickListener();//声明实例化点击事件
		mCuslist_back.setOnClickListener(mOnclickListener);
		mQuery_Btn.setOnClickListener(mOnclickListener);
		mRLview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,long arg3) {
				Intent intent = new Intent(QuickBillingActivity.this, ShopCartAcitivity.class);
				//用Bundle传递数据
				Contacts contact = (Contacts)adapterView.getItemAtPosition(position);
				Customer customer = contact.getCustomer();
				Bundle bundle = new Bundle();
				bundle.putSerializable("contacts", contact);
				bundle.putSerializable("customer",  customer);
				//发送contacts_id
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	private void findViews() {
		mRLview=(RefreshListView) findViewById(R.id.QuickbillingListView);
		mCuslist_back=(ImageView) findViewById(R.id.Quickbilling_back);
		mQuery_Btn=(Button) findViewById(R.id.QuickbillingQuery_Btn);
    	presenter=new CustomerContactsPresenterImpl(this);
    	presenter.loadCustomerContacts(AppContext.getInstance().getUser(), nPage, nSpage, phone, contactName);
	}
	private class MyOnclickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			int cid = v.getId();
			//返回键处理
			if(cid == R.id.Quickbilling_back){
				loadingdialog.dismiss();
				Intent intent=new Intent(QuickBillingActivity.this, MainActivity.class);
				startActivity(intent);
				QuickBillingActivity.this.finish();
			}
			//联系人搜索
			if(cid == R.id.QuickbillingQuery_Btn){
				Intent intent=new Intent(QuickBillingActivity.this,QueryQuickBilingActivity.class);
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
		presenter.loadCustomerContacts(AppContext.getInstance().getUser(), nPage, nSpage, phone,contactName);
		mRLview.hideFooterView();
	}
	//退出键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        QuickBillingActivity.this.finish();
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
		mRLview.setOnRefreshListener(QuickBillingActivity.this);
	}
	public void onDestroy(){
		super.onDestroy();
		if(this.presenter!=null)
			this.presenter.onDestroy();
	}
}
