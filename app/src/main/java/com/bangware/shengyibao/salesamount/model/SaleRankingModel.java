package com.bangware.shengyibao.salesamount.model;

import com.bangware.shengyibao.salesamount.presenter.OnGroupRankingListener;
import com.bangware.shengyibao.salesamount.presenter.OnSaleRankingListener;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/8/10.
 */
public interface SaleRankingModel {
    //销售排名
    void loadSaleRanking(String requestTag, User user, String begin_date, String end_date,OnSaleRankingListener saleRankingListener);
    //组内排名
    void loadGroupRanking(String requestTag, User user, String begin_date, String end_date, OnGroupRankingListener groupRankingListener);
}
