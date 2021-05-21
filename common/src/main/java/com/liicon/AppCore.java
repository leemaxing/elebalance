package com.liicon;

import java.util.Map;

import android.content.Context;
import android.view.View;

import com.android.volley.VolleyError;
import com.liicon.application.BasicAppHelper;
import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.log.LLog;
import com.liicon.utils.log.Level;
import com.liicon.volley.ResultCallback;
import com.liicon.volley.error.VolleyErrorHelper;
import com.liicon.volley.parser.JsonParser;
import com.liicon.volley.request.ParserRequest;

/**
 * APP辅助工具
 * @version  v0.1  king  2014-12-18  APP通用方法管理
 */
public class AppCore {

    public static void toast(Context context, VolleyError error) {
        toast(context, VolleyErrorHelper.getMessage(error, context));
    }
    
    public static void toast(Context context, String text) {
        ToastUtils.show(context, text);
    }
    
    public static void log(Level level, Object... messages) {
        LLog.log(level, messages);
    }
    
    public static <T extends JsonParser<?>> void load(int method, String url,
            Map<String, String> params, T parser, ResultCallback<T> listener) {
        BasicAppHelper.get().load(new ParserRequest<T>(method, url, params, parser, listener));
    }

    public static void load(View view, String url) {
        BasicAppHelper.get().loadImage(view, url);
    }
    
    
}
