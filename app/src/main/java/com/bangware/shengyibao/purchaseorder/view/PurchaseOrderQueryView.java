package com.bangware.shengyibao.purchaseorder.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;

import java.util.List;


public interface PurchaseOrderQueryView extends BaseView {
	 void loadPurchaseOrderQueryData(List<DeliveryNote> noteList);
}
