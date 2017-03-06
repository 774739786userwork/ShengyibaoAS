package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.returngood.model.entity.ReturnNote;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface OnReturnNoteListener {
    public void onSaveSuccess(ReturnNote returnNote);

    public void onError(String message);

}
