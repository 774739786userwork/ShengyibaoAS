package com.bangware.shengyibao.customercontacts.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.view.CustomerInfoActivity;
import com.bangware.shengyibao.customer.view.UpdateContactsActivity;

/**
 * 联系人详情界面
 * Detail 详情
 * @author zcb
 *
 */
public class ContactDetailsActivity extends BaseActivity {
   private ImageView mConDetailList_back;//回退键
   private TextView mCon_Detail_name;//联系人姓名
   private TextView mCon_Detail_Shopname;//联系人店名
   private TextView mContact_Detail_mobile1;//第一个手机号码
   private TextView mContact_Detail_mobile2;//第二个手机号码
   private TextView mContact_Detail_Position;//职务
   private TextView mContact_Detail_PositionValue;//职务名称
   private TextView mContact_Detail_Mobile;//第一手机号码的名称
   private TextView mContact_Detail_Mobilephone;//第二个手机号码的名称
   private ImageView mCall_phone1;//第一个手机的拨号点击按钮
   private ImageView mCall_phone2;//第二个手机的拨号点击按钮
   private TextView mContact_editor;//编辑按钮
   private ImageView mMsg_one,mMsg_two;//发送短信点击按钮
   private Contacts contact;//传递的联系人对象
   private View customer_contact_view1,customer_contact_view2,customer_contact_view3;//下划线
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_contactdetails);
	findView();//控件的初始化
	setListener();//点击事件的初始化
	initView();//初始化下划线
	initData();//初始化数据
	
}

private void initView() {
     customer_contact_view1=findViewById(R.id.customer_contact_view1);
	 customer_contact_view2=findViewById(R.id.customer_contact_view2);
	 customer_contact_view3=findViewById(R.id.customer_contact_view3);
}

private void initData() {
	contact = (Contacts) getIntent().getExtras().getSerializable("contact");
	//给控件赋值 
	    mCon_Detail_Shopname.setText(contact.getCustomer().getName());
		mCon_Detail_name.setText(contact.getName());
		mContact_Detail_mobile1.setText(contact.getMobile1());
		mContact_Detail_PositionValue.setText(contact.getPosition());
	   Log.d("TAG", "qweqrqwrq"+mContact_Detail_PositionValue.getText().toString());
		mContact_Detail_mobile2.setText(contact.getMobile2());
		//如果控件有值就显示控件，没有值就隐藏控件
	if ((mContact_Detail_mobile1.getText().toString()).equals("")||(mContact_Detail_mobile1.getText().toString()).equals("null")) {
		mContact_Detail_Mobile.setVisibility(View.GONE);
		mContact_Detail_mobile1.setVisibility(View.GONE);
		mMsg_one.setVisibility(View.GONE);
		mCall_phone1.setVisibility(View.GONE);
		customer_contact_view1.setVisibility(View.GONE);
	}else
	{
		mContact_Detail_Mobile.setVisibility(View.VISIBLE);
		mContact_Detail_mobile1.setVisibility(View.VISIBLE);
		mCall_phone1.setVisibility(View.VISIBLE);
		mMsg_one.setVisibility(View.VISIBLE);
	}
	
	if ((mContact_Detail_mobile2.getText().toString()).equals("")||(mContact_Detail_mobile2.getText().toString()).equals("null")) {
		mContact_Detail_Mobilephone.setVisibility(View.GONE);
		mContact_Detail_mobile2.setVisibility(View.GONE);
		mCall_phone2.setVisibility(View.GONE);
		mMsg_two.setVisibility(View.GONE);
		customer_contact_view2.setVisibility(View.GONE);
	}else
	{
		
		mContact_Detail_Mobilephone.setVisibility(View.VISIBLE);
		mContact_Detail_mobile2.setVisibility(View.VISIBLE);
		mCall_phone2.setVisibility(View.VISIBLE);
		mMsg_two.setVisibility(View.VISIBLE);
	}
		if ((contact.getPosition()).equals("")||(contact.getPosition()).equals("null")) {
			mContact_Detail_PositionValue.setVisibility(View.GONE);
			mContact_Detail_Position.setVisibility(View.GONE);
			customer_contact_view3.setVisibility(View.GONE);
		}else
		{
			mContact_Detail_PositionValue.setVisibility(View.VISIBLE);
			mContact_Detail_Position.setVisibility(View.VISIBLE);
		}
}

private void setListener() {
	MyOnclickListener myOnclickListener=new MyOnclickListener();
	mContact_editor.setOnClickListener(myOnclickListener);
	mConDetailList_back.setOnClickListener(myOnclickListener);
	mCall_phone1.setOnClickListener(myOnclickListener);
	mCall_phone2.setOnClickListener(myOnclickListener);
	mCon_Detail_Shopname.setOnClickListener(myOnclickListener);
	mMsg_one.setOnClickListener(myOnclickListener);
	mMsg_two.setOnClickListener(myOnclickListener);
}

private void findView() {
	mConDetailList_back=(ImageView) findViewById(R.id.ConDetailList_back);
	mCon_Detail_name=(TextView) findViewById(R.id.Contact_Detail_name);
    mCon_Detail_Shopname=(TextView) findViewById(R.id.Con_Detail_Shopname);
	mContact_Detail_Mobile=(TextView) findViewById(R.id.Contact_Detail_mobile);
	mContact_Detail_Mobilephone=(TextView) findViewById(R.id.Contact_Detail_mobilephone);
	mContact_Detail_mobile1=(TextView) findViewById(R.id.Contact_Detail_mobile1);
	mContact_Detail_mobile2=(TextView) findViewById(R.id.Contact_Detail_mobilephone2);
	mContact_Detail_Position=(TextView) findViewById(R.id.Contact_Detail_Position);
	mContact_Detail_PositionValue=(TextView) findViewById(R.id.Contact_Detail_PositionValue);
	mMsg_one=(ImageView) findViewById(R.id.msg_one);
	mMsg_two=(ImageView) findViewById(R.id.msg_two);
	mContact_editor=(TextView) findViewById(R.id.Contact_editor);
	mCall_phone1=(ImageView) findViewById(R.id.Call_Phone1);
	mCall_phone2=(ImageView) findViewById(R.id.Call_Phone2);
	

}
private class MyOnclickListener implements View.OnClickListener{

	@Override
	public void onClick(View v) {
		int cid = v.getId();
		//返回键处理
		if(cid == R.id.ConDetailList_back){
			loadingdialog.dismiss();
			finish();
		}
		//跳转到客户详情界面
		if (cid==R.id.Con_Detail_Shopname) {
			Intent intent=new Intent(ContactDetailsActivity.this,CustomerInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("customer_id", contact.getCustomer().getId());
		    bundle.putSerializable("customer", contact.getCustomer());
			//传contact
			bundle.putSerializable("contacts", contact);
			intent.putExtras(bundle);
			startActivity(intent);
			
		}
		//跳转到联系人编辑界面
		if (cid==R.id.Contact_editor) {
			Intent intent=new Intent(ContactDetailsActivity.this, UpdateContactsActivity.class);
			intent.putExtra("contacts", contact);
			startActivity(intent);
		}
		if(cid==R.id.Call_Phone1)
		{
			callMobile(mContact_Detail_mobile1.getText().toString());
		}
		if (cid==R.id.Call_Phone2) {
			callMobile(mContact_Detail_mobile2.getText().toString());
		}
		if (cid==R.id.msg_one) {
			sendSMS(contact.getMobile1());
		}
		if (cid==R.id.msg_two) {
			sendSMS(contact.getMobile2());
		}
	}
	}
//拨打电话
private void callMobile(String telephone){
	Uri callToUri = Uri.parse("tel:"+telephone);
	Intent intent=new Intent(Intent.ACTION_CALL,callToUri);
	intent.putExtra("tele_phone", telephone);
	Log.d("TAG", "拨打电话：：：：：：：：：：：-------->"+telephone);
	startActivity(intent);
}
//发送短信
	private void sendSMS(String smsBody)  
	{  
		Uri smsToUri = Uri.parse("smsto:"+smsBody);  
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
		Log.d("TAG", "发送短信：：：：：：：：：：：=========>"+smsBody);
		intent.putExtra("sms_body", smsBody);  
		startActivity(intent);  
	}  
}
