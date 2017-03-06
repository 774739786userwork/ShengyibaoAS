package com.bangware.shengyibao.salesamount.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.salesamount.model.entity.GroupItem;

import java.util.List;

/**
 * 组内排名
 * Created by bangware on 2016/8/25.
 */
public interface GroupRankingView extends BaseView{
    void doGroupRankingLoadSuccess(List<GroupItem> groupItemList);
}
