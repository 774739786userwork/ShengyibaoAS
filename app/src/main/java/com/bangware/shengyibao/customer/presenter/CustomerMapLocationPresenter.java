package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface CustomerMapLocationPresenter {
	//客户地理位置标注
	void loadMapLocation(User user,String customerId, String longitude, String latitude, String location_address);
		
	void destory();
}
