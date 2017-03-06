package com.bangware.shengyibao.deliverynote.presenter.impl;

import com.bangware.shengyibao.deliverynote.model.DeliveryNoteModel;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.deliverynote.model.impl.DeliveryNoteModelImpl;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteListener;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNotePresenter;
import com.bangware.shengyibao.deliverynote.view.DeliveryNoteDetailView;
import com.bangware.shengyibao.deliverynote.view.DeliveryNoteQueryView;

import java.util.List;

import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class DeliveryNotePresenterImpl implements DeliveryNotePresenter,DeliveryNoteListener {
	
	public static final String REQUEST_TAG = "DeliveryNote";
	private String requestTag;
	private DeliveryNote deliveryNote;
	private DeliveryNoteModel noteModel;
	private DeliveryNoteQueryView noteView;
	private DeliveryNoteDetailView noteDetailView;
	public DeliveryNotePresenterImpl(DeliveryNoteQueryView noteView) {
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.noteView=noteView;
		this.noteModel = new DeliveryNoteModelImpl();
		this.deliveryNote = new DeliveryNote();
	}
	
	public DeliveryNotePresenterImpl(DeliveryNoteDetailView noteDetailView) {
		// TODO Auto-generated constructor stub
		this.noteDetailView=noteDetailView;
		this.noteModel = new DeliveryNoteModelImpl();
	}
	/**
	 * 保存
	 */
	@Override
	public void doSave(User user,DeliveryNote deliveryNote) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 作废
	 */
	@Override
	public void doAbort(User user,String deliveryNoteId) {
		// TODO Auto-generated method stub
		noteModel.abort(requestTag, deliveryNoteId, user,this);
	}
	
	/**
	 * 产品详情
	 */
	@Override
	public void doLoadDetail(User user,String deliveryNoteId) {
		// TODO Auto-generated method stub
		noteDetailView.showLoading();
		noteModel.load(requestTag, deliveryNoteId,user, this);
	}

	//通过时间查询送货单
	@Override
	public void doLoad(User user,String begin_date, String end_date, int nPage, int nSpage, int show_type) {
		// TODO Auto-generated method stub
		noteView.showLoading();
		noteModel.query(requestTag, begin_date, end_date, nPage, nSpage,show_type,user, this);
	}
	
	////////////////////////////////////响应事件////////////////////////////////
	/**
	 * 保存成功
	 */
	@Override
	public void onSaveSuccess(DeliveryNote deliveryNote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String errorMessage) {
		// TODO Auto-generated method stub
		if (noteView != null){
			noteView.hideLoading();
			noteView.showMessage(errorMessage);
		}else {
			noteDetailView.hideLoading();
			noteDetailView.showMessage(errorMessage);
		}
	}

	@Override
	public void onQueryDeliveryNote(List<DeliveryNote> deliveryNotes) {
		// TODO Auto-generated method stub
		noteView.hideLoading();
		noteView.loadDeliveryNoteData(deliveryNotes);
	}
	
	/**
	 * 加载产品详情成功
	 */
	@Override
	public void onLoadSuccess(List<DeliveryNoteGoods> newList) {
		// TODO Auto-generated method stub
		if(newList != null){
			noteDetailView.hideLoading();
			noteDetailView.addDeliveryNoteDetailProduct(newList);
		}
	}

	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public DeliveryNote getDeliveryNote() {
		// TODO Auto-generated method stub
		return this.deliveryNote;
	}
}
