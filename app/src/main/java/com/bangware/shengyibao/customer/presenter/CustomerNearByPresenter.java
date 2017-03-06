package com.bangware.shengyibao.customer.presenter;


import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by ccssll on 2016/8/3.
 */
public interface CustomerNearByPresenter {
    void loadNearByCustomerData(User user,int nPage, int nSpage, String latitude, String longitude);
    void destroy();
}
