package com.bangware.shengyibao.daysaleaccount.model.entity;

/**
 * 业务员首字母的实体类
 * Created by bangware on 2016/9/1.
 */
public class ChoicePersonBean {
    private String name;         //显示的数据
    private String sortLetters;  //显示数据拼音的首字母
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

}
