package com.liicon.http.callback;

import com.android.volley.VolleyError;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.liicon.http.HttpResultCallBack;
import com.liicon.json.Json;
import com.liicon.json.JsonParseException;
import com.liicon.json.JsonUtils;
import com.liicon.volley.error.JsonParseError;
import com.liicon.volley.parser.JsonParser;

/**
 * 自动完成JSON解析的CallBack
 * @version  v0.1  king  2014-11-14  实现对字符的处理，自动完成JSON解析
 * @param <T>
 */
public class JsonRequestCallBack<E extends Json, T extends JsonParser> extends RequestCallBack<String> {
    
    private T parser;
    private HttpResultCallBack<T> callBack;
    
    public JsonRequestCallBack(T parser, HttpResultCallBack<T> callBack) {
        super();
        this.parser = parser;
        this.callBack = callBack;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public final void onSuccess(ResponseInfo<String> responseInfo) {
        if (callBack != null) {
            VolleyError error = null;
            
            String result = responseInfo.result;
            

            // 获取JSON对象
            E json = null;
            
            try {
                json = (E) JsonUtils.fromJson(result);
                if (json == null) {
                    error = new JsonParseError("result【"+result+"】 can not Empty.");
                }
            } catch (JsonParseException e) {
                error = new JsonParseError(e);
            }
            
            if (json != null) {
                // 解析JSON到实体类
                try {
                    parser.parse(json);
                } catch (JsonParseError e) {
                    error = e;
                }
            }

            // 回掉处理
            if (error != null) {
                // 出现异常
                callBack.onFailure(new HttpException(error));
            } else {
                // 成功处理
                callBack.onSuccess(parser, responseInfo.resultFormCache);
            }
        }
    }
    @Override
    public void onFailure(HttpException error, String msg) {
        if (callBack != null) {
            callBack.onFailure(error);
        }
    }
    @Override
    public void onCancelled() {
        if (callBack != null) {
            callBack.onCancelled();
        }
    }
    @Override
    public void onLoading(long total, long current, boolean isUploading) {
        if (callBack != null) {
            callBack.onLoading(total, current, isUploading);
        }
    }
    @Override
    public void onStart() {
        if (callBack != null) {
            callBack.onStart();
        }
    }

}
