package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;
import it.polito.tdp.borders.model.reachablecountries.ReachableCountriesStrategy;

public class CountriesModel 
{
	private BordersDAO dao;
	private Graph<Country, DefaultEdge> graph;
	private Map<Integer, Country> countriesIdMap;
	private Map<String, Contiguity> contiguitiesIdMap;
	private ReachableCountriesStrategy searchStrategy;
	
	
	public CountriesModel(ReachableCountriesStrategy searchStrategy) 
	{
		this.dao = new BordersDAO();
		this.countriesIdMap = new HashMap<>();
		this.contiguitiesIdMap = new HashMap<>();
		this.searchStrategy = searchStrategy;
	}

	public void createCountriesGraphUntil(int year)
	{
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.searchStrategy.setGraph(this.graph);
		
		// add vertices
		Collection<Country> vertices = this.dao.getCountriesConnectedUntil(year, countriesIdMap);
		Graphs.addAllVertices(this.graph, vertices);
		
		// add edges
		Collection<Contiguity> contiguities = this.dao.getContiguities(year, countriesIdMap, contiguitiesIdMap);
		
		for(Contiguity c : contiguities)
		{
			Country c1 = c.getCountry1();
			Country c2 = c.getCountry2();
			
			if(this.graph.containsVertex(c1) && this.graph.containsVertex(c2))
				this.graph.addEdge(c1, c2);
		}
	}

	public Map<Country, Integer> getCountryContiguities()
	{
		if(this.graph == null)
			return null;
		
		Map<Country,Integer> countryContiguities = new HashMap<>();
		
		for(Country country : this.graph.vertexSet())
		{
			int numNeighboringCountries = this.graph.degreeOf(country);
			countryContiguities.put(country,numNeighboringCountries);
		}
		
		return countryContiguities;
	}

	public int computeConnectedComponents()
	{
		ConnectivityInspector<Country, DefaultEdge> inspector = 
									new ConnectivityInspector<>(this.graph);
		
		List<Set<Country>> connectedComponents = inspector.connectedSets();
		
		return connectedComponents.size();
	}

	public int getNumEdges()
	{
		if(this.graph == null)
			return 0;
		
		return this.graph.edgeSet().size();
	}

	public Collection<Country> computeReachableCountriesFrom(Country selectedCountry)
	{
		return this.searchStrategy.computeReachableCountriesFrom(selectedCountry);
	}

}
