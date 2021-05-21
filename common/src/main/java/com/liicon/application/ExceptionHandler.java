package com.liicon.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.http.HttpException;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.liicon.config.BasicConfig;
import com.liicon.config.ConfigKeys;
import com.liicon.manager.FileManager;
import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.app.AppUtils.AppInfo;
import com.liicon.utils.log.LLog;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 * @version  v0.1  king  2012-09-02  异常捕获
 */
class ExceptionHandler extends Exception implements UncaughtExceptionHandler {
	private static final long serialVersionUID = 1L;

	private final static boolean Debug = true;  //是否保存错误日志
	
	/* 定义异常类型 */
	public final static byte TYPE_NETWORK 	= 0x01;
	public final static byte TYPE_SOCKET	= 0x02;
	public final static byte TYPE_HTTP_CODE	= 0x03;
	public final static byte TYPE_HTTP_ERROR= 0x04;
	public final static byte TYPE_XML	 	= 0x05;
	public final static byte TYPE_IO	 	= 0x06;
	public final static byte TYPE_RUN	 	= 0x07;
	public final static byte TYPE_PARSE	 	= 0x08;
	public final static byte TYPE_NULL_POINTER	 	= 0x09;
	
	private byte type;
	private int code;
	
	/** 系统默认的UncaughtException处理类 */
	private UncaughtExceptionHandler mDefaultHandler;
	
	protected ExceptionHandler(){
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}
	
	protected ExceptionHandler(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;		
		if(Debug)
			this.saveErrorLog(excp);
	}
	
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}
	

	public static ExceptionHandler http(int code) {
		return new ExceptionHandler(TYPE_HTTP_CODE, code, null);
	}
	
	public static ExceptionHandler http(Exception e) {
		return new ExceptionHandler(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static ExceptionHandler socket(Exception e) {
		return new ExceptionHandler(TYPE_SOCKET, 0 ,e);
	}
	
	public static ExceptionHandler io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new ExceptionHandler(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof IOException){
			return new ExceptionHandler(TYPE_IO, 0 ,e);
		}
		return run(e);
	}

	public static ExceptionHandler xml(Exception e) {
		return new ExceptionHandler(TYPE_XML, 0, e);
	}

	public static ExceptionHandler parse(Exception e) {
		return new ExceptionHandler(TYPE_PARSE, 0, e);
	}
	
	public static ExceptionHandler network(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new ExceptionHandler(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof HttpException){
			return http(e);
		}
		else if(e instanceof SocketException){
			return socket(e);
		}
		return http(e);
	}
	
	public static ExceptionHandler run(Exception e) {
		return new ExceptionHandler(TYPE_RUN, 0, e);
	}
	public static ExceptionHandler nullpointer(Exception e) {
		return new ExceptionHandler(TYPE_NULL_POINTER, 0, e);
	}
	
	
	/**
	 * 提示友好的错误信息
	 * @param ctx
	 */
	public void makeToast(Context ctx){
		switch(this.getType()){
		case TYPE_HTTP_CODE:
			String err = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_STATUS_CODE, this.getCode());
			ToastUtils.show(ctx, err);
			break;
		case TYPE_HTTP_ERROR:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP));
			break;
		case TYPE_SOCKET:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_SOCKET));
			break;
		case TYPE_NETWORK:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_NETWORK));
			break;
		case TYPE_XML:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_XML_PARSER));
			break;
		case TYPE_IO:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_IO_EXCEPTION));
			break;
		case TYPE_RUN:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_RUNTIME_EXCEPTION));
			break;
		case TYPE_NULL_POINTER:
			ToastUtils.show(ctx, BasicConfig.getString(ConfigKeys.CONF_ERROR_NULLPOINTER_EXCEPTION));
			break;
		}
	}
	
	/**
	 * 保存异常日志
	 * @param excp
	 */
	@SuppressWarnings("deprecation")
	private void saveErrorLog(Exception excp) {
		String errorlog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();		
			if (storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = FileManager.getCacheDirByUsable(BasicAppHelper.get().getPackageName(), "Log");
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			//没有挂载SD卡，无法写文件
			if (logFilePath == "") {
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile,true);
			pw = new PrintWriter(fw);
			pw.println("--------------------"+(new Date().toLocaleString())+"---------------------");	
			excp.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		} finally { 
			if (pw != null) pw.close();
			
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) { }
			}
		}
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}
	
	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}
		LLog.e("error:", ex);
		
		final Activity activity = BasicAppHelper.get().getActivityManager().currentActivity();
		if(activity == null) {
			return false;
		}
		
		return BasicAppHelper.get().handleException(activity, ex);
	}
	/**
	 * 获取APP崩溃异常报告
	 * @param ex
	 * @return
	 */
	protected static String getCrashReport(Throwable ex) {
		AppInfo appInfo = BasicAppHelper.get().getAppInfo();
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: "+appInfo.versionName+"("+appInfo.versionCode+")\n");
		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
		exceptionStr.append("Exception: "+ex.getMessage()+"\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString()+"\n");
		}
		return exceptionStr.toString();
	}


	/**
	 * 获取APP异常崩溃处理对象
	 * @return
	 */
	public static ExceptionHandler getExceptionHandler(){
		return new ExceptionHandler();	
	}
}
