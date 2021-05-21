package com.liicon.volley.error;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.liicon.config.BasicConfig;
import com.liicon.config.ConfigKeys;
import com.liicon.json.JsonObject;
import com.liicon.utils.log.LLog;

/**
 * VolleyError处理帮助类
 * <p>
 * 请先初始化异常信息描述
 * </p>
 * @version  v0.1  king  2014-03-07  根据异常类型，获取可视化异常信息
 */
public class VolleyErrorHelper {
    
    /** 请求超时 */
    private static String ex_http_timeout = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_TIMEOUT);
    /** 网络异常 */
    private static String ex_http_internet = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_NETWORK);
    /** 服务器异常 */
    private static String ex_http_serverdown = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_SERVERDOWN);
    /** 服务器异常 */
    private static String ex_http_404 = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_404);
    /** JSON数据解析异常 */
    private static String ex_parse_json = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_PARSEJSON);
    /** 空指针异常 */
    private static String ex_null = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_NULLPOINT);
    /** 未知异常 */
    private static String ex_http_unknown = BasicConfig.getString(ConfigKeys.CONF_ERROR_HTTP_UNKNOWN);
    
    
    
	/**
	 * 根据指定的异常对象，返回相应的提示信息
	 */
	public static String getMessage(VolleyError error, Context context) {
		if (error instanceof TimeoutError) {
			return ex_http_timeout;
		} else if (isServerProblem(error)) {
			return handleServerError(error, context);
		} else if (isNetworkProblem(error)) {
			return ex_http_internet;
		} else if (error instanceof JsonParseError) {
		    return ex_parse_json;
		} else if (error instanceof NullError) {
		    return ex_null;
		}
		return ex_http_unknown;
	}
	
	/**
	 * 判断是否为请求超时
	 */
	public static boolean isTimeoutError(VolleyError error) {
		return error instanceof TimeoutError;
	}
	/**
	 * 判断是否为服务器异常
	 */
	public static boolean isServerProblem(VolleyError error) {
		return (error instanceof ServerError) || (error instanceof AuthFailureError);
	}
	/**
	 * 判断是否为网络异常
	 */
	public static boolean isNetworkProblem(VolleyError error) {
		return (error instanceof NetworkError) || (error instanceof NoConnectionError);
	}
	

	/**
	 * 处理服务器异常，试图确定是否显示旧信息，或显示从服务器检索的信息
	 */
	private static String handleServerError(Object err, Context context) {
		VolleyError error = (VolleyError) err;

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 401:
				try {
				    String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
					
				    try {
	                    // server might return error like this { "error": "Some error occured" }
	                    // Use "Gson" to parse the result
	                    JsonObject result = new JsonObject(data);
	                    String errorMsg = result.getString("error");
	                    if (!TextUtils.isEmpty(errorMsg))
	                        return errorMsg;
                    } catch (Exception e) {
                        return ex_http_404+"[状态码:"+response.statusCode+"]";
                    }
				} catch (Exception e) {
				    LLog.e(e);
				}
				// invalid request
				return ex_http_404;

			default:
                return ex_http_serverdown+"[状态码:"+response.statusCode+"]";
			}
		}
		return ex_http_unknown;
	}
	
}