package com.bangware.shengyibao.deliverynote.adapter;

import java.util.List;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.view.BillingCustomersActivity;
import com.bumptech.glide.Glide;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 客户列表适配器
 * 
 * @author ccssll implements SectionIndexer
 */
public class BillingCustomerAdapter extends BaseAdapter {
	private BillingCustomersActivity context;// 上下文内置对象
	private LayoutInflater mInflater;// 容器，找到数据所在位置
	private List<Customer> customerList = null;
	// 上下文对象
	public BillingCustomerAdapter(BillingCustomersActivity context,
			List<Customer> customerList) {
		this.customerList = customerList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public void refreshData(List<Customer> customerList) {
		this.customerList = customerList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// 适配器中所代表的数据集中的条目数
		return customerList.size();
	}

	@Override
	public Object getItem(int position) {
		// 获取数据集中与指定索引对应的数据项
		if (customerList.size() == 0 || customerList == null) {
			return 0;
		}
		return customerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// 取在列表中与指定索引对应的行id
		Log.d("TAG", "取在列表中与指定索引对应的行id==>" + position);
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 此方法是对listview的优化，节省资源，提升性能
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_customer, null);
		}
		final ImageView user_img = ViewHolder.get(convertView,R.id.CustomerItemImage);
		TextView name_tv = ViewHolder.get(convertView,R.id.CustomerItemTextView);
		TextView username_tv = ViewHolder.get(convertView, R.id.username_textview);
		TextView mobile_tv = ViewHolder.get(convertView, R.id.CustomerItemPhone);
		TextView nearby_tv = ViewHolder.get(convertView,R.id.nearby_textview);
		TextView address_tv = ViewHolder.get(convertView,R.id.CustomerItemAddress);
		nearby_tv.setText("");
		name_tv.setText(customerList.get(position).getName().toString().trim());
		if (customerList.get(position).getContacts().size() > 0) {
			username_tv.setText(customerList.get(position).getContacts().get(0).getName().toString().trim());
			mobile_tv.setText(customerList.get(position).getContacts().get(0).getMobile1().toString().trim());
		}
		address_tv.setText(customerList.get(position).getAddress().toString().trim());
		
		user_img.setImageResource(R.drawable.no_pic_300);
		if(customerList.get(position).getImages().size() > 0){
			String img_url = customerList.get(position).getImages().get(0).getImg_url();
			if (img_url != null && !"".equals(img_url)) {
				img_url = Model.HTTPURL+img_url;
				Glide.with(context).load(img_url).placeholder(R.drawable.no_pic_300).error(R.drawable.no_pic_300)
				.into(user_img);
			} else {
				Log.d("TAG", "无图片");
			}
		}
		convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		return convertView;
	}
}
