package com.bangware.shengyibao.daysaleaccount.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/11.
 */
public interface SaleAccountView extends BaseView{
    void doLoadSaleAccountData(List<SaleAccountListBean> saleAccountList);
}
