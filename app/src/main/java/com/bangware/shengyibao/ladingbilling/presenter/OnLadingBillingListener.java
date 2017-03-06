package com.bangware.shengyibao.ladingbilling.presenter;

import com.bangware.shengyibao.ladingbilling.model.entity.LadingbillingQuery;

import java.util.List;


public interface OnLadingBillingListener {
	void onLoadDataSuccess(List<LadingbillingQuery> ladingbillinglist);

    void onLoadDataFailure(String errorMessage);
}
