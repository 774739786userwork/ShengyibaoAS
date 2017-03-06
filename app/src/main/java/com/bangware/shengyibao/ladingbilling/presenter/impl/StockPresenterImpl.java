package com.bangware.shengyibao.ladingbilling.presenter.impl;

import com.bangware.shengyibao.ladingbilling.model.StockQueryModel;
import com.bangware.shengyibao.ladingbilling.model.impl.StockQueryModelImpl;
import com.bangware.shengyibao.ladingbilling.presenter.StockListener;
import com.bangware.shengyibao.ladingbilling.presenter.StockPresenter;
import com.bangware.shengyibao.ladingbilling.view.StockQueryView;
import com.bangware.shengyibao.shopcart.model.entity.StockInfo;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * Created by bangware on 2016/8/22.
 */
public class StockPresenterImpl implements StockPresenter,StockListener{
    public static final String REQUEST_TAG = "Stock";
    private StockQueryModel queryModel;
    private StockQueryView queryView;
    private String requestTag;

    public StockPresenterImpl(StockQueryView queryView) {
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
        this.queryView=queryView;
        this.queryModel = new StockQueryModelImpl();
    }

    @Override
    public void onStockLoaded(StockInfo stockInfo) {
        queryView.hideLoading();
        queryView.loadProductStockData(stockInfo.getProducts());
    }

    @Override
    public void onErrors(String errorMessage) {
        queryView.hideLoading();
        queryView.showMessage(errorMessage);
    }

    @Override
    public void onLoadStock(User user) {
        queryView.showLoading();
        queryModel.onQueryStockinfo(requestTag, user,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
