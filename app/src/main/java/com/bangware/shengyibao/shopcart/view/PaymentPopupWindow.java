package com.bangware.shengyibao.shopcart.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.SettingActivity;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.view.BluetoothPrinterActivity;
import com.bangware.shengyibao.purchaseorder.presenter.PurchaseOrderPresenter;
import com.bangware.shengyibao.purchaseorder.view.PurchaseOrderDetailActivity;
import com.bangware.shengyibao.purchaseorder.view.PurchaseOrderDetailView;
import com.bangware.shengyibao.shopcart.adapter.ShowShopCartGoodsListAdapter;
import com.bangware.shengyibao.shopcart.model.entity.Payment;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.shopcart.presenter.ShopCartPresenter;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bangware.shengyibao.utils.customdialog.CustomDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PaymentPopupWindow extends PopupWindow {

	private View mMenuView;
	private EditText wechat_payment,Alipay,bank_receive_total_sum,cash_payment;
    private Button payment_ok;
    private double wechat_payment_text,Alipay_text,bank_receive_total_sum_text,cash_payment_text,total;
    public PaymentPopupWindow(final Activity context, final DeliveryNote deliveryNote, final double receive_edit,boolean isFrist) {
        super(context);  
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mMenuView = inflater.inflate(R.layout.show_paymentpopupwindow, null);
        findView();
        total=receive_edit;
        if (isFrist==true) {
            deliveryNote.getPayment().setCash_receive_total_sum(receive_edit);
        }
       /* wechat_payment.setText(String.valueOf(deliveryNote.getPayment().getWechat_payment()));
        Alipay.setText(String.valueOf(deliveryNote.getPayment().getAlipay()));
        bank_receive_total_sum.setText(String.valueOf(deliveryNote.getPayment().getBank_receive_total_sum()));*/
        cash_payment.setText(String.valueOf(receive_edit));
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);  
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.MATCH_PARENT);  
        //设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.AnimBottom);  
        //实例化一个ColorDrawable颜色为半透明  
    /*    ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景  
        this.setBackgroundDrawable(dw);*/
        this.setOutsideTouchable(true);
        wechat_payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (wechat_payment.getText().toString().isEmpty())
                {
                    wechat_payment_text=0;
                }else
                {
                    wechat_payment_text=Double.valueOf(wechat_payment.getText().toString());
                }

                if (total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text<0)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                }else
                {
                    cash_payment.setText(String.valueOf(NumberUtils.toDoubleRound(total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text)));
                }
            }
        });
        Alipay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Alipay.getText().toString().isEmpty())
                {
                    Alipay_text=0;
                }else
                {
                    Alipay_text=Double.valueOf(Alipay.getText().toString());
                }
                if (total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text<0)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                }else
                {
                    cash_payment.setText(String.valueOf(NumberUtils.toDoubleRound(total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text)));
                }
            }
        });
        bank_receive_total_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bank_receive_total_sum.getText().toString().isEmpty())
                {
                    bank_receive_total_sum_text=0;
                }else
                {
                    bank_receive_total_sum_text=Double.valueOf(bank_receive_total_sum.getText().toString());
                }
                if (total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text<0)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                }else
                {
                    cash_payment.setText(String.valueOf(NumberUtils.toDoubleRound(total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text)));
                }
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        payment_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((wechat_payment.getText().toString()).isEmpty()){
                    wechat_payment_text=0;
                }else {
                    wechat_payment_text = Double.valueOf(wechat_payment.getText().toString());
                }
                if ((cash_payment.getText().toString()).isEmpty())
                {
                    cash_payment_text=0;
                }else{
                    cash_payment_text=Double.valueOf(cash_payment.getText().toString());
                }
                if ((Alipay.getText().toString()).isEmpty()){
                    Alipay_text=0;
                }else {
                    Alipay_text = Double.valueOf(Alipay.getText().toString());
                }
                if ((bank_receive_total_sum.getText().toString()).isEmpty())
                {
                    bank_receive_total_sum_text=0;
                }else {
                    bank_receive_total_sum_text = Double.valueOf(bank_receive_total_sum.getText().toString());
                }
                deliveryNote.getPayment().setWechat_payment(wechat_payment_text);
                deliveryNote.getPayment().setAlipay(Alipay_text);
                deliveryNote.getPayment().setBank_receive_total_sum(bank_receive_total_sum_text);
                deliveryNote.getPayment().setCash_receive_total_sum(cash_payment_text);
                double totalPayment = NumberUtils.toDoubleRound(deliveryNote.getPayment().getTotalPayment()).doubleValue();
             /*   if (totalPayment > receive_edit)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                    return;
                }else {*/
                    PaymentPopupWindow.this.dismiss();
//                }
            }
        });

    }
    public PaymentPopupWindow(final Activity context, final DeliveryNote deliveryNote, final Contacts contacts, final PurchaseOrderPresenter presenter) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.show_paymentpopupwindow, null);
        findView();
        total=deliveryNote.getTotalAmount();
            deliveryNote.getPayment().setCash_receive_total_sum(total);
       /* wechat_payment.setText(String.valueOf(deliveryNote.getPayment().getWechat_payment()));
        Alipay.setText(String.valueOf(deliveryNote.getPayment().getAlipay()));
        bank_receive_total_sum.setText(String.valueOf(deliveryNote.getPayment().getBank_receive_total_sum()));*/
        cash_payment.setText(String.valueOf(total));
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
    /*    ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);*/
        this.setOutsideTouchable(true);
        wechat_payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (wechat_payment.getText().toString().isEmpty())
                {
                    wechat_payment_text=0;
                }else
                {
                    wechat_payment_text=Double.valueOf(wechat_payment.getText().toString());
                }

                if (total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text<0)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                }else
                {
                    cash_payment.setText(String.valueOf(NumberUtils.toDoubleRound(total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text)));
                }
            }
        });
        Alipay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Alipay.getText().toString().isEmpty())
                {
                    Alipay_text=0;
                }else
                {
                    Alipay_text=Double.valueOf(Alipay.getText().toString());
                }
                if (total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text<0)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                }else
                {
                    cash_payment.setText(String.valueOf(NumberUtils.toDoubleRound(total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text)));
                }
            }
        });
        bank_receive_total_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bank_receive_total_sum.getText().toString().isEmpty())
                {
                    bank_receive_total_sum_text=0;
                }else
                {
                    bank_receive_total_sum_text=Double.valueOf(bank_receive_total_sum.getText().toString());
                }
                if (total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text<0)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                }else
                {
                    cash_payment.setText(String.valueOf(NumberUtils.toDoubleRound(total-Alipay_text-wechat_payment_text-bank_receive_total_sum_text)));
                }
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        payment_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((wechat_payment.getText().toString()).isEmpty()){
                    wechat_payment_text=0;
                }else {
                    wechat_payment_text = Double.valueOf(wechat_payment.getText().toString());
                }
                if ((cash_payment.getText().toString()).isEmpty())
                {
                    cash_payment_text=0;
                }else{
                    cash_payment_text=Double.valueOf(cash_payment.getText().toString());
                }
                if ((Alipay.getText().toString()).isEmpty()){
                    Alipay_text=0;
                }else {
                    Alipay_text = Double.valueOf(Alipay.getText().toString());
                }
                if ((bank_receive_total_sum.getText().toString()).isEmpty())
                {
                    bank_receive_total_sum_text=0;
                }else {
                    bank_receive_total_sum_text = Double.valueOf(bank_receive_total_sum.getText().toString());
                }
                deliveryNote.getPayment().setWechat_payment(wechat_payment_text);
                deliveryNote.getPayment().setAlipay(Alipay_text);
                deliveryNote.getPayment().setBank_receive_total_sum(bank_receive_total_sum_text);
                deliveryNote.getPayment().setCash_receive_total_sum(cash_payment_text);
                double totalPayment = NumberUtils.toDoubleRound(deliveryNote.getPayment().getTotalPayment()).doubleValue();
                if (totalPayment > total)
                {
                    Toast.makeText(context,"付款方式总计不能超出实收金额！",Toast.LENGTH_SHORT).show();
                    return;
                }else {
//                    presenter.update_purchase_order(deliveryNote,wechat_payment_text,Alipay_text,bank_receive_total_sum_text,cash_payment_text);
                    Log.e("qweee===>","cash_payment_text"+cash_payment_text+"      bank_receive_total_sum_text"+bank_receive_total_sum_text);
                    Intent intent=new Intent(context, BluetoothPrinterActivity.class);
                    Bundle bundle =  new Bundle();
                    bundle.putSerializable("deliveryNote", (Serializable) deliveryNote);
                    bundle.putSerializable("contacts", contacts);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                  PaymentPopupWindow.this.dismiss();
                }
            }
        });

    }
    private void findView() {
        wechat_payment= (EditText) mMenuView.findViewById(R.id.wechat_payment);
        Alipay= (EditText) mMenuView.findViewById(R.id.Alipay);
        bank_receive_total_sum= (EditText) mMenuView.findViewById(R.id.bank_receive_total_sum);
        payment_ok= (Button) mMenuView.findViewById(R.id.payment_ok);
        cash_payment= (EditText) mMenuView.findViewById(R.id.cash_payment);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View view) {
        if (!this.isShowing()) {
            this.showAtLocation(view, Gravity.CENTER
                    | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            this.dismiss();
        }
    }
}