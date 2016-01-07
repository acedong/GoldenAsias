package com.goldenasia.lottery.bean;

import java.util.List;

public class AppendUndo {
	private String cellphone;	//":1,
	private String id;			//":1,
	private String[] idsList;//":[1,2]
	
	public AppendUndo() {
		
	}
	public AppendUndo(String cellphone, String id, String[] idsList) {
		this.cellphone = cellphone;
		this.id = id;
		this.idsList = idsList;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getIdsList() {
		return idsList;
	}
	public void setIdsList(String[] idsList) {
		this.idsList = idsList;
	}

}
