package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;

import java.util.List;


public interface OnReturnNoteDetailListener {
	/**
	 * 退货单产品详情响应
	 * @param returnNote
	 */
	
	public void onLoadSuccess(List<ReturnNoteGoods> noteGoodsList);
	public void onLoadMessage(String message);
}
