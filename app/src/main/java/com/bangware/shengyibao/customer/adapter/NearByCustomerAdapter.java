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
 * 头部附近客户数据源
 * Created by ccssll on 2016/7/31.
 */
public class NearByCustomerAdapter extends BaseAdapter{
    private Context ctx;
    private String[] text;
    private int position = 0;
    private int layout = R.layout.item_nearby_more_list;

    public NearByCustomerAdapter(Context ctx,String[] text,int layout) {
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
            holder.nearlayout = (LinearLayout) convertView.findViewById(R.id.near_list_lishi);
            holder.nearText = (TextView) convertView.findViewById(R.id.near_more_moreitem_txt);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nearText.setText(text[arg0]);
        holder.nearlayout.setBackgroundResource(R.drawable.my_list_txt_background);
        holder.nearText.setTextColor(Color.parseColor("#ff666666"));
        if(arg0 == position){
            holder.nearlayout.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
            holder.nearText.setTextColor(Color.parseColor("#FFFF8C00"));
        }
        return convertView;
    }

    public void setSelectItem(int i) {
        position = i;
    }

    private static class ViewHolder{
        private LinearLayout nearlayout;
        private TextView nearText;
    }
}
