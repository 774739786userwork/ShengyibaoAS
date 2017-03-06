package com.bangware.shengyibao.manager.shoptype.presenter;

import com.bangware.shengyibao.manager.shoptype.helper.GroupData;

import java.util.List;

/**
 * Created by bangware on 2016/9/2.
 */
public interface ShopTypeListener {
    void onLoadGroupSuccess(List<GroupData> groupDataList);
    void onLoadGroupFailure(String errorMessage);
}
