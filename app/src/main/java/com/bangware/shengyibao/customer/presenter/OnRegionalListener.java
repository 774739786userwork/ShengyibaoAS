package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.RegionalArea;

import java.util.List;

/**
 * 行政区域
 * Created by ccssll on 2016/7/31.
 */
public interface OnRegionalListener {
    void onLoadAreaDataSuccess(List<RegionalArea> regionalAreass);
    void onLoadAreaDataFailure(String errorMessage);
}
