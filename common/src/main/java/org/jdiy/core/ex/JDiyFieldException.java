package org.jdiy.core.ex;

/**
 * JDiy数据库字段错误时将抛出此异常。例如，要获取的字段不存在.
 */
public class JDiyFieldException extends JDiyException {
    public JDiyFieldException(String message) {
        super(message);
    }
    public JDiyFieldException(String message, String ... ins){
        super(message, ins);
    }

    public JDiyFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyFieldException(Throwable cause) {
        super(cause);
    }
}
