package com.bangware.shengyibao.returngood.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bangware.shengyibao.utils.volley.ImageRequester;

import java.util.List;


public class ReturnCartGoodsListAdapter extends BaseAdapter {

	private List<ReturnNoteGoods> mReturnNoteGoodslist;
	private Context mContext;
	private ImageRequester mImageRequester;

	private class Holder{
//        public ImageView mImage;
        public TextView mName;
        public TextView mSalesVolume;
        public TextView mGiftsVolume;
        public TextView mPrice;
        public TextView mSubtotalVolume;
        public TextView msubtotalAmount;
        public TextView mForegift;
        public TextView mForegiftSum;
		public TextView mBasicPrice;
		public TextView mBasicVolume;
		public LinearLayout returnCart_basic_linearLayout;

    }
	public ReturnCartGoodsListAdapter(List<ReturnNoteGoods> list, Context context){
		this.mReturnNoteGoodslist = list;
		this.mContext = context;
		this.mImageRequester = new ImageRequester(mContext, false);
	}
	@Override
	public int getCount() {
		return mReturnNoteGoodslist.size();
	}

	@Override
	public Object getItem(int position) {
		return mReturnNoteGoodslist.get(position);
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
			view = View.inflate(mContext, R.layout.activity_settlement_returncartgoods_item, null);
			hold.mName = (TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_Product_Name);
//			hold.mImage = (ImageView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_Product_Image);
			hold.mSalesVolume = (TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_SalesVolume);
			hold.mGiftsVolume = (TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_GiftsVolume);
			hold.mPrice = (TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_Price);
			hold.mSubtotalVolume = (TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item__SubtotalVolume);
			hold.msubtotalAmount = (TextView)view.findViewById(R.id.ReturnsCart_GoodsList_Item_SubtotalAmount);
			hold.mForegift=(TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_Product_Foregift);
			hold.mForegiftSum=(TextView) view.findViewById(R.id.ReturnsCart_GoodsList_Item_Product_Foregift_sum);
			hold.returnCart_basic_linearLayout = (LinearLayout) view.findViewById(R.id.returnCart_basic_linearLayout);
			hold.mBasicPrice = (TextView)view.findViewById(R.id.ReturnsCart_GoodsList_Item_BasicPrice);
			hold.mBasicVolume = (TextView)view.findViewById(R.id.ReturnsCart_GoodsList_Item_BasicVolume);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		Product product = mReturnNoteGoodslist.get(position).getProduct();
		hold.mName.setText(product.getName());
//		hold.mImage.setTag(Model.PRODUCT_IMAGE_URL + product.getImage());
		hold.mPrice.setText("¥"+String.valueOf(product.getPrice()));
		hold.mSalesVolume.setText(" X "+String.valueOf(mReturnNoteGoodslist.get(position).getReturnsVolume())+mReturnNoteGoodslist.get(position).getProduct().getUnit());
		hold.mGiftsVolume.setText(mContext.getString(R.string.gifts)+String.valueOf(mReturnNoteGoodslist.get(position).getGiftsVolume())+mReturnNoteGoodslist.get(position).getProduct().getUnit());
		hold.mSubtotalVolume.setText(String.valueOf(mReturnNoteGoodslist.get(position).getTotalVolume()));

		hold.msubtotalAmount.setText("总计：¥"+ NumberUtils.toDoubleDecimal(mReturnNoteGoodslist.get(position).getTotalAmount()));

		hold.mForegiftSum.setText(String.valueOf(mReturnNoteGoodslist.get(position).getTotalForegift()));

		if (mReturnNoteGoodslist.get(position).getRetailVolume()==0){
			hold.returnCart_basic_linearLayout.setVisibility(View.GONE);
		}else{
			hold.returnCart_basic_linearLayout.setVisibility(View.VISIBLE);
			hold.mBasicPrice.setText("¥"+String.valueOf(product.getBasic_price()));
			hold.mBasicVolume.setText(" X "+String.valueOf(mReturnNoteGoodslist.get(position).getRetailVolume())+mReturnNoteGoodslist.get(position).getProduct().getBasic_unit());
		}

		if(mReturnNoteGoodslist.get(position).getGiftsVolume()==0){
			hold.mGiftsVolume.setVisibility(View.GONE);
		}else{
			hold.mGiftsVolume.setVisibility(View.VISIBLE);
		}
		if (mReturnNoteGoodslist.get(position).getProduct().getForegift()==0) {
			hold.mForegift.setVisibility(View.GONE);
			hold.mForegiftSum.setVisibility(View.GONE);
		}else
		{
			hold.mForegift.setVisibility(View.VISIBLE);
			hold.mForegiftSum.setVisibility(View.VISIBLE);
		}
		
		/*String imageURL = product.getImage();
		hold.mImage.setImageResource(R.drawable.shop_photo_frame);
		if (imageURL != null && !"".equals(imageURL)) {
			imageURL = Model.PRODUCT_IMAGE_URL+imageURL;
			mImageRequester.load(hold.mImage, imageURL, R.drawable.shop_photo_frame, R.drawable.shop_photo_frame);
		}else{
			Log.e("ddd", ""+product.getName()+"图片缺失");
		}*/
		return view;
	}
	
	public void destroy(){
		mImageRequester.cancelAllRequest();
	}
}
