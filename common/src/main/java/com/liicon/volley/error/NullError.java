package com.liicon.volley.error;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * 空指针异常
 * @author King
 * @version v1.0
 * @created 2014-3-10
 */
@SuppressWarnings("serial")
public class NullError extends VolleyError {

    public NullError() {
        super();
    }

    public NullError(NetworkResponse response) {
        super(response);
    }

    public NullError(String message) {
       super(message);
    }

    public NullError(String message, Throwable cause) {
        super(message, cause);
    }

    public NullError(Throwable cause) {
        super(cause);
    }
    
}
