package com.newwing.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 法定节假日获取
 * API Key = 1b40f80dae8cf2336ab08709be11af80
 * @author zhang_yu
 *
 */
public class LegalHolidaysUtil {

	/**
	 * @param urlAll :请求接口
	 * @param httpArg : 参数
	 * @return 返回结果(0 - 工作日; 1 - 休息日; 2 - 节假日)
	 */
	public static String request(String httpUrl, String httpArg) {
		
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	    	
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
//	        connection.setRequestProperty("apikey", "1b40f80dae8cf2336ab08709be11af80");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	    
	}

//	public static void main(String[] args) {
//		
//		String httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
//		String httpUrl = "http://www.easybots.cn/api/holiday.php";
//		String httpArg = "d=20160313";
//		String jsonResult = request(httpUrl, httpArg);
//		System.out.println(jsonResult);
//		
//	}
	
	
}
