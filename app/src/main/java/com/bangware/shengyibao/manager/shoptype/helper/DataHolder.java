package com.bangware.shengyibao.manager.shoptype.helper;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
	
	private volatile List<GroupData> contentData;

	public List<GroupData> getContentData() {
		return contentData;
	}

	public void setContentData(List<GroupData> contentData) {
		this.contentData = contentData;
	}
	
	public GroupData getGroupData(int groupPosition){
		if(contentData == null){
			return null;
		}
		return contentData.get(groupPosition);
	}
	
	public int getGroupCount(){
		if(contentData == null){
			return 0;
		}
		return contentData.size();
	}
	
	public ChildData getChildData(int groupPosition, int childPosition){
		
		List<ChildData> childDataList = getChildDataList(groupPosition);
		if(childDataList == null){
			return null;
		}
		return childDataList.get(childPosition);
	}
	
	public List<ChildData> getChildDataList(int groupPosition){
		GroupData groupData  = getGroupData(groupPosition);
		if(groupData == null){
			return null;
		}
		return groupData.getItems();
	}
	
	public int getChildCount(int groupPosition){
		List<ChildData> childDataList = getChildDataList(groupPosition);
		if(childDataList == null){
			return 0;
		}
		return childDataList.size();
	}

	public void setGroupChecked(int groupPosition){
		GroupData groupData = contentData.get(groupPosition);
		if(groupData == null){
			return;
		}
		groupData.setGroupSelected(true);
		List<ChildData> childDataList = groupData.getItems();
		if(childDataList != null){
			for(ChildData childData : childDataList){
				childData.setChildSelected(true);
			}
		}
	}
	public void setGroupUnChecked(int groupPosition){
		GroupData groupData = contentData.get(groupPosition);
		if(groupData == null){
			return;
		}
		groupData.setGroupSelected(false);
		List<ChildData> childDataList = groupData.getItems();
		if(childDataList != null){
			for(ChildData childData : childDataList){
				childData.setChildSelected(false);
			}
		}
		
	}
	public void setChildChecked(int groupPosition, int childPosition){
		GroupData groupData = contentData.get(groupPosition);
		if(groupData == null){
			return;
		}
		List<ChildData> childDataList = groupData.getItems();
		if(childDataList == null){
			return;
		}
		boolean allChecked = true;
		for(int i=0,j=childDataList.size(); i<j; i++){
			ChildData childData = childDataList.get(i);
			if(i == childPosition){
				childData.setChildSelected(true);
			}
			if(!childData.isChildSelected()){
				allChecked = false;
			}
		}
		if(allChecked){
			groupData.setGroupSelected(true);
		}
	}
	
	public void setChildUnChecked(int groupPosition, int childPosition){
		GroupData groupData = contentData.get(groupPosition);
		if(groupData == null){
			return;
		}
		groupData.setGroupSelected(false);
		List<ChildData> childDataList = groupData.getItems();
		if(childDataList == null){
			return;
		}
		ChildData childData = childDataList.get(childPosition);
		childData.setChildSelected(false);

	}
	
	public boolean isGroupSelected(int groupPosition){
		GroupData groupData = contentData.get(groupPosition);
		if(groupData == null){
			return false;
		}
		return groupData.isGroupSelected();
	}
	
	public boolean isChildSelected(int groupPosition, int childPosition){
		ChildData childData = getChildData(groupPosition, childPosition);
		if(childData == null){
			return false;
		}
		return childData.isChildSelected();
	}
	
	public boolean isAllChildSelected(int groupPosition){
		List<ChildData> childDataList = getChildDataList(groupPosition);
		if(childDataList == null){
			return false;
		}
		for(ChildData childData : childDataList){
			if(childData == null){
				return false;
			}
			if(!childData.isChildSelected()){
				return false;
			}
		}
		return true;
	}

	public void setAllGroupAndChildChecked(){
		int groupCount = getGroupCount();
		for(int i=0;i<groupCount;i++){
			setGroupChecked(i);
		}
	}
	
	public void setAllGroupAndChildUnChecked(){
		int groupCount = getGroupCount();
		for(int i=0;i<groupCount;i++){
			setGroupUnChecked(i);
		}
	}

	public List<GroupData> getCheckedDataList(){
		if(contentData == null){
			return null;
		}
		List<GroupData> checkedGroupList = new ArrayList<GroupData>();
		for(GroupData shopData : contentData){
			List<ChildData> items = shopData.getItems();
			if(items == null){
				continue;
			}
			List<ChildData> checkedChildList = null;
			for(ChildData item : items){
				if(item.isChildSelected()){
					if(checkedChildList == null){
						checkedChildList = new ArrayList<ChildData>();
					}
					checkedChildList.add(item);
				}
			}
			if(checkedChildList != null){
				GroupData checkedShopData = new GroupData();
				checkedShopData.setItems(checkedChildList);
				checkedShopData.setGroupName(shopData.getGroupName());
				checkedShopData.setGroupSelected(shopData.isGroupSelected());
				checkedGroupList.add(checkedShopData);
			}
		}
		return checkedGroupList;
	}
	
}
