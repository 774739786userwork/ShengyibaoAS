package com.bangware.shengyibao.salesamount.model.impl;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.salesamount.SalesAmountJsonUtils;
import com.bangware.shengyibao.salesamount.model.SaleRankingModel;
import com.bangware.shengyibao.salesamount.model.entity.GroupItem;
import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;
import com.bangware.shengyibao.salesamount.presenter.OnGroupRankingListener;
import com.bangware.shengyibao.salesamount.presenter.OnSaleRankingListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import org.json.JSONObject;

import java.util.List;

/**
 * 销售排名数据请求
 * Created by ccssll on 2016/8/10.
 */
public class SaleRankingModelImpl implements SaleRankingModel{
    @Override
    public void loadSaleRanking(String requestTag, User user, String begin_date, String end_date,final OnSaleRankingListener saleRankingListener) {
        String sale_rangking_url = Model.SALERANKINGURL + "&token="+user.getLogin_token()+"&organization_id="+user.getOrg_id()+"&begin_date="+begin_date+"&end_date="+end_date;
        DataRequest.getInstance().newJsonObjectGetRequest(requestTag, sale_rangking_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                // TODO Auto-generated method stub
                if (jsonObject!=null) {
                    List<SaleRankingBean> list = SalesAmountJsonUtils.getSaleRanking(jsonObject.toString());
                    saleRankingListener.onLoadSaleRankingSuccess(list);
                }else
                {
                    saleRankingListener.onLoadSaleRankingFailure("返回内容为空！");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                saleRankingListener.onLoadSaleRankingFailure("请求失败，服务器异常......");
            }
        });
    }

    /**
     * 组内排名
     * @param requestTag
     * @param user
     * @param begin_date
     * @param end_date
     * @param groupRankingListener
     */
    @Override
    public void loadGroupRanking(String requestTag, User user, String begin_date, String end_date, final OnGroupRankingListener groupRankingListener) {
        String group_rangking_url = Model.GROUPRANKINGURL + "&token="+user.getLogin_token()+"&organization_id="+user.getOrg_id()+"&begin_date="+begin_date+"&end_date="+end_date;
        DataRequest.getInstance().newJsonObjectGetRequest(requestTag, group_rangking_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                // TODO Auto-generated method stub
                if (jsonObject!=null) {
                    List<GroupItem> grouplist = SalesAmountJsonUtils.getGroupRanking(jsonObject.toString());
                    groupRankingListener.onLoadGroupSuccess(grouplist);
                }else
                {
                    groupRankingListener.onLoadGroupFailure("返回内容为空！");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                groupRankingListener.onLoadGroupFailure("请求失败，服务器异常......");
            }
        });
    }
}
