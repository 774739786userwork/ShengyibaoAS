package com.bangware.shengyibao.manager.shoptype.helper;

import android.util.SparseArray;

import com.bangware.shengyibao.manager.shoptype.view.ChildView;
import com.bangware.shengyibao.manager.shoptype.view.GroupView;

public class ViewHolder {
	
	private volatile SparseArray<GroupView> groupViewMap = new SparseArray<GroupView>();
	
	private volatile SparseArray<SparseArray<ChildView>> childViewMap = new SparseArray<SparseArray<ChildView>>();
	
	public void setGroupView(int groupPosition, GroupView groupView){
		groupViewMap.put(groupPosition, groupView);
	}
	
	public GroupView getGroupView(int groupPosition){
		return groupViewMap.get(groupPosition);
	}
	
	public void setChildView(int groupPosition, int childPosition, ChildView childView){
		SparseArray<ChildView> childViews =  childViewMap.get(groupPosition);
		if(childViews == null){
			childViews = new SparseArray<ChildView>();
			childViewMap.put(groupPosition, childViews);
		}
		childViews.put(childPosition, childView);
	}
	
	public ChildView getChildView(int groupPosition, int childPosition){
		SparseArray<ChildView> childViews =  childViewMap.get(groupPosition);
		if(childViews == null){
			return null;
		}
		return childViews.get(childPosition);
	}
	
	public SparseArray<ChildView> getChildViewMap(int groupPosition){
		return childViewMap.get(groupPosition);
	}
	
	public boolean isGroupChecked(int groupPosition){
		GroupView groupView = getGroupView(groupPosition);
		if(groupView == null){
			return false;
		}
		return groupView.getSelectGroup().isChecked();
	}
	
	public boolean isChildChecked(int groupPosition, int childPosition){
		ChildView childView = getChildView(groupPosition, childPosition);
		if(childView == null){
			return false;
		}
		return childView.getSelectChild().isChecked();
	}
	
	public void setGroupChecked(int groupPosition){
		GroupView groupView = getGroupView(groupPosition);
		if(groupView == null){
			return;
		}
		if(!groupView.getSelectGroup().isChecked()){
			groupView.getSelectGroup().setChecked(true);
		}
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);
		if(childViews == null || childViews.size() <= 0){
			return;
		}
		for(int i=0;i<childViews.size();i++){
			ChildView childView = childViews.get(i);
			if(!childView.getSelectChild().isChecked()){
				childView.getSelectChild().setChecked(true);
			}
		}
	}
	
	public void setGroupUnChecked(int groupPosition){
		GroupView groupView = getGroupView(groupPosition);
		if(groupView == null){
			return;
		}
		/*if(groupView.getSelectGroup().isChecked()){
			groupView.getSelectGroup().setChecked(false);
		}*/
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);
		if(childViews == null || childViews.size() <= 0){
			return;
		}
		for(int i=0;i<childViews.size();i++){
			ChildView childView = childViews.get(i);
			if(childView.getSelectChild().isChecked()){
				childView.getSelectChild().setChecked(false);
			}
		}
	}
	
	public void setChildChecked(int groupPosition, int childPosition){
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);
		if(childViews == null || childViews.size() <= 0){
			return;
		}
		boolean allChildChecked = true;
		for(int i=0;i<childViews.size();i++){
			ChildView childView = childViews.get(i);
			if(childPosition == i){
				if(!childView.getSelectChild().isChecked()){
					childView.getSelectChild().setChecked(true);
				}
			}
			if(!childView.getSelectChild().isChecked()){
				allChildChecked = false;
			}
		}
		if(allChildChecked){
			GroupView groupView = getGroupView(groupPosition);
			/*if(!groupView.getSelectGroup().isChecked()){
				groupView.getSelectGroup().setChecked(true);
			}*/
		}
	}
	
	public void setChildUnChecked(int groupPosition, int childPosition){
		ChildView childView = getChildView(groupPosition, childPosition);
		if(childView == null){
			return;
		}
		if(childView.getSelectChild().isChecked()){
			childView.getSelectChild().setChecked(false);
		}
		GroupView groupView = getGroupView(groupPosition);
		/*if(groupView.getSelectGroup().isChecked()){
			groupView.getSelectGroup().setChecked(false);
		}*/
	}
	
	public void setAllGroupAndChildChecked(){
		if(groupViewMap == null || groupViewMap.size() <= 0){
			return;
		}
		int groupCount = groupViewMap.size();
		for(int i=0;i<groupCount;i++){
			setGroupChecked(i);
		}
	}
	public void setAllGroupAndChildUnChecked(){
		if(groupViewMap == null || groupViewMap.size() <= 0){
			return;
		}
		int groupCount = groupViewMap.size();
		for(int i=0;i<groupCount;i++){
//			setGroupUnChecked(i);
		}
	}
	
	public boolean isAllGroupandChildChecked(){
		if(groupViewMap == null || groupViewMap.size() <= 0){
			return false;
		}
		int groupCount = groupViewMap.size();
		for(int i=0;i<groupCount;i++){
			if(!isGroupChecked(i)){
				return false;
			}
		}
		return true;
	}
}
