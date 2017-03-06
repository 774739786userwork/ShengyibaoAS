package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerPurchasePresenter;
import com.bangware.shengyibao.customer.presenter.OnCustomerPurchaseListener;
import com.bangware.shengyibao.customer.view.CustomerPurchaseView;

import java.util.List;

import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * 客户进货记录
 * @author ccssll
 *
 */
public class CustomerPurchaseImpl implements CustomerPurchasePresenter,OnCustomerPurchaseListener {
	public static final String REQUEST_TAG = "CustomerPurchase";
	private CustomerModel mCustomerModel;
	private CustomerPurchaseView mPurchaseView;
	private CustomerPurchase purchase;
	private String requestTag;
	
	public CustomerPurchaseImpl(CustomerPurchaseView mPurchaseView) {
		super();
		this.requestTag = REQUEST_TAG + System.currentTimeMillis();
		this.mPurchaseView = mPurchaseView;
		this.mCustomerModel = new CustomerModelImpl();
		this.purchase = new CustomerPurchase();
	}

	/*******************处理数据加载方法***************************/
	@Override
	public void queryCustomerPurchaseData(User user,String customer_id, int nPage, int nSpage, String begin_date, String end_date) {
		// TODO Auto-generated method stub
		mPurchaseView.showLoading();
		mCustomerModel.queryCustomerPurchase(requestTag, user,
				customer_id,nPage,nSpage,begin_date,end_date, this);
	}

	/******************响应事件***********************/
	@Override
	public void onPurchaseLoaded(List<CustomerPurchase> customerPurchases) {
		// TODO Auto-generated method stub
		mPurchaseView.hideLoading();
		mPurchaseView.loadPurchaseData(customerPurchases);
	}

	@Override
	public void onError(String errorMessage) {
		// TODO Auto-generated method stub
		mPurchaseView.showMessage(errorMessage);
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

}
