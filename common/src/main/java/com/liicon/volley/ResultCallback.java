package com.liicon.volley;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

/**
 * 网络请求的回掉处理接口
 * @version  v0.1.1  king  2014-11-10  重命名，增加回掉默认实现
 * @version  v0.1.0  king  2014-04-01  定义网络请求的回掉接口
 * @param <T>
 */
public abstract class ResultCallback<T> implements Listener<T>, ErrorListener {
    
    @Override
    public void onResponse(T result) {
    }
    
    @Override
    public void onErrorResponse(VolleyError error) {
    }
    
}
