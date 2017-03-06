package com.bangware.shengyibao.customer.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

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
import com.bangware.shengyibao.customer.adapter.AreaOneAdapter;
import com.bangware.shengyibao.customer.adapter.AreaTwoAdapter;
import com.bangware.shengyibao.customer.adapter.CustomerAdapter;
import com.bangware.shengyibao.customer.adapter.NearByCustomerAdapter;
import com.bangware.shengyibao.customer.adapter.OrderConditionSortAdapter;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.DistanceType;
import com.bangware.shengyibao.customer.model.entity.RegionalArea;
import com.bangware.shengyibao.customer.presenter.CustomerPresenter;
import com.bangware.shengyibao.customer.presenter.RegionalAreaPresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerPresenterImpl;
import com.bangware.shengyibao.customer.presenter.impl.RegionalAreaPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.ClearEditText;
import com.bangware.shengyibao.view.OnRefreshListener;
import com.bangware.shengyibao.view.RefreshListView;

public class CustomerActivity extends BaseActivity implements OnRefreshListener, CustomerView, RegionalAreaView {
    private RefreshListView lv;//列表
    private ImageView Cuslist_back;
    private TextView queryBtn, Arealist_title_textbtn, DefaultSortlist_title_textbtn, nearby_title_textbtn;//行政区域、默认排序、附近查询title
    private ImageView contactphonecall;//通讯录按钮
    private String username,usernumber;//声明变量已便于接收调取通讯录联系人号码和姓名

    private TextView distance_titleId;
    private String lat = "";
    private String lng = "";
    private String province = "";
    private CustomerAdapter adapter = null;
    private int nPage = 1;
    private int nSpage = 10;
    private int pageSize;
    public int totalSize = 0;
    //自定义输入框类
    private ClearEditText mClearEditText;
    //汉字转换成拼音的类
    private List<Customer> customerlist = new ArrayList<Customer>();
    private String mobile = "";
    private String shopName = "";
    private CustomerPresenter customerPresenter;
    private boolean arealistview = false;//区域选择图标切换
    private LinearLayout Arealist_mainlist;//全部地区点击选择
    private ListView Arealist_onelist;//左边第一个listview
    private ListView Arealist_twolist;//左边listview的子listview
    private AreaOneAdapter areaAdapter;
    private AreaTwoAdapter twoAdapter;
    private int newPosition = 0;//position位置标识
    /*附近客户变量**/
    public static String[] NEARBY_DISTANCE = new String[]{"附近500米", "附近1000米", "附近2000米", "附近5000米"};
    private boolean nearbylistview = false;//附近距离选择图标
    private ListView nearby_customer_toplist;//附近客户listview
    private NearByCustomerAdapter nearbyAdapter = null;//附近客户数据源展示

    /*行政区域**/
    private List<RegionalArea> areaList = new ArrayList<RegionalArea>();
    private DistanceType[][] areaListNew = null;
    private RegionalAreaPresenter areaPresenter = null;

    /*默认排序*/
    public static String[] ORDER_CONDITION_SORT = new String[]{"进货时间", "进货次数"};
    private boolean sort_toplist = false;
    private ListView orderSort_listview;//默认排序选择listview
    private OrderConditionSortAdapter sortAdapter;
    private String sortListStr = "";

    //地理定位
    private LocationClient mLocClient;
    private MyLocationData locData;
    public  MyLocationListenner myListener = new MyLocationListenner();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; // 是否首次定位

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除页面标题
        setContentView(R.layout.activity_customer);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findView();
        setListener();
    }

    //初始化控件
    private void findView() {
        //加载布局控件
        lv = (RefreshListView) findViewById(R.id.CustomerListView);
        Cuslist_back = (ImageView) findViewById(R.id.Cuslist_back);
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        queryBtn = (TextView) findViewById(R.id.queryBtn);
        contactphonecall = (ImageView) findViewById(R.id.contactphonecall);
        Arealist_title_textbtn = (TextView) findViewById(R.id.Arealist_title_textbtn);//地区分类选择
        distance_titleId = (TextView) findViewById(R.id.distance_titleId);//右边地区ID
        DefaultSortlist_title_textbtn = (TextView) findViewById(R.id.DefaultSortlist_title_textbtn);//默认排序选择
        nearby_title_textbtn = (TextView) findViewById(R.id.nearby_title_textbtn);//附近距离选择
        Arealist_mainlist = (LinearLayout) findViewById(R.id.Arealist_mainlist);
        Arealist_onelist = (ListView) findViewById(R.id.Arealist_onelist);
        Arealist_twolist = (ListView) findViewById(R.id.Arealist_twolist);
        nearby_customer_toplist = (ListView) findViewById(R.id.nearby_customer_toplist);
        orderSort_listview = (ListView) findViewById(R.id.orderSort_listview);
        mMapView = (MapView)findViewById(R.id.customer_list_map);

        /**
         * 附近客户距离数据源
         */
        nearbyAdapter = new NearByCustomerAdapter(CustomerActivity.this, NEARBY_DISTANCE, R.layout.item_nearby_more_list);
        nearby_customer_toplist.setAdapter(nearbyAdapter);
        //加载客户列表默认查附近500米
        customerPresenter = new CustomerPresenterImpl(this);
        newPosition = 7;
        customerPresenter.loadCustomerData(user,nPage, nSpage,"", mobile, shopName, "", "", "", "","");
        adapter = new CustomerAdapter(this, customerlist);
        lv.setAdapter(adapter);

        //加载地区数据
        areaPresenter = new RegionalAreaPresenterImpl(this);
        areaAdapter = new AreaOneAdapter(this, areaList);

        //默认排序列表选择数据
        sortAdapter = new OrderConditionSortAdapter(CustomerActivity.this,ORDER_CONDITION_SORT,R.layout.item_sort_more_list);
        orderSort_listview.setAdapter(sortAdapter);

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

    //初始化点击事件
    private void setListener() {
        MyOnclickListener mOnclickListener = new MyOnclickListener();//声明实例化点击事件
        //给控件设置点击事件
        Cuslist_back.setOnClickListener(mOnclickListener);
        queryBtn.setOnClickListener(mOnclickListener);
        contactphonecall.setOnClickListener(mOnclickListener);
        Arealist_title_textbtn.setOnClickListener(mOnclickListener);//地区选择点击
        DefaultSortlist_title_textbtn.setOnClickListener(mOnclickListener);//排序选择点击
        nearby_title_textbtn.setOnClickListener(mOnclickListener);//附近客户选择
        //给listview条目设置选择项事件客户列表数据

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                try{
                    Intent intent = new Intent(CustomerActivity.this, CustomerInfoActivity.class);
                    //用Bundle传递数据
                    Customer customer = (Customer) adapterView.getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("customer_id", customer.getId());
                    bundle.putSerializable("customer", customer);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        //附近客户查询选择切换
        nearby_customer_toplist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newPosition = position;
                nearbyAdapter.setSelectItem(newPosition);
                nearby_title_textbtn.setText(NEARBY_DISTANCE[newPosition]);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                nearby_title_textbtn.setCompoundDrawables(null, null, drawable,
                        null);

                switch (newPosition) {
                    case 0:
                        customerlist.clear();
                        nPage = 1;
                        totalSize = nSpage;
                        customerPresenter.loadCustomerData(user,nPage, nSpage,"",  mobile, shopName,lat, lng, "500", "0","");
                        adapter.notifyDataSetInvalidated();
                        break;
                    case 1:
                        customerlist.clear();
                        nPage = 1;
                        totalSize = nSpage;
                        customerPresenter.loadCustomerData(user,nPage, nSpage,"",  mobile, shopName,lat, lng, "1000", "0","");
                        adapter.notifyDataSetInvalidated();
                        break;
                    case 2:
                        customerlist.clear();
                        nPage = 1;
                        totalSize = nSpage;
                        customerPresenter.loadCustomerData(user,nPage, nSpage,"",  mobile, shopName,lat, lng, "2000", "0","");
                        adapter.notifyDataSetInvalidated();
                        break;
                    case 3:
                        customerlist.clear();
                        nPage = 1;
                        totalSize = nSpage;
                        customerPresenter.loadCustomerData(user,nPage, nSpage,"",  mobile, shopName,lat, lng, "5000", "0","");
                        adapter.notifyDataSetInvalidated();
                        break;
                    default:
                        break;
                }
                Arealist_title_textbtn.setText("全部地区");
                areaAdapter.notifyDataSetChanged();
//                twoAdapter.notifyDataSetChanged();

                DefaultSortlist_title_textbtn.setText("默认排序");
                sortAdapter.notifyDataSetChanged();

                mClearEditText.setText("");
                nearby_customer_toplist.setVisibility(View.GONE);
                nearbylistview = false;
            }
        });

        //默认排序列表数据item点击
        orderSort_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newPosition = 6;
                sortAdapter.setSelectItem(position);
                DefaultSortlist_title_textbtn.setText(ORDER_CONDITION_SORT[position]);
                sortListStr = DefaultSortlist_title_textbtn.getText().toString();
                Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                DefaultSortlist_title_textbtn.setCompoundDrawables(null, null, drawable,null);
                orderSort_listview.setVisibility(View.GONE);
                sort_toplist = false;

                if(position == 0){
                    customerlist.clear();
                    nPage = 1;
                    totalSize = nSpage;
                    customerPresenter.loadCustomerData(user,nPage,nSpage,"","","","","","","","last_delivery_date");
                    adapter.notifyDataSetInvalidated();
                }else if(position == 1){
                    customerlist.clear();
                    nPage = 1;
                    totalSize = nSpage;
                    customerPresenter.loadCustomerData(user,nPage,nSpage,"","","","","","","","delivery_goods_count");
                    adapter.notifyDataSetInvalidated();
                }

                Arealist_title_textbtn.setText("全部地区");
                areaAdapter.notifyDataSetChanged();
//                twoAdapter.notifyDataSetChanged();

                nearby_title_textbtn.setText("附近");
                nearbyAdapter.notifyDataSetChanged();
                mClearEditText.setText("");
            }
        });

        //选择区域刷新列表数据
        Arealist_onelist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                initAdapter1(areaListNew[position]);
                areaAdapter.setSelectItem(position);
                areaAdapter.notifyDataSetChanged();
            }
        });

        //区域类型切换选择
        Arealist_twolist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                newPosition = 5;
                twoAdapter.setSelectItem(position);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                Arealist_title_textbtn.setCompoundDrawables(null, null, drawable,
                        null);
                int pos = areaAdapter.getSelectItem();//得到左边区域选择位置
                Arealist_title_textbtn.setText(areaListNew[pos][position].getDistance_name());//设置选择的区域类型并展示到头部
                distance_titleId.setText(areaListNew[pos][position].getDistance_id());
                String regionalId = distance_titleId.getText().toString();

                customerlist.clear();
                nPage = 1;
                totalSize = nSpage;
                customerPresenter.loadCustomerData(user,nPage, nSpage, regionalId,"", "","",  "", "", "","");
                adapter.notifyDataSetInvalidated();

                nearby_title_textbtn.setText("附近");
                nearbyAdapter.notifyDataSetChanged();

                DefaultSortlist_title_textbtn.setText("默认排序");
                sortAdapter.notifyDataSetChanged();

                mClearEditText.setText("");
                Arealist_mainlist.setVisibility(View.GONE);
                arealistview = false;
            }
        });
    }
    //初始化地区数据并刷新
    private void initAdapter1(DistanceType[] array) {
        twoAdapter = new AreaTwoAdapter(this, array);
        Arealist_twolist.setAdapter(twoAdapter);
        twoAdapter.notifyDataSetChanged();
    }

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

            lat = String.valueOf(locData.latitude);
            lng = String.valueOf(locData.longitude);
            province = location.getProvince();
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
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocClient != null)
            mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        if (this.customerPresenter != null)
            this.customerPresenter.destroy();
//        unregisterReceiver();
    }

    //自定义类实现点击事件接口
    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int cid = v.getId();
            //返回键处理
            if (cid == R.id.Cuslist_back) {
                loadingdialog.dismiss();
                finish();
            }
            //客户查询
            if (cid == R.id.queryBtn) {
                Quickquery();
            }
            if (cid == R.id.contactphonecall){
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
            }
            //附近距离查询
            if (cid == R.id.nearby_title_textbtn) {
                Drawable drawable = null;
                if (!nearbylistview) {
                    drawable = getResources().getDrawable(R.drawable.ic_arrow_up_black);
                    nearby_customer_toplist.setVisibility(View.VISIBLE);
                    nearbyAdapter.notifyDataSetChanged();
                    nearbylistview = true;
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                    nearby_customer_toplist.setVisibility(View.GONE);
                    nearbylistview = false;
                }
                //动态设置textview drawableRight属性
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//设置边界
                nearby_title_textbtn.setCompoundDrawables(null, null, drawable, null);//设置图片在文字右边
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                nearby_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);
                nearby_customer_toplist.setVisibility(View.GONE);
                nearbylistview = false;
            }
            //地区选择
            if (cid == R.id.Arealist_title_textbtn) {
                Drawable drawable = null;
                if (!arealistview) {
                    drawable = getResources().getDrawable(R.drawable.ic_arrow_up_black);
                    //请求行政区域数据
                    areaPresenter.loadAreaData(user,province);
                    Arealist_onelist.setAdapter(areaAdapter);
                    areaAdapter.setSelectItem(0);
                    Arealist_mainlist.setVisibility(View.VISIBLE);
//                    twoAdapter.notifyDataSetChanged();
                    arealistview = true;
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                    Arealist_mainlist.setVisibility(View.GONE);
                    arealistview = false;
                }

                //动态设置textview drawableRight属性
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());//设置边界
                Arealist_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);//设置图片在文字右边
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                Arealist_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);
                Arealist_mainlist.setVisibility(View.GONE);
                arealistview = false;
            }
            //排序选择
            if (cid == R.id.DefaultSortlist_title_textbtn) {
                Drawable drawable = null;
                if (!sort_toplist) {
                    drawable = getResources().getDrawable(R.drawable.ic_arrow_up_black);
                    orderSort_listview.setVisibility(View.VISIBLE);
                    sortAdapter.notifyDataSetChanged();
                    sort_toplist = true;
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                    orderSort_listview.setVisibility(View.GONE);
                    sort_toplist = false;
                }
                //动态设置textview drawableRight属性
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//设置边界
                DefaultSortlist_title_textbtn.setCompoundDrawables(null, null, drawable, null);//设置图片在文字右边
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                DefaultSortlist_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);
                orderSort_listview.setVisibility(View.GONE);
                sort_toplist = false;
            }
        }
    }

    //读取手机通讯录的回调函数方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                //获得DATA表中的名字
                username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //条件为联系人ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
                Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null);
                if (phone!=null){
                    while (phone.moveToNext()) {
                        usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        // 对手机号码进行预处理（去掉号码前的+86、首尾空格、“-”号等）
                        usernumber = usernumber.replaceAll("^(\\+86)", "");
                        usernumber = usernumber.replaceAll("^(86)", "");
                        usernumber = usernumber.replaceAll("-", "");
                        usernumber = usernumber.replaceAll(" ", "");
                        usernumber = usernumber.trim();
                        mClearEditText.setText(usernumber);
                    }
                    phone.close();
                }
            }
        }
    }

    //快捷查询方法
    public void Quickquery() {
        newPosition = 4;
        String text = mClearEditText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            showToast("查询条件不能为空！");
            return;
        }
        //通过手机号码查询
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(text);
        if (m.matches()) {
            mobile = text;
            customerlist.clear();
            nPage = 1;
            totalSize = nSpage;
            shopName = "";
            customerPresenter.loadCustomerData(user,nPage, nSpage,"", mobile,  "","", "", "", "","");
            adapter.notifyDataSetInvalidated();

            Arealist_title_textbtn.setText("全部地区");
            areaAdapter.notifyDataSetChanged();

            DefaultSortlist_title_textbtn.setText("默认排序");
            sortAdapter.notifyDataSetChanged();

            nearby_title_textbtn.setText("附近");
            nearbyAdapter.notifyDataSetChanged();
        }
        //通过店名和联系人名字查询
        p = Pattern.compile("[\u4e00-\u9fa5]*");
        m = p.matcher(text);
        if (m.matches()) {
            shopName = text;
            customerlist.clear();
            nPage = 1;
            totalSize = nSpage;
            mobile = "";
            customerPresenter.loadCustomerData(user,nPage, nSpage, "", "",shopName, "","", "", "","");
            adapter.notifyDataSetInvalidated();

            Arealist_title_textbtn.setText("全部地区");
            areaAdapter.notifyDataSetChanged();
//            twoAdapter.notifyDataSetChanged();

            DefaultSortlist_title_textbtn.setText("默认排序");
            sortAdapter.notifyDataSetChanged();

            nearby_title_textbtn.setText("附近");
            nearbyAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDownPullRefresh() {

    }

    @Override
    public void onLoadingMore() {
        nPage += 1;
        if (totalSize >= pageSize) {
            lv.hideFooterView();
            return;
        } else {
            switch (newPosition) {
                case 0:
                    //附近500米刷新数据分页请求
                    customerPresenter.loadCustomerData(user,nPage, nSpage, "", mobile, shopName, lat, lng, "500", "0","");
                    break;
                case 1:
                    //附近1000米刷新数据分页请求
                    customerPresenter.loadCustomerData(user,nPage, nSpage, "", mobile, shopName, lat, lng, "1000", "0","");
                    break;
                case 2:
                    //附近2000米刷新数据分页请求
                    customerPresenter.loadCustomerData(user,nPage, nSpage, "", mobile, shopName, lat, lng, "2000", "0","");
                    break;
                case 3:
                    //附近5000米刷新数据分页请求
                    customerPresenter.loadCustomerData(user,nPage, nSpage, "", mobile, shopName, lat, lng, "5000", "0","");
                    break;
                case 4:
                    //根据号码，店名查询并刷新数据分页请求
                    customerPresenter.loadCustomerData(user,nPage, nSpage, "", mobile, shopName, "", "", "", "","");
                    break;
                case 5:
                    String distanceId = distance_titleId.getText().toString();
                    customerPresenter.loadCustomerData(user,nPage, nSpage, distanceId,"", "","",  "", "", "","");
                    break;
                case 6:
                    if(sortListStr.equals(ORDER_CONDITION_SORT[0])){
                        customerPresenter.loadCustomerData(user,nPage,nSpage,"","","","","","","","last_delivery_date");
                    }else if(sortListStr.equals(ORDER_CONDITION_SORT[1])){
                        customerPresenter.loadCustomerData(user,nPage,nSpage,"","","","","","","","delivery_goods_count");
                    }
                    break;
                case 7:
                    customerPresenter.loadCustomerData(user,nPage, nSpage,"", mobile, shopName, "", "", "", "","");
                    break;
                default:
                    break;
            }
        }
        totalSize += nSpage;
    }

    //手机屏幕返回手指按下事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Drawable drawable = null;
            if (arealistview == true) {
                drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                Arealist_mainlist.setVisibility(View.GONE);
                arealistview = false;
                //动态设置textview drawableRight属性
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());//设置边界
                Arealist_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);//设置图片在文字右边
            } else if (nearbylistview == true) {
                drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                nearby_customer_toplist.setVisibility(View.GONE);
                nearbylistview = false;
                //动态设置textview drawableRight属性
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());//设置边界
                nearby_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);//设置图片在文字右边
            }else if(sort_toplist == true){
                drawable = getResources().getDrawable(R.drawable.ic_arrow_down_black);
                orderSort_listview.setVisibility(View.GONE);
                sort_toplist = false;
                //动态设置textview drawableRight属性
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());//设置边界
                DefaultSortlist_title_textbtn.setCompoundDrawables(null, null,
                        drawable, null);//设置图片在文字右边
            }
            else {
                CustomerActivity.this.finish();
            }
        }
        return true;
    }

    /**
     * 客户列表数据
     *
     * @paramcustomers
     */
    @Override
    public void addCustomers(List<Customer> customers) {
        // TODO Auto-generated method stub
        if (customers.size() > 0) {
            customerlist.addAll(customers);
            pageSize = customerlist.get(0).getTotal_record_sum();
            adapter.notifyDataSetChanged();
        } else {
			showToast("暂无更多数据！");
            adapter.notifyDataSetChanged();
        }
        lv.hideFooterView();
        lv.setOnRefreshListener(CustomerActivity.this);
    }

    @Override
    public void showLoadFailureMsg(String errorMessage) {
        // TODO Auto-generated method stub
        showToast(errorMessage);
    }

    /**
     * 行政区域数据展示
     *
     * @paramcustomerArea
     */
    @Override
    public void queryRegionalArea(List<RegionalArea> regionalArea) {
        int regionalAreaCount = regionalArea.size();
        if (regionalAreaCount > 0) {
            if (areaList != null) {
                areaList.clear();
            }
            areaList.addAll(regionalArea);
            areaAdapter.notifyDataSetChanged();
            areaListNew = new DistanceType[regionalAreaCount][];
            //对数组进行循环遍历
            for (int i = 0; i < regionalAreaCount; i++) {
                RegionalArea regionalAreaList = regionalArea.get(i);
                areaListNew[i] = new DistanceType[regionalAreaList.getTypeList().size()];
                for (int j = 0; j < regionalAreaList.getTypeList().size(); j++) {
                    //得到每一项对应的区域名和id
                    areaListNew[i][j] = regionalAreaList.getTypeList().get(j);
                }
            }
            if (null != areaListNew) {
                initAdapter1(areaListNew[0]);
            }
        }
       else {
            showToast("暂无数据");
            areaAdapter.notifyDataSetChanged();
        }
    }
}
