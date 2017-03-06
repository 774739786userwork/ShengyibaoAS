package com.bangware.shengyibao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bangware.shengyibao.user.view.UpdatePasswordActivity;

public class SettingActivity extends BaseActivity {

    private ImageButton mSettingTitleBtnLeft;
    private TextView mUpdate_password_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSettingTitleBtnLeft= (ImageButton) findViewById(R.id.settingTitleBtnLeft);
        mUpdate_password_setting= (TextView) findViewById(R.id.update_password_setting);
        mSettingTitleBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mUpdate_password_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
