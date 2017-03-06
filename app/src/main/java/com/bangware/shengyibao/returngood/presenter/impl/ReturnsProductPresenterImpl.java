package com.bangware.shengyibao.returngood.presenter.impl;

import android.util.Log;

import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.ReturnGoodQueryModel;
import com.bangware.shengyibao.returngood.model.entity.ReturnCart;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;
import com.bangware.shengyibao.returngood.model.impl.ReturnGoodQueryModelImpl;
import com.bangware.shengyibao.returngood.presenter.OnReturnsProductListener;
import com.bangware.shengyibao.returngood.presenter.ReturnsProductPresenter;
import com.bangware.shengyibao.returngood.view.ReturnsProductView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public class ReturnsProductPresenterImpl implements ReturnsProductPresenter,OnReturnsProductListener {
    public static final String REQUEST_TAG = "returnProduct";
    private ReturnCart returnCart;
    private ReturnGoodQueryModel goodModel;
    private ReturnsProductView productView;
    private String requestTag;

    public ReturnsProductPresenterImpl(ReturnsProductView productView) {
        this.requestTag = REQUEST_TAG+System.currentTimeMillis();
        this.productView=productView;
        this.returnCart=new ReturnCart();
        this.goodModel = new ReturnGoodQueryModelImpl();
    }

    @Override
    public void onLoadMessage(String message) {

    }

    @Override
    public void loadReturnsProduct(User user) {
        productView.showLoading();
        goodModel.loadReturnsProduct(requestTag, user,this);
    }


    @Override
    public void addGoods(ReturnNoteGoods goods) {
            returnCart.addGoods(goods);
             updateReturnNote();
        productView.doChanged(returnCart);
    }

    @Override
    public void removeGoods(Product product) {
              returnCart.removeGoods(product);
        updateReturnNote();
        productView.doChanged(returnCart);
    }

    @Override
    public void removeAllGoods() {
              returnCart.removeAllGoods();
             updateReturnNote();
             productView.doChanged(returnCart);
    }

    @Override
    public ReturnCart getReturnCart() {
        return this.returnCart;
    }

    @Override
    public void onLoadDataSuccess(List<ProductsTypes> productsList) {
        productView.hideLoading();
        productView.queryReturnProduct(productsList);
    }


    @Override
    public void destroy() {
        DataRequest.getInstance().cancelRequestByTag(requestTag);
    }
    private void updateReturnNote(){
        List<ReturnNoteGoods> returnNoteGoodsList = returnCart.getAllGoodsList();
        int totalVolumes = 0;
        double totalAmount = 0;
        int totalFordgift=0;
        double totalBasic=0;
        for(ReturnNoteGoods goods: returnNoteGoodsList){
            totalVolumes+=goods.getTotalVolume();
            totalAmount+=goods.getTotalAmount()+goods.getTotalForegift();
            totalFordgift+=goods.getTotalForegift();
            totalBasic=goods.getTotalBasicPrice();
        }
        returnCart.setReturn_total_sum(totalAmount);
        returnCart.setTotalVolumes(totalVolumes);
        returnCart.setTotalForeigft(totalFordgift);
        returnCart.setTotalBasicSum(totalBasic);
    }
}
