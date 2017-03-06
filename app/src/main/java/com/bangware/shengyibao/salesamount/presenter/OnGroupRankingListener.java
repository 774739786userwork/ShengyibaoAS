package com.bangware.shengyibao.salesamount.presenter;

import com.bangware.shengyibao.salesamount.model.entity.GroupItem;

import java.util.List;

/**
 * 组内排名
 * Created by bangware on 2016/8/25.
 */
public interface OnGroupRankingListener {
    void onLoadGroupSuccess(List<GroupItem> groupItemList);
    void onLoadGroupFailure(String errorMessage);
}
