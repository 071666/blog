package com.yc.blog.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yc.blog.utils.FileUploadUtil;
import com.yc.blog.utils.StringUtil;

/**
 * 文件上传路径的监听器	注解
 * @author Admin
 *
 */
@WebListener
public class ApplicationLinstener implements ServletContextListener
{
	private String path = "photos";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("我进入了监听器中。。。");
		//获取初始化参数
		String temp = sce.getServletContext().getInitParameter("uploadPath");//application
		System.out.println(temp);
		//判断
		if(!StringUtil.chekcNull(temp)) {
			path = temp;
		}
		
		//判断路径是否存在，如果存在就不管了，否则需要创建
		String bathPath = sce.getServletContext().getRealPath("/");//D:\Program Files (x86)\Tomcat\apache-tomcat-9.0.34\webapps\web5\
		System.out.println(bathPath);
		File fl = new File(bathPath, path);
		//判断
		if(!fl.exists()) {
			fl.mkdirs();
		}
		//修改文件上传工具类FileUploadUtil2中默认路径
		FileUploadUtil.uploadPath = path;
	}	
}
