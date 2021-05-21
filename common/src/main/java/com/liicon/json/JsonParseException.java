package com.liicon.json;

/**
 * JSON解析异常
 * @version  v0.1  king  2014-11-10  JSON解析异常
 */
public class JsonParseException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public JsonParseException() {
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public JsonParseException(Throwable throwable) {
        super(throwable);
    }
    
}
