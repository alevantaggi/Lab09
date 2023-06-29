package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country, DefaultEdge> grafo;
	private List<Country> listaCountry;
	private Map<Integer, Country> countryIdMap;
	private BordersDAO dao;
	
	// Ricorsione
	private Set<Country> statiVisitati;
	

	public Model() {
		this.dao= new BordersDAO();
		this.listaCountry= new ArrayList<>();
		this.countryIdMap= new HashMap<>();
		this.grafo= new SimpleGraph<>(DefaultEdge.class);
		loadCountry();
	}
	
	private void loadCountry(){
		if(this.listaCountry.isEmpty()) 		
			this.listaCountry=dao.loadAllCountries();
		
		if(this.countryIdMap.isEmpty()) {
			for(Country c: this.listaCountry)
				this.countryIdMap.put(c.getIdStato(), c);
		}
	}

	public void creaGrafo(int anno) {
		this.grafo= new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.listaCountry);
		
		List<Border> listaConfini= new ArrayList<>(this.dao.getCountryPairs(anno, this.countryIdMap));
		
		for(Border b: listaConfini) 
			this.grafo.addEdge(b.getFirstCountry(), b.getSecondCountry());		
	}
	
	public List<VertexCar> getAllCharacteristics() {
		List<VertexCar> risultato= new ArrayList<>();
		
		for(Country c: this.grafo.vertexSet()) {
			int count=-1;
			BreadthFirstIterator<Country, DefaultEdge> iteratore= new BreadthFirstIterator<Country, DefaultEdge>(this.grafo, c);
			
			while(iteratore.hasNext()) {
				iteratore.next();
				count++;
			}
			
			if(count!=0 && this.grafo.degreeOf(c)!=0) 
				risultato.add(new VertexCar(c, this.grafo.degreeOf(c), count));

			
		}		
		
		return risultato;
	}

	public List<Country> getListaCountry() {
		return listaCountry;
	}
	
	public List<Country> getAllRaggiungibili(Country paese) {
		List<Country> risultato= new ArrayList<>();
		BreadthFirstIterator<Country, DefaultEdge> visita= new BreadthFirstIterator<Country, DefaultEdge>(this.grafo, paese);
		
		while(visita.hasNext()) 
			risultato.add(visita.next());
	
		return risultato.subList(1, risultato.size());
	}
	
	public Boolean isGrafoPopolato() {
		return (this.grafo.edgeSet().size()>0 && this.grafo.vertexSet().size()>0);
	}
	
	
	public Set<Country> getRaggiungigibiliRicorsione(Country paese){
		this.statiVisitati= new HashSet<>();
		this.statiVisitati.add(paese);
		Ricorsione(paese);
		this.statiVisitati.remove(paese);
		
		return this.statiVisitati;
	}
	
	private Boolean Ricorsione(Country paese) {
		
		if(this.statiVisitati.size()== this.listaCountry.size()) {
			return true;
		}
		
		for(Country c: Graphs.neighborSetOf(this.grafo, paese)) {
			if(!this.statiVisitati.contains(c)) {
				this.statiVisitati.add(c);
				
				if(Ricorsione(c))
					return true;
			}
				
		}
		
		return false;
	}
	 
	
}
