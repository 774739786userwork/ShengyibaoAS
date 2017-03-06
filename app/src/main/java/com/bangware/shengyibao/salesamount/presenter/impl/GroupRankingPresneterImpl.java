package com.bangware.shengyibao.salesamount.presenter.impl;

import com.bangware.shengyibao.salesamount.model.SaleRankingModel;
import com.bangware.shengyibao.salesamount.model.entity.GroupItem;
import com.bangware.shengyibao.salesamount.model.impl.SaleRankingModelImpl;
import com.bangware.shengyibao.salesamount.presenter.GroupRankingPresenter;
import com.bangware.shengyibao.salesamount.presenter.OnGroupRankingListener;
import com.bangware.shengyibao.salesamount.view.GroupRankingView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by bangware on 2016/8/25.
 */
public class GroupRankingPresneterImpl implements GroupRankingPresenter,OnGroupRankingListener{
    public  static final String  REQUEST_TAG = "GroupRanking";
    private SaleRankingModel gModel;
    private GroupRankingView gView;
    private String requestTag;

    public GroupRankingPresneterImpl(
            GroupRankingView gView) {
        super();
        this.gModel = new SaleRankingModelImpl();
        this.gView = gView;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }
    @Override
    public void loadGroupRankingData(User user,String begin_date, String end_date) {
        gView.showLoading();
        gModel.loadGroupRanking(requestTag, user,begin_date,end_date,this);
    }

    @Override
    public void destory() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }

    @Override
    public void onLoadGroupSuccess(List<GroupItem> groupItemList) {
        gView.hideLoading();
        gView.doGroupRankingLoadSuccess(groupItemList);

    }

    @Override
    public void onLoadGroupFailure(String errorMessage) {
        gView.hideLoading();
    }
}
