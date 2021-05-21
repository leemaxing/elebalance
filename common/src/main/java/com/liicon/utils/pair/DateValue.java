package com.liicon.utils.pair;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 日期值（标准时间，month应该为实际月份）
 * @version v0.1 king 2014-9-19
 */
public class DateValue {
	
	private int year;
	private int month;
	private int day;
	


	public DateValue() {
		super();
	}
	public DateValue(Calendar calendar) {
		super();
		if (calendar != null) {
			this.year = calendar.get(Calendar.YEAR);
			this.month = calendar.get(Calendar.MONTH) + 1;
			this.day = calendar.get(Calendar.DAY_OF_MONTH);
		}
	}
	public DateValue(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public long getTimeInMillis() {
		GregorianCalendar cal = new GregorianCalendar(year, month-1, day+1);
		return cal.getTimeInMillis();
	}

	
	/**
	 * 比较
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o instanceof DateValue) {
			DateValue dateValue = (DateValue) o;
			if (dateValue.year == year
					&& dateValue.month == month
					&& dateValue.day == day) {
				return true;
			}
			return false;
		}
		return super.equals(o);
	}
	
	
	
	/*
	 * getter\setter
	 */
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
}
