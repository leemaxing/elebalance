package com.liicon.volley.error;

import com.android.volley.NetworkResponse;

/**
 * JSON数据解析异常
 * @version v0.1 king 2014-10-20
 */
@SuppressWarnings("serial")
public class JsonParseError extends ParseError {

    public JsonParseError() {
        super();
    }

    public JsonParseError(NetworkResponse response) {
        super(response);
    }

    public JsonParseError(String exceptionMessage) {
       super(exceptionMessage);
    }

    public JsonParseError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
    }

    public JsonParseError(Throwable cause) {
        super(cause);
    }
    
}
