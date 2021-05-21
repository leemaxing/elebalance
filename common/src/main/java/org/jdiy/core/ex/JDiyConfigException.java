package org.jdiy.core.ex;

/**
 * JDiy配置信息异常类.
 * 当因jdiy.xml配置错误时，将抛出此异常.
 */
public class JDiyConfigException extends JDiyException {
    public JDiyConfigException(String message){
        super(message);
    }
    public JDiyConfigException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyConfigException(Throwable cause) {
        super(cause);
    }
}
