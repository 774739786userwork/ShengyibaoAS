package com.bangware.shengyibao.deliverynote.presenter;


import com.bangware.shengyibao.user.model.entity.User;

/**
 * presenter 接口控制器，用来协调model和view
 * @author ccssll
 *
 */
public interface BillingCustomerPresenter {
	//客户列表数据加载
	void loadBilingCustomerData(int nPage, int nSpage, String phone, String shopName, String employee_id, User user);
	
	void destroy();
}
