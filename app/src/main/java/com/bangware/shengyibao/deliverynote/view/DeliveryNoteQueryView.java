package com.bangware.shengyibao.deliverynote.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;

import java.util.List;



public interface DeliveryNoteQueryView extends BaseView {
//	void doChanged(DeliveryNote deliveryNote);
	
	 void loadDeliveryNoteData(List<DeliveryNote> noteList);
}
