package com.liicon.volley.request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.liicon.json.Json;
import com.liicon.json.JsonParseException;
import com.liicon.json.JsonUtils;
import com.liicon.volley.error.JsonParseError;
import com.liicon.volley.error.NullError;
import com.liicon.volley.error.ParseError;

/**
 * 支持GET、POST的JSON请求
 * 
 * <pre>
 * 传参方式：
 * new JsonRequest(Request.Method.POST,
 *     "http://192.168.1.10/login.action",
 *     params,
 *     listener,
 *     errorListener);
 * </pre>
 * 
 * @version  v0.1  king  2014-12-15  支持GET、POST
 */
public class JsonRequest<V extends Json> extends Request<V> {
	private final Listener<V> mListener;
	private final Map<String, String> mParams;
	
	public JsonRequest(int method, String url, Listener<V> listener,
	        ErrorListener errorListener) {
	    super(method, parseParams(method, url, null), errorListener);
	    mListener = listener;
	    this.mParams = null;
	}
	public JsonRequest(int method, String url, Map<String, String> params, Listener<V> listener,
            ErrorListener errorListener) {
		super(method, parseParams(method, url, params), errorListener);
        mListener = listener;
		this.mParams = params;
	}
	public JsonRequest(String url, Map<String, String> params, Listener<V> listener,
	        ErrorListener errorListener) {
	    this(Method.GET, url, params, listener, errorListener);
	}
	
	/**
	 * 解析请求参数
	 * @param method
	 * @param url
	 * @param params
	 * @return 返回处理后的URL
	 */
	private static String parseParams(int method, String url, Map<String, String> params) {
		if (params!=null && method == Request.Method.GET) {
			if (url.contains("?")) {
				if (url.endsWith("&")) {
					url = url.substring(0, url.length()-1);
				}
			} else {
				url += "?";
			}
			
			for (String key : params.keySet()) {
				url += "&"+key+"="+params.get(key);
			}
			url = url.replace("?&", "?");
		}
		return url;
	}

	@Override
	protected Map<String, String> getParams()
			throws com.android.volley.error.AuthFailureError {
		if (getMethod() == Request.Method.GET) {
			return super.getParams();
		}
		return (mParams!=null ? mParams : new HashMap<String, String>());
	}
	
    @Override
    protected void deliverResponse(V response) {
        mListener.onResponse(response);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected Response<V> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<V>) Response.success(JsonUtils.fromJson(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new JsonParseError(e));
        } catch (NullPointerException e) {
            return Response.error(new NullError(e));
        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }
	
}
