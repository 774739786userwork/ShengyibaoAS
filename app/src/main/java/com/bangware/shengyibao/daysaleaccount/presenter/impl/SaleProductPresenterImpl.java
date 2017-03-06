package com.bangware.shengyibao.daysaleaccount.presenter.impl;

import com.bangware.shengyibao.daysaleaccount.model.SaleAccountModel;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;
import com.bangware.shengyibao.daysaleaccount.model.impl.SaleAccountModelImpl;
import com.bangware.shengyibao.daysaleaccount.presenter.OnSaleProductListener;
import com.bangware.shengyibao.daysaleaccount.presenter.SaleProductPresenter;
import com.bangware.shengyibao.daysaleaccount.view.SaleProductView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by ccssll on 2016/8/12.
 */
public class SaleProductPresenterImpl implements SaleProductPresenter,OnSaleProductListener{
    public  static final String  REQUEST_TAG = "SaleAccountProduct";
    private SaleAccountModel model;
    private SaleProductView pView;
    private String requestTag;

    public SaleProductPresenterImpl(
            SaleProductView pView) {
        super();
        this.model = new SaleAccountModelImpl();
        this.pView = pView;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }
    @Override
    public void onLoadSalesProductSuccess(List<SaleAccountProductBean> productBeanList) {
        pView.hideLoading();
        pView.doLoadSaleProductData(productBeanList);
    }

    @Override
    public void onLoadSalesProductFailure(String errorMessage) {
        pView.showMessage(errorMessage);
    }

    @Override
    public void loadSalesAccountData(User user,String saler_journals_id) {
        pView.showLoading();
        model.onloadProductAccount(requestTag, user,saler_journals_id,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
