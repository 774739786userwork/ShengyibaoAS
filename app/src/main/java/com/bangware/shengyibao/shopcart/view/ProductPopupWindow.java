package com.bangware.shengyibao.shopcart.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.shopcart.presenter.ShopCartPresenter;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bangware.shengyibao.utils.volley.ImageRequester;


/**
 * 送货单产品信息对话框
 * 
 * @author luming.tang
 * 
 */
public class ProductPopupWindow extends PopupWindow {
	private Context mContext;
	private Product mProduct;
	private ShopCartGoods shopCartGoods;
	private ImageView productImage = null;
	private EditText priceView, salesVolumeView, giftsVolumeView;
	private TextView productStock, foregiftView, foregiftView_price;
	private Button salesVolumeAddBtn, salesVolumeSubBtn, giftsVolumeAddBtn,
			giftsVolumeSubBtn;

	public ProductPopupWindow(final Context context, final Product product,
			final ShopCartPresenter shopCartPresenter) {
		this.mContext = context;
		this.mProduct = product;
		shopCartGoods = shopCartPresenter.getShopCart().getGoods(
				product.getId());
		// 如果购物车中没有此产品
		if (shopCartGoods == null) {
			shopCartGoods = new ShopCartGoods();
			shopCartGoods.setProduct(product);
			shopCartGoods.setPrice(product.getPrice());
		}
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = inflater.inflate(
				R.layout.deliverynote_product_popup_dialog, null);
		contentView.setFocusable(true);
		contentView.setFocusableInTouchMode(true);
		this.setContentView(contentView);
		this.setBackgroundDrawable(new ColorDrawable(0x00000000));
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(false);
		this.setAnimationStyle(R.style.anim_menu_bottombar);
		productImage = (ImageView) contentView
				.findViewById(R.id.product_dialog_image);
		// 加载产品图片
		ImageRequester imageRequester = new ImageRequester(mContext, true);
		productImage.setImageResource(R.drawable.shop_photo_frame);
		String imageURL = shopCartGoods.getProduct().getImage();
		if (imageURL != null && !"".equals(imageURL)) {
			imageRequester.load(productImage, Model.PRODUCT_IMAGE_URL
					+ imageURL, R.drawable.shop_photo_frame,
					R.drawable.shop_photo_frame);
		}

		// 产品名称
		TextView productNameView = (TextView) contentView
				.findViewById(R.id.product_dialog_pname);
		productNameView.setText(shopCartGoods.getProduct().getName());

		// 产品库存
		productStock = (TextView) contentView
				.findViewById(R.id.product_dialog_pstock);
		productStock.setText(String.valueOf(shopCartGoods.getProduct()
				.getStock()));

		// 产品单价
		priceView = (EditText) contentView
				.findViewById(R.id.ProductDialog_Price);
		priceView
				.setText(String.valueOf(shopCartGoods.getProduct().getPrice()));

		salesVolumeView = (EditText) contentView
				.findViewById(R.id.ProductDialog_SalesVolume);
		salesVolumeView.setFilters(new InputFilter[] { new InputFilterMax(
				InputFilterMax.SALES) });
		// 押金
		foregiftView = (TextView) contentView
				.findViewById(R.id.ProductDialog_ForegiftTxt);
		foregiftView_price = (TextView) contentView
				.findViewById(R.id.ProductDialog_Foregift);
		if (shopCartGoods.getProduct().getForegift()==0) {
			foregiftView_price.setVisibility(View.GONE);
			foregiftView.setVisibility(View.GONE);
		}else{
		foregiftView_price.setText(String.valueOf(shopCartGoods.getProduct().getForegift()));
		}
		giftsVolumeView = (EditText) contentView
				.findViewById(R.id.ProductDialog_GiftsVolume);
		giftsVolumeView.setFilters(new InputFilter[] { new InputFilterMax(
				InputFilterMax.GIFTS) });
		// if(goods!=null){
		priceView.setText(String.valueOf(shopCartGoods.getPrice()));
		salesVolumeView.setText(String.valueOf(shopCartGoods.getSalesVolume()));
		giftsVolumeView.setText(String.valueOf(shopCartGoods.getGiftsVolume()));
		// }

		salesVolumeAddBtn = (Button) contentView
				.findViewById(R.id.ProductDialog_SalesVolume_AddBtn);
		salesVolumeSubBtn = (Button) contentView
				.findViewById(R.id.ProductDialog_SalesVolume_SubstractBtn);
		giftsVolumeAddBtn = (Button) contentView
				.findViewById(R.id.ProductDialog_GiftsVolume_AddBtn);
		giftsVolumeSubBtn = (Button) contentView
				.findViewById(R.id.ProductDialog_GiftsVolume_SubstractBtn);


		// 销量加
		salesVolumeAddBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int salesVolume = NumberUtils.toInt(salesVolumeView.getText()
						.toString()) + 1;
				salesVolume = (salesVolume > 0) ? salesVolume : 0;
				salesVolumeView.setText(String.valueOf(salesVolume));
			}
		});

		// 销量减
		salesVolumeSubBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int salesVolume = NumberUtils.toInt(salesVolumeView.getText()
						.toString()) - 1;
				salesVolume = (salesVolume > 0) ? salesVolume : 0;
				salesVolumeView.setText(String.valueOf(salesVolume));
			}
		});

		//单价edittext输入监听
		priceView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub
				if (priceView.getText().toString().indexOf(".") >= 0) {
					if (priceView.getText().toString().indexOf(".", priceView.getText().toString().indexOf(".") + 1) > 0) {
						Log.e("TAG", "onTextChanged: 已经输入\\\".\\\"不能重复输入");
						priceView.setText(priceView.getText().toString().substring(0, priceView.getText().toString().length() - 1));
						priceView.setSelection(priceView.getText().toString().length());
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// 销量edittext输入监听
		salesVolumeView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkAndUpdateButtonStatus();
			}
		});
		//押金输入框的更新

		foregiftView_price.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				double fordgift =shopCartGoods.getProduct().getForegift();
				int giftsVolume = NumberUtils.toInt(giftsVolumeView.getText()
						.toString());
				int salesVolume = NumberUtils.toInt(salesVolumeView.getText()
						.toString());

				shopCartGoods.getProduct().setForegift(fordgift);
			}
		});

		// 赠送量加
		giftsVolumeAddBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int giftsVolume = NumberUtils.toInt(giftsVolumeView.getText()
						.toString()) + 1;
				giftsVolume = (giftsVolume > 0) ? giftsVolume : 0;
				giftsVolumeView.setText(String.valueOf(giftsVolume));
			}
		});

		// 赠送量减
		giftsVolumeSubBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int giftsVolume = NumberUtils.toInt(giftsVolumeView.getText()
						.toString()) - 1;
				giftsVolume = (giftsVolume > 0) ? giftsVolume : 0;
				giftsVolumeView.setText(String.valueOf(giftsVolume));
			}
		});

		// 赠送量edittext输入监听事件
		giftsVolumeView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkAndUpdateButtonStatus();
			}
		});

		Button cancelBtn = (Button) contentView
				.findViewById(R.id.product_dialog_cancelbtn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ProductPopupWindow.this.dismiss();
			}
		});

		// popupwindow 弹出框
		Button okBtn = (Button) contentView
				.findViewById(R.id.product_dialog_okbtn);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				shopCartGoods.setPrice(NumberUtils.toDouble(priceView.getText()
						.toString()));
				int giftsVolume = NumberUtils.toInt(giftsVolumeView.getText()
						.toString());
				int salesVolume = NumberUtils.toInt(salesVolumeView.getText()
						.toString());
				int total_Volume=giftsVolume+salesVolume;
				double foregiftVolume = shopCartGoods.getProduct().getForegift();
				
				shopCartGoods.getProduct().setForegift(foregiftVolume);

				shopCartGoods.setGiftsVolume(giftsVolume);
				shopCartGoods.setSalesVolume(salesVolume);
				shopCartGoods.setAmount(shopCartGoods.getSalesVolume()
						+ shopCartGoods.getGiftsVolume());

				shopCartGoods.setSalesVolume(salesVolume);
				shopCartGoods.setGiftsVolume(giftsVolume);
                 shopCartGoods.setP_totalforegift(foregiftVolume*total_Volume);
				product.setPrice(NumberUtils.toDouble(priceView.getText()
						.toString()));
				// 如果产品总额为零就从购物车中移除产品否则添加到购物车
				if (shopCartGoods.getAmount() == 0) {
					shopCartGoods.setPrice(mProduct.getPrice());
					shopCartPresenter.removeGoods(shopCartGoods.getProduct());
				} else {
					shopCartPresenter.addGoods(shopCartGoods);
				}
				ProductPopupWindow.this.dismiss();
			}
		});

	}

	/**
	 * 更新按钮状态
	 */
	private void checkAndUpdateButtonStatus() {
		try {
			int sale_quantity = NumberUtils.toInt(salesVolumeView.getText()
					.toString());
			int gift_quantity = NumberUtils.toInt(giftsVolumeView.getText()
					.toString());

			int quantity = sale_quantity + gift_quantity;
			int product_stock = mProduct.getStock();
			if (quantity == product_stock) {
				giftsVolumeAddBtn.setClickable(false);
				giftsVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_empty);
				giftsVolumeSubBtn.setClickable(true);
				salesVolumeAddBtn.setClickable(false);
				salesVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_empty);
				salesVolumeSubBtn.setClickable(true);

			} else {
				giftsVolumeAddBtn.setClickable(true);
				giftsVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_add);
				giftsVolumeSubBtn.setClickable(true);
				salesVolumeAddBtn.setClickable(true);
				salesVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_add);
				salesVolumeSubBtn.setClickable(true);
			}
		} catch (Exception e) {
			Log.e("ProductPopupWindow",
					"checkAndUpdateButtonStatus出错：" + e.toString());
		}
	}

	class InputFilterMax implements InputFilter {

		public static final int SALES = 1;
		public static final int GIFTS = 2;
		private int destObject;

		public InputFilterMax(int destObject) {
			this.destObject = destObject;
		}

		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			try {
				char[] data = dest.toString().toCharArray();
				String newInput = "";
				int maxLength = (dest.toString() + source.toString()).length();
				int d = 0;
				for (int i = 0; i < maxLength; i++) {
					if (i <= dstart && i >= dend) {
						newInput += source;
					} else {
						if (d < data.length) {
							newInput += data[d++];
						}
					}
				}
				int input = NumberUtils.toInt(newInput);
				if (this.destObject == GIFTS) {
					int sale_quantity = NumberUtils.toInt(salesVolumeView
							.getText().toString());
					input += sale_quantity;
				} else if (this.destObject == SALES) {
					int gift_quantity = NumberUtils.toInt(giftsVolumeView
							.getText().toString());
					input += gift_quantity;
				}
				Log.e("PopuWindow", "inputData is " + input + ":" + newInput
						+ " " + dest.toString() + ":" + source.toString()
						+ "  start:" + dstart + " end:" + dend + " maxLength:"
						+ maxLength);
				int max = mProduct.getStock();
				if (isInRange(0, max, input)) {
					return null;
				}
			} catch (NumberFormatException nfe) {
			}
			return "";
		}

		private boolean isInRange(int a, int b, int c) {
			return b > a ? c >= a && c <= b : c >= b && c <= a;
		}
	}

	/**
	 * 显示popupWindow
	 * 
	 * @param parent
	 */
	public void showPopupWindow(View view) {
		if (!this.isShowing()) {
			this.showAtLocation(view, Gravity.CENTER
					| Gravity.CENTER_HORIZONTAL, 0, 0);
		} else {
			this.dismiss();
		}
	}

}
