package com.bangware.shengyibao.updateversion.presenter;


import com.bangware.shengyibao.updateversion.model.entity.VersionBean;

public interface VersionListener {
	public void onUpdateVersion(VersionBean beaninfo);
	public void onErrors(String errorMessage);
}
