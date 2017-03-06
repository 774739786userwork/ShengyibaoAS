package com.bangware.shengyibao.purchaseorder.model.impl;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.deliverynote.DeliveryNoteQueryUtils;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.deliverynote.presenter.DeliveryNoteListener;
import com.bangware.shengyibao.purchaseorder.PurchaseOrderQueryUtils;
import com.bangware.shengyibao.purchaseorder.model.PurchaseOrderModel;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderListener;
import com.bangware.shengyibao.purchaseorder.view.PurchaseOrderDetailActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class PurchaseOrderModelImpl implements PurchaseOrderModel {

	/**
	 * 加载送订货单详情
	 */
	@Override
	public void load(String requestTag,String deliveryNoteId,User user,final PurchaseOrderListener detailListener) {
		// TODO Auto-generated method stub
		String noteDetail_url = Model.DELIVERYNOTE_DETAILURL +"&delivery_id="+deliveryNoteId+"&token="+user.getLogin_token();
		DataRequest.getInstance().newJsonObjectGetRequest(requestTag, noteDetail_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if (jsonObject != null) {
					List<DeliveryNoteGoods> detailProducts = PurchaseOrderQueryUtils.getGoodsList(jsonObject.toString());
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

	@Override
	public void update_purchase_order(User user,final DeliveryNote deliveryNote, double wechat_payment, double Alipay, double bank_receive_total_sum, double cash_payment,
									  final  PurchaseOrderListener queryListener, String requestTag) {
		String update_purchase_order_url=Model.UPDATE_PURCHASE_ORDER+"&token="+ user.getLogin_token();

		try {
			JSONObject object=new JSONObject();
			object.put("wechat_pay",wechat_payment);
			object.put("delivery_id",deliveryNote.getDelivery_id());
			object.put("alipay",Alipay);
			object.put("bank_receive_total_sum",bank_receive_total_sum);
			object.put("cash_receive_total_sum",cash_payment);
			DataRequest.getInstance().newJsonObjectPostRequest(requestTag,update_purchase_order_url,object, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject jsonObject) {
					if (jsonObject!=null)
					{
						try {
							switch(jsonObject.getInt("result")){
								case 0://修改成功
									queryListener.onError("订货单已配送");
									queryListener.onSaveSuccess();
									break;
								default:
									queryListener.onError("服务器更改订货单失败");
									break;
							}
						} catch (JSONException e) {
							queryListener.onError("JSON解析错误！");
						}

					}else
					{
						queryListener.onError("返回内容为空");
					}
				}

			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {
					queryListener.onError("请求失败，服务器异常......");
				}
			});
		} catch (JSONException e) {
			queryListener.onError("请求参数设置失败！");
		}

	}


	/**
	 * 查询订货单
	 */
	@Override
	public void query(String requestTag,String begin_date, String end_date, int nPage,
			int nSpage,int show_type,User user,final PurchaseOrderListener queryListener) {
		// TODO Auto-generated method stub
		String query_deliveryNote_url = Model.DELIVAERY_NOTE_QUERYURL+"&employee_id="+user.getEmployee_id()+"&organization_id="+user.getOrg_id()
				+"&begin_date="+begin_date+"&end_date="+end_date+"&page="+nPage+"&rows="+nSpage+"&show_type="+show_type+"&token="+user.getLogin_token()+"&status=1";

		DataRequest.getInstance().newJsonObjectGetRequest(requestTag,query_deliveryNote_url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				if(jsonObject != null){
					List<DeliveryNote> querylist = PurchaseOrderQueryUtils.getDeliveryList(jsonObject.toString());
					queryListener.onQueryPurchaseOrder(querylist);
				}else {
					queryListener.onError("返回内容为空！数据传输失败！");
                }
			}
			
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				queryListener.onError("请求失败，服务器异常......");
			}
        });
	}

}
