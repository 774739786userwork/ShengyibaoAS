package com.bangware.shengyibao.ladingbilling.presenter;

import com.bangware.shengyibao.shopcart.model.entity.StockInfo;

/**
 * 余货
 * Created by bangware on 2016/8/22.
 */
public interface StockListener {
    public void onStockLoaded(StockInfo stockInfo);
    public void onErrors(String errorMessage);
}
