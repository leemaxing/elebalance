package com.liicon.volley.parser;

import com.liicon.json.Json;
import com.liicon.volley.error.JsonParseError;

/**
 * 请求返回的数据解析器
 * @version v0.1 king 2014-10-20
 */
public interface JsonParser<T extends Json> extends AbsParser<T> {
	
	@Override
	public void parse(T result) throws JsonParseError;
	
}
