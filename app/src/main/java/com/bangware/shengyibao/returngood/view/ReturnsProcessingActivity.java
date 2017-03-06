package com.bangware.shengyibao.returngood.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.adapter.LeftListAdapter;
import com.bangware.shengyibao.returngood.adapter.MainSectionedAdapter;
import com.bangware.shengyibao.returngood.model.entity.CarBean;
import com.bangware.shengyibao.returngood.model.entity.ReturnCart;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;
import com.bangware.shengyibao.returngood.presenter.ReturnsProductPresenter;
import com.bangware.shengyibao.returngood.presenter.impl.ReturnsProductPresenterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.DensityUtil;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bangware.shengyibao.view.PinnedHeaderListView;
import com.jauker.widget.BadgeView;


/**
 * 退货处理页面
 *
 * @author zcb
 */
public class ReturnsProcessingActivity extends BaseActivity implements ReturnsProductView {
    private TextView mRP_Customer_Name;//客户名称
    private TextView mRP_Customer_Contact;//客户联系人
    private TextView mRP_Customer_Mobile;//客户电话
    private TextView mRP_Customer_Address;//客户地址
    private TextView mRP_Amount;//总退金额
    private Button mRP_Btn;//去结算按钮
    private ImageView mRP_back;//回退按钮
    private Contacts contact;
    private ReturnNote mReturnNote;
    private ReturnNoteGoods mReturnNoteGoods;
    private RelativeLayout  ReturnsProcessing_board;
    private BadgeView mReturnsProcessingIcon;
    private ReturnGoodPopupWindow mReturnGoodPopupWindow;
    private ReturnsProductPopupWindow mReturnsProductPopupWindow;
    private long mExitTime = System.currentTimeMillis();
    ListView leftListview;//左侧产品系列分类listview
    PinnedHeaderListView pinnedListView;//右侧更多产品展示listview
    private boolean isScroll = true;
    private LeftListAdapter adapter;//左侧数据源适配

    private MainSectionedAdapter sectionedAdapter;//右边数据适配器
    private ReturnsProductPresenter productPresenter;
    private List<ProductsTypes> returnsProductsList = new ArrayList<ProductsTypes>();
    private List<ReturnNoteGoods> goodsList;
    private CarBean carBean;
    private boolean[] flagArray =new boolean[]{};
    private Product[][] rightProductArray = new Product[][]{};

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除页面标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returnsprocessing);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        findView();//初始化控件
        setListener();//设置点击事件
    }

    private void setListener() {
        // TODO Auto-generated method stub
        //回退事件
        mRP_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        //退款跳转到下一个界面
        mRP_Btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(ReturnsProcessingActivity.this, RefundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("returnNote",  (Serializable)mReturnNote);
                bundle.putSerializable("returnCart",  (Serializable)productPresenter.getReturnCart());
                bundle.putSerializable("contact", (Serializable) contact);
                bundle.putSerializable("carbean",carBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ReturnsProcessing_board.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt((String) (mReturnsProcessingIcon.getText())) > 0) {
                    mReturnGoodPopupWindow = new ReturnGoodPopupWindow(ReturnsProcessingActivity.this, productPresenter);
                    mReturnGoodPopupWindow.getContentView().getHeight();
                    int goodsCount = productPresenter.getReturnCart().getAllGoodsList().size();
                    int popWindowHeight = DensityUtil.dip2px(ReturnsProcessingActivity.this, (goodsCount * 49) + 40 + 58);
                    int d = popWindowHeight;
                    int h = ReturnsProcessing_board.getHeight() + pinnedListView.getHeight() + DensityUtil.dip2px(ReturnsProcessingActivity.this, 0);

                    if (d > h) {
                        mReturnGoodPopupWindow.setHeight(pinnedListView.getHeight());
                        mReturnGoodPopupWindow.showAsDropDown(view, 0, -h);

                    } else {
                        mReturnGoodPopupWindow.showAsDropDown(view, 0, -d);
                    }
                    backgroundAlpha(0.4f);
                    mReturnGoodPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                        @Override
                        public void onDismiss() {
                            // TODO Auto-generated method stub
                            backgroundAlpha(1f);
                        }
                    });
                }
            }
        });
    }


    private void findView() {
        mRP_back = (ImageView) findViewById(R.id.ReturnsProcessing_back);
        mRP_Customer_Name = (TextView) findViewById(R.id.ReturnsProcessing_Customer_Name);
        mRP_Customer_Contact = (TextView) findViewById(R.id.ReturnsProcessing_Customer_Contact);
        mRP_Customer_Mobile = (TextView) findViewById(R.id.ReturnsProcessing_Customer_Mobile);
        mRP_Customer_Address = (TextView) findViewById(R.id.ReturnsProcessing_Customer_Address);
        mRP_Amount = (TextView) findViewById(R.id.ReturnsProcessing_Amount);
        mRP_Btn = (Button) findViewById(R.id.ReturnsProcessing_Btn);
        ReturnsProcessing_board = (RelativeLayout) findViewById(R.id.ReturnsProcessing_board);
        contact = (Contacts) getIntent().getSerializableExtra("contacts");
        mReturnNote = (ReturnNote) getIntent().getSerializableExtra("returnNote");
        mRP_Customer_Name.setText(contact.getCustomer().getName());
        mRP_Customer_Contact.setText(contact.getName());
        mRP_Customer_Mobile.setText(contact.getMobile1());
        mRP_Customer_Address.setText(contact.getCustomer().getAddress());

        productPresenter = new ReturnsProductPresenterImpl(this);
        productPresenter.loadReturnsProduct(user);
        mReturnsProcessingIcon=new BadgeView(ReturnsProcessingActivity.this);
        mReturnsProcessingIcon.setTargetView(findViewById(R.id.ReturnsProcessingIcon));

    }


    private void initListView(){
        leftListview = (ListView) findViewById(R.id.left_listview);
        pinnedListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);

        sectionedAdapter = new MainSectionedAdapter(this, returnsProductsList, rightProductArray);
        pinnedListView.setAdapter(sectionedAdapter);//右侧数据源适配
        adapter = new LeftListAdapter(this, returnsProductsList, flagArray);
        leftListview.setAdapter(adapter);
        leftListview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                isScroll = false;
                for (int i = 0; i < rightProductArray.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);
            }
        });
        pinnedListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

              if ((System.currentTimeMillis() - mExitTime) > 1000) {
                    Product item= rightProductArray[section][position];
                    mReturnsProductPopupWindow = new ReturnsProductPopupWindow(ReturnsProcessingActivity.this,item,productPresenter);
                    mReturnsProductPopupWindow.showPopupWindow(view);
                    backgroundAlpha(0.4f);
                    mReturnsProductPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){
                        @Override
                        public void onDismiss() {
                            backgroundAlpha(1f);
                        }
                    });
                    mExitTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                        // 判断滚动到顶部
                        if (pinnedListView.getFirstVisiblePosition() == 0) {
                            leftListview.setSelection(0);
                        }
                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll && rightProductArray!=null) {
                    for (int i = 0; i < rightProductArray.length; i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == leftListview.getLastVisiblePosition()) {
//                            z = z + 3;
                            leftListview.setSelection(z);
                        }
                        if (x == leftListview.getFirstVisiblePosition()) {
//                            z = z - 1;
                            leftListview.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    public void loadReturnGoods(List<Product> products){

            if(mReturnNote!=null){
                goodsList = mReturnNote.getGoodslist();
                for(ReturnNoteGoods g:goodsList){
                    mReturnNoteGoods=new ReturnNoteGoods();
                    mReturnNoteGoods.setProduct(g.getProduct());
                    mReturnNoteGoods.setPrice(g.getPrice());

                    mReturnNoteGoods.setReturnsVolume(g.getReturnsVolume());
                    mReturnNoteGoods.setGiftsVolume(g.getGiftsVolume());

                    mReturnNoteGoods.setReturnsAmount(g.getReturnsVolume()+g.getGiftsVolume());
                    Product product = getProductFromList(products, g.getProduct().getId());
                    if(product!=null){
                        mReturnNoteGoods.setGiftsVolume(g.getGiftsVolume());
                        mReturnNoteGoods.setReturnsVolume(g.getReturnsVolume());
                        mReturnNoteGoods.setProduct(product);
                    }
                    productPresenter.addGoods(mReturnNoteGoods);
                }
            }
        }


    private Product getProductFromList(List<Product> list, String id){
        for(Product p: list){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
    @Override
    public void doChanged(ReturnCart returnCart) {
        double total_sum = returnCart.getReturn_total_sum();
        mRP_Amount.setText("¥"+ NumberUtils.toDoubleDecimal(total_sum) +"元");
        mReturnsProcessingIcon.setText(""+returnCart.getTotalVolumes());
        if(returnCart.getTotalVolumes()>0){
            mRP_Btn.setVisibility(View.VISIBLE);
        }else{
            mRP_Btn.setVisibility(View.GONE);
        }
        sectionedAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryReturnProduct(List<ProductsTypes> productses) {
        returnsProductsList.addAll(productses);

        int productKindCount = productses.size();
        if (productKindCount > 0) {
            rightProductArray = new Product[productKindCount][];
            flagArray=new boolean[productKindCount];
            for (int i = 0; i < productKindCount; i++) {

                ProductsTypes productType = productses.get(i);
                if (i==0)
                {
                    flagArray[i]=true;
                    carBean = new CarBean(productType.getCarBean().getCarId(),productType.getCarBean().getCarNumber());
                }else
                {
                    flagArray[i]=false;
                }
                rightProductArray[i] =new Product[productType.getProduct_list().size()];
                for (int j = 0; j < productType.getProduct_list().size(); j++) {
                    rightProductArray[i][j] = productType.getProduct_list().get(j);
                }
            }
            initListView();
            sectionedAdapter.notifyDataSetChanged();
        } else {
            showToast("无产品数据");
        }
    }
}
