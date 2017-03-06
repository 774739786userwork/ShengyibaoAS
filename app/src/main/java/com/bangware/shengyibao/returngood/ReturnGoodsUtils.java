package com.bangware.shengyibao.returngood;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.entity.CarBean;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 退货查询数据解析
 * @author ccssll
 *
 */
public class ReturnGoodsUtils {
 public static List<ReturnNote> getReturnGood(String str){
	 List<ReturnNote> goodsBeans = new ArrayList<ReturnNote>();
	 try {
		JSONObject jsonObject = new JSONObject(str);
		JSONArray jsonArray = jsonObject.getJSONArray("rows");
		ReturnNote bean = null;
		Customer customer = null;
		List<Contacts> contactlist = null;
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject goodObject = jsonArray.getJSONObject(i);
			bean = new ReturnNote();
			
			bean.setReturn_id(goodObject.getString("id"));
			bean.setSaler_id(goodObject.getString("saler_id"));
			bean.setSerial_number(goodObject.getString("serial_number"));
			bean.setReturn_date(goodObject.getString("return_date"));
			bean.setSalerName(goodObject.getString("salerName"));

			CarBean carBean = new CarBean("",goodObject.getString("car_number"));

			bean.setCarBean(carBean);
			bean.setReturn_reson(goodObject.getString("return_reson"));
			bean.setTotal_sum(goodObject.getString("total_sum"));
			bean.setTotalForeigft(goodObject.getDouble("foregift"));
			
			JSONArray jsoncontactArray = goodObject.getJSONArray("contacts");
			for(int k = 0; k<jsoncontactArray.length();k++){
				JSONObject kindObject = jsoncontactArray.getJSONObject(k);
				Contacts contact = new Contacts();
				contact.setId(kindObject.getString("contact_id"));
				contact.setName(kindObject.getString("contact_name"));
				contact.setMobile1(kindObject.getString("mobile1"));
				contact.setMobile2(kindObject.getString("mobile2"));
				contactlist = new ArrayList<Contacts>();
				contactlist.add(contact);
			}
			
			customer = new Customer(goodObject.getString("customer_id"), goodObject.getString("customername"), 
									goodObject.getString("customeraddress"), contactlist);
			bean.setCustomer(customer);
			bean.setTotal_record(jsonObject.getInt("total"));
			bean.setReturn_total_sum(jsonObject.getString("return_total_sum"));
			
			goodsBeans.add(bean);
		}
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	 return goodsBeans;
 }
 
	 /**
	  * 退货单产品详情数据解析
	  * 
	  */
 	public static List<ReturnNoteGoods> getNoteGoods(String key){
		List<ReturnNoteGoods> goodList = new ArrayList<ReturnNoteGoods>();
		try{
			JSONObject jsonObject = new JSONObject(key);
			JSONArray jay = jsonObject.getJSONArray("data");
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);

				ReturnNoteGoods note_goods = new ReturnNoteGoods();
				note_goods.setReturnsVolume(job.getInt("quantity"));
				note_goods.setGiftsVolume(job.getInt("gift_quantity"));
				note_goods.setPrice(job.getDouble("price"));
				note_goods.setReturnsAmount(job.getDouble("sum"));

				Product product = new Product();
				product.setId(job.getString("product_id"));
				product.setImage(job.getString("img_url"));
				product.setName(job.getString("product_name"));
				product.setIs_show(job.getString("is_show"));
				if(product.getIs_show().equals("1")){
					product.setBasic_price(job.getDouble("basic_price"));
					note_goods.setRetailVolume(job.getInt("basic_quantity"));
				}else{
				}
				note_goods.setProduct(product);
				goodList.add(note_goods);
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
 		return goodList;
 	}

	/**
	 * 产品系列分类数据解析
	 * @param key
	 * @return
     */
    public static List<ProductsTypes> getProductKinds(String key){

		List<ProductsTypes> serisesList = new ArrayList<ProductsTypes>();

		try{
			JSONObject jsonObject = new JSONObject(key);

			JSONArray jsonArray = jsonObject.getJSONArray("data");
			ProductsTypes returnsProducts = null;
			Product product = null;

			CarBean carBean = new CarBean(jsonObject.getString("car_id"),jsonObject.getString("car_number"));

			for (int i = 0;i <jsonArray.length(); i++){
				JSONObject tempObj = jsonArray.getJSONObject(i);
				returnsProducts = new ProductsTypes();

				returnsProducts.setId(tempObj.getString("id"));
				returnsProducts.setKindName(tempObj.getString("kind_name"));
				returnsProducts.setCarBean(carBean);
				JSONArray productArray = tempObj.getJSONArray("product_list");
				for (int j = 0;j < productArray.length(); j++){

					JSONObject productObj = productArray.getJSONObject(j);
					product = new Product();
					product.setId(productObj.getString("product_id"));
					product.setName(productObj.getString("product_name"));
					product.setPrice(productObj.getDouble("price"));
					product.setUnit(productObj.getString("unit"));
					product.setForegift(productObj.getDouble("foregift"));
					product.setSpecifications(productObj.getString("specification"));
					product.setImage(productObj.getString("img_url"));
					product.setIs_show(productObj.getString("is_show"));
					if(product.getIs_show().equals("1")){
						product.setBasic_unit(productObj.getString("basic_unit"));
						product.setBasic_price(productObj.getDouble("basic_price"));
						product.setConversion_unit(productObj.getString("conversion_unit"));
					}else{

					}
					returnsProducts.addProduct_list(product);
					Log.e("eeeeee","----------------->"+product.getName()+"======================>"+product.getBasic_unit()+"dddddddd:::::>"+product.getBasic_price());
				}
				serisesList.add(returnsProducts);
			}
		}catch (JSONException e){
			e.printStackTrace();
		}
		return  serisesList;
	}
}
