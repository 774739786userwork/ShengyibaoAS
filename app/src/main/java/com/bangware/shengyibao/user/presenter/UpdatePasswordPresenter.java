package com.bangware.shengyibao.user.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface UpdatePasswordPresenter {
	void doUpdatePassword(User user,String oldPassword, String newPassword);
	
	void destroy();
}
