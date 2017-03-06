package com.bangware.shengyibao.returngood.presenter.impl;

import com.bangware.shengyibao.returngood.model.ReturnGoodQueryModel;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.model.impl.ReturnGoodQueryModelImpl;
import com.bangware.shengyibao.returngood.presenter.OnReturnNoteListener;
import com.bangware.shengyibao.returngood.presenter.RefundPrestener;
import com.bangware.shengyibao.returngood.view.RefundView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * Created by ccssll on 2016/8/2.
 */
public class RefundPrestenerImpl implements RefundPrestener,OnReturnNoteListener{
    public static final String REQUEST_TAG = "Refund";
    private String requestTag;
    private RefundView refundView;
    private ReturnGoodQueryModel returnGoodQueryModel;

    public RefundPrestenerImpl(RefundView refundView) {
        requestTag = REQUEST_TAG + System.currentTimeMillis();
        this.refundView = refundView;
        returnGoodQueryModel = new ReturnGoodQueryModelImpl();
    }

    @Override
    public void onSaveSuccess(ReturnNote returnNote) {
        refundView.hideLoading();
        refundView.doSaveSuccess(returnNote);
    }

    @Override
    public void onError(String message) {
        refundView.hideLoading();
        refundView.showMessage(message);
    }

    @Override
    public void doSave(User user,ReturnNote returnNote) {
        refundView.showLoading("正在提交退货单，请稍后！");
        returnGoodQueryModel.save(requestTag, returnNote, user,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
