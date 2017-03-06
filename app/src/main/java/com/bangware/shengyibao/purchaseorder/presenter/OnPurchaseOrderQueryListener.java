package com.bangware.shengyibao.purchaseorder.presenter;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteQuery;

import java.util.List;


public interface OnPurchaseOrderQueryListener {
	public void onQuerySuccess(List<DeliveryNoteQuery> list);
	
	public void onError(String message);
}
