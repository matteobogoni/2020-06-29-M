package it.polito.tdp.imdb.model;

public class Adiacenza {
	
	Integer idD1;
	Integer idD2;
	Integer peso;
	
	public Adiacenza(Integer idD1, Integer idD2, Integer peso) {
		super();
		this.idD1 = idD1;
		this.idD2 = idD2;
		this.peso = peso;
	}

	public Integer getIdD1() {
		return idD1;
	}

	public void setIdD1(Integer idD1) {
		this.idD1 = idD1;
	}

	public Integer getIdD2() {
		return idD2;
	}

	public void setIdD2(Integer idD2) {
		this.idD2 = idD2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
}
