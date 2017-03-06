package com.bangware.shengyibao.deliverynote.adapter;

import java.util.List;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.utils.volley.ImageRequester;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 送货单详情页产品列表适配器
 * @author ccssll
 *
 */
public class DeliveryNoteDetailAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<DeliveryNoteGoods> goodslist;
	private ImageRequester mImageRequester;
	public DeliveryNoteDetailAdapter( Context context,List<DeliveryNoteGoods> goodslist){
		this.goodslist = goodslist;
		this.context = context;
		this.mImageRequester = new ImageRequester(this.context, false);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goodslist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return goodslist.get(position);
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
			mInflater = LayoutInflater.from(parent.getContext());
			convertView = mInflater.inflate(R.layout.item_deliverynote_detail, null);
		}
		final ImageView product_image = ViewHolder.get(convertView, R.id.deliveryNote_detail_Product_Image);
		TextView  product_name = ViewHolder.get(convertView, R.id.deliveryNote_detail_GoodsList_Product_Name);
		TextView  product_saler_money = ViewHolder.get(convertView, R.id.deliveryNote_detail_GoodsList_Sale_Summary);
		TextView  product_saler_quantity = ViewHolder.get(convertView, R.id.deliveryNote_detail_Product_SalesVolume);
		TextView  product_gift_quantity = ViewHolder.get(convertView, R.id.deliveryNote_detail_Product_GiftsVolume);
		TextView  product_total_amount = ViewHolder.get(convertView, R.id.deliveryNote_detail_Product_SubtotalAmount);
		TextView  p_totalforegift=ViewHolder.get(convertView,R.id.p_totalforegift);
		TextView  p_totalforegift_txt=ViewHolder.get(convertView,R.id.p_totalforegift_txt);


		Product product = goodslist.get(position).getProduct();
		
		String productName = product.getName();
		Double productSalerMoney = goodslist.get(position).getPrice();
		int productSalerQuantity = goodslist.get(position).getSalesVolume();
		int productGiftQuantity = goodslist.get(position).getGiftsVolume();
		Double productTotalSum = goodslist.get(position).getAmount();
		
		String image_url =  product.getImage();
		product_image.setImageResource(R.drawable.shop_photo_frame);// 设置默认显示的图片
		//下载图片，第二个参数是否缓存至内存中  
    	if (image_url != null && !"".equals(image_url)) {
    		image_url = Model.PRODUCT_IMAGE_URL +image_url;
			mImageRequester.load(product_image, image_url, R.drawable.shop_photo_frame, R.drawable.shop_photo_frame);
        }else{
        	Log.d("TAG", "11111");
        }
		product_name.setText(productName);
		product_saler_money.setText("售价："+Double.valueOf(productSalerMoney)+" X ");
		product_saler_quantity.setText(String.valueOf(productSalerQuantity));
		product_gift_quantity.setText("赠送："+String.valueOf(productGiftQuantity));
		product_total_amount.setText(Double.valueOf(productTotalSum)+"元");

		p_totalforegift_txt.setText(product.getP_totalforegift()+"元");
		if (product.getP_totalforegift()==0)
		{
			p_totalforegift.setVisibility(View.GONE);
			p_totalforegift_txt.setVisibility(View.GONE);
		}
		return convertView;
	}
}
