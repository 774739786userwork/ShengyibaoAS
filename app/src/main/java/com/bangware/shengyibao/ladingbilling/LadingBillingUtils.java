package com.bangware.shengyibao.ladingbilling;

import com.bangware.shengyibao.ladingbilling.model.entity.LadingbillingQuery;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提货单json工具类
 * @author ccssll
 *
 */
public class LadingBillingUtils {
	//json解析数据方法
	public static List<LadingbillingQuery> getLadingBillingList(String jsonString){
		List<LadingbillingQuery> ladingQueries = new ArrayList<LadingbillingQuery>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString.toString()).getJSONObject("data");
			JSONArray jay = jsonObject.getJSONArray("ladingbill_order_list");
			LadingbillingQuery ladingInfo = null;
			for (int i = 0; i < jay.length(); i++) {
				JSONObject job = jay.getJSONObject(i);
				ladingInfo = new LadingbillingQuery();
				ladingInfo.setLadingbilling_date(job.getString("loadingdate"));
				ladingInfo.setLadingbilling_numer(job.getString("serial_number"));
				ladingInfo.setLoadingcount(job.getString("loadingcount"));
				ladingInfo.setLadingbilling_person(job.getString("create_user_name"));
				ladingInfo.setLadingbilling_product(job.getString("goodsStr"));
				
				ladingQueries.add(ladingInfo);
				ladingInfo.setLadingbilling_total_count(jsonObject.getString("total_count"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ladingQueries;
	}
}
