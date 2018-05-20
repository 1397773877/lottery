package com.newwing.util;

import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import sun.misc.BASE64Decoder;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	
	public final static String EMPTY = "";

	/**
	 * 空字符串和NULL值的判断
	 * 
	 * @param str
	 *            ：输入的字符串值
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())
				|| "null".equalsIgnoreCase(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 非空字符串的判断
	 * 
	 * @param str
	 *            ：输入的字符串值
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * 将NULL值转换为空字符串
	 * 
	 * @param str
	 *            ：输入的字符串值
	 * @return
	 */
	public static String convertNull(String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}
		if ("null".equalsIgnoreCase(str))
			return "";
		return str;
	}

	public static String arr2string(int[] obj) {
		StringBuffer sb = new StringBuffer();
		String ret = "";
		if (obj instanceof int[]) {
			for (int i : obj) {
				sb.append(i).append(",");
			}
			ret = StringUtil.isEmpty(sb.toString()) ? "" : sb.toString()
					.substring(0, sb.toString().length() - 1);
		} else {
			new RuntimeException("整型数组转换成 字符串错误");
		}
		return ret;
	}

	public static String arr2string(String[] obj) {
		StringBuffer sb = new StringBuffer();
		String ret = "";
		if (obj instanceof String[]) {
			for (String a : obj) {
				sb.append("'").append(a).append("',");
			}
			ret = StringUtil.isEmpty(sb.toString()) ? "" : sb.toString()
					.substring(0, sb.toString().length() - 1);
		} else {
			new RuntimeException("字符串数组转换成 字符串错误");
		}
		return ret;
	}

	public static String arr2string(String[] obj, String split) {
		StringBuffer sb = new StringBuffer();
		String ret = "";
		if (obj instanceof String[]) {
			for (String a : obj) {
				sb.append(split).append(a).append(split).append(",");
			}
			ret = StringUtil.isEmpty(sb.toString()) ? "" : sb.toString()
					.substring(0, sb.toString().length() - 1);
		} else {
			new RuntimeException("字符串数组转换成 字符串错误");
		}
		return ret;
	}

	public static String arr2string(Object[] obj) {
		return StringUtil.arr2string(obj, "'");
	}

	public static String arr2string(Object[] obj, String split) {
		StringBuffer sb = new StringBuffer();
		String ret = "";
		if (obj instanceof Object[]) {
			for (Object o : obj) {
				if (o instanceof String) {
					String a = (String) o;
					sb.append(split).append(a).append(split).append(",");
				} else
					sb.append(split).append(String.valueOf(o)).append(split)
							.append(",");
			}
			ret = StringUtil.isEmpty(sb.toString()) ? "" : sb.toString()
					.substring(0, sb.toString().length() - 1);
		} else {
			new RuntimeException("字符串数组转换成 字符串错误");
		}
		return ret;
	}

	public static boolean equal(String a, String b) {
		if (a == null)
			return false;
		if (b == null)
			return false;
		if (a.equals(b)) {
			return true;
		}
		return false;

	}

	/**
	 * 移除重复的空格
	 * 
	 * @param s
	 * @return
	 */
	public static String removeMultiSpace(String s) {
		return s.replaceAll("\\s+", " ");
	}

	/**
	 * 将字符串首字母变大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFristLetter(String s) {
		if (s != null && s.length() > 1) {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return null;
	}

	/**
	 * 替换首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String upperFirst(String s) {
		return s.replaceFirst(s.substring(0, 1), s.substring(0, 1)
				.toUpperCase());
	}

	/**
	 * 每length长度字符串换行输出
	 * 
	 * @param s
	 * @param length
	 * @return
	 * @see
	 */
	public static String newLineOutput(String s, int length) {
		if (s == null || s.length() == 0 || length == 0)
			return s;
		int size = s.length() / length;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < size - 1; i++) {
			result.append(s.substring(length * i, length * (i + 1))).append(
					"<br/>");
		}
		result.append(s.substring(length * (size - 1), s.length()));
		return result.toString();
	}

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static String getUuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static String getEmptyIfNull(String str) {
		String tmp = "";
		if (isNotEmpty(str)) {
			return str;
		}
		return tmp;
	}

	/**
	 * 生成32位UUID
	 * 
	 * @return
	 */
	/*
	 * public String createUUID32Code() { return new
	 * String(Hex.encodeHex(UUIDHexGenerator.randomUUID().getRawBytes())); }
	 */
	public static void main(String[] args) {
		System.out.println(StringUtil.getStrNumFromSequence(2101232110));
	}

	/**
	 * 讲字符串进行 BASE64 编码
	 * @param s
	 * @return
	 */
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}
	
	
	public static String getStrNumFromSequence(Integer sequence){
		String no=String.format("%010d",sequence);
		return no;
	}

	/**
	 * 将BASE64 编码的字符串 s进行解码
	 * @param s
	 * @return
	 */
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	   * 设置下载文件中文件的名称
	   * 
	   * @param filename
	   * @param request
	   * @return
	   */
	  public static String encodeFilename(String filename, HttpServletRequest request) {
	    /**
	     * 获取客户端浏览器和操作系统信息
	     * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
	     * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
	     */
	    String agent = request.getHeader("USER-AGENT");
	    try {
	      if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
	        String newFileName = URLEncoder.encode(filename, "UTF-8");
	        newFileName = StringUtil.replace(newFileName, "+", "%20");
	        newFileName = newFileName.replace("+", "%20");
	        if (newFileName.length() > 150) {
	          newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
	          newFileName = StringUtil.replace(newFileName, " ", "%20");
	        }
	        return newFileName;
	      }
	      if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
	        return MimeUtility.encodeText(filename, "UTF-8", "B");

	      return filename;
	    } catch (Exception ex) {
	      return filename;
	    }
	  }
	  
	  public static String replace(String s,String target,String replacement){
		  return s.replace(target, replacement);
	  }

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	

}
