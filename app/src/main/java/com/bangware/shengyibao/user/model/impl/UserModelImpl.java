package com.bangware.shengyibao.user.model.impl;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.user.model.UserModel;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.presenter.OnUpdatePasswordListener;
import com.bangware.shengyibao.user.presenter.OnUserLoginListener;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModelImpl implements UserModel {
	@Override
	public void login(String requestTag, final String username, final String password, final OnUserLoginListener loginListener) {
		// TODO Auto-generated method stub
		try{
			String login_url = Model.HTTPURL+ Model.LOGINURL;
			//todo:这里要进行判断，用户是否需要重新登陆处理。。。
			JSONObject userData = new JSONObject();
			userData.put("username", username);
			userData.put("password", password);
			JSONObject jsonData = new JSONObject();
			jsonData.put("user", userData);

			DataRequest.getInstance().newJsonObjectPostRequest(requestTag, login_url, jsonData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (jsonObject != null) {
                    	try{
							switch(jsonObject.getInt("status")){
		                    	case -1://登陆密码错误，登陆失败！
		                    		loginListener.onLoginError("登陆密码错误，登陆失败！");
		                    		break;
		                    	case -2://登陆账号不存在，登陆失败！
		                    		loginListener.onLoginError("登陆账号不存在，登陆失败！");
		                    		break;
		                    	/*case -3://账号已登录，不能重复登录！
		                    		loginListener.onLoginError("账号已登录，不能重复登录！");
		                    		break;*/
		                    	default:
		                    		User user = new User();
		                    		setValues(user, jsonObject);
		                    		user.setUser_name(username);
		                    		user.setPassword(password);
		                    		loginListener.onLoginSuccess(user);
		                    		break;
	                    	}
                    	}catch(JSONException jsonException){
                    		loginListener.onLoginError("JSON解析错误！");
                    	}
                    } else {
                    	loginListener.onLoginError("返回内容为空！");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                	loginListener.onLoginError("请求失败，服务器异常......");
                }
            });
		}catch(JSONException ex){
			loginListener.onLoginError("请求参数设置失败！");
		}
	}

	@Override
	public void updatePassword(User user,String requestTag, String oldPassword, String newPassword, final OnUpdatePasswordListener Listener) {
		String updatepassword_url=Model.HTTPURL+Model.UPDATEPASSWORD+"?token="+user.getLogin_token();
		try {
			JSONObject object=new JSONObject();
			object.put("current_password",oldPassword);
			object.put("password",newPassword);
			object.put("password_confirmation",newPassword);
			JSONObject jsonData = new JSONObject();
			jsonData.put("user",object);
			DataRequest.getInstance().newJsonObjectPutRequest(requestTag, updatepassword_url, jsonData, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject!=null)
					{
						try {
							switch(jsonObject.getInt("result")){
                                case -1://修改失败
                                    Listener.onUpdateError(jsonObject.getString("msg"));
                                    break;
                                case 0://修改成功
									Listener.onUpdateSuccess();
                                    break;
                            }
						} catch (JSONException e) {
							Listener.onUpdateError("JSON解析错误！");
						}

					}else
					{
						Listener.onUpdateError("返回内容为空");
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {
                  Listener.onUpdateError("请求失败，服务器异常......");
				}
			});
		} catch (JSONException e) {
			Listener.onUpdateError("请求参数设置失败！");
		}
	}

	/**
	 * 为UserInfo赋值
	 * @param jsonObject
	 */
	private void setValues(User user, JSONObject jsonObject)throws JSONException {
		if(jsonObject.has("token")){
			user.setLogin_token(jsonObject.getString("token"));
		}
		if(jsonObject.has("user_id")){
			user.setUser_id(jsonObject.getString("user_id"));
		}
		if(jsonObject.has("username")){
			user.setUser_name(jsonObject.getString("username"));
		}
		if(jsonObject.has("password")){
			user.setPassword(jsonObject.getString("password"));
		}
		if(jsonObject.has("user_real_name")){
			user.setUser_realname(jsonObject.getString("user_real_name"));
		}
		if(jsonObject.has("organization_id")){
			user.setOrg_id(jsonObject.getString("organization_id"));
		}
		if(jsonObject.has("organization_name")){
			user.setOrg_name(jsonObject.getString("organization_name"));
		}
		if(jsonObject.has("roles")){
			user.setRoles(jsonObject.getString("roles"));
		}
		if(jsonObject.has("employee_id")){
			user.setEmployee_id(jsonObject.getString("employee_id"));
		}
		if (jsonObject.has("mobile_number")){
			user.setMobile_number(jsonObject.getString("mobile_number"));
		}
	}

}
