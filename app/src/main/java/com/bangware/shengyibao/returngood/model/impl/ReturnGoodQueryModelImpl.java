package com.bangware.shengyibao.returngood.model.impl;

import android.util.Log;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.returngood.ReturnGoodsUtils;
import com.bangware.shengyibao.returngood.model.ReturnGoodQueryModel;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;
import com.bangware.shengyibao.returngood.presenter.OnReturnGoodListener;
import com.bangware.shengyibao.returngood.presenter.OnReturnNoteDetailListener;
import com.bangware.shengyibao.returngood.presenter.OnReturnNoteListener;
import com.bangware.shengyibao.returngood.presenter.OnReturnsProductListener;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

public class ReturnGoodQueryModelImpl implements ReturnGoodQueryModel {

	/**
	 * 退货单查询请求
	 */
	@Override
	public void loadReturnGood(String requestTag, String begin_date,
			String end_date, int nPage, int nSpage, User user,
			final OnReturnGoodListener goodListener) {
		// TODO Auto-generated method stub
		String return_good_queryUrl = Model.RETURNGOODQUERYURL+"?saler_id="+user.getEmployee_id()+"&return_date="+begin_date+"&end_return_date="+end_date+
				"&page="+nPage+"&rows="+nSpage+"&token="+user.getLogin_token()+"&mobile_abort_status=normal";
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag,return_good_queryUrl, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if(jsonObject != null){
					List<ReturnNote> goodBean = ReturnGoodsUtils.getReturnGood(jsonObject.toString());
					goodListener.onLoadDataSuccess(goodBean);
				}else {
					goodListener.onLoadDataFailure("返回内容为空！数据传输失败！");
                }
			}
			
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				goodListener.onLoadDataFailure("请求失败，服务器异常......");
			}
        });
	}

	/**
	 * 退货单产品详情展示访问请求数据方法
	 */
	@Override
	public void loadReturnNoteDetail(String requestTag, String returnNoteId,
			User user,final OnReturnNoteDetailListener detailListener) {
		// TODO Auto-generated method stub
		final String returnNote_productUrl = Model.RETURNNOTEPRODUCTDETAILURL +returnNoteId+".json?token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, returnNote_productUrl, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				Log.d("eee","====================================>"+returnNote_productUrl);
				// TODO Auto-generated method stub
				List<ReturnNoteGoods> goodslist = ReturnGoodsUtils.getNoteGoods(jsonObject.toString());
				detailListener.onLoadSuccess(goodslist);
				detailListener.onLoadMessage("数据加载成功......");
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				detailListener.onLoadMessage("请求失败，服务器异常......");
			}
		});
	
	}

	/**
	 * 作废数据请求
	 */
	@Override
	public void abortReturnNote(String requestTag, String returnNoteId,
			User user) {
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		try {
			data.put("returnNoteId", returnNoteId);
			final String returnNote_abortUrl = Model.RETURNABORTURL+"?abort_id="+data.getString("returnNoteId")+"&token="+user.getLogin_token();
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag,returnNote_abortUrl, null, new Response.Listener<JSONObject>() {
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
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//加载产品系列请求
	@Override
	public void loadReturnsProduct(String requestTag, User user, final OnReturnsProductListener Listener) {
		final String returnsProduct_url = Model.RETURN_PRODUCT_LISTURL + "?token=" + user.getLogin_token()+ "&employee_id=" + user.getEmployee_id();
		Log.e("eeee", "=========================================>" + returnsProduct_url);
		try {
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag, returnsProduct_url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject != null) {

						List<ProductsTypes> productsList = ReturnGoodsUtils.getProductKinds(jsonObject.toString());
						Listener.onLoadDataSuccess(productsList);
						Listener.onLoadMessage("数据加载成功......");
					}else
					{
						Listener.onLoadMessage("数据加载失败......");
					}
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {
					Listener.onLoadMessage("请求失败，服务器异常......");
				}
			});
		}catch(Exception e) {
			Listener.onLoadMessage("服务器异常......");
		}
	}

	//退货单保存请求
	@Override
	public void save(String requestTag, final ReturnNote returnNote,User user, final OnReturnNoteListener listener) {
		String sava_returnnote_url = Model.RETURNNOREURL + "?token="+user.getLogin_token();
		try{
			JSONObject jsonDelivery = new JSONObject();
			JSONObject jsonData = new JSONObject();
			jsonDelivery.put("return_id", "");
			jsonDelivery.put("user_id", user.getUser_id());
			jsonDelivery.put("customer_id", returnNote.getCustomer().getId());
			jsonDelivery.put("car_id", returnNote.getCarBean().getCarId());
			jsonDelivery.put("total_sum", returnNote.getReturn_total_sum());
			jsonDelivery.put("foregift", returnNote.getTotalForeigft());
			jsonDelivery.put("return_date", returnNote.getReturn_date());
			jsonDelivery.put("return_reson", returnNote.getReturn_reson());
			JSONArray jsonArray = new JSONArray();
			JSONObject objectParams;

			for (int i = 0; i < returnNote.getGoodslist().size(); i++) {
				objectParams = new JSONObject();
				objectParams.put("sequence",i);
				objectParams.put("product_id", returnNote.getGoodslist().get(i).getProduct().getId());
				objectParams.put("product_name", returnNote.getGoodslist().get(i).getProduct().getName());
				objectParams.put("quantity", returnNote.getGoodslist().get(i).getReturnsVolume());
				objectParams.put("gift_quantity", returnNote.getGoodslist().get(i).getGiftsVolume());
				objectParams.put("price", returnNote.getGoodslist().get(i).getProduct().getPrice());
				objectParams.put("sum", returnNote.getGoodslist().get(i).getTotalAmount()+returnNote.getGoodslist().get(i).getTotalForegift());
				if (returnNote.getGoodslist().get(i).getProduct().getIs_show().equals("0")){
					objectParams.put("basic_quantity","");
					objectParams.put("conversion_unit","");
					objectParams.put("basic_price","");
					objectParams.put("basic_unit","");
				}else{
					objectParams.put("basic_quantity",returnNote.getGoodslist().get(i).getRetailVolume());
					objectParams.put("conversion_unit",returnNote.getGoodslist().get(i).getProduct().getConversion_unit());
					objectParams.put("basic_price",returnNote.getGoodslist().get(i).getProduct().getBasic_price());
					objectParams.put("basic_unit",returnNote.getGoodslist().get(i).getProduct().getBasic_unit());
				}

				jsonArray.put(objectParams);
			}

			jsonDelivery.put("goodses",jsonArray);
			jsonData.put("data", jsonDelivery);

			DataRequest.getInstance().newJsonObjectPostRequest(requestTag,sava_returnnote_url, jsonData, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject != null) {
						try{
							if(jsonObject.getInt("result")==0){
								listener.onSaveSuccess(returnNote);
							}else{
								listener.onError("退货单保存失败！请重新提交！");
							}
						}catch(JSONException jsonException){
							listener.onError("JSON解析错误！");
						}
					} else {
						listener.onError("返回内容为空！");
					}
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					listener.onError("请求失败，服务器异常......");
				}
			});
		}catch(JSONException ex){
			listener.onError("请求参数设置失败！");
		}
	}
}



