package com.yc.blog.bean;

import java.io.Serializable;

/**
 * 博客类型
 * company 源辰信息
 * @author Admin
 * @data 2020年10月19日
 * Email 1171595422@qq.com
 */
public class BlogType implements Serializable{

	private static final long serialVersionUID = -3899545116057580111L;
	private Integer tid;//类型编号
	private String tname;//类型名称
	private Integer status;//类型状态
	@Override
	public String toString() {
		return "BlogType [tid=" + tid + ", tname=" + tname + ", status=" + status + "]";
	}
	public BlogType() {
		super();
	}
	public BlogType(Integer tid, String tname, Integer status) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
		result = prime * result + ((tname == null) ? 0 : tname.hashCode());
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
		BlogType other = (BlogType) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
