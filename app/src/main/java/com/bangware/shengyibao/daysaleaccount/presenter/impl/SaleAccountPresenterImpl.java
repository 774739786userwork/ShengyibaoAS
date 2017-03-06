package com.bangware.shengyibao.daysaleaccount.presenter.impl;

import com.bangware.shengyibao.daysaleaccount.model.SaleAccountModel;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;
import com.bangware.shengyibao.daysaleaccount.model.impl.SaleAccountModelImpl;
import com.bangware.shengyibao.daysaleaccount.presenter.OnSaleAccountListener;
import com.bangware.shengyibao.daysaleaccount.presenter.SaleAccountPresenter;
import com.bangware.shengyibao.daysaleaccount.view.SaleAccountView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 *
 * Created by ccssll on 2016/8/11.
 */
public class SaleAccountPresenterImpl implements SaleAccountPresenter,OnSaleAccountListener{

    public  static final String  REQUEST_TAG = "SalesAccountList";
    private SaleAccountModel model;
    private SaleAccountView view;
    private String requestTag;

    public SaleAccountPresenterImpl(
            SaleAccountView view) {
        super();
        this.model = new SaleAccountModelImpl();
        this.view = view;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }
    @Override
    public void onLoadSalesAccountSuccess(List<SaleAccountListBean> saleAccounts) {
        view.hideLoading();
        view.doLoadSaleAccountData(saleAccounts);
    }

    @Override
    public void onLoadSalesAccountFailure(String errorMessage) {
        view.showLoading(errorMessage);
    }

    @Override
    public void loadSalesAccountData(User user,String begin_date, String end_date, int nPage, int nSpage) {
        view.showLoading();
        model.onloadAccount(requestTag, user,begin_date,end_date,nPage,nSpage,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
