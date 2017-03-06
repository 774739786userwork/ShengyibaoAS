package com.bangware.shengyibao.manager.exlistdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.manager.exlistdemo.data.ChildData;
import com.bangware.shengyibao.manager.exlistdemo.data.GroupData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangware on 2016/9/7.
 */
public class ExListViewAdapter extends BaseExpandableListAdapter {

    public List<ArrayList<ChildData>> childDatas;
    public List<GroupData> grouDatas;
    private GroupData gdata;
    private ChildData cdata;
    private View tempView;
    private LayoutInflater mInflater = null;
    private View.OnClickListener onclickListener;
    private ChildViewHolder childHolder;
    private GroupViewHolder groupHolder;
    public ExListViewAdapter (Context ct, List<GroupData> gdatas, List<ArrayList<ChildData>> cdatas, View.OnClickListener onclick)
    {
        mInflater = LayoutInflater.from(ct);
        grouDatas = gdatas;
        childDatas = cdatas;
        onclickListener = onclick;
    }
    public void addGroup(GroupData gdata)
    {
    }
    public void delGroup(int groupIndex)
    {

    }
    public void addChild(int groupIndex,ChildData cdata)
    {
        if(childDatas.get(groupIndex) == null)
        {
            ArrayList<ChildData> tempDatas = new ArrayList<ChildData>();
            childDatas.set(groupIndex, tempDatas);
        }
        childDatas.get(groupIndex).add(cdata);

    }
    public void delChild(int groupIndex,int childIndex)
    {
        if(childDatas != null  && childDatas.get(groupIndex).size() > childIndex)
        {

        }
    }
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return (grouDatas!= null) ? grouDatas.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(childDatas!= null)
        {
            if(childDatas.size() > groupPosition && childDatas.get(groupPosition) != null)
            {
                childDatas.get(groupPosition).size();
            }else
            {
                return 0;
            }
        }else
        {
            return 0;
        }
        // TODO Auto-generated method stub
        return (childDatas!= null) ? childDatas.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return (grouDatas!= null) ? grouDatas.get(groupPosition): null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView != null)
        {
            tempView = convertView;
        }else
        {
            tempView = mInflater.inflate(R.layout.ex_list_group_item, parent,false);
        }
        groupHolder = (GroupViewHolder)tempView.getTag();
        if(groupHolder == null)
        {
            groupHolder = new GroupViewHolder();
            groupHolder.nameTextView = (TextView)tempView.findViewById(R.id.group_name);
            groupHolder.rightBtn = (Button)tempView.findViewById(R.id.group_rightbtn);

            groupHolder.rightBtn.setOnClickListener(onclickListener);
            tempView.setTag(groupHolder);
        }
        groupHolder.rightBtn.setTag(String.valueOf(groupPosition));
        gdata = grouDatas.get(groupPosition);
        groupHolder.nameTextView.setText(gdata.name);
        return tempView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView != null)
        {
            tempView = convertView;
        }else
        {
            tempView = mInflater.inflate(R.layout.ex_list_child_item, parent,false);
        }
        childHolder = (ChildViewHolder)tempView.getTag();
        if(childHolder == null)
        {
            childHolder = new ChildViewHolder();
            childHolder.nameTextView = (TextView)tempView.findViewById(R.id.child_name);
            childHolder.imageView = (ImageView)tempView.findViewById(R.id.child_image);
            childHolder.delBtn = (Button)tempView.findViewById(R.id.child_rightbtn);
            childHolder.delBtn.setOnClickListener(onclickListener);
            tempView.setTag(childHolder);
        }
        cdata = childDatas.get(groupPosition).get(childPosition);
        childHolder.delBtn.setTag(groupPosition+","+childPosition);
        if(cdata.type == 1)//只显示文字
        {
            childHolder.imageView.setVisibility(View.GONE);
            childHolder.nameTextView.setVisibility(View.VISIBLE);
            childHolder.nameTextView.setText(cdata.name);
        }else
        {
            childHolder.nameTextView.setVisibility(View.GONE);
            childHolder.imageView.setVisibility(View.VISIBLE);
            childHolder.imageView.setImageResource(cdata.picid);
        }

        return tempView;
    }
    public class GroupViewHolder
    {
        private TextView nameTextView;
        private Button rightBtn;
    }
    public class ChildViewHolder
    {
        private TextView nameTextView;
        private ImageView imageView;
        private Button delBtn;
    }
}
