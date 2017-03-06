package com.bangware.shengyibao.deliverynote.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;

import java.util.List;



public interface DeliveryNoteDetailView extends BaseView {
	void addDeliveryNoteDetailProduct(List<DeliveryNoteGoods> newList);
}
