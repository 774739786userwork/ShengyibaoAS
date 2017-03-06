package com.bangware.shengyibao.salesamount.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 组内排名父容器的实体
 * Created by bangware on 2016/8/24.
 */
public class GroupItem {
    private String title;
    private int imageId;

    List<ChildItem> childItemList = new ArrayList<ChildItem>();

    public GroupItem(String title, int imageId)
    {
        this.title = title;
        this.imageId = imageId;
    }

    public GroupItem() {
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public List<ChildItem> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        this.childItemList = childItemList;
    }

    public void addChildItemList(ChildItem childItem){
        this.childItemList.add(childItem);
    }
}
