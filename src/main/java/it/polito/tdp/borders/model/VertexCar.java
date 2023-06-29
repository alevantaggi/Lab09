package it.polito.tdp.borders.model;

public class VertexCar {
	
	private Country country;
	private Integer gradoVertcice;
	private Integer componenteConnessa;
		
	public VertexCar(Country country, Integer gradoVertcice, Integer componenteConnessa) {
		this.country = country;
		this.gradoVertcice = gradoVertcice;
		this.componenteConnessa = componenteConnessa;
	}
	
	public Country getCountry() {
		return country;
	}
	public Integer getGradoVertcice() {
		return gradoVertcice;
	}
	public Integer getComponenteConnessa() {
		return componenteConnessa;
	}
	
	

}
