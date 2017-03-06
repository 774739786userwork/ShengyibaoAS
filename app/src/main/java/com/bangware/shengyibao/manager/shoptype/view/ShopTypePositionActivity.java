package com.bangware.shengyibao.manager.shoptype.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.manager.shoptype.helper.ChildData;
import com.bangware.shengyibao.manager.shoptype.helper.DataHolder;
import com.bangware.shengyibao.manager.shoptype.helper.GroupData;
import com.bangware.shengyibao.manager.shoptype.helper.ViewHolder;
import com.bangware.shengyibao.manager.shoptype.presenter.ShopTypePresenter;
import com.bangware.shengyibao.manager.shoptype.presenter.impl.ShopTypePresenterImpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopTypePositionActivity extends BaseActivity implements
        GroupView.OnGroupClickListener,
        ChildView.OnChildClickListener,ShopTypeView{

    private ExpandableListView listView;
    private ImageView shop_typeimg;
    private TextView sure_textview;
    private DataHolder dataHolder = new DataHolder();
    private ViewHolder viewholder = new ViewHolder();

    private ShopTypePresenter shopTypePresenter;
    private List<GroupData> contentData = new ArrayList<GroupData>();
    private Map<Integer, List<ChildData>> childDataList = new HashMap<Integer, List<ChildData>>();//child的数据源
    private ExpandableListAdapter listAdapter;

    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_type_position);

        findView();
    }

    private void findView(){
        listView = (ExpandableListView)findViewById(R.id.listview_group_list);

        shopTypePresenter = new ShopTypePresenterImpl(this);
        shopTypePresenter.loadShopTypeData();
        //加载数据
        dataHolder.setContentData(contentData);
        listAdapter = new ExpandableListAdapter(this);
        listView.setAdapter(listAdapter);
        //去掉系统默认的箭头图标
        listView.setGroupIndicator(null);
        //点击Group不收缩
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        //返回键
        shop_typeimg = (ImageView) findViewById(R.id.shop_typeimg);
        shop_typeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //确定按钮
        sure_textview = (TextView) findViewById(R.id.surebtn_textview);
        sure_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*for (int i=0;i<contentData.size();i++){
                    GroupData groupList = contentData.get(i);
                    for (int j=0;j<groupList.getItems().size();j++){
                        String id = groupList.getItems().get(j).getChildId();
                        String name = groupList.getItems().get(j).getChildName();

                        System.out.println("state.get("+id+")=="+"----------------->"+name);
                        showToast("点击的是:"+id);
                    }
                }*/
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                return true;
            }
        });

    }

    @Override
    public void onChildChecked(int groupPosition, int childPosition) {
        dataHolder.setChildChecked(groupPosition, childPosition);
//        viewholder.setChildChecked(groupPosition, childPosition);
    }

    @Override
    public void onChildUnChecked(int groupPosition, int childPosition) {
        dataHolder.setChildUnChecked(groupPosition, childPosition);
//        viewholder.setChildUnChecked(groupPosition, childPosition);
    }

    @Override
    public void onGroupChecked(int groupPosition) {
        dataHolder.setGroupChecked(groupPosition);
        viewholder.setGroupChecked(groupPosition);
    }

    @Override
    public void onGroupUnChecked(int groupPosition) {
        dataHolder.setGroupUnChecked(groupPosition);
        viewholder.setGroupUnChecked(groupPosition);
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity activity;

        public ExpandableListAdapter(Activity activity) {
            this.activity = activity;
        }
        @Override
        public Object getGroup(int groupPosition) {
            return dataHolder.getGroupData(groupPosition);
        }
        @Override
        public int getGroupCount() {
            return dataHolder.getGroupCount();
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return dataHolder.getChildData(groupPosition, childPosition);
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return dataHolder.getChildCount(groupPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            final GroupView groupView = new GroupView((ShopTypePositionActivity)activity, getBaseContext());
            groupView.setGroupPosition(groupPosition);

            GroupData groupData = (GroupData)getGroup(groupPosition);
            groupView.getGroupName().setText(groupData.getGroupName());
            if (isExpanded) {
                groupView.getArrowImage().setBackgroundResource(R.drawable.ic_arrow_up_black);
            }else {
                groupView.getArrowImage().setBackgroundResource(R.drawable.ic_arrow_down_black);
            }
            viewholder.setGroupView(groupPosition, groupView);
            return groupView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            ImageHolder holder;
            if(convertView == null){
                holder = new ImageHolder();
                convertView = new ChildView((ShopTypePositionActivity)activity,getBaseContext());
                holder.childView = (ChildView)convertView;
                convertView.setTag(holder);
            }else{
                holder = (ImageHolder)convertView.getTag();
            }

            ChildView childView = holder.childView;
            childView.setGroupPosition(groupPosition);
            childView.setChildPosition(childPosition);

            ChildData childData = (ChildData)getChild(groupPosition, childPosition);
            childView.getSelectChild().setChecked(childData.isChildSelected());

            childView.getChildId().setText(childData.getChildId());
            childView.getChildName().setText(childData.getChildName());

            viewholder.setChildView(groupPosition, childPosition, childView);
            childView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(dataHolder.isChildSelected(groupPosition, childPosition)){
                        ((ShopTypePositionActivity)activity).onChildUnChecked(groupPosition, childPosition);
                    }else{
                        ((ShopTypePositionActivity)activity).onChildChecked(groupPosition, childPosition);
                    }
                }
            });

            return childView;
        }
        class ImageHolder{
            ChildView childView;
        }
    }

    /*加载数据*/
    @Override
    public void doShopTypeLoadSuccess(List<GroupData> groupDataList) {
        int childtempCount = groupDataList.size();
        if (childtempCount>0){
            contentData.addAll(groupDataList);
            listAdapter.notifyDataSetChanged();
            for (int i=0;i<groupDataList.size();i++){
                listView.collapseGroup(i);
                List<ChildData> childDatas = new ArrayList<ChildData>();
                GroupData groupList = groupDataList.get(i);
                for (int j=0;j<groupList.getItems().size();j++){
                    ChildData child_data = new ChildData();
                    child_data.setChildId(groupList.getItems().get(j).getChildId());
                    child_data.setChildName(groupList.getItems().get(j).getChildName());
                    childDatas.add(child_data);
                    childDataList.put(i,childDatas);
                }
                listView.expandGroup(i);//默认展开列表
            }
        }else{
            showToast("无数据");
            listAdapter.notifyDataSetChanged();
        }
    }

}
