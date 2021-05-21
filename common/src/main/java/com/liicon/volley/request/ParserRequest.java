package com.liicon.volley.request;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.liicon.json.JsonParseException;
import com.liicon.json.JsonUtils;
import com.liicon.volley.ResultCallback;
import com.liicon.volley.error.JsonParseError;
import com.liicon.volley.error.NullError;
import com.liicon.volley.error.ParseError;
import com.liicon.volley.parser.JsonParser;

/**
 * 内部实现解析的Request
 * @created v0.1 king 2014-07-21
 * @created v0.2 king 2014-12-16
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class ParserRequest<T extends JsonParser> extends Request<T> {
    private final Listener<T> mListener;
    private final Map<String, String> mParams;
    private final T parser;

    public ParserRequest(int method, String url,
            T parser, ResultCallback<T> listener) {
        super(method, parseParams(method, url, null), listener);
        this.mListener = listener;
        this.mParams = null;
        this.parser = parser;
    }
    public ParserRequest(int method, String url, Map<String, String> params,
            T parser, ResultCallback<T> listener) {
        super(method, parseParams(method, url, params), listener);
        this.mListener = listener;
        this.mParams = (params==null ? new HashMap<String, String>() : params);
        this.parser = parser;
    }
    public ParserRequest(int method, String url, Map<String, String> params,
            T parser, Listener<T> listener, ErrorListener errorListener) {
        super(method, parseParams(method, url, params), errorListener);
        this.mListener = listener;
        this.mParams = (params==null ? new HashMap<String, String>() : params);
        this.parser = parser;
    }


    /**
     * 解析请求参数
     * @param method
     * @param url
     * @param params
     * @return 返回处理后的URL
     */
    private static String parseParams(int method, String url, Map<String, String> params) {
        if (params != null && method == Request.Method.GET) {
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
        return mParams!=null ? mParams : new HashMap<String, String>();
    }
    
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

	@SuppressWarnings("unchecked")
    @Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            parser.parse(JsonUtils.fromJson(jsonString));
            return Response.success(parser,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (VolleyError e) {
            return Response.error(e);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new JsonParseError(e));
		} catch (NullPointerException e) {
			return Response.error(new NullError(e));
        } catch (RuntimeException e) {
            return Response.error(new VolleyError(e));
        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        } catch (OutOfMemoryError e) {
            return Response.error(new VolleyError(e));
        } catch (Error e) {
            return Response.error(new VolleyError(e));
        } catch (Throwable e) {
            return Response.error(new VolleyError(e));
		}
	}
	
	public T getParser() {
		return parser;
	}
	
}
