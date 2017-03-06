package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.Customer;

import java.util.List;

/**
 * Created by ccssll on 2016/8/3.
 */
public interface CustomerNearByView extends BaseView{
    void queryNearByCustomer(List<Customer> customers);
    void showLoadFailureMsg(String errorMessage);
}
