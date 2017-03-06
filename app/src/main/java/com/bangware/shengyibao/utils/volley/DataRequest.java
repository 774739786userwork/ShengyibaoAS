package com.bangware.shengyibao.utils.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/2/6.
 */
public class DataRequest {
    private static RequestQueue mRequestQueue;

    private static DataRequest volleyRequest = new DataRequest();
    private DataRequest() {
    }

    /**
     * @param context ApplicationContext
     */
    public static void buildRequestQueue(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);

    }

    public static DataRequest getInstance() {
        if (mRequestQueue == null) {
            throw new NullPointerException("请先调用 buildRequestQueue方法");
        }
        return volleyRequest;
    }
    /**
     * 取消所有请求
     */
    public void cancelAllRequest(){
    	if (mRequestQueue == null) {
	    	mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
				@Override
				public boolean apply(Request<?> request) {
					return true;
				}
			});
    	}
    }
    /**
     * 根据RequestTag取消网络请求
     * @param requestTag
     */
    public void cancelRequestByTag(final String requestTag){
    	if (mRequestQueue == null) {
    		mRequestQueue.cancelAll(requestTag);
    	}
    }

    /**
     * 使用GET方法发送URL请求
     * @param url 请求路径
     * @param clazz 结果转换类型
     * @param listener 成功回调监听
     * @param errorListener 错误回调监听
     * @return
     */
    public <T> GsonRequest<T> newGsonGetRequest(String requestTag, String url, Class<T> clazz, Response.Listener<T> listener,
                                                Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(requestTag);
        mRequestQueue.add(request);
        return request;
    }
    /**
     * 发送URL请求
     * @param method 请求方法Request.Method.GET/POST
     * @param url 请求路径
     * @param clazz 结果转换类型
     * @param listener 成功回调监听
     * @param errorListener 错误回调监听
     * @return
     */
    public <T> GsonRequest<T> newGsonRequest(String requestTag, int method, String url, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(method, url, clazz, listener, errorListener);
        request.setTag(requestTag);
        mRequestQueue.add(request);
        
        return request;
    }
  
    /**
     * 发送JSON请求
     * @param requestTag 请求标识
     * @param method 请求方法
     * @param url 请求路径
     * @param jsonRequest 请求数据
     * @param listener 成功回调监听
     * @param errorListener 错误回调监听
     * @return
     */
    public JsonObjectRequest newJsonObjectRequest(String requestTag, int method, String url, JSONObject jsonRequest,
                                                  Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		JsonObjectRequest request = new JsonObjectRequest(method, url, jsonRequest, listener, errorListener);
		request.setTag(requestTag);
		mRequestQueue.add(request);
		return request;
	}
    /**
     * 使用POST方法发送JSON请求
     * @paramrequestTag 请求标识
     * @parammethod 请求方法
     * @paramurl 请求路径
     * @paramjsonRequest 请求数据
     * @paramlistener 成功回调监听
     * @paramerrorListener 错误回调监听
     * @return
     */
    public JsonObjectRequest newJsonObjectPostRequest(String requestTag, String url, JSONObject jsonRequest,
                                                      Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		request.setTag(requestTag);
		mRequestQueue.add(request);
		return request;
	}

    /**
     * 使用PUT方法发送JSON请求
     * @paramrequestTag 请求标识
     * @parammethod 请求方法
     * @paramurl 请求路径
     * @paramjsonRequest 请求数据
     * @paramlistener 成功回调监听
     * @paramerrorListener 错误回调监听
     * @return
     */

    public JsonObjectRequest newJsonObjectPutRequest(String requestTag, String url, JSONObject jsonRequest,
                                                      Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonRequest, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(requestTag);
        mRequestQueue.add(request);
        return request;
    }
    /**
     * 使用GET方法发送JSON请求
     * @paramrequestTag 请求标识
     * @parammethod 请求方法
     * @paramurl 请求路径
     * @paramjsonRequest 请求数据
     * @paramlistener 成功回调监听
     * @paramerrorListener 错误回调监听
     * @return
     */
    public JsonObjectRequest newJsonObjectGetRequest(String requestTag, String url, JSONObject jsonRequest,
                                                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
    	
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonRequest, listener, errorListener);
        //设置超时重新请求
        request.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		request.setTag(requestTag);
		mRequestQueue.add(request);
		return request;
	}
}
