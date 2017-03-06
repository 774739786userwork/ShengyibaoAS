package com.bangware.shengyibao.purchaseorder.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;

import java.util.List;


public interface PurchaseOrderDetailView extends BaseView {
	void addPurchaseOrderDetailProduct(List<DeliveryNoteGoods> newList);
    void save();
}
