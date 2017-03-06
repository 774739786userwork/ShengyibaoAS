package com.bangware.shengyibao.returngood.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface RefundView extends BaseView {
    void  doSaveSuccess(ReturnNote returnNote);
}
