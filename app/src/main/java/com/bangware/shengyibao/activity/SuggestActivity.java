package com.bangware.shengyibao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bangware.shengyibao.user.model.entity.User;

public class SuggestActivity extends BaseActivity {
    private ImageButton mSuggestTitleBtnLeft;
    private Button btn_submit;
    private EditText suggest_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        findView();
        setListener();
    }

    public void findView(){
        mSuggestTitleBtnLeft= (ImageButton) findViewById(R.id.suggestTitleBtnLeft);
        btn_submit = (Button) findViewById(R.id.suggestion_button);
        suggest_edit = (EditText) findViewById(R.id.suggesttion_edit);
    }

    public void setListener(){
        MyOnClickListener listener = new MyOnClickListener();
        mSuggestTitleBtnLeft.setOnClickListener(listener);
        btn_submit.setOnClickListener(listener);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.suggestTitleBtnLeft){
                finish();
            }
            if (v.getId() == R.id.suggestion_button){

            }
        }
    }

    public void submitSuggestion(String content,User user){

    }
}
