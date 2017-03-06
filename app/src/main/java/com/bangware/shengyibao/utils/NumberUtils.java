package com.bangware.shengyibao.utils;

import java.math.BigDecimal;

public class NumberUtils {

	public static int toInt(String str){
		if((str.matches("\\d+"))){
			return Integer.valueOf(str);
		}
		return 0;
	}

	//保留两位小数
	public static double toDouble(String str){
		try{
			return Double.valueOf(str);
		}catch(NumberFormatException ex){}
		return 0.0;
	}

	//向上取整并保留一位小数
	public static BigDecimal toDoubleDecimal(Double dstr){
		BigDecimal bd = null;
		try{
			bd = new BigDecimal(dstr);
			return  bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		}catch (NumberFormatException ex){

		}
		return bd;
	}

	//向下取整
	public static BigDecimal toDoubleRound(Double roundStr){
		BigDecimal bd = null;
		try{
			bd = new BigDecimal(roundStr);
			return  bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}catch (NumberFormatException ex){

		}
		return bd;
	}
}
