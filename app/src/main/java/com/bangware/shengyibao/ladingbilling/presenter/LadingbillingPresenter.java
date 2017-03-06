package com.bangware.shengyibao.ladingbilling.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface LadingbillingPresenter {
	void loadLadingBilling(User user,String begin_date, String end_date, int nPage, int nSpage);
	void destroy();
}
