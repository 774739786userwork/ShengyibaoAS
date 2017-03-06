package com.bangware.shengyibao.returngood.presenter.impl;

import com.bangware.shengyibao.returngood.model.ReturnGoodQueryModel;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.model.impl.ReturnGoodQueryModelImpl;
import com.bangware.shengyibao.returngood.presenter.OnReturnNoteDetailListener;
import com.bangware.shengyibao.returngood.presenter.ReturnNoteDetailPresenter;
import com.bangware.shengyibao.returngood.view.ReturnGoodProductView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


public class ReturnNoteDetailPresenterImpl implements ReturnNoteDetailPresenter,OnReturnNoteDetailListener {
	public static final String REQUEST_TAG = "ReturnNote";
	private String requestTag;
	private ReturnGoodQueryModel mModel;
	private ReturnGoodProductView pView;
	
	public ReturnNoteDetailPresenterImpl(ReturnGoodProductView pView) {
		// TODO Auto-generated constructor stub
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.pView=pView;
		this.mModel = new ReturnGoodQueryModelImpl();
	}
	
	//通过ID访问后台数据
	@Override
	public void doLoadReturnDetail(User user,String returnNoteId) {
		// TODO Auto-generated method stub
		pView.showLoading();
		mModel.loadReturnNoteDetail(requestTag, returnNoteId, user, this);

	}
	
	//请求得到数据并响应展示
	@Override
	public void onLoadSuccess(List<ReturnNoteGoods> noteGoodsList) {
		// TODO Auto-generated method stub
		pView.hideLoading();
		pView.queryReturnNoteProduct(noteGoodsList);
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public void onLoadMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	//发起作废请求
	@Override
	public void doAbort(User user,String returnNoteId) {
		// TODO Auto-generated method stub
		mModel.abortReturnNote(requestTag, returnNoteId, user);
	}

}
