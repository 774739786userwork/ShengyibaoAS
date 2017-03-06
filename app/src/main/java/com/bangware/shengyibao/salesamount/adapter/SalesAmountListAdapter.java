package com.bangware.shengyibao.salesamount.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;

import java.util.List;

/**
 * 销售金额适配器
 * @author zcb
 *
 */
public class SalesAmountListAdapter extends BaseAdapter {
	private List<MonthProductAmount> MonthProductAmount;
	private Context context;
//	private BadgeView deliveryNoteStock;
	private class Holder{
	        public TextView mName;//产品名字
	        public TextView mNumber;//销售总数
	        public TextView mSumamount;//销售总金额
	        public TextView mSalesamount_giver;//赠送量
	}
	public SalesAmountListAdapter(List<MonthProductAmount> list, Context context){
		this.MonthProductAmount = list;
		this.context = context;
	}
	@Override
	public int getCount() {
		return MonthProductAmount.size();
	}

	@Override
	public Object getItem(int arg0) {
		return MonthProductAmount.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		final Holder hold;
		if (view == null) {
			hold = new Holder();
			view = View.inflate(context, R.layout.salseamount_products_item, null);
			hold.mName = (TextView) view.findViewById(R.id.salesamount_product_name);
			hold.mNumber = (TextView)view.findViewById(R.id.salesamount_product_number);
			hold.mSumamount=(TextView) view.findViewById(R.id.salesamount_sumamount);
			hold.mSalesamount_giver=(TextView) view.findViewById(R.id.salesamount_giver);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		
		MonthProductAmount mAmount = MonthProductAmount.get(position);
		hold.mName.setText(mAmount.getSalesAomuntName());
		hold.mNumber.setText(mAmount.getSalesAomuntNumber());
		hold.mSalesamount_giver.setText(mAmount.getSalesAomuntGiver());
		hold.mSumamount.setText(mAmount.getSalesAomuntSum()+"元");
		return view;
	}
	
}
