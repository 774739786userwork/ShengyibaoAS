package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.CustomerSalerArea;

import java.util.List;


public interface CustomerSalerAreaView extends BaseView {
	void loadSalerAreaData(List<CustomerSalerArea> salerAreas);
}
