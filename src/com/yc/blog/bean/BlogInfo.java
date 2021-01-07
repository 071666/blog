package com.yc.blog.bean;

import java.io.Serializable;

public class BlogInfo  implements Serializable{

	private static final long serialVersionUID = -5367276439262442783L;
	private Integer bid;
	private Integer aid;
	private Integer tid;
	private String title;//博文标题
	private String content;//博文正文
	private String bdate;//发布时间
	private Integer collect;//收藏量
	private Integer support;//点赞量
	private Integer oppose;//反对量
	
	//博客涉及多表故在类中增加属性  用于多表查询
	private String aname;//博文发布者姓名
	private String tname;//博文类型
	private String photo;//博文发表图像
	@Override
	public String toString() {
		return "BlogInfo [bid=" + bid + ", aid=" + aid + ", tid=" + tid + ", title=" + title + ", content=" + content
				+ ", bdate=" + bdate + ", collect=" + collect + ", support=" + support + ", oppose=" + oppose
				+ ", aname=" + aname + ", tname=" + tname + ", photo=" + photo + "]";
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public Integer getCollect() {
		return collect;
	}
	public void setCollect(Integer collect) {
		this.collect = collect;
	}
	public Integer getSupport() {
		return support;
	}
	public void setSupport(Integer support) {
		this.support = support;
	}
	public Integer getOppose() {
		return oppose;
	}
	public void setOppose(Integer oppose) {
		this.oppose = oppose;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
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
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		result = prime * result + ((bdate == null) ? 0 : bdate.hashCode());
		result = prime * result + ((bid == null) ? 0 : bid.hashCode());
		result = prime * result + ((collect == null) ? 0 : collect.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((oppose == null) ? 0 : oppose.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((support == null) ? 0 : support.hashCode());
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		BlogInfo other = (BlogInfo) obj;
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
		if (bdate == null) {
			if (other.bdate != null)
				return false;
		} else if (!bdate.equals(other.bdate))
			return false;
		if (bid == null) {
			if (other.bid != null)
				return false;
		} else if (!bid.equals(other.bid))
			return false;
		if (collect == null) {
			if (other.collect != null)
				return false;
		} else if (!collect.equals(other.collect))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (oppose == null) {
			if (other.oppose != null)
				return false;
		} else if (!oppose.equals(other.oppose))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (support == null) {
			if (other.support != null)
				return false;
		} else if (!support.equals(other.support))
			return false;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}
	public BlogInfo(Integer bid, Integer aid, Integer tid, String title, String content, String bdate, Integer collect,
			Integer support, Integer oppose, String aname, String tname, String photo) {
		super();
		this.bid = bid;
		this.aid = aid;
		this.tid = tid;
		this.title = title;
		this.content = content;
		this.bdate = bdate;
		this.collect = collect;
		this.support = support;
		this.oppose = oppose;
		this.aname = aname;
		this.tname = tname;
		this.photo = photo;
	}
	public BlogInfo() {
		super();
	}
	
	
}
