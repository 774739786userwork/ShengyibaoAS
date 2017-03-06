package com.bangware.shengyibao.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.view.AddCustomerActivity;
import com.bangware.shengyibao.customer.view.CustomerActivity;
import com.bangware.shengyibao.customer.view.MonthCustomerBillingActivity;
import com.bangware.shengyibao.customercontacts.view.QueryCustomerContactsActivity;
import com.bangware.shengyibao.main.model.entity.ThisMonthSummary;
import com.bangware.shengyibao.main.model.entity.ToDaySummary;
import com.bangware.shengyibao.main.presenter.MainPresenter;
import com.bangware.shengyibao.main.presenter.impl.MainPresenterImpl;
import com.bangware.shengyibao.main.view.MainView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

/**
 * Created by bangware on 2017/2/21.
 */

public class CustomerFragment extends Fragment implements MainView {
    private RelativeLayout customerData,customerContactData,myCustomerData,addCustomerData;
    private View view = null;
    private int maxNumber,billingcustomerQuantity,nobillingCustomer;
    private MainPresenter presenter;
    private User user;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.acticity_customer_fragment, container,
                    false);
        }
        if(view!=null)
        {
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(User.SHARED_NAME, Context.MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findById();
        setListener();
    }

    private void findById(){
        customerData = (RelativeLayout) getView().findViewById(R.id.customer_data_rel);
        customerContactData = (RelativeLayout) getView().findViewById(R.id.customer_contact_rel);
        myCustomerData = (RelativeLayout) getView().findViewById(R.id.my_customer_rel);
        addCustomerData = (RelativeLayout) getView().findViewById(R.id.add_customer_rel);

        //初始化Presenter
        presenter=new MainPresenterImpl(this);
        presenter.loadThisMonthSummary(user);
        presenter.loadToDaySummary(user);
    }

    private void setListener(){
        MyOnClickListener listener = new MyOnClickListener();
        customerData.setOnClickListener(listener);
        customerContactData.setOnClickListener(listener);
        myCustomerData.setOnClickListener(listener);
        addCustomerData.setOnClickListener(listener);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String text) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    private class MyOnClickListener implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.customer_data_rel:
                    intent = new Intent(getActivity(), CustomerActivity.class);
                    startActivity(intent);
                    break;
                case R.id.customer_contact_rel:
                    intent = new Intent(getActivity(), QueryCustomerContactsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_customer_rel:
                    intent = new Intent(getActivity(), MonthCustomerBillingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("customerQuantity", maxNumber);
                    bundle.putInt("billingcustomerQuantity", billingcustomerQuantity);
                    bundle.putInt("nobillingCustomer", nobillingCustomer);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.add_customer_rel:
                    intent = new Intent(getActivity(), AddCustomerActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void doTodaySummaryLoadSuccess(ToDaySummary bean) {

    }

    @Override
    public void doThisMonthSummaryLoadSuccess(ThisMonthSummary bean) {
        if (bean != null){
            maxNumber = Integer.valueOf(bean.getCustomersum());
            billingcustomerQuantity = Integer.valueOf(bean.getBillingcustomer());

            nobillingCustomer = maxNumber - billingcustomerQuantity;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止监听screen状态
        if(presenter!=null){
            presenter.destroy();
        }
    }
}
