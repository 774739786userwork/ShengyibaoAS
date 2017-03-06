package com.bangware.shengyibao.manager.exlistdemo.data;

public class GroupData {
   public String id;
   public String name;
   public int type;//类型 1 文字 2图片
   public GroupData(String id,String name,int type)
   {
	   this.id = id;
	   this.name = name;
	   this.type = type;
   }
}
