package com.bangware.shengyibao.returngood.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.presenter.ReturnsProductPresenter;
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
public class ReturnsProductPopupWindow extends PopupWindow {
	private Context mContext;
	private Product mProduct;
	private ReturnNoteGoods returnNoteGoods;
	private ImageView productImage = null;
	private EditText priceView, salesVolumeView, giftsVolumeView,retailVolumeView;
	private TextView  foregiftView, foregiftView_price,retail_price,unit,basicunit,ProductDialog_GiftsVolumeTxt;
	private Button salesVolumeAddBtn, salesVolumeSubBtn, giftsVolumeAddBtn,
			giftsVolumeSubBtn,retailVolumeAddBtn,retailVolumeSubBtn;
	private LinearLayout retailPrice_linearLayout,retail_volumes_linearLayout;

	public ReturnsProductPopupWindow(final Context context, final Product product,
									 final ReturnsProductPresenter returnsProductPresenter) {
		this.mContext = context;
		this.mProduct = product;
		returnNoteGoods = returnsProductPresenter.getReturnCart().getGoods(
				product.getId());
		// 如果购物车中没有此产品
		if (returnNoteGoods == null) {
			returnNoteGoods = new ReturnNoteGoods();
			returnNoteGoods.setProduct(product);
			returnNoteGoods.setPrice(product.getPrice());
		}
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = inflater.inflate(
				R.layout.returns_product_popup_dialog, null);
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
		String imageURL = returnNoteGoods.getProduct().getImage();
		if (imageURL != null && !"".equals(imageURL)) {
			imageRequester.load(productImage, Model.PRODUCT_IMAGE_URL
					+ imageURL, R.drawable.shop_photo_frame,
					R.drawable.shop_photo_frame);
		}

		// 产品名称
		TextView productNameView = (TextView) contentView
				.findViewById(R.id.ReturnsProductDialog_pname);
		productNameView.setText(returnNoteGoods.getProduct().getName());
		productNameView.setText(product.getName());

		// 产品单价
		priceView = (EditText) contentView
				.findViewById(R.id.ReturnsProductDialog_Price);
		priceView.setText(String.valueOf(returnNoteGoods.getProduct().getPrice()));
		priceView.setText(String.valueOf(product.getPrice()));
		salesVolumeView = (EditText) contentView
				.findViewById(R.id.ReturnsProductDialog_SalesVolume);
		/*salesVolumeView.setFilters(new InputFilter[] { new InputFilterMax(
				InputFilterMax.SALES) });*/
		// 押金
		foregiftView = (TextView) contentView
				.findViewById(R.id.ReturnsProductDialog_ForegiftTxt);
		foregiftView_price = (TextView) contentView
				.findViewById(R.id.ReturnsProductDialog_Price_Foregift);
		if (returnNoteGoods.getProduct().getForegift()==0) {
			foregiftView_price.setVisibility(View.GONE);
			foregiftView.setVisibility(View.GONE);
		}else{
		foregiftView_price.setText(String.valueOf(returnNoteGoods.getProduct().getForegift()));
		}
		//零售价
		retailPrice_linearLayout = (LinearLayout) contentView.findViewById(R.id.retailPrice_linearLayout);
		retail_volumes_linearLayout = (LinearLayout) contentView.findViewById(R.id.retail_volumes_linearLayout);
		retail_price = (TextView) contentView.findViewById(R.id.retail_price_textview);
		if(returnNoteGoods.getProduct().getIs_show().equals("1")){
			retailPrice_linearLayout.setVisibility(View.VISIBLE);
			retail_volumes_linearLayout.setVisibility(View.VISIBLE);
			retail_price.setText(String.valueOf(returnNoteGoods.getProduct().getBasic_price()+"元"));
		}else{
			retailPrice_linearLayout.setVisibility(View.GONE);
			retail_volumes_linearLayout.setVisibility(View.GONE);
		}
		ProductDialog_GiftsVolumeTxt= (TextView) contentView.findViewById(R.id.ProductDialog_GiftsVolumeTxt);
		ProductDialog_GiftsVolumeTxt.setText("退赠("+mProduct.getUnit()+"):");
		unit = (TextView)contentView.findViewById(R.id.ReturnsProductDialog_SalesVolumeTxt);
		unit.setText("数量("+mProduct.getUnit()+"):");

		basicunit = (TextView)contentView.findViewById(R.id.productRetailVolumeTxt);
		basicunit.setText("数量("+mProduct.getBasic_unit()+"):");

		giftsVolumeView = (EditText) contentView
				.findViewById(R.id.ReturnsProductDialog_Price_GiftsVolume);
		retailVolumeView = (EditText) contentView
				.findViewById(R.id.productRetail_Price_edittext);
		/*giftsVolumeView.setFilters(new InputFilter[] { new InputFilterMax(
				InputFilterMax.GIFTS) });*/
		priceView.setText(String.valueOf(returnNoteGoods.getPrice()));
		salesVolumeView.setText(String.valueOf(returnNoteGoods.getReturnsVolume()));
		giftsVolumeView.setText(String.valueOf(returnNoteGoods.getGiftsVolume()));
		retailVolumeView.setText(String.valueOf(returnNoteGoods.getRetailVolume()));

		salesVolumeAddBtn = (Button) contentView
				.findViewById(R.id.ReturnsProductDialog_SalesVolume_AddBtn);
		salesVolumeSubBtn = (Button) contentView
				.findViewById(R.id.ReturnsProductDialog_SalesVolume_SubstractBtn);
		giftsVolumeAddBtn = (Button) contentView
				.findViewById(R.id.ReturnsProductDialog_GiftsVolume_AddBtn);
		giftsVolumeSubBtn = (Button) contentView
				.findViewById(R.id.ReturnsProductDialog_GiftsVolume_SubstractBtn);
		retailVolumeAddBtn = (Button) contentView
				.findViewById(R.id.productRetailVolume_AddBtn);
		retailVolumeSubBtn = (Button) contentView
				.findViewById(R.id.productRetailVolume_SubstractBtn);

		//零售数量加
		retailVolumeAddBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int retailVolume = NumberUtils.toInt(retailVolumeView.getText().toString()) + 1;
				retailVolume = (retailVolume > 0) ? retailVolume : 0;
				retailVolumeView.setText(String.valueOf(retailVolume));
			}
		});

		//零售数量减
		retailVolumeSubBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int retailVolume = NumberUtils.toInt(retailVolumeView.getText().toString()) - 1;
				retailVolume = (retailVolume > 0) ? retailVolume : 0;
				retailVolumeView.setText(String.valueOf(retailVolume));
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
//				checkAndUpdateButtonStatus();
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
//				checkAndUpdateButtonStatus();
			}
		});

		Button cancelBtn = (Button) contentView
				.findViewById(R.id.ReturnsProductDialog_cancelbtn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ReturnsProductPopupWindow.this.dismiss();
			}
		});

		// popupwindow 弹出框
		Button okBtn = (Button) contentView
				.findViewById(R.id.ReturnsProductDialog_okbtn);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				returnNoteGoods.setPrice(NumberUtils.toDouble(priceView.getText()
						.toString()));
				int giftsVolume = NumberUtils.toInt(giftsVolumeView.getText()
						.toString());
				int salesVolume = NumberUtils.toInt(salesVolumeView.getText()
						.toString());

				int retailVolume = NumberUtils.toInt(retailVolumeView.getText().toString());
                 Log.e("giftsVolume", "赠送"+giftsVolume+"销量"+salesVolume+"零售数量"+retailVolume);

				double foregiftVolume = returnNoteGoods.getProduct().getForegift();
				returnNoteGoods.getProduct().setForegift(foregiftVolume);

				returnNoteGoods.setGiftsVolume(giftsVolume);
				returnNoteGoods.setReturnsVolume(salesVolume);
				returnNoteGoods.setRetailVolume(retailVolume);

				Log.d("eeee","数量：：：：："+returnNoteGoods.getRetailVolume());
				//产品总金额
				returnNoteGoods.setReturnsAmount(returnNoteGoods.getReturnsVolume()
						+ returnNoteGoods.getGiftsVolume() + returnNoteGoods.getRetailVolume());

				returnNoteGoods.setBasic_price(mProduct.getBasic_price());

				mProduct.setPrice(NumberUtils.toDouble(priceView.getText()
						.toString()));
				// 如果产品总额为零就从购物车中移除产品否则添加到购物车
				if (returnNoteGoods.getReturnsAmount() == 0) {
					returnNoteGoods.setPrice(mProduct.getPrice());
					returnsProductPresenter.removeGoods(returnNoteGoods.getProduct());
				} else {
					returnsProductPresenter.addGoods(returnNoteGoods);
				}
				ReturnsProductPopupWindow.this.dismiss();
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

//			int quantity = sale_quantity + gift_quantity;
//			int product_stock = mProduct.getStock();
			/*if (quantity == product_stock) {
				giftsVolumeAddBtn.setClickable(false);
				giftsVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_empty);
				giftsVolumeSubBtn.setClickable(true);
				salesVolumeAddBtn.setClickable(false);
				salesVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_empty);
				salesVolumeSubBtn.setClickable(true);

			} else {*/
				giftsVolumeAddBtn.setClickable(true);
				giftsVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_add);
				giftsVolumeSubBtn.setClickable(true);
				salesVolumeAddBtn.setClickable(true);
				salesVolumeAddBtn
						.setBackgroundResource(R.drawable.goods_button_add);
				salesVolumeSubBtn.setClickable(true);
		//	}
		} catch (Exception e) {
			Log.e("ProductPopupWindow",
					"checkAndUpdateButtonStatus出错：" + e.toString());
		}
	}

	/*class InputFilterMax implements InputFilter {

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
	}*/

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
