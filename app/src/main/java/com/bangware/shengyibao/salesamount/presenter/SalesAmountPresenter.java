package com.bangware.shengyibao.salesamount.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface SalesAmountPresenter {
	public void loadSalesAmount(User user, String begin_date, String end_date, int nPage, int nSpage);
	public void destroy();
}
