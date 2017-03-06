package com.bangware.shengyibao.ladingbilling.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.model.Product;

import java.util.List;

/**
 * Created by bangware on 2016/8/22.
 */
public interface StockQueryView extends BaseView{
    void loadProductStockData(List<Product> productstockList);
}
