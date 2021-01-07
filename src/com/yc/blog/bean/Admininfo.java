package com.yc.blog.bean;

import java.io.Serializable;

/**
 * 管理员  实现序列号接口  强烈建议属性名和对应数据库表的列表一致
 * company 源辰信息
 * @author Admin
 * @data 2020年10月19日
 * Email 1171595422@qq.com
 */
public class Admininfo implements Serializable{
	
	private static final long serialVersionUID = 7147123903289121774L;
	private Integer aid;//管理员编号
	private String aname;//管理员昵称
	private String pwd;//管理员密码
	private String photo;//个人头像
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "Admininfo [aid=" + aid + ", aname=" + aname + ", pwd=" + pwd + ", photo=" + photo + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admininfo other = (Admininfo) obj;
		if (aid == null) {
			if (other.aid != null)
				return false;
		} else if (!aid.equals(other.aid))
			return false;
		if (aname == null) {
			if (other.aname != null)
				return false;
		} else if (!aname.equals(other.aname))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		return true;
	}
	public Admininfo(Integer aid, String aname, String pwd, String photo) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.pwd = pwd;
		this.photo = photo;
	}
	public Admininfo() {
		super();
	}
	
	
}
