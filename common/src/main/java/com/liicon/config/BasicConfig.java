package com.liicon.config;

/**
 * 配置管理类
 * @created v1.0 king 2014-07-21
 * @created v1.1 king 2014-12-16
 */
public abstract class BasicConfig extends AbsConfig {
    
    @Override
    protected void initialize() {
        set(ConfigKeys.CONF_ERROR_HTTP_TIMEOUT, "请求超时");
        set(ConfigKeys.CONF_ERROR_HTTP_NETWORK, "网络异常");
        set(ConfigKeys.CONF_ERROR_HTTP_SERVERDOWN, "服务器异常");
        set(ConfigKeys.CONF_ERROR_HTTP_404, "404,找不到数据");
        set(ConfigKeys.CONF_ERROR_HTTP_PARSEJSON, "JSON数据解析异常");
        set(ConfigKeys.CONF_ERROR_HTTP_NULLPOINT, "空指针异常");
        set(ConfigKeys.CONF_ERROR_HTTP_UNKNOWN, "未知错误");
        
        set(ConfigKeys.CONF_ERROR_APP_REPORT_TITLE, "应用程序错误");
        set(ConfigKeys.CONF_ERROR_APP_REPORT_DESCRIPTION, "很抱歉，应用程序出现错误，即将退出。\n请提交错误报告，我们会尽快修复这个问题！");
        set(ConfigKeys.CONF_ERROR_APP_REPORT_SUBMIT, "提交报告");
        set(ConfigKeys.CONF_ERROR_APP_REPORT_CANCEL, "取消");
        set(ConfigKeys.CONF_ERROR_APP_REPORT_EMAIL, "alafighting@163.com");
        set(ConfigKeys.CONF_ERROR_APP_REPORT_SUBJECT, "Android客户端 - 错误报告");
        set(ConfigKeys.CONF_ERROR_HTTP_IMAGE_FAIL, "图片加载失败");

        set(ConfigKeys.CONF_HTTP_IMAGE_LOADDING_RESID, android.R.drawable.ic_menu_rotate);
        set(ConfigKeys.CONF_HTTP_IMAGE_ERROR_RESID, android.R.drawable.ic_menu_report_image);
        
    }
    
}
