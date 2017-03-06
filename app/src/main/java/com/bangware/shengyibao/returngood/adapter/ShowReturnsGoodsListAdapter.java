package com.bangware.shengyibao.returngood.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;

import java.util.List;


public class ShowReturnsGoodsListAdapter extends BaseAdapter {

	private List<ReturnNoteGoods> mReturnNoteGoodsList;
	private Context mContext;

	private class Holder{
        public TextView mName;
        public TextView mSalesVolume;
        public TextView mGiftVolume;
        public TextView mForegift;
        public TextView mForegiftVolume;
    }
	public ShowReturnsGoodsListAdapter(List<ReturnNoteGoods> list, Context context){
		this.mReturnNoteGoodsList = list;
		this.mContext = context;
	}
	@Override
	public int getCount() {
		return mReturnNoteGoodsList.size();
	}

	@Override
	public Object getItem(int position) {
		return mReturnNoteGoodsList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		final Holder hold;
		if (view == null) {
			hold = new Holder();
			view = View.inflate(mContext, R.layout.activity_show_returngoods_item, null);
			hold.mName = (TextView) view.findViewById(R.id.Returns_GoodsList_Item_Product_Name);
			hold.mSalesVolume = (TextView) view.findViewById(R.id.Returns_GoodsList_Item_salesVolume);
			hold.mGiftVolume=(TextView) view.findViewById(R.id.Returns_GoodsList_Item_gift);
			hold.mForegift=(TextView) view.findViewById(R.id.Returns_GoodsList_Item_foregift);
			hold.mForegiftVolume=(TextView) view.findViewById(R.id.Returns_GoodsList_Item_foregift_Sum);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		Product product = mReturnNoteGoodsList.get(position).getProduct();
		hold.mName.setText(product.getName());
	    hold.mSalesVolume.setText(String.valueOf(mReturnNoteGoodsList.get(position).getReturnsAmount()));
		hold.mGiftVolume.setText(String.valueOf(mReturnNoteGoodsList.get(position).getGiftsVolume()));
		hold.mForegiftVolume.setText(String.valueOf(mReturnNoteGoodsList.get(position).getTotalForegift()));
		return view;
	}
	
}