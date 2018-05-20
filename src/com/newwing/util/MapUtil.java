package com.newwing.util;
/**   
  * @Title: 地图工具类 
  * @author ljn   
  * @date 2016-3-16 
  */
public class MapUtil {
	
	
	/**   
	  * @Title: 是否存在范围
	  * @Description:  
	  * @author ljn   
	  * @date 2016-3-16 
	  * @return
	  */
	public static boolean isInRange(double n1, double e1, double n2, double e2,double range){
		double distance = Distance(n1, e1, n2, e2);
		if(distance>range){
			return false;
		}
		return true;
	}
	
	/**   
	  * @Title:计算范围
	  * @Description:  
	  * @author ljn   
	  * @date 2016-3-16 
	  * @return
	  */
	public static double Distance(double n1, double e1, double n2, double e2)
    {
        double jl_jd = 102834.74258026089786013677476285;
        double jl_wd = 111712.69150641055729984301412873;
        double b = Math.abs((e1 - e2) * jl_jd);
        double a = Math.abs((n1 - n2) * jl_wd);
        return Math.sqrt((a * a + b * b));

    }
	public static void main(String[] args) {
		System.out.println(Distance(24.490673,118.18212, 24.4908206, 118.182311));
	}
}
