package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface RefundPrestener {
    /**
     * 退货单保存
     * @param returnNote
     */
    public void doSave(User user,ReturnNote returnNote);

    public void destroy();
}
