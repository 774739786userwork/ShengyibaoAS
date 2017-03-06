package com.bangware.shengyibao.returngood.adapter;

import java.util.List;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 开单选择客户适配器
 * @author ccssll
 *
 */
public class ChoiceContactAdapter extends BaseAdapter {
	private LayoutInflater mInflater;//容器，找到数据所在位置
	private List<Contacts> contactsList = null;
	private Context context;//上下文内置对象
	public ChoiceContactAdapter(Context context, List<Contacts> contactsList) {
		this.contactsList = contactsList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// 适配器中所代表的数据集中的条目数
		return contactsList.size();
	}

	@Override
	public Object getItem(int position) {
		// 获取数据集中与指定索引对应的数据项
		if(contactsList.size() ==0 && contactsList ==null){
			return 0;
		}
		return contactsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// 取在列表中与指定索引对应的行id
		return position;
	}

	@Override
	public View getView(int position,View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_choice_contact, null);
		}
		TextView con_name = ViewHolder.get(convertView,R.id.returnContact_name);
		TextView con_phone = ViewHolder.get(convertView,R.id.returnContact_Phone);
		TextView con_shopname = ViewHolder.get(convertView, R.id.returnContact_Shopname);
		TextView con_address=ViewHolder.get(convertView, R.id.returnContact_Address);
		con_shopname.setText(contactsList.get(position).getCustomer().getName());
		con_address.setText(contactsList.get(position).getCustomer().getAddress());

		con_name.setText(contactsList.get(position).getName());
		String mobile1 = contactsList.get(position).getMobile1();
		if(mobile1.equals("")){
			con_phone.setText(contactsList.get(position).getMobile2());
		}else{
			con_phone.setText(mobile1);
		}
 		
	 	convertView.setBackgroundResource(R.drawable.my_tab_background);//设置listview点击的背景色 
	    return convertView;
	}
}
