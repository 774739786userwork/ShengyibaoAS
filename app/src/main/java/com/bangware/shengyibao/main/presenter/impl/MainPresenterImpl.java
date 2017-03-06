package com.bangware.shengyibao.main.presenter.impl;

import com.bangware.shengyibao.main.model.MainModel;
import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;
import com.bangware.shengyibao.main.model.impl.MainModelImpl;
import com.bangware.shengyibao.main.presenter.MainPresenter;
import com.bangware.shengyibao.main.presenter.OnMainSalerListener;
import com.bangware.shengyibao.main.view.MainView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

public class MainPresenterImpl implements MainPresenter,OnMainSalerListener {
	public static final String REQUEST_TAG= "MainPage";
	private MainModel billingModel;
	private MainView mView;
	private String requestTag;
	
	public MainPresenterImpl(MainView mView) {
		super();
		this.requestTag = REQUEST_TAG+ System.currentTimeMillis();
		this.billingModel = new MainModelImpl();
		this.mView = mView;
	}

	@Override
	public void OnLoadToDaySummarySuccess(ToDaySummary daySummary) {
		// TODO Auto-generated method stub
		mView.doTodaySummaryLoadSuccess(daySummary);
		mView.hideLoading();
	}
	@Override
	public void OnLoadThisMonthSummarySuccess(ThisMonthSummary monthSummary) {
		// TODO Auto-generated method stub
		mView.hideLoading();
		mView.doThisMonthSummaryLoadSuccess(monthSummary);
	}

	@Override
	public void showFailure(String errorMessage) {
		// TODO Auto-generated method stub
		mView.hideLoading();
		mView.showMessage(errorMessage);
	}

	@Override
	public void loadToDaySummary(User user) {
		// TODO Auto-generated method stub
		mView.showLoading();
		billingModel.loadToDaySummary(requestTag, user,this);
	}

	@Override
	public void loadThisMonthSummary(User user) {
		// TODO Auto-generated method stub
		mView.showLoading();
		billingModel.loadThisMonthSummary(requestTag,user,this);
	}
	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}
}
