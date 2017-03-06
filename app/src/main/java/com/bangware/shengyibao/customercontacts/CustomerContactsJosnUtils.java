package com.bangware.shengyibao.customercontacts;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.CustomerImage;
import com.bangware.shengyibao.customer.model.entity.CustomerKind;


public class CustomerContactsJosnUtils {
private static final String TAG = "CustomerContactsUtils";
	
	//客户联系json数据解析
	public static List<Contacts> getCustomerContactsList(String str){
		List<Contacts> contactslist = new ArrayList<Contacts>();
		try {
			JSONObject jsonObject = new JSONObject(str);
			JSONArray jsonArray;
			jsonArray = jsonObject.getJSONArray("rows");
			Customer customer = null;
			CustomerKind cuskind = null;
			Contacts contact = null;
			CustomerImage image = null;
			JSONObject cusObject = null;
			int len=jsonArray.length();
			Log.d("TGA", "```````````"+len);
			for (int i = 0; i < len; i++) {
				JSONObject conObject=jsonArray.getJSONObject(i);
				contact=new Contacts();
				contact.setId(conObject.getString("id"));
				contact.setName(conObject.getString("name"));
				contact.setMobile1(conObject.getString("mobile1"));
				contact.setMobile2(conObject.getString("mobile2"));
				contact.setPosition(conObject.getString("position"));
				contact.setFirst(conObject.getBoolean("ischeif"));
				cusObject = conObject.getJSONObject("customer");
				
				customer = new Customer();
				customer.setId(cusObject.getString("customer_id"));
				customer.setName(cusObject.getString("customer_name"));
				customer.setCode(cusObject.getString("customer_code"));
				customer.setAddress(cusObject.getString("customer_address"));
				customer.setTotal_record_sum(jsonObject.getInt("total"));

				contact.setCustomer(customer);
				
				JSONArray jsonkindsArray = cusObject.getJSONArray("customer_kinds");
				
				for(int j = 0; j< jsonkindsArray.length(); j++){
					JSONObject kindObject = jsonkindsArray.getJSONObject(j);
					cuskind = new CustomerKind();
					cuskind.setId(kindObject.getString("id"));
					cuskind.setName(kindObject.getString("name"));
					customer.addKind(cuskind);
				}
				JSONArray jsonimgsArray = cusObject.getJSONArray("img_url");
				for (int j = 0; j < jsonimgsArray.length(); j++) {
					image = new CustomerImage();
					JSONObject imgObject = jsonimgsArray.getJSONObject(j);
//					image.setImg_id(imgObject.getString("img_id"));
					image.setImg_url(imgObject.getString("img_url"));
					customer.addImages(image);
				}
				Log.d("Tasd", "asdasdasd++++"+contact.toString());
				contactslist.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contactslist;
	}
}
