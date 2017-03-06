package com.bangware.shengyibao.shopcart.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;

import java.util.List;



public interface ShopCartView extends BaseView {
	 
	 void doChanged(ShopCart shopCart);
	 
	 void loadProductStock(List<Product> products);
}
