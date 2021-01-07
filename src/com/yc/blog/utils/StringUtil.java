package com.yc.blog.utils;

/**
 * 字符串工具类
 * @author 38929
 *
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @param strs
	 * @return
	 */
	public static boolean chekcNull(String ... strs) {
		if( strs == null || strs.length <= 0) {
			return true;
		}
		for(String str : strs) {
			if( str == null || "".equals(str)) {
				return true;
			}
		}
		return false;
	}
}
