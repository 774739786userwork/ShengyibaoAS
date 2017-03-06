package com.bangware.shengyibao.user.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface OnUserLoginListener {
	void onLoginSuccess(User user);

    void onLoginError(String errorMessage);
}
