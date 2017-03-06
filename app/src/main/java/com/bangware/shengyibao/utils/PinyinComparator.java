package com.bangware.shengyibao.utils;

import com.bangware.shengyibao.daysaleaccount.model.entity.ChoicePersonBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ChoicePersonBean>{
	//这里主要是用来对ListView里面的数据根据ABCDEFG...来排序

	@Override
	public int compare(ChoicePersonBean o1, ChoicePersonBean o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")){
			return -1;
		}else if(o1.getSortLetters().equals("#")|| o2.getSortLetters().equals("@")){
			return 1;
		}
		else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}
}
