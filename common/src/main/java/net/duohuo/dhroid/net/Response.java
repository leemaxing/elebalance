package net.duohuo.dhroid.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.duohuo.dhroid.Const;

import org.json.JSONObject;

import android.text.TextUtils;

import com.liicon.json.JsonArray;
import com.liicon.json.JsonObject;
import com.liicon.json.JsonParseException;
import com.liicon.json.JsonUtils;
import com.liicon.utils.log.LLog;
import com.liicon.utils.type.TypeToken;

/**
 * 返回数据要求 可以接受任意格式文本数据 网络访问返回数据
 * 
 * @author duohuo-jinghao
 */
public class Response {

	/**
	 * 返回的纯文本
	 */
	public String result;

	// 是否缓存的数据
	public boolean isCache;

	/**
	 * 数据暂存
	 */
	public Map<String, Object> bundle;

	public boolean isCache() {
		return isCache;
	}

	public void isCache(boolean isCache) {
		this.isCache = isCache;
	}

	// 操作是否成功
	public Boolean success;
	// 消息
	public String msg;
	// 错误码
	public String code;
	// 获取默认返回jsonarray jo对象的缓存 不一定有值
	JsonObject jo;

	public Response(String result) {
		bundle = new HashMap<String, Object>();
		this.result = result;
		this.success = true;
		// 默认处理结果为 true
		// 有返回success code 登按 返回结果
		if (!TextUtils.isEmpty(result)) {
			// json对象
			if (result.trim().startsWith("{")) {
				try {
					jo = new JsonObject(result);
					if (jo.has(Const.response_success)) {
						success = jo.getBoolean(Const.response_success, false);
					}
					if (jo.has(Const.response_msg)) {
						msg = jo.getString(Const.response_msg);
					}
					if (jo.has(Const.response_code)) {
						code = jo.getString(Const.response_code, null);
					}
				} catch (JsonParseException e) {
					e.printStackTrace();
				}
			} else if (result.trim().startsWith("[")) {
				// 不处理
			}
		}
	}

	/**
	 * 添加传递数据 基本在后台线程添加 前台用getBundle 获取
	 * 
	 * @param key
	 * @param obj
	 */
	public void addBundle(String key, Object obj) {
		bundle.put(key, obj);
	}

	/**
	 * 获取传递数据
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBundle(String key) {
		return (T) bundle.get(key);
	}

	/**
	 * 返回纯文本
	 * 
	 * @return
	 */
	public String plain() {
		return result;
	}

	/**
	 * 将返回的数据转换为jsonobject
	 * 
	 * @return
	 */
	public JSONObject jSON() {
		return jo;
	}

	/**
	 * 将返回的数据转换为jsonArray
	 * 
	 * @return
	 */
	public JsonArray jSONArray() {
		try {
			return new JsonArray(this.result);
		} catch (JsonParseException e) {
            LLog.e(e);
        }
		return null;
	}

	/**
	 * 解析json结果 为bean
	 * 
	 * @return
	 * @throws com.liicon.json.JsonParseException
	 */
	public <T> T model(Class<T> clazz) throws JsonParseException {
		return JsonUtils.fromJson(result, clazz);
	}

	/**
	 * 解析json结果 为 bean list
	 * 
	 * @return
	 * @throws com.liicon.json.JsonParseException
	 */
	public <T> List<T> list(final Class<T> clazz) throws JsonParseException {
		Type type = new ParameterizedType() {
			public Type getRawType() {
				return ArrayList.class;
			}

			public Type getOwnerType() {
				return null;
			}

			public Type[] getActualTypeArguments() {
				Type[] type = new Type[1];
				type[0] = clazz;
				return type;
			}
		};
		List<T> list = JsonUtils.fromJson(result, type);
		return list;
	}

	/**
	 * 获取json结果 其中的某个属性 为 jsonarray
	 * 
	 * @param prefix
	 * @return
	 */
	public JsonArray jSONArrayFrom(String prefix) {
		if (jo != null) {
			return jo.getJSONArray(prefix, new JsonArray());
		} else {
			return jSONArray();
		}
	}

	/**
	 * 获取json结果 其中的data 为 jsonarray
	 * 
	 * @param prefix
	 * @return
	 */
	public JsonArray jSONArrayFromData() {
		return jSONArrayFrom(Const.response_data);
	}

	/**
	 * 获取json结果 其中的某个属性 为 jsonobject
	 * 
	 * @param prefix
	 * @return
	 */
	public JsonObject jSONFrom(String prefix) {
		if (jo != null) {
			return jo.getJSONObject(prefix);
		}
		return null;
	}

	/**
	 * 获取json结果 其中data为 jsonobject
	 * 
	 * @param prefix
	 * @return
	 */
	public JSONObject jSONFromData() {
		return jSONFrom(Const.response_data);
	}

	/**
	 * 获取json结果 对象中的某个属性 为对象 prefix data
	 * 
	 * @param prefix
	 * @return
	 */
	public <T> T modelFrom(String prefix) {
		if (jo != null) {
			String str = jo.getString(prefix);
			Type type = new TypeToken<T>() {}.getType();
            try {
                return JsonUtils.fromJson(str, type);
            } catch (JsonParseException e) {
            }
		}
		return null;
	}

	/**
	 * 解析json结果 为bean
	 * 
	 * @return
	 * @throws com.liicon.json.JsonParseException
	 */
	public <T> T modelFrom(Class<T> clazz, String prefix) throws JsonParseException {
		String str = jo.getString(prefix);
		T obj = JsonUtils.fromJson(str, clazz);
		return obj;
	}

	public <T> T modelFromData() {
		return modelFrom(Const.response_data);
	}

	public <T> T modelFromData(Class<T> clazz) throws JsonParseException {
		return modelFrom(clazz, Const.response_data);
	}

	/**
	 * 获取json结果 对象中的某个属性 为对象 list
	 * 
	 * @param prefix
	 * @return
	 */
	public <T> List<T> listFrom(final Class<T> clazz, String prefix) {
		if (jo != null) {
			String str = jo.getString(prefix);
			Type type = new ParameterizedType() {
				public Type getRawType() {
					return ArrayList.class;
				}

				public Type getOwnerType() {
					return null;
				}

				public Type[] getActualTypeArguments() {
					Type[] type = new Type[1];
					type[0] = clazz;
					return type;
				}
			};
			try {
                return JsonUtils.fromJson(str, type);
            } catch (JsonParseException e) { }
		}
		return null;
	}

	public <T> List<T> listFromData(Class<T> clazz) {
		return listFrom(clazz, Const.response_data);
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
