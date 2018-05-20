package com.newwing.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
public class DateUtil {
	
	public final static String FORMAT_HH_MM = "HH:mm"; // 指定转换的格式(时:分)
	
	/**
	 * 将Date类型的日期转换为对应的周几数 -----> 2016-03-15 对应周二
	 * (1 - 周七, 2 - 周一, 3 - 周二, ·····, 7 - 周六)
	 */
	public static Integer dateToWeekDay(Date date) {
		
		Calendar calendar = Calendar.getInstance(); 
		if (null != date) {
			calendar.setTime(date);
		}
		Integer weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		return weekDay;
		
	}
	
	/**
	 * 将Date类型的日期转换为指定格式的字符串 -----> 2016-04-10 14:30:00 -----> 1430
	 */
	public static String dateToStr(Date date) {
		
		if (null != date) {
			SimpleDateFormat format = new SimpleDateFormat("HHmm");
			String str = format.format(date);
			return str;
		}
		return null;
		
	}
	
	/**
	 * 两日期相减, 得到相差的分钟数
	 */
	public static Integer dateSub(Date startTime, Date endTime) {
		
		Long subTime = endTime.getTime() - startTime.getTime();
		Long subMinutes = subTime / (1000 * 60); // 得到的是相差的毫秒数, 转为分钟数
		return subMinutes.intValue();
		
	}
	
	/**
	 * 将Date类型的"年 - 月 - 日"和字符串类型的"时:分钟:秒"拼接在一起, 精确到"年-月-日 时:分钟:秒"返回
	 * @param date         年-月-日 时:分钟:秒
	 * @param splicingTime 时:分钟:秒
	 * @return             年-月-日 时:分钟:秒
	 */
	public static Date splicingDayWithTime(Date date, String splicingTime) throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String ocDay = format.format(date);           // Sun Mar 13 00:00:00 CST 2016 -----> 2016-03-13
		StringBuilder stringBuilder = new StringBuilder();
		String preciseTime = stringBuilder.append(ocDay).append(" ").append(splicingTime).toString();
		
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		Date splicingDay = format.parse(preciseTime); // 2016-03-13 15:52 -----> Sun Mar 13 15:52:00 CST 2016
		return splicingDay;
		
	}
	
	/**
	 * 对Date类型的"年-月-日 时:分钟:秒"与整型的"分钟数"进行相加减(Sun Mar 13 15:52:00 CST 2016 + 60 = Sun Mar 13 16:22:00 CST 2016)
	 * @param date         年-月-日 时:分钟:秒
	 * @param splicingTime 分钟数
	 * @return             年-月-日 时:分钟:秒
	 */
	public static Date addSplicingDayCalculate(Date date, Integer splicingMinutes) throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, splicingMinutes); // 日期 + splicingMinutes 分钟
		Date resultDate = calendar.getTime();
		return resultDate;
		
	}
	
	/**
	 * 对Date类型的"年-月-日 时:分钟:秒"与整型的"分钟数"进行相加减(Sun Mar 13 15:52:00 CST 2016 - 60 = Sun Mar 13 14:22:00 CST 2016)
	 * @param date         年-月-日 时:分钟:秒
	 * @param splicingTime 分钟数
	 * @return             年-月-日 时:分钟:秒
	 */
	public static Date subSplicingDayCalculate(Date date, Integer splicingMinutes) throws Exception {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("-").append(splicingMinutes);
		return DateUtil.addSplicingDayCalculate(date, Integer.parseInt(stringBuilder.toString()));
		
	}
	
	/**
	 * 字符串转换为指定格式的日期
	 */
	public static Date strDateToDateWithFormat(String strDate, String format) throws Exception {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date parseDate = sdf.parse(strDate);
			return parseDate;
			
		} catch (Exception e) {
			throw new RuntimeException("Your format is error! It must be yyyy-MM-dd");
		}
		
		
	}
	
	/**
	 * 字符串转换为日期
	 */
	public static Date strDateToDate(String strDate) throws Exception {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(strDate);
			return date;
			
		} catch (Exception e) {
			throw new RuntimeException("Your date format is false! It must be yyyy-MM-dd");
		}
		
	}
	
	/**
	 * 日期转换为字符串
	 */
	public static String dateToStrDate(Date date) throws Exception {
		String strDate = "";
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				strDate = sdf.format(date);
			}
		} catch (Exception e) {
			strDate = "";
		}
		return strDate;
	}
	
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final static SimpleDateFormat strTime = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}
	
	//获得yyyyMMddHHmmss
	public static String getStrTime() {
		return strTime.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static Date getAfterDayDate(Date fromDate, Integer diff) {
//    	int daysInt = Integer.parseInt(days);
    	
    	Calendar canlendar = Calendar.getInstance(); // java.util包
    	canlendar.setTime(fromDate);
    	canlendar.add(Calendar.DATE, diff); // 日期减 如果不够减会将月变动
    	Date date = canlendar.getTime();
    	
//    	SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	String dateStr = sdfd.format(date);
    	
    	return date;
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }
    
	public static List<Date> getOneWeekBetween(Date time) {
		Date firstDayOfWeek;
		Date lastDayOfWeek;
		List<Date> list = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			list.add(time);
			cal.setTime(time);
			cal.set(Calendar.HOUR_OF_DAY, 0);  
			cal.set(Calendar.MINUTE, 0);  
			cal.set(Calendar.SECOND, 0);  
			cal.add(Calendar.DATE, 6);
			cal.set(Calendar.HOUR_OF_DAY, 23);  
			cal.set(Calendar.MINUTE, 59);  
			cal.set(Calendar.SECOND, 59); 
			list.add(cal.getTime());
		}else{
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
			int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
			cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
			cal.set(Calendar.HOUR_OF_DAY, 0);  
			cal.set(Calendar.MINUTE, 0);  
			cal.set(Calendar.SECOND, 0);  
			list.add(cal.getTime());
			cal.add(Calendar.DATE, 6);
			cal.set(Calendar.HOUR_OF_DAY, 23);  
			cal.set(Calendar.MINUTE, 59);  
			cal.set(Calendar.SECOND, 59); 
			list.add(cal.getTime());
		}
		for (Date date : list) {

		}
		return list;

	}
    /**
     * 获取两个时间中间差异的分钟数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int  minutesDifference(Date startDate, Date endDate){
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        return Integer.valueOf(String.valueOf(elapsedMinutes));
 
    }
    
    

	 
	    public static void main(String[] args) {
	 

	      SimpleDateFormat simpleDateFormat = 
	                new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
	 
	      try {
	 
	        Date date1 = simpleDateFormat.parse("10/10/2016 10:30:00");
	        Date date2 = simpleDateFormat.parse("10/10/2016 10:45:10");
	 
	        System.out.println(minutesDifference(date1, date2));
	 
	      } catch (ParseException e) {
	        e.printStackTrace();
	      }
	 
	    }
	 


}
