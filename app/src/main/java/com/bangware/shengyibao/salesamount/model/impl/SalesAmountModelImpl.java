package com.bangware.shengyibao.salesamount.model.impl;

import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.salesamount.SalesAmountJsonUtils;
import com.bangware.shengyibao.salesamount.model.SalesAmountModel;
import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;
import com.bangware.shengyibao.salesamount.presenter.OnSalesAmountListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class SalesAmountModelImpl implements SalesAmountModel {

	@Override
	public void loadCustomerContacts(String requestTag, User user,
			String begin_date, String end_date, int nPage, int nSpage,
			final OnSalesAmountListener salesAmountListener) {
		// TODO Auto-generated method stub
		String query_salesamount_url = Model.DELIVAERY_NOTE_QUERYURL+"&employee_id="+user.getEmployee_id()+"&organization_id="+user.getOrg_id()
				+"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage+"&token="
				+user.getLogin_token()+"&show_type=2";
		Log.e("query_salesamount_url", query_salesamount_url);
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, query_salesamount_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject!=null) {
					List<MonthProductAmount> list= SalesAmountJsonUtils.getGoodsList(jsonObject.toString());
					salesAmountListener.onLoadSalesAmountSuccess(list);
					Log.e("list", "111111============"+list.size());
				}else
				{
					salesAmountListener.onLoadSalesAmountFailure("返回内容为空！");
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				salesAmountListener.onLoadSalesAmountFailure("请求失败，服务器异常......");
			}
		});
	}

}
