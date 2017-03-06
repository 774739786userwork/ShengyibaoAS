package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.returngood.model.entity.ReturnNote;

import java.util.List;


public interface OnReturnGoodListener {
	void onLoadDataSuccess(List<ReturnNote> returnGoodlist);

    void onLoadDataFailure(String errorMessage);
}
