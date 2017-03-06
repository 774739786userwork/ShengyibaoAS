package com.bangware.shengyibao.shopcart.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.ShowShopCartPopupWindow;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.adapter.ShopCartProductListAdapter;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.shopcart.presenter.ShopCartPresenter;
import com.bangware.shengyibao.shopcart.presenter.impl.ShopCartPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.DensityUtil;
import com.jauker.widget.BadgeView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;


public class ShopCartAcitivity extends BaseActivity implements ShopCartView {
	private ListView productListView;//产品列表
	private ImageView goBack;//回退按钮
	private RelativeLayout shopCartIcon;
	private RelativeLayout detail_buy_board;
	private LinearLayout DeliveryNoteTitle;
	private Button toSettlementBtn;//结算按钮
	private int height;//屏幕的高度
	private List<Product> list = new ArrayList<Product>();
	private ShopCartProductListAdapter mAdapter= null;
	private ProductPopupWindow mPopupWindow;
	private ShowShopCartPopupWindow mShowShopCartPopupWindow;
	private BadgeView deliveryNoteTotalVolume;
	private TextView deliveryNoteAmountText;
	private TextView Customer_Id,Customer_Contact,Customer_Mobile,Customer_Name,Customer_Address;
	private long mExitTime = System.currentTimeMillis();
	private Contacts contact;
	private DeliveryNote deliveryNote;
	private ShopCartGoods shopCartGoods;
	private ShopCartPresenter presenter;
	private List<DeliveryNoteGoods> goodsList;
	private Customer customer;
	private User user;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		height=wm.getDefaultDisplay().getHeight();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deliverynote);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		deliveryNoteTotalVolume = new BadgeView(ShopCartAcitivity.this);
		deliveryNoteTotalVolume.setTargetView(findViewById(R.id.shopCartIcon));
		
		findViews();
		setListeners();
		
		Bundle bundle = this.getIntent().getExtras();
		//接收客户对象
		customer = (Customer)bundle.getSerializable("customer");
		//接收contacts_id联系人对象
		contact=(Contacts) bundle.getSerializable("contacts");
		//接收送货单对象
		deliveryNote = (DeliveryNote) bundle.getSerializable("deliveryNote");
		
		presenter = new ShopCartPresenterImpl(this);
		//设置购物车中的客户信息
		presenter.getShopCart().setCustomer(customer);
		updateCustomerInfo(customer);
		presenter.loadStocks(user);
		mAdapter = new ShopCartProductListAdapter(presenter.getShopCart(),list, this);
		productListView.setAdapter(mAdapter);
	}
	
	private void findViews(){
		deliveryNoteAmountText = (TextView)findViewById(R.id.ShopCart_Amount);
		goBack = (ImageView)findViewById(R.id.DeliveryNote_Goback);
		shopCartIcon=(RelativeLayout) findViewById(R.id.shopCartIcon);
		detail_buy_board=(RelativeLayout) findViewById(R.id.detail_buy_board);
		DeliveryNoteTitle=(LinearLayout) findViewById(R.id.DeliveryNoteTitle);
		toSettlementBtn =(Button)findViewById(R.id.ShopCart_toSettlementBtn);
		productListView = (ListView)findViewById(R.id.ProductListView);
		
		
		Customer_Id = (TextView) findViewById(R.id.ShopCart_Customer_Id);
		Customer_Contact = (TextView) findViewById(R.id.ShopCart_Customer_Contact);
		Customer_Mobile = (TextView) findViewById(R.id.ShopCart_Customer_Mobile);
		Customer_Name = (TextView) findViewById(R.id.ShopCart_Customer_Name); 
		Customer_Address = (TextView) findViewById(R.id.ShopCart_Customer_Address);
	}
	
	private void setListeners(){
		MyOnClickLietener myonclick = new MyOnClickLietener();
		goBack.setOnClickListener(myonclick);
		toSettlementBtn.setOnClickListener(myonclick);
		productListView.setOnItemClickListener(new ItemClickListener());
		shopCartIcon.setOnClickListener(myonclick);
	}
	
	
	private class MyOnClickLietener implements View.OnClickListener {
		public void onClick(View view) {
			int mID = view.getId();
			switch(mID){
				case R.id.DeliveryNote_Goback:
					dismissLoading();
					ShopCartAcitivity.this.finish();
					break;
					//点击购物篮图标显示已选的产品
				case R.id.shopCartIcon:
					if (Integer.parseInt((String)(deliveryNoteTotalVolume.getText()))>0) {
						mShowShopCartPopupWindow=new ShowShopCartPopupWindow(ShopCartAcitivity.this,presenter);
						//mShowShopCartPopupWindow.getContentView().getHeight();
						int goodsCount = presenter.getShopCart().getAllGoodsList().size();
						int popWindowHeight= DensityUtil.dip2px(ShopCartAcitivity.this, (goodsCount*49)+40+58);
						int d = popWindowHeight ;
						int h=detail_buy_board.getHeight()+productListView.getHeight()+DensityUtil.dip2px(ShopCartAcitivity.this,0);
						
						if (d>h) {
							mShowShopCartPopupWindow.setHeight(productListView.getHeight());
							mShowShopCartPopupWindow.showAsDropDown(view, 0, -h);
							
						}else{
						mShowShopCartPopupWindow.showAsDropDown(view, 0,-d);
						}
						backgroundAlpha(0.4f);
						mShowShopCartPopupWindow.setOnDismissListener(new OnDismissListener() {
							
							@Override
							public void onDismiss() {
								// TODO Auto-generated method stub
								backgroundAlpha(1f);
							}
						});
					}
					
					break;
				case R.id.ShopCart_toSettlementBtn:
					if(presenter.getShopCart().getCustomer()!=null){
						//通过bundle传递对象数据到下级页面
						Intent intent = new Intent(ShopCartAcitivity.this, SettlementActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("deliveryNote",  (Serializable)deliveryNote);
						bundle.putSerializable("shopCart",  (Serializable)presenter.getShopCart());
						bundle.putString("customer_id", Customer_Id.getText().toString());
						bundle.putSerializable("contacts", contact);
						intent.putExtras(bundle);
						startActivity(intent);
					}else{
						Toast.makeText(ShopCartAcitivity.this, "请选择开单客户！", Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
	}
	/** 
     * 设置添加屏幕的背景透明度 
     * @param bgAlpha 
     */  
	 private void backgroundAlpha(float bgAlpha)  
	    {  
	        WindowManager.LayoutParams lp = getWindow().getAttributes();  
	        lp.alpha = bgAlpha; //0.0-1.0  
	        getWindow().setAttributes(lp);  
	    }  
	
    /**
     * 更新客户信息显示
     * @param customer
     */
    private void updateCustomerInfo(Customer customer){
    	Customer_Id.setText(customer.getId());
    	Customer_Name.setText(customer.getName());
    	if(contact != null){
	    	Customer_Contact.setText(contact.getName());
	    	String mobile = contact.getMobile1();
	    	if(mobile==null || "".equals(mobile)){
	    		mobile = contact.getMobile2();
	    	}
	    	Customer_Mobile.setText(mobile);
    	}else{
    		Customer_Contact.setText("无");
    		Customer_Mobile.setText("无");
    	}
    	Customer_Address.setText(customer.getAddress());
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismissLoading();
			this.finish();
		}
		return true;
	}
	
	/**
	 * ListView中Item的点击事件处理
	 * 当dialog窗口点击确认关闭时，
	 * 读取购物车中的商品清单，更新listView的数据源并执行notifyDataSetChanged刷新页面
	 * @author luming.tang
	 *
	 */
	class  ItemClickListener implements OnItemClickListener     
	{     
		public void onItemClick(AdapterView<?> adapterView,View view,final int position,long row) 
		{     
			if ((System.currentTimeMillis() - mExitTime) > 1000) {
				Product item=(Product) adapterView.getItemAtPosition(position);  
				if(item.getStock()>0){
					mPopupWindow = new ProductPopupWindow(ShopCartAcitivity.this, item, presenter);
					mPopupWindow.showPopupWindow(view);
					backgroundAlpha(0.4f);  
					mPopupWindow.setOnDismissListener(new OnDismissListener(){
						@Override
						public void onDismiss() {
							backgroundAlpha(1f);
						}
					});
					mExitTime = System.currentTimeMillis();
				}
			}
		}     
		
	}    
	
    public void showMessage(String message){
    	showToast(message);
    }
	public void doChanged(ShopCart shopCart) {
		// TODO Auto-generated method stub

		double total_sum = shopCart.getTotalAmount();
		deliveryNoteAmountText.setText("¥"+total_sum+"元");
		deliveryNoteTotalVolume.setBadgeCount(shopCart.getTotalVolumes());
		if(shopCart.getTotalVolumes()>0){
			toSettlementBtn.setVisibility(View.VISIBLE);
		}else{
			toSettlementBtn.setVisibility(View.GONE);
		}
		mAdapter.notifyDataSetChanged();
	}
	private Product getProductFromList(List<Product> list, String id){
		for(Product p: list){
			if(p.getId().equals(id)){
				return p;
			}
		}
		return null;
	}
	/**
	 * 加载库存产品数据
	 */
	public void loadProductStock(List<Product> products){
		if(products.size() == 0){
			showAlertDialog();
		}else{
			list.clear();
			//根据库存排序
			Collections.sort(products,new Comparator<Product>(){
			   public int compare(Product p1, Product p2) {
			         return Integer.valueOf(p2.getStock()).compareTo(Integer.valueOf(p1.getStock()));
			    }
			});
			list.addAll(products);
			mAdapter.notifyDataSetChanged();
			
			if(deliveryNote!=null){
				goodsList = deliveryNote.getGoodsList();
				for(DeliveryNoteGoods g:goodsList){
					shopCartGoods=new ShopCartGoods();
					shopCartGoods.setProduct(g.getProduct());
					shopCartGoods.setPrice(g.getPrice());
					shopCartGoods.setSalesVolume(g.getSalesVolume());
					shopCartGoods.setGiftsVolume(g.getGiftsVolume());
					
					shopCartGoods.setAmount(g.getSalesVolume()+g.getGiftsVolume());
					Product product = getProductFromList(products, g.getProduct().getId());
					if(product!=null){
                        shopCartGoods.setGiftsVolume(g.getGiftsVolume());
                        shopCartGoods.setSalesVolume(g.getSalesVolume());
						shopCartGoods.setP_totalforegift(g.getP_totalforegift());
						product.setStock(g.getProduct().getStock());
						product.setPrice(shopCartGoods.getPrice());
						shopCartGoods.setProduct(product);
					}
					presenter.addGoods(shopCartGoods);
				}
			}
		}
	}
	private void showAlertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(ShopCartAcitivity.this,R.style.dialog);
		builder.setTitle("提示");
        builder.setMessage("当前暂无提货单！");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
       
        builder.setPositiveButton("确定！", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	dismissLoading();
            	finish();
                dialog.cancel();
            }
       });
       builder.create().show();
	}
	
	public void onDestroy(){
		super.onDestroy();
		if(this.mAdapter!=null)
			this.mAdapter.destory();
		if(presenter!=null)
			presenter.destroy();
	}

}
