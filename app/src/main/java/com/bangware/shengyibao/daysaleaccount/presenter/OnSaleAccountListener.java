package com.bangware.shengyibao.daysaleaccount.presenter;


import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/11.
 */
public interface OnSaleAccountListener {
    void onLoadSalesAccountSuccess(List<SaleAccountListBean> saleAccountlist);
    void onLoadSalesAccountFailure(String errorMessage);
}
