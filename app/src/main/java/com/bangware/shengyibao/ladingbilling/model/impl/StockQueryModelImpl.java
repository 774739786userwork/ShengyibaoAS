package com.bangware.shengyibao.ladingbilling.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.ladingbilling.model.StockQueryModel;
import com.bangware.shengyibao.ladingbilling.presenter.StockListener;
import com.bangware.shengyibao.shopcart.model.entity.StockInfo;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

/**
 * Created by bangware on 2016/8/22.
 */
public class StockQueryModelImpl implements StockQueryModel{
    @Override
    public void onQueryStockinfo(String requestTag, User user, final StockListener stockListener) {
        String stockinfo_url = Model.STOCKQUERYURL + "&employee_id="+user.getEmployee_id()+"&token="+user.getLogin_token();
        DataRequest.getInstance().newGsonGetRequest(requestTag,stockinfo_url, StockInfo.class,
                new Response.Listener<StockInfo>() {
                    @Override
                    public void onResponse(StockInfo stockInfo) {
                        if (stockInfo != null){
                            if(StockInfo.RESULT_SUCCESS.equals(stockInfo.getResult())) {
                                stockListener.onStockLoaded(stockInfo);
                            }else{
                                stockListener.onErrors(stockInfo.getMsg());
                            }
                        } else {
                            stockListener.onErrors("返回结果转换失败！");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stockListener.onErrors("请求失败，服务器异常......");
                    }
                });
    }
}
