package org.jdiy.core.ex;

/**
 * JDiy参数异常.
 * 例如调用{@link org.jdiy.core.Dao#rs(org.jdiy.core.Args)}方法时，传入的参数对象设置有误时抛出此异常.
 */
public class JDiyParamException extends JDiyException{
    public JDiyParamException(String message){
        super(message);
    }
    public JDiyParamException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyParamException(Throwable cause) {
        super(cause);
    }
}
