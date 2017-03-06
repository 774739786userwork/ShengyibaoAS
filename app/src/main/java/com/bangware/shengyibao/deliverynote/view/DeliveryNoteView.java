package com.bangware.shengyibao.deliverynote.view;

/**
 * 送货单视图接口
 * @author luming.tang
 *
 */
public interface DeliveryNoteView {
	 void showLoading();

	 void hideLoading();

	 void showError(String errorMessage);
}
