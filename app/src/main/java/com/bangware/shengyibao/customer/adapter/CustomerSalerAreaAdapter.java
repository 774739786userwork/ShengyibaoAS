package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.customer.model.entity.CustomerSalerArea;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;

public class CustomerSalerAreaAdapter extends BaseAdapter {
	private LayoutInflater mInflater;//容器，找到数据所在位置
	private List<CustomerSalerArea> salerArealList = null;
	private Context context;//上下文内置对象
	
	
	public CustomerSalerAreaAdapter(Context context,List<CustomerSalerArea> salerArealList) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.salerArealList=salerArealList;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return salerArealList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(salerArealList.size() ==0 && salerArealList ==null){
			return 0;
		}
		return salerArealList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_spinner_editlist, null);
		}
		TextView name = ViewHolder.get(convertView, R.id.text);
		name.setText(salerArealList.get(position).getName());
		TextView saler_id = ViewHolder.get(convertView, R.id.saler_id);
		saler_id.setText(salerArealList.get(position).getId());
		
		convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		return convertView;
	}
}
