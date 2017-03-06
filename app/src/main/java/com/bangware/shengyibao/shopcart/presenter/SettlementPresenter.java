package com.bangware.shengyibao.shopcart.presenter;


import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.user.model.entity.User;

public interface SettlementPresenter {

	/**
	 * 送货单保存
	 * @param deliveryNote
	 */
	public void doSave(User user,DeliveryNote deliveryNote);
	
	public void destroy();
}
