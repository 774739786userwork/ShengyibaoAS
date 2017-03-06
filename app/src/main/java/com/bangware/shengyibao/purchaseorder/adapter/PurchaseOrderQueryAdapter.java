package com.bangware.shengyibao.purchaseorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;

import java.util.List;

/**
 * 送货单查询适配器
 * @author ccssll
 *
 */
public class PurchaseOrderQueryAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;// 容器，找到数据所在位置
	List<DeliveryNote> querylist;

	public PurchaseOrderQueryAdapter(Context context, List<DeliveryNote> querylist) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.querylist = querylist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return querylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (querylist.size() == 0 || querylist == null) {
			return 0;
		}
		return querylist.get(position);
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
			convertView = mInflater.inflate(R.layout.item_query_purchaseorder, null);
		}
		TextView shop_name = ViewHolder.get(convertView,R.id.purchaseorder_shop_name);
		TextView total_money = ViewHolder.get(convertView, R.id.purchaseorder_total_money);
		TextView serial_number = ViewHolder.get(convertView,R.id.purchaseorder_serial_number);
		TextView show_time = ViewHolder.get(convertView,R.id.purchaseorder_show_time);
		TextView unpaid = ViewHolder.get(convertView,R.id.purchaseorder_unPaid);
		TextView delivery_detail=ViewHolder.get(convertView,R.id.purchaseorder_detail);
		TextView order_number = ViewHolder.get(convertView,R.id.purchaseorder_condition);


		DeliveryNote deliveryNote = querylist.get(position);
		
		String timeStr = deliveryNote.getDelivery_date();
		String serialNumberStr = deliveryNote.getSerial_number();
		String nameStr = deliveryNote.getCustomer().getName();
		String product_detail=deliveryNote.getDeliveryNote_product();
		double moneyStr = deliveryNote.getReceiveAmount();
		double unpaidStr = deliveryNote.getUnpaidAmount();
		/*if (deliveryNote.getFlag()==3)
		{
			order_number.setText("已配送");
			order_number.setTextColor(context.getResources().getColor(R.color.blue));
		}*/
		show_time.setText(timeStr);
		serial_number.setText(serialNumberStr);
		shop_name.setText(nameStr);
		delivery_detail.setText(product_detail);
		total_money.setText("总计金额：¥"+moneyStr+"元");
		unpaid.setText("未付：¥"+unpaidStr+"元");
		convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		return convertView;
	}

}
