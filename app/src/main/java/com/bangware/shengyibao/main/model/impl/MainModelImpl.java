package com.bangware.shengyibao.main.model.impl;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.main.MainJsonUtils;
import com.bangware.shengyibao.main.model.MainModel;
import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;
import com.bangware.shengyibao.main.presenter.OnMainSalerListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

public class MainModelImpl implements MainModel {

	@Override
	public void loadToDaySummary(String requestTag,User user,
								 final OnMainSalerListener onMainSalerListener) {
		// TODO Auto-generated method stub
		String main_header_url = Model.MAINHEADERURL+"&employee_id="+user.getEmployee_id()+"&token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, main_header_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject != null) {
					ToDaySummary daySummary = MainJsonUtils.getHeaderData(jsonObject.toString());
					onMainSalerListener.OnLoadToDaySummarySuccess(daySummary);
				}else{
					onMainSalerListener.showFailure("返回内容为空");
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				onMainSalerListener.showFailure("请求失败，服务器异常......");
			}
		});
	}

	@Override
	public void loadThisMonthSummary(String requestTag, User user,
									 final OnMainSalerListener onMainSalerListener) {
		// TODO Auto-generated method stub
		String saler_url = Model.MAINURL+"&employee_id="+user.getEmployee_id()+"&organization_id="+user.getOrg_id()+"&token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, saler_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject!=null) {
					ThisMonthSummary thisMonthSummary= MainJsonUtils.getSalerData(jsonObject.toString());
					onMainSalerListener.OnLoadThisMonthSummarySuccess(thisMonthSummary);
				}else{
					onMainSalerListener.showFailure("返回内容为空");
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				onMainSalerListener.showFailure("请求失败，服务器异常......");
			}
		});
	}

}
