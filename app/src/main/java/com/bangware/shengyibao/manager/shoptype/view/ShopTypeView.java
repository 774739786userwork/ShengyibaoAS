package com.bangware.shengyibao.manager.shoptype.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.manager.shoptype.helper.GroupData;

import java.util.List;

/**
 *
 * Created by bangware on 2016/9/2.
 */
public interface ShopTypeView extends BaseView{
    void doShopTypeLoadSuccess(List<GroupData> groupDataList);
}
