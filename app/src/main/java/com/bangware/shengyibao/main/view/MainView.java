package com.bangware.shengyibao.main.view;



import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;


public interface MainView extends BaseView {
	 void doTodaySummaryLoadSuccess(ToDaySummary bean);
	 void doThisMonthSummaryLoadSuccess(ThisMonthSummary bean);
}
