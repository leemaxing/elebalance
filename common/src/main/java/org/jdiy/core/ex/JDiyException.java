package org.jdiy.core.ex;

import org.jdiy.util.Txt;

/**
 * JDiy异常类.
 * 这是JDiy所有异常类的超类.此类是抽象的，通过其子类型构造实例.
 * 它被定义为运行时异常.
 */
public abstract class JDiyException extends RuntimeException{

    public JDiyException(String message){
        super(message);
    }
    
    public JDiyException(String message, String ... ins){
        this(Txt.inject(message, ins));
    }

    public JDiyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiyException(Throwable cause) {
        super(cause);
    }

    public JDiyException(Throwable cause, String message, String ... ins) {
        super(Txt.inject(message, ins), cause);
    }

}
