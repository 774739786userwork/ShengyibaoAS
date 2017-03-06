package com.bangware.shengyibao.salesamount.model.entity;

/**
 * 组内排名子空间实体
 * Created by bangware on 2016/8/24.
 */
public class ChildItem {
    private String groupPerson;
    private String groupRanking;
    private String groupQuantity;
    private double groupTotalSum;

    public ChildItem(String groupPerson, String groupRanking, String groupQuantity, double groupTotalSum) {
        this.groupPerson = groupPerson;
        this.groupRanking = groupRanking;
        this.groupQuantity = groupQuantity;
        this.groupTotalSum = groupTotalSum;
    }

    public ChildItem() {
    }

    public String getGroupPerson() {
        return groupPerson;
    }

    public void setGroupPerson(String groupPerson) {
        this.groupPerson = groupPerson;
    }

    public String getGroupRanking() {
        return groupRanking;
    }

    public void setGroupRanking(String groupRanking) {
        this.groupRanking = groupRanking;
    }

    public String getGroupQuantity() {
        return groupQuantity;
    }

    public void setGroupQuantity(String groupQuantity) {
        this.groupQuantity = groupQuantity;
    }

    public double getGroupTotalSum() {
        return groupTotalSum;
    }

    public void setGroupTotalSum(double groupTotalSum) {
        this.groupTotalSum = groupTotalSum;
    }
}
