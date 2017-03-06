package com.bangware.shengyibao.salesamount.presenter;

import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;

import java.util.List;



public interface OnSalesAmountListener {
	void onLoadSalesAmountSuccess(List<MonthProductAmount> monthProductAmounts);
	void onLoadSalesAmountFailure(String errorMessage);
}
