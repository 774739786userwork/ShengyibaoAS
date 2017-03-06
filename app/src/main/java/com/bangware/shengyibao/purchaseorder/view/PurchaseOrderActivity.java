package com.bangware.shengyibao.purchaseorder.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.purchaseorder.adapter.PurchaseOrderQueryAdapter;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderPresenter;
import com.bangware.shengyibao.purchaseorder.presenter.impl.PurchaseOrderPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class PurchaseOrderActivity extends BaseActivity implements PurchaseOrderQueryView,OnRefreshListener {
    private ImageView purchasse_order_back;
    private RefreshListView purchase_order_ListView;
    private TextView purchaseStart_time,purchaseEnd_time,purchase_total_sum,purchase_deliverys_sum;//查询时间控件，总销售额，未付，送货单数
    private LinearLayout purchase_date_layout;
    private List<DeliveryNote> querylist = new ArrayList<DeliveryNote>();
    private PurchaseOrderQueryAdapter purchaseOrderQueryAdapter;
    private PurchaseOrderPresenter presenter;
    String begin_date;
    String end_date;
    private int show_type = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    String currenttime = sdf.format(new Date());
    private int nPage = 1;
    private int nSpage = 10;
    public int totalSize = 0;
    private int MaxDateNum;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findView();
        setListener();
    }

    private void setListener() {
        MyOnClickLinstener clickLinstener = new MyOnClickLinstener();
        purchasse_order_back.setOnClickListener(clickLinstener);
        purchase_date_layout.setOnClickListener(clickLinstener);

        //listview条目点击事件
        purchase_order_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(PurchaseOrderActivity.this, PurchaseOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                DeliveryNote deliveryNote = (DeliveryNote) adapterView.getItemAtPosition(position);
                bundle.putString("currenttime",currenttime);
                bundle.putSerializable("deliveryNote", deliveryNote);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        purchase_date_layout = (LinearLayout) findViewById(R.id.purchase_date_layout);
        purchaseStart_time = (TextView) findViewById(R.id.purchase_start_time);
        purchaseEnd_time = (TextView) findViewById(R.id.purchase_end_time);
        purchase_total_sum = (TextView) findViewById(R.id.purchase_total_sum);
        purchase_deliverys_sum= (TextView) findViewById(R.id.purchase_sum);
        purchasse_order_back = (ImageView) findViewById(R.id.purchasse_order_back);
        purchase_order_ListView = (RefreshListView) this.findViewById(R.id.purchase_order_ListView);
        begin_date=currenttime;
        end_date=currenttime;
        purchaseStart_time.setText(begin_date);
        purchaseEnd_time.setText(end_date);
        purchase_total_sum.setText("¥0");
        purchase_deliverys_sum.setText("0张");
        purchaseOrderQueryAdapter = new PurchaseOrderQueryAdapter(this, querylist);
        purchase_order_ListView.setDividerHeight(0);
        purchase_order_ListView.setAdapter(purchaseOrderQueryAdapter);
        presenter=new PurchaseOrderPresenterImpl(this);
        presenter.doLoad(user,begin_date,end_date,nPage,nSpage,show_type);
    }

    @Override
    public void loadPurchaseOrderQueryData(List<DeliveryNote> noteList) {
        if(noteList.size() > 0){
            querylist.addAll(noteList);
            for (int i = 0; i < querylist.size(); i++) {
                purchase_total_sum.setText("¥"+querylist.get(i).getD_total_sum());
                MaxDateNum = querylist.get(i).getTotal_record();
                purchase_deliverys_sum.setText(String.valueOf(querylist.get(i).getTotal_record())+"张");
            }
            purchaseOrderQueryAdapter.notifyDataSetChanged();
        }else{
            purchaseOrderQueryAdapter.notifyDataSetChanged();
            showOrderDialog();
        }
        purchase_order_ListView.hideFooterView();
        purchase_order_ListView.setOnRefreshListener(PurchaseOrderActivity.this);
    }

    private void showOrderDialog(){
        showDialog(this,"当日暂无订货单记录！",1,new Handler(){
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                if (msg.what == 1){
                    finish();
                }
            }
        });
    }

    @Override
    public void onDownPullRefresh() {
        // TODO Auto-generated method stub

    }
    @Override
    public void onLoadingMore() {
        nPage+=1;
        if(totalSize >= MaxDateNum){
//			showToast("暂无更多数据！请不要重复刷新！");
            purchase_order_ListView.hideFooterView();
            return;
        }else{
            presenter.doLoad(user,begin_date, end_date, nPage, nSpage,show_type);
        }
        totalSize += nSpage;
    }

    private class MyOnClickLinstener implements View.OnClickListener {
        Calendar c = Calendar.getInstance();
        @Override
        public void onClick(View v) {
            // 返回键
            if(v.getId() == R.id.purchasse_order_back){
                loadingdialog.dismiss();
                PurchaseOrderActivity.this.finish();
            }
            //时间选择控件
            if(v.getId() == R.id.purchase_date_layout){
                new DoubleDatePickerDialog(PurchaseOrderActivity.this,THEME_HOLO_LIGHT, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {

                        begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1, startDayOfMonth);
                        end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);

                        purchaseStart_time.setText(begin_date);
                        purchaseEnd_time.setText(end_date);
                        if(java.sql.Date.valueOf(begin_date).after(java.sql.Date.valueOf(end_date))){
                            purchase_total_sum.setText("¥0");
                            purchase_deliverys_sum.setText("0");
                            showToast("开始日期不能大于结束日期！");
                        }
                        else if(java.sql.Date.valueOf(end_date).after(java.sql.Date.valueOf(begin_date))){
                            purchase_total_sum.setText("¥0");
                            purchase_deliverys_sum.setText("0张");
//							showToast("结束日期不能大于开始日期！！");
                        }

                        querylist.clear();
                        nPage = 1;
                        totalSize = nSpage;
                        presenter.doLoad(user,begin_date, end_date, nPage, nSpage,show_type);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();

            }
        }

    }

    @Override
    protected void onDestroy() {
        if(presenter!=null){
            presenter.destroy();
        }
        super.onDestroy();
    }
}
