package com.bangware.shengyibao.deliverynote.model.impl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.deliverynote.DeliveryNoteQueryUtils;
import com.bangware.shengyibao.deliverynote.model.DeliveryNoteModel;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteMonthQuery;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteListener;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteMonthQueryListener;
import com.bangware.shengyibao.deliverynote.presenter.OnDeliveryNoteSaveListener;
import com.bangware.shengyibao.shopcart.view.SettlementActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class DeliveryNoteModelImpl implements DeliveryNoteModel {
	/**
	 * 保存送货单
	 */
	@Override
	public void save(User user,String requestTag, final DeliveryNote deliveryNote, final OnDeliveryNoteSaveListener listener) {
		String submit_delivery_url = Model.DELIVAERYNOTEURL+"&token="+user.getLogin_token();
		try{
			JSONObject jsonDelivery = new JSONObject();
			JSONObject jsonData = new JSONObject();
			jsonDelivery.put("delivery_id", deliveryNote.getDelivery_id());
			jsonDelivery.put("user_id", user.getUser_id());
			jsonDelivery.put("customer_id", deliveryNote.getCustomer().getId());
			jsonDelivery.put("delivery_goods_count", deliveryNote.getDelivery_goods_count());
			jsonDelivery.put("car_id", deliveryNote.getCarId());

			jsonDelivery.put("wechat_pay",deliveryNote.getPayment().getWechat_payment());
			jsonDelivery.put("alipay",deliveryNote.getPayment().getAlipay());
			jsonDelivery.put("bank_receive_total_sum",deliveryNote.getPayment().getBank_receive_total_sum());
            jsonDelivery.put("cash_receive_total_sum",deliveryNote.getPayment().getCash_receive_total_sum());

			jsonDelivery.put("source_equipment", "1");
			jsonDelivery.put("total_sum", deliveryNote.getTotalAmount());
			jsonDelivery.put("foregift", deliveryNote.getTotalForeigft());
			jsonDelivery.put("paid_total_sum", deliveryNote.getReceiveAmount());
			jsonDelivery.put("delivery_date", deliveryNote.getDeliveryDate());
			jsonDelivery.put("rounding",deliveryNote.getRounding());
			jsonDelivery.put("small_change",deliveryNote.getSmallchange());
			jsonDelivery.put("lng", deliveryNote.getLng());
			jsonDelivery.put("lat", deliveryNote.getLat());
			jsonDelivery.put("remark", deliveryNote.getRemark());
			jsonDelivery.put("contact_mobile", deliveryNote.getContact_phone());
			jsonDelivery.put("contact_name", deliveryNote.getContact_name());
			JSONArray jsonArray = new JSONArray();
			JSONObject objectParams;
						
			for (int i = 0; i < deliveryNote.getGoodsList().size(); i++) {
				objectParams = new JSONObject();
				objectParams.put("sequence",i);
				objectParams.put("product_id", deliveryNote.getGoodsList().get(i).getProduct().getId());
				objectParams.put("product_name", deliveryNote.getGoodsList().get(i).getProduct().getName());
				objectParams.put("sale_quantity", deliveryNote.getGoodsList().get(i).getSalesVolume());
				objectParams.put("gifts_quantity", deliveryNote.getGoodsList().get(i).getGiftsVolume());
				objectParams.put("price", deliveryNote.getGoodsList().get(i).getProduct().getPrice());
				objectParams.put("p_total_foregift",deliveryNote.getGoodsList().get(i).getP_totalforegift());
				objectParams.put("sum", deliveryNote.getGoodsList().get(i).getTotalAmount());
				jsonArray.put(objectParams);
			}
				
			jsonDelivery.put("good_list",jsonArray);
			jsonData.put("data", jsonDelivery);
			
			DataRequest.getInstance().newJsonObjectPostRequest(requestTag,submit_delivery_url, jsonData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (jsonObject != null) {
                    	try{
                    		Log.e("wwwwwwww", "qweeeeee========="+jsonObject.getInt("result"));
	                    	if(jsonObject.getInt("result")==0){
								SettlementActivity.flag=0;
	                    		listener.onSaveSuccess(deliveryNote);
	                    	}else{
								SettlementActivity.flag=0;
	                    		listener.onError("送货单保存失败！请重新结算再提交！");
	                    	}
                    	}catch(JSONException jsonException){
							SettlementActivity.flag=0;
                    		listener.onError("JSON解析错误！");
                    	}
                    } else {
						SettlementActivity.flag=0;
                    	listener.onError("返回内容为空！");
                    }
                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
					SettlementActivity.flag=0;
                	listener.onError("请求失败，服务器异常......");
                }
            });
		}catch(JSONException ex){
			SettlementActivity.flag=0;
			listener.onError("请求参数设置失败！");
		}

	}
	
	/**
	 * 作废
	 */
	public void abort(String requestTag, String deliveryNoteId, User user, DeliveryNoteListener queryListener) {
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		try {
			data.put("deliveryId", deliveryNoteId);
			final String delivery_abortUrl = Model.DELIVERYABORTURL+"?deliverynote_id="+data.getString("deliveryId")+"&token="+user.getLogin_token();
			DataRequest.getInstance().newJsonObjectGetRequest(requestTag,delivery_abortUrl, null, new Response.Listener<JSONObject>() {
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

	/**
	 * 加载送货单详情 
	 */
	@Override
	public void load(String requestTag,String deliveryNoteId,User user,final DeliveryNoteListener detailListener) {
		// TODO Auto-generated method stub
		String noteDetail_url = Model.DELIVERYNOTE_DETAILURL +"&delivery_id="+deliveryNoteId+"&token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, noteDetail_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject != null) {
					List<DeliveryNoteGoods> detailProducts = DeliveryNoteQueryUtils.getGoodsList(jsonObject.toString());
					detailListener.onLoadSuccess(detailProducts);
				}else{
					detailListener.onError("返回内容为空！");
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				detailListener.onError("请求失败，服务器异常......");
			}
		});
	}
	
	/**
	 * 查询送货单
	 */
	@Override
	public void query(String requestTag,String begin_date, String end_date, int nPage,
			int nSpage,int show_type,User user,final DeliveryNoteListener queryListener) {
		// TODO Auto-generated method stub
		String query_deliveryNote_url = Model.DELIVAERY_NOTE_QUERYURL+"&employee_id="+user.getEmployee_id()+"&organization_id="+user.getOrg_id()
				+"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage+"&show_type="+show_type+"&token="+user.getLogin_token();

		DataRequest.getInstance().newJsonObjectGetRequest(requestTag,query_deliveryNote_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if(jsonObject != null){
					List<DeliveryNote> querylist = DeliveryNoteQueryUtils.getDeliveryList(jsonObject.toString());
					queryListener.onQueryDeliveryNote(querylist);
				}else {
					queryListener.onError("返回内容为空！数据传输失败！");
                }
			}
			
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				Class klass = arg0.getClass();
				if(klass == com.android.volley.NetworkError.class) {
					queryListener.onError("网络连接错误");
				}else if (klass == com.android.volley.NoConnectionError.class){
					queryListener.onError("无网络连接");
				}else if(klass == com.android.volley.ServerError.class) {
					queryListener.onError("请求失败，服务器异常......");
				}else if(klass == com.android.volley.TimeoutError.class) {
					queryListener.onError("连接超时......");
				}
				queryListener.onError("请求超时......");
			}
        });
	}
	
	/**
	 * 销售记录
	 */
	@Override
	public void loadDeliveryNoteMonthQuery(String requestTag,User user,String begin_date, String end_date,
			int nPage, int nSpage,int show_type, final OnDeliveryNoteMonthQueryListener listener) {
		String query_deliveryMonthNote_url = Model.DELIVAERY_NOTE_QUERYURL+"&employee_id="+user.getEmployee_id()+"&organization_id="+user.getOrg_id()
				+"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage+"&show_type="+show_type+"&token="+user.getLogin_token();
		Log.e("yyyrrrrrrrrrrrrrrr",query_deliveryMonthNote_url);
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, query_deliveryMonthNote_url, null, new Response.Listener<JSONObject>() {
            
			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject!=null) {
					List<DeliveryNoteMonthQuery> list=DeliveryNoteQueryUtils.getDeliveryMonthList(jsonObject.toString());
					listener.OnLoadNoteMonthQuerySuccess(list);
				}else
				{
					listener.showFailure("返回內容为空！");
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				listener.showFailure("请求失败，服务器异常......");
			}
		});
	}
}
