package com.bangware.shengyibao.activity;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.bangware.shengyibao.model.CityModel;
import com.bangware.shengyibao.model.DistrictModel;
import com.bangware.shengyibao.model.ProvinceModel;
import com.bangware.shengyibao.net.ConnectionChangeReceiver;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;


import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class BaseActivity extends Activity implements BaseView {
	private Toast mToast;
	protected CustomProgressDialog loadingdialog;
	private  ConnectionChangeReceiver myReceiver;
	protected void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState!=null)
		{
			DataRequest.buildRequestQueue(this);
		}
		super.onCreate(savedInstanceState);
		registerReceiver();
		loadingdialog = CustomProgressDialog.createDialog(this,R.drawable.frame);
		setDefaultFont();
	}

	/**
	 * onSaveInstanceState和onRestoreInstanceState保存状态与恢复
	 * @param outState
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.e("HELLO", "HELLO：当Activity被销毁的时候，不是用户主动按back销毁，例如按了home键");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.e("HELLO", "HELLO:如果应用进程被系统咔嚓，则再次打开应用的时候会进入");
	}

	/**广播注册*/
	private  void registerReceiver(){
		IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		myReceiver=new ConnectionChangeReceiver();
		this.registerReceiver(myReceiver, filter);
	}

	private  void unregisterReceiver(){
		this.unregisterReceiver(myReceiver);
	}

	@Override
	protected void onDestroy() {
		if (myReceiver != null){
			unregisterReceiver();
		}
		super.onDestroy();
	}



	/** 判断触摸时间派发间隔 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (AppContext.isFastDoubleClick()) {
				return true;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 设置程序字体不会随系统改变而改变
	 */
	public void setDefaultFont(){
		Resources res = super.getResources();
		Configuration config=new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config, res.getDisplayMetrics());
	}

	/**
	 * 公共弹窗
	 *
	 * @param context
	 *            :Context 传入当前调用该方法的activity实例
	 * @param msg
	 *            :String 要显示的显示文字
	 * @param type
	 *            :int 显示类型1：有“确定”、“取消”两个操作，2：仅为确定
	 * @param handler
	 *            :Handler 传入的需要回调的handler信息，可作为回调方法是用，msg.what = 1时为操作完成状态符
	 */
	public void showDialog(Context context, String title, int type, final Handler handler){
		final AlertDialog.Builder builer = new AlertDialog.Builder(context);
		builer.setTitle(title);
		builer.setCancelable(false);
		if (type == 1){
			builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					if(handler != null){
						Message msg = handler.obtainMessage();
						msg.what = 1;
						handler.sendMessage(msg);
					}

				}
			});
			builer.setNegativeButton("取消", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{

				}
			});
		}
		if(type == 2){
			builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					if(handler != null){
						Message msg = handler.obtainMessage();
						msg.what = 2;
						handler.sendMessage(msg);
					}
				}
			});
		}
		builer.show();
	}
	/**
     * 显示Toast消息
     * @param msg
     */
    public void showToast(String msg){
    	showToast(msg, Toast.LENGTH_SHORT);
    }
    public void showToast(String msg, int length){
        if(mToast == null){
            mToast = Toast.makeText(this, msg, length);
        }else{
            mToast.setText(msg);
            mToast.setDuration(length);
        }
        mToast.show();
    }
    /**
     * 显示Toast消息
     * @param
     */
    public void showToast(int stringId){
    	showToast(stringId, Toast.LENGTH_SHORT);
    }
    public void showToast(int stringId, int length){
        if(mToast == null){
            mToast = Toast.makeText(this, stringId, length);
        }else{
            mToast.setText(stringId);
            mToast.setDuration(length);
        }
        mToast.show();
    }

    public void showLoading(){
    	loadingdialog.show();
    }

	@Override
	public void showLoading(String text) {
		loadingdialog.show();
	}

    public void hideLoading(){
    	loadingdialog.dismiss();
    }
    public void showMessage(String message){
    	showToast(message);
    }
    public void dismissLoading(){
    	loadingdialog.dismiss();
    }

    /**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName ="";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode ="";

	/**
	 * 解析省市区的XML数据
	 */

    protected void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			//*/ 初始化默认选中的省、市、区
			if (provinceList!= null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList!= null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			//*/
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// 遍历所有省的数据
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// 区/县对于的邮编，保存到mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// 市-区/县的数据，保存到mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {
            e.printStackTrace();  
        } finally {
        	
        } 
	}
    
}
