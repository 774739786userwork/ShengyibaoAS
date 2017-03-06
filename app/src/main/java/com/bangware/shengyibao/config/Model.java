package com.bangware.shengyibao.config;

/**
 * 静态变量的存放
 * 192.168.5.113
 *my.duobangjc.com:8088
 *my.duobangjc.com:8086
 * */

public class Model {
 
		// 网络交互地址前段
		public static String HTTPURL = "http://my.duobangjc.com:8088/";
		
		//登录
		public static String LOGINURL = "users/sign_in.json";

	     //修改密码
	   public  static String UPDATEPASSWORD="users.json";
		
		//app版本升级接口
		public static String UPDATEVERSION = HTTPURL+"mobile_interfaces/mobile_info?method=update_app_version";

		//用户反馈
		public static String SUGGESTIONURL = HTTPURL + "feedback_suggestions.json";

	    //订货单确认配送
	    public  static String UPDATE_PURCHASE_ORDER=HTTPURL+"mobile_interfaces/get_mobile_info?method=update_purchase_order";
		
		//主页月排名、月销售额、客户数
		public static String MAINURL = HTTPURL+"mobile_interfaces/mobile_info?method=month_sale_briefly_info";

		//销售排名
		public static String SALERANKINGURL = HTTPURL + "mobile_interfaces/mobile_info?method=get_month_sale_ranking_list";

		//组内排名
		public static String GROUPRANKINGURL = HTTPURL + "mobile_interfaces/mobile_info?method=month_sale_group_ranking";
		
		//主页头部数据及单据接口
		public static String MAINHEADERURL = HTTPURL+"mobile_interfaces/mobile_info?method=order_notice";
		
		//主页查询本月开单客户、未开单客户、送货客户记录
		public static String MONTHCUSTOMERSALERRECORDURL = HTTPURL + "mobile_interfaces/mobile_info?method=get_delivery_customers";
		
		//消息推送
		public static String MESSAGEURL = HTTPURL + "mobile_interfaces/get_mobile_info/";
		
		//提货单数据接口
		public static String LADINGBILLURL = HTTPURL + "mobile_interfaces/mobile_info?method=get_ladingbill_product_list";
		
		//提货单查询接口
		public static String LADINGBILL_QUERYURL = HTTPURL + "mobile_interfaces/mobile_info?method=employee_to_ladingbill_order";

		//余货查询接口
		public static String STOCKQUERYURL = HTTPURL + "mobile_interfaces/mobile_info?method=get_ladingbill_product_list";
		
		//送货单保存数据接口
		public static String DELIVAERYNOTEURL = HTTPURL + "mobile_interfaces/get_mobile_info?method=add_delivery_order";

		//送货单查询数据接口
		public static String DELIVAERY_NOTE_QUERYURL = HTTPURL + "mobile_interfaces/mobile_info?method=employee_to_delivery_order";
		
		//送货单产品详情列表接口以及送货单修改接口
		public static String DELIVERYNOTE_DETAILURL = HTTPURL + "mobile_interfaces/mobile_info?method=get_delivery_product_list";
		
		//送货单产品详情作废接口
		public static String DELIVERYABORTURL = HTTPURL + "delivery_notes/delivery_abort";
		
		//退货单查询接口
		public static String RETURNGOODQUERYURL = HTTPURL + "app_api/return_lists.json";

	   //退货开单产品列表接口
	   public  static String RETURN_PRODUCT_LISTURL=HTTPURL+"app_api/product_kinds.json";
		
		//退货单产品详情数据接口
		public static String RETURNNOTEPRODUCTDETAILURL = HTTPURL + "app_api/return_lists/";
		
		//退货单产品作废接口
		public static String RETURNABORTURL = HTTPURL + "return_lists/abort";

		//退货单保存提交接口
		public static String RETURNNOREURL = HTTPURL + "app_api/return_lists/create_return_list.json";

		//日销售清单查询接口
		public  static String DAYSALEACCOUNTURL = HTTPURL + "app_api/saler_journals.json";

		//日销售清单产品查询
		public static  String DAYSALEPRODUCTACCOUNTURL = HTTPURL + "app_api/saler_journals/";
		
		//客户列表
		public static String MYCUSTOMERURL = HTTPURL+"app_api/customers.json";

		//行政区域
		public static String REGIONALDICTIONARIESURL = HTTPURL + "app_api/regionaldictionaries.json";
		
		//产品图片链接
		public static String PRODUCT_IMAGE_URL = HTTPURL;
		
		//客户信息资料
		public static String CUSTOMER_INFO_URL = HTTPURL;
		
		//客户联系人
		public static String CUSETOMER_CONTACTS_DETAIL_URL=HTTPURL+"contacts.json";

		//添加联系人
		public static String CONTACT_ADD_URL=HTTPURL+"/mobile_interfaces/get_mobile_info";
		
		//删除联系人
		public static String CONTACT_UPDATE_URL=HTTPURL+"/mobile_interfaces/get_mobile_info";
		
		//添加客户 
		public static String CUSTOMER_ADD_URL = HTTPURL + "mobile_interfaces/get_mobile_info?method=addCustomersInfo";
		
		//编辑客户
		public static String CUSTOMER_EDIT_URL = HTTPURL + "mobile_interfaces/get_mobile_info?method=edit_customer_info";
		
		//客户店面类型
		public static String CUSTOMER_TYPE_URL = HTTPURL + "mobile_interfaces/mobile_info?method=getCustomerKinds";
		
		//客户进货记录查询
		public static String CUSTOMER_PURCHASE_URL = HTTPURL + "mobile_interfaces/mobile_info?method=query_customer_purchase";
	
		//营销区域
		public static String SALER_AREA_URL = HTTPURL + "sale_areas.json";
		
		//客户地理位置标注
		public static String CUSTOMER_MAPLOCATION_URL = HTTPURL + "mobile_interfaces/mobile_info?method=edit_customer_position";
}

