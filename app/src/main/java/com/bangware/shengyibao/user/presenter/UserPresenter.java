package com.bangware.shengyibao.user.presenter;
public interface UserPresenter {
	void doLogin(String username, String password);
	
	void destroy();
}
