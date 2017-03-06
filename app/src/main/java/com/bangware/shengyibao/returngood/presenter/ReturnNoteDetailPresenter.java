package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface ReturnNoteDetailPresenter {
	/**
	 * 加载退货单产品数据
	 */
	public void doLoadReturnDetail(User user,String returnNoteId);
	
	/**
	 * 作废产品单据
	 */
	public void doAbort(User user,String returnNoteId);
	
	public void destroy();
}
