package com.yc.blog.dao;

import java.util.List;

import com.yc.blog.bean.Admininfo;

/**
 * 管理员数据库的操作接口 软件 升级维修  协同合作
 * company 源辰信息
 * @author Admin
 * @data 2020年10月19日
 * Email 1171595422@qq.com
 */
public interface IAdminInfoDAO {
	
	/**
	 * 后台管理员登录
	 * @param aname 账号
	 * @param pwd 密码
	 * @return
	 */
	public Admininfo login(String aname, String pwd);
	/**
	 * 添加后台管理员
	 * @param aname
	 * @param pwd
	 * @param photo
	 * @return
	 */
	public int add(String aname, String pwd, String photo);
	/**
	 * 查询所有的管理员信息
	 * @return
	 */
	public List<Admininfo> findAll();
}