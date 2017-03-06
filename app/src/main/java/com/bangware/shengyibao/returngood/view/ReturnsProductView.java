package com.bangware.shengyibao.returngood.view;


import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.returngood.model.entity.ReturnCart;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;

import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public interface ReturnsProductView extends BaseView{
    void doChanged(ReturnCart returnCart);
    public void queryReturnProduct(List<ProductsTypes> productses);
}
