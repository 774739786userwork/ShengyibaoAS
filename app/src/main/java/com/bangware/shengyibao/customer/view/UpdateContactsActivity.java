package com.bangware.shengyibao.customer.view;
/**
 * 编辑联系人界面
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.presenter.UpdateContactsPresenter;
import com.bangware.shengyibao.customer.presenter.impl.UpdateContactsPresenterImp;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.customdialog.CommonDialog;

public class UpdateContactsActivity extends BaseActivity implements UpdateContactsView {
	private EditText mUpdateName,mUpdateMobile1,mUpdateMoblie2,mUpdatePosition;
	private ImageView mUpdate_back;
	private Button mUpdate_submit;
	private TextView mUpdate_delete;
	private String name,mobile1,mobile2,position;
	private Contacts contact;
	private UpdateContactsPresenter presenter;
	private CommonDialog customDialog;
	private User user;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_updatecontacts);

		 SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		 user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
    	findView();//找到控件
    	setListener();//初始化点击事件
    }

	private void setListener() {
		MyOnClickLinstener onClickLinstener=new MyOnClickLinstener();
		mUpdate_back.setOnClickListener(onClickLinstener);
		mUpdate_submit.setOnClickListener(onClickLinstener);
		mUpdate_delete.setOnClickListener(onClickLinstener);
	}

	private void findView() {
		
		mUpdateName=(EditText) findViewById(R.id.update_contact_name);
		mUpdateMobile1=(EditText) findViewById(R.id.update_contact_phone1);
		mUpdateMoblie2=(EditText) findViewById(R.id.update_contact_phone2);
		mUpdatePosition=(EditText) findViewById(R.id.update_contact_position);
		mUpdate_back=(ImageView) findViewById(R.id.update_contact_back);
		mUpdate_submit=(Button) findViewById(R.id.update_contact_submit);
		mUpdate_delete=(TextView) findViewById(R.id.delete_contact);

		presenter=new UpdateContactsPresenterImp(this);
		contact=(Contacts)getIntent().getExtras().getSerializable("contacts");
		mUpdateName.setText(contact.getName());
        mUpdateName.setSelection(contact.getName().length());
		mUpdateMobile1.setText(contact.getMobile1());
		mUpdateMoblie2.setText(contact.getMobile2());
		mUpdatePosition.setText(contact.getPosition());
		//判断返回的值如果为null就设为空  显示默认的值
		if ((mUpdatePosition.getText().toString()).equals("null")) {
			mUpdatePosition.setText("");
		}
		if((mUpdateMoblie2.getText().toString()).equals("null"))
		{
			mUpdateMoblie2.setText("");
		}
	}
	private void init() {
		if (!TextUtils.isEmpty(mUpdateName.getText().toString())&&isPhoneNumberValid(mUpdateMobile1.getText().toString()))
		   {
			name=mUpdateName.getText().toString();
			mobile1=mUpdateMobile1.getText().toString();
			mobile2=mUpdateMoblie2.getText().toString();
			position=mUpdatePosition.getText().toString();
		    
			presenter.updateContacts(user, contact.getId(), name, mobile1, mobile2, position);
			Intent intent=new Intent(UpdateContactsActivity.this,MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		//回退事件
		case R.id.update_contact_back:
			loadingdialog.dismiss();
			UpdateContactsActivity.this.finish();
			break;
		//提交修改信息
		case R.id.update_contact_submit:
			init();
			break;
		case R.id.delete_contact:
			//删除联系人 弹出Dialog 提示是否删除联系人
			isDeleteContact();
		}
	  }
	}


	private void isDeleteContact(){
		customDialog = null;
		int srceenW =  ((BaseActivity)this).getWindowManager().getDefaultDisplay().getWidth();
		//联系人对话框
		customDialog = new CommonDialog(UpdateContactsActivity.this,srceenW, R.layout.common_dialog_layout,R.style.custom_dialog);
		TextView tv_dialog_login_context = (TextView)customDialog.findViewById(R.id.tv_dialog_common_context);
		TextView tv_dialog_login_go = (TextView)customDialog.findViewById(R.id.tv_dialog_common_go);
		TextView tv_dialog_login_close = (TextView)customDialog.findViewById(R.id.tv_dialog_common_close);
		tv_dialog_login_context.setText("是否删除该联系人？");
		tv_dialog_login_go.setText("绝不留情");
		customDialog.setCanceledOnTouchOutside(false);
		tv_dialog_login_go.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//设置你的操作事项
				if (contact.isFirst()) {
					showToast("首要联系人不可删除，只可修改");
					return;
				}else{
					presenter.deleteContacts(user, contact.getId());
					Intent intent=new Intent(UpdateContactsActivity.this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					UpdateContactsActivity.this.finish();
				}
				customDialog.dismiss();
			}
		});
		tv_dialog_login_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				customDialog.dismiss();
			}
		});
		customDialog.show();
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
	public void updateContactsSuccessMessage(String successMessage) {
		showToast("操作成功");
	}

	@Override
	public void deleteContactsSuccessMessage(String successMessage) {
		showToast("操作成功");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(this.presenter!=null)
			this.presenter.destory();
	}
	
}
