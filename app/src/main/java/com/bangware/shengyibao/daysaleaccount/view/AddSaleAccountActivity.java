package com.bangware.shengyibao.daysaleaccount.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.daysaleaccount.adapter.MyBaseExpandableListAdapter;
import com.bangware.shengyibao.daysaleaccount.model.entity.ProductInfoItemBean;
import com.bangware.shengyibao.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 新建销售清单ui界面
 */
public class AddSaleAccountActivity extends BaseActivity {
    private ImageView back_img;
    private TextView start_date_view;
    private RelativeLayout click_relLatyout,againGet_rlatlayout;
    private ExpandableListView sale_expanlist;
    private List<String> groupData;//group的数据源
    private Map<Integer, List<ProductInfoItemBean>> childData;//child的数据源
    private MyBaseExpandableListAdapter saleAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    String currenttime = sdf.format(new Date());
    private String begin_date;
    private DatePickerDialog dpd=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale_account);

        begin_date=currenttime;
        findView();
        setListener();
    }

    public void findView(){
        back_img = (ImageView) findViewById(R.id.back_imageView);
        start_date_view = (TextView) findViewById(R.id.start_date_text);
        click_relLatyout = (RelativeLayout) findViewById(R.id.click_relLatyout);
        againGet_rlatlayout = (RelativeLayout) findViewById(R.id.againGet_rlatlayout);
        sale_expanlist = (ExpandableListView) findViewById(R.id.saleExpandlist);

        if(dpd==null){
            dpd_init();
        }
        dpd.show();
    }

    public void setListener(){
        //返回键
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //时间选择
        click_relLatyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dpd==null){
                    dpd_init();
                }
                dpd.show();
            }
        });

        //重新获取
        againGet_rlatlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sale_expanlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                childData.get(groupPosition).get(childPosition).setSaleperson_amount("111111");
                showToast("点击的是:"+childData.get(groupPosition).get(childPosition).getSaleperson_amount());
                Intent intent = new Intent(AddSaleAccountActivity.this,ChoiceSalerPersonActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    /*单日期初始化*/
    private void dpd_init(){
        DatePickerDialog.OnDateSetListener otsl=new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                begin_date = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                start_date_view.setText(begin_date);
                initDatas();
                dpd.dismiss();
            }
        };
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        dpd=new DatePickerDialog(this,otsl,year,month,day);
    }

    /**
     * group和child子项的数据源
     */
    private void initDatas() {

        groupData = new ArrayList<String>();
        groupData.add("多帮正品大桶胶");
        groupData.add("外墙内耐水");
        groupData.add("多邦净味火炼胶10KG");
        groupData.add("小桶108胶水");
        groupData.add("森林氧吧（大）");
        groupData.add("火炼胶");

        List<ProductInfoItemBean> childItems = new ArrayList<ProductInfoItemBean>();

        Product product = new Product();
        product.setStock(0);
        product.setPrice(8.00);
        product.setForegift(15.0);
        ProductInfoItemBean childData1 = new ProductInfoItemBean("刘鹏","100",200,product,50,10);
        childItems.add(childData1);

        List<ProductInfoItemBean> childItems2 = new ArrayList<ProductInfoItemBean>();
        ProductInfoItemBean childData4 = new ProductInfoItemBean("刘鹏","100",200,product,50,10);
        childItems2.add(childData4);

        List<ProductInfoItemBean> childItems3 = new ArrayList<ProductInfoItemBean>();
        ProductInfoItemBean childData5 = new ProductInfoItemBean("刘鹏","100",200,product,50,10);
        childItems3.add(childData5);

        List<ProductInfoItemBean> childItems4 = new ArrayList<ProductInfoItemBean>();
        ProductInfoItemBean childData6 = new ProductInfoItemBean("刘鹏","100",200,product,50,10);
        childItems4.add(childData6);

        List<ProductInfoItemBean> childItems5 = new ArrayList<ProductInfoItemBean>();
        ProductInfoItemBean childData7 = new ProductInfoItemBean("刘鹏","100",200,product,50,10);
        childItems5.add(childData7);

        List<ProductInfoItemBean> childItems6 = new ArrayList<ProductInfoItemBean>();
        ProductInfoItemBean childData8 = new ProductInfoItemBean("刘鹏","100",200,product,50,10);
        childItems6.add(childData8);

        childData = new HashMap<Integer, List<ProductInfoItemBean>>();
        childData.put(0, childItems);
        childData.put(1, childItems2);
        childData.put(2, childItems3);
        childData.put(3, childItems4);
        childData.put(4, childItems5);
        childData.put(5, childItems6);

        saleAdapter = new MyBaseExpandableListAdapter(this, groupData, childData);
        sale_expanlist.setGroupIndicator(null);//这里不显示系统默认的group indicator
        sale_expanlist.setAdapter(saleAdapter);
        registerForContextMenu(sale_expanlist);//给ExpandListView添加上下文菜单
    }
}
