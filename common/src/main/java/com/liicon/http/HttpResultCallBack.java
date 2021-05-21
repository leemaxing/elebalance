package com.liicon.http;

import com.lidroid.xutils.exception.HttpException;

/**
 * HTTP请求回掉接口
 * @version  v0.1  king  2014-11-14  定义回掉事件
 * @param <T>
 */
public interface HttpResultCallBack<T> {
    
    public void onStart();

    public void onCancelled();

    public void onLoading(long total, long current, boolean isUploading);
    
    public void onSuccess(T result, boolean formCache);

    public void onFailure(HttpException error);
    
}
