package com.liicon.json;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.liicon.utils.log.LLog;

import android.text.TextUtils;

/**
 * 继承自org.json.JSONObject
 * @version  v0.1  king  2014-11-10  增强存储的方法，去除抛异常
 */
public class JsonObject extends JSONObject implements Json {
    
    public JsonObject(String copyFrom) throws JsonParseException {
        this(new JSONTokener(copyFrom));
    }
    public JsonObject(Map<?, ?> copyFrom) {
        super(copyFrom);
    }
    public JsonObject() {
        super();
    }

    public JsonObject(JSONTokener copyFrom) throws JsonParseException {
        super();

        Object object = null;
        try {
            object = copyFrom.nextValue();
            copy(((JSONObject) object));
        } catch (JSONException e) {
            throw new JsonParseException(e);
        } catch (ClassCastException e) {
            throw new JsonParseException(String.valueOf(object)+" cannot be cast to JsonObject");
        }
    }
    public JsonObject(JsonObject copyFrom, String[] names) {
        super();
        
        if (names != null && names.length > 0) {
            copy(copyFrom, Arrays.asList(names).iterator());
        }
    }
    public JsonObject(JsonObject copyFrom) {
        super();
        copy(copyFrom);
    }
    JsonObject(JSONObject copyFrom) {
        super();
        copy(copyFrom);
    }
    
    private JsonObject copy(JSONObject copyFrom) {
        if (copyFrom != null) {
            return copy(copyFrom, copyFrom.keys());
        } else {
            return this;
        }
    }
    private JsonObject copy(JSONObject copyFrom, Iterator<String> names) {
        if (copyFrom != null && names != null) {
            while (names.hasNext()) {
                String name = names.next();
                Object value = copyFrom.opt(name);
                if (value != null) {
                    try {
                        super.put(name, value);
                    } catch (JSONException e) { }
                }
            }
        }
        return this;
    }
    
    
    /**
     * 判断某个name对应的值是否为空
     * @param name
     * @return true：不存在，或" "空字符串
     */
    public boolean isEmpty(String name) {
        return super.isNull(name) || TextUtils.isEmpty(String.valueOf(super.opt(name)));
    }
    
    
    
    
    /*
     * getter
     */
    public boolean getBoolean(String name, boolean defValue) {
        boolean value = defValue;
        try {
            value = super.getBoolean(name);
        } catch (JSONException e) { }
        return value;
    }
    public double getDouble(String name, double defValue) {
        double value = defValue;
        try {
            value = super.getDouble(name);
        } catch (JSONException e) { }
        return value;
    }
    public int getInt(String name, int defValue) {
        int value = defValue;
        try {
            value = super.getInt(name);
        } catch (JSONException e) { }
        return value;
    }
    public JsonArray getJSONArray(String name, JsonArray defValue) {
        JsonArray value = defValue;
        try {
            value = new JsonArray(super.getJSONArray(name));
        } catch (JSONException e) { }
        if (value == null) {
            value = defValue;
        }
        return value;
    }
    public JsonObject getJSONObject(String name, JsonObject defValue) {
        JsonObject value = defValue;
        try {
            value = new JsonObject(super.getJSONObject(name));
        } catch (JSONException e) { }
        if (value == null) {
            value = defValue;
        }
        return value;
    }
    public long getLong(String name, long defValue) {
        long value = defValue;
        try {
            value = super.getLong(name);
        } catch (JSONException e) { }
        return value;
    }
    public String getString(String name, String defValue) {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            return defValue;
        }
    }
    @Override
    public JsonArray getJSONArray(String name) {
        try {
            return new JsonArray(super.getJSONArray(name));
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public JsonObject getJSONObject(String name) {
        try {
            return new JsonObject(super.getJSONObject(name));
        } catch (JSONException e) {
            LLog.e(e);
            return null;
        }
    }
    @Override
    public Object get(String name) {
        try {
            return super.get(name);
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public boolean getBoolean(String name) {
        try {
            return super.getBoolean(name);
        } catch (JSONException e) {
            return false;
        }
    }
    @Override
    public double getDouble(String name) {
        try {
            return super.getDouble(name);
        } catch (JSONException e) {
            return -1;
        }
    }
    @Override
    public int getInt(String name) {
        try {
            return super.getInt(name);
        } catch (JSONException e) {
            return -1;
        }
    }
    @Override
    public long getLong(String name) {
        try {
            return super.getLong(name);
        } catch (JSONException e) {
            return -1;
        }
    }
    @Override
    public String getString(String name) {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            return null;
        }
    }
    
    
    

    @Override
    public JsonArray names() {
        return new JsonArray(super.names());
    }
    @Override
    public JsonArray optJSONArray(String name) {
        return new JsonArray(super.optJSONArray(name));
    }
    @Override
    public JsonObject optJSONObject(String name) {
        return new JsonObject(super.optJSONObject(name));
    }
    @Override
    public JsonArray toJSONArray(JSONArray names) {
        try {
            return new JsonArray(super.toJSONArray(names));
        } catch (JSONException e) {
            return null;
        }
    }
    
    

    /*
     * set
     */
    public JsonObject putOpt(String name, Object value) {
        try {
            super.putOpt(name, value);
        } catch (JSONException e) { }
        return this;
    }
    public JsonObject put(String name, Object value) {
        try {
            super.put(name, value);
        } catch (JSONException e) { }
        return this;
    }
    public JsonObject put(String name, boolean value) {
        try {
            super.put(name, value);
        } catch (JSONException e) { }
        return this;
    }
    public JsonObject put(String name, double value) {
        try {
            super.put(name, value);
        } catch (JSONException e) { }
        return this;
    }
    public JsonObject put(String name, int value) {
        try {
            super.put(name, value);
        } catch (JSONException e) { }
        return this;
    }
    public JsonObject put(String name, long value) {
        try {
            super.put(name, value);
        } catch (JSONException e) { }
        return this;
    }
    
    
    
    /**
     * 判断是否含有该Key值
     */
    @Override
    public boolean has(String name) {
        return super.has(name) && !isNull(name);
    }
    
    @Override
    public JsonObject accumulate(String name, Object value) {
        try {
            return new JsonObject(super.accumulate(name, value));
        } catch (JSONException e) {
            return null;
        }
    }

    
    /**
     * wrap
     */
    public static Object wrapObject(Object o) {
        if (o == null) {
            return NULL;
        }
        if (o instanceof JSONArray) {
            return new JsonArray((JSONArray) o);
        } else if (o instanceof JSONObject) {
            return new JsonObject((JSONObject) o);
        }
        if (o.equals(NULL)) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JsonArray((Collection<?>) o);
            } else if (o.getClass().isArray()) {
                return new JsonArray(o);
            }
            if (o instanceof Map) {
                return new JsonObject((Map<?, ?>) o);
            }
            if (o instanceof Boolean ||
                o instanceof Byte ||
                o instanceof Character ||
                o instanceof Double ||
                o instanceof Float ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Short ||
                o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
