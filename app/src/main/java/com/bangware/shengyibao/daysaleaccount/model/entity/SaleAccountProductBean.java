package com.bangware.shengyibao.daysaleaccount.model.entity;

import com.bangware.shengyibao.model.Product;

/**
 * 日销售清单产品信息实体bean
 * Created by ccssll on 2016/8/9.
 */
public class SaleAccountProductBean {
    private double totalmoney;//总计金额
    private double receivemoney;//已收现金
    private double rounding;//四舍五入
    private double smallchange;//抹零
    private double unpaidmoney;//未收款
    private String saleperson_amount;//记量
    private double product_subtotal_sum;//产品小计金额
    private Product product;
    private int salesVolume;//销售数量
    private int giftsVolume;//赠送数量

    public double getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(double totalmoney) {
        this.totalmoney = totalmoney;
    }

    public double getReceivemoney() {
        return receivemoney;
    }

    public void setReceivemoney(double receivemoney) {
        this.receivemoney = receivemoney;
    }

    public double getUnpaidmoney() {
        return unpaidmoney;
    }

    public void setUnpaidmoney(double unpaidmoney) {
        this.unpaidmoney = unpaidmoney;
    }

    public double getRounding() {
        return rounding;
    }

    public void setRounding(double rounding) {
        this.rounding = rounding;
    }

    public double getSmallchange() {
        return smallchange;
    }

    public void setSmallchange(double smallchange) {
        this.smallchange = smallchange;
    }

    public String getSaleperson_amount() {
        return saleperson_amount;
    }

    public void setSaleperson_amount(String saleperson_amount) {
        this.saleperson_amount = saleperson_amount;
    }

    public double getProduct_subtotal_sum() {
        return product_subtotal_sum;
    }

    public void setProduct_subtotal_sum(double product_subtotal_sum) {
        this.product_subtotal_sum = product_subtotal_sum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getGiftsVolume() {
        return giftsVolume;
    }

    public void setGiftsVolume(int giftsVolume) {
        this.giftsVolume = giftsVolume;
    }
}
