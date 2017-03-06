package com.bangware.shengyibao.deliverynote.presenter;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteQuery;

import java.util.List;


public interface OnDeliveryNoteQueryListener {
	public void onQuerySuccess(List<DeliveryNoteQuery> list);
	
	public void onError(String message);
}
