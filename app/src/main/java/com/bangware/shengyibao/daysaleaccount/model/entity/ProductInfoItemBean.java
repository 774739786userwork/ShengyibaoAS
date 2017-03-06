package com.bangware.shengyibao.daysaleaccount.model.entity;

import com.bangware.shengyibao.model.Product;

/**
 * 新建日销售清单产品信息展示
 * Created by bangware on 2016/8/31.
 */
public class ProductInfoItemBean {
    private String saleperson_amount;//记量
    private String person_count;//记多少量
    private double product_subtotal_sum;//产品小计金额
    private Product product;
    private int salesVolume;//销售数量
    private int giftsVolume;//赠送数量

    public ProductInfoItemBean() {
    }

    public ProductInfoItemBean(String saleperson_amount,String person_count, double product_subtotal_sum, Product product, int salesVolume, int giftsVolume) {
        this.saleperson_amount = saleperson_amount;
        this.person_count = person_count;
        this.product_subtotal_sum = product_subtotal_sum;
        this.product = product;
        this.salesVolume = salesVolume;
        this.giftsVolume = giftsVolume;
    }

    public String getSaleperson_amount() {
        return saleperson_amount;
    }

    public void setSaleperson_amount(String saleperson_amount) {
        this.saleperson_amount = saleperson_amount;
    }

    public String getPerson_count() {
        return person_count;
    }

    public void setPerson_count(String person_count) {
        this.person_count = person_count;
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
