package com.yc.blog.bean;

import java.io.Serializable;

/**
 * 博客会员
 * company 源辰信息
 * @author Admin
 * @data 2020年10月19日
 * Email 1171595422@qq.com
 */
public class MemberInfo implements Serializable{
	
	private static final long serialVersionUID = -8114090528463647353L;
	private Integer mid;//会员编号
	private String nickName;//会员昵称
	private String pwd;//会员密码
	private String email;//会员邮箱
	private String photo;//个人头像
	@Override
	public String toString() {
		return "MemberInfo [mid=" + mid + ", nickName=" + nickName + ", pwd=" + pwd + ", email=" + email + ", photo="
				+ photo + "]";
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
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
		MemberInfo other = (MemberInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
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
	public MemberInfo(Integer mid, String nickName, String pwd, String email, String photo) {
		super();
		this.mid = mid;
		this.nickName = nickName;
		this.pwd = pwd;
		this.email = email;
		this.photo = photo;
	}
	public MemberInfo() {
		super();
	}
	
}
