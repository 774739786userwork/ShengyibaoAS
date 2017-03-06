package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;

/**
 * 客户进货查看更多数据适配
 * @author ccssll
 *
 */
public class CustomerPurchaseMoreAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private List<CustomerPurchase> purchaselist;
	public CustomerPurchaseMoreAdapter(Context context,List<CustomerPurchase> purchaselist) {
		super();
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.purchaselist=purchaselist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return purchaselist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return purchaselist.get(position);
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
			convertView = mInflater.inflate(R.layout.item_purchasemore_list, null);
		}
		TextView purchase_time = ViewHolder.get(convertView, R.id.purchaseMore_time);
		TextView deliveryNotePerson = ViewHolder.get(convertView, R.id.deliveryNotePerson_textview);
		TextView order_number = ViewHolder.get(convertView, R.id.order_number);
		/*TextView purchase_money = ViewHolder.get(convertView, R.id.purchaseMore_money);
		TextView unpaid_sum = ViewHolder.get(convertView, R.id.unpaid_sum);*/
		TextView product_more_text=ViewHolder.get(convertView,R.id.product_more_text);
		
		String timeStr = purchaselist.get(position).getDelivery_date();
		String personStr = purchaselist.get(position).getEmployee_name();
		String numberStr = purchaselist.get(position).getSerial_number();
		/*double moneyStr = purchaselist.get(position).getTotal_sum();
		double unpaidStr = purchaselist.get(position).getUnpaid_total_sum();*/
		String productStr=purchaselist.get(position).getProduct_edit();

		purchase_time.setText(timeStr);
		deliveryNotePerson.setText(personStr);
		order_number.setText(numberStr);
		product_more_text.setText(productStr);
		/*purchase_money.setText("进货金额：¥"+moneyStr+"元");
		unpaid_sum.setText("未付：¥"+unpaidStr+"元");*/
		return convertView;
	}

}
