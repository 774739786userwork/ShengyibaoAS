package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bumptech.glide.Glide;

/**
 * 主页月开单客户数据源adapter
 * @author ccssll
 *
 */
public class MonthCustomerBillingAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<Customer> mainBillingCustomerList = null;
	
	public MonthCustomerBillingAdapter(Context context,List<Customer> mainBillingCustomerList) {
		// TODO Auto-generated constructor stub
		this.mainBillingCustomerList = mainBillingCustomerList;
		this.context=context;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mainBillingCustomerList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mainBillingCustomerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_mainmonth_billing_customer, null);
		}
		ImageView customer_image = ViewHolder.get(convertView, R.id.mainCustomerBillingItemImage);
		TextView customer_name = ViewHolder.get(convertView, R.id.mainCustomerBillingNameTextView);
		TextView contact_name = ViewHolder.get(convertView, R.id.mainCustomerContactName_textview);
		TextView contact_telephone = ViewHolder.get(convertView, R.id.mainCustomerBillingItemPhone);
		TextView lastPurchase_time = ViewHolder.get(convertView, R.id.mainCustomerBillingTimeTextView);
		TextView delivery_goods_count = ViewHolder.get(convertView,R.id.CustomerBillingCountTextView);
//		TextView purchase_total_sum = ViewHolder.get(convertView,R.id.customer_purchase_totalSumTv);
		TextView customer_address = ViewHolder.get(convertView, R.id.mainCustomerBillingItemAddress);
		
		customer_name.setText(mainBillingCustomerList.get(position).getName().toString());
		if(mainBillingCustomerList.get(position).getContacts().size() > 0){
			contact_name.setText(mainBillingCustomerList.get(position).getContacts().get(0).getName().trim());
			contact_telephone.setText(mainBillingCustomerList.get(position).getContacts().get(0).getMobile1().trim());
		}
		delivery_goods_count.setText("送货次数"+mainBillingCustomerList.get(position).getDeliver_good_count()+"次");

//		purchase_total_sum.setText("金额：¥"+mainBillingCustomerList.get(position).getPurchase_total_sum());

		lastPurchase_time.setText(mainBillingCustomerList.get(position).getLasttime());
		if(!lastPurchase_time.getText().toString().isEmpty() && !lastPurchase_time.getText().toString().equals("null")){
			lastPurchase_time.setText("上次进货日期："+mainBillingCustomerList.get(position).getLasttime());
		}else{
			lastPurchase_time.setText("暂无进货");
		}
		customer_address.setText(mainBillingCustomerList.get(position).getAddress().toString());
		
		customer_image.setImageResource(R.drawable.no_pic_300);
		if(mainBillingCustomerList.get(position).getImages().size() > 0){
			String customer_img_url = mainBillingCustomerList.get(position).getImages().get(0).getImg_url();
			if(customer_img_url != null && !"".equals(customer_img_url)){
				customer_img_url = Model.HTTPURL + customer_img_url;
				Glide.with(context).load(customer_img_url).placeholder(R.drawable.no_pic_300).error(R.drawable.no_pic_300)
				.into(customer_image);
			}else{
				Log.d("TAG", "1111111"+customer_img_url);
			}
		}
		convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		return convertView;
	}
}
