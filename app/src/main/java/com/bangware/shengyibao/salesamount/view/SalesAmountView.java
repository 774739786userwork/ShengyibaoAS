package com.bangware.shengyibao.salesamount.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;

import java.util.List;


public interface SalesAmountView extends BaseView {
	void doSalesAmountLoadSuccess(List<MonthProductAmount> monthProductAmountslist);
}
