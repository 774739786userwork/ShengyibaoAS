package com.bangware.shengyibao.main;

import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;

import org.json.JSONException;
import org.json.JSONObject;

public class MainJsonUtils {
	public static ToDaySummary getHeaderData(String key){
		ToDaySummary ts = new ToDaySummary();
		try {
			JSONObject jsonObject = new JSONObject(key.toString()).getJSONObject("data");
			ts.setTodaytime(jsonObject.getString("today"));
			ts.setTodaysaler(jsonObject.getString("delivery_total_sum"));
			ts.setLadingbill_sum(jsonObject.getInt("ladingbill_number"));
			ts.setDeliverynote_sum(jsonObject.getInt("delivery_note_number"));
			ts.setOrdernote_sum(jsonObject.getInt("purchase_order_number"));
			ts.setReturngood_sum(jsonObject.getInt("return_list_number"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;
	}
	public static ThisMonthSummary getSalerData(String key){
		ThisMonthSummary summary = new ThisMonthSummary();
		try {
			JSONObject jsonObject = new JSONObject(key.toString()).getJSONObject("data");
			summary.setMonthtime(jsonObject.getString("date"));
			summary.setMonthtop(jsonObject.getString("ranking"));
			summary.setCustomersum(jsonObject.getString("total_customers"));
			summary.setBillingcustomer(jsonObject.getString("un_total_customers"));
			summary.setMonthsalermoney(jsonObject.getString("total_sum"));
			summary.setMonthsalerunpaid(jsonObject.getString("unpaid_total_sum"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return summary;
	}
}
