package com.bangware.shengyibao.returngood.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.RemarkPopupWindow;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.view.BluetoothPrinterActivity;
import com.bangware.shengyibao.net.NetWork;
import com.bangware.shengyibao.returngood.adapter.ReturnCartGoodsListAdapter;
import com.bangware.shengyibao.returngood.model.entity.CarBean;
import com.bangware.shengyibao.returngood.model.entity.ReturnCart;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.presenter.RefundPrestener;
import com.bangware.shengyibao.returngood.presenter.ReturnNoteDetailPresenter;
import com.bangware.shengyibao.returngood.presenter.impl.RefundPrestenerImpl;
import com.bangware.shengyibao.returngood.presenter.impl.ReturnNoteDetailPresenterImpl;
import com.bangware.shengyibao.shopcart.adapter.ShopCartGoodsListAdapter;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 退货确认界面
 * @author zcb
 *
 */
public class RefundActivity extends BaseActivity implements RefundView{
     private ImageButton mRefund_GoBackBtn;//回退按钮
     private TextView mRefund_Remark;//退货单弹出输入框
     private TextView mRefund_Customer_Id;//客户id
     private TextView mRefund_Customer_Contact;//客户联系人
     private TextView mRefund_Customer_Mobile;//客户电话号码
     private TextView mRefund_Customer_Name;//客户号码
     private TextView mRefund_Customer_Address;//客户地址
     private TextView mRefund_summary;//退款总计
//     private TextView mRefund_TotalAmount;//实际退款数
     private TextView mRefund_textview;//底部备注展示标题
     private TextView mRefund_RemarkText;//底部备注内容展示
     private TextView mRefund_BuBtn;//退款确认按钮
     private ListView mRefund_GoodsListView;//退款产品明细
	 private TextView mRefund_foregift;//总押金
	 private Contacts contacts;
	 private CarBean carBean;
	 private ReturnCart returnCart;
	 private ReturnNote returnNote;
	 private ReturnCartGoodsListAdapter mAdapter;
	 private RefundPrestener refundPrestener;
     private String return_reason;
	 private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_refund);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findView();
		setListener();
	}


	private void setListener() {
		// TODO Auto-generated method stub
		MyOnclickListener listener = new MyOnclickListener();
		mRefund_GoBackBtn.setOnClickListener(listener);
		mRefund_Remark.setOnClickListener(listener);
		mRefund_BuBtn.setOnClickListener(listener);

	}


	private void findView() {
		// TODO Auto-generated method stub
		mRefund_GoBackBtn=(ImageButton) findViewById(R.id.Refund_GoBackBtn);
		mRefund_Remark=(TextView) findViewById(R.id.Refund_Remark);
		mRefund_Customer_Id=(TextView) findViewById(R.id.Refund_Customer_Id);
		mRefund_Customer_Contact=(TextView) findViewById(R.id.Refund_Customer_Contact);
		mRefund_Customer_Mobile=(TextView) findViewById(R.id.Refund_Customer_Mobile);
		mRefund_Customer_Name=(TextView) findViewById(R.id.Refund_Customer_Name);
		mRefund_Customer_Address=(TextView) findViewById(R.id.Refund_Customer_Address);
		mRefund_summary=(TextView) findViewById(R.id.Refund_summary);
//		mRefund_TotalAmount=(TextView) findViewById(R.id.Refund_TotalAmount);
		mRefund_foregift= (TextView) findViewById(R.id.Refund_foregift);
		mRefund_textview=(TextView) findViewById(R.id.Refund_textview);
		mRefund_RemarkText=(TextView) findViewById(R.id.Refund_RemarkText);
		mRefund_BuBtn=(TextView) findViewById(R.id.Refund_BuBtn);
		mRefund_GoodsListView=(ListView) findViewById(R.id.Refund_GoodsListView);
		//接收上级页面发过来的客户对象
		contacts= (Contacts) getIntent().getSerializableExtra("contact");
		returnNote= (ReturnNote) getIntent().getSerializableExtra("returnNote");
		returnCart= (ReturnCart) getIntent().getSerializableExtra("returnCart");
		carBean= (CarBean) getIntent().getSerializableExtra("carbean");
		//给控件赋值
		mRefund_Customer_Id.setText(contacts.getCustomer().getId());
		mRefund_Customer_Name.setText(contacts.getCustomer().getName());
		mRefund_Customer_Address.setText(contacts.getCustomer().getAddress());
		mRefund_Customer_Contact.setText(contacts.getName());
		mRefund_Customer_Mobile.setText(contacts.getMobile1());

		double totalAmount = returnCart.getReturn_total_sum();
		final int totalVolumes = returnCart.getTotalVolumes();
		double totalForegift=returnCart.getTotalForeigft();

//		mRefund_TotalAmount.setText("¥"+ NumberUtils.toDoubleRound(totalAmount) +"元");
		mRefund_summary.setText("共"+totalVolumes+"件商品 总计:¥"+NumberUtils.toDoubleRound(totalAmount));
		mRefund_foregift.setText("含押金总额:¥"+String .valueOf(totalForegift));
		mRefund_BuBtn.setText("退款 ¥"+NumberUtils.toDoubleRound(totalAmount));

		refundPrestener = new RefundPrestenerImpl(this);
		mAdapter=new ReturnCartGoodsListAdapter(returnCart.getAllGoodsList(),this);
		mRefund_GoodsListView.setAdapter(mAdapter);

	}

	//自定义类实现点击事件接口
		private class MyOnclickListener implements View.OnClickListener{
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.Refund_GoBackBtn:
					finish();
					break;
				case R.id.Refund_Remark:
					Intent intent = new Intent(RefundActivity.this, RemarkPopupWindow.class);
					intent.putExtra("remark", return_reason);
					startActivityForResult(intent, 1000);
					break;
				case R.id.Refund_BuBtn:
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
					String currenttime = sdf.format(new Date());
					ReturnNote returnNote = returnCart.toReturnNote();
					returnNote.setReturn_total_sum(String.valueOf(returnCart.getReturn_total_sum()));
					returnNote.setReturntotalVolumes(returnCart.getTotalVolumes());
					returnNote.setReturn_date(currenttime);
					returnNote.setCarBean(carBean);
					Customer customer = contacts.getCustomer();
					returnNote.setCustomer(customer);
					return_reason = mRefund_RemarkText.getText().toString();
					returnNote.setReturn_reson(return_reason);
					if(null != returnNote){
						returnNote.setReturn_id(returnNote.getReturn_id());
					}
					if(return_reason.isEmpty() && return_reason.equals("")){
						showToast("请填写退货原因！");
						return ;
					}else {
						mRefund_BuBtn.setClickable(false);
						mRefund_BuBtn.setBackgroundColor(Color.GRAY);
						refundPrestener.doSave(user,returnNote);
					}
					break;
				}
			}
		}
		//弹出输入框后取内容的回调响应
		@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
	        super.onActivityResult(requestCode, resultCode, data);
	        if(requestCode == 1000 && resultCode == 1001)
	        {
	        	if(mRefund_textview.getText().toString().isEmpty() && mRefund_RemarkText.getText().toString().isEmpty()){
	        		mRefund_textview.setVisibility(View.GONE);
	        		mRefund_RemarkText.setVisibility(View.GONE);
	        	}else{
	        		mRefund_textview.setVisibility(View.VISIBLE);
	        		mRefund_RemarkText.setVisibility(View.VISIBLE);
	                return_reason = data.getStringExtra("remark");
	                mRefund_RemarkText.setText(return_reason);
	        	}
	        }
	    }

		@Override
		public void doSaveSuccess(ReturnNote returnNote) {
			Intent intent = new Intent(RefundActivity.this, ReturnBluetoothPrinterActivity.class);
			Bundle bundle =  new Bundle();
			bundle.putSerializable("returnNote",  (Serializable)returnNote);
			bundle.putSerializable("contacts", contacts);
			intent.putExtras(bundle);
			startActivity(intent);
		}
}
