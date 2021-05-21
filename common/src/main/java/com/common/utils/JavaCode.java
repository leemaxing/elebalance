package com.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaCode {
	private int totle = 0; // 总行数
	private int source = 0; // 代码行数
	private int blank = 0; // 空白行数
	private int comments = 0; // 注释行数

	/**
	 * 读取文件夹内java文件
	 * @param dir
	 */
	public JavaCode(File javaFile) {
		super();

		try {
			if (javaFile.getName().endsWith(".java")) {
				javaLine(javaFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取java文件总行数，代码行数，空白行数，注释行数
	 * 
	 * @param f
	 * @throws java.io.IOException
	 * @throws java.io.FileNotFoundException
	 */
	private void javaLine(File f) throws FileNotFoundException,
			IOException {
		String strLine = "";
		String str = readFile(f);
		if (str.length() > 0) {
			while (str.indexOf('\n') != -1) {
				totle++;
				// System.out.println(totle);
				strLine = str.substring(0, str.indexOf('\n')).trim();
				// str = str.substring(str.indexOf('\n') + 1, str.length());
				if (strLine.length() == 0) {
					blank++;
				} else if (strLine.charAt(0) == '*' || strLine.charAt(0) == '/') {
					comments++;
				} else {
					source++;
					String regEx = "^*//";
					if (regEx(strLine, regEx)) {
						comments++;
					}
				}
				str = str.substring(str.indexOf('\n') + 1, str.length());
			}
		}
	}

	/**
	 * 将java文件以字符数组形式读取
	 * 
	 * @param f
	 * @return
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	private String readFile(File f) throws FileNotFoundException,
			IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[(int) f.length()];
		fis.read(b);
		fis.close();
		return new String(b);
	}

	/**
	 * 正则匹配
	 * 
	 * @param str
	 *            输入字符串
	 * @param regEx
	 *            正则匹配字符串
	 * @return
	 */
	private boolean regEx(String str, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		boolean result = m.find();
		return result;
	}

	
	
	
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		// File root = new File(PROJECT_DIR);
		File directory = new File("");// 参数为空
		// 获取项目路径
		String projectFile = directory.getCanonicalPath();
		System.out.println(projectFile + "===============");
		
		JavaCode javaCode = new JavaCode(new File(projectFile));
		System.out.println(javaCode.totle + 1);
		System.out.println(javaCode.source);
		System.out.println(javaCode.blank);
		System.out.println(javaCode.comments);
	}
}
