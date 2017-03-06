package com.bangware.shengyibao.customer;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.CustomerImage;
import com.bangware.shengyibao.customer.model.entity.CustomerKind;
import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;
import com.bangware.shengyibao.customer.model.entity.CustomerShopType;
import com.bangware.shengyibao.customer.model.entity.DistanceType;
import com.bangware.shengyibao.customer.model.entity.RegionalArea;
import com.bangware.shengyibao.manager.shoptype.helper.ChildData;
import com.bangware.shengyibao.manager.shoptype.helper.GroupData;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * json解析工具类
 * @author ccssll
 *
 */
public class CustomerUtils {
	private static final String TAG = "CustomerUtils";
	
	//客户列表json数据解析
	public static List<Customer> getCustomersList(String str){
		List<Customer> customerlist = new ArrayList<Customer>();
		try {
			JSONObject jsonObject = new JSONObject(str);
			JSONArray jsonArray;
			jsonArray = jsonObject.getJSONArray("rows");
			Customer customer = null;
			CustomerKind cuskind = null;
			Contacts contact = null;
			CustomerImage image = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject cusObject = jsonArray.getJSONObject(i);
				customer = new Customer();
				customer.setId(cusObject.getString("id"));
				customer.setCode(cusObject.getString("code"));
				customer.setName(cusObject.getString("name"));
				customer.setTelephone(cusObject.getString("telephone"));
				customer.setAddress(cusObject.getString("address"));
				customer.setDistrict(cusObject.getString("sale_area"));
				customer.setLatitude(cusObject.getString("lat"));
				customer.setLongitude(cusObject.getString("lng"));

				customer.setLasttime(cusObject.getString("last_delivery_date"));
				customer.setDeliver_good_count(cusObject.getString("delivery_goods_count"));
				customer.setTotal_record_sum(jsonObject.getInt("total"));
				customer.setType(jsonObject.getString("type"));
				if(customer.getType().equals("0")){
					customer.setNearBy(cusObject.getString("nearby"));
				}
				JSONArray jsonimageArray = cusObject.getJSONArray("images");
				for(int p = 0;p<jsonimageArray.length();p++){
					JSONObject imageObject = jsonimageArray.getJSONObject(p);
					image = new CustomerImage();
					image.setImg_id(imageObject.getString("img_id"));
					image.setImg_url(imageObject.getString("img_url"));
					customer.addImages(image);
				}
				
				JSONArray jsonkindsArray = cusObject.getJSONArray("customer_kinds");
				for(int j = 0; j< jsonkindsArray.length(); j++){
					JSONObject kindObject = jsonkindsArray.getJSONObject(j);
					cuskind = new CustomerKind();
					cuskind.setId(kindObject.getString("id"));
					cuskind.setName(kindObject.getString("name"));
					customer.addKind(cuskind);
				}
				
				JSONArray jsoncontactArray = cusObject.getJSONArray("contacts");
				for(int k = 0; k<jsoncontactArray.length();k++){
					JSONObject kindObject = jsoncontactArray.getJSONObject(k);
					contact = new Contacts();
					contact.setName(kindObject.getString("name"));
					contact.setMobile1(kindObject.getString("mobile1"));
					contact.setMobile2(kindObject.getString("mobile2"));
					contact.setPosition(kindObject.getString("position"));
					customer.addContacts(contact);
				}
				customerlist.add(customer);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customerlist;
	}

	//行政区域json数据解析
	public static  List<RegionalArea> getRegionalAreaData(String key){
		List<RegionalArea> areaList = new ArrayList<RegionalArea>();
		try
		{
			JSONObject jsonObject = new JSONObject(key);
			JSONArray jsonArray = jsonObject.getJSONArray("city_list").getJSONArray(0);
			RegionalArea regionalArea = null;
			DistanceType distanceType = null;
			for (int i = 0; i<jsonArray.length(); i++){
				JSONObject areaObject = jsonArray.getJSONObject(i);
				regionalArea = new RegionalArea();
				regionalArea.setRegional_id(areaObject.getString("id"));
				regionalArea.setRegional_name(areaObject.getString("name"));

				JSONArray distanceArray = areaObject.getJSONArray("children");
				for(int j = 0;j<distanceArray.length();j++){
					distanceType = new DistanceType();
					JSONObject distanceObject = distanceArray.getJSONObject(j);
					distanceType.setDistance_id(distanceObject.getString("id"));
					distanceType.setDistance_name(distanceObject.getString("name"));

					regionalArea.addTypeList(distanceType);
				}
				areaList.add(regionalArea);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return areaList;
	}
	
	//客户信息解析数据
	public static List<Customer> getCustomerInfoData(String key){
		List<Customer> InfoList = new ArrayList<Customer>();
		try {
			Contacts contact = null;
			Customer customer = null;
			CustomerKind cuskind = null;
			CustomerImage image = null;
			JSONObject jsonObject = new JSONObject(key);
			customer = new Customer();
			customer.setId(jsonObject.getString("id"));
			customer.setName(jsonObject.getString("name"));
			customer.setCode(jsonObject.getString("code"));
			customer.setTelephone(jsonObject.getString("telephone"));
			customer.setAddress(jsonObject.getString("address"));
			customer.setSalerareaId(jsonObject.getString("sale_area_id"));
			customer.setCity(jsonObject.getString("regional_name"));
			customer.setDistrict(jsonObject.getString("sale_area_name"));
			customer.setLongitude(jsonObject.getString("lng"));
			customer.setLatitude(jsonObject.getString("lat"));
			customer.setDeliver_good_count(jsonObject.getString("delivery_goods_count"));
			JSONArray jsonkindsArray = jsonObject.getJSONArray("customer_kinds");
			for(int j = 0; j< jsonkindsArray.length(); j++){
				JSONObject kindObject = jsonkindsArray.getJSONObject(j);
				cuskind = new CustomerKind();
				cuskind.setId(kindObject.getString("id"));
				cuskind.setName(kindObject.getString("name"));
				customer.addKind(cuskind);
			}     
			JSONArray jsonArray = jsonObject.getJSONArray("contacts");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject detailObject = jsonArray.getJSONObject(i);
				contact = new Contacts();
				contact.setId(detailObject.getString("id"));
				contact.setName(detailObject.getString("name"));
				contact.setMobile1(detailObject.getString("mobile1"));
				contact.setMobile2(detailObject.getString("mobile2"));
				contact.setFirst(detailObject.getBoolean("ischeif"));
				customer.addContacts(contact);
			}
			JSONArray jsonimageArray = jsonObject.getJSONArray("img_url");
			for(int p = 0;p<jsonimageArray.length();p++){
				image = new CustomerImage();
				JSONObject imageObject = jsonimageArray.getJSONObject(p);
				image.setImg_id(imageObject.getString("img_id"));
				image.setImg_url(imageObject.getString("img_url"));
				customer.addImages(image);
			}
			
			InfoList.add(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return InfoList;
	}
	
	//客户进货记录
	public static List<CustomerPurchase> getCustomerPurchase(String key){
		List<CustomerPurchase> purchases = new ArrayList<CustomerPurchase>();
		try {
			JSONObject jsonObject = new JSONObject(key.toString()).getJSONObject("data");
			JSONArray jay = jsonObject.getJSONArray("purchase_list");
			CustomerPurchase purchaseinfo = null;
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);
				purchaseinfo = new CustomerPurchase();
				purchaseinfo.setDelivery_id(job.getString("delivery_id"));
				purchaseinfo.setDelivery_date(job.getString("delivery_date"));
				purchaseinfo.setSerial_number(job.getString("serial_number"));
				purchaseinfo.setEmployee_name(job.getString("employee_name"));
				purchaseinfo.setTotal_sum(job.getDouble("total_sum"));
                purchaseinfo.setProduct_edit(job.getString("goods_str"));
				purchaseinfo.setUnpaid_total_sum(job.getDouble("unpaid_total_sum"));
				purchaseinfo.setPurchase_total_sum(jsonObject.getString("purchase_total_sum"));
				purchaseinfo.setPurchase_untotal_sum(jsonObject.getString("purchase_untotal_sum"));
				purchaseinfo.setTotal_record(jsonObject.getInt("total_record"));
				purchases.add(purchaseinfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return purchases;
	}
	
	//本月未开单客户、已开单客户、送货客户数据解析
	public static List<Customer>  getMonthCustomerBilling(String key){
		List<Customer> customers = new ArrayList<Customer>();
		try {
			JSONObject jsonObject = new JSONObject(key.toString()).getJSONObject("data");
			JSONArray jayArray = jsonObject.getJSONArray("customer_list");
			Customer customerInfo = null;
			Contacts contactInfo = null;
			CustomerImage imageInfo = null;
			for (int i = 0; i < jayArray.length(); i++) {
				JSONObject job = jayArray.getJSONObject(i);
				customerInfo = new Customer();
				customerInfo.setId(job.getString("customer_id"));
				customerInfo.setName(job.getString("customer_name"));
				customerInfo.setAddress(job.getString("address"));
				customerInfo.setCompositor(job.getString("compositor"));

				customerInfo.setLasttime(job.getString("last_delivery_date"));
				customerInfo.setDeliver_good_count(job.getString("delivery_goods_count"));
				customerInfo.setPurchase_total_sum(job.getString("total_sum"));
				JSONArray jsonArray = job.getJSONArray("contacts");
				for (int c = 0; c < jsonArray.length(); c++) {
					JSONObject detailObject = jsonArray.getJSONObject(c);
					contactInfo = new Contacts();
					contactInfo.setId(detailObject.getString("contact_id"));
					contactInfo.setName(detailObject.getString("contact_name"));
					contactInfo.setMobile1(detailObject.getString("mobile1"));
					contactInfo.setMobile2(detailObject.getString("mobile2"));
					customerInfo.addContacts(contactInfo);
				}
				
				JSONArray jsonimageArray = job.getJSONArray("images");
				for(int p = 0;p<jsonimageArray.length();p++){
					imageInfo = new CustomerImage();
					JSONObject imageObject = jsonimageArray.getJSONObject(p);
					imageInfo.setImg_id(imageObject.getString("img_id"));
					imageInfo.setImg_url(imageObject.getString("img_url"));
					customerInfo.addImages(imageInfo);
				}
				customers.add(customerInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}
	
	/**
	 * Array to String
	 * @param str
	 * @return
	 */
    public static String getArrayToString(String str){
    
		String[] wordList = (str.substring(1,str.length()-1).replaceAll("\"","")).split(",");
		
		String newStr = "";
		
		for (String shop_type : wordList) {
			newStr += shop_type+"\t";
		}
		return newStr;
    }
    
    
    /**
     * 客户联系人
     * @param key
     * @param
     * @return
     * @throws JSONException
     */
    public static List<Contacts> getContact(String key) throws JSONException{
    	List<Contacts> contact_list = new ArrayList<Contacts>();
    	
    	JSONObject jsonObject = new JSONObject(key);
    	JSONArray jsonArray = jsonObject.getJSONArray("contacts");
    	Contacts contact;
    	for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject cusObject = jsonArray.getJSONObject(i);
			contact = new Contacts();
			contact.setId(cusObject.getString("id"));
			contact.setName(cusObject.getString("name"));
			contact.setMobile1(cusObject.getString("mobile1"));
			contact.setMobile2(cusObject.getString("mobile2"));
			contact.setPosition(cusObject.getString("position"));
			contact_list.add(contact);
		}
    	return contact_list;
    	
    }
    
    /**
     * 客户店面类型
     * @throws JSONException ,String jsonString
     */

	public static List<GroupData> getShopType(String jsonString){
		List<GroupData> datalist = new ArrayList<GroupData>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			GroupData groupData = null;
			ChildData childData = null;

			for (int i=0;i<jsonArray.length();i++){
				JSONObject groupObj = jsonArray.getJSONObject(i);
				groupData = new GroupData();
				groupData.setId(groupObj.getInt("kind_id"));
				groupData.setGroupName(groupObj.getString("kind_name"));

				JSONArray typeArray = groupObj.getJSONArray("kind_list");
				for (int j=0;j<typeArray.length();j++){
					JSONObject typeObj = typeArray.getJSONObject(j);
					childData = new ChildData();
					childData.setChildId(typeObj.getString("id"));
					childData.setChildName(typeObj.getString("name"));
					groupData.addItems(childData);
				}
				datalist.add(groupData);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return datalist;
	}
    public static List<CustomerShopType> getShopType(String key,String jsonString) throws JSONException{
    	List<CustomerShopType> shoptypeList = new ArrayList<CustomerShopType>();
    	
    	JSONObject jsonObject = new JSONObject(jsonString.toString()).getJSONObject("data");
    	JSONArray jsonArray = jsonObject.getJSONArray(key);
    	
    	CustomerShopType shopType;
    	for (int i = 0; i < jsonArray.length(); i++) {
    		shopType = new CustomerShopType();
    		JSONObject cusObject = jsonArray.getJSONObject(i);
    		shopType.setId(cusObject.getInt("id"));
    		shopType.setName(cusObject.getString("name"));
    		shoptypeList.add(shopType);
    	}
		return shoptypeList;
    }
    
    
}
