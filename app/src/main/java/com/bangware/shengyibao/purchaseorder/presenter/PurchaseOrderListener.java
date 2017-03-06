package com.bangware.shengyibao.purchaseorder.presenter;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;

import java.util.List;


public interface PurchaseOrderListener {
	/**
	 * 保存成功
	 * @param deliveryNote
	 */
	public void onSaveSuccess();
	
	/**
	 * 加载成功
	 * @param deliveryNote
	 */
	
	public void onLoadSuccess(List<DeliveryNoteGoods> newList);
	/**
	 * 查询成功
	 * @param list
	 */
	public void onQueryPurchaseOrder(List<DeliveryNote> deliveryNotes);
	
//	public void onAbortSuccess(String deliveryNoteId,User user);
	/**
	 * 操作失败
	 * @param errorMessage
	 */
	public void onError(String errorMessage);
	
}
