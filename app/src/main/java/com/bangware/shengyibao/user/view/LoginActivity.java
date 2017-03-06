package com.bangware.shengyibao.user.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.manager.JPushMessageManager;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.presenter.UserPresenter;
import com.bangware.shengyibao.user.presenter.impl.UserPresenterImpl;
import com.bangware.shengyibao.utils.AppContext;


public class LoginActivity extends BaseActivity implements LoginView {

	private long mExitTime= System.currentTimeMillis();
	private EditText mLogin_user, mLogin_password;
	private TextView mLogin_OK;
	private CheckBox mIsRememberMe;
	private UserPresenter userPresenter;
	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		findViews();
		setListeners();
		userPresenter = new UserPresenterImpl(this);
	}

	private void findViews() {
		mLogin_user = (EditText) findViewById(R.id.Login_user);
		mLogin_password = (EditText) findViewById(R.id.Login_password);
		mIsRememberMe = (CheckBox)findViewById(R.id.Login_rememberme);

		/**
		 * 判断记住密码状态
		 */
		sharedPreferences = this.getSharedPreferences(User.SHARED_NAME, MODE_PRIVATE);
		if (sharedPreferences.getBoolean("ISCHECK", true)){
			//设置默认是记录密码状态
			mIsRememberMe.setChecked(true);
			mLogin_user.setText(sharedPreferences.getString("username", ""));
			mLogin_password.setText(sharedPreferences.getString("password", ""));
		}else{
			mIsRememberMe.setChecked(false);
			mLogin_user.setText(sharedPreferences.getString("username", ""));
			mLogin_password.setText("");
		}

		mLogin_OK = (TextView) findViewById(R.id.Login_OK);
		
	}
	
	private void setListeners(){
		MyOnClickListener myonclick = new MyOnClickListener();
		mLogin_OK.setOnClickListener(myonclick);

		mIsRememberMe.setOnCheckedChangeListener(changeListener);//给记住密码设置选中点击事件
	}

	private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (buttonView.getId() == R.id.Login_rememberme){
				//判断点击是否被选中
				if (isChecked){
					mIsRememberMe.setChecked(true);
					sharedPreferences.edit().putBoolean("ISCHECK",true).commit();
				}else{
					mIsRememberMe.setChecked(false);
					sharedPreferences.edit().putBoolean("ISCHECK",false).commit();
				}
			}
		}
	};
	
	private class MyOnClickListener implements View.OnClickListener {
		public void onClick(View arg0) {
			int mID = arg0.getId();
			
			if (mID == R.id.Login_OK) {
				String username = mLogin_user.getText().toString().trim();
				String password = mLogin_password.getText().toString().trim();
				
				if(username==null || "".equals(username)){
					Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_LONG).show();
					return;
				}
				if(password==null || "".equals(password)){
					Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
					return;
				}
				//Toast.makeText(LoginActivity.this, "登录按钮被单击", 1).show();
				userPresenter.doLogin(username, password);
				//调用消息推送接口发送手机设备序列号到后台
			}
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();// 更新mExitTime
			} else {

				System.exit(0);// 否则退出程序
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void loginSuccess(){
		Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
		cacheUser(AppContext.getInstance().getUser());
		Intent intent = new Intent(LoginActivity.this,MainActivity.class);
		this.startActivity(intent);	
		this.finish();
		JPushMessageManager pushMessageManager = new JPushMessageManager(LoginActivity.this);
		pushMessageManager.doMessage();
	}
	@Override
	public void onloginError(String ErrorMessage){
		showMessage(ErrorMessage);
	}

	/**
	 * 缓存用户信息
	 */
	private void cacheUser(User user){
		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		
		editor.putString("login_token", user.getLogin_token());
		editor.putString("user_id", user.getUser_id());
		editor.putString("username", user.getUser_name());
		editor.putString("password", user.getPassword());
		editor.putString("user_real_name", user.getUser_realname());
		editor.putString("organization_id", user.getOrg_id());
		editor.putString("organization_name", user.getOrg_name());
		editor.putString("roles", user.getRoles());
		editor.putString("employee_id", user.getEmployee_id());
		editor.putString("mobile_number", user.getMobile_number());
		editor.commit();
	}

	public void onDestroy(){
		super.onDestroy();
		this.userPresenter.destroy();
	}
}
