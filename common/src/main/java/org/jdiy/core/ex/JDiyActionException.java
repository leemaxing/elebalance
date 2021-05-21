package org.jdiy.core.ex;

/**
 * JDiy Action错误异常。
 */
public class JDiyActionException extends JDiyException {
    public JDiyActionException(String message) {
        super(message);
    }
    public JDiyActionException(String message, String... ins){
        super(message, ins);
    }

    public JDiyActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyActionException(Throwable cause) {
        super(cause);
    }

    public JDiyActionException(Throwable cause, String message, String... ins) {
        super(cause, message, ins);
    }
}
