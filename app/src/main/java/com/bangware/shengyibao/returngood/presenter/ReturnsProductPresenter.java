package com.bangware.shengyibao.returngood.presenter;

import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.entity.ReturnCart;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by Administrator on 2016/7/30.
 */
public interface ReturnsProductPresenter {
    //退货开单的所有产品
    public void loadReturnsProduct(User user);
    /**
     * 添加商品
     * @param product
     * @param salesVolume
     * @param giftsVolume
     * @param price
     */
    public void addGoods(ReturnNoteGoods goods);

    /**
     * 删除商品
     * @param product
     */
    public void removeGoods(Product product);
    /**
     * 清空购物车
     */
    public void removeAllGoods();
    /**
     * 获取购物车
     * @return
     */
    public ReturnCart getReturnCart();
    /**
     * 销毁
     */

    void destroy();
}
