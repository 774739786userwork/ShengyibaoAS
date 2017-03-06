package com.bangware.shengyibao.daysaleaccount.model.entity;

import java.io.Serializable;

/**
 * Created by ccssll on 2016/8/11.
 */
public class SaleAccountListBean implements Serializable{
    private static final long serialVersionUID = -3033663599091826822L;
    private String id;
    private String saledate;//销售时间
    private String createperson;//创建人
    private double totalsum;//总计金额
    private double unpaidmoney;//未付金额
    private double bankreceive;//银行支付
    private double wetchatreceive;//微信支付
    private double paymentreceive;//支付宝支付
    private double cashreceive;//现金支付

    private int total_pagerecord;

    public String getSaledate() {
        return saledate;
    }

    public void setSaledate(String saledate) {
        this.saledate = saledate;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public double getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(double totalsum) {
        this.totalsum = totalsum;
    }

    public double getUnpaidmoney() {
        return unpaidmoney;
    }

    public void setUnpaidmoney(double unpaidmoney) {
        this.unpaidmoney = unpaidmoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal_pagerecord() {
        return total_pagerecord;
    }

    public void setTotal_pagerecord(int total_pagerecord) {
        this.total_pagerecord = total_pagerecord;
    }

    public double getBankreceive() {
        return bankreceive;
    }

    public void setBankreceive(double bankreceive) {
        this.bankreceive = bankreceive;
    }

    public double getWetchatreceive() {
        return wetchatreceive;
    }

    public void setWetchatreceive(double wetchatreceive) {
        this.wetchatreceive = wetchatreceive;
    }

    public double getPaymentreceive() {
        return paymentreceive;
    }

    public void setPaymentreceive(double paymentreceive) {
        this.paymentreceive = paymentreceive;
    }

    public double getCashreceive() {
        return cashreceive;
    }

    public void setCashreceive(double cashreceive) {
        this.cashreceive = cashreceive;
    }
}
