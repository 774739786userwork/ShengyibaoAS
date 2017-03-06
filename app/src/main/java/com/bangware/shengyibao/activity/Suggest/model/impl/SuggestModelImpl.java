package com.bangware.shengyibao.activity.Suggest.model.impl;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.activity.Suggest.model.SuggestModel;
import com.bangware.shengyibao.activity.Suggest.presenter.SuggestListener;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户反馈请求
 * Created by bangware on 2016/8/23.
 */
public class SuggestModelImpl implements SuggestModel{
    @Override
    public void onLoadsubmit(String requestTag,String content, User user, final SuggestListener suggestListener) {
        String suggest_url = Model.SUGGESTIONURL +"?token="+user.getLogin_token();
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("content", content);
            jsonData.put("user_id", user.getUser_id());
            jsonData.put("user_name",user.getUser_realname());
            JSONObject messageData = new JSONObject();
            jsonData.put("feedback_suggestions", messageData);
            DataRequest.getInstance().newJsonObjectPostRequest(requestTag,suggest_url, jsonData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (jsonObject != null) {
                        try{
                            switch (jsonObject.getInt("result")) {
                                case 0:
                                    suggestListener.onSubmitSuccess("提交成功！你的意见我们已收到,我们会及时处理！");
                                    break;
                                case -1:
                                    suggestListener.onSubmitFailure("添加失败");
                                    break;
                                default:
                                    break;
                            }
                        }catch(JSONException jsonException){
                            suggestListener.onSubmitFailure("数据解析错误！");
                        }
                    } else {
                        suggestListener.onSubmitFailure("返回内容为空！");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    suggestListener.onSubmitFailure("请求失败，服务器异常......");
                }
            });

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
