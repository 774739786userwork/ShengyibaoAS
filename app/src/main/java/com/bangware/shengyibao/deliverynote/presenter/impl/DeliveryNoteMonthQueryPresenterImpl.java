package com.bangware.shengyibao.deliverynote.presenter.impl;

import com.bangware.shengyibao.deliverynote.model.DeliveryNoteModel;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;
import com.bangware.shengyibao.deliverynote.model.impl.DeliveryNoteModelImpl;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteMonthQueryPresenter;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteMonthQueryListener;
import com.bangware.shengyibao.deliverynote.view.DeliveryNoteMonthQueryView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


public class DeliveryNoteMonthQueryPresenterImpl implements DeliveryNoteMonthQueryPresenter,OnDeliveryNoteMonthQueryListener {

	public static final String REQUEST_TAG = "DeliveryNoteMonthQuery";
	private String requestTag;
	private DeliveryNoteModel noteMonthQueryModel;
	private DeliveryNoteMonthQueryView monthQueryView;
	
	public DeliveryNoteMonthQueryPresenterImpl(
			DeliveryNoteMonthQueryView monthQueryView) {
		super();
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.noteMonthQueryModel = new DeliveryNoteModelImpl();
		this.monthQueryView = monthQueryView;
	}

	@Override
	public void showDialog() {
		// TODO Auto-generated method stub
		monthQueryView.showLoading();
	}

	@Override
	public void hideDialog() {
		// TODO Auto-generated method stub
		monthQueryView.hideLoading();
	}

	@Override
	public void OnLoadNoteMonthQuerySuccess(List<DeliveryNoteMonthQuery> list) {
		// TODO Auto-generated method stub
		monthQueryView.doDeliveryNoteMonthQuerySuccess(list);
		monthQueryView.hideLoading();
	}

	@Override
	public void showFailure(String errorMessage) {
		// TODO Auto-generated method stub
		monthQueryView.hideLoading();
		monthQueryView.showError(errorMessage);
	}

	@Override
	public void loadDeliveryNoteMonthQuery(User user, String begin_date,
										   String end_date, int nPage, int nSpage, int show_type) {
		// TODO Auto-generated method stub
		monthQueryView.showLoading();
		noteMonthQueryModel.loadDeliveryNoteMonthQuery(requestTag,user, begin_date, end_date, nPage, nSpage,show_type, this);
	}
	
	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

}
