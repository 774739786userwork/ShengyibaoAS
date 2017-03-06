package com.bangware.shengyibao.user.presenter.impl;


import com.bangware.shengyibao.user.model.UserModel;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.model.impl.UserModelImpl;
import com.bangware.shengyibao.user.presenter.OnUserLoginListener;
import com.bangware.shengyibao.user.presenter.UserPresenter;
import com.bangware.shengyibao.user.view.LoginView;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

public class UserPresenterImpl implements UserPresenter, OnUserLoginListener {

	public static final String REQUEST_TAG = "User";
	private UserModel userModel;
	private LoginView loginView;
	private String requestTag;
	public UserPresenterImpl(LoginView loginView){
		this.requestTag = REQUEST_TAG+ System.currentTimeMillis();
		this.loginView = loginView;
		this.userModel = new UserModelImpl();
	}
	@Override
	public void doLogin(String updateUsername, String password) {
		loginView.showLoading();
		userModel.login(requestTag, updateUsername, password, this);
	}
	
	@Override
	public void onLoginSuccess(User user) {
		loginView.hideLoading();
		AppContext.getInstance().setUser(user);
		loginView.loginSuccess();
	}

	@Override
	public void onLoginError(String errorMessage) {
		loginView.hideLoading();
		loginView.onloginError(errorMessage);
	}
	@Override
	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}
}
