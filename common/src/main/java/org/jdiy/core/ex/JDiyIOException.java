package org.jdiy.core.ex;

/**
 * JDiy文件读写(I/O)异常类. 例如在存储XML或文件数据时，发生错误.
 */
public class JDiyIOException extends JDiyException {
    public JDiyIOException(String message){
        super(message);
    }

    public JDiyIOException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyIOException(Throwable cause) {
        super(cause);
    }

    public JDiyIOException(Throwable cause, String message, String... ins) {
        super(cause, message, ins);
    }
}
