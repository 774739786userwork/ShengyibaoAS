package com.bangware.shengyibao.ladingbilling.model;


import com.bangware.shengyibao.ladingbilling.presenter.OnLadingBillingListener;
import com.bangware.shengyibao.user.model.entity.User;


public interface LadingbillingQueryModel {
	void LoadLadingBillingData(String requestTag, String begin_date, String end_date, int nPage, int nSpage, User user, OnLadingBillingListener billingListener);
}
