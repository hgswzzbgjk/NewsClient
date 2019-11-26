package com.dgpt.newsclient.bean;

public class NewsInfo {
	private String iconPath;      //图片了路径
	private String title ;        //新闻标题
	private String description; //新闻描述
	private int type;             //新闻类型
	private long comment;        //新闻评论数
	public String getIconPath() {

		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getComment() {
		return comment;
	}
	public void setComment(long comment) {
		this.comment = comment;
	}
}



