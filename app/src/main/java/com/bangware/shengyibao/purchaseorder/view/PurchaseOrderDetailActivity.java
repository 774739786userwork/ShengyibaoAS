package com.bangware.shengyibao.purchaseorder.view;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.deliverynote.view.BluetoothPrinterActivity;
import com.bangware.shengyibao.main.view.FragmentSaler;
import com.bangware.shengyibao.purchaseorder.adapter.PurchaseOrderDetailAdapter;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderPresenter;
import com.bangware.shengyibao.purchaseorder.presenter.impl.PurchaseOrderPresenterImpl;
import com.bangware.shengyibao.shopcart.view.ShopCartAcitivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订货单详情
 * @author zcb
 *
 */
public class PurchaseOrderDetailActivity extends BaseActivity implements PurchaseOrderDetailView{
	private ImageButton purchaseorder_detail_GoBackBtn;
	private TextView customer_id,shop_name,contact_name,customer_mobile,customer_address;
	private TextView purchaseorder_summary,purchaseorder_Foregift;
	private ListView purchaseorder_Detail_GoodsListView;
	private LinearLayout resetBtn,resetPrinterBtn;
	private PurchaseOrderDetailAdapter detailAdapter;
	List<DeliveryNoteGoods> noteGoodsList = new ArrayList<DeliveryNoteGoods>();
	private LinearLayout bottom_linear;
	private PurchaseOrderPresenter notePresenter;
	private DeliveryNote deliveryNote = null;
	private Contacts contact=null;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_purshaseorder_detail);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		init();
		initView();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		purchaseorder_detail_GoBackBtn = (ImageButton) findViewById(R.id.purchaseorder_detail_GoBackBtn);
		resetBtn = (LinearLayout) findViewById(R.id.purchaseorder_resetBtn);
		resetPrinterBtn = (LinearLayout) findViewById(R.id.purchaseorder_confirm);
		customer_id = (TextView) findViewById(R.id.purchaseorder_detail_Customer_Id);
		shop_name = (TextView) findViewById(R.id.purchaseorder_detail_Customer_Name);
		contact_name = (TextView) findViewById(R.id.purchaseorder_detail_Customer_Contact);
		customer_mobile = (TextView) findViewById(R.id.purchaseorder_detail_Customer_Mobile);
		customer_address = (TextView) findViewById(R.id.purchaseorder_detail_Customer_Address);
		purchaseorder_summary = (TextView) findViewById(R.id.purchaseorder_summary);
		purchaseorder_Foregift=(TextView) findViewById(R.id.purchaseorder_Foregift);
		purchaseorder_Detail_GoodsListView = (ListView) findViewById(R.id.purchaseorder_Detail_GoodsListView);
		bottom_linear=(LinearLayout) findViewById(R.id.purchaseorder_bottom_linear);

		deliveryNote = (DeliveryNote)getIntent().getExtras().getSerializable("deliveryNote");
		
		contact=new Contacts();
		contact.setName(deliveryNote.getContact_name());
		contact.setMobile1(deliveryNote.getContact_phone());
		notePresenter = new PurchaseOrderPresenterImpl(this);
		notePresenter.doLoadDetail(user,deliveryNote.getDelivery_id());
		customer_id.setText(deliveryNote.getCustomer().getId());
		shop_name.setText(deliveryNote.getCustomer().getName());
		contact_name.setText(deliveryNote.getContact_name());
		customer_mobile.setText(contact.getMobile1());
		customer_address.setText(deliveryNote.getCustomer().getAddress());
		purchaseorder_Foregift.setText("其中押金¥"+deliveryNote.getTotalForeigft()+"元");
//		customer_address.setText(bundle.getString("address"));
		
		detailAdapter = new PurchaseOrderDetailAdapter(this,noteGoodsList);
		purchaseorder_Detail_GoodsListView.setAdapter(detailAdapter);
		String date1= getIntent().getExtras().getString("currenttime");
		String date2=deliveryNote.getDelivery_date();
		int result=date1.compareTo(date2);
		if (result!=0) {
			bottom_linear.setVisibility(View.GONE);
		}
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		MyOnClickLinstener clickLinstener = new MyOnClickLinstener();
		purchaseorder_detail_GoBackBtn.setOnClickListener(clickLinstener);
		resetBtn.setOnClickListener(clickLinstener);
		resetPrinterBtn.setOnClickListener(clickLinstener);
	}

	@Override
	public void addPurchaseOrderDetailProduct(List<DeliveryNoteGoods> newList) {
		int product_total_count = 0;
		if(newList.size() > 0){
			noteGoodsList.clear();
			noteGoodsList.addAll(newList);
			//todo notifyDatasetChange();
			deliveryNote.setUser(AppContext.getInstance().getUser());
			deliveryNote.setGoodsList(noteGoodsList);

			for (DeliveryNoteGoods goods: noteGoodsList) {
				product_total_count += goods.getTotalVolume();
			}
			deliveryNote.setTotalVolumes(product_total_count);
			String car_number = deliveryNote.getCarNumber();
			deliveryNote.setCarNumber(car_number);
			purchaseorder_summary.setText("共"+deliveryNote.getTotalVolumes()+"件商品 总计¥"+deliveryNote.getTotalAmount()+"元");
			detailAdapter.notifyDataSetChanged();
		}else{
			showToast("暂无订货单产品详情记录");
		}
	}

	@Override
	public void save() {
		Intent intent = new Intent(PurchaseOrderDetailActivity.this, BluetoothPrinterActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle bundle =  new Bundle();
		bundle.putSerializable("deliveryNote", (Serializable) deliveryNote);
		bundle.putSerializable("contacts", contact);
		intent.putExtras(bundle);
		startActivity(intent); //执行
	}

	private class MyOnClickLinstener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			//返回键处理
			if(v.getId() == R.id.purchaseorder_detail_GoBackBtn){
				loadingdialog.dismiss();
				PurchaseOrderDetailActivity.this.finish();
			}
			//修改订单
			if(v.getId() == R.id.purchaseorder_resetBtn){
//				resetSalerDialog();
				Intent intent = new Intent(PurchaseOrderDetailActivity.this, ShopCartAcitivity.class);
				Bundle bundle =new Bundle();
				//传递客户数据
				bundle.putSerializable("customer", deliveryNote.getCustomer());
				bundle.putSerializable("deliveryNote", deliveryNote);
				bundle.putSerializable("contacts", contact);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			//确认配送
			if(v.getId() == R.id.purchaseorder_confirm){
				notePresenter.update_purchase_order(user,deliveryNote,0,0,0,deliveryNote.getTotalAmount());
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

	public void onDestroy(){
		if(notePresenter!=null)
			notePresenter.destroy();
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	loadingdialog.dismiss();
        	finish();
        }
		return true;
	}
}
