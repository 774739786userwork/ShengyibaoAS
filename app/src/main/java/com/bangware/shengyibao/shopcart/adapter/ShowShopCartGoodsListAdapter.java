package com.bangware.shengyibao.shopcart.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;


public class ShowShopCartGoodsListAdapter extends BaseAdapter {

	private List<ShopCartGoods> mShopCartGoodslist;
	private Context mContext;
	
	private class Holder{
        public TextView mName;
        public TextView mSalesVolume;
        public TextView mGiftVolume;
        public TextView mForegift;
        public TextView mForegiftVolume;
    }
	public ShowShopCartGoodsListAdapter(List<ShopCartGoods> list, Context context){
		this.mShopCartGoodslist = list;
		this.mContext = context;
	}
	@Override
	public int getCount() {
		return mShopCartGoodslist.size();
	}

	@Override
	public Object getItem(int position) {
		return mShopCartGoodslist.get(position);
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
			view = View.inflate(mContext, R.layout.activity_show_shopcartgoods_item, null);
			hold.mName = (TextView) view.findViewById(R.id.Show_GoodsList_Item_Product_Name);
			hold.mSalesVolume = (TextView) view.findViewById(R.id.Show_GoodsList_Item_salesVolume);
			hold.mGiftVolume=(TextView) view.findViewById(R.id.Show_GoodsList_Item_gift);
			hold.mForegift=(TextView) view.findViewById(R.id.Show_GoodsList_Item_foregift);
			hold.mForegiftVolume=(TextView) view.findViewById(R.id.Show_GoodsList_Item_foregift_Sum);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		Product product = mShopCartGoodslist.get(position).getProduct();
		hold.mName.setText(product.getName());
	    hold.mSalesVolume.setText(String.valueOf(mShopCartGoodslist.get(position).getSalesVolume()));
		hold.mGiftVolume.setText(String.valueOf(mShopCartGoodslist.get(position).getGiftsVolume()));
		hold.mForegiftVolume.setText(String.valueOf(mShopCartGoodslist.get(position).getTotalForegift()));
		return view;
	}
	
}