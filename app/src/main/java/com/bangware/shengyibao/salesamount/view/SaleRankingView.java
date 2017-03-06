package com.bangware.shengyibao.salesamount.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/10.
 */
public interface SaleRankingView extends BaseView{
    void doSaleRankingLoadSuccess(List<SaleRankingBean> rankingBeanList);
}
