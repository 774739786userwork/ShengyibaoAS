package com.bangware.shengyibao.user.model;


import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.presenter.OnUpdatePasswordListener;
import com.bangware.shengyibao.user.presenter.OnUserLoginListener;

public interface UserModel {
	public void login(String requestTag, String username, String password, OnUserLoginListener loginListener);
	
	public void updatePassword(User user,String requestTag, String oldPassword, String newPassword, OnUpdatePasswordListener Listener);
}
