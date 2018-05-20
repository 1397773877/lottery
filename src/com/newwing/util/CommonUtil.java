package com.newwing.util;

import java.util.List;

/**   
  * @Title: 公共类 
  * @author ljn   
  * @date 2016-3-13 
  */
public class CommonUtil {
	/**   
	  * @Title: list 是否为空
	  * @Description:  
	  * @author ljn   
	  * @date 2016-3-13 
	  * @return
	  */
	public static boolean isNotEmptyList(List list){
		if(null!=list&&list.size()>0){
			return true;
		}
		return false;
	}
	/**   
	 * @Title: Object 是否为空
	 * @Description:  
	 * @author ljn   
	 * @date 2016-3-13 
	 * @return
	 */
	public static boolean isNotEmptyObject(Object obj){
		if(null!=obj&&!"".equals(obj)){
			return true;
		}
		return false;
	}
	
/*	*//**   
	  * @Title: 设置属性
	  * @Description:  
	  * @author ljn   
	  * @date 2016-3-13 
	  * @return
	  *//*
	public static void setBaseField(Object obj){
		Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		  try {
	            for (int j = 0; j < field.length; j++) { // 遍历所有属性
	                String name = field[j].getName(); // 获取属性的名字
	                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
	                String type = field[j].getGenericType().toString(); // 获取属性的类型
	            	 if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
	                    Method m = obj.getClass().getMethod("get" + name);
	                    String value = (String) m.invoke(obj); // 调用getter方法获取属性值
	                    if (value == null) {
	                        m = obj.getClass().getMethod("set"+name,String.class);
	                       if(name.equals("Id")){
	                    	  m.invoke(obj, ShortUUID.genId());
	                       }
	                       if(name.equals("Enabled")){
	                    	  m.invoke(obj, CommonConstant.ENABLED_YES);
	                       }
	                    }
	                }
                	if (type.equals("class java.util.Date")) {
                		Method m = obj.getClass().getMethod("get" + name);
                		Date value = (Date) m.invoke(obj);
                		if (value == null) {
                			m = obj.getClass().getMethod("set"+name,Date.class);
                			if(name.equals("CreateDate")||name.equals("ModifyDate")){
                				m.invoke(obj, new Date());
                			}
                		}
                	}
                	if (type.equals("class com.newwing.entity.ClubInfo")) {
                		Method m = obj.getClass().getMethod("get" + name);
                		ClubInfo value = (ClubInfo) m.invoke(obj);
                		if (value == null) {
                			m = obj.getClass().getMethod("set"+name,ClubInfo.class);
                			if(name.equals("ClubInfo")){
                				m.invoke(obj, SysConstant.getCurrentClubInfo());
                			}
                		}
                	}
                	if (type.equals("class com.newwing.entity.system.User")) {
                		Method m = obj.getClass().getMethod("get" + name);
                		User value = (User) m.invoke(obj);
                		if (value == null) {
                			m = obj.getClass().getMethod("set"+name,User.class);
                			if(name.equals("User")){
                				m.invoke(obj, SysConstant.getCurrentUser());
                			}
                		}
                	}
	                
	                if (type.equals("class java.lang.Integer")) {
	                    Method m = obj.getClass().getMethod("get" + name);
	                    Integer value = (Integer) m.invoke(obj);
	                    if (value == null) {
	                        m = obj.getClass().getMethod("set"+name,Integer.class);
	                        m.invoke(obj, 0);
	                    }
	                }
	                if (type.equals("class java.lang.Boolean")) {
	                    Method m = obj.getClass().getMethod("get" + name);
	                    Boolean value = (Boolean) m.invoke(obj);
	                    if (value == null) {
	                        m = obj.getClass().getMethod("set"+name,Boolean.class);
	                        m.invoke(obj, false);
	                    }
	                }
	             // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}*/
}
