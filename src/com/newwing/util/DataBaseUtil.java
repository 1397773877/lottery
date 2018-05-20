package com.newwing.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public class DataBaseUtil {
	/**
	 * 
	 * 功能描述：将数据库的字段转换成对应的属性，例如： user_name ---> userName
	 * 
	 * @param column
	 * @return
	 */
	private static Logger log = Logger.getLogger(DataBaseUtil.class);

	public static String translateColumnIntoProperty(String column) {
		// 字段若为大写形式，则应先转换为小写，方可能实现‘字段’ --> ‘属性’的转换(命名应按照规范)
		String lowerColumn = column.toLowerCase();
		Pattern p = Pattern.compile("[_][a-zA-Z0-9]");
		Matcher m = p.matcher(lowerColumn);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String str = m.group();
			str = str.substring(1, 2).toUpperCase();
			m.appendReplacement(sb, str);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static List resolve(List recordList, Class type)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException, InvocationTargetException,
			NoSuchMethodException {

		long start = System.currentTimeMillis();

		List voList = new ArrayList();
		String className = type.getName();
		Class cla = Class.forName(className);
		for (int i = 0, n = recordList.size(); i < n; i++) {
			Object bean = cla.newInstance();
			Map recordMap = (Map) recordList.get(i);
			Iterator ite = recordMap.entrySet().iterator();
			while (ite.hasNext()) {
				Entry entry = (Entry) ite.next();
				Object columnKey = entry.getKey();
				Object value = entry.getValue();
				String propertyName = translateColumnIntoProperty((String) columnKey);
				// String propertyName =
				// String.valueOf(columnKey).toLowerCase();
				// 校验BEAN的定义
				Class propertyType = PropertyUtils.getPropertyType(bean,propertyName);
				/*
				 * if (propertyType == null) { //
				 * 所定义的BEAN中必须跟查询字段对应的属性一一匹配，否则，抛出异常。 log.warn("请给类：" +
				 * className + "定义名字为：" + propertyName + "的属性"); throw new
				 * BizException("请给类：" + className + "定义名字为：" + propertyName +
				 * "的属性！"); }
				 */

				/*
				 * if (PropertyUtils.isWriteable(bean, propertyName)) {
				 * BeanUtil.copyProperty(bean, propertyName, value); }
				 * PropertyUtils.copyProperties(dest, orig)
				 */
			}
			voList.add(bean);
		}
		long end = System.currentTimeMillis();

		/*
		 * if (log.isInfoEnabled()) {
		 * log.info("自动填充VO性能消耗(s) :result resolver total time = " + (end -
		 * start) + " ms"); }
		 */

		return voList;
	}
}
