package com.bangware.shengyibao.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.CustomProgressDialog;
import com.bangware.shengyibao.customercontacts.view.QueryQuickBilingActivity;
import com.bangware.shengyibao.deliverynote.view.DeliveryNoteQueryActivity;
import com.bangware.shengyibao.ladingbilling.view.LadingbillingQueryActivity;
import com.bangware.shengyibao.main.model.entity.GridViewItem;
import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;
import com.bangware.shengyibao.main.presenter.MainPresenter;
import com.bangware.shengyibao.main.presenter.impl.MainPresenterImpl;
import com.bangware.shengyibao.manager.ScreenObserverManager;
import com.bangware.shengyibao.purchaseorder.view.PurchaseOrderActivity;
import com.bangware.shengyibao.returngood.view.ChoiceContactActivity;
import com.bangware.shengyibao.returngood.view.ReturngoodQueryActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 销售页面
 * @author luming.tang
 *
 */
public class FragmentSaler extends Fragment implements MainView
{
	private Intent intent;
	private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
	protected CustomProgressDialog loadingdialog;
    private List<GridViewItem> griditems = null;
    private MainPresenter presenter;
    private ScreenObserverManager observerManager;
    private TextView todaySaleDate = null;
    private TextView todaySalerMoney = null;
    private TextView monthSalerTop = null;
    private TextView saler_person = null;
	private TextView orgName = null;
    public static String Date=null;
	private View view = null;
	private User user;
	private SharedPreferences sharedPreferences;
    /**
     * 加载GridView的Item数据
     * todo:此处可能需要动态加载
     */
    private void loadGridItemListData(){
    	griditems = new ArrayList<GridViewItem>(9);
		griditems.add(new GridViewItem(R.drawable.home_item_invsa_order_n,"提货单",""));
		griditems.add(new GridViewItem(R.drawable.home_item_invsa_bill_n,"送货单",""));
		griditems.add(new GridViewItem(R.drawable.home_item_invsa_order_n,"订货单",""));
		griditems.add(new GridViewItem(R.drawable.home_item_invpu_bill_n,"销货单",""));
		griditems.add(new GridViewItem(R.drawable.home_item_invpu_order_n,"退货处理单",""));
		griditems.add(new GridViewItem(R.drawable.home_item_invpu_return_n,"退货单",""));
		griditems.add(new GridViewItem(R.drawable.home_item_invpu_goods_n,"卸货单",""));
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		if (view == null){
			view = inflater.inflate(R.layout.fragment_saler, container, false);
			initView(view);
		}
		if(view!=null)
		{
			return view;
		}
		return super.onCreateView(inflater, container, savedInstanceState);

    }

	private void findById(){
		todaySaleDate = (TextView) getView().findViewById(R.id.sale_date);
		todaySalerMoney = (TextView) getView().findViewById(R.id.today_sale_sum);

		monthSalerTop = (TextView) getView().findViewById(R.id.thismonth_saler_top);

		//获取业务员姓名
		saler_person = (TextView) getView().findViewById(R.id.saler_person);
		saler_person.setText(user.getUser_realname());

		orgName = (TextView)getView().findViewById(R.id.orgNameText);
		orgName.setText(user.getOrg_name());
		loadingdialog = CustomProgressDialog.createDialog(getActivity(),R.drawable.frame);
		loadingdialog.show();
		//初始化Presenter
		presenter=new MainPresenterImpl(this);
		if(presenter != null){
			presenter.loadThisMonthSummary(user);
			presenter.loadToDaySummary(user);
		}

		//锁屏监听回调方法
		observerManager = new ScreenObserverManager(getActivity());
		observerManager.requestScreenStateUpdate(new ScreenObserverManager.ScreenStateListener() {

			//屏幕开启状态
			@Override
			public void onScreenOn() {
				loadingdialog.show();
				presenter.loadThisMonthSummary(user);
				presenter.loadToDaySummary(user);
			}

			//屏幕锁屏状态
			@Override
			public void onScreenOff() {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

		sharedPreferences = getActivity().getSharedPreferences(User.SHARED_NAME,Context.MODE_PRIVATE);
		user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
		findById();
    }

    private void initView(View view) {
    	
    	loadGridItemListData();
 		gview = (GridView) view.findViewById(R.id.gview);
 		data_list = new ArrayList<Map<String, Object>>();
        data_list = getData();
        String[] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(view.getContext(), data_list, R.layout.fragment_saler_menu_item, from, to);

        //配置适配器
        gview.setAdapter(sim_adapter);
		gview.setOnItemClickListener(new ItemClickListener());
 	}
 	public List<Map<String, Object>> getData(){
 		int itemsize = griditems.size();
         for(int i=0;i<itemsize;i++){
             Map<String, Object> map = new HashMap<String, Object>();
             map.put("image", griditems.get(i).getImage());
             map.put("text", griditems.get(i).getText());
             data_list.add(map);
         }
         return data_list;
     }

	@Override
	public void onDestroy() {
		super.onDestroy();
		//停止监听screen状态
		if(presenter!=null){
			observerManager.stopScreenStateUpdate();
			presenter.destroy();
		}
	}

	/**
	 * GridView中Item的点击事件处理
	 * @author luming.tang
	 *
	 */
	class  ItemClickListener implements OnItemClickListener
	{     
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long row)
		{     
		    HashMap<String, Object> item=(HashMap<String, Object>) adapterView.getItemAtPosition(position);
			//各页面的跳转
		    String name = (String)item.get("text");
		    Context ctx = view.getContext();
		    switch (position) {
				case 0:
					//提货单
					intent = new Intent(ctx, LadingbillingQueryActivity.class);
				    startActivity(intent);
					break;
				case 1:
					//送货单
					intent = new Intent(ctx,DeliveryNoteQueryActivity.class);
				    startActivity(intent);
					break;
				case 2:
					//订货单
					intent=new Intent(ctx, PurchaseOrderActivity.class);
					ctx.startActivity(intent);
					break;
				case 3:
					//快速开单
					intent = new Intent(ctx, QueryQuickBilingActivity.class);
					ctx.startActivity(intent);
					break;
				case 4:
					//退货处理DebtOwedMainActivity ShopTypePositionActivity
					intent=new Intent(ctx, ChoiceContactActivity.class);
					ctx.startActivity(intent);
					break;
				case 5:
					//退货单
					intent = new Intent(ctx, ReturngoodQueryActivity.class);
					ctx.startActivity(intent);
					break;
				case 6:
					//卸货单
					/*intent=new Intent(ctx, HelloActivity.class);
					ctx.startActivity(intent);*/
					break;
				default:
					break;
			}
		}     
	}

	@Override
	public void showLoading(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hideLoading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void showMessage(String errorMessage) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
	}

	@Override
	public void doTodaySummaryLoadSuccess(ToDaySummary bean) {
		// TODO Auto-generated method stub
		if(bean != null){
			loadingdialog.dismiss();
			Date=bean.getTodaytime();
			todaySaleDate.setText(Date);  //当天时间
			todaySalerMoney.setText("日销售金额：¥"+bean.getTodaysaler()+"元");//当天销售额
		}
		else{
			Toast.makeText(getActivity(), "暂无数据！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void doThisMonthSummaryLoadSuccess(ThisMonthSummary bean) {
		// TODO Auto-generated method stub
		if(bean != null){
			monthSalerTop.setText("月销售排名："+bean.getMonthtop());      //本月的销售排名
		}
		else{
			Toast.makeText(getActivity(), "暂无数据！", Toast.LENGTH_SHORT).show();
		}
	}

}
