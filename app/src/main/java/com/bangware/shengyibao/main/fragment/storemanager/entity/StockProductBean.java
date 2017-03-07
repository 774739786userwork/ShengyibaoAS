package com.bangware.shengyibao.main.fragment.storemanager.entity;

/**
 * 仓库货品报表分析实体类
 * Created by bangware on 2017/3/7.
 */

public class StockProductBean {
    private String goodName;
    private String goodCount;
    private Double goodMoney;
    private Float goodPercent;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public Double getGoodMoney() {
        return goodMoney;
    }

    public void setGoodMoney(Double goodMoney) {
        this.goodMoney = goodMoney;
    }

    public Float getGoodPercent() {
        return goodPercent;
    }

    public void setGoodPercent(Float goodPercent) {
        this.goodPercent = goodPercent;
    }
}
