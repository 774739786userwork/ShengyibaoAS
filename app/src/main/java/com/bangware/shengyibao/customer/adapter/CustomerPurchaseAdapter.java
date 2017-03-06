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
 * 客户信息页进货记录
 * @author ccssll
 *
 */
public class CustomerPurchaseAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private List<CustomerPurchase> purchaselist;
	public CustomerPurchaseAdapter(Context context,List<CustomerPurchase> purchaselist) {
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
			convertView = mInflater.inflate(R.layout.item_purchase_records, null);
		}
		TextView purchase_time = ViewHolder.get(convertView, R.id.purchase_time);
		TextView deliveryNotePerson = ViewHolder.get(convertView, R.id.deliveryNotePerson_textview);
		TextView order_number = ViewHolder.get(convertView, R.id.order_number);
		/*TextView purchase_money = ViewHolder.get(convertView, R.id.purchase_money);
		TextView unpaid_money = ViewHolder.get(convertView, R.id.unpaid_money);*/
		TextView prodcut_edit_text=ViewHolder.get(convertView,R.id.prodcut_edit_text);
		
		String timeStr = purchaselist.get(position).getDelivery_date();
		String personStr = purchaselist.get(position).getEmployee_name();
		String numberStr = purchaselist.get(position).getSerial_number();
		/*double moneyStr = purchaselist.get(position).getTotal_sum();
		double unpaidStr = purchaselist.get(position).getUnpaid_total_sum();*/
		String prodcutStr=purchaselist.get(position).getProduct_edit();

		prodcut_edit_text.setText(prodcutStr);
		purchase_time.setText(timeStr);
		deliveryNotePerson.setText(personStr);
		order_number.setText(numberStr);
		/*purchase_money.setText("进货金额：¥"+moneyStr+"元");
		unpaid_money.setText("未付：¥"+unpaidStr+"元");*/
		return convertView;
	}

}
