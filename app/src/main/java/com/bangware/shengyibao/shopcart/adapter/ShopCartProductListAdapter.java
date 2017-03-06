package com.bangware.shengyibao.shopcart.adapter;

import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.utils.volley.ImageRequester;

public class ShopCartProductListAdapter extends BaseAdapter {
	private List<Product> mShopCartProductList;
	private Context context;
	private ShopCart shopCart;
	private ImageRequester mImageRequester;
	private class Holder{
	        public ImageView mImage;
	        public TextView mName;
	        public TextView mSpecifications;
	        public TextView mStock;
	        public TextView mActivity;
	        public TextView mPrice;
	        public TextView foregift;
	        public EditText numTxt;
	        public TextView foregift_Price;
	}
	public ShopCartProductListAdapter(ShopCart shopCart,List<Product> list, Context context){
		this.mShopCartProductList = list;
		this.shopCart=shopCart;
		this.context = context;
		this.mImageRequester = new ImageRequester(this.context, true);
	}
	@Override
	public int getCount() {
		return mShopCartProductList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mShopCartProductList.get(arg0);
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
			view = View.inflate(context, R.layout.deliverynote_products_item, null);
			hold.mName = (TextView) view.findViewById(R.id.product_name);
			hold.mImage = (ImageView) view.findViewById(R.id.product_image);
			hold.mActivity = (TextView) view.findViewById(R.id.product_activity);
			hold.mSpecifications = (TextView) view.findViewById(R.id.product_specifications);
			hold.mPrice = (TextView) view.findViewById(R.id.product_price);
			hold.mStock = (TextView) view.findViewById(R.id.product_stock);
			hold.foregift=(TextView) view.findViewById(R.id.product_foregift);
			hold.foregift_Price=(TextView) view.findViewById(R.id.product_foregift_price);
			/*hold.addBtn = (Button)view.findViewById(R.id.product_number_add);
			hold.subBtn = (Button)view.findViewById(R.id.product_number_substract);*/
			hold.numTxt = (EditText)view.findViewById(R.id.product_number);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		
		Product product = mShopCartProductList.get(position);
		hold.mName.setText(product.getName());
		hold.mImage.setTag(Model.PRODUCT_IMAGE_URL + product.getImage());
		
		hold.mActivity.setText(product.getActivity());
		if(!hold.mActivity.getText().toString().isEmpty()){
			hold.mActivity.setText(product.getActivity());
		}
		else{
			hold.mActivity.setText("无赠送");
		}
		
		hold.mSpecifications.setText(product.getSpecifications());
		hold.mPrice.setText(String.valueOf(product.getPrice()+"/元"));
		hold.mStock.setText(String.valueOf(product.getStock()));
		
		/*hold.addBtn.setTag(position);
		hold.subBtn.setTag(position);
		hold.addBtn.setOnClickListener(new addBtnClickListener());
		hold.subBtn.setOnClickListener(new substractBtnClickListener());*/
		
		
		//如果开单数量大于0，则将产品名称红字标识
		int totalVolumes = 0;
		ShopCartGoods goods = shopCart.getGoods(product.getId());
		if(goods!=null){
			totalVolumes = goods.getSalesVolume()+goods.getGiftsVolume();
			hold.foregift_Price.setTextColor(Color.BLACK);
			if (goods.getProduct().getForegift()!=0) {
				hold.foregift_Price.setText(String.valueOf(goods.getProduct().getForegift()));
				
			}else
			{
				hold.foregift.setVisibility(View.GONE);
				hold.foregift_Price.setVisibility(View.GONE);
			}
		}else
		{
			hold.foregift.setVisibility(View.GONE);
			hold.foregift_Price.setVisibility(View.GONE);
		}
			//数量大于0时产品名颜色为红色
			if(totalVolumes > 0){
				hold.mName.setTextColor(Color.RED);
				hold.numTxt.setTextColor(Color.BLACK);
				hold.numTxt.setText(String.valueOf(totalVolumes));
				
			}else{
				hold.mName.setTextColor(Color.BLACK);
				hold.numTxt.setTextColor(666666);
			}

		//库存为0时的动态操作
		int stockVolumes = product.getStock();
		if(stockVolumes == 0){
			view.setBackgroundColor(Color.LTGRAY);
			hold.mName.setTextColor(Color.DKGRAY);
			hold.mImage.setImageResource(R.drawable.product_sale_out);
		}else{
			String imageURL = product.getImage();
			hold.mImage.setImageResource(R.drawable.shop_photo_frame);
			if (imageURL != null && !"".equals(imageURL)) {
				imageURL = Model.PRODUCT_IMAGE_URL+imageURL;
				mImageRequester.load(hold.mImage, imageURL, R.drawable.shop_photo_frame, R.drawable.shop_photo_frame);
			}else{
				Log.e("ShopCartProductListAdapter", product.getName()+"图片缺失");
			}
			view.setBackgroundColor(Color.WHITE);
			view.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
		}
		/*if(totalVolumes==0){
			hold.numTxt.setVisibility(View.GONE);
			hold.subBtn.setVisibility(View.GONE);
		}else{
			hold.subBtn.setVisibility(View.VISIBLE);
			hold.numTxt.setVisibility(View.VISIBLE);
			hold.numTxt.setText(String.valueOf(totalVolumes));
		}*/
		
		/*RelativeLayout secondRowRightLayout = (RelativeLayout)view.findViewById(R.id.secondRowRightLayout);
		secondRowRightLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				//do nothing
			}
		});*/
		return view;
	}
	
	public void destory(){
		mImageRequester.cancelAllRequest();
	}
}
