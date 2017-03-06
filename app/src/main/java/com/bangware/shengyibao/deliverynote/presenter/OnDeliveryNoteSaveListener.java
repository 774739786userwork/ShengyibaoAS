package com.bangware.shengyibao.deliverynote.presenter;


import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;

public interface OnDeliveryNoteSaveListener {
	public void onSaveSuccess(DeliveryNote deliveryNote);
	
	public void onError(String message);
}
