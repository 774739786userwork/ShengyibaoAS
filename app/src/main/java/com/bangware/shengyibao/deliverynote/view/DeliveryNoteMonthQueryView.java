package com.bangware.shengyibao.deliverynote.view;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;

import java.util.List;


public interface DeliveryNoteMonthQueryView {
	 void showLoading();

	 void hideLoading();
     
	 void showError(String errorMessage);
	 
	 void doDeliveryNoteMonthQuerySuccess(List<DeliveryNoteMonthQuery> list);
}
