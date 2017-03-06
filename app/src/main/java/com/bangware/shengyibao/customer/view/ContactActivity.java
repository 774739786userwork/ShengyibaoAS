package com.bangware.shengyibao.customer.view;

import java.util.ArrayList;
import java.util.List;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.adapter.ContactListAdapter;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.presenter.ContactPresenter;
import com.bangware.shengyibao.customer.presenter.impl.ContactPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

public class ContactActivity extends BaseActivity implements ContactListAdapter.Callback_detail,ContactView{
	private List<Contacts> contacts = new ArrayList<Contacts>();
	private ImageView contact_back;
	private TextView contact_add;
	private ListView contacts_listview;
	private ContactListAdapter listAdapter;
	private ContactPresenter contactPresenter;
	private Customer customer;
	public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
	public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
	private String mobile,sendmessage;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findview();
		setListener();
	}
	
	private void findview() {
		contact_back = (ImageView) findViewById(R.id.contact_back);
		contacts_listview = (ListView) findViewById(R.id.contacts_listview);
		contact_add=(TextView) findViewById(R.id.contact_add);
		
		Bundle bundle = this.getIntent().getExtras();
		customer = (Customer)bundle.getSerializable("customer");
		
		contactPresenter = new ContactPresenterImpl(this);
		contactPresenter.loadContact(user,customer.getId());
		
		listAdapter = new ContactListAdapter(this, contacts, this);
		contacts_listview.setAdapter(listAdapter);
	}
	
	private void setListener() {
		MyOnClickListener clickListener = new MyOnClickListener();
		contact_back.setOnClickListener(clickListener);
		contact_add.setOnClickListener(clickListener);
	}
	
	private class MyOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.contact_back) {
				ContactActivity.this.finish();
			}
			if (v.getId()==R.id.contact_add) {
				Intent intent=new Intent(ContactActivity.this, AddContactsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
		
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (which) {
        case R.id.phone_img:
        	callMobile(contacts.get(position).getMobile1());
            break;
        case R.id.msg_img:
        	sendSMS(contacts.get(position).getMobile1());
            break;
        case R.id.phone_img_two:
        	callMobile(contacts.get(position).getMobile2());
            break;
        case R.id.msg_img_two:
        	sendSMS(contacts.get(position).getMobile2());
            break;
        case R.id.editor_tv:
        	Intent intent=new Intent(this,UpdateContactsActivity.class);
    		intent.putExtra("contacts", customer.getContacts().get(position));
        	startActivity(intent);
        	break;
        default:
            break;
        }
		
	}
	
	//发送短信
	private void sendSMS(String smsBody)  
	{  
		Uri smsToUri = Uri.parse("smsto:"+smsBody);  
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
		startActivity(intent);  
	}
	public void onSMS(String smsBody){
		this.sendmessage = smsBody;
		if(Build.VERSION.SDK_INT >= 23) {
			int checkSMSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
			if(checkSMSPermission != PackageManager.PERMISSION_GRANTED){
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
				return;
			}else{
				//上面已经写好的拨号方法
				sendSMS(sendmessage);
			}
		}else{
			sendSMS(sendmessage);
		}
	}

	
	//拨打电话
	private void callMobile(String telephone){
		Uri callToUri = Uri.parse("tel:"+telephone);
		Intent intent=new Intent(Intent.ACTION_CALL,callToUri);
		intent.putExtra("tele_phone", telephone);
		startActivity(intent);
	}

	public void onCall(String mobile){
		if(Build.VERSION.SDK_INT >= 23) {
			this.mobile= mobile;
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
			if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
				return;
			}else{
				callMobile(mobile);
			}
		}else{
			callMobile(mobile);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_CALL_PHONE:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// Permission Granted
					callMobile(mobile);
				} else {
					// Permission Denied
					showToast("用户取消了权限");
				}
				break;
			case MY_PERMISSIONS_REQUEST_SEND_SMS:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// Permission Granted
					sendSMS(sendmessage);
				} else {
					// Permission Denied
					showToast("用户取消了权限");
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	@Override
	public void loadContact(List<Contacts> contactList) {
		if(contactList.size() > 0){
			contacts.addAll(contactList);
			listAdapter.notifyDataSetChanged();
		}else{
			showToast("暂无联系人");
		}
	}
	
	public void onDestroy(){
		super.onDestroy();
		if(this.contactPresenter!=null)
			this.contactPresenter.destory();
	}
}
