package com.bangware.shengyibao.salesamount.view;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.DoubleDatePickerDialog;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.salesamount.adapter.MyBaseExpandableListAdapter;
import com.bangware.shengyibao.salesamount.model.entity.ChildItem;
import com.bangware.shengyibao.salesamount.model.entity.GroupItem;
import com.bangware.shengyibao.salesamount.presenter.GroupRankingPresenter;
import com.bangware.shengyibao.salesamount.presenter.impl.GroupRankingPresneterImpl;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组排名查询
 */
public class GroupRankingActivity extends BaseActivity implements GroupRankingView{
    private ImageView backimage;
    private ExpandableListView expandlist;
    private List<GroupItem> groupDataList = new ArrayList<GroupItem>();
    private Map<Integer, List<ChildItem>> childData = new HashMap<Integer, List<ChildItem>>();//child的数据源
    private MyBaseExpandableListAdapter myAdapter;

    private LinearLayout timetopLinearLayout;
    private TextView group_start_date,group_end_date;
    private String begin_date;
    private String end_date;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    private String year=String.valueOf(c.get(Calendar.YEAR));
    private String month=String.valueOf(c.get(Calendar.MONTH));

    private GroupRankingPresenter groupRankingPresenter = null;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_ranking);

        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);
        c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMinimum(Calendar.DAY_OF_MONTH));
        begin_date=sdf.format(c.getTime());
        c.set(Integer.parseInt(year), Integer.parseInt(month), c.getActualMaximum(Calendar.DAY_OF_MONTH));
        end_date = sdf.format(c.getTime());

        findView();
        setListener();
    }


    private void findView(){
        backimage = (ImageView) findViewById(R.id.group_backImg);
        expandlist = (ExpandableListView) findViewById(R.id.expandlist);
        timetopLinearLayout = (LinearLayout) findViewById(R.id.timetopLinearLayout);
        group_start_date = (TextView) findViewById(R.id.group_start_date);
        group_end_date = (TextView) findViewById(R.id.group_end_date);

        groupRankingPresenter = new GroupRankingPresneterImpl(this);
        groupRankingPresenter.loadGroupRankingData(user,begin_date,end_date);

        group_start_date.setText(begin_date);
        group_end_date.setText(end_date);

        myAdapter = new MyBaseExpandableListAdapter(this, groupDataList, childData);
        expandlist.setGroupIndicator(null);//这里不显示系统默认的group indicator
        expandlist.setAdapter(myAdapter);
        registerForContextMenu(expandlist);//给ExpandListView添加上下文菜单
    }

    private void setListener(){
        MyOnClickListener listener = new MyOnClickListener();
        backimage.setOnClickListener(listener);
        timetopLinearLayout.setOnClickListener(listener);
    }

    /**
     * group和child子项的数据请求
     * @param groupItemList
     */
    @Override
    public void doGroupRankingLoadSuccess(List<GroupItem> groupItemList) {
        int childtempCount = groupItemList.size();
        if (childtempCount>0){
            groupDataList.addAll(groupItemList);
            myAdapter.refresh();
            for (int i=0;i<groupDataList.size();i++){
                expandlist.collapseGroup(i);
                List<ChildItem> childItems = new ArrayList<ChildItem>();
                GroupItem groupList = groupItemList.get(i);
                for (int j=0;j<groupList.getChildItemList().size();j++){
                    ChildItem child_data = new ChildItem();
                    child_data.setGroupPerson(groupList.getChildItemList().get(j).getGroupPerson());
                    child_data.setGroupRanking(groupList.getChildItemList().get(j).getGroupRanking());
                    child_data.setGroupQuantity(groupList.getChildItemList().get(j).getGroupQuantity());
                    child_data.setGroupTotalSum(groupList.getChildItemList().get(j).getGroupTotalSum());
                    childItems.add(child_data);
                    childData.put(i,childItems);
                }
//                expandlist.expandGroup(i);//默认展开列表
            }
        }else{
            showToast("无数据");
            myAdapter.refresh();
        }
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.group_backImg){
                finish();
            }
            if (view.getId() == R.id.timetopLinearLayout){
                new DoubleDatePickerDialog(GroupRankingActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {

                        begin_date = String.format("%d-%d-%d", startYear,startMonthOfYear + 1,1);
                        end_date = String.format("%d-%d-%d", endYear, endMonthOfYear + 1,endDayOfMonth);
                        group_start_date.setText(begin_date);
                        group_end_date.setText(end_date);

                        groupDataList.clear();
                        groupRankingPresenter.loadGroupRankingData(user,begin_date,end_date);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
            }
        }
    }
}
