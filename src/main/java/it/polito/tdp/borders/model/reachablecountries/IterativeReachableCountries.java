package it.polito.tdp.borders.model.reachablecountries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;

public class IterativeReachableCountries implements ReachableCountriesStrategy
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
		
		List<Country> countriesToBeVisited = new ArrayList<>();
		countriesToBeVisited.add(country);
		
		Set<Country> visitedCountries = new HashSet<>();
			
		while(!countriesToBeVisited.isEmpty())
		{
			Country c = countriesToBeVisited.get(0);
			
			if(!visitedCountries.contains(c))
			{
				visitedCountries.add(c);
				
				Collection<Country> neighborhood = Graphs.neighborSetOf(this.graph, c);
				countriesToBeVisited.addAll(neighborhood);
			}
			
			countriesToBeVisited.remove(0);
		}
		
		return visitedCountries;
	}
}
