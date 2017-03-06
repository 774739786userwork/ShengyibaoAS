package com.bangware.shengyibao.customer.model.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.customer.CustomerUtils;
import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;
import com.bangware.shengyibao.customer.model.entity.CustomerSalerAreaInfo;
import com.bangware.shengyibao.customer.model.entity.RegionalArea;
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
import com.bangware.shengyibao.utils.volley.DataRequest;


public class CustomerModelImpl implements CustomerModel {
	/**
	 * 加载客户列表
	 */
	@Override
	public void loadCustomer(String requestTag, int nPage, int nSpage,String xzqh, String phone,User user,
			String shopName,String latitude,String longitude,String nearBy,String type,String compositor,final OnCustomerListener Listener) {
		// TODO Auto-generated method stub
		final String customer_url;
		try {
			customer_url = Model.MYCUSTOMERURL+"?token="+user.getLogin_token()+"&page="+nPage+"&rows="+nSpage+"&xzqh="+xzqh
					+"&contactMobile="+phone+"&contactName="+URLEncoder.encode(shopName, "UTF-8")+
					"&lat="+latitude+"&lng="+longitude+"&nearby="+nearBy+"&type="+type+"&compositor="+compositor;
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, customer_url, null, new Response.Listener<JSONObject>() {
	
				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					if(jsonObject != null){
						List<Customer> customers = CustomerUtils.getCustomersList(jsonObject.toString());
						Listener.onLoadDataSuccess(customers);
					}else{
						Listener.onLoadDataFailure("数据传输失败！");
					}
				}
			}, new ErrorListener() {
	
				@Override
				public void onErrorResponse(VolleyError arg0) {
					Listener.onLoadDataFailure("请求失败，服务器异常......");
				}
			});
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 行政区域数据请求
	 */
	@Override
	public void queryRegionalArea(String requestTag, String province, User user,final OnRegionalListener Listener){
		final String regional_url;
		try {
			regional_url = Model.REGIONALDICTIONARIESURL+"?token="+user.getLogin_token()+"&province="+URLEncoder.encode(province,"UTF-8");
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, regional_url, null, new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					if(jsonObject != null){
						List<RegionalArea> regionalAreas = CustomerUtils.getRegionalAreaData(jsonObject.toString());
						Listener.onLoadAreaDataSuccess(regionalAreas);
					}else{
						Listener.onLoadAreaDataFailure("数据传输失败！");
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Listener.onLoadAreaDataFailure("请求失败，服务器异常......");
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载联系人
	 */
	@Override
	public void loadContact(String requestTag, String customerId,User user, final OnContactListener contactListener) {
		String contact_url = Model.CUSTOMER_INFO_URL+"customers/"+customerId+".json?token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, contact_url, null,new Response.Listener<JSONObject>() {
         
			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if(jsonObject != null){
					try {
						List<Contacts> contactList = CustomerUtils.getContact(jsonObject.toString());
						contactListener.onLoadContact(contactList);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					contactListener.onLoadFailure("数据传输失败！");
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				contactListener.onLoadFailure("请求失败，服务器异常......");
			}
		});
	}
    /**
     * 添加联系人
     */
	@Override
	public void addContacts(String requesTag,User user,String customer_id,String name,String mobile1,String mobile2,String position,final OnAddContactsListener addContactsListener) {
		String add_contact_url = Model.CONTACT_ADD_URL+"?token="+user.getLogin_token()+"&id="+customer_id+"&method=add_customer_contacts";
		try {
			JSONObject contactsData=new JSONObject();
			contactsData.put("customer_id", customer_id);
			contactsData.put("name", name);
			contactsData.put("mobile1", mobile1);
			contactsData.put("mobile2", mobile2);
			contactsData.put("position", position);
			JSONObject jsonData=new JSONObject();
			jsonData.put("contacts", contactsData);
			DataRequest.getInstance().newJsonObjectPostRequest(requesTag, add_contact_url, jsonData, new Response.Listener<JSONObject>() {
				
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject!=null) {
						try {
							Log.d("wwwwww", String.valueOf(jsonObject.getInt("result")));
							switch (jsonObject.getInt("result")) {
							case 0:
								addContactsListener.onAddContactsSuccess("添加成功");
								break;
							default:
								addContactsListener.onAddFailure("添加失败");
								break;
							}
						} catch (JSONException e) {
							addContactsListener.onAddFailure("数据解析错误");
						}
					}else
					{
						addContactsListener.onAddFailure("返回内容为空");
					}
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					addContactsListener.onAddFailure("请求失败，服务器异常......");
				}
			});
		} catch (JSONException e) {
			addContactsListener.onAddFailure("请求参数设置失败");
		}
	}

	//客户进货记录数据解析
	@Override
	public void queryCustomerPurchase(String requestTag, User user,String customer_id,int nPage, int nSpage,
			String begin_date,String end_date, final OnCustomerPurchaseListener purchaseListener) {
		
		String purchase_url = Model.CUSTOMER_PURCHASE_URL + "&token="+user.getLogin_token()+"&customer_id="+customer_id
								+"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage;

		DataRequest.getInstance().newJsonObjectGetRequest(requestTag,purchase_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if(jsonObject != null){

					List<CustomerPurchase> purchaselist = CustomerUtils.getCustomerPurchase(jsonObject.toString());
					purchaseListener.onPurchaseLoaded(purchaselist);
				}else {
					purchaseListener.onError("返回内容为空！数据传输失败！");
                }
			}
			
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				purchaseListener.onError("请求失败，服务器异常......");
			}
        });
	}

	//客户信息
	@Override
	public void queryCustomerInfo(String requestTag, User user,
			String customer_id, final OnCustomerInfoListener customerInfoListener) {
		final String customerInfo_url = Model.CUSTOMER_INFO_URL+"customers/"+customer_id+".json?token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, customerInfo_url, null,new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				if(jsonObject != null){
					try {
						List<Customer> infoList = CustomerUtils.getCustomerInfoData(jsonObject.toString());
						customerInfoListener.onLoadCustomerInfo(infoList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					customerInfoListener.onError("数据传输失败！");
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				customerInfoListener.onError("请求失败，服务器异常......");
			}
		});
	}
    //修改联系人
	@Override
	public void updateContacts(String requesTag, User user, String contacts_id,
			String name, String mobile1, String mobile2, String position,
			final onUpdateContactsListener onUpdateContactsListener) {
		String updatecontacts_url = Model.CONTACT_UPDATE_URL+"?token="+user.getLogin_token()+"&method=edit_contacts";
		try {
			JSONObject contactsData=new JSONObject();
			contactsData.put("id", contacts_id);
			contactsData.put("name", name);
			contactsData.put("mobile1", mobile1);
			contactsData.put("mobile2", mobile2);
			contactsData.put("position", position);
			contactsData.put("operation", "edit");
			Log.d("TSGA", "sdsadsadsad"+contacts_id);
			JSONObject jsonData=new JSONObject();
			jsonData.put("contacts", contactsData);
			
			DataRequest.getInstance().newJsonObjectPostRequest(requesTag, updatecontacts_url, jsonData, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject!=null) {
						try {
							switch (jsonObject.getInt("result")) {
							case 0:
								onUpdateContactsListener.onUpdateContactsSuccess("修改成功");
								break;
							default:
								onUpdateContactsListener.onUpdateFailure("修改失败");
								break;
							}
						} catch (JSONException e) {
							onUpdateContactsListener.onUpdateFailure("Json解析错误");
						}
					}else
					{
						onUpdateContactsListener.onUpdateFailure("修改返回内容为空");
					}
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					onUpdateContactsListener.onUpdateFailure("请求失败，服务器异常......");
				}
			});
		} catch (JSONException e) {
			onUpdateContactsListener.onUpdateFailure("请求参数设置失败");
		}
		
	}
    //删除联系人
	@Override
	public void deleteContacts(String requerTag, User user, String contacts_id,
			final onUpdateContactsListener onUpdateContactsListener) {
		String deletecontacts_url = Model.CONTACT_UPDATE_URL+"?token="+user.getLogin_token()+"&method=edit_contacts";
		try {
			JSONObject contactsData=new JSONObject();
			contactsData.put("id", contacts_id);
			contactsData.put("operation", "delete");
			Log.d("TSGA", "sdsadsadsad"+contacts_id);
			JSONObject jsonData=new JSONObject();
			jsonData.put("contacts", contactsData);
			
			DataRequest.getInstance().newJsonObjectPostRequest(requerTag, deletecontacts_url, jsonData, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject!=null) {
						try {
							switch (jsonObject.getInt("result")) {
							case 0:
								onUpdateContactsListener.ondeleteContactsSuccess("删除成功");
								break;
							default:
								onUpdateContactsListener.ondeleteFailure("删除失败");
								break;
							}
						} catch (JSONException e) {
							onUpdateContactsListener.ondeleteFailure("Json解析错误");
						}
					}else
					{
						onUpdateContactsListener.onUpdateFailure("删除返回内容为空");
					}
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					onUpdateContactsListener.onUpdateFailure("请求失败，服务器异常......");
				}
			});
		} catch (JSONException e) {
			onUpdateContactsListener.onUpdateFailure("请求参数设置失败");
		}
		
		
	}

	//客户营销区域
	@Override
	public void loadSalerArea(String requestTag, User user,
			final OnCustomerSalerAreaListener salerAreaListener) {
		// TODO Auto-generated method stub
		String saler_area_url = Model.SALER_AREA_URL + "?token="+user.getLogin_token();
		DataRequest.getInstance().newGsonGetRequest(requestTag, saler_area_url, CustomerSalerAreaInfo.class, new Response.Listener<CustomerSalerAreaInfo>() {
			@Override
			public void onResponse(CustomerSalerAreaInfo salerAreaInfo) {
				// TODO Auto-generated method stub
				if(salerAreaInfo != null){
					if(CustomerSalerAreaInfo.RESULT_SUCCESS.equals(salerAreaInfo.getResult())){
						salerAreaListener.onLoadCustomerSalerArea(salerAreaInfo);
					}else{
						salerAreaListener.onError(salerAreaInfo.getMsg());
					}
				}else{
					salerAreaListener.onError("数据解析失败！");
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				salerAreaListener.onError("请求失败，服务器异常......");
			}
		});
	}

	//客户地理位置标注数据提交
	@Override
	public void customerMapLocation(String requestTag, User user,
			String customer_id, String longitude, String latitude,
			String location_address,OnCustomerMapLocationListener locationListener) {
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		try {
			data.put("customer_id", customer_id);
			data.put("Longitude", longitude);
			data.put("Latitude", latitude);
			data.put("address", location_address);
			final String map_location_url = Model.CUSTOMER_MAPLOCATION_URL+
					"&customer_id="+data.getString("customer_id")+"&Longitude="+data.getString("Longitude")+
					"&Latitude="+data.getString("Latitude")+"&address="+URLEncoder.encode(data.getString("address"), "UTF-8")+"&token="+user.getLogin_token();
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag,map_location_url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
				}
				
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
				}
			});
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    //快速开单客户
	@Override
	public void loadBilingCustomerData(String requestTag,User user, int nPage,
			int nSpage, String phone, String shopName, String employee_id,
			 final OnCustomerListener Listener) {
		// TODO Auto-generated method stub
		String billing_customer_url;
		try {
			billing_customer_url = Model.MYCUSTOMERURL+"?token="+user.getLogin_token()+"&page="+nPage+"&rows="+nSpage
					+"&contactMobile="+phone+"&contactName="+URLEncoder.encode(shopName, "UTF-8")+"&employee_id="+
					employee_id;
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, billing_customer_url, null, new Response.Listener<JSONObject>() {
	
				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					if(jsonObject != null){
						List<Customer> customers = CustomerUtils.getCustomersList(jsonObject.toString());
						Listener.onLoadDataSuccess(customers);
					}else{
						Listener.onLoadDataFailure("数据传输失败！");
					}
				}
			}, new ErrorListener() {
	
				@Override
				public void onErrorResponse(VolleyError arg0) {
					Listener.onLoadDataFailure("请求失败，服务器异常......");
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//客户附近查询客户
	@Override
	public void loadNearByCustomer(String requestTag, User user, int nPage, int nSpage,String latitude,String longitude,final OnNearByCustomerListener nearbyListener) {
		final String nearBycustomer_url;
		try {
			nearBycustomer_url = Model.MYCUSTOMERURL+"?token="+user.getLogin_token()+"&page="+nPage+"&rows="+nSpage+"&lat="+latitude+"&lng="+longitude+"&type=0"+"&nearby=3000";
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, nearBycustomer_url, null, new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					if(jsonObject != null){
						List<Customer> customers = CustomerUtils.getCustomersList(jsonObject.toString());
						nearbyListener.onLoadNearbyCustomer(customers);
					}else{
						nearbyListener.onLoadDataFailure("数据传输失败！");
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					nearbyListener.onLoadDataFailure("请求失败，服务器异常......");
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//主页月开单客户查询已开单、未开单、送货客户数据请求
	@Override
	public void queryCustomerBillingMonthRecord(String requestTag, User user,int nPage, int nSpage,int show_type,
												String compositor,final OnMonthCustomerBillingRecordListener recordListener) {
		// TODO Auto-generated method stub
		String month_customerBilling_url = Model.MONTHCUSTOMERSALERRECORDURL + "&token="+user.getLogin_token()+"&organization_id="+user.getOrg_id()+
				"&employee_id="+user.getEmployee_id()+"&page="+nPage+"&rows="+nSpage+"&show_type="+show_type+"&compositor="+compositor;
		try {
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, month_customerBilling_url, null, new Response.Listener<JSONObject>() {
	
				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					if(jsonObject != null){
						List<Customer> customer_list = CustomerUtils.getMonthCustomerBilling(jsonObject.toString());
						recordListener.onLoadDataSuccess(customer_list);
					}else{
						recordListener.onLoadDataFailure("数据传输失败！");
					}
				}
			}, new ErrorListener() {
	
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					recordListener.onLoadDataFailure("请求失败，服务器异常......");
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
