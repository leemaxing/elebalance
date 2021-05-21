package org.jdiy.core.ex;

/**
 * JDiy数据库操作过程中的SQL语句异常.
 * 例如调用{@link org.jdiy.core.Dao#rs(org.jdiy.core.Args)}方法时，
 * 由于传入的{@link org.jdiy.core.Args}参数设置不当引起的SQL错误.
 */
public class JDiySqlException extends JDiyException{
    public JDiySqlException(String message) {
        super(message);
    }
    public JDiySqlException(String message, String ... ins){
        super(message, ins);
    }
    public JDiySqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDiySqlException(Throwable cause) {
        super(cause);
    }
}
