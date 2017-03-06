package com.bangware.shengyibao.customer.presenter.impl;


import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerMapLocationPresenter;
import com.bangware.shengyibao.customer.presenter.OnCustomerMapLocationListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

public class CustomerMapLocationPresenterImpl implements
		CustomerMapLocationPresenter,OnCustomerMapLocationListener {
	
	public  static final String  REQUEST_TAG = "MapViewActivity";
	private String requestTag;
	private CustomerModel mCustomerModel;
	
	public CustomerMapLocationPresenterImpl() {
		// TODO Auto-generated constructor stub
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	
	@Override
	public void loadMapLocation(User user,String customerId, String longitude,
								String latitude, String location_address) {
		// TODO Auto-generated method stub
		mCustomerModel.customerMapLocation(requestTag, user, customerId, longitude, latitude,location_address,this);
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

}
