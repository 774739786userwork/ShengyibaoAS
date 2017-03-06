package com.bangware.shengyibao.main.model;


import com.bangware.shengyibao.main.presenter.OnMainSalerListener;
import com.bangware.shengyibao.user.model.entity.User;


public interface MainModel {
	/**
	 * 加载当天摘要
	 * @param employee_id
	 * @param token
	 * @param onMainSalerListener
	 */
	public void loadToDaySummary(String requestTag, User user, OnMainSalerListener onMainSalerListener);
	/**
	 * 加载本月摘要
	 * @param employee_id
	 * @param organization_id
	 * @param token
	 * @param onMainSalerListener
	 */
	public void loadThisMonthSummary(String requestTag, User user, OnMainSalerListener onMainSalerListener);
}
