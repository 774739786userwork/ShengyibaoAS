package com.bangware.shengyibao.activity.Suggest.presenter.impl;

import com.bangware.shengyibao.activity.Suggest.model.SuggestModel;
import com.bangware.shengyibao.activity.Suggest.model.impl.SuggestModelImpl;
import com.bangware.shengyibao.activity.Suggest.presenter.SuggestListener;
import com.bangware.shengyibao.activity.Suggest.presenter.SuggestPresenter;
import com.bangware.shengyibao.activity.Suggest.view.SuggestView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * Created by bangware on 2016/8/23.
 */
public class SuggestPresenterImpl implements SuggestPresenter,SuggestListener{
    public  static final String  REQUEST_TAG = "Suggest";
    private String requestTag;
    private SuggestModel sModel;
    private SuggestView  sView;

    public SuggestPresenterImpl(SuggestView  sView) {
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
        this.sView = sView;
        this.sModel = new SuggestModelImpl();
    }
    @Override
    public void onSubmitSuccess(String successMessage) {
        sView.hideLoading();
        sView.onLoadSuccess(successMessage);
    }

    @Override
    public void onSubmitFailure(String errorMessage) {
        sView.hideLoading();
        sView.showMessage(errorMessage);
    }

    @Override
    public void loadData(User user,String content) {
        sView.showLoading();
        sModel.onLoadsubmit(requestTag,content, user,this);
    }

    @Override
    public void destory() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
