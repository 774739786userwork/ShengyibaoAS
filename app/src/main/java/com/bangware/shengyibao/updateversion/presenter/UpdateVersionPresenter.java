package com.bangware.shengyibao.updateversion.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface UpdateVersionPresenter {
	public void versionUpdate(User user);
	
	public void destroy();
}
