package com.bangware.shengyibao.customer.shoptypeflowlayout.presenter;

import com.bangware.shengyibao.customer.shoptypeflowlayout.flowlayout.Flow;

import java.util.List;

/**
 * Created by bangware on 2016/10/12.
 */
public interface ShopFlowListener {
    void onLoadGroupSuccess(List<Flow> dataList);
    void onLoadGroupFailure(String errorMessage);
}
