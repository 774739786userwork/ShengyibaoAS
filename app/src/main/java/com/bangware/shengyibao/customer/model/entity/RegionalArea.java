package com.bangware.shengyibao.customer.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccssll on 2016/7/31.
 * 行政区域实体bean
 */
public class RegionalArea {
    private String regional_id;
    private String regional_name;
    private List<DistanceType> typeList = new ArrayList<DistanceType>();

    public String getRegional_id() {
        return regional_id;
    }

    public void setRegional_id(String regional_id) {
        this.regional_id = regional_id;
    }

    public String getRegional_name() {
        return regional_name;
    }

    public void setRegional_name(String regional_name) {
        this.regional_name = regional_name;
    }

    public List<DistanceType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<DistanceType> typeList) {
        this.typeList = typeList;
    }

    public void addTypeList(DistanceType distanceType){
        this.typeList.add(distanceType);
    }
}
