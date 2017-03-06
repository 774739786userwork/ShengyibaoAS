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
 * 送货客户与开单客户、未开单客户头部的listview适配器 
 * @author ccssll
 *
 */
public class LeftMoreAdapter extends BaseAdapter {
	private Context ctx;
	private String[] text;
	private int position = 0;
	private int layout = R.layout.search_more_list;

	public LeftMoreAdapter(Context ctx, String[] text) {
		this.ctx = ctx;
		this.text = text;
	}
	
	public LeftMoreAdapter(Context ctx, String[] text, int layout) {
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
			holder.layout = (LinearLayout) convertView.findViewById(R.id.More_list_lishi);
			holder.text = (TextView) convertView.findViewById(R.id.Search_more_moreitem_txt);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.text.setText(text[arg0]);
		holder.layout.setBackgroundResource(R.drawable.my_list_txt_background);
		holder.text.setTextColor(Color.parseColor("#ff666666"));
		if(arg0 == position){
			holder.layout.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
			holder.text.setTextColor(Color.parseColor("#FFFF8C00"));
		}
		return convertView;
	}
	
	public void setSelectItem(int i) {
		position = i;
	}
	
	private static class ViewHolder{
		private LinearLayout layout;
		private TextView text;
	}

}
