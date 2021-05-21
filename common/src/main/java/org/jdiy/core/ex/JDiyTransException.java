package org.jdiy.core.ex;

/**
 * JDiy事务处理异常.
 * 当事务处理过程中出现错误，可能抛出此异常.
 */
public class JDiyTransException extends JDiyException {
    public JDiyTransException(String message){
        super(message);
    }
    public JDiyTransException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyTransException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyTransException(Throwable cause) {
        super(cause);
    }
}
