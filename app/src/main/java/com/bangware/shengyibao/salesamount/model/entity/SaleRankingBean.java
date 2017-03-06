package com.bangware.shengyibao.salesamount.model.entity;

/**
 * 销售排名实体类
 * Created by ccssll on 2016/8/10.
 */
public class SaleRankingBean {
    private String salerPerson;
    private String saleRanking;
    private String customerQuantity;
    private double saleTotalSum;

    public String getSalerPerson() {
        return salerPerson;
    }

    public void setSalerPerson(String salerPerson) {
        this.salerPerson = salerPerson;
    }

    public String getSaleRanking() {
        return saleRanking;
    }

    public void setSaleRanking(String saleRanking) {
        this.saleRanking = saleRanking;
    }

    public String getCustomerQuantity() {
        return customerQuantity;
    }

    public void setCustomerQuantity(String customerQuantity) {
        this.customerQuantity = customerQuantity;
    }

    public double getSaleTotalSum() {
        return saleTotalSum;
    }

    public void setSaleTotalSum(double saleTotalSum) {
        this.saleTotalSum = saleTotalSum;
    }
}
