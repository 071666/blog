package com.yc.blog.utils;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 文件上传的工具类
 * 
 * @author Admin
 *
 */
public class FileUploadUtil {
	public static String uploadPath = "../images";// 默认上传的文件路径
	private static final String ALLOWEDLIST = "gif,jpg,jpeg,png";// 允许上传的文件类型
//	private static final String DENIEDLIST = "gif,jpg,jpeg";//不允许上传的文件类型
	private static final int MAXFILESIZE = 10 * 1024 * 1024;// 单个上传文件的最大值
	private static final int TOTALMAXFILESIZE = 100 * 1024 * 1024;// 最大总上传文件的最大值

	/**
	 * 文件上传 单个文件
	 * 
	 * @param pagecontext 页面上文件
	 * @return map 网页数据 包含 文件信息
	 * @throws ServletException
	 * @throws ServerException
	 * @throws IOException
	 * @throws SmartUploadException
	 */
	public Map<String, String> uploads(PageContext pagecontext)
			throws ServletException, IOException, SmartUploadException {
		Map<String, String> map = new HashMap<String, String>();
		SmartUpload su = new SmartUpload();
		// 初始化上传组件
		su.initialize(pagecontext);
		// 设置参数
		su.setMaxFileSize(MAXFILESIZE);
		su.setTotalMaxFileSize(TOTALMAXFILESIZE);
		su.setAllowedFilesList(ALLOWEDLIST);
//		su.setDeniedFilesList(DENIEDLIST);
		su.setCharset("utf-8");// 上传数据的编码集
		su.upload();

		// 获取上传的参数信息 非文件参数 注意此时的request是jspsmartupload中的而不是Servlet中的
		Request rq = su.getRequest();
		// 获取参数 枚举 获取所有表单控件的名字
		Enumeration<String> enus = rq.getParameterNames();

		// 单个控件的名称 name属性
		String name = null;
		while (enus.hasMoreElements()) {
			name = enus.nextElement();
			map.put(name, rq.getParameter(name));
		}
//		return map;//此时获取的所有的普通文本信息

		// TODO 思考问题
		/*
		 * 1、如何上传文件 2、文件存在服务器哪个位置 3、文件如何写入指定的位置 4、获取存储后的文件路径 如何处理 5、如果服务器指定的位置有多张相同名称图片
		 * 如何处理？
		 */
		// 处理上次文件
		Files files = su.getFiles();
		// 如果无文件 就直接返回普通的文本信息
		if (files == null && files.getCount() <= 0) {
			// 说明用户未上传文件
			return map;
		}
		// 有 则循环取出用户上传的文件
		Collection<File> fls = files.getCollection();

		// TODO 文件存储服务器的详情位置
		// 获取保存文件夹的绝对路径 -> Tomcat在服务器的绝对路径 C...\webapps\项目\

		String basePath = pagecontext.getServletContext().getRealPath("/");
		// System.out.println(basePath);//D:\Program Files
		// (x86)\Tomcat\apache-tomcat-9.0.34\webapps\web5\

		String fileName = null;// 上传后文件名称
		String fieldName = null;// 文件空间名
		String filePath = null;// 文件保存路径

		String pathStr = "";// 多文件拼接路径
		String temp = null;// 网页文件中文件上传的控件名

		for (File fl : fls) {// 遍历文件
			if (!fl.isMissing()) {// 文件未丢失

				temp = fl.getFieldName();// photo files 页面多个文件域控件
				if (StringUtil.chekcNull(fieldName)) {// 如果这个变量是空 说明这是第一次要上传的文件
					fieldName = temp;
				} else {// 否则 是其他的文件域控件
					if (!temp.equals(fieldName)) {// 说明是另外的文件域
						map.put(fieldName, pathStr);// 首先需要将第一个文件域的内容
						pathStr = "";// 初始化清空上一个上传路径 准备放下一个文件上传路径
						fieldName = temp;
					}
				}
				// 为了避免文件名相同出现文件覆盖 所以自定义 上传文件的名称
				fieldName = fl.getFieldName();
				fileName = uploadPath + "/" + new Date().getTime() + "_" + fl.getFileName();

				if (StringUtil.chekcNull(pathStr)) {// 说明是第一个要上传的文件
					pathStr = fieldName;
				} else {
					pathStr += ";" + fileName;// 多图片 字符串拼接
				}
				// 存储在服务器中指定的目录 获取上传路径 自定义路径 + 自定义的名称
				filePath = basePath + fileName;
				// 存储文件
				System.out.println(filePath);
				fl.saveAs(filePath, SmartUpload.SAVE_PHYSICAL);

				// 现象 保存在服务器项目目录下 不能永久保存 服务器重启消失
				// TODO 如何永久存储图片信息 不因服务器重启而消失 二进制 存入数据库
				// 方案 webapps/项目/images -> webapps/images -> 在默认路径上面加上../

				// TODO 1、如果是一个文件域中有多个文件如何处理？ --> 提示：字符串拼接1.png 2.png
				// TODO 2、如果是多个文件域，每一个文件域可能有多个文件又该如何处理？
			}
		}
		map.put(fieldName, fileName);
		return map;
	}
}
