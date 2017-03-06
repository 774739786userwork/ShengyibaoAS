package com.bangware.shengyibao.daysaleaccount.presenter;

import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/8/12.
 */
public interface SaleProductPresenter {
    void loadSalesAccountData(User user,String saler_journals_id);
    void destroy();
}
