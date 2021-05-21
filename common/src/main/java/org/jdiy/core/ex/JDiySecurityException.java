package org.jdiy.core.ex;

/**
 * JDiy安全异常.
 * 例如存在SQL注入，或RSS威胁时，有可能抛出此异常.
 */
public class JDiySecurityException extends JDiyException{
    public JDiySecurityException(String message){
        super(message);
    }
    public JDiySecurityException(String message, String ... ins){
        super(message, ins);
    }
    public JDiySecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiySecurityException(Throwable cause) {
        super(cause);
    }

    public JDiySecurityException(Throwable cause, String message, String... ins) {
        super(cause, message, ins);
    }
}
