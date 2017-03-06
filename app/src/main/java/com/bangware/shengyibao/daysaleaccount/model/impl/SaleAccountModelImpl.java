package com.bangware.shengyibao.daysaleaccount.model.impl;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.daysaleaccount.SaleAccountJsonUtils;
import com.bangware.shengyibao.daysaleaccount.model.SaleAccountModel;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;
import com.bangware.shengyibao.daysaleaccount.presenter.OnSaleAccountListener;
import com.bangware.shengyibao.daysaleaccount.presenter.OnSaleProductListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import org.json.JSONObject;

import java.util.List;

/**
 * 日销售清单数据请求
 * Created by ccssll on 2016/8/11.
 */
public class SaleAccountModelImpl implements SaleAccountModel{
    //销售清单列表查询数据请求
    @Override
    public void onloadAccount(String requestTag, User user, String begin_date, String end_date, int nPage, int nSpage,final OnSaleAccountListener saleAccountListener) {
        String query_salesaccount_url = Model.DAYSALEACCOUNTURL+"?employee_id="+user.getEmployee_id()
                +"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage+"&token="+user.getLogin_token()+"&organization_id="+user.getOrg_id();
        DataRequest.getInstance().newJsonObjectGetRequest(requestTag, query_salesaccount_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                // TODO Auto-generated method stub
                if (jsonObject!=null) {
                    List<SaleAccountListBean> beanlist = SaleAccountJsonUtils.getSaleAcoountData(jsonObject.toString());
                    saleAccountListener.onLoadSalesAccountSuccess(beanlist);
                }else
                {
                    saleAccountListener.onLoadSalesAccountFailure("数据传输失败！");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                saleAccountListener.onLoadSalesAccountFailure("请求失败，服务器异常......");
            }
        });
    }

    //产品查询数据请求
    @Override
    public void onloadProductAccount(String requestTag, User user, String saler_journals_id, final OnSaleProductListener productListener) {
        String query_productAccount_url = Model.DAYSALEPRODUCTACCOUNTURL +saler_journals_id+".json?token="+user.getLogin_token();
        DataRequest.getInstance().newJsonObjectGetRequest(requestTag, query_productAccount_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                // TODO Auto-generated method stub
                if (jsonObject!=null) {
                    List<SaleAccountProductBean> productlist = SaleAccountJsonUtils.getProductData(jsonObject.toString());
                    productListener.onLoadSalesProductSuccess(productlist);
                }else
                {
                    productListener.onLoadSalesProductFailure("数据传输失败！");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                productListener.onLoadSalesProductFailure("请求失败，服务器异常......");
            }
        });
    }
}
