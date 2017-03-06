package com.bangware.shengyibao.returngood.presenter;


import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;

import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 *
 */
public interface OnReturnsProductListener {
    public void onLoadMessage(String message);
    public void onLoadDataSuccess(List<ProductsTypes> productsList);
}
