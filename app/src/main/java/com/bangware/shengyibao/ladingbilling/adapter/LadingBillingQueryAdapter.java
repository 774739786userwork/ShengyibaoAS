package com.bangware.shengyibao.ladingbilling.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.ladingbilling.model.entity.LadingbillingQuery;

/**
 * 提货单列表适配器
 * @author ccssll
 *
 */
public class LadingBillingQueryAdapter extends BaseAdapter {
	private Context context;
	private List<LadingbillingQuery> ladingbillingQueries;
	private LayoutInflater inflater;
	
	public LadingBillingQueryAdapter(Context context,List<LadingbillingQuery> ladingbillingQueries) {
		// TODO Auto-generated constructor stub
		super();
		this.context=context;
		this.ladingbillingQueries=ladingbillingQueries;
	}
	
	public void refreshData(List<LadingbillingQuery> ladingbillingQueries) {
		this.ladingbillingQueries = ladingbillingQueries;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ladingbillingQueries.size();
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
		// TODO Auto-generated method stub
		if (convertView == null) {
			inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.item_query_ladingbilling, null);
		}
		
		TextView ladbilling_time = ViewHolder.get(convertView, R.id.ladbilling_time);
		TextView serial_number = ViewHolder.get(convertView, R.id.ladbilling_serial_number);
		TextView product = ViewHolder.get(convertView, R.id.product);
		TextView car_count = ViewHolder.get(convertView, R.id.car_count);
		TextView billing_person = ViewHolder.get(convertView, R.id.billing_person);
		
		String order_ladbillingTime = ladingbillingQueries.get(position).getLadingbilling_date().toString();
		String order_serialNumber = ladingbillingQueries.get(position).getLadingbilling_numer().toString();
		String order_carCount = ladingbillingQueries.get(position).getLoadingcount().toString();
		String order_product = ladingbillingQueries.get(position).getLadingbilling_product().toString();
		String order_billingPerson = ladingbillingQueries.get(position).getLadingbilling_person().toString();
		
			
		ladbilling_time.setText(order_ladbillingTime);
		serial_number.setText(order_serialNumber);
		car_count.setText("车次:第"+order_carCount+"车");
		billing_person.setText("开单人:"+order_billingPerson);
		product.setText(order_product);
		return convertView;
	}

}
