package com.bangware.shengyibao.deliverynote.presenter;


import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * 送货单Presenter类
 * @author luming.tang
 *
 */
public interface DeliveryNotePresenter {
	/**
	 * 执行送货单查询
	 * @param salerId
	 * @param startDate
	 * @param endDate
	 */
	public void doLoad(User user,String begin_date, String end_date, int nPage, int nSpage, int show_type);
	
	public DeliveryNote getDeliveryNote();
	
	/**
	 * 执行送货单保存
	 * @param deliveryNote
	 */
	public void doSave(User user,DeliveryNote deliveryNote);
	
	/**
	 * 执行送货单作废
	 * @param deliveryNoteId
	 */
	public void doAbort(User user,String deliveryNoteId);
	
	/**
	 * 加载送货单单个产品
	 */
	public void doLoadDetail(User user,String deliveryNoteId);
	
	public void destroy();

}
