package com.bangware.shengyibao.deliverynote.model;


import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteListener;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteMonthQueryListener;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteSaveListener;
import com.bangware.shengyibao.user.model.entity.User;


/**
 * 送货单数据模型
 * @author luming.tang
 *
 */
public interface DeliveryNoteModel {
	/**
	 * 根据销售员ID和开单时间查询送货单
	 * @param salerId
	 * @param startDate
	 */
	public void query(String requestTag, String begin_date, String end_date, int nPage, int nSpage, int show_type, User user, DeliveryNoteListener queryListener);
	/**
	 * 保存送货单
	 * @param deliveryNote
	 */
	public void save(User user,String requestTag, DeliveryNote deliveryNote, OnDeliveryNoteSaveListener listener);
	/**
	 * 根据送货单ID作废
	 * @param deliveryNoteId
	 */
	public void abort(String requestTag, String deliveryNoteId, User user, DeliveryNoteListener queryListener);
	
	/**
	 * 加载送货单详情
	 * @param deliveryNoteId
	 */
	public void load(String requestTag, String deliveryNoteId, User user, DeliveryNoteListener detailListener);
	
	public void loadDeliveryNoteMonthQuery(String requestTag, User user, String begin_date, String end_date, int nPage, int nSpage, int show_type, OnDeliveryNoteMonthQueryListener listener);

}
