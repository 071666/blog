package com.yc.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.blog.bean.Admininfo;
import com.yc.blog.biz.IAdminInfoBiz;
import com.yc.blog.biz.impl.AdminInfoBizImpl;

/**
 * 管理员业务控制层 调用业务层方法 company 源辰信息
 * 
 * @author Admin
 * @data 2020年10月20日 Email 1171595422@qq.com
 */
@WebServlet("/admin")
public class AdminInfoServlet extends BaseServlet {

	private static final long serialVersionUID = -237501900349622953L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取前端发送的操作指令
		String op = req.getParameter("op");
		System.out.println(op);
		// 操作方法
		if ("login".equals(op)) {// 登录操作
			login(req, resp);
		} else if ("findAll".equals(op)) {// 查询所有
			findAll(req, resp);
		} else if ("info".equals(op)) {// 获取登录信息
			info(req, resp);
		} else if ("add".equals(op)) {// 添加管理员
			add(req, resp);
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 获取当前登录管理员信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object obj = req.getSession().getAttribute("admin");//在登录成功的时候存储信息
		if(obj == null) {//当前未登录
			this.send(resp, 500,null);
			return;
		}
		this.send(resp, 200, obj);
	}

	/**
	 * 查询所有管理员信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IAdminInfoBiz biz = new AdminInfoBizImpl();
		List<Admininfo> list = biz.findAll();
		
		if(list != null && !list.isEmpty()) {//查询所有数据
			this.send(resp, 200, list);
			return;
		}
		this.send(resp, 500, null);
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String aname = req.getParameter("aname");
		String pwd = req.getParameter("pwd");
		//调用业务层方法
		IAdminInfoBiz biz = new AdminInfoBizImpl();
		Admininfo admin = biz.login(aname, pwd);//登录用户
		
		/*PrintWriter out = resp.getWriter();
		//判断
		if(admin == null) {//登录失败
			out.print("500");
			return;
		}
		//登录成功  存入session  追踪用户
		req.getSession().setAttribute("admin", admin);
		out.print("200");*/
		//判断
		if(admin == null) {//登陆失败
			this.send(resp, 500);
			return;
		}
		//登录成功  存入session  追踪用户
		req.getSession().setAttribute("admin", admin);
		this.send(resp, 200);
	}
}
