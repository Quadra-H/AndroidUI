package com.ssm.quadrah.diymarket.content;

public class Items {
	private String title;
	private String designer;
	private String tag;
	
	public Items(String title, String designer, String tag){
		this.title = title;
		this.designer = designer;
		this.tag = tag;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getDesigner(){
		return this.designer;
	}
	
	public String getTag(){
		return this.tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDesigner(String designer){
		this.designer = designer;
	}
}
