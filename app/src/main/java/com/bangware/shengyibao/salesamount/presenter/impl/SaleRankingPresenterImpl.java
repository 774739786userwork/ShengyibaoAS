package com.bangware.shengyibao.salesamount.presenter.impl;

import com.bangware.shengyibao.salesamount.model.SaleRankingModel;
import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;
import com.bangware.shengyibao.salesamount.model.impl.SaleRankingModelImpl;
import com.bangware.shengyibao.salesamount.presenter.OnSaleRankingListener;
import com.bangware.shengyibao.salesamount.presenter.SaleRankingPresenter;
import com.bangware.shengyibao.salesamount.view.SaleRankingView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by ccssll on 2016/8/10.
 */
public class SaleRankingPresenterImpl implements SaleRankingPresenter,OnSaleRankingListener{
    public  static final String  REQUEST_TAG = "SaleRanking";
    private SaleRankingModel model;
    private SaleRankingView view;
    private String requestTag;

    public SaleRankingPresenterImpl(
            SaleRankingView view) {
        super();
        this.model = new SaleRankingModelImpl();
        this.view = view;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }

    @Override
    public void onLoadSaleRankingSuccess(List<SaleRankingBean> rankingBeenList) {
        view.hideLoading();
        view.doSaleRankingLoadSuccess(rankingBeenList);
    }

    @Override
    public void onLoadSaleRankingFailure(String errorMessage) {
        view.showLoading();
        view.showMessage(errorMessage);
    }

    @Override
    public void loadSaleRankingData(User user,String begin_date, String end_date) {
        view.showLoading();
        model.loadSaleRanking(requestTag, user,begin_date,end_date,this);
    }

    @Override
    public void destory() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
