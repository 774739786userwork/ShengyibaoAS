package com.bangware.shengyibao.salesamount;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售金额信息解析
 */





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.bangware.shengyibao.salesamount.model.entity.ChildItem;
import com.bangware.shengyibao.salesamount.model.entity.GroupItem;
import com.bangware.shengyibao.salesamount.model.entity.MonthProductAmount;
import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;


public class SalesAmountJsonUtils {
	//销售金额数据解析
	public static List<MonthProductAmount> getGoodsList(String jsonString){
		List<MonthProductAmount> list = new ArrayList<MonthProductAmount>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString.toString()).getJSONObject("data");
			JSONArray jay = jsonObject.getJSONArray("delivery_order_list");
			MonthProductAmount mProductAmount=null;
			for (int i = 0; i < jay.length(); i++) {
				JSONObject obj=jay.getJSONObject(i);
				mProductAmount=new MonthProductAmount();
				mProductAmount.setProductId(obj.getInt("product_id"));
				mProductAmount.setSalesAomuntGiver(obj.getString("gifts_quantity"));
				mProductAmount.setSalesAomuntName(obj.getString("product_name"));
				mProductAmount.setSalesAomuntNumber(obj.getString("sale_quantity"));
				mProductAmount.setSalesAomuntSum(obj.getString("sum"));
				
				
				mProductAmount.setSales_amount_coutomer(jsonObject.getString("customer_number"));
				mProductAmount.setSales_amount_unpaid_sum(jsonObject.getString("d_unpaid_total_sum"));
				mProductAmount.setSales_amount_total_sum(jsonObject.getString("d_total_sum"));
				mProductAmount.setTotal_record(jsonObject.getInt("total_record"));
				list.add(mProductAmount);
			}
			Log.e("list1111", "111111============"+list.size());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//销售排名数据解析
	public static List<SaleRankingBean> getSaleRanking(String jsonString){
		List<SaleRankingBean> list = new ArrayList<SaleRankingBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jay = jsonObject.getJSONArray("data");
			SaleRankingBean rankingBean = null;
			for (int i = 0;i<jay.length();i++){
				JSONObject rankObj = jay.getJSONObject(i);
				rankingBean = new SaleRankingBean();
				rankingBean.setSalerPerson(rankObj.getString("employee_name"));
				rankingBean.setSaleRanking(rankObj.getString("ranking"));
				rankingBean.setCustomerQuantity(rankObj.getString("customer_count"));
				rankingBean.setSaleTotalSum(rankObj.getDouble("total_sum"));

				list.add(rankingBean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	//组内排名数据解析
	public static List<GroupItem> getGroupRanking(String jsonString){
		List<GroupItem> groupList = new ArrayList<GroupItem>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			GroupItem groupItem = null;
			ChildItem childItem = null;
			for (int i=0;i<jsonArray.length();i++){
				JSONObject groupObj = jsonArray.getJSONObject(i);
				groupItem = new GroupItem();
				groupItem.setTitle(groupObj.getString("group_name"));

				JSONArray rankArray = groupObj.getJSONArray("sale_list");
				for (int j=0;j<rankArray.length();j++){
					JSONObject rankObj = rankArray.getJSONObject(j);
					childItem = new ChildItem();
					childItem.setGroupPerson(rankObj.getString("employee_name"));
					childItem.setGroupRanking(rankObj.getString("ranking"));
					childItem.setGroupTotalSum(rankObj.getDouble("total_sum"));
					childItem.setGroupQuantity(rankObj.getString("customer_count"));

					groupItem.addChildItemList(childItem);
				}
				groupList.add(groupItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return groupList;
	}
}
