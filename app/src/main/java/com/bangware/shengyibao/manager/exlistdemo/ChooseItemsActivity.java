package com.bangware.shengyibao.manager.exlistdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.manager.exlistdemo.adapter.ChooseAdapter;

import java.util.ArrayList;

public class ChooseItemsActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private int type; //0选择文字  1选择图片
    private String tags;
    private ChooseAdapter adapter;
    private ListView mistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_choose_items);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.exlistdemo_title);

        mistView = (ListView)findViewById(R.id.choose_listview);

        TextView  titleTextView = (TextView)findViewById(R.id.title_text);
        titleTextView.setText("选择");
        Button btn = (Button)findViewById(R.id.title_right_btn);
        btn.setText("确定选择");
        btn.setOnClickListener(this);
        Button back = (Button)findViewById(R.id.title_left_btn);
        back.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        type =bundle.getInt("type");
        tags = bundle.getString("tags");
        init();
        Toast t = Toast.makeText(this,"index="+type,  Toast.LENGTH_SHORT);
        t.show();
    }

    private void init()
    {
        String[] names = new String[]
                {
                        "1111111111.mp3","222222222.avi","333333333.mp4","4444444444.avi","55555555555.avd","666666666.mp4","77777777.mp4","88888888.mp4"
                };
        int []ids = new int[]
                {
                        R.drawable.contact_default,R.drawable.contact_default,R.drawable.contact_default,R.drawable.contact_default,
                        R.drawable.contact_default,R.drawable.contact_default,R.drawable.contact_default,R.drawable.contact_default
                };
        adapter = new ChooseAdapter(this, type, names, ids, this);
        mistView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.title_left_btn)
        {
            this.finish();
        }else
        {
            if(adapter != null)
            {
                ArrayList<String> tnames = new ArrayList<String>();
                ArrayList<String> ids = new ArrayList<String>();
                for(int i = 0; i < adapter.mark.length; i++)
                {
                    if(adapter.mark[i])
                    {
                        if(type == 2)
                        {
                            ids.add(String.valueOf(adapter.picids[i]));
                        }else
                        {
                            tnames.add(adapter.names[i]);
                        }
                    }
                }

                //need set result
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tags", tags);
                bundle.putStringArrayList("names", tnames);
                bundle.putStringArrayList("pics", ids);
                bundle.putInt("type", type);
                it.putExtras(bundle);
                this.setResult(20, it);
            }
            this.finish();
        }

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        int index = Integer.parseInt(buttonView.getTag().toString());
        adapter.mark[index] = isChecked;
    }
}
