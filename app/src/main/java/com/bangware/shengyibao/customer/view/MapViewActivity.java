package com.bangware.shengyibao.customer.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.presenter.CustomerMapLocationPresenter;
import com.bangware.shengyibao.customer.presenter.impl.CustomerMapLocationPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

public class MapViewActivity extends BaseActivity {
	// 定位相关
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private LocationClient mLocClient;
    private MyLocationData locData;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    // UI相关
    OnCheckedChangeListener radioButtonListener;
    private TextView resetMarkerBtn;
    private TextView bdgetLocation;
    
    boolean isFirstLoc = true; // 是否首次定位
    boolean isRequest = false;//是否手动触发请求定位
    
    private CustomerMapLocationPresenter locationPresenter;
    private String longitude,latitude,address;
    private TextView location_textview;
    private TextView current_location;
    private Customer customer;
    private double lat;
    private double lng;
    private float mCurrentAccracy;

    private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bdmap);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);

        mCurrentMode = LocationMode.NORMAL;
        findViews();
        setListener();
        
        mBaiduMap = mMapView.getMap();
        
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        
        //根据后台传过来的经纬度定位到默认位置
        LatLng cenpt = new LatLng(lat,lng);
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(cenpt).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        
       // 此处设置开发者获取到的方向信息，顺时针0-360,描点设置
        locData = new MyLocationData.Builder()
        .accuracy(mCurrentAccracy)
        .direction(100).latitude(lat)
        .longitude(lng).build();
        mBaiduMap.setMyLocationData(locData);//设置定位数据
        location_textview.setText("客户位置：");
	}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    requestLocClick();
                } else {
                    // Permission Denied
                    showToast("用户取消了权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
	
	private void findViews() {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.bmapView);
		bdgetLocation = (TextView) findViewById(R.id.bdgetLocation);
		current_location = (TextView) findViewById(R.id.current_location);
		location_textview = (TextView) findViewById(R.id.location_textview);
		resetMarkerBtn = (TextView) findViewById(R.id.resetMarkerBtn);
		
		Bundle bundle = this.getIntent().getExtras();
		locationPresenter = new CustomerMapLocationPresenterImpl();
		customer = (Customer)bundle.getSerializable("customer");

		if (!customer.getLatitude().isEmpty() && !customer.getLongitude().isEmpty()) {
			try{
				lat = Double.valueOf(customer.getLatitude());
				lng = Double.valueOf(customer.getLongitude());
			}catch(Exception e){
				lat = 28.21;
				lng = 113;
				showToast("当前位置不精准，请重新定位！");
			}
		}else{
			lat = 28.21;
			lng = 113;
			showToast("当前位置不准确，请重新定位！");
		}
		current_location.setText(customer.getAddress());
	}
	
	private void setListener() {
		// TODO Auto-generated method stub
		MyOnClickListener clickListener = new MyOnClickListener();
		bdgetLocation.setOnClickListener(clickListener);
		resetMarkerBtn.setOnClickListener(clickListener);
	}
	
	private class MyOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.bdgetLocation){
                if(Build.VERSION.SDK_INT >= 23) {
                    int checkSMSPermission = ContextCompat.checkSelfPermission(MapViewActivity.this, Manifest.permission.WRITE_CALENDAR);
                    if(checkSMSPermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MapViewActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
                        return;
                    }else{
                        requestLocClick();
                    }
                }else{
                    requestLocClick();
                }
			}
			if(v.getId() == R.id.resetMarkerBtn){
				//提交数据至后台
				locationPresenter.loadMapLocation(user,customer.getId(), longitude, latitude,customer.getAddress());
				Intent intent = new Intent(MapViewActivity.this, CustomerInfoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				showToast("标注成功");
				Bundle bundle = new Bundle();
				bundle.putSerializable("customer", customer);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
		
	}
	
	//手动请求定位
	public void requestLocClick(){
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.setIsNeedLocationDescribe(true);//设置附近地址 
        option.setScanSpan(2000);//设置发起定位请求的间隔时间为1000ms
        switch (mCurrentMode) {
	        case NORMAL:
	        mLocClient.setLocOption(option);
	        mLocClient.start();//	调用此方法开始定位
	        mCurrentMode = LocationMode.FOLLOWING;
            mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                            mCurrentMode, true, mCurrentMarker));
	        showToast("正在定位……");
	        break;
        default:
            break;
		}
        /*isRequest = true;
    	if(mLocClient != null && mLocClient.isStarted()){
    		switch (mCurrentMode) {
            case NORMAL:
            	mLocClient.requestLocation();
            	mLocClient.start();//	调用此方法开始定位
                mCurrentMode = LocationMode.FOLLOWING;
                mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
                showToast("正在定位……");
                break;
            default:
                break;
    		}
    	}
    	else{
    		Log.d("TAG", "locClient is null........... ");
    	}*/
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
            
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            }else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            mCurrentAccracy = location.getRadius();//获取定位中心点
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            current_location.setText(mLocClient.getLastKnownLocation().getAddrStr());
            /*String city = location.getCity();
            String district = location.getDistrict();
            String street = location.getStreet();
            String near = location.getLocationDescribe();
                   address = street;
                   current_location.setText(address);*/
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            location_textview.setText("当前位置：");
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
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
