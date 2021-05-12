package it.polito.tdp.borders.model.reachablecountries;

import java.util.Collection;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;

public class ConnInpectorReachableCountries implements ReachableCountriesStrategy
{
	private Graph<Country, DefaultEdge> graph;
	
	@Override
	public void setGraph(Graph<Country, DefaultEdge> graph)
	{
		this.graph = graph;
	}

	@Override
	public Collection<Country> computeReachableCountriesFrom(Country country)
	{
		if(this.graph == null)
			throw new RuntimeException("Error: graph not created");
		
		if(!this.graph.containsVertex(country))
			throw new RuntimeException("Error: the graph doesn't contain a vertex like " + country);
		
		ConnectivityInspector<Country, DefaultEdge> inspector = 
									new ConnectivityInspector<>(this.graph);
		
		Collection<Country> reachableCountries = inspector.connectedSetOf(country);
		
		return reachableCountries;
	}
}
