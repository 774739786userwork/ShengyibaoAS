package com.bangware.shengyibao.salesamount.presenter;

import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/10.
 */
public interface OnSaleRankingListener {
    void onLoadSaleRankingSuccess(List<SaleRankingBean> rankingBeenList);
    void onLoadSaleRankingFailure(String errorMessage);
}
