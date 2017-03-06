package com.bangware.shengyibao.purchaseorder;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.Payment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PurchaseOrderQueryUtils {
	//json解析数据方法
	public static List<DeliveryNote> getDeliveryList(String jsonString){
		List<DeliveryNote> noteQueries = new ArrayList<DeliveryNote>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString.toString()).getJSONObject("data");
			JSONArray jay = jsonObject.getJSONArray("delivery_order_list");
			DeliveryNote deliveryInfo = null;
			Customer customer;
			Payment payment;
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);
				deliveryInfo = new DeliveryNote();
				payment=new Payment();
				payment.setCash_receive_total_sum(job.getDouble("cash_receive_total_sum"));
				payment.setWechat_payment(job.getDouble("wechat_pay"));
				Object o = job.getDouble("bank_receive_total_sum");
				if(o == null){
					payment.setBank_receive_total_sum(0.0);
				}else{
					payment.setBank_receive_total_sum(job.getDouble("bank_receive_total_sum"));
				}
				payment.setAlipay(job.getDouble("alipay"));
				deliveryInfo.setDelivery_id(job.getString("delivery_id"));
				deliveryInfo.setDelivery_date(job.getString("delivery_date"));
				deliveryInfo.setSerial_number(job.getString("serial_number"));
				deliveryInfo.setCarNumber(job.getString("car_number"));
				deliveryInfo.setReceiveAmount(job.getDouble("paid_total_sum"));
				deliveryInfo.setUnpaidAmount(job.getDouble("unpaid_total_sum"));
				deliveryInfo.setContact_name(job.getString("contact_name"));
				deliveryInfo.setContact_phone(job.getString("contact_mobile"));
				deliveryInfo.setTotalForeigft(job.getDouble("foregift"));
				deliveryInfo.setD_total_sum(jsonObject.getString("d_total_sum"));
				deliveryInfo.setD_unpaid_total_sum(jsonObject.getString("d_unpaid_total_sum"));
				deliveryInfo.setTotal_record(jsonObject.getInt("total_record"));
				deliveryInfo.setTotalAmount(job.getDouble("total_sum"));
				deliveryInfo.setFlag(job.getInt("flag"));
				deliveryInfo.setDeliveryNote_product(job.getString("goods_str"));
				customer = new Customer(job.getString("customer_id"),job.getString("shop_name"),job.getString("customer_address"),null);
				deliveryInfo.setCustomer(customer);
				deliveryInfo.setPayment(payment);
				noteQueries.add(deliveryInfo);
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return noteQueries;
	}
	
	//订货单单个产品解析
	public static List<DeliveryNoteGoods> getGoodsList(String jsonString){
		List<DeliveryNoteGoods> list = new ArrayList<DeliveryNoteGoods>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jay = jsonObject.getJSONArray("data");
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);
				Product product = new Product(job.getString("product_id"),job.getString("img_url"),
								job.getString("product_name"),job.getInt("stock"),job.getDouble("total_foregift"));
				
				DeliveryNoteGoods note_goods = new DeliveryNoteGoods( job.getInt("sale_quantity"), 
											job.getInt("gifts_quantity"),
										job.getDouble("price"),job.getDouble("sum"));
				note_goods.setProduct(product);
				list.add(note_goods);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<DeliveryNoteMonthQuery> getDeliveryMonthList(String jsonString){
		List<DeliveryNoteMonthQuery> noteQueries = new ArrayList<DeliveryNoteMonthQuery>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString.toString()).getJSONObject("data");
			JSONArray jay = jsonObject.getJSONArray("delivery_order_list");
			DeliveryNoteMonthQuery deliveryInfo = null;
//			List<Contacts> contactsList = null;
			Customer customer;
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);
				deliveryInfo = new DeliveryNoteMonthQuery();
				
				deliveryInfo.setDelivery_id(job.getString("delivery_id"));
				deliveryInfo.setOrder_Time(job.getString("delivery_date"));
				deliveryInfo.setOrder_Id(job.getString("serial_number"));
				deliveryInfo.setOrder_total_money(job.getString("total_sum"));
				deliveryInfo.setOrder_unpaid(job.getString("unpaid_total_sum"));
				deliveryInfo.setDeliveryNote_product(job.getString("goods_str"));
				deliveryInfo.setContact_name(job.getString("contact_name"));
				deliveryInfo.setCustomer_phone(job.getString("contact_mobile"));

//				job.getString("customer_phone")
			    /*Contacts contacts = new Contacts(job.getString("contacts_name"),job.getString("contacts_mobile"));
			    contactsList = new ArrayList<Contacts>();
			    contactsList.add(contacts);*/
			    
				customer = new Customer(job.getString("customer_id"),job.getString("shop_name"),job.getString("customer_address"),null);
				deliveryInfo.setCustomer(customer);
				
				noteQueries.add(deliveryInfo);
				
				deliveryInfo.setD_total_sum(jsonObject.getString("d_total_sum"));
				deliveryInfo.setD_unpaid_total_sum(jsonObject.getString("d_unpaid_total_sum"));
				deliveryInfo.setTotal_record(jsonObject.getInt("total_record"));
				deliveryInfo.setCustomer_number(jsonObject.getString("customer_number"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return noteQueries;
	}
}
