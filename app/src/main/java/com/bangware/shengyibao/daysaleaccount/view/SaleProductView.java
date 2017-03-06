package com.bangware.shengyibao.daysaleaccount.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/12.
 */
public interface SaleProductView extends BaseView{
    void doLoadSaleProductData(List<SaleAccountProductBean> productBeanList);
}
