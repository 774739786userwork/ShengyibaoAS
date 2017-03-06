package com.bangware.shengyibao.updateversion.model.impl;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.updateversion.UpdateJsonUtil;
import com.bangware.shengyibao.updateversion.model.VersionModel;
import com.bangware.shengyibao.updateversion.model.entity.VersionBean;
import com.bangware.shengyibao.updateversion.presenter.VersionListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * 版本更新访问后台数据接口
 * @author ccssll
 *
 */
public class VersionModelImpl implements VersionModel {
	@Override
	public void versionUpdate(String requestTag, final VersionListener listener, User user) {
		// TODO Auto-generated method stub
		final String url = Model.UPDATEVERSION + "&token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject!=null) {
					VersionBean versionBean = UpdateJsonUtil.getUpdateVersion(jsonObject.toString());
					listener.onUpdateVersion(versionBean);
				}else{
					listener.onErrors("解析出错");
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				listener.onErrors("请求失败，服务器异常......");
			}
		});
	}

}
