package it.polito.tdp.borders.model;

public class Contiguity 
{
	private final Country country1;
	private final Country country2;
	private final int year;
	
	
	public Contiguity(Country country1, Country country2, int year)
	{
		this.country1 = country1;
		this.country2 = country2;
		this.year = year;
	}

	public Country getCountry1()
	{
		return this.country1;
	}

	public Country getCountry2()
	{
		return this.country2;
	}

	public int getYear()
	{
		return this.year;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country1 == null) ? 0 : country1.hashCode());
		result = prime * result + ((country2 == null) ? 0 : country2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contiguity other = (Contiguity) obj;
		if (country1 == null)
		{
			if (other.country1 != null)
				return false;
		}
		else
			if (!country1.equals(other.country1))
				return false;
		if (country2 == null)
		{
			if (other.country2 != null)
				return false;
		}
		else
			if (!country2.equals(other.country2))
				return false;
		return true;
	}
	
	
	
}
