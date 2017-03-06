package com.bangware.shengyibao.manager.shoptype.model;

import com.bangware.shengyibao.manager.shoptype.presenter.ShopTypeListener;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * 店面类型与位置的请求
 * Created by bangware on 2016/9/2.
 */
public interface ShopTypeModel {
    void onLoadShopType(String requestTag, User user, ShopTypeListener typeListener);
}
