package com.liicon.http;

import java.io.File;
import java.util.Map;

import android.text.TextUtils;

import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.liicon.http.callback.JsonRequestCallBack;
import com.liicon.json.Json;
import com.liicon.volley.parser.JsonParser;

/**
 * HTTP网络请求管理工具
 * @version  v0.1  king  2014-11-13  继承自com.lidroid.xutils.HttpUtils
 */
public class HttpManager extends com.lidroid.xutils.HttpUtils {
    
    private static HttpManager mHttpManager = new HttpManager();
    
    public static HttpManager get(boolean createNew) {
        if (createNew) {
            return new HttpManager();
        }
        return mHttpManager;
    }
    public static HttpManager get() {
        return get(false);
    }
 
    
    /**
     * 下载文件
     * @param url
     * @param target 文件名
     * @param params 请求参数
     * @param callback 回掉函数
     */
    @Override
    public HttpHandler<File> download(String url, String target, RequestParams params, RequestCallBack<File> callback) {
        return super.download(HttpRequest.HttpMethod.GET, url, target, params, true, false, callback);
    }

    /**
     * 下载文件
     * @param method
     * @param url
     * @param target 文件名
     * @param params 请求参数
     * @param autoResume 是否续传
     * @param autoRename 是否重命名
     * @param callback 回掉函数
     */
    @Override
    public HttpHandler<File> download(HttpMethod method, String url,
            String target, RequestParams params, boolean autoResume,
            boolean autoRename, RequestCallBack<File> callback) {
        return super.download(method, url, target, params, autoResume, autoRename, callback);
    }
    
    public <E extends Json, T extends JsonParser> HttpHandler<String> send(HttpMethod method, String url,
            RequestParams params, T parser, HttpResultCallBack<T> callBack) {
        return super.send(method, url, params, new JsonRequestCallBack<E, T>(parser, callBack));
    }
    public <E extends Json, T extends JsonParser> HttpHandler<String> send(HttpMethod method, String url,
            T parser, HttpResultCallBack<T> callBack) {
        return super.send(method, url, new JsonRequestCallBack<E, T>(parser, callBack));
    }
    
    
    
    
    
    
    
    

    /**
     * 文件上传
     * @param url 提交的URL地址
     * @param callBack 回掉处理事件
     * @param bodyParams 请求的Body参数
     * @param files 上传的文件
     */
    public static void upload(HttpManager http,
            String url,
            Map<String, String> bodyParams,
            Map<String, File> files,
            RequestCallBack<String> callBack) {
        upload(http, url, HttpRequest.HttpMethod.POST, null, bodyParams, files, callBack, null, null);
    }
    /**
     * 文件上传
     * @param url 提交的URL地址
     * @param method 提交方式
     * @param callBack 回掉处理事件
     * @param queryParams 请求的URL参数
     * @param bodyParams 请求的Body参数
     * @param files 上传的文件
     */
    public static void upload(HttpManager http,
            String url,
            HttpRequest.HttpMethod method,
            Map<String, String> queryParams,
            Map<String, String> bodyParams,
            Map<String, File> files,
            RequestCallBack<String> callBack) {
        upload(http, url, method, queryParams, bodyParams, files, callBack, null, null);
    }
    /**
     * 文件上传
     * @param url 提交的URL地址
     * @param method 提交方式
     * @param callBack 回掉处理事件
     * @param queryParams 请求的URL参数
     * @param bodyParams 请求的Body参数
     * @param files 上传的文件
     * @param responseCharset 回复编码
     */
    public static void upload(HttpManager http,
            String url,
            HttpRequest.HttpMethod method,
            Map<String, String> queryParams,
            Map<String, String> bodyParams,
            Map<String, File> files,
            RequestCallBack<String> callBack,
            String responseCharset) {
        upload(http, url, method, queryParams, bodyParams, files, callBack, null, responseCharset);
    }
    
    /**
     * 文件上传
     * @param url 提交的URL地址
     * @param callBack 回掉处理事件
     * @param bodyParams 请求的Body参数
     * @param files 上传的文件
     */
    public static <T extends JsonParser> void uploadJson(HttpManager http,
            String url,
            Map<String, String> bodyParams,
            Map<String, File> files,
            final T parser,
            final HttpResultCallBack<T> callBack) {
        uploadJson(http, url, null, null, bodyParams, files, parser, callBack, null, null);
    }
    
    /**
     * 文件上传
     * @param requestCharset 请求编码
     * @param responseCharset 回复编码
     * @param url 提交的URL地址
     * @param method 提交方式
     * @param callBack 回掉处理事件
     * @param queryParams 请求的URL参数
     * @param bodyParams 请求的Body参数
     * @param files 上传的文件
     */
    public static <T extends Json, P extends JsonParser> void uploadJson(HttpManager http,
            String url,
            HttpRequest.HttpMethod method,
            Map<String, String> queryParams,
            Map<String, String> bodyParams,
            Map<String, File> files,
            final P parser,
            final HttpResultCallBack<P> callBack,
            String requestCharset,
            String responseCharset) {
        if (parser == null) {
            throw new NullPointerException("'JsonParser' Can not be Empty");
        }
        
        if (method == null) {
            method = HttpMethod.POST;
        }
        JsonRequestCallBack<T, P> requestCallBack = new JsonRequestCallBack<T, P>(parser, callBack);
        upload(http, url, method, queryParams, bodyParams, files, requestCallBack, requestCharset, responseCharset);
    }
    /**
     * 文件上传
     * @param requestCharset 请求编码
     * @param responseCharset 回复编码
     * @param url 提交的URL地址
     * @param method 提交方式
     * @param callBack 回掉处理事件
     * @param queryParams 请求的URL参数
     * @param bodyParams 请求的Body参数
     * @param files 上传的文件
     */
    protected static void upload(HttpManager http,
            String url,
            HttpRequest.HttpMethod method,
            Map<String, String> queryParams,
            Map<String, String> bodyParams,
            Map<String, File> files,
            RequestCallBack<String> callBack,
            String requestCharset,
            String responseCharset) {
        // 默认编码UTF-8
        if (TextUtils.isEmpty(requestCharset)) {
            requestCharset = "UTF-8";
        }
        if (TextUtils.isEmpty(responseCharset)) {
            responseCharset = "UTF-8";
        }
        if (method == null) {
            method = HttpRequest.HttpMethod.GET;
        }
        
        
        // 设置请求参数的编码
        RequestParams params = new RequestParams(requestCharset);

        // 添加URL参数
        if (queryParams != null && queryParams.size() > 0) {
            for (String name : queryParams.keySet()) {
                params.addQueryStringParameter(name, queryParams.get(name));
            }
        }
        
        // 添加Body参数
        if (bodyParams != null && bodyParams.size() > 0) {
            for (String name : bodyParams.keySet()) {
                params.addBodyParameter(name, bodyParams.get(name));
            }
        }

        // 添加Body文件
        if (files != null && files.size() > 0) {
            for (String name : files.keySet()) {
                params.addBodyParameter(name, files.get(name));
            }
        }
        // 用于非multipart表单的单文件上传
        //params.setBodyEntity(new FileUploadEntity(new File("/sdcard/test.zip"), "binary/octet-stream"));
        // 用于非multipart表单的流上传
        //params.setBodyEntity(new InputStreamUploadEntity(stream ,length));

        // 设置返回文本的编码， 默认编码UTF-8
        http.configResponseTextCharset(responseCharset);
        
        // 发送请求
        http.send(method, url, params, callBack);

    }
    
}
