package org.jdiy.core.ex;
/**
 * JDiy类型转换异常类.
 * 当字段类型转换时出现错误(例如一个字符串不能被转换为数字时)，将抛出此异常.
 */
public class JDiyConvertException extends JDiyException {
    public JDiyConvertException(String message){
        super(message);
    }
    public JDiyConvertException(String message, String ... ins){
        super(message, ins);
    }
    public JDiyConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyConvertException(Throwable cause) {
        super(cause);
    }
}
