package com.bangware.shengyibao.returngood.view;

import java.io.Serializable;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.main.view.FragmentSaler;
import com.bangware.shengyibao.main.view.MainActivity;
import com.bangware.shengyibao.returngood.adapter.ReturnGoodProductAdapter;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.presenter.ReturnNoteDetailPresenter;
import com.bangware.shengyibao.returngood.presenter.impl.ReturnNoteDetailPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bangware.shengyibao.utils.customdialog.CommonDialog;

/**
 * 退货产品展示
 * @author ccssll
 *
 */
public class ReturnGoodProductActivity extends BaseActivity implements ReturnGoodProductView {
	private ImageView product_back_image;
	private ListView  productListview;
	private ReturnGoodProductAdapter returnGoodAdapter;
	private TextView total_sum_money;
	private TextView mCustomerId,mCustomerName,mCustomerContact,mContactMobile,mCustomerAddress;
	private LinearLayout deleteBtn,returnGoodsBtn,resetPrinterBtn,bottom_linear;
	private ReturnNote returnNote = null;
	private List<ReturnNoteGoods> notegoodList = new ArrayList<ReturnNoteGoods>();
	private ReturnNoteDetailPresenter detailPresenter;
	private Contacts con = new Contacts();

	private CommonDialog customDialog;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_returngood_product);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findViews();
		setListener();
	}
	
	private void findViews() {
		// TODO Auto-generated method stub
		product_back_image = (ImageView) findViewById(R.id.returngood_product_back);
		productListview = (ListView) findViewById(R.id.returngood_productListView);
		total_sum_money = (TextView) findViewById(R.id.total_sum_money);
		deleteBtn = (LinearLayout) findViewById(R.id.deleteBtn);
		returnGoodsBtn = (LinearLayout) findViewById(R.id.returnGoodsBtn);
		resetPrinterBtn = (LinearLayout) findViewById(R.id.return_resetBtn);
		bottom_linear= (LinearLayout) findViewById(R.id._bottom_linear);
		mCustomerId = (TextView)findViewById(R.id.returnNote_detail_Customer_Id);
		mCustomerName = (TextView)findViewById(R.id.returnNote_detail_Customer_Name);
		mCustomerContact = (TextView)findViewById(R.id.returnNote_detail_Customer_Contact);
		mContactMobile = (TextView)findViewById(R.id.returnNote_detail_Customer_Mobile);
		mCustomerAddress = (TextView)findViewById(R.id.returnNote_detail_Customer_Address);

		returnNote = (ReturnNote) getIntent().getExtras().getSerializable("returnNote");
		Customer customer = returnNote.getCustomer();
		mCustomerId.setText(customer.getId());
		mCustomerName.setText(customer.getName());
		if (returnNote.getCustomer().getContacts().size()>0) {
			mCustomerContact.setText(customer.getContacts().get(0).getName());
			mContactMobile.setText(customer.getContacts().get(0).getMobile1());
		}
		mCustomerAddress.setText(customer.getAddress());

		detailPresenter = new ReturnNoteDetailPresenterImpl(this);
		detailPresenter.doLoadReturnDetail(user,returnNote.getReturn_id());
		returnGoodAdapter = new ReturnGoodProductAdapter(this, notegoodList);
		productListview.setAdapter(returnGoodAdapter);

		String date1 = getIntent().getExtras().getString("currenttime");
		String date2=returnNote.getReturn_date();
		int result=date1.compareTo(date2);
		if (result!=0) {
			bottom_linear.setVisibility(View.GONE);
		}
		
	}
	
	private void setListener() {
		// TODO Auto-generated method stub
		MyOnClickListener onClickListener = new MyOnClickListener();
		product_back_image.setOnClickListener(onClickListener);
		deleteBtn.setOnClickListener(onClickListener);
		returnGoodsBtn.setOnClickListener(onClickListener);
		resetPrinterBtn.setOnClickListener(onClickListener);
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//返回键
			if(v.getId() == R.id.returngood_product_back){
				ReturnGoodProductActivity.this.finish();
			}
			//作废
			if(v.getId() == R.id.deleteBtn){
				showDialog();
			}
			//重开退货
			if(v.getId() == R.id.returnGoodsBtn){
				resetBillingDialog();
			}
			//重新打印
			if(v.getId() == R.id.return_resetBtn){
				Intent intent = new Intent(ReturnGoodProductActivity.this, ReturnBluetoothPrinterActivity.class);
				Bundle bundle =  new Bundle();
				if (returnNote.getCustomer().getContacts().size()>0) {
					con.setName(returnNote.getCustomer().getContacts().get(0).getName());
					con.setMobile1(returnNote.getCustomer().getContacts().get(0).getMobile1());
				}
				con.setCustomer(returnNote.getCustomer());
				bundle.putSerializable("contacts", con);
				bundle.putSerializable("returnNote",  (Serializable)returnNote);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}

	//作废dialog
	private void showDialog(){
		customDialog = null;
		int srceenW =  ((BaseActivity)this).getWindowManager().getDefaultDisplay().getWidth();
		//联系人对话框
		customDialog = new CommonDialog(ReturnGoodProductActivity.this,srceenW, R.layout.common_dialog_layout,R.style.custom_dialog);
		TextView tv_dialog_login_context = (TextView)customDialog.findViewById(R.id.tv_dialog_common_context);
		TextView tv_dialog_login_go = (TextView)customDialog.findViewById(R.id.tv_dialog_common_go);
		TextView tv_dialog_login_close = (TextView)customDialog.findViewById(R.id.tv_dialog_common_close);
		tv_dialog_login_context.setText("确认作废该单据！");
		tv_dialog_login_go.setText("坚决作废");
		customDialog.setCanceledOnTouchOutside(false);
		tv_dialog_login_go.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Intent intent = new Intent(ReturnGoodProductActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						detailPresenter.doAbort(user,returnNote.getReturn_id());
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

	//重新开单dialog
	private void resetBillingDialog(){
		customDialog = null;
		int srceenW =  ((BaseActivity)this).getWindowManager().getDefaultDisplay().getWidth();
		//联系人对话框
		customDialog = new CommonDialog(ReturnGoodProductActivity.this,srceenW, R.layout.common_dialog_layout,R.style.custom_dialog);
		TextView tv_dialog_login_context = (TextView)customDialog.findViewById(R.id.tv_dialog_common_context);
		TextView tv_dialog_login_go = (TextView)customDialog.findViewById(R.id.tv_dialog_common_go);
		TextView tv_dialog_login_close = (TextView)customDialog.findViewById(R.id.tv_dialog_common_close);
		tv_dialog_login_context.setText("重开退货将作废本单据！");
		tv_dialog_login_go.setText("坚决重开");
		customDialog.setCanceledOnTouchOutside(false);
		tv_dialog_login_go.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Intent intent = new Intent(ReturnGoodProductActivity.this, ReturnsProcessingActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						Bundle bundle = new Bundle();
						if (returnNote.getCustomer().getContacts().size()>0) {
							con.setName(returnNote.getCustomer().getContacts().get(0).getName());
							con.setMobile1(returnNote.getCustomer().getContacts().get(0).getMobile1());
						}
						con.setCustomer(returnNote.getCustomer());
						bundle.putSerializable("contacts", con);
						bundle.putSerializable("returnNote", returnNote);
						intent.putExtras(bundle);
						detailPresenter.doAbort(user,returnNote.getReturn_id());
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

		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("重新开单将作废本单据！");
//		builder.setIcon(R.mipmap.ic_launcher);
		builder.setCancelable(false);

		builder.setPositiveButton("确定！", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final Intent intent = new Intent(ReturnGoodProductActivity.this, ReturnsProcessingActivity.class);
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						Bundle bundle = new Bundle();
						if (returnNote.getCustomer().getContacts().size()>0) {
							con.setName(returnNote.getCustomer().getContacts().get(0).getName());
							con.setMobile1(returnNote.getCustomer().getContacts().get(0).getMobile1());
						}
						con.setCustomer(returnNote.getCustomer());
						bundle.putSerializable("contacts", con);
						bundle.putSerializable("returnNote", returnNote);
						intent.putExtras(bundle);
						detailPresenter.doAbort(returnNote.getReturn_id());
						startActivity(intent); //执行
					}
				};
				timer.schedule(task, 1000 * 1);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();*/
	}

	@Override
	public void queryReturnNoteProduct(List<ReturnNoteGoods> noteGoodsList) {
		// TODO Auto-generated method stub
		int productQuantity = 0;
		double productTotalSum = 0;
		if(noteGoodsList.size() > 0){
			notegoodList.clear();
			notegoodList.addAll(noteGoodsList);
			returnNote.setGoodslist(notegoodList);

			for (ReturnNoteGoods goods: notegoodList) {
				productQuantity += goods.getTotalVolume();
				productTotalSum += goods.getReturnsAmount();
			}
			returnNote.setReturntotalVolumes(productQuantity);
			returnNote.setReturn_total_sum(String.valueOf(productTotalSum));
			total_sum_money.setText("共退货"+productQuantity+"件商品  总计¥"+ NumberUtils.toDoubleDecimal(productTotalSum)+"元   其中押金¥"+returnNote.getTotalForeigft()+"元");
			returnGoodAdapter.notifyDataSetChanged();
		}else{
			showToast("暂无退货产品！");
		}
	}


	public void onDestroy(){
		if(detailPresenter!=null)
			detailPresenter.destroy();
		super.onDestroy();
	}

	}
