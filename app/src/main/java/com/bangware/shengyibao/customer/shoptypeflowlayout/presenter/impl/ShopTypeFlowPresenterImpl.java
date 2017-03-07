package com.bangware.shengyibao.customer.shoptypeflowlayout.presenter.impl;

import com.bangware.shengyibao.customer.shoptypeflowlayout.flowlayout.Flow;
import com.bangware.shengyibao.customer.shoptypeflowlayout.model.ShopFlowModel;
import com.bangware.shengyibao.customer.shoptypeflowlayout.model.impl.ShopFlowModelImpl;
import com.bangware.shengyibao.customer.shoptypeflowlayout.presenter.ShopFlowListener;
import com.bangware.shengyibao.customer.shoptypeflowlayout.presenter.ShopTypeFlowPresenter;
import com.bangware.shengyibao.customer.shoptypeflowlayout.view.ShopTypeFlowView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by bangware on 2016/10/12.
 */
public class ShopTypeFlowPresenterImpl implements ShopTypeFlowPresenter,ShopFlowListener {
    public  static final String  REQUEST_TAG = "ShopFlow";
    private ShopFlowModel model;
    private ShopTypeFlowView view;
    private String requestTag;

    public ShopTypeFlowPresenterImpl(ShopTypeFlowView view) {
        this.model = new ShopFlowModelImpl();
        this.view = view;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }

    @Override
    public void onLoadGroupSuccess(List<Flow> dataList) {
        view.hideLoading();
        view.doShopTypeLoadSuccess(dataList);
    }

    @Override
    public void onLoadGroupFailure(String errorMessage) {
        view.hideLoading();
        view.showMessage(errorMessage);
    }

    @Override
    public void loadShopTypeData(User user) {
        view.showLoading();
        model.onLoadShopType(requestTag, user,this);
    }

    @Override
    public void destory() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
