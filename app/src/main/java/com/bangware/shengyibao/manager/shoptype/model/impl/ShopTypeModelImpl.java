package com.bangware.shengyibao.manager.shoptype.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.customer.CustomerUtils;
import com.bangware.shengyibao.manager.shoptype.helper.GroupData;
import com.bangware.shengyibao.manager.shoptype.model.ShopTypeModel;
import com.bangware.shengyibao.manager.shoptype.presenter.ShopTypeListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import org.json.JSONObject;

import java.util.List;

/**
 * 店面类型位置访问数据接口请求
 * Created by bangware on 2016/9/2.
 */
public class ShopTypeModelImpl implements ShopTypeModel{
    @Override
    public void onLoadShopType(String requestTag, User user, final ShopTypeListener typeListener) {
        String shop_type_url = Model.CUSTOMER_TYPE_URL+"&token="+ user.getLogin_token();
        DataRequest.getInstance().newJsonObjectGetRequest(requestTag, shop_type_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                // TODO Auto-generated method stub
                if (jsonObject!=null) {
                    List<GroupData> grouplist = CustomerUtils.getShopType(jsonObject.toString());
                    typeListener.onLoadGroupSuccess(grouplist);
                }else
                {
                    typeListener.onLoadGroupFailure("返回内容为空！");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                typeListener.onLoadGroupFailure("请求失败，服务器异常......");
            }
        });
    }
}
