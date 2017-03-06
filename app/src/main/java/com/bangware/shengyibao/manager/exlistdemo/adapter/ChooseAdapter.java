package com.bangware.shengyibao.manager.exlistdemo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;

public class ChooseAdapter extends BaseAdapter{

	public String[] names;
	public int[] picids;
	public boolean[] mark;
	public int type;
	private View tempView;
	ViewHolder viewHolder;
	private LayoutInflater mInflater = null;
	private OnCheckedChangeListener onCheckedListerner;
	public ChooseAdapter(Context ct,int type,String[] names,int[] pics,OnCheckedChangeListener onCheck)
	{
		this.type = type;
		this.names = names;
		this.picids = pics;
		this.onCheckedListerner = onCheck;
		mInflater = LayoutInflater.from(ct);
		mark = new boolean[this.names.length];
	}
	
	@Override
	public int getCount() {
		
		// TODO Auto-generated method stub
		return (this.names == null)?0:this.names.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (this.names == null)?0:this.names[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView != null)
		{
			tempView = convertView;
		}else
		{
			tempView = mInflater.inflate(R.layout.list_choose_item, parent,false);
		}
		viewHolder = (ViewHolder)tempView.getTag();
		if(viewHolder == null)
		{
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView)tempView.findViewById(R.id.name_textview);
			viewHolder.checkBox = (CheckBox)tempView.findViewById(R.id.check_box);
			viewHolder.checkBox.setTag(String.valueOf(position));
			viewHolder.checkBox.setOnCheckedChangeListener(onCheckedListerner);
			viewHolder.img = (ImageView)tempView.findViewById(R.id.imageview);
			tempView.setTag(viewHolder);
		}
		viewHolder.checkBox.setChecked(mark[position]);
		if(type == 1)
		{
			viewHolder.nameTextView.setVisibility(View.VISIBLE);
			viewHolder.nameTextView.setText(names[position]);
			viewHolder.img.setVisibility(View.GONE);
		}else
		{
			viewHolder.nameTextView.setVisibility(View.GONE);
			viewHolder.img.setVisibility(View.VISIBLE);
			viewHolder.img.setBackgroundResource(picids[position]);
		}
		return tempView;
	}
	public class ViewHolder
	{
		public CheckBox checkBox;
		public ImageView img;
		public TextView nameTextView;
	}

}
