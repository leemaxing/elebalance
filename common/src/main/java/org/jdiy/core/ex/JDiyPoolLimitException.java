package org.jdiy.core.ex;

/**
 * JDiy数据库连接池异常.
 * 当连接达到上限时，有可能抛出此异常.
 */
public class JDiyPoolLimitException extends JDiyException {
    public JDiyPoolLimitException(String message){
        super(message);
    }

    public JDiyPoolLimitException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyPoolLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyPoolLimitException(Throwable cause) {
        super(cause);
    }
}
