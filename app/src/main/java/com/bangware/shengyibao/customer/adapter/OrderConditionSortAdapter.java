package com.bangware.shengyibao.customer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;

/**
 * 头部默认排序数据源
 * Created by ccssll on 2016/8/6.
 */
public class OrderConditionSortAdapter extends BaseAdapter {
    private Context ctx;
    private String[] text;
    private int position = 0;
    private int layout = R.layout.item_sort_more_list;

    public OrderConditionSortAdapter(Context ctx,String[] text,int layout) {
        this.ctx = ctx;
        this.text = text;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(ctx, layout, null);
            holder.sortlayout = (LinearLayout) convertView.findViewById(R.id.sort_list_lishi);
            holder.sortText = (TextView) convertView.findViewById(R.id.sort_more_txt);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sortText.setText(text[arg0]);
        holder.sortlayout.setBackgroundResource(R.drawable.my_list_txt_background);
        holder.sortText.setTextColor(Color.parseColor("#ff666666"));
        if(arg0 == position){
            holder.sortlayout.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
            holder.sortText.setTextColor(Color.parseColor("#FFFF8C00"));
        }
        return convertView;
    }

    public void setSelectItem(int i) {
        position = i;
    }

    private static class ViewHolder{
        private LinearLayout sortlayout;
        private TextView sortText;
    }
}
