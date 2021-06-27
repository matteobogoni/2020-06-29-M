package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	ImdbDAO dao;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private List<Director> percorsomigliore;
	int maxPeso;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<Integer, Director>();
		this.dao.listAllDirectors(idMap);
	}
	
	public void creaGrafo (int year) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getDirectorYear(idMap, year));
		
		//aggiungo gli archi
		for(Adiacenza a : this.dao.getAdiacenza(year)) {
			if(this.grafo.containsVertex(idMap.get(a.getIdD1())) && this.grafo.containsVertex(idMap.get(a.getIdD2())))
			Graphs.addEdgeWithVertices(this.grafo, idMap.get(a.getIdD1()), idMap.get(a.getIdD2()), a.getPeso());
		}
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Director> getGrafoDirector(int anno){
		return dao.getDirectorYear(idMap, anno);
	}
	
	public List<Vicino> getVicini (Director d){
		
		//trov o tutti i nodi vicini a d
		List<Director> vicini = Graphs.neighborListOf(this.grafo, d);
		
		//ritorno i vicini in modo da poter mettere anche il peso
		List<Vicino> result = new ArrayList<>();
		
		for(Director di : vicini) {
			result.add(new Vicino(di.getId(), (int) this.grafo.getEdgeWeight(this.grafo.getEdge(d, di))));
		}
		
		Collections.sort(result);
		return result;
	}
	
	public List<Director> getPercorso(int c, Director d){
		
		this.percorsomigliore = new ArrayList<>();
		this.maxPeso = 0;
		List<Director> parziale = new ArrayList<>();
		
		parziale.add(d);
		cerca(c, parziale);
		return percorsomigliore;
	}
	
	private void cerca (int c, List<Director> parziale) {
		
		Director ultimo = parziale.get(parziale.size()-1);
		List<Director> listaprova;
		
		//caso terminale
		if(calcolacosto(parziale) > this.maxPeso) {
			if(parziale.size() > this.percorsomigliore.size()) {
				this.maxPeso = this.calcolacosto(parziale);
				this.percorsomigliore = new ArrayList<>(parziale);
				return;
			}else {
				return;
			}
		}
		
		for(Director d : Graphs.neighborListOf(this.grafo, ultimo)) {
			if(!parziale.contains(d)) {
				listaprova = new ArrayList<>(parziale);
				listaprova.add(d);
				if(calcolacosto(listaprova) <= c) {
					parziale.add(d);
					cerca(c,parziale);
					parziale.remove(d);
				}
			}
		}
		
	}
	
	private int calcolacosto(List<Director> parziale) {
		
		int pesototale = 0;
		for(int i=1; i<parziale.size(); i++) {
			Director d1 = parziale.get(i);
			Director d2 = parziale.get(i-1);
			
			pesototale += this.grafo.getEdgeWeight(this.grafo.getEdge(d1, d2));
		}
		return pesototale;
	}
	
	public int getPeso() {
		return this.maxPeso;
	}
}
