package com.bangware.shengyibao.purchaseorder.presenter;


import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * 订货单Presenter类
 * @author luming.tang
 *
 */
public interface PurchaseOrderPresenter {
	/**
	 * 执行订货单查询
	 * @param salerId
	 * @param startDate
	 * @param endDate
	 */
	public void doLoad(User user,String begin_date, String end_date, int nPage, int nSpage, int show_type);
	
	public DeliveryNote getPurchaseOrder();

	/**
	 * 加载订货单单个产品
	 */
	public void doLoadDetail(User user,String deliveryNoteId);

	public void update_purchase_order(User user,DeliveryNote deliveryNote,double wechat_payment,double Alipay,double bank_receive_total_sum,double cash_payment);
	
	public void destroy();

}
