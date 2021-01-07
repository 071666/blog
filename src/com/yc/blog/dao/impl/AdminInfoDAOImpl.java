package com.yc.blog.dao.impl;

import java.util.List;

import com.yc.blog.bean.Admininfo;
import com.yc.blog.dao.DBHelper;
import com.yc.blog.dao.IAdminInfoDAO;

/**
 * 后台管理员数据模型层 实现 SQL语句
 * company 源辰信息
 * @author Admin
 * @data 2020年10月19日
 * Email 1171595422@qq.com
 */
public class AdminInfoDAOImpl implements IAdminInfoDAO{

	@Override
	public Admininfo login(String aname, String pwd) {
		DBHelper db = new DBHelper();
		String sql = "select aid, aname, photo from admininfo where aname = ? and pwd = ?";
		return db.findMultiple(Admininfo.class, sql, aname, pwd);
	}

	@Override
	public int add(String aname, String pwd, String photo) {
		DBHelper db = new DBHelper();
		String sql = "insert into admininfo values(seq_admininfo_aid.nextval, ?, ?, ?)";
		return db.update(sql, aname, pwd, photo);
	}

	@Override
	public List<Admininfo> findAll() {
		DBHelper db = new DBHelper();
		String sql = "select aid, aname, photo from admininfo order by aid desc";
		return db.findMultiples(Admininfo.class, sql, null);
	}

}
