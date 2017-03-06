package com.bangware.shengyibao.returngood.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;

/**
 * 退货查询数据源
 * @author ccssll
 *
 */
public class ReturnGoodAdapter extends BaseAdapter {
	private Context context;
	private List<ReturnNote> list;
	private LayoutInflater mInflater;
	
	public ReturnGoodAdapter(Context context,List<ReturnNote> list) {
		// TODO Auto-generated constructor stub
		super();
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
			mInflater = LayoutInflater.from(parent.getContext());
			convertView = mInflater.inflate(R.layout.item_query_returngood, null);
		}
		TextView time_text = ViewHolder.get(convertView, R.id.return_good_time);
		TextView number_text = ViewHolder.get(convertView, R.id.return_good_number);
		TextView shopName_text = ViewHolder.get(convertView, R.id.shop_name);
		TextView returnPerson_text = ViewHolder.get(convertView, R.id.return_good_person);
//		TextView carNumber_text = ViewHolder.get(convertView, R.id.return_good_carnumber);
		TextView reason_text = ViewHolder.get(convertView, R.id.return_good_reason);
		TextView totalSum_text = ViewHolder.get(convertView, R.id.return_good_totalSum);
		
		String timeTextStr = list.get(position).getReturn_date().toString();
		String numberTextStr = list.get(position).getSerial_number();
		String shopNameStr = list.get(position).getCustomer().getName().toString();
		String personStr = list.get(position).getSalerName().toString();
//		String carNumberStr = list.get(position).getCar_number().toString();
		String reasonStr = list.get(position).getReturn_reson().toString();
		String totalSumStr = list.get(position).getTotal_sum().toString();
		
		time_text.setText(timeTextStr);
//		int number_postion = position+1;
		number_text.setText(numberTextStr);
		shopName_text.setText(shopNameStr);
		returnPerson_text.setText("退货人："+personStr);
//		carNumber_text.setText("车牌号："+carNumberStr);
		totalSum_text.setText("金额：¥"+totalSumStr+"元");
		reason_text.setText("退货原因："+reasonStr);
		convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		return convertView;
	}

}
