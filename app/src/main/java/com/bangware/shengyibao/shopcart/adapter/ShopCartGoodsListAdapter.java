package com.bangware.shengyibao.shopcart.adapter;

import java.math.BigDecimal;
import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.utils.volley.ImageRequester;


public class ShopCartGoodsListAdapter extends BaseAdapter {

	private List<ShopCartGoods> mShopCartGoodslist;
	private Context mContext;
	private ImageRequester mImageRequester;
	
	private class Holder{
        public ImageView mImage;
        public TextView mName;
        public TextView mSalesVolume;
        public TextView mGiftsVolume;
        public TextView mPrice;
        public TextView mSubtotalVolume;
        public TextView msubtotalAmount;
        public TextView mForegift;
        public TextView mForegiftSum;
    }
	public ShopCartGoodsListAdapter(List<ShopCartGoods> list, Context context){
		this.mShopCartGoodslist = list;
		this.mContext = context;
		this.mImageRequester = new ImageRequester(mContext, true);
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
			view = View.inflate(mContext, R.layout.activity_settlement_shopcartgoods_item, null);
			hold.mName = (TextView) view.findViewById(R.id.ShopCart_GoodsList_Item_Product_Name);
			hold.mImage = (ImageView) view.findViewById(R.id.ShopCart_GoodsList_Item_Product_Image);
			hold.mSalesVolume = (TextView) view.findViewById(R.id.ShopCart_GoodsList_Item_SalesVolume);
			hold.mGiftsVolume = (TextView) view.findViewById(R.id.ShopCart_GoodsList_Item_GiftsVolume);
			hold.mPrice = (TextView) view.findViewById(R.id.ShopCart_GoodsList_Item_Price);
			hold.mSubtotalVolume = (TextView) view.findViewById(R.id.ShopCart_GoodsList_Item__SubtotalVolume);
			hold.msubtotalAmount = (TextView)view.findViewById(R.id.ShopCart_GoodsList_Item_SubtotalAmount);
			hold.mForegift=(TextView) view.findViewById(R.id.ShopCart_GoodsList_Item_Product_Foregift);
			hold.mForegiftSum=(TextView) view.findViewById(R.id.ShopCart_GoodsList_Item_Product_Foregift_sum);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		Product product = mShopCartGoodslist.get(position).getProduct();
		hold.mName.setText(product.getName());
		hold.mImage.setTag(Model.PRODUCT_IMAGE_URL + product.getImage());
		hold.mPrice.setText("¥"+String.valueOf(product.getPrice()));
		hold.mSalesVolume.setText(" X "+String.valueOf(mShopCartGoodslist.get(position).getSalesVolume()));
		hold.mGiftsVolume.setText(mContext.getString(R.string.gifts)+String.valueOf(mShopCartGoodslist.get(position).getGiftsVolume()));
		hold.mSubtotalVolume.setText(String.valueOf(mShopCartGoodslist.get(position).getTotalVolume()));

		BigDecimal bd = new BigDecimal(mShopCartGoodslist.get(position).getTotalAmount());
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		hold.msubtotalAmount.setText("¥"+bd.doubleValue());
		hold.mForegiftSum.setText(String.valueOf(mShopCartGoodslist.get(position).getTotalForegift()));
		if(mShopCartGoodslist.get(position).getGiftsVolume()==0){
			hold.mGiftsVolume.setVisibility(View.GONE);
		}else{
			hold.mGiftsVolume.setVisibility(View.VISIBLE);
		}
		if (mShopCartGoodslist.get(position).getProduct().getForegift()==0) {
			hold.mForegift.setVisibility(View.GONE);
			hold.mForegiftSum.setVisibility(View.GONE);
		}else
		{
			hold.mForegift.setVisibility(View.VISIBLE);
			hold.mForegiftSum.setVisibility(View.VISIBLE);
		}
		
		String imageURL = product.getImage();
		hold.mImage.setImageResource(R.drawable.shop_photo_frame);
		if (imageURL != null && !"".equals(imageURL)) {
			imageURL = Model.PRODUCT_IMAGE_URL+imageURL;
			mImageRequester.load(hold.mImage, imageURL, R.drawable.shop_photo_frame, R.drawable.shop_photo_frame);
		}else{
//			Log.e("ShopCartGoodsListAdapter", product.getName()+"图片缺失");
		}
		return view;
	}
	
	public void destroy(){
		mImageRequester.cancelAllRequest();
	}
}
