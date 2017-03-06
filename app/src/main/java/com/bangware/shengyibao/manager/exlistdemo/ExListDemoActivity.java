package com.bangware.shengyibao.manager.exlistdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.manager.exlistdemo.adapter.ExListViewAdapter;
import com.bangware.shengyibao.manager.exlistdemo.data.ChildData;
import com.bangware.shengyibao.manager.exlistdemo.data.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ExListDemoActivity extends BaseActivity implements View.OnClickListener {
    private ExpandableListView mListView;
    ExListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_ex_list_demo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.exlistdemo_title);
        TextView  titleTextView = (TextView)findViewById(R.id.title_text);
        titleTextView.setText("测试");
        Button btn = (Button)findViewById(R.id.title_right_btn);
        btn.setVisibility(View.GONE);
        Button back = (Button)findViewById(R.id.title_left_btn);
        back.setVisibility(View.GONE);

        mListView = (ExpandableListView)findViewById(R.id.ex_listview);
        init();
    }

    private void init()
    {
        List<GroupData> tempGroup = new ArrayList<GroupData>();
        tempGroup.add(new GroupData("1","分组1",1));
        tempGroup.add(new GroupData("2","分组2",1));
        tempGroup.add(new GroupData("3","分组3",2));
        tempGroup.add(new GroupData("4","分组4",2));
        tempGroup.add(new GroupData("5","分组5",1));
        tempGroup.add(new GroupData("6","分组6",1));

        List<ArrayList<ChildData>> cDatas = new ArrayList<ArrayList<ChildData>>();
        for(int i = 0;i < tempGroup.size(); i ++)
        {
            cDatas.add(null);
        }
        adapter = new ExListViewAdapter(this, tempGroup, cDatas, this);
        mListView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        String tags = v.getTag().toString();
//        int index = 0;
        Intent it = new Intent(this,ChooseItemsActivity.class);
        Bundle bundle = new Bundle();
        if(v.getId() == R.id.group_rightbtn)
        {
            GroupData gdata = adapter.grouDatas.get(Integer.parseInt(tags));
            bundle.putInt("type", gdata.type);
            bundle.putString("tags", tags);
            it.putExtras(bundle);
            startActivityForResult(it, 20);
        }else
        {
            String[] temptag = v.getTag().toString().split(",");
            int gIndex =Integer.parseInt(temptag[0]);
            int cIndex = Integer.parseInt(temptag[1]);
            adapter.childDatas.get(gIndex).remove(cIndex);
            adapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 20)
        {
            if(data != null)
            {
                Bundle bundle = data.getExtras();
                String tags = data.getStringExtra("tags");
                int type = bundle.getInt("type");
                ArrayList<String> tnames = bundle.getStringArrayList("names");
                ArrayList<String> ids = bundle.getStringArrayList("pics");
                if(type == 1)//选择文字
                {
                    int index = Integer.parseInt(tags);

                    ChildData item = null;
                    for(int i = 0; i < tnames.size(); i ++)
                    {
                        item = new ChildData();
                        item.name = tnames.get(i);
                        item.type = 1;
                        adapter.addChild(index, item);
                    }
                    adapter.notifyDataSetChanged();
                    mListView.expandGroup(index);
                }else
                {
                    int index = Integer.parseInt(tags);
                    ChildData item = null;
                    for(int i = 0; i < ids.size(); i ++)
                    {
                        item = new ChildData();
                        item.picid = Integer.parseInt(ids.get(i));
                        item.type = 2;
                        adapter.addChild(index, item);
                    }
                    adapter.notifyDataSetChanged();
                    mListView.expandGroup(index);
                }

//				 Toast t = Toast.makeText(this, "tnames ="+tnames.size()+",pics "+ids.size()+",index="+type,  Toast.LENGTH_SHORT);
//				  t.show();
            }
        }
    }
}
