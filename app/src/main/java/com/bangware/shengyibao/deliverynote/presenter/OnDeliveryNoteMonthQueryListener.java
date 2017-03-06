package com.bangware.shengyibao.deliverynote.presenter;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;

import java.util.List;


public interface OnDeliveryNoteMonthQueryListener {
	void showDialog();
	void hideDialog();
	void OnLoadNoteMonthQuerySuccess(List<DeliveryNoteMonthQuery> list);
	void showFailure(String errorMessage);
}
