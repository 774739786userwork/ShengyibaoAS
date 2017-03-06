package com.bangware.shengyibao.salesamount.presenter;


import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/8/10.
 */
public interface SaleRankingPresenter {
    void loadSaleRankingData(User user,String begin_date, String end_date);
    void destory();
}
