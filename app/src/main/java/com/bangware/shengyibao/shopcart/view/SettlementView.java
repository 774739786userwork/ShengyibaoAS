package com.bangware.shengyibao.shopcart.view;


import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;


public interface SettlementView extends BaseView {
	public void doSaveSuccess(DeliveryNote deliveryNote);
}
