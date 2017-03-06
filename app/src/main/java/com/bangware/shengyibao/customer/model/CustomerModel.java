package com.bangware.shengyibao.customer.model;


import com.bangware.shengyibao.customer.presenter.OnAddContactsListener;
import com.bangware.shengyibao.customer.presenter.OnContactListener;
import com.bangware.shengyibao.customer.presenter.OnCustomerInfoListener;
import com.bangware.shengyibao.customer.presenter.OnCustomerListener;
import com.bangware.shengyibao.customer.presenter.OnCustomerMapLocationListener;
import com.bangware.shengyibao.customer.presenter.OnCustomerPurchaseListener;
import com.bangware.shengyibao.customer.presenter.OnCustomerSalerAreaListener;
import com.bangware.shengyibao.customer.presenter.OnMonthCustomerBillingRecordListener;
import com.bangware.shengyibao.customer.presenter.OnNearByCustomerListener;
import com.bangware.shengyibao.customer.presenter.OnRegionalListener;
import com.bangware.shengyibao.customer.presenter.onUpdateContactsListener;
import com.bangware.shengyibao.user.model.entity.User;

import java.io.UnsupportedEncodingException;


public interface CustomerModel {
	//客户列表及快捷查询
	void loadCustomer(String requestTag, int nPage, int nSpage,String xzqh, String phone, User user, String shopName,
					  String latitude,String longitude,String nearBy,String type,String compositor,OnCustomerListener Listener);

	//行政区域查询
	void queryRegionalArea(String requestTag,String province,User user,OnRegionalListener Listener);

	//客户进货记录通过时间、id查询
	void queryCustomerPurchase(String requestTag, User user, String customer_id, int nPage, int nSpage,
							   String begin_date, String end_date, OnCustomerPurchaseListener purchaseListener);
	
	//客户月开单情况查询未开单、已开单、送过货客户记录
	void queryCustomerBillingMonthRecord(String requestTag,User user,int nPage,int nSpage,int show_type,String compositor,OnMonthCustomerBillingRecordListener recordListener);
	
	//客户信息
	void queryCustomerInfo(String requestTag,User user,String customer_id,OnCustomerInfoListener customerInfoListener);
	
	//查看联系人
	void loadContact(String requestTag, String customerId,User user,OnContactListener contactListener);
	
	//添加联系人
	void addContacts(String requesTag,User user,String customer_id,String name,String mobile1,String mobile2,String position,OnAddContactsListener addContactsListener);
    
	//修改联系人
	void updateContacts(String requesTag,User user,String contacts_id,String name,String mobile1,String mobile2,String position,onUpdateContactsListener onUpdateContactsListener);
	
	//删除联系人
	void deleteContacts(String requestTag,User user,String contacts_id,onUpdateContactsListener onUpdateContactsListener);

	//营销区域
	void loadSalerArea(String requestTag,User user,OnCustomerSalerAreaListener salerAreaListener);
	
	//客户位置标注
	void customerMapLocation(String requestTag,User user,String customer_id,String longitude,String latitude,String location_address,OnCustomerMapLocationListener locationListener);
	
	//开单选择客户
	void loadBilingCustomerData(String requestTag,User user,int nPage,int nSpage,String phone,String shopName,String employee_id, OnCustomerListener Listener);

	//查询客户附近的客户
	void loadNearByCustomer(String requestTag,User user,int nPage,int nSpage,String latitude,String longitude,OnNearByCustomerListener nearbyListener);
}
 