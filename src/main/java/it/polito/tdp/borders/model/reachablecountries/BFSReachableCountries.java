package it.polito.tdp.borders.model.reachablecountries;

import java.util.Collection;
import java.util.HashSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.model.Country;

public class BFSReachableCountries implements ReachableCountriesStrategy
{
	private Graph<Country, DefaultEdge> graph;
	
	@Override
	public void setGraph(Graph<Country,DefaultEdge> graph) 
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
			
		BreadthFirstIterator<Country, DefaultEdge> iterator =
										new BreadthFirstIterator<>(this.graph, country);
		
		Collection<Country> reachableCountries = new HashSet<>();
		
		while(iterator.hasNext())
		{
			Country nextCountry = iterator.next();
			reachableCountries.add(nextCountry);
		}

		return reachableCountries;
	}
}
