package com.bangware.shengyibao.returngood.adapter;

import java.util.List;
import java.util.Map;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.utils.volley.ImageRequester;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 退货产品详情数据源
 * @author ccssll
 *
 */
public class ReturnGoodProductAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInfalter;
	private List<ReturnNoteGoods> list;
	private ImageRequester mImageRequester;

	public ReturnGoodProductAdapter(Context context,List<ReturnNoteGoods> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list= list;
		this.mImageRequester = new ImageRequester(this.context, false);
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
			mInfalter = LayoutInflater.from(parent.getContext());
			convertView = mInfalter.inflate(R.layout.item_returngood_product_detail, null);
		}
		ImageView product_Image = ViewHolder.get(convertView, R.id.returnGood_detail_Product_Image);
		TextView product_Name = ViewHolder.get(convertView, R.id.returnGood_detail_Product_Name);
		TextView product_ReturnsVolume = ViewHolder.get(convertView, R.id.returnGood_detail_Product_SalesVolume);
		TextView product_price = ViewHolder.get(convertView, R.id.returnGood_product_unitPrice);
		TextView product_giftVolume = ViewHolder.get(convertView, R.id.returnGood_detail_Product_GiftsVolume);
		TextView totalSum = ViewHolder.get(convertView, R.id.returnGood_detail_Product_totalSum);
		LinearLayout basic_linearLayout = ViewHolder.get(convertView,R.id.basic_linearLayout);
		TextView basicVolume = ViewHolder.get(convertView,R.id.returnGood_detail_Product_BasicVolume);
		TextView basicPrice = ViewHolder.get(convertView,R.id.returnGood_product_basicprice);
		
		String image_url = list.get(position).getProduct().getImage();
		product_Image.setImageResource(R.drawable.shop_photo_frame);// 设置默认显示的图片
    	if (image_url != null && !"".equals(image_url)) {
    		image_url = Model.PRODUCT_IMAGE_URL +image_url;
			mImageRequester.load(product_Image, image_url, R.drawable.shop_photo_frame, R.drawable.shop_photo_frame);
        }else{  
        	Log.d("TAG", "11111");
        }

		if(list.get(position).getRetailVolume() == 0){
			basic_linearLayout.setVisibility(View.GONE);
		}else{
			basicVolume.setText(String.valueOf(list.get(position).getRetailVolume()));
			basicPrice.setText("¥"+String.valueOf(list.get(position).getProduct().getBasic_price())+"元");
			basic_linearLayout.setVisibility(View.VISIBLE);
		}
		
		String nameStr = list.get(position).getProduct().getName().toString();
		int returnsStr = list.get(position).getReturnsVolume();
		int giftStr = list.get(position).getGiftsVolume();
		double totalSumStr = list.get(position).getReturnsAmount();
		double priceStr = list.get(position).getPrice();
		
		product_Name.setText(nameStr);
		product_ReturnsVolume.setText(String.valueOf(returnsStr));
		product_giftVolume.setText("退赠："+giftStr);
		totalSum.setText("¥"+totalSumStr+"元");
		product_price.setText("¥"+priceStr+"元");
		
		return convertView;
	}

}
