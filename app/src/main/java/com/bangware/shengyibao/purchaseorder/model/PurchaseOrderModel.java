package com.bangware.shengyibao.purchaseorder.model;


import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteListener;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteMonthQueryListener;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteSaveListener;
import com.bangware.shengyibao.purchaseorder.presenter.OnPurchaseOrderQueryListener;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderListener;
import com.bangware.shengyibao.user.model.entity.User;


/**
 * 订货单数据模型
 * @author zcb
 *
 */
public interface PurchaseOrderModel {
	/**
	 * 根据销售员ID和开单时间查询送货单
	 * @param salerId
	 * @param startDate
	 */
	public void query(String requestTag, String begin_date, String end_date, int nPage, int nSpage, int show_type, User user, PurchaseOrderListener queryListener);

	/**
	 * 加载送货单详情
	 * @param deliveryNoteId
	 */
	public void load(String requestTag, String deliveryNoteId, User user, PurchaseOrderListener detailListener);

	/**
	 * 订货单确认配送
	 * @param wechat_payment
	 * @param Alipay
	 * @param bank_receive_total_sum
	 * @param cash_payment
     */
	public void update_purchase_order(User user,DeliveryNote deliveryNote,double wechat_payment,double Alipay,double bank_receive_total_sum,double cash_payment,PurchaseOrderListener queryListener,String requestTag);

}
