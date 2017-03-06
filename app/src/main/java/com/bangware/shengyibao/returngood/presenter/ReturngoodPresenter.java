package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface ReturngoodPresenter {
	void loadreturnGood(User user,String begin_date, String end_date, int nPage, int nSpage);
	void destroy();
}
