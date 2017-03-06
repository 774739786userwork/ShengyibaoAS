package com.bangware.shengyibao.customer.presenter;

public interface onUpdateContactsListener {
	 void onUpdateContactsSuccess(String successMessage);
		
	 void onUpdateFailure(String errorMessage);
	 
	 void ondeleteContactsSuccess(String successMessage);
	 
	 void ondeleteFailure(String errorMessage);
}
