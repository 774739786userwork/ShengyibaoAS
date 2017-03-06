package com.bangware.shengyibao.daysaleaccount.presenter;


import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/8/11.
 */
public interface SaleAccountPresenter {
    void loadSalesAccountData(User user,String begin_date, String end_date, int nPage, int nSpage);
    void destroy();
}
