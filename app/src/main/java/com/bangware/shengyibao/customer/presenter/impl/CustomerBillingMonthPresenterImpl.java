package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerBillingMonthPresenter;
import com.bangware.shengyibao.customer.presenter.OnMonthCustomerBillingRecordListener;
import com.bangware.shengyibao.customer.view.CustomerBillingMonthSalerRecordView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


/**
 * 主页客户未开单、已开单、送货客户实现类
 * @author ccssll
 *
 */
public class CustomerBillingMonthPresenterImpl implements CustomerBillingMonthPresenter,OnMonthCustomerBillingRecordListener {
	
	public static final String REQUEST_TAG = "CustomerBilling";
	public String requestTag;
	public CustomerModel mCustomerModel;
	public CustomerBillingMonthSalerRecordView salerView;
	
	public CustomerBillingMonthPresenterImpl(CustomerBillingMonthSalerRecordView salerRecordView) {
		// TODO Auto-generated constructor stub
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.salerView=salerRecordView;
		this.mCustomerModel = new CustomerModelImpl();
		
	}
	@Override
	public void loadCustomerBillingMonthData(User user,int nPage,
											 int nSpage, int show_type, String compositor) {
		// TODO Auto-generated method stub
		salerView.showLoading();
		mCustomerModel.queryCustomerBillingMonthRecord(requestTag, user, nPage, nSpage,show_type,compositor, this);
	}


	@Override
	public void onLoadDataSuccess(List<Customer> customers) {
		// TODO Auto-generated method stub
		salerView.queryCustomerSalerRecord(customers);
		salerView.hideLoading();
	}

	@Override
	public void onLoadDataFailure(String errorMessage) {
		// TODO Auto-generated method stub
		salerView.hideLoading();
		salerView.showLoadFailureMsg(errorMessage);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

}
