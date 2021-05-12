package it.polito.tdp.borders.model.reachablecountries;

import java.util.Collection;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;

public interface ReachableCountriesStrategy
{
	Collection<Country> computeReachableCountriesFrom(Country country);
	void setGraph(Graph<Country, DefaultEdge> graph);
}
