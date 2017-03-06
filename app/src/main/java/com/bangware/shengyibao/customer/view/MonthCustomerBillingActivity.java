package com.bangware.shengyibao.customer.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.CustomProgressDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.adapter.MonthCustomerBillingAdapter;
import com.bangware.shengyibao.customer.adapter.LeftMoreAdapter;
import com.bangware.shengyibao.customer.adapter.OrderConditionSortAdapter;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.presenter.CustomerBillingMonthPresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerBillingMonthPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

/**
 * 主页月开单客户查询
 * @author ccssll
 *
 */
public class MonthCustomerBillingActivity extends BaseActivity implements OnRefreshListener,CustomerBillingMonthSalerRecordView{
	private ImageView Billinglist_back,Search_billingcustomer_img;
	private TextView Sortlist_title_textbtn; //右侧默认排序头部标题设置
	private boolean rightToplistview = false;//右侧头部选择
	private ListView orderCondition_sortList;//右侧头部列表内容
	private OrderConditionSortAdapter sortAdapter = null;
	public static String[] ORDER_CONDITION_SORT = new String[]{"进货时间", "进货次数"}; //,"进货金额"

	private TextView BillingList_title_txt;			//左侧客户条件查询头部标题设置
	private LinearLayout BillingList_Customertype;//左侧头部选择切换
	private boolean leftToplistview = false;//左侧头部选择
	private ListView Billing_customerlist_toplist;	//左侧头部列表内容
	private LeftMoreAdapter topadapter = null;//左侧头部切换数据适配
	private MonthCustomerBillingAdapter billingAdapter = null;//页面内容数据适配
	private RefreshListView BillingCustomer_ListView;//页面列表内容
	private List<Customer> mainCustomerBillingList = new ArrayList<Customer>();
	private int nPage = 1;
	private int nSpage = 30;
	private static int MaxNumer;
	private static int billingCustomerSum;
	private static int nobillingCustomer;
	public int totalSize = 0;
	private CustomerBillingMonthPresenter billingMonthPresenter;

	public static String[] CUSTOMERLIST_TOPLIST;
	private int positionTemp = 0;
	private String sortListBillingStr = "";

	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_monthbilling_customer);

		SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findViews();
		setListeners();
	}

	//控件初始化绑定
	private void findViews() {
		// TODO Auto-generated method stub
		Billinglist_back = (ImageView) findViewById(R.id.Billinglist_back);
		Search_billingcustomer_img = (ImageView) findViewById(R.id.Search_billingcustomer_img);
		BillingList_Customertype = (LinearLayout) findViewById(R.id.BillingList_Customertype);
		BillingList_title_txt = (TextView) findViewById(R.id.BillingList_title_txt);
		Sortlist_title_textbtn = (TextView)findViewById(R.id.Sortlist_title_textbtn);
		BillingCustomer_ListView = (RefreshListView) findViewById(R.id.BillingCustomer_ListView);
		Billing_customerlist_toplist = (ListView) findViewById(R.id.Billing_customerlist_toplist);
		orderCondition_sortList = (ListView) findViewById(R.id.orderCondition_sortList);

		Bundle bundle = this.getIntent().getExtras();
		MaxNumer = bundle.getInt("customerQuantity");
		billingCustomerSum = bundle.getInt("billingcustomerQuantity");
		nobillingCustomer = bundle.getInt("nobillingCustomer");

		CUSTOMERLIST_TOPLIST= new String[] {"我送过货的客户"+MaxNumer,"本月未开单客户"+nobillingCustomer,"本月已开单客户"+billingCustomerSum};
		//默认加载我送过货的客户
		BillingList_title_txt.setText(CUSTOMERLIST_TOPLIST[positionTemp]);
		billingMonthPresenter = new CustomerBillingMonthPresenterImpl(this);
		billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"");
		billingAdapter = new MonthCustomerBillingAdapter(MonthCustomerBillingActivity.this,mainCustomerBillingList);
		BillingCustomer_ListView.setAdapter(billingAdapter);

		//设置左侧列表数据
		topadapter = new LeftMoreAdapter(MonthCustomerBillingActivity.this,CUSTOMERLIST_TOPLIST,R.layout.search_more_list);
		Billing_customerlist_toplist.setAdapter(topadapter);

		sortAdapter = new OrderConditionSortAdapter(MonthCustomerBillingActivity.this,ORDER_CONDITION_SORT,R.layout.item_sort_more_list);
		orderCondition_sortList.setAdapter(sortAdapter);
	}

	//控件点击监听事件绑定
	private void setListeners() {
		// TODO Auto-generated method stub
		MyOnClickListener clickListener = new MyOnClickListener();
		Billinglist_back.setOnClickListener(clickListener);
		Search_billingcustomer_img.setOnClickListener(clickListener);
		BillingList_Customertype.setOnClickListener(clickListener);
		Sortlist_title_textbtn.setOnClickListener(clickListener);

		TopListOnItemclick topListOnItemclick = new TopListOnItemclick();
		Billing_customerlist_toplist.setOnItemClickListener(topListOnItemclick);//左侧列表选中查询item点击

		RightTopListOnItemclick rightTopListOnItemclick = new RightTopListOnItemclick();
		orderCondition_sortList.setOnItemClickListener(rightTopListOnItemclick);//右侧列表选中查询item点击
		//列表区域内容item点击
		BillingCustomer_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MonthCustomerBillingActivity.this, CustomerInfoActivity.class);
				//用Bundle传递数据
				Customer customer = (Customer)adapterView.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	//左头部条目点击事件绑定
	private class TopListOnItemclick implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			positionTemp = position;
			topadapter.setSelectItem(positionTemp);
			Search_billingcustomer_img.setImageResource(R.drawable.ic_arrow_down_black);
			BillingList_title_txt.setText(CUSTOMERLIST_TOPLIST[positionTemp]);
			Billing_customerlist_toplist.setVisibility(View.GONE);
			leftToplistview = false;
			switch (positionTemp){
				case 0://我送货客户
					mainCustomerBillingList.clear();
					nPage = 1;
					totalSize = nSpage;
					billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"");
					billingAdapter.notifyDataSetInvalidated();
					break;
				case 1://本月未开单客户
					mainCustomerBillingList.clear();
					nPage = 1;
					totalSize = nSpage;
					billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 3,"");
					billingAdapter.notifyDataSetInvalidated();
					break;
				case 2://本月已开单客户
					mainCustomerBillingList.clear();
					nPage = 1;
					totalSize = nSpage;
					billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 2,"total_sum");
					billingAdapter.notifyDataSetInvalidated();
					break;
				default:
					break;
			}
		}
	}

	//右侧头部条目点击事件绑定
	private class RightTopListOnItemclick implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			positionTemp = 3;
			sortAdapter.setSelectItem(position);
			Sortlist_title_textbtn.setText(ORDER_CONDITION_SORT[position]);
			sortListBillingStr = Sortlist_title_textbtn.getText().toString();
			Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
			Sortlist_title_textbtn.setCompoundDrawables(null, null, drawable,null);
			orderCondition_sortList.setVisibility(View.GONE);
			rightToplistview = false;

			if(position == 0){
				//进货时间排序
				mainCustomerBillingList.clear();
				nPage = 1;
				totalSize = nSpage;
				billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"delivery_date");
				billingAdapter.notifyDataSetInvalidated();
			}else if(position == 1){
				//进货次数排序
				mainCustomerBillingList.clear();
				nPage = 1;
				totalSize = nSpage;
				billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"count");
				billingAdapter.notifyDataSetInvalidated();
			}/*else if(position == 2){
				//进货金额排序
				mainCustomerBillingList.clear();
				nPage = 1;
				totalSize = nSpage;
				billingMonthPresenter.loadCustomerBillingMonthData(nPage, nSpage, 1,"total_sum");
				billingAdapter.notifyDataSetInvalidated();
			}*/
		}
	}

	//各控件的点击事件绑定
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int mID = v.getId();
			//返回按钮
			if(mID == R.id.Billinglist_back){
				MonthCustomerBillingActivity.this.finish();
			}
			//头部切换按钮
			if(mID == R.id.BillingList_Customertype){
				if(!leftToplistview){
					Search_billingcustomer_img.setImageResource(R.drawable.ic_arrow_up_black);
					Billing_customerlist_toplist.setVisibility(View.VISIBLE);
					topadapter.notifyDataSetChanged();
					leftToplistview = true;
				} else {
					Search_billingcustomer_img.setImageResource(R.drawable.ic_arrow_down_black);
					Billing_customerlist_toplist.setVisibility(View.GONE);
					leftToplistview = false;
				}
			}else {
				Search_billingcustomer_img.setImageResource(R.drawable.ic_arrow_down_black);
				Billing_customerlist_toplist.setVisibility(View.GONE);
				leftToplistview = false;
			}
			if (mID == R.id.Sortlist_title_textbtn){
				Drawable drawable = null;
				if(!rightToplistview){
					drawable = getResources().getDrawable(R.drawable.ic_arrow_up_black);
					orderCondition_sortList.setVisibility(View.VISIBLE);
					sortAdapter.notifyDataSetChanged();
					rightToplistview = true;
				} else {
					drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
					orderCondition_sortList.setVisibility(View.GONE);
					rightToplistview = false;
				}
				//动态设置textview drawableRight属性
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//设置边界
				Sortlist_title_textbtn.setCompoundDrawables(null, null, drawable, null);//设置图片在文字右边
			} else {
				Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				Sortlist_title_textbtn.setCompoundDrawables(null, null,
						drawable, null);
				orderCondition_sortList.setVisibility(View.GONE);
				rightToplistview = false;
			}
		}
	}

	//手机屏幕返回手指按下事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(leftToplistview == true){
				Search_billingcustomer_img.setImageResource(R.drawable.ic_arrow_down_black);
				Billing_customerlist_toplist.setVisibility(View.GONE);
				leftToplistview = false;
			}else {
				MonthCustomerBillingActivity.this.finish();
			}

		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(leftToplistview == true){
				Search_billingcustomer_img.setImageResource(R.drawable.search_customer);
				Billing_customerlist_toplist.setVisibility(View.GONE);
				leftToplistview = false;
			}
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		nPage+=1;
		if(totalSize >= MaxNumer){
			BillingCustomer_ListView.hideFooterView();
			return;
		}else{
			switch (positionTemp){
				case 0://我送货客户
					billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"");
					break;
				case 1://本月未开单客户
					billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 3,"");
					break;
				case 2://本月已开单客户
					billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 2,"total_sum");
					break;
				case 3:
					if(sortListBillingStr.equals(ORDER_CONDITION_SORT[0])){
						billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"delivery_date");
					}else if(sortListBillingStr.equals(ORDER_CONDITION_SORT[1])){
						billingMonthPresenter.loadCustomerBillingMonthData(user,nPage, nSpage, 1,"count");
					}/*else if(sortListBillingStr.equals(ORDER_CONDITION_SORT[2])){
						billingMonthPresenter.loadCustomerBillingMonthData(nPage, nSpage, 1,"total_sum");
					}*/
					break;
				default:
					break;
			}
		}
		totalSize += nSpage;
	}

	@Override
	public void queryCustomerSalerRecord(List<Customer> customerList) {
		// TODO Auto-generated method stub
		if(customerList.size() > 0){
			mainCustomerBillingList.addAll(customerList);
			billingAdapter.notifyDataSetChanged();
		}else{
			billingAdapter.notifyDataSetChanged();
			showToast("暂无更多数据");
		}
		BillingCustomer_ListView.hideFooterView();
		BillingCustomer_ListView.setOnRefreshListener(MonthCustomerBillingActivity.this);
	}

	@Override
	public void showLoadFailureMsg(String errorMessage) {
		// TODO Auto-generated method stub

	}

	public void onDestroy(){
		super.onDestroy();
		if(this.billingMonthPresenter!=null)
			this.billingMonthPresenter.destroy();
	}
}
