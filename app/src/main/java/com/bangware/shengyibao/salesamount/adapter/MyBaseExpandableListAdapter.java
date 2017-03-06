package com.bangware.shengyibao.salesamount.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.salesamount.model.entity.ChildItem;
import com.bangware.shengyibao.salesamount.model.entity.GroupItem;
import com.bangware.shengyibao.utils.NumberUtils;

import java.util.List;
import java.util.Map;

/**
 * ExpandListView的适配器，继承自BaseExpandableListAdapter
 * Created by bangware on 2016/8/24.
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter{
    private Context mContext;
    private List<GroupItem> groupList;
    //子项是一个map，key是group的id，每一个group对应一个ChildItem的list
    private Map<Integer, List<ChildItem>> childMap;
    private Handler handler;
    public MyBaseExpandableListAdapter(Context context, List<GroupItem> groupList, Map<Integer, List<ChildItem>> childMap) {
        this.mContext = context;
        this.groupList = groupList;
        this.childMap = childMap;

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                notifyDataSetChanged();
                super.handleMessage(msg);
            }
        };
    }

    public void refresh() {
        handler.sendMessage(new Message());
    }
    /**
     * 取得分组数
     */
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /*
	 * 取得指定分组的子元素数
	 */
    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return childMap.get(groupPosition).size();
    }


    /**
     * 取得与给定分组关联的数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //我们这里返回一下每个item的名称，以便单击item时显示
        return childMap.get(groupPosition).get(childPosition).getGroupPerson();
    }

    /**
     * 取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /*
	 * 取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
	 */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // Indicates whether the child and group IDs are stable across changes to the underlying data
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // Whether the child at the specified position is selectable
        return true;
    }

    /**
     * 父控件数据源
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
        }
        TextView group_name = ViewHolder.get(convertView,R.id.tv_group_text);
        ImageView groupImg = ViewHolder.get(convertView,R.id.img_indicator);
        if (isExpanded) {
            groupImg.setBackgroundResource(R.drawable.ic_arrow_up_black);
        }else {
            groupImg.setBackgroundResource(R.drawable.ic_arrow_down_black);
        }

        if (groupList.size() > 0){
            group_name.setText(groupList.get(groupPosition).getTitle());
        }
        return convertView;
    }

    /**
     * 子控件数据源
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child, null);
        }
        TextView group_person_name = ViewHolder.get(convertView,R.id.group_person_tv);
        TextView group_ranking = ViewHolder.get(convertView,R.id.group_ranking_tv);
        TextView group_customer_quantity = ViewHolder.get(convertView,R.id.group_customer_quantity_tv);
        TextView group_total_sum = ViewHolder.get(convertView,R.id.group_sale_totalsum_tv);

        group_person_name.setText(childMap.get(groupPosition).get(childPosition).getGroupPerson());
        group_ranking.setText(childMap.get(groupPosition).get(childPosition).getGroupRanking());
        group_customer_quantity.setText(childMap.get(groupPosition).get(childPosition).getGroupQuantity()+"家");
        group_total_sum.setText(String.valueOf("¥"+NumberUtils.toDoubleDecimal(childMap.get(groupPosition).get(childPosition).getGroupTotalSum())));

        return convertView;
    }
}
