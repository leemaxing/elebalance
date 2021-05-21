package com.liicon.utils.log;

import com.liicon.utils.stack.StackTraceInfo;


/**
 * 日志打印工具类
 * @version  v0.1  king  2014-11-07  自动生成TAG信息
 */
public class LLog extends AbsLog {
    
    /**
     * 设置自定义Log
     */
    public static ILog customLog;
	
	/**
	 * 自动生成TAG
	 * @return
	 */
	protected static String getTag(int index) {
		StackTraceInfo info = StackTraceInfo.getStackTraceInfo(index);
		if (info == null) {
			return "[null]";
		}
		
		String tag = "[%s]%s.%s(L:%d)";
        String classSimpleName = info.getClassSimpleName();
        return String.format(tag, getThreadName(), classSimpleName, info.getMethodName(), info.getLineNumber());
        
//		return info.getClassSimpleName() + "."
//			+ info.getMethodName() + "()"
//			+ "["+info.getClassName()+":"+info.getLineNumber()+"]";
	}
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
	
	
	
	/*
	 * 自动生成TAG
	 */
	public static void i(Object... messages) {
        if (customLog != null) {
            customLog.i(messages);
        } else {
            i(getTag(3), messages);
        }
	}
	public static void d(Object... messages) {
        if (customLog != null) {
            customLog.d(messages);
        } else {
            d(getTag(3), messages);
        }
	}
	public static void e(Object... messages) {
        if (customLog != null) {
            customLog.e(messages);
        } else {
            e(getTag(3), messages);
        }
	}
	

    /**
     * 按级别，输出格式化后的消息
     */
	public static void format(Level level, String format, Object... args) {
	    if (customLog != null) {
	        customLog.format(level, format, args);
	    } else {
	        log(level, getTag(3), buildMessage(format, args));
        }
    }
    /**
     * 按级别，输出格式化后的消息+异常信息
     */
	public static void format(Level level, Throwable exception, String format, Object... args) {
        if (customLog != null) {
            customLog.format(level, exception, format, args);
        } else {
            log(level, getTag(3), buildMessage(format, args), exception);
        }
    }
	
	public static void log(Level level, Object... messages) {
        if (customLog != null) {
            customLog.log(level, messages);
        } else {
            log(level, getTag(4), messages);
        }
    }
	
}
