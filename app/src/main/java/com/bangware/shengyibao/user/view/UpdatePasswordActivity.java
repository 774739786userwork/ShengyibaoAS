package com.bangware.shengyibao.user.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.presenter.UpdatePasswordPresenter;
import com.bangware.shengyibao.user.presenter.impl.UpdatePasswordPresenterImpl;
import com.bangware.shengyibao.utils.AppContext;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatePasswordActivity extends BaseActivity implements UpdatePasswordView{
    private EditText mOldPassword,mNewPassword,mNewPasswords,mUpdateusername;//原密码，新密码，确认新密码
    private Button mUpdate_password_submit;//确认修改
    private ImageView mUpdate_password_back;//回退按钮
    String oldPwd,newPwd,newPwds,name;//接收输入框的信息
    private UpdatePasswordPresenter presenter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findView();//初始化控件
        setListener();//点击事件的监听

    }

    private void setListener() {
           MyOnClickListener listener=new MyOnClickListener();
          mUpdate_password_submit.setOnClickListener(listener);
          mUpdate_password_back.setOnClickListener(listener);
    }

    private void findView() {
        mOldPassword= (EditText) findViewById(R.id.old_password);
        mNewPassword= (EditText) findViewById(R.id.new_password_one);
        mNewPasswords= (EditText) findViewById(R.id.new_password_two);
        mUpdateusername= (EditText) findViewById(R.id.update_username);
        mUpdate_password_submit= (Button) findViewById(R.id.update_password_submit);
        mUpdate_password_back= (ImageView) findViewById(R.id.update_password_back);
        mUpdateusername.setText(user.getUser_name());
        presenter=new UpdatePasswordPresenterImpl(this);
    }

    @Override
    public void updateSuccess() {
        showToast("修改成功！");
        Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);//设置跳转时A页面的activity处于栈顶
        startActivity(intent);
    }

    @Override
    public void onUpdateError(String ErrorMessage) {

    }

    private class MyOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.update_password_back:
                      UpdatePasswordActivity.this.finish();
                    break;
                case R.id.update_password_submit:
                    oldPwd=mOldPassword.getText().toString();
                    newPwd=mNewPassword.getText().toString();
                    newPwds=mNewPasswords.getText().toString();
                    name=mUpdateusername.getText().toString();
                    Pattern p = Pattern.compile("[0-9a-zA-Z]{6,16}");
                    Matcher m=p.matcher(newPwd);
                    if (!m.matches())
                    {
                        showToast("密码格式不正确");
                        return;
                    }else if(!newPwd.equals(newPwds))
                    {
                        showToast("输入的新密码不一致");
                        return;
                    }else if(oldPwd==null || "".equals(oldPwd))
                    {
                         showToast("请输入原密码");
                        return;
                    }else
                    {
                        presenter.doUpdatePassword(user,oldPwd,newPwd);
                    }

                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.destroy();
    }
}
