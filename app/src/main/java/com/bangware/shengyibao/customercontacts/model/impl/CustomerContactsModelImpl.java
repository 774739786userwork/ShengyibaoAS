package com.bangware.shengyibao.customercontacts.model.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customercontacts.CustomerContactsJosnUtils;
import com.bangware.shengyibao.customercontacts.model.CustomerContactsModel;
import com.bangware.shengyibao.customercontacts.presenter.OnCustomerContactsListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class CustomerContactsModelImpl implements CustomerContactsModel {
	@Override
	public void loadCustomerContacts(String requestTag, User user, int nPage, int nSpage,
									 String phone, String contactName,
									 final OnCustomerContactsListener customerContactsListener) {
		String customer_contacts_url;
		try {
			customer_contacts_url = Model.CUSETOMER_CONTACTS_DETAIL_URL+"?token="+user.getLogin_token()+"&page="+nPage+"&rows="+nSpage
					+"&contactMobile="+phone+"&content="+URLEncoder.encode(contactName, "UTF-8");
			Log.e("TGA", customer_contacts_url);
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, customer_contacts_url, null, new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject jsonObject) {
					if(jsonObject != null){
						List<Contacts> contacts = CustomerContactsJosnUtils.getCustomerContactsList(jsonObject.toString());
						
						customerContactsListener.onLoadCustomerContactsSuccess(contacts);
						Log.d("TAG", "query11111  "+contacts.size());
					}else{
						customerContactsListener.onLoadCustomerContactsFailure("数据传输失败！");
				      }
				}
				}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					customerContactsListener.onLoadCustomerContactsFailure("请求失败，服务器异常......");
				}
			});
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
