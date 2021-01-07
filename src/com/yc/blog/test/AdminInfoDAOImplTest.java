package com.yc.blog.test;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yc.blog.bean.Admininfo;
import com.yc.blog.dao.IAdminInfoDAO;
import com.yc.blog.dao.impl.AdminInfoDAOImpl;

/**
 * 后台管理员测试类
 * company 源辰信息
 * @author Admin
 * @data 2020年10月20日
 * Email 1171595422@qq.com
 */
public class AdminInfoDAOImplTest {

	IAdminInfoDAO dao = new AdminInfoDAOImpl();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogin() {
//		System.out.println(dao.login("andy", "123456"));
		Admininfo admin = dao.login("andy", "123456");
		System.out.println(admin);
		Gson gson = new Gson();//属性值是null  会被省略掉了
		System.out.println(gson.toJson( admin));
		//使用了serializeNulls()之后，就会出现值为null的属性名，方便我们后期工作调试
		Gson gson2 = new GsonBuilder().serializeNulls().create();//方便调试
		System.out.println(gson2.toJson(admin));
	}

	@Test
	public void testAdd() {
		System.out.println(dao.add("yc", "a", "images/user.png"));
	}

	@Test
	public void testFindAll() {
		System.out.println(dao.findAll());
	}

}
