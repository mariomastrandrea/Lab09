package it.polito.tdp.borders.model.reachablecountries;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;

public class RecursiveReachableCountries implements ReachableCountriesStrategy
{
	private Graph<Country, DefaultEdge> graph;
	private Set<Country> allReachableCountries;
	
	
	public RecursiveReachableCountries()
	{
		this.allReachableCountries = new HashSet<>();
	}
	
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
		
		this.allReachableCountries.clear();
		this.allReachableCountries.add(country);
		
		this.recursiveComputation(country);
		
		return this.allReachableCountries;
	}
	
	private void recursiveComputation(Country lastCountry)
	{
		Set<Country> neighborhood = Graphs.neighborSetOf(this.graph, lastCountry);
		
		for(Country c : neighborhood)
		{
			if(!this.allReachableCountries.contains(c))
			{
				this.allReachableCountries.add(c);
				this.recursiveComputation(c);
			}
		}
		
	}
}
