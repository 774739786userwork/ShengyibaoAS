package com.bangware.shengyibao.shopcart.model.impl;

import java.util.Date;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.shopcart.model.ShopCartModel;
import com.bangware.shengyibao.shopcart.model.entity.StockInfo;
import com.bangware.shengyibao.shopcart.presenter.ShopCartListener;
import com.bangware.shengyibao.shopcart.view.SettlementActivity;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class ShopCartModelImpl implements ShopCartModel {

	@Override
	public void loadStocks(String requestTag, String salerId, Date date,String token,final ShopCartListener listener) {
		String ladingbill_url = Model.LADINGBILLURL + "&employee_id="+salerId+"&token="+token;
		DataRequest.getInstance().newGsonGetRequest(requestTag,ladingbill_url, StockInfo.class,
				new Response.Listener<StockInfo>() {
            @Override
            public void onResponse(StockInfo stockInfo) {
                if (stockInfo != null){
                	if(StockInfo.RESULT_SUCCESS.equals(stockInfo.getResult())) {
                		listener.onStockLoaded(stockInfo);
                	}else{
                		listener.onErrors(stockInfo.getMsg());
                	}
                } else {
                    listener.onErrors("返回结果转换失败！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrors("请求失败，服务器异常......");
            }
        });
	}

	

}
