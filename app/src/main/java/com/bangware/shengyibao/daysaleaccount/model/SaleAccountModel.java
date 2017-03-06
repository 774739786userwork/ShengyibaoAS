package com.bangware.shengyibao.daysaleaccount.model;

import com.bangware.shengyibao.daysaleaccount.presenter.OnSaleAccountListener;
import com.bangware.shengyibao.daysaleaccount.presenter.OnSaleProductListener;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/8/9.
 */
public interface SaleAccountModel {

    //日销售清单查询列表
    void onloadAccount(String requestTag, User user, String begin_date, String end_date, int nPage, int nSpage,
                       OnSaleAccountListener saleAccountListener);

    //日销售清单产品查询
    void onloadProductAccount(String requestTag, User user, String saler_journals_id, OnSaleProductListener productListener);
}
