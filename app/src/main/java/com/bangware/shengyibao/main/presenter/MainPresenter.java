package com.bangware.shengyibao.main.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface MainPresenter {
	/**
	 * 加载首页数据
	 */
	public void loadToDaySummary(User user);
	
	
	public void loadThisMonthSummary(User user);
	
	public void destroy();
}
