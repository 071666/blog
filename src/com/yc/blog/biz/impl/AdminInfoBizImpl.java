package com.yc.blog.biz.impl;

import java.util.List;

import com.yc.blog.bean.Admininfo;
import com.yc.blog.biz.IAdminInfoBiz;
import com.yc.blog.dao.IAdminInfoDAO;
import com.yc.blog.dao.impl.AdminInfoDAOImpl;
import com.yc.blog.utils.StringUtil;

/**
 * 管理员业务实现接口 company 源辰信息
 * 
 * @author Admin
 * @data 2020年10月19日 Email 1171595422@qq.com
 */
public class AdminInfoBizImpl implements IAdminInfoBiz {

	@Override
	public Admininfo login(String aname, String pwd) {
		// 数据的规范验证
		if (StringUtil.chekcNull(aname, pwd)) {
			return null;
		}
		IAdminInfoDAO dao = new AdminInfoDAOImpl();
		return dao.login(aname, pwd);
	}

	@Override
	public int add(String aname, String pwd, String photo) {
		// 数据的规范验证
		if (StringUtil.chekcNull(aname, pwd)) {
			return -1;
		}
		IAdminInfoDAO dao = new AdminInfoDAOImpl();
		return dao.add(aname, pwd, photo);
	}

	@Override
	public List<Admininfo> findAll() {
		IAdminInfoDAO dao = new AdminInfoDAOImpl();
		return dao.findAll();
	}

}
