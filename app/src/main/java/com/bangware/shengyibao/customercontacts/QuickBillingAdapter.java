package com.bangware.shengyibao.customercontacts;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;

/**
 * 选择客户联系人
 * @author zcb
 *
 */
public class QuickBillingAdapter extends BaseAdapter{
	private Context context;// 上下文内置对象
	private LayoutInflater mInflater;// 容器，找到数据所在位置
	private List<Contacts> contactsList = null;
	
	public QuickBillingAdapter(Context context,
			 List<Contacts> contactsList) {
		super();
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.contactsList = contactsList;
	}
	public void refreshData(List<Contacts> contactsList) {
		this.contactsList = contactsList;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		// 获取数据集中与指定索引对应的数据项
		if (contactsList.size() == 0 || contactsList == null) {
			return 0;
		}
		return contactsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_quickbilling, null);
		}
		TextView con_name = ViewHolder.get(convertView, R.id.Contact_name);
		TextView con_phone = ViewHolder.get(convertView,R.id.Contact_Phone);
		TextView con_shopname = ViewHolder.get(convertView, R.id.Contact_Shopname);
		TextView con_address=ViewHolder.get(convertView, R.id.Contact_Address);
		con_shopname.setText(contactsList.get(position).getCustomer().getName());
		con_address.setText(contactsList.get(position).getCustomer().getAddress());
				
		con_name.setText(contactsList.get(position).getName());
		String mobile1 = contactsList.get(position).getMobile1();
		if(mobile1.equals("")){
			con_phone.setText(contactsList.get(position).getMobile2());
		}else{
			con_phone.setText(mobile1);
		}
		convertView.setBackgroundResource(R.drawable.my_tab_background);
		return convertView;
	}
}
