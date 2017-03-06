package com.bangware.shengyibao.ladingbilling.presenter;

import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by bangware on 2016/8/22.
 */
public interface StockPresenter {

    void onLoadStock(User user);
    void destroy();
}
