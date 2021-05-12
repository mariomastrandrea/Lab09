package it.polito.tdp.borders.model;

public class Country 
{
	private final String abbreviation;
	private final int code;
	private final String name;
	
	
	public Country(String abbreviation, int code, String name)
	{
		this.abbreviation = abbreviation;
		this.code = code;
		this.name = name;
	}

	public String getAbbreviation()
	{
		return this.abbreviation;
	}

	public int getCode()
	{
		return this.code;
	}

	public String getName()
	{
		return this.name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
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
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
}
