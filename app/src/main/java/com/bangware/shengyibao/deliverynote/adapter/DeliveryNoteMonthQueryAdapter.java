package com.bangware.shengyibao.deliverynote.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;

/**
 * 销售记录适配器
 * @author zcb
 *
 */
public class DeliveryNoteMonthQueryAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;// 容器，找到数据所在位置
	
	List<DeliveryNoteMonthQuery> querylist;
	
	public DeliveryNoteMonthQueryAdapter(Context context,List<DeliveryNoteMonthQuery> querylist) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.querylist = querylist;
	}
	
	public void refreshData(List<DeliveryNoteMonthQuery> querylist) {
		this.querylist = querylist;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return querylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			mInflater = LayoutInflater.from(parent.getContext());
			convertView = mInflater.inflate(R.layout.item_query_deliverynote, null);
		}
		TextView shop_name = ViewHolder.get(convertView,R.id.shop_name);
		TextView total_money = ViewHolder.get(convertView,R.id.total_money);
		TextView serial_number = ViewHolder.get(convertView,R.id.delivery_serial_number);
		TextView show_time = ViewHolder.get(convertView,R.id.show_time);
		TextView unpaid = ViewHolder.get(convertView, R.id.unPaid);
		TextView delivery_detail=ViewHolder.get(convertView,R.id.deliverynote_detail);

		Customer customer = querylist.get(position).getCustomer();
		String timeStr = querylist.get(position).getOrder_Time();
		String serialNumberStr = querylist.get(position).getOrder_Id();
		String nameStr = customer.getName();
		String moneyStr = querylist.get(position).getOrder_total_money();
		String unpaidStr = querylist.get(position).getOrder_unpaid();
		String product_detail=querylist.get(position).getDeliveryNote_product();

		show_time.setText(timeStr);
		int p = position + 1;
		serial_number.setText(String.valueOf(p));
		shop_name.setText(nameStr);
		delivery_detail.setText(product_detail);
		total_money.setText("总计金额：¥"+moneyStr+"元");
		unpaid.setText("未付：¥"+unpaidStr+"元");
//		convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		return convertView;
	}

}
