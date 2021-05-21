package com.liicon.utils.lang;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * IO流处理工具类
 * @version  v0.1  king  2014-11-18  IO流处理
 */
public class IOUtils {

	/**
	 * 从输入流读取数据
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) !=-1 ){
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
}
