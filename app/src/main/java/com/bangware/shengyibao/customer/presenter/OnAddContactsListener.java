package com.bangware.shengyibao.customer.presenter;


public interface OnAddContactsListener {
	
     void onAddContactsSuccess(String successMessage);
	
	  void onAddFailure(String errorMessage);
}
