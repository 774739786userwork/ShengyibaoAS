package com.bangware.shengyibao.deliverynote.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.CustomProgressDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.deliverynote.adapter.DeliveryNoteDetailAdapter;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNotePresenter;
import com.bangware.shengyibao.deliverynote.presenter.impl.DeliveryNotePresenterImpl;
import com.bangware.shengyibao.main.view.FragmentSaler;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.shopcart.view.ShopCartAcitivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.customdialog.CommonDialog;
import com.bangware.shengyibao.utils.customdialog.CustomDialog;

/**
 * 送货单详情
 * @author ccssll
 *
 */
public class DeliveryNoteDetailActivity extends BaseActivity implements DeliveryNoteDetailView{
	private ImageButton gobackBtn;
	private TextView customer_id,shop_name,contact_name,customer_mobile,customer_address;
	private TextView deliveryNote_settlement_summary,deliveryNote_settlement_Foregift;
	private ListView deliveryNoteDetail_ListView;
	private LinearLayout disuseBtn,resetBtn,resetPrinterBtn;
	private DeliveryNoteDetailAdapter detailAdapter;
	List<DeliveryNoteGoods> noteGoodsList = new ArrayList<DeliveryNoteGoods>();
	private LinearLayout bottom_linear;

	private DeliveryNotePresenter notePresenter;
	private DeliveryNote deliveryNote = null;
	private Contacts contact=null;
	private CommonDialog customDialog;

	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deliverynote_detail);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		init();
		initView();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		gobackBtn = (ImageButton) findViewById(R.id.deliverynote_detail_GoBackBtn);
		disuseBtn = (LinearLayout) findViewById(R.id.disuseBtn);
		resetBtn = (LinearLayout) findViewById(R.id.resetBtn);
		resetPrinterBtn = (LinearLayout) findViewById(R.id.resetPrinterBtn);
		customer_id = (TextView) findViewById(R.id.deliveryNote_detail_Customer_Id);
		shop_name = (TextView) findViewById(R.id.deliveryNote_detail_Customer_Name);
		contact_name = (TextView) findViewById(R.id.deliveryNote_detail_Customer_Contact);
		customer_mobile = (TextView) findViewById(R.id.deliveryNote_detail_Customer_Mobile);
		customer_address = (TextView) findViewById(R.id.deliveryNote_detail_Customer_Address);
		deliveryNote_settlement_summary = (TextView) findViewById(R.id.deliveryNote_settlement_summary);
		deliveryNote_settlement_Foregift=(TextView) findViewById(R.id.deliveryNote_settlement_Foregift);
		deliveryNoteDetail_ListView = (ListView) findViewById(R.id.deliveryNoteDetail_GoodsListView);
		bottom_linear=(LinearLayout) findViewById(R.id.bottom_linear);

		deliveryNote = (DeliveryNote)getIntent().getExtras().getSerializable("deliveryNote");
		String date1 = getIntent().getExtras().getString("currenttime");

		contact=new Contacts();
		contact.setName(deliveryNote.getContact_name());
		contact.setMobile1(deliveryNote.getContact_phone());
		notePresenter = new DeliveryNotePresenterImpl(this);
		notePresenter.doLoadDetail(user,deliveryNote.getDelivery_id());
		customer_id.setText(deliveryNote.getCustomer().getId());
		shop_name.setText(deliveryNote.getCustomer().getName());
		contact_name.setText(deliveryNote.getContact_name());
		customer_mobile.setText(contact.getMobile1());
		customer_address.setText(deliveryNote.getCustomer().getAddress());
		deliveryNote_settlement_Foregift.setText("其中押金¥"+deliveryNote.getTotalForeigft()+"元");
		detailAdapter = new DeliveryNoteDetailAdapter(this,noteGoodsList);
		deliveryNoteDetail_ListView.setAdapter(detailAdapter);

		String date2=deliveryNote.getDelivery_date();
		int result=date1.compareTo(date2);
		if (result!=0) {
			disuseBtn.setVisibility(View.GONE);
			resetBtn.setVisibility(View.GONE);
		}
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		MyOnClickLinstener clickLinstener = new MyOnClickLinstener();
		gobackBtn.setOnClickListener(clickLinstener);
		disuseBtn.setOnClickListener(clickLinstener);
		resetBtn.setOnClickListener(clickLinstener);
		resetPrinterBtn.setOnClickListener(clickLinstener);
	}
	
	private class MyOnClickLinstener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			//返回键处理
			if(v.getId() == R.id.deliverynote_detail_GoBackBtn){
				DeliveryNoteDetailActivity.this.finish();
			}
			//作废
			if(v.getId() == R.id.disuseBtn){
				showDialog();
			}
			//重开
			if(v.getId() == R.id.resetBtn){
//				resetSalerDialog();
				Intent intent = new Intent(DeliveryNoteDetailActivity.this, ShopCartAcitivity.class);
				Bundle bundle =new Bundle();
				//传递客户数据
				bundle.putSerializable("customer", deliveryNote.getCustomer());
				bundle.putSerializable("deliveryNote", deliveryNote);
				bundle.putSerializable("contacts", contact);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			
			//重新打印
			if(v.getId() == R.id.resetPrinterBtn){
				Intent intent = new Intent(DeliveryNoteDetailActivity.this, BluetoothPrinterActivity.class);
				Bundle bundle =  new Bundle();
				bundle.putSerializable("deliveryNote", (Serializable) deliveryNote);
				bundle.putSerializable("contacts", contact);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}
	//作废dialog
	private  void showDialog(){
		customDialog = null;
		int srceenW =  ((BaseActivity)this).getWindowManager().getDefaultDisplay().getWidth();
			//联系人对话框
		customDialog = new CommonDialog(DeliveryNoteDetailActivity.this,srceenW, R.layout.common_dialog_layout,R.style.custom_dialog);
		TextView tv_dialog_login_context = (TextView)customDialog.findViewById(R.id.tv_dialog_common_context);
		TextView tv_dialog_login_go = (TextView)customDialog.findViewById(R.id.tv_dialog_common_go);
		TextView tv_dialog_login_close = (TextView)customDialog.findViewById(R.id.tv_dialog_common_close);
		tv_dialog_login_context.setText("确认作废该单据！");
		tv_dialog_login_go.setText("坚决作废");
		customDialog.setCanceledOnTouchOutside(false);
			tv_dialog_login_go.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					final Intent intent = new Intent(DeliveryNoteDetailActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						@Override
						public void run() {
							notePresenter.doAbort(user,deliveryNote.getDelivery_id());
							startActivity(intent); //执行
						}
					};
					timer.schedule(task, 1000 * 1);
					customDialog.dismiss();
				}
			});
			tv_dialog_login_close.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					customDialog.dismiss();
				}
			});
			customDialog.show();
	}

	@Override
	public void addDeliveryNoteDetailProduct(
			List<DeliveryNoteGoods> newList) {
		// TODO Auto-generated method stub
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
			deliveryNote_settlement_summary.setText("共"+product_total_count+"件商品 总计¥"+deliveryNote.getTotalAmount()+"元");
			detailAdapter.notifyDataSetChanged();
		}else{
			showToast("暂无送货单产品详情记录");
		}
	}
	
	public void onDestroy(){
		if(notePresenter!=null)
			notePresenter.destroy();
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	finish();
        }
		return true;
	}
}
