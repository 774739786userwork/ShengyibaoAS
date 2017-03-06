package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.CustomerSalerAreaInfo;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerSalerAreaPresenter;
import com.bangware.shengyibao.customer.presenter.OnCustomerSalerAreaListener;
import com.bangware.shengyibao.customer.view.CustomerSalerAreaView;

import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * 营销区域实现类
 * @author ccssll
 *
 */
public class CustomerSalerAreaPresenterImpl implements
		CustomerSalerAreaPresenter,OnCustomerSalerAreaListener {

	public static final String REQUEST_TAG = "CustomerPurchase";
	private CustomerModel mCustomerModel;
	private CustomerSalerAreaView mSalerAreaView;
	private String requestTag;
	
	
	public CustomerSalerAreaPresenterImpl(CustomerSalerAreaView mSalerAreaView) {
		// TODO Auto-generated constructor stub
		this.requestTag = REQUEST_TAG + System.currentTimeMillis();
		this.mSalerAreaView = mSalerAreaView;
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	@Override
	public void loadSalerAreaData(User user) {
		// TODO Auto-generated method stub
		mSalerAreaView.showLoading();
		mCustomerModel.loadSalerArea(requestTag, user, this);
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public void onLoadCustomerSalerArea(CustomerSalerAreaInfo salerareaInfos) {
		// TODO Auto-generated method stub
		mSalerAreaView.hideLoading();
		mSalerAreaView.loadSalerAreaData(salerareaInfos.getCustomerSalerAreas());
	}

	@Override
	public void onError(String errorMessage) {
		// TODO Auto-generated method stub
		mSalerAreaView.hideLoading();
		mSalerAreaView.showMessage(errorMessage);
	}

}
