package com.bangware.shengyibao.manager.shoptype.presenter.impl;

import com.bangware.shengyibao.manager.shoptype.helper.GroupData;
import com.bangware.shengyibao.manager.shoptype.model.ShopTypeModel;
import com.bangware.shengyibao.manager.shoptype.model.impl.ShopTypeModelImpl;
import com.bangware.shengyibao.manager.shoptype.presenter.ShopTypeListener;
import com.bangware.shengyibao.manager.shoptype.presenter.ShopTypePresenter;
import com.bangware.shengyibao.manager.shoptype.view.ShopTypeView;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by bangware on 2016/9/2.
 */
public class ShopTypePresenterImpl implements ShopTypePresenter,ShopTypeListener{
    public  static final String  REQUEST_TAG = "ShopType";
    private ShopTypeModel model;
    private ShopTypeView view;
    private String requestTag;

    public ShopTypePresenterImpl(ShopTypeView view) {
        this.model = new ShopTypeModelImpl();
        this.view = view;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }

    //请求成功加载数据
    @Override
    public void onLoadGroupSuccess(List<GroupData> groupDataList) {
        view.hideLoading();
        view.doShopTypeLoadSuccess(groupDataList);
    }

    @Override
    public void onLoadGroupFailure(String errorMessage) {
        view.hideLoading();
        view.showMessage(errorMessage);
    }

    //发起请求
    @Override
    public void loadShopTypeData() {
        view.showLoading();
        model.onLoadShopType(requestTag, AppContext.getInstance().getUser(),this);
    }

    //销毁请求队列
    @Override
    public void destory() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
