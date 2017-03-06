package com.bangware.shengyibao.purchaseorder.presenter.impl;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.purchaseorder.model.PurchaseOrderModel;
import com.bangware.shengyibao.purchaseorder.model.impl.PurchaseOrderModelImpl;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderListener;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderPresenter;
import com.bangware.shengyibao.purchaseorder.view.PurchaseOrderDetailView;
import com.bangware.shengyibao.purchaseorder.view.PurchaseOrderQueryView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


public class PurchaseOrderPresenterImpl implements PurchaseOrderPresenter,PurchaseOrderListener {

	public static final String REQUEST_TAG = "PurchaseOrder";
	private String requestTag;
	private DeliveryNote deliveryNote;
	private PurchaseOrderModel purchaseModel;
	private PurchaseOrderDetailView purchasedetailView;
	private PurchaseOrderQueryView purchaseOrderQueryView;

	public PurchaseOrderPresenterImpl( PurchaseOrderQueryView purchaseOrderQueryView) {
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.deliveryNote = new DeliveryNote();
		this.purchaseModel = new PurchaseOrderModelImpl();
		this.purchaseOrderQueryView = purchaseOrderQueryView;
	}

	public PurchaseOrderPresenterImpl(PurchaseOrderDetailView purchasedetailView) {
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.purchasedetailView = purchasedetailView;
		this.purchaseModel = new PurchaseOrderModelImpl();
	}


	@Override
	public void onSaveSuccess() {
       purchasedetailView.save();
	}

	@Override
	public void onLoadSuccess(List<DeliveryNoteGoods> newList) {
		if(newList != null){
			purchasedetailView.hideLoading();
			purchasedetailView.addPurchaseOrderDetailProduct(newList);
		}
	}

	@Override
	public void onQueryPurchaseOrder(List<DeliveryNote> deliveryNotes) {
		  purchaseOrderQueryView.hideLoading();
          purchaseOrderQueryView.loadPurchaseOrderQueryData(deliveryNotes);
	}


	@Override
	public void onError(String errorMessage) {
		// TODO Auto-generated method stub
		if (purchaseOrderQueryView != null){
			purchaseOrderQueryView.hideLoading();
			purchaseOrderQueryView.showMessage(errorMessage);
		}else {
			purchasedetailView.hideLoading();
			purchasedetailView.showMessage(errorMessage);
		}
	}

	@Override
	public void doLoad(User user,String begin_date, String end_date, int nPage, int nSpage, int show_type) {
		purchaseOrderQueryView.showLoading();
		purchaseModel.query(requestTag, begin_date, end_date, nPage, nSpage,show_type,user, this);
	}

	@Override
	public DeliveryNote getPurchaseOrder() {
		return this.deliveryNote;
	}



	@Override
	public void doLoadDetail(User user,String deliveryNoteId) {
		purchasedetailView.showLoading();
		purchaseModel.load(requestTag, deliveryNoteId,user, this);
	}


	@Override
	public void update_purchase_order(User user,DeliveryNote deliveryNote, double wechat_payment, double Alipay, double bank_receive_total_sum, double cash_payment) {
		purchasedetailView.showLoading();
		purchaseModel.update_purchase_order(user,deliveryNote,wechat_payment,Alipay,bank_receive_total_sum,cash_payment,this,requestTag);
	}


	@Override
	public void destroy() {
			DataRequest.getInstance().cancelRequestByTag(requestTag);
	}
}
