package com.bangware.shengyibao.activity.Suggest.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.Suggest.presenter.SuggestPresenter;
import com.bangware.shengyibao.activity.Suggest.presenter.impl.SuggestPresenterImpl;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

/**
 * 意见反馈
 */
public class SuggestActivity extends BaseActivity implements SuggestView{
    private ImageButton mSuggestTitleBtnLeft;
    private Button btn_submit;
    private EditText suggest_edit;
    private SuggestPresenter suggestPresenter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findView();
        setListener();
    }

    public void findView(){
        mSuggestTitleBtnLeft= (ImageButton) findViewById(R.id.suggestTitleBtnLeft);
        btn_submit = (Button) findViewById(R.id.suggestion_button);
        suggest_edit = (EditText) findViewById(R.id.suggesttion_edit);

        suggestPresenter = new SuggestPresenterImpl(this);
    }

    public void setListener(){
        MyOnClickListener listener = new MyOnClickListener();
        mSuggestTitleBtnLeft.setOnClickListener(listener);
        btn_submit.setOnClickListener(listener);
    }

    @Override
    public void onLoadSuccess(String successMessage) {
        showMessage(successMessage);
        Intent intent = new Intent(SuggestActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onLoadError(String ErrorMessage) {
       showMessage(ErrorMessage);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.suggestTitleBtnLeft){
                finish();
            }
            if (v.getId() == R.id.suggestion_button){
                String contentStr = suggest_edit.getText().toString();
                if (!contentStr.isEmpty()&&!"".equals(contentStr)){
                    suggestPresenter.loadData(user,contentStr);
                }else{
                    showToast("请输入你要反馈的内容！");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.suggestPresenter.destory();
    }
}
