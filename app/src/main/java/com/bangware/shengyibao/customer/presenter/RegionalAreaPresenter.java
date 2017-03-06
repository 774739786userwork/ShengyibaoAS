package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/7/31.
 */
public interface RegionalAreaPresenter {
    void loadAreaData(User user,String province);

    void destroy();
}
