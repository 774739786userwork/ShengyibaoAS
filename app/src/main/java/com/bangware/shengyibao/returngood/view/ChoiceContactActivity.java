package com.bangware.shengyibao.returngood.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customercontacts.presenter.CustomerContactsPresenter;
import com.bangware.shengyibao.customercontacts.presenter.impl.CustomerContactsPresenterImpl;
import com.bangware.shengyibao.customercontacts.view.CustomerContactsView;
import com.bangware.shengyibao.returngood.adapter.ChoiceContactAdapter;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.ClearEditText;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;


/**
 * 开单选择客户
 * @author ccssll
 */
public class ChoiceContactActivity extends BaseActivity implements OnRefreshListener,CustomerContactsView {
	public static String text="";
	private ImageView customercontactquery_back;//回退按钮
	private RefreshListView mRefreshListView;
	private TextView mCustomerContactQuery_btn;//查询按钮
	private ImageView contactphonecall;//通讯录按钮
	private String username,usernumber;//声明变量已便于接收调取通讯录联系人号码和姓名
	private ClearEditText mClearEditText;
	private CustomerContactsPresenter presenter;
	int nPage=1;
	int nSpage=10;
	private int pageSize;
	public int totalSize = 0;
	String phone="";
	String contactName="";
	private List<Contacts> customerlist = new ArrayList<Contacts>();
	private ChoiceContactAdapter adapter;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去除页面标题
		setContentView(R.layout.activity_choice_contact);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		init();
		initView();
	}
	
	//初始化控件
	private void init() {
		//加载布局控件
		mRefreshListView = (RefreshListView) findViewById(R.id.choicecontact_ListView);
		customercontactquery_back = (ImageView) findViewById(R.id.customercontactquery_back);
		mClearEditText = (ClearEditText) findViewById(R.id.choicecontact_edit);
		mCustomerContactQuery_btn = (TextView) findViewById(R.id.choicecontact_btn);
		contactphonecall = (ImageView) findViewById(R.id.return_contactphonecall);
		presenter=new CustomerContactsPresenterImpl(this);
		presenter.loadCustomerContacts(user, nPage, nSpage, phone, contactName);

		adapter = new ChoiceContactAdapter(this,customerlist);
		mRefreshListView.setAdapter(adapter);
	}
	
	
	//初始化点击事件
	private void initView(){
		MyOnclickListener mOnclickListener = new MyOnclickListener();//声明实例化点击事件
		//给控件设置点击事件
		customercontactquery_back.setOnClickListener(mOnclickListener);
		mCustomerContactQuery_btn.setOnClickListener(mOnclickListener);
		contactphonecall.setOnClickListener(mOnclickListener);
		//给listview条目设置选择项事件
		mRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
									long arg3) {
				Intent intent = new Intent(ChoiceContactActivity.this, ReturnsProcessingActivity.class);
				Bundle bundle = new Bundle();
				Contacts contact = (Contacts)adapterView.getItemAtPosition(position);
				bundle.putSerializable("contacts",contact);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	//自定义类实现点击事件接口
	private class MyOnclickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			int cid = v.getId();
			//返回键处理
			if(cid == R.id.customercontactquery_back){
				loadingdialog.dismiss();
				finish();
			}
			//客户快捷查询
			if(cid == R.id.choicecontact_btn){
				ChoiceQuickQuery();
			}
			if (cid == R.id.return_contactphonecall){
				startActivityForResult(new Intent(
						Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
			}
		}
	}
	
	public void ChoiceQuickQuery(){
		text = mClearEditText.getText().toString();
		if(TextUtils.isEmpty(text)){
			showToast("查询条件不能为空！");
			return;
		}
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(text);
		if(m.matches()){
			phone = text;
			customerlist.clear();
			nPage = 1;
			totalSize = nSpage;
			contactName="";
			presenter.loadCustomerContacts(user, nPage, nSpage, phone,"");
		}

		p=Pattern.compile("[\u4e00-\u9fa5]*");
		m=p.matcher(text);
		if(m.matches()){
			contactName = text;
			customerlist.clear();
			nPage = 1;
			totalSize = nSpage;
			phone="";
			presenter.loadCustomerContacts(user, nPage, nSpage, "",contactName);
		}
	}

	//读取手机通讯录的回调函数方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			ContentResolver reContentResolverol = getContentResolver();
			Uri contactData = data.getData();
			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(contactData, null, null, null, null);
			cursor.moveToFirst();
			//获得DATA表中的名字
			username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			//条件为联系人ID
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

			// 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
			Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
					null,
					null);
			if (phone!=null){
				while (phone.moveToNext()) {
					usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					// 对手机号码进行预处理（去掉号码前的+86、首尾空格、“-”号等）
					usernumber = usernumber.replaceAll("^(\\+86)", "");
					usernumber = usernumber.replaceAll("^(86)", "");
					usernumber = usernumber.replaceAll("-", "");
					usernumber = usernumber.replaceAll(" ", "");
					usernumber = usernumber.trim();
					mClearEditText.setText(usernumber);
				}
				phone.close();
			}
		}
	}
	
	@Override
	public void onDownPullRefresh() {

	}

	@Override
	public void onLoadingMore() {
		nPage+=1;
		if(totalSize >= pageSize){
			mRefreshListView.hideFooterView();
			return;
		}else{
			presenter.loadCustomerContacts(user, nPage, nSpage, phone, contactName);
		}
		totalSize += nSpage;
	}



	@Override
	public void doCustomerContactsLoadSuccess(List<Contacts> Contacts) {
		if(Contacts.size() > 0){
			customerlist.addAll(Contacts);
			pageSize = customerlist.get(0).getCustomer().getTotal_record_sum();
			adapter.notifyDataSetChanged();

		}else{
			showToast("暂无客户数据！");
			//		customerlist.clear();
			adapter.notifyDataSetChanged();
		}
		mRefreshListView.hideFooterView();
		mRefreshListView.setOnRefreshListener(ChoiceContactActivity.this);
	}
	@Override
	protected void onDestroy() {
		if(presenter!=null){
			presenter.onDestroy();
		}
		super.onDestroy();
	}
}
