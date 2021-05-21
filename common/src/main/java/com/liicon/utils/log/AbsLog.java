package com.liicon.utils.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import android.text.TextUtils;

/**
 * 打印信息控制
 * <p>
 * Log.DEBUG控制是否打印
 * </p>
 * @version  v0.2  king  2014-11-07  方法整理，简化调用方式；美化打印内容（空/换行/制表符）
 * @version  v0.1  king  2013-09-18  实现日志打印，增加Level枚举类，整理重载方法
 */
class AbsLog {

    /** debug开关. */
    protected static boolean D = true;
	
	/** info开关. */
    protected static boolean I = true;
	
	/** error开关. */
    protected static boolean E = true;

	/**
	 * 设置日志的开关
	 * @param e
	 */
    protected static void setVerbose(boolean d, boolean i, boolean e) {
		D  = d;
		I  = i;
		E  = e;
	}
	
	/**
	 * 打开所有日志，默认全打开
	 * @param d
	 */
    protected static void openAll() {
		D  = true;
		I  = true;
		E  = true;
	}
	
	/**
	 * 关闭所有日志
	 * @param d
	 */
    protected static void closeAll() {
		D  = false;
		I  = false;
		E  = false;
	}
	
	
	protected static void log(Level level, String tag, Object... messages) {
		boolean isLog = false;
		
		// 判断是否打印日志
		switch (level) {
		case INFO:
			if (AbsLog.I) {
				isLog = true;
			}
			break;
			
		case DEBUG:
			if (AbsLog.D) {
				isLog = true;
			}
			break;
			
		case ERROR:
			if (AbsLog.E) {
				isLog = true;
			}
			break;

		default:
			break;
		}
		
		if (isLog) {
			// 打印日志
			String logTag = TextUtils.isEmpty(tag) ? "[null]" : tag;
			String logMessage = "";
			if (messages == null || messages.length == 0) {
				logMessage = "[NULL]";
			} else if (messages.length == 1) {
			    logMessage = toLogString(messages[0]);
			    logMessage = TextUtils.isEmpty(logMessage) ? "[ ]" : logMessage;
			    if (logMessage.equals("\n")) {
                    logMessage = "[\\n]";
                }
			} else {
				// 换行分割符 \u21d3 ╝ ◄ "⏎" "➤" "➜"
				final String lineSplit = " \t ➜ \t ";
				for (Object message : messages) {
				    if (message instanceof Throwable) {
				        logMessage += toLogString(message)+"\n"+lineSplit;
				    } else {
	                    logMessage += toLogString(message)+lineSplit;
                    }
				}
				logMessage = logMessage.substring(0, logMessage.length() - lineSplit.length());
				// 空行替换
				logMessage = logMessage.replace("\n\n", "\n[ ]\n").replace("\n\t\n", "\n[\\t]\n");
				if (logMessage.startsWith("\n")) {
				    logMessage = "[\\n]" + logMessage;
				} else if (logMessage.startsWith("\t\n")) {
                    logMessage = "[\\t][\\n]" + logMessage;
				}
				if (logMessage.endsWith("\n")) {
				    logMessage += "[\\n]";
                } else if (logMessage.endsWith("\n\t")) {
                    logMessage = "[\\n][\\t]" + logMessage;
				}
			}
			
			android.util.Log.println(level.getLevel(), logTag, logMessage);
			android.util.Log.getStackTraceString(new Throwable());
		}
	}
	
	
	/**
	 * 按级别，输出格式化后的消息
	 */
	protected static void format(Level level, String tag, String format, Object... args) {
        log(level, tag, buildMessage(format, args));
    }
	/**
	 * 按级别，输出格式化后的消息+异常信息
	 */
    protected static void format(Level level, String tag, Throwable exception, String format, Object... args) {
        log(level, tag, buildMessage(format, args), exception);
    }
	
	
	/*
	 * 根据Object/Class/String，获取TAG
	 */
	protected static void i(String tag, Object... messages) {
		log(Level.INFO, tag, messages);
	}
	protected static void d(String tag, Object... messages) {
		log(Level.DEBUG, tag, messages);
	}
	protected static void e(String tag, Object... messages) {
		log(Level.ERROR, tag, messages);
	}
	
	
	
	/**
	 * 将Object转换为String【便于输出】
	 */
	protected static String toLogString(Object obj) {
		if (obj == null) {
		    if (obj instanceof Exception) {
		        return "[NULL (Exception)]";
		    } else if (obj instanceof Error) {
	            return "[NULL (Error)]";
		    } else {
		        return "[NULL]";
		    }
		}
		
		if (obj instanceof Throwable) {
			return getStackTraceString((Throwable)obj);
		}
		
		if (obj instanceof byte[]) {
			return new String((byte[]) obj);
		}
		
		if (obj instanceof char[]) {
			return new String((char[]) obj);
		}
		
		return String.valueOf(obj);
	}
    /**
     * Formats the caller's provided message and prepends useful info like calling thread ID and method name.
     */
	protected static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        if (trace.length >= 2) {
            String callingClass = trace[2].getClassName();
            callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
            callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

            caller = callingClass + "." + trace[2].getMethodName();
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }
	protected static String getStackTraceString(Throwable throwable) {
	    if (throwable == null) {
	        return null;
	    }
	    
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        
        // 过滤次要信息
        String strErrorMessage = sw.toString();
        String[] lineErrorMessage = strErrorMessage.split("\n");
        
        StringBuffer buffer = new StringBuffer();
        for (String line : lineErrorMessage) {
            // 忽略的条件
            if (line.contains("java:1)")
                    || line.contains("at com.android.volley.NetworkDispatcher.run(NetworkDispatcher.java:")
                    || line.contains("at com.android.volley.toolbox.BasicNetwork.performRequest(BasicNetwork.java:")
                    || line.contains("com.android.volley.NetworkDispatcher.run(NetworkDispatcher.java:")
                    || line.contains("com.liicon.volley.request.JsonRequest.parseNetworkResponse(JsonRequest.java:")
                    || line.contains("at org.json.JSON.typeMismatch(JSON.java:")
                    || line.contains("at org.json.JSONObject.getJSONObject(JSONObject.java:")
                    || line.contains("at com.android.internal.os.")
                    || line.contains("at android.os.Handler.")
                    || line.contains("at android.view.View.performClick")
                    || line.contains("at android.view.View$PerformClick.run")
                    || line.contains("at android.os.Looper.loop(Looper.java:")
                    || line.contains("at android.app.Activity")
                    || line.contains("at java.lang.reflect.Method.")
                    || line.contains("at dalvik.system.NativeStart.")
                    || line.contains("at android.os.Looper.loop")
                    || line.contains("at android.app.ActivityThread.main")
                    || line.contains("at dalvik.system.NativeStart.")
                    || line.contains("at com.android.internal.os.")
                    || line.contains("at android.app.Instrumentation")
                    || line.contains("at android.app.ActivityThread")
                    || line.contains("at android.test.InstrumentationTestRunner")
                    || line.contains("at android.test.AndroidTestRunner.")
                    || line.contains("at junit.framework.TestCase")
                    || line.contains("at junit.framework.TestResult")
                    || line.contains("at org.apache.http.impl.")
                    || line.contains("at org.apache.http.protocol.HttpRequestExecutor")
                    || line.contains("at org.apache.http.client.")
                    || line.contains("at android.os.StrictMode")
                    || line.contains("at java.net.InetAddress.getAllByNameImpl")
                    || line.contains("at java.net.InetAddress.getAllByName")
                    || line.contains("at android.view.LayoutInflater.inflate")
                    || line.contains("at org.json.JSONObject.getJSONObject(JSONObject.java:")) {
                continue;
            }
            buffer.append(line);
            buffer.append("\n");
        }
        buffer.deleteCharAt(buffer.length()-"\n".length());
        return buffer.toString();
    }
    
}
