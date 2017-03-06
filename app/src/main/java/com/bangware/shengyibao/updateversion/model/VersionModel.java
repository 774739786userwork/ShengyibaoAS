package com.bangware.shengyibao.updateversion.model;


import com.bangware.shengyibao.updateversion.presenter.VersionListener;
import com.bangware.shengyibao.user.model.entity.User;


public interface VersionModel {
	public void versionUpdate(String requestTag, VersionListener listener, User user);
}
