package com.bangware.shengyibao.returngood.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;

import java.util.List;


public interface ReturnGoodView extends BaseView {
	void loadReturnGoodData(List<ReturnNote> goodsBeanList);
	void showFailureMsg(String errorMessage);
}
