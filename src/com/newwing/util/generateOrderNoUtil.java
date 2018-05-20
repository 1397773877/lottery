package com.newwing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成订单编号工具类
 * @author zhang_yu
 *
 */
public class generateOrderNoUtil {

	private static long orderNum = 0l;  
    private static String date ;  
	
	public static synchronized String getOrderNo() {  
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());  
        if(date==null||!date.equals(str)){  
            date = str;  
            orderNum  = 0l;  
        }  
        orderNum ++;  
        long orderNo = Long.parseLong((date)) * 100000;  
        orderNo += orderNum;;  
        return orderNo+"";  
    }  

//	public static void main(String[] args) {
//		
//		for (int i = 0; i <= 2000; i++) {
//			System.out.println(generateOrderNoUtil.getOrderNo());
//		}
//		
//		
//	}
	
}
