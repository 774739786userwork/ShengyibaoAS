package com.bangware.shengyibao.returngood.model.entity;

import com.bangware.shengyibao.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class ProductsTypes {
    private String id;
    private String kindName;
    private CarBean carBean;
    private List<Product> product_list = new ArrayList<Product>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public CarBean getCarBean() {
        return carBean;
    }

    public void setCarBean(CarBean carBean) {
        this.carBean = carBean;
    }

    public List<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<Product> product_list) {
        this.product_list = product_list;
    }

    public void addProduct_list(Product products){
        this.product_list.add(products);
    }
}
