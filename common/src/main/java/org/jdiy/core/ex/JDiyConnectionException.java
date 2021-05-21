package org.jdiy.core.ex;

/**
 * JDiy连接异常。例如，数据库连接失败.
 */
public class JDiyConnectionException extends JDiyException {
    public JDiyConnectionException(String message) {
        super(message);
    }
    public JDiyConnectionException(String message, String... ins){
        super(message, ins);
    }

    public JDiyConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyConnectionException(Throwable cause) {
        super(cause);
    }
}
