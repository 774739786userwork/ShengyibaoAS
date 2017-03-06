package com.bangware.shengyibao.ladingbilling.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.ladingbilling.model.entity.LadingbillingQuery;

import java.util.List;


public interface LadingbillingQueryView extends BaseView{
	void addLadingbillingData(List<LadingbillingQuery> ladingbillingList);
	void showFailureMsg(String errorMessage);
}
