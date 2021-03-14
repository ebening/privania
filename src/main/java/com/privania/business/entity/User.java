package com.privania.business.entity;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long privadaId;
	private String username;
	private String password;
	public Long getPrivadaId() {
		return privadaId;
	}
	public void setPrivadaId(Long privadaId) {
		this.privadaId = privadaId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
