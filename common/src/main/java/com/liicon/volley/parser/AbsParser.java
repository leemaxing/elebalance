package com.liicon.volley.parser;

import com.liicon.volley.error.ParseError;

/**
 * 请求返回的数据解析器
 * @version v0.1 king 2014-10-20
 */
public interface AbsParser<T> {

	/**
	 * 解析数据
	 * @param result
	 * @throws com.liicon.volley.error.ParseError
	 */
	public void parse(T result) throws ParseError;
	
}
