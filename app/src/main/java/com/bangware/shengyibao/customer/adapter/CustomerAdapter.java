package com.bangware.shengyibao.customer.adapter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.view.CustomerActivity;
import com.bangware.shengyibao.utils.NumberUtils;
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
public class CustomerAdapter extends BaseAdapter {
	private CustomerActivity context;// 上下文内置对象
	private LayoutInflater mInflater;// 容器，找到数据所在位置
	private List<Customer> customerList = null;
	// 上下文对象
	public CustomerAdapter(CustomerActivity context,
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
		ImageView user_img = ViewHolder.get(convertView,R.id.CustomerItemImage);
		TextView name_tv = ViewHolder.get(convertView,R.id.CustomerItemTextView);
		TextView username_tv = ViewHolder.get(convertView,R.id.username_textview);
		TextView mobile_tv = ViewHolder.get(convertView, R.id.CustomerItemPhone);
		TextView nearby_tv = ViewHolder.get(convertView,R.id.nearby_textview);
		TextView last_time = ViewHolder.get(convertView,R.id.last_delivery_date);
		TextView deliver_good_count = ViewHolder.get(convertView,R.id.deliver_good_count);
		TextView address_tv = ViewHolder.get(convertView,R.id.CustomerItemAddress);

		name_tv.setText(customerList.get(position).getName().toString().trim());
		if (customerList.get(position).getContacts().size() > 0) {
			username_tv.setText(customerList.get(position).getContacts().get(0).getName().toString().trim());
			mobile_tv.setText(customerList.get(position).getContacts().get(0).getMobile1().toString().trim());
		}

		nearby_tv.setText(customerList.get(position).getNearBy());
		if(!nearby_tv.getText().toString().isEmpty() && !nearby_tv.getText().toString().equals("null")){
			String distance = customerList.get(position).getNearBy().toString();
			String newdistance = NumberUtils.toDouble(distance) > 1000 ? (NumberUtils.toDouble(distance) / 1000) + "km" : distance + "m";
			nearby_tv.setText(newdistance);
		}else{
			nearby_tv.setText("");
		}

		last_time.setText(customerList.get(position).getLasttime());
		if(!last_time.getText().toString().isEmpty() && !last_time.getText().toString().equals("null")){
			last_time.setText("上次进货日期："+customerList.get(position).getLasttime());
		}else{
			last_time.setText("暂无进货");
		}

		deliver_good_count.setText(customerList.get(position).getDeliver_good_count());
		if(!deliver_good_count.getText().toString().isEmpty() && !deliver_good_count.getText().toString().equals("null")){
			deliver_good_count.setText("进货次数"+customerList.get(position).getDeliver_good_count()+"次");
		}else{
			deliver_good_count.setText("0次");
		}

		address_tv.setText(customerList.get(position).getAddress().toString().trim());
		
		user_img.setImageResource(R.drawable.no_pic_300);
		if(customerList.get(position).getImages().size() > 0){
			String img_url = customerList.get(position).getImages().get(0).getImg_url();
			if (img_url != null && !"".equals(img_url)) {
				img_url = Model.HTTPURL+img_url;
				//谷歌推荐的图片加载库  用于显示图片 
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
