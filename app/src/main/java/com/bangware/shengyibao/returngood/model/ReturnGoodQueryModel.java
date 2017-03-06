package com.bangware.shengyibao.returngood.model;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteSaveListener;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.presenter.OnReturnGoodListener;
import com.bangware.shengyibao.returngood.presenter.OnReturnNoteDetailListener;
import com.bangware.shengyibao.returngood.presenter.OnReturnNoteListener;
import com.bangware.shengyibao.returngood.presenter.OnReturnsProductListener;
import com.bangware.shengyibao.user.model.entity.User;


public interface ReturnGoodQueryModel {
	//退货单查询方法
	public void loadReturnGood(String requestTag, String begin_date, String end_date, int nPage, int nSpage, User user, OnReturnGoodListener goodListener);

	//退货单产品详情
	public void loadReturnNoteDetail(String requestTag, String returnNoteId, User user, OnReturnNoteDetailListener detailListener);

	//作废退货单据
	public void abortReturnNote(String requestTag, String returnNoteId, User user);

	//退货开单
	public void loadReturnsProduct(String requestTag,User user, OnReturnsProductListener Listener);

	//保存退货单
	public void save(String requestTag, ReturnNote returnNote,User user, OnReturnNoteListener listener);
}
