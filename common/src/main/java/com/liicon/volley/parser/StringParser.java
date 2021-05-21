package com.liicon.volley.parser;

import com.liicon.json.Json;
import com.liicon.volley.error.JsonParseError;
import com.liicon.volley.error.ParseError;

/**
 * 请求返回的数据解析器
 * @version v0.1 king 2015-03-26
 */
public interface StringParser extends AbsParser<String> {
	
	@Override
	public void parse(String result) throws ParseError;
	
}
