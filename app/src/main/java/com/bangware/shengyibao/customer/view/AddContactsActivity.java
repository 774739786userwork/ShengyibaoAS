package com.bangware.shengyibao.customer.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.presenter.AddContactsPresenter;
import com.bangware.shengyibao.customer.presenter.impl.AddContactsPresenterImp;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;


/**
 * 添加联系人界面
 * @author zcb
 *
 */
public class AddContactsActivity extends BaseActivity implements AddContactsView{
   private TextView mAddcontact_name;//添加联系人的姓名
   private TextView mAddcontact_phone1;//添加联系人的第一个手机号码
   private TextView mAddcontact_phone2;//添加联系人的第二个手机号码
   private TextView mAddcontact_position;//添加联系人职务
   private Button mAddcontact_submit;//提交
   private ImageView mAddcontact_back;//返回键
   private AddContactsPresenter presenter;
   private String customer_id,name,mobile1,mobile2,position;
   private Customer customer;
	private User user;
  @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_addcontacts);

	  SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
	  user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
	findView();//初始化控件
	setListener();//初始化点击事件
}

private void setListener() {
	MyOnClickLinstener clickLinstener=new MyOnClickLinstener();
	mAddcontact_back.setOnClickListener(clickLinstener);
	mAddcontact_submit.setOnClickListener(clickLinstener);
}

private void findView() {
	mAddcontact_name=(TextView) findViewById(R.id.addcontact_name);
	mAddcontact_phone1=(TextView) findViewById(R.id.addcontact_phone1);
	mAddcontact_phone2=(TextView) findViewById(R.id.addcontact_phone2);
	mAddcontact_position=(TextView) findViewById(R.id.addcontact_position);
	mAddcontact_submit=(Button) findViewById(R.id.addcontact_submit);
	mAddcontact_back=(ImageView) findViewById(R.id.addcontact_back);
	
	presenter=new AddContactsPresenterImp(this);
	Bundle bundle = this.getIntent().getExtras();
	customer = (Customer)bundle.getSerializable("customer");
	
}
//得到输入框的内容信息
private void init() {
	if (!TextUtils.isEmpty(mAddcontact_name.getText().toString())&&isPhoneNumberValid(mAddcontact_phone1.getText().toString()))
	   {
		name=mAddcontact_name.getText().toString();
		mobile1=mAddcontact_phone1.getText().toString();
		mobile2=mAddcontact_phone2.getText().toString();
		position=mAddcontact_position.getText().toString();
		presenter.addContacts(user, customer.getId(), name, mobile1, mobile2, position);
		Log.d("TGA", customer_id+" "+name+" "+mobile1+" "+mobile2+" "+position);
		Intent intent=new Intent(AddContactsActivity.this,CustomerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle bundle = new Bundle();
		bundle.putSerializable("customer", customer);
		intent.putExtras(bundle);
		startActivity(intent);
	   }else
	   {
		   showToast("姓名和手机1号码不能为空或号码格式不正确");
	   }
}
private class MyOnClickLinstener implements OnClickListener
{
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.addcontact_back:
		loadingdialog.dismiss();
		AddContactsActivity.this.finish();
		break;
	case R.id.addcontact_submit:
		init();
		
		break;
	}
  }
}
//判断电话号码是否正确输入
public boolean isPhoneNumberValid(String phoneNumber) {
	String expression = "((^(13|15|18|17|14)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
    CharSequence inputStr = phoneNumber;
 
    Pattern pattern = Pattern.compile(expression);
 
    Matcher matcher = pattern.matcher(inputStr);
 
    if (matcher.matches()) {
    	return true;
    }else{
    	showToast("手机或电话号码不正确!格式如：13855558888或01088885555");
    }
	return false;
}

@Override
public void addContactsSuccessMessage(String successMessage) {
	// TODO Auto-generated method stub
	showToast("添加成功");
}
@Override
public void onDestroy(){
	super.onDestroy();
	if(this.presenter!=null)
		this.presenter.destory();
}
}
