package com.bangware.shengyibao.shopcart.presenter.impl;


import com.bangware.shengyibao.deliverynote.model.DeliveryNoteModel;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.impl.DeliveryNoteModelImpl;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteSaveListener;
import com.bangware.shengyibao.shopcart.presenter.SettlementPresenter;
import com.bangware.shengyibao.shopcart.view.SettlementView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class SettlementPresenterImpl implements SettlementPresenter,OnDeliveryNoteSaveListener {
	
	public static final String REQUEST_TAG = "SettlementActivity";
	private String requestTag;
	private SettlementView settlementView;
	private DeliveryNoteModel deliveryNoteModel;
	public SettlementPresenterImpl(SettlementView view){
		requestTag = REQUEST_TAG+System.currentTimeMillis();
		settlementView = view;
		deliveryNoteModel = new DeliveryNoteModelImpl();
	}
	@Override
	public void doSave(User user,DeliveryNote deliveryNote) {
		settlementView.showLoading("正在提交送货单，请稍等！");
		deliveryNoteModel.save(user,requestTag, deliveryNote, this);
	}
	

	@Override
	public void onSaveSuccess(DeliveryNote deliveryNote) {
		settlementView.hideLoading();
		settlementView.doSaveSuccess(deliveryNote);
	}

	@Override
	public void onError(String message) {
		settlementView.hideLoading();
		settlementView.showMessage(message);
	}
	
	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}
}
