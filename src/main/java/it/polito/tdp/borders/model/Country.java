package it.polito.tdp.borders.model;

import java.util.Objects;

public class Country {
	
	private String abbStato;
	private Integer idStato;
	private String nomeStato;
	
	
	public Country(String abbStato, Integer idStato, String nomeStato) {
		this.abbStato = abbStato;
		this.idStato = idStato;
		this.nomeStato = nomeStato;
	}


	public String getAbbStato() {
		return abbStato;
	}


	public Integer getIdStato() {
		return idStato;
	}


	public String getNomeStato() {
		return nomeStato;
	}


	@Override
	public int hashCode() {
		return Objects.hash(idStato);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return Objects.equals(idStato, other.idStato);
	}


	@Override
	public String toString() {
		return nomeStato;
	}
	
	
	

}
