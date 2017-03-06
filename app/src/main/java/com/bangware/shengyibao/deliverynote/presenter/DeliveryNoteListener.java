package com.bangware.shengyibao.deliverynote.presenter;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;

import java.util.List;


public interface DeliveryNoteListener {
	/**
	 * 保存成功
	 * @param deliveryNote
	 */
	public void onSaveSuccess(DeliveryNote deliveryNote);
	
	/**
	 * 加载成功
	 * @param deliveryNote
	 */
	
	public void onLoadSuccess(List<DeliveryNoteGoods> newList);
	/**
	 * 查询成功
	 * @param list
	 */
	public void onQueryDeliveryNote(List<DeliveryNote> deliveryNotes);
	
//	public void onAbortSuccess(String deliveryNoteId,User user);
	/**
	 * 操作失败
	 * @param errorMessage
	 */
	public void onError(String errorMessage);
	
}
