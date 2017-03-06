package com.bangware.shengyibao.daysaleaccount.presenter;

import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/12.
 */
public interface OnSaleProductListener {
    void onLoadSalesProductSuccess(List<SaleAccountProductBean> productBeanList);
    void onLoadSalesProductFailure(String errorMessage);
}
