package com.bangware.shengyibao.daysaleaccount;


import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;
import com.bangware.shengyibao.model.Product;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccssll on 2016/8/11.
 */
public class SaleAccountJsonUtils {
    //日销售清单查询列表解析
    public static List<SaleAccountListBean> getSaleAcoountData(String jsonString){
        List<SaleAccountListBean> list = new ArrayList<SaleAccountListBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jay = jsonObject.getJSONArray("rows");
            SaleAccountListBean saleAccountListBean = null;
            for (int i = 0;i<jay.length();i++){
                JSONObject object = jay.getJSONObject(i);
                saleAccountListBean = new SaleAccountListBean();
                saleAccountListBean.setId(object.getString("id"));
                saleAccountListBean.setSaledate(object.getString("sale_date"));
                saleAccountListBean.setCreateperson(object.getString("create_name"));
                saleAccountListBean.setTotalsum(object.getDouble("total_sum"));
                saleAccountListBean.setUnpaidmoney(object.getDouble("unpaid_total_sum"));
                saleAccountListBean.setBankreceive(object.getDouble("bank_received_total_sum"));
                saleAccountListBean.setWetchatreceive(object.getDouble("wechat_pay"));
                saleAccountListBean.setPaymentreceive(object.getDouble("alipay"));
                saleAccountListBean.setCashreceive(object.getDouble("receive_total_sum"));
                saleAccountListBean.setTotal_pagerecord(jsonObject.getInt("total"));
                list.add(saleAccountListBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //日销售清单产品列表解析
    public static List<SaleAccountProductBean> getProductData(String jsonString){
        List<SaleAccountProductBean>  productBeanList = new ArrayList<SaleAccountProductBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray  jsonArray = jsonObject.getJSONArray("product_list");
            SaleAccountProductBean productBean = null;
            Product product = null;
            for (int i =0;i<jsonArray.length();i++){
                JSONObject saleobject = jsonArray.getJSONObject(i);
                productBean = new SaleAccountProductBean();
                productBean.setTotalmoney(jsonObject.getDouble("total_sum"));
                productBean.setUnpaidmoney(jsonObject.getDouble("unpaid_total_sum"));
                productBean.setRounding(jsonObject.getDouble("rounding"));
                productBean.setSmallchange(jsonObject.getDouble("small_change"));
                productBean.setSalesVolume(saleobject.getInt("sale_quantity"));
                productBean.setGiftsVolume(saleobject.getInt("gifts_quantity"));
                productBean.setProduct_subtotal_sum(saleobject.getDouble("sum"));
                productBean.setSaleperson_amount(saleobject.getString("calculate_employee"));

                product = new Product();
                product.setId(saleobject.getString("id"));
                product.setName(saleobject.getString("product_name"));
                product.setPrice(saleobject.getDouble("price"));
                product.setForegift(saleobject.getDouble("total_foregift"));

                productBean.setProduct(product);

                productBeanList.add(productBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productBeanList;
    }
}
