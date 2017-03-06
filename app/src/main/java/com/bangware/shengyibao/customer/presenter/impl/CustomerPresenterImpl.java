package com.bangware.shengyibao.customer.presenter.impl;
import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerPresenter;
import com.bangware.shengyibao.customer.presenter.OnCustomerListener;
import com.bangware.shengyibao.customer.view.CustomerDetailView;
import com.bangware.shengyibao.customer.view.CustomerView;

import java.util.List;

import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class CustomerPresenterImpl implements CustomerPresenter, OnCustomerListener {
	
	public  static final String  REQUEST_TAG = "Customer";
	private String requestTag;
	private CustomerModel mCustomerModel;
	private CustomerView mCustomerView;
	
	public CustomerPresenterImpl(CustomerView newCustomersView) {
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.mCustomerView=newCustomersView;
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	public CustomerPresenterImpl(CustomerDetailView mDetailView) {
		// TODO Auto-generated constructor stub
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	//调用客户列表数据加载方法传递参数
	public void loadCustomerData(User user,int nPage, int nSpage, String xzqh, String phone, String shopName,
								 String latitude, String longitude, String nearBy, String type, String compositor) {
		// TODO Auto-generated method stub
		mCustomerView.showLoading();
		mCustomerModel.loadCustomer(requestTag, nPage, nSpage,xzqh,
				phone, user, shopName,latitude,longitude,nearBy,type,compositor,this);
	}

	//调用客户列表数据加载方法传递对象
	@Override
	public void onLoadDataSuccess(List<Customer> customers) {
		// TODO Auto-generated method stub
		if(customers != null){
			mCustomerView.addCustomers(customers);
		}
		mCustomerView.hideLoading();
	}


	//加载失败
	@Override
	public void onLoadDataFailure(String errorMessage) {
		// TODO Auto-generated method stub
		mCustomerView.hideLoading();
		mCustomerView.showLoadFailureMsg(errorMessage);
	}

	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}
}
