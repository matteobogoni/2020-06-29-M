package it.polito.tdp.imdb.model;

public class Vicino implements Comparable<Vicino> {
	
	Integer idD;
	Integer peso;
	public Vicino(Integer idD, Integer peso) {
		super();
		this.idD = idD;
		this.peso = peso;
	}
	public Integer getIdD() {
		return idD;
	}
	public void setIdD(Integer idD) {
		this.idD = idD;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Vicino other) {
		
		return -(this.getPeso()-other.getPeso());
	}
	
	
}
