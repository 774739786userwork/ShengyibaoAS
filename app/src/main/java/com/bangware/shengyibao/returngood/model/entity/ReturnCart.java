package com.bangware.shengyibao.returngood.model.entity;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ReturnCart implements Serializable {
    private static final long serialVersionUID = -7115024539286808273L;
    private Map<String,ReturnNoteGoods> goodsList = new HashMap<String,ReturnNoteGoods>();
    private String saler_id;  //业务员ID
    private Customer customer;
    private String salerName;   //送货人
    private CarBean carBean;
    private String total_sum;    //金额
    private int total_record; //总记录数
    private double return_total_sum;//总计金额
    private double totalForeigft=0;//总押金
    private int totalVolumes=0;//总数量
    private double totalBasicSum;//总的零售价

    public Map<String, ReturnNoteGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(Map<String, ReturnNoteGoods> goodsList) {
        this.goodsList = goodsList;
    }
    public ReturnNoteGoods getGoods(String productId){
        return goodsList.get(productId);
    }
    public void addGoods(ReturnNoteGoods goods){
        this.goodsList.put(goods.getProduct().getId(), goods);
    }
    public void removeGoods(Product product){
        this.goodsList.remove(product.getId());
    }

    public CarBean getCarBean() {
        return carBean;
    }

    public void setCarBean(CarBean carBean) {
        this.carBean = carBean;
    }

    public void removeAllGoods(){
        this.goodsList.clear();
        return_total_sum = 0;
        totalVolumes = 0;
        totalForeigft = 0;
        totalBasicSum = 0;
    }
    public List<ReturnNoteGoods> getAllGoodsList(){
        List<ReturnNoteGoods> list = new ArrayList<ReturnNoteGoods>();
        for(String key : goodsList.keySet()){
            list.add(goodsList.get(key));
        }
        return list;
    }

    public String getSaler_id() {
        return saler_id;
    }

    public void setSaler_id(String saler_id) {
        this.saler_id = saler_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }


    public String getTotal_sum() {
        return total_sum;
    }

    public void setTotal_sum(String total_sum) {
        this.total_sum = total_sum;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public double getReturn_total_sum() {
        return return_total_sum;
    }

    public void setReturn_total_sum(double return_total_sum) {
        this.return_total_sum = return_total_sum;
    }

    public double getTotalForeigft() {
        return totalForeigft;
    }

    public void setTotalForeigft(double totalForeigft) {
        this.totalForeigft = totalForeigft;
    }

    public int getTotalVolumes() {
        return totalVolumes;
    }

    public void setTotalVolumes(int totalVolumes) {
        this.totalVolumes = totalVolumes;
    }

    public double getTotalBasicSum() {
        return totalBasicSum;
    }

    public void setTotalBasicSum(double totalBasicSum) {
        this.totalBasicSum = totalBasicSum;
    }

    /**
     * 将购物车转换成送货单
     * @return DeliveryNote
     */
    public ReturnNote toReturnNote(){

        ReturnNote returnNote = new ReturnNote();
        returnNote.setCustomer(this.getCustomer());
        returnNote.setReturn_total_sum(String.valueOf(this.getReturn_total_sum()));
        returnNote.setSaler_id(this.getSaler_id());
        returnNote.setTotalForeigft(this.getTotalForeigft());
        for(ReturnNoteGoods goods : this.getAllGoodsList()){
            ReturnNoteGoods dgoods = new ReturnNoteGoods();
            dgoods.setReturnsAmount(goods.getTotalAmount());
            dgoods.setReturnsVolume(goods.getReturnsVolume());
            dgoods.setGiftsVolume(goods.getGiftsVolume());
            dgoods.setRetailVolume(goods.getRetailVolume());
            dgoods.setBasic_price(goods.getBasic_price());
            dgoods.setPrice(goods.getPrice());
            dgoods.setProduct(goods.getProduct());
            returnNote.getGoodslist().add(dgoods);
        }

        return returnNote;
    }
}
