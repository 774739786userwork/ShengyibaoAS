package com.bangware.shengyibao.shopcart.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Payment implements Serializable {
    private double wechat_payment;
    private double Alipay;
    private double bank_receive_total_sum;
    private double cash_receive_total_sum;

    public Payment() {
    }

    public Payment(double wechat_payment, double alipay, double bank_receive_total_sum,double cash_receive_total_sum) {
        this.wechat_payment = wechat_payment;
        this.Alipay = alipay;
        this.bank_receive_total_sum = bank_receive_total_sum;
        this.cash_receive_total_sum=cash_receive_total_sum;
    }

    public double getAlipay() {
        return Alipay;
    }

    public void setAlipay(double alipay) {
        Alipay = alipay;
    }

    public double getWechat_payment() {
        return wechat_payment;
    }

    public void setWechat_payment(double wechat_payment) {
        this.wechat_payment = wechat_payment;
    }

    public double getBank_receive_total_sum() {
        return bank_receive_total_sum;
    }

    public void setBank_receive_total_sum(double bank_receive_total_sum) {
        this.bank_receive_total_sum = bank_receive_total_sum;
    }

    public double getCash_receive_total_sum() {
        return cash_receive_total_sum;
    }

    public void setCash_receive_total_sum(double cash_receive_total_sum) {
        this.cash_receive_total_sum = cash_receive_total_sum;
    }

    public double getTotalPayment()
    {
        double totalPayment = this.getAlipay()+this.getBank_receive_total_sum()+this.getWechat_payment()+this.getCash_receive_total_sum();
        return totalPayment;
    }
}
