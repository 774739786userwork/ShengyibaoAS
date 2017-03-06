package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerNearByPresenter;
import com.bangware.shengyibao.customer.presenter.OnNearByCustomerListener;
import com.bangware.shengyibao.customer.view.CustomerNearByView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by ccssll on 2016/8/3.
 */
public class CustomerNearByPresenterImpl implements CustomerNearByPresenter,OnNearByCustomerListener{
    public  static final String  REQUEST_TAG = "NearByCustomer";
    private String requestTag;
    private CustomerModel mCustomerModel;
    private CustomerNearByView mNearByView;

    public CustomerNearByPresenterImpl(CustomerNearByView mNearByView) {
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
        this.mNearByView=mNearByView;
        this.mCustomerModel = new CustomerModelImpl();
    }
    @Override
    public void loadNearByCustomerData(User user,int nPage, int nSpage, String latitude, String longitude) {
        mNearByView.showLoading();
        mCustomerModel.loadNearByCustomer(requestTag,user,nPage,nSpage,latitude,longitude,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }

    @Override
    public void onLoadNearbyCustomer(List<Customer> customers) {
        mNearByView.hideLoading();
        mNearByView.queryNearByCustomer(customers);
    }

    @Override
    public void onLoadDataFailure(String errorMessage) {
        mNearByView.hideLoading();
        mNearByView.showLoadFailureMsg(errorMessage);
    }
}
