package com.bangware.shengyibao.main.presenter;


import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;

public interface OnMainSalerListener {
	void OnLoadToDaySummarySuccess(ToDaySummary daySummary);
	void OnLoadThisMonthSummarySuccess(ThisMonthSummary monthSummary);
	void showFailure(String errorMessage);
}
