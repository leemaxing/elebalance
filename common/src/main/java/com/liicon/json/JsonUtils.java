package com.liicon.json;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.liicon.utils.type.TypeToken;

/**
 * JSON处理工具类
 * @version v0.1 king 2014-11-21 JSON序列/反序列
 */
public class JsonUtils {
    
    /**
     * 序列化
     * @param javaObject 要序列化的对象
     * @return JSON格式字符串
     */
    public static String toJsonString(Object javaObject) {
        return JSON.toJSONString(javaObject);
    }
    /**
     * 序列化为JSON对象
     * @param javaObject 要序列化的对象
     * @return 三种可能值：JsonObject、JsonArray、null
     */
    public static Json toJson(Object javaObject) {
        if (javaObject == null) {
            return null;
        }
        if (javaObject instanceof JSON) {
            return null;
        }

        if (javaObject instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) javaObject;

            JsonObject json = new JsonObject();

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                String jsonKey = TypeUtils.castToString(key);
                Object jsonValue = toJson(entry.getValue());
                json.put(jsonKey, jsonValue);
            }

            return json;
        }

        if (javaObject instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<Object> collection = (Collection<Object>) javaObject;

            JsonArray array = new JsonArray();

            for (Object item : collection) {
                Object jsonValue = toJson(item);
                array.put(jsonValue);
            }

            return array;
        }

        Class<?> clazz = javaObject.getClass();

        if (clazz.isEnum()) {
            return null;
        }

        if (clazz.isArray()) {
            int len = Array.getLength(javaObject);

            JsonArray array = new JsonArray();

            for (int i = 0; i < len; ++i) {
                Object item = Array.get(javaObject, i);
                Object jsonValue = toJson(item);
                array.put(jsonValue);
            }

            return array;
        }

        if (ParserConfig.getGlobalInstance().isPrimitive(clazz)) {
            return null;
        }

        try {
            List<FieldInfo> getters = TypeUtils.computeGetters(clazz, null);

            JsonObject json = new JsonObject();

            for (FieldInfo field : getters) {
                Object value = field.get(javaObject);
                Object jsonValue = toJson(value);

                json.put(field.getName(), jsonValue);
            }

            return json;
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }
    
    
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @return 三种可能值：JsonObject、JsonArray、null
     */
    public static Json fromJson(String json) throws JsonParseException {
        try {
            Object value = new JSONTokener(json).nextValue();
            if (value == null) {
                return null;
            }
            
            if (value instanceof JSONObject) {
                return new JsonObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                return new JsonArray((JSONArray) value);
            }
            return null;
        } catch (org.json.JSONException e) {
            throw new JsonParseException(e.getLocalizedMessage());
        }
    }
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @param type 反序列化的对象类型
     * @return 对象实例
     */
    public static <T>T fromJson(String json, TypeToken<T> type) throws JsonParseException {
        return fromJson(json, type.getType());
    }
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @param type 反序列化的对象类型
     * @return 对象实例
     */
    public static <T>T fromJson(String json, Class<T> type) throws JsonParseException {
        return fromJson(json, (Type)type);
    }
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @param type 反序列化的对象类型
     * @return 对象实例
     */
    public static <T>T fromJson(String json, Type type) throws JsonParseException {
        try {
            return JSON.parseObject(json, type, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
        } catch (JSONException e) {
            throw new JsonParseException(e.getLocalizedMessage());
        } catch (Throwable e) {
            throw new JsonParseException(e.getLocalizedMessage());
        }
    }
    
  
    
}
