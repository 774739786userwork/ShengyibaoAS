package com.bangware.shengyibao.shopcart.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.RemarkPopupWindow;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.view.BluetoothPrinterActivity;
import com.bangware.shengyibao.shopcart.adapter.ShopCartGoodsListAdapter;
import com.bangware.shengyibao.shopcart.model.entity.Payment;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.shopcart.presenter.SettlementPresenter;
import com.bangware.shengyibao.shopcart.presenter.impl.SettlementPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.NumberUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettlementActivity extends BaseActivity implements SettlementView {
	private CheckBox round_checkbox,delete_small_count_checkbox;//四舍五入与抹零
	private ImageView goBack;
	private TextView settlement_summary;
	public  static int flag=0;
	private EditText settlement_totalAmount;
	private TextView shopCart_remark,remark_textview,RemarkText,settlement_foregift;
	private Button settlement_BuyBtn;
	private ListView listView;
	private ShopCart shopCart = null;
	private TextView customer_Id,customer_contact,customer_mobile,customer_name,customer_address;
	private TextView rounding_text,small_count_text;
	private EditText longitude,latitude;
	private ShopCartGoodsListAdapter mAdapter;
	private SettlementPresenter presenter;
	private Payment payment=new Payment(0,0,0,0);
	private BigDecimal bd;
	private double small_change_amount;
	private double rounding_amount;
	//地理定位
	private LocationClient mLocClient;
    private MyLocationData locData;
    public  MyLocationListenner myListener = new MyLocationListenner();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; // 是否首次定位
	private String remark;
	private Contacts contact;
	private DeliveryNote delivery_Note;
	private double receive_edit;
	private User user;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settlement);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findViews();
		setListeners();

		mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
        mLocClient.setLocOption(option);
        mLocClient.start();//	调用此方法开始定位

	}
	/**
	 * 初始化UI中的View对象
	 */
	private void findViews(){
		round_checkbox = (CheckBox) findViewById(R.id.round_checkbox);
		delete_small_count_checkbox = (CheckBox) findViewById(R.id.delete_small_count_checkbox);
		rounding_text = (TextView)findViewById(R.id.rounding_text);
		small_count_text = (TextView)findViewById(R.id.small_count_text);
		mMapView = (MapView) findViewById(R.id.bmapView_billing);
		longitude = (EditText) findViewById(R.id.longitude_edit);
		latitude = (EditText) findViewById(R.id.latitude_edit);

		shopCart_remark = (TextView) findViewById(R.id.ShopCart_Remark);
		remark_textview = (TextView) findViewById(R.id.remark_textview);
		RemarkText = (TextView) findViewById(R.id.RemarkText);
		customer_Id = (TextView) findViewById(R.id.ShopCart_Customer_Id);
		customer_contact = (TextView) findViewById(R.id.ShopCart_Customer_Contact);
		customer_mobile = (TextView) findViewById(R.id.ShopCart_Customer_Mobile);
		customer_name = (TextView) findViewById(R.id.ShopCart_Customer_Name);
		customer_address = (TextView) findViewById(R.id.ShopCart_Customer_Address);
		settlement_summary = (TextView)findViewById(R.id.ShopCart_settlement_summary);
		settlement_totalAmount =(EditText) findViewById(R.id.ShopCart_Settlement_TotalAmount);
		settlement_BuyBtn=(Button)findViewById(R.id.ShopCart_Settlement_BuyBtn);
		settlement_foregift=(TextView) findViewById(R.id.ShopCart_settlement_foregift);
		listView = (ListView)findViewById(R.id.ShopCart_GoodsListView);
		goBack = (ImageView)findViewById(R.id.Settlement_GoBackBtn);

		shopCart = (ShopCart) getIntent().getExtras().getSerializable("shopCart");
		//接收送货单对象
		delivery_Note = (DeliveryNote) getIntent().getExtras().getSerializable("deliveryNote");
		presenter = new SettlementPresenterImpl(this);
		mAdapter = new ShopCartGoodsListAdapter(shopCart.getAllGoodsList(), this);
		listView.setAdapter(mAdapter);
		initViewData(shopCart);
		bd = new BigDecimal(shopCart.getTotalAmount());
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		receive_edit=bd.doubleValue();

	}
	/**
	 * 初始化界面数据
	 * @param shopCart
	 */
	private void initViewData(ShopCart shopCart){
		contact=(Contacts) getIntent().getSerializableExtra("contacts");
		customer_Id.setText(shopCart.getCustomer().getId());
		if(contact != null){
			customer_contact.setText(contact.getName());
			String mobile = contact.getMobile1();
	    	if(mobile==null || "".equals(mobile)){
	    		mobile = contact.getMobile2();
	    	}
			customer_mobile.setText(mobile);
		}else{
			customer_contact.setText("无");
			customer_mobile.setText("无");
		}
		customer_name.setText(shopCart.getCustomer().getName());
		customer_address.setText(shopCart.getCustomer().getAddress());

		double totalAmount = shopCart.getTotalAmount();
		final int totalVolumes = shopCart.getTotalVolumes();
		int totalForegift=shopCart.getTotalForegift();
		bd = new BigDecimal(totalAmount);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		settlement_summary.setText("共"+totalVolumes+"件商品 总计 :¥"+bd.doubleValue());
		settlement_foregift.setText("其中押金总计:¥"+totalForegift);
		//本单实收
		settlement_totalAmount.setText(String.valueOf(bd.doubleValue()));
		//ShopCart_Settlement_BuyBtn
		settlement_BuyBtn.setText("收款 ¥"+bd.doubleValue());


	}
	/**
	 * 初始化界面监听
	 */
	private void setListeners(){
		MyOnClickLietener myonclick = new MyOnClickLietener();
		goBack.setOnClickListener(myonclick);
		shopCart_remark.setOnClickListener(myonclick);
		settlement_BuyBtn.setOnClickListener(myonclick);
		//输入框监听输入值改变事件
		settlement_totalAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				settlement_BuyBtn.setText("收款 ¥"+s);
				/**
				 * 判断小数点只能输入一位并保留两位小数
				 */
				if (settlement_totalAmount.getText().toString().indexOf(".") >= 0) {
					if (settlement_totalAmount.getText().toString().indexOf(".", settlement_totalAmount.getText().toString().indexOf(".") + 1) > 0) {
						Log.e("TAG", "onTextChanged: 已经输入小数点不能重复输入");
						settlement_totalAmount.setText(settlement_totalAmount.getText().toString().substring(0, settlement_totalAmount.getText().toString().length() - 1));
						settlement_totalAmount.setSelection(settlement_totalAmount.getText().toString().length());
					}
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

			@Override
			public void afterTextChanged(Editable s) {
				/**
				 * 实收金额不能超出总计金额
				 */
				try{
					double totalAmount = shopCart.getTotalAmount()+1;
					receive_edit = NumberUtils.toDouble(settlement_totalAmount.getText().toString());
					if (receive_edit > totalAmount){
						settlement_BuyBtn.setClickable(false);
						settlement_BuyBtn.setBackgroundColor(Color.GRAY);
					}else{
						settlement_BuyBtn.setClickable(true);
						settlement_BuyBtn.setBackgroundResource(R.drawable.dialog_positive_btn_bg);
					}
				}catch (Exception e){
					showToast("输入有误");
				}
			}
		});

		round_checkbox.setOnCheckedChangeListener(changeListener);
		delete_small_count_checkbox.setOnCheckedChangeListener(changeListener);
	}

	private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			//四舍五入复选框点击事件
			if(buttonView.getId() == R.id.round_checkbox){
				if (isChecked){
					settlement_totalAmount.setText(""+bd.doubleValue());
					//ShopCart_Settlement_BuyBtn
					settlement_BuyBtn.setText("收款 ¥"+bd.doubleValue());
					shopCart.setTotalAmount(bd.doubleValue());
					small_count_text.setText("");

					settlement_summary.setText("共"+shopCart.getTotalVolumes()+"件商品 总计 :¥"+NumberUtils.toDoubleDecimal(shopCart.getTotalAmount()));
					//本单实收
					settlement_totalAmount.setText(""+NumberUtils.toDoubleDecimal(shopCart.getTotalAmount()));
					settlement_BuyBtn.setText("收款 ¥"+NumberUtils.toDoubleDecimal(shopCart.getTotalAmount()));
					delete_small_count_checkbox.setChecked(false);

					double newrounding = NumberUtils.toDoubleDecimal(shopCart.getTotalAmount()).doubleValue();
					rounding_amount =  newrounding - shopCart.getTotalAmount();
					shopCart.setTotalAmount(newrounding);
					small_change_amount = 0;
					rounding_text.setText("(¥"+NumberUtils.toDoubleRound(rounding_amount)+")");
				}
			}else if(buttonView.getId() == R.id.delete_small_count_checkbox){//抹零复选框点击事件
				if (isChecked){
					settlement_totalAmount.setText(""+bd.doubleValue());
					//ShopCart_Settlement_BuyBtn
					settlement_BuyBtn.setText("收款 ¥"+bd.doubleValue());
					shopCart.setTotalAmount(bd.doubleValue());
					rounding_text.setText("");

					settlement_summary.setText("共"+shopCart.getTotalVolumes()+"件商品 总计 :¥"+Math.floor(shopCart.getTotalAmount()));
					//本单实收
					settlement_totalAmount.setText(""+Math.floor(shopCart.getTotalAmount()));
					//ShopCart_Settlement_BuyBtn
					settlement_BuyBtn.setText("收款 ¥"+Math.floor(shopCart.getTotalAmount()));
					round_checkbox.setChecked(false);

					double newSmallChange = Math.floor(shopCart.getTotalAmount());
					small_change_amount = shopCart.getTotalAmount() - newSmallChange;
					shopCart.setTotalAmount(newSmallChange);
					rounding_amount = 0;
					small_count_text.setText("(¥"+NumberUtils.toDoubleRound(small_change_amount)+")");
				}
			}else{

			}
		}
	};

	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            longitude.setText(String.valueOf(locData.longitude));
            latitude.setText(String.valueOf(locData.latitude));

            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;

                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        	if (poiLocation == null){
                return ;
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
    	if (mLocClient != null)
        mLocClient.stop();
		if (presenter!=null)
		{
			presenter.destroy();
			loadingdialog.dismiss();
		}
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }



	private class MyOnClickLietener implements View.OnClickListener {
		public void onClick(View v) {
			DeliveryNote deliveryNote= shopCart.toDeliveryNote();
			int mID = v.getId();
			switch(mID){
				//返回点击事件
				case R.id.Settlement_GoBackBtn:
					if (flag==0)
					{
					finish();
					}else
					{
						showToast("正在生成送货单，请稍后···");
						loadingdialog.show();
					}
					break;
				case R.id.ShopCart_Settlement_BuyBtn:
					double receive_amount = NumberUtils.toDouble(settlement_totalAmount.getText().toString());
					double unpaid_money =  shopCart.getTotalAmount()- receive_amount;
					deliveryNote.setReceiveAmount(receive_amount);
					deliveryNote.setUnpaidAmount(unpaid_money);
					deliveryNote.setDeliveryDate(new Date());
					String lng_itude = longitude.getText().toString();
					String lat_itude = latitude.getText().toString();
					remark = RemarkText.getText().toString();
					deliveryNote.setRemark(remark);
					deliveryNote.setPayment(payment);
					deliveryNote.setRounding(rounding_amount);
					deliveryNote.setSmallchange(small_change_amount);

					if(contact != null){
						deliveryNote.setContact_phone(contact.getMobile1());
						deliveryNote.setContact_name(contact.getName());
					}else{
						deliveryNote.setContact_phone("无");
						deliveryNote.setContact_name("老板");
					}

					if(null != delivery_Note){
						deliveryNote.setDelivery_id(delivery_Note.getDelivery_id());
					}

					if(!lng_itude.isEmpty() && !lat_itude.isEmpty()){
						try {
							deliveryNote.setLat(Double.valueOf(lat_itude));
							deliveryNote.setLng(Double.valueOf(lng_itude));
							///dialog text set 正在提交，请稍后
								flag = 1;
								presenter.doSave(user,deliveryNote);
								settlement_BuyBtn.setClickable(false);
								settlement_BuyBtn.setBackgroundColor(Color.GRAY);
						}catch (Exception ex){
							Log.e("eeeeeee",ex.toString());
						}
					}else{
						showToast("网络状况不佳，请稍后提交！");
					}

					break;
				case R.id.ShopCart_Remark:
					Intent intent = new Intent(SettlementActivity.this, RemarkPopupWindow.class);
					intent.putExtra("remark", remark);
					startActivityForResult(intent, 1000);
					break;
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
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001)
        {
        	if(remark_textview.getText().toString().isEmpty() && RemarkText.getText().toString().isEmpty()){
        		remark_textview.setVisibility(View.GONE);
            	RemarkText.setVisibility(View.GONE);
        	}else{
        		remark_textview.setVisibility(View.VISIBLE);
            	RemarkText.setVisibility(View.VISIBLE);
                remark = data.getStringExtra("remark");
                RemarkText.setText(remark);
        	}
        }
    }

	@Override
	public void doSaveSuccess(DeliveryNote deliveryNote) {
		//bundle对象传递参数
		Intent intent = new Intent(SettlementActivity.this, BluetoothPrinterActivity.class);
		Bundle bundle =  new Bundle();
		bundle.putSerializable("deliveryNote",  (Serializable)deliveryNote);
		bundle.putSerializable("contacts", contact);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (flag==1)
		{
			if (keyCode==KeyEvent.KEYCODE_BACK)
			{
				showToast("正在生成送货单，请稍后···");
				loadingdialog.show();
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
