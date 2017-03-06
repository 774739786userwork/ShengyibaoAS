package com.bangware.shengyibao.salesamount.presenter.impl;

import com.bangware.shengyibao.salesamount.model.SalesAmountModel;
import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;
import com.bangware.shengyibao.salesamount.model.impl.SalesAmountModelImpl;
import com.bangware.shengyibao.salesamount.presenter.OnSalesAmountListener;
import com.bangware.shengyibao.salesamount.presenter.SalesAmountPresenter;
import com.bangware.shengyibao.salesamount.view.SalesAmountView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


public class SalesAmountPresenterImpl implements SalesAmountPresenter,OnSalesAmountListener {
	public  static final String  REQUEST_TAG = "SalesAmount";
	private SalesAmountModel model;
	private SalesAmountView view;
	private String requestTag;
	
	public SalesAmountPresenterImpl(
			SalesAmountView view) {
		super();
		this.model = new SalesAmountModelImpl();
		this.view = view;
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
	}

	@Override
	public void loadSalesAmount(User user, String begin_date, String end_date,
								int nPage, int nSpage) {
		// TODO Auto-generated method stub
		view.showLoading();
		model.loadCustomerContacts(requestTag, user, begin_date, end_date, nPage, nSpage, this);
	}
	@Override
	public void onLoadSalesAmountSuccess(
			List<MonthProductAmount> monthProductAmounts) {
		// TODO Auto-generated method stub
		view.doSalesAmountLoadSuccess(monthProductAmounts);
		view.hideLoading();
	}

	@Override
	public void onLoadSalesAmountFailure(String errorMessage) {
		// TODO Auto-generated method stub
		view.showMessage(errorMessage);
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

}
