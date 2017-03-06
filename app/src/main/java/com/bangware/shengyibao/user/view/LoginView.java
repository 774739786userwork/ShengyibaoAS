package com.bangware.shengyibao.user.view;

import com.bangware.shengyibao.activity.BaseView;

public interface LoginView extends BaseView {
	 void loginSuccess();
	 void onloginError(String ErrorMessage);
}
