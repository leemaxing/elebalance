package org.jdiy.core.ex;

/**
 * 不被支持或者不被允许的异常.
 * 如：<br/>
 * (1). JDiy不受支持的异常.<br/>
 * 　　例如数据库中存在不受支持的字段数据类型(例如Blob)时，有可能抛出此异常.
 * 有关JDiy所支持的字段数据类型，
 * 请参见JDiy官网(<a href="http://www.jdiy.org" target="_blank">http://www.jdiy.org</a>)相关教程.<br/>
 * (2). 不被允许的异常<br/>
 * 　　例如从一个非multipart/form-data内容类型的请求中去调用
 * {@link org.jdiy.core.AppCore#getFile(String)}等方法获取上传文件.
 */
public class JDiyUnSupportException extends JDiyException {
    public JDiyUnSupportException(String message) {
        super(message);
    }
    public JDiyUnSupportException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyUnSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyUnSupportException(Throwable cause) {
        super(cause);
    }
}
