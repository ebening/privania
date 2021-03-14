package com.privania.business.entity;

import java.io.Serializable;

public class Privada implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long privadaId;
	private String nombre;
	
	public Long getPrivadaId() {
		return privadaId;
	}
	public void setPrivadaId(Long privadaId) {
		this.privadaId = privadaId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
