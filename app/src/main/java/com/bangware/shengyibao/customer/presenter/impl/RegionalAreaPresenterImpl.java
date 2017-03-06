package com.bangware.shengyibao.customer.presenter.impl;

import android.util.Log;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.RegionalArea;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.OnRegionalListener;
import com.bangware.shengyibao.customer.presenter.RegionalAreaPresenter;
import com.bangware.shengyibao.customer.view.RegionalAreaView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by ccssll on 2016/7/31.
 */
public class RegionalAreaPresenterImpl implements RegionalAreaPresenter,OnRegionalListener{

    public  static final String  REQUEST_TAG = "Customer";
    private String requestTag;
    private CustomerModel mCustomerModel;
    private RegionalAreaView mAreaView;

    public RegionalAreaPresenterImpl(RegionalAreaView mAreaView) {
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
        this.mAreaView=mAreaView;
        this.mCustomerModel = new CustomerModelImpl();
    }
    @Override
    public void onLoadAreaDataSuccess(List<RegionalArea> regionalAreas) {
        mAreaView.hideLoading();
        mAreaView.queryRegionalArea(regionalAreas);
    }

    @Override
    public void onLoadAreaDataFailure(String errorMessage) {

    }

    @Override
    public void loadAreaData(User user,String province) {
        mAreaView.showLoading();
        mCustomerModel.queryRegionalArea(requestTag,province, user,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
