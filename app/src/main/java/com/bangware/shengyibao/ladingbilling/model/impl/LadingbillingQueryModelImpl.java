package com.bangware.shengyibao.ladingbilling.model.impl;

import android.util.Log;

import java.util.List;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.ladingbilling.LadingBillingUtils;
import com.bangware.shengyibao.ladingbilling.model.LadingbillingQueryModel;
import com.bangware.shengyibao.ladingbilling.model.entity.LadingbillingQuery;
import com.bangware.shengyibao.ladingbilling.presenter.OnLadingBillingListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class LadingbillingQueryModelImpl implements LadingbillingQueryModel {

	@Override
	public void LoadLadingBillingData(String requestTag, String begin_date, String end_date,
									  int nPage, int nSpage, User user, final OnLadingBillingListener billingListener) {
		// TODO Auto-generated method stub
		String query_ladingbilling_url = Model.LADINGBILL_QUERYURL+"&employee_id="+user.getEmployee_id()
				+"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage+"&token="+user.getLogin_token();
		Log.e("TAG", "LoadLadingBillingData: "+query_ladingbilling_url);
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag,query_ladingbilling_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if(jsonObject != null){
					List<LadingbillingQuery> list_ladingbilling = LadingBillingUtils.getLadingBillingList(jsonObject.toString());
					billingListener.onLoadDataSuccess(list_ladingbilling);
				}else {
					billingListener.onLoadDataFailure("返回内容为空！数据传输失败！");
                }
			}
			
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				billingListener.onLoadDataFailure("请求失败，服务器异常......！");
			}
        });
	} 
}
