package com.bangware.shengyibao.deliverynote.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface DeliveryNoteMonthQueryPresenter {
	public void loadDeliveryNoteMonthQuery(User user, String begin_date, String end_date, int nPage, int nSpage, int show_type);
	public void destroy();
}
