package com.liicon.json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import com.liicon.utils.version.SdkVersion;
import com.liicon.utils.version.SdkVersionCodes;

/**
 * 继承自org.json.JsonArray
 * @version  v0.1  king  2014-11-10  增强存储的方法，去除抛异常
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class JsonArray extends JSONArray implements Json {
    
    public JsonArray() {
        super();
    }
    @SuppressWarnings("unchecked")
    <T>JsonArray(Object copyFrom) {
        this();
        
        if (copyFrom != null && copyFrom.getClass().isArray()) {
            copy((T[]) copyFrom);
        }
    }
    public <T>JsonArray(T[] copyFrom) {
        this();
        
        if (copyFrom != null && copyFrom.length > 0) {
            copy(copyFrom);
        }
    }
    public JsonArray(Collection<?> copyFrom) {
        this();
        
        if (copyFrom != null && !copyFrom.isEmpty()) {
            for (Iterator<?> it = copyFrom.iterator(); it.hasNext();) {
                put(JsonObject.wrapObject(it.next()));
            }
        }
    }
    public JsonArray(JSONTokener copyFrom) throws JsonParseException {
        this();
        
        Object object = null;
        try {
            object = copyFrom.nextValue();
            copy((JSONArray) object);
        } catch (JSONException e) {
            throw new JsonParseException(e);
        } catch (ClassCastException e) {
            throw new JsonParseException(String.valueOf(object)+" cannot be cast to JSONArray");
        }
    }
    public JsonArray(String copyFrom) throws JsonParseException {
        this(new JSONTokener(copyFrom));
    }
    public JsonArray(JsonArray copyFrom) {
        this();
        copy(copyFrom);
    }
    JsonArray(JSONArray copyFrom) {
        this();
        copy(copyFrom);
    }
    
    private JsonArray copy(JSONArray copyFrom) {
        if (copyFrom != null && copyFrom.length() > 0) {
            for (int i=0; i<copyFrom.length(); i++) {
                Object value = copyFrom.opt(i);
                if (value != null) {
                    super.put(value);
                }
            }
        }
        return this;
    }
    private <T>JsonArray copy(T[] copyFrom) {
        if (copyFrom != null && copyFrom.length > 0) {
            final int length = Array.getLength(copyFrom);
            for (int i = 0; i < length; ++i) {
                put(JsonObject.wrapObject(Array.get(copyFrom, i)));
            }
        }
        return this;
    }
    

    /**
     * 判断某个index对应的值是否为空
     * @param name
     * @return true：不存在，或" "空字符串
     */
    public boolean isEmpty(int index) {
        return super.isNull(index) || TextUtils.isEmpty(String.valueOf(super.opt(index)));
    }
    
    
    
    
    
    
    
    /*
     * get
     */
    public boolean getBoolean(int index, boolean defValue) {
        boolean value = defValue;
        try {
            value = super.getBoolean(index);
        } catch (JSONException e) { }
        return value;
    }
    public double getDouble(int index, double defValue) {
        double value = defValue;
        try {
            value = super.getDouble(index);
        } catch (JSONException e) { }
        return value;
    }
    public int getInt(int index, int defValue) {
        int value = defValue;
        try {
            value = super.getInt(index);
        } catch (JSONException e) { }
        return value;
    }
    public JsonArray getJSONArray(int index, JsonArray defValue) {
        JsonArray value = defValue;
        try {
            value = new JsonArray(super.getJSONArray(index));
        } catch (JSONException e) { }
        if (value == null) {
            value = defValue;
        }
        return value;
    }
    public JsonObject getJSONObject(int index, JsonObject defValue) {
        JsonObject value = defValue;
        try {
            value = new JsonObject(super.getJSONObject(index));
        } catch (JSONException e) { }
        if (value == null) {
            value = defValue;
        }
        return value;
    }
    public long getLong(int index, long defValue) {
        long value = defValue;
        try {
            value = super.getLong(index);
        } catch (JSONException e) { }
        return value;
    }
    public String getString(int index, String defValue) {
        String value = defValue;
        try {
            value = super.getString(index);
        } catch (JSONException e) { }
        if (value == null) {
            value = defValue;
        }
        return value;
    }
    @Override
    public Object get(int index) {
        try {
            return super.get(index);
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public boolean getBoolean(int index) {
        try {
            return super.getBoolean(index);
        } catch (JSONException e) {
            return false;
        }
    }
    @Override
    public double getDouble(int index) {
        try {
            return super.getDouble(index);
        } catch (JSONException e) {
            return -1;
        }
    }
    @Override
    public int getInt(int index) {
        try {
            return super.getInt(index);
        } catch (JSONException e) {
            return -1;
        }
    }
    @Override
    public JsonArray getJSONArray(int index) {
        try {
            return new JsonArray(super.getJSONArray(index));
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public JsonObject getJSONObject(int index) {
        try {
            return new JsonObject(super.getJSONObject(index));
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public long getLong(int index) {
        try {
            return super.getLong(index);
        } catch (JSONException e) {
            return -1;
        }
    }
    @Override
    public String getString(int index) {
        try {
            return super.getString(index);
        } catch (JSONException e) {
            return null;
        }
    }
    
    
    
    /*
     * set
     */
    public JsonArray put(Object value) {
        super.put(value);
        return this;
    }
    public JsonArray put(boolean value) {
        super.put(value);
        return this;
    }
    public JsonArray put(double value) {
        try {
            super.put(value);
        } catch (JSONException e) { }
        return this;
    }
    public JsonArray put(int value) {
        super.put(value);
        return this;
    }
    public JsonArray put(long value) {
        super.put(value);
        return this;
    }
    @Override
    public JsonArray put(int index, boolean value) {
        try {
            super.put(index, value);
        } catch (JSONException e) { }
        return this;
    }
    @Override
    public JsonArray put(int index, double value) {
        try {
            super.put(value);
        } catch (JSONException e) { }
        return this;
    }
    @Override
    public JsonArray put(int index, int value) {
        try {
            super.put(index, value);
        } catch (JSONException e) { }
        return this;
    }
    @Override
    public JsonArray put(int index, long value) {
        try {
            super.put(index, value);
        } catch (JSONException e) { }
        return this;
    }
    @Override
    public JsonArray put(int index, Object value) {
        try {
            super.put(index, value);
        } catch (JSONException e) { }
        return this;
    }
    
    
    
    @Override
    public JsonObject toJSONObject(JSONArray names) {
        try {
            return new JsonObject(super.toJSONObject(names));
        } catch (JSONException e) {
            return null;
        }
    }
    
    /**
     * 不建议低版本【API<19】调用
     * <pre>
     * 会在JsonArray中残留null值，且length()大小不会改变
     * 通过重新实例化JsonArray，可以清除所有null值
     * 例：
     *   jsonArray.remove(1);
     *   jsonArray = new JsonArray(jsonArray);
     * </pre>
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Object remove(int index) {
        if (SdkVersion.isSupport(SdkVersionCodes.KITKAT)) {
            return super.remove(index);
        } else {
            if (index < 0 || index >= length()) {
                return null;
            }

            Object value = get(index);
            try {
                super.put(index, (Object)null);
            } catch (JSONException e) {
                return null;
            }
            return value;
        }
    }
    
}
