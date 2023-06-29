package it.polito.tdp.borders.model;

import java.util.Objects;

public class Border {
	
	private Country firstCountry;
	private Country secondCountry;
	private Integer anno;
	
	public Border(Country firstCountry, Country secondCountry, Integer anno) {
		this.firstCountry = firstCountry;
		this.secondCountry = secondCountry;
		this.anno = anno;
	}

	public Country getFirstCountry() {
		return firstCountry;
	}

	public Country getSecondCountry() {
		return secondCountry;
	}

	public Integer getAnno() {
		return anno;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstCountry, secondCountry);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		return Objects.equals(firstCountry, other.firstCountry) && Objects.equals(secondCountry, other.secondCountry);
	}

	@Override
	public String toString() {
		return "Border [firstCountry=" + firstCountry + ", secondCountry=" + secondCountry + ", anno=" + anno + "]";
	}
	
	
	
	

}
