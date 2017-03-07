package com.bangware.shengyibao.customer.shoptypeflowlayout.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.shoptypeflowlayout.flowlayout.Flow;

import java.util.List;

/**
 * Created by bangware on 2016/10/12.
 */
public interface ShopTypeFlowView extends BaseView {
    void doShopTypeLoadSuccess(List<Flow> groupDataList);
}
