package com.yc.blog.biz;

import java.util.List;

import com.yc.blog.bean.Admininfo;

public interface IAdminInfoBiz {
	/**
	 * 后台管理员登录
	 * 
	 * @param aname 账号
	 * @param pwd   密码
	 * @return
	 */
	public Admininfo login(String aname, String pwd);

	/**
	 * 添加后台管理员
	 * 
	 * @param aname
	 * @param pwd
	 * @param photo
	 * @return
	 */
	public int add(String aname, String pwd, String photo);

	/**
	 * 查询所有的管理员信息
	 * 
	 * @return
	 */
	public List<Admininfo> findAll();
}
