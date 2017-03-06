package com.bangware.shengyibao.returngood.model.entity;

import java.io.Serializable;

/**
 * 车辆的基本信息
 * Created by ccssll on 2016/8/2.
 */
public class CarBean implements Serializable{
    private static final long serialVersionUID = -6115024539286808273L;
    private String carId;
    private String carNumber;

    public CarBean() {
    }

    public CarBean(String carId, String carNumber) {
        this.carId = carId;
        this.carNumber = carNumber;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
