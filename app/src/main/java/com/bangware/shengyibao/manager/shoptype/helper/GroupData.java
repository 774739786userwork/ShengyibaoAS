package com.bangware.shengyibao.manager.shoptype.helper;

import java.util.ArrayList;
import java.util.List;


public class GroupData {
	private int id;
	private String groupName;
	
	private boolean groupSelected;
	
	private List<ChildData> items = new ArrayList<ChildData>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isGroupSelected() {
		return groupSelected;
	}

	public void setGroupSelected(boolean groupSelected) {
		this.groupSelected = groupSelected;
	}

	public List<ChildData> getItems() {
		return items;
	}

	public void setItems(List<ChildData> items) {
		this.items = items;
	}
	public void addItems(ChildData childData){
		this.items.add(childData);
	}
	
}
