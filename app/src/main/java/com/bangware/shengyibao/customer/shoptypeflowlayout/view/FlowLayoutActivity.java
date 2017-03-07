package com.bangware.shengyibao.customer.shoptypeflowlayout.view;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.shoptypeflowlayout.flowlayout.Flow;
import com.bangware.shengyibao.customer.shoptypeflowlayout.flowlayout.FlowLayout;
import com.bangware.shengyibao.customer.shoptypeflowlayout.presenter.ShopTypeFlowPresenter;
import com.bangware.shengyibao.customer.shoptypeflowlayout.presenter.impl.ShopTypeFlowPresenterImpl;
import com.bangware.shengyibao.customer.view.AddCustomerActivity;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutActivity extends BaseActivity implements View.OnClickListener,ShopTypeFlowView{
    private FlowLayout mFlowLayout;
    private List<Flow> mList = new ArrayList<>();
    private TextView sureBtn,textView;
    private ImageView backImg;

    private ShopTypeFlowPresenter flowPresenter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        SharedPreferences sharedPreferences=this.getSharedPreferences(User.SHARED_NAME, MODE_PRIVATE);

        user= AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        mFlowLayout = (FlowLayout) findViewById(R.id.mFlowLayout);

        sureBtn = (TextView) findViewById(R.id.surebtn_shopflow);
        sureBtn.setOnClickListener(this);

        backImg = (ImageView) findViewById(R.id.shop_flowimg);
        backImg.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.test_textview);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shop_flowimg){
            finish();
        }
        if (v.getId() == R.id.surebtn_shopflow){
            if(mFlowLayout.isSelectPosition()){
//                StringBuffer buffer=new StringBuffer();
                String tempStr = "";
                String tempId = "";
                int count = 0;
                for (int i=0;i<mFlowLayout.isSelectedIndexs().size();i++){
//                    buffer.append(mList.get(mFlowLayout6.isSelectedIndexs().get(i)).getFlowName());
                    tempStr += mList.get(mFlowLayout.isSelectedIndexs().get(i)).getFlowName()+ " ";
                    tempId += mList.get(mFlowLayout.isSelectedIndexs().get(i)).getFlowId()+ " ";
                    count++;
                }
                if (count<2){
                    Toast.makeText(FlowLayoutActivity.this,"至少选择两个！",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(FlowLayoutActivity.this,AddCustomerActivity.class);
                    intent.putExtra("myValue",tempStr);
                    intent.putExtra("myKindId",tempId);
                    setResult(1201,intent);
                    finish();
                }
            }else{
                Toast.makeText(FlowLayoutActivity.this,"你没有进行选择！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initData(){
        flowPresenter = new ShopTypeFlowPresenterImpl(this);
        flowPresenter.loadShopTypeData(user);
    }

    private void initListener(){
        mFlowLayout.setOnSelectListener(new FlowLayout.OnSelectListener() {
            @Override
            public void onSelect(int position) {
//                Toast.makeText(FlowLayoutActivity.this,"position："+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOutLimit() {
                Toast.makeText(FlowLayoutActivity.this,"不能多于两个！",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void doShopTypeLoadSuccess(List<Flow> groupDataList) {
        int childtempCount = groupDataList.size();
        if (childtempCount>0){
            mList.addAll(groupDataList);
            mFlowLayout.setFlowData(mList);
            textView.setVisibility(View.VISIBLE);
        }else{
            showToast("无数据");
        }
    }
}
