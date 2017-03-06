package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.Customer;

import java.util.List;

/**
 * 查询附近客户
 * Created by ccssll on 2016/8/3.
 */
public interface OnNearByCustomerListener {
    void onLoadNearbyCustomer(List<Customer> customers);
    void onLoadDataFailure(String errorMessage);
}
