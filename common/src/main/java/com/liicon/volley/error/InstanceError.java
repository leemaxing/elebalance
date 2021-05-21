package com.liicon.volley.error;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * 实例化异常
 * @version  v0.1  king  2014-03-10  定义实例化异常类
 */
@SuppressWarnings("serial")
public class InstanceError extends VolleyError {

    public InstanceError() {
        super();
    }

    public InstanceError(NetworkResponse response) {
        super(response);
    }

    public InstanceError(String message) {
       super(message);
    }

    public InstanceError(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceError(Throwable cause) {
        super(cause);
    }
    
}
