package com.bangware.shengyibao.user.presenter.impl;

import android.util.Log;

import com.bangware.shengyibao.user.model.UserModel;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.model.impl.UserModelImpl;
import com.bangware.shengyibao.user.presenter.OnUpdatePasswordListener;
import com.bangware.shengyibao.user.presenter.UpdatePasswordPresenter;
import com.bangware.shengyibao.user.view.UpdatePasswordView;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * Created by Administrator on 2016/8/10.
 */
public class UpdatePasswordPresenterImpl implements UpdatePasswordPresenter,OnUpdatePasswordListener{

    public static final String REQUEST_TAG = "UpdatePassword";
    private UserModel userModel;
    private UpdatePasswordView updatePasswordView;
    private String requestTag;

    public UpdatePasswordPresenterImpl(UpdatePasswordView updatePasswordView) {
        this.userModel = new UserModelImpl();
        this.updatePasswordView = updatePasswordView;
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
    }

    @Override
    public void onUpdateSuccess() {
        updatePasswordView.hideLoading();
        updatePasswordView.updateSuccess();
    }

    @Override
    public void onUpdateError(String errorMessage) {
           updatePasswordView.hideLoading();
           updatePasswordView.showMessage(errorMessage);
    }

    @Override
    public void doUpdatePassword(User user,String oldPassword, String newPassword) {
         updatePasswordView.showLoading();
        userModel.updatePassword(user,requestTag,oldPassword,newPassword,this);
    }

    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
}
