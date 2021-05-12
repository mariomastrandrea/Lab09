package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import it.polito.tdp.borders.model.Contiguity;
import java.util.Collection;
import java.util.HashSet;

import it.polito.tdp.borders.model.Country;

public class BordersDAO 
{
	public Collection<Country> getCountriesConnectedUntil(int year, Map<Integer, Country> countriesIdMap)
	{
		String sqlQuery = String.format("%s %s %s %s",
						"SELECT DISTINCT c1.stateAbb AS abbreviation, c1.CCode AS ccode, c1.StateNme AS name",
						"FROM country c1, contiguity c2",
						"WHERE conttype = 1 AND (ccode = c2.state1no OR ccode = c2.state2no)",
						"AND c2.year <= ?"); 
		
		Collection<Country> countries = new HashSet<>();
		
		try
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, year);
			ResultSet queryResult = statement.executeQuery();
			
			Country c;
			while(queryResult.next())
			{
				int code = queryResult.getInt("ccode");
				
				if(countriesIdMap.containsKey(code))
					c = countriesIdMap.get(code);
				else
				{	//create a new country
					String abbreviation = queryResult.getString("abbreviation");
					String name = queryResult.getString("name");
					c = new Country(abbreviation, code, name);
					countriesIdMap.put(code, c);
				}
				
				countries.add(c);
			}
			
			ConnectDB.close(queryResult, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("DAO Error at getCountriesConnectedUntil()", sqle);
		}
		
		return countries;
	}

	public Collection<Contiguity> getContiguities(int year, 
			Map<Integer, Country> countriesIdMap, Map<String, Contiguity> contiguitiesIdMap)
	{
		String sqlQuery = String.format("%s %s %s %s %s %s %s",
								"SELECT state1no AS state1, state2no AS state2, year",
								"FROM contiguity",
								"WHERE conttype = 1 AND year <= ?",
								"UNION",
								"SELECT state2no, state1no, year",
								"FROM contiguity",
								"WHERE conttype = 1 AND year <= ?");
		
		Collection<Contiguity> contiguities = new HashSet<>();
		
		try
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, year);
			statement.setInt(2, year);
			ResultSet queryResult = statement.executeQuery();
			
			while(queryResult.next())
			{
				int code1 = queryResult.getInt("state1");
				int code2 = queryResult.getInt("state2");
				
				String newId = String.format("%04d%04d", code1, code2);
				Contiguity newContiguity;
				
				if(contiguitiesIdMap.containsKey(newId))
					newContiguity = contiguitiesIdMap.get(newId);
				else
				{
					Country c1 = countriesIdMap.get(code1);
					Country c2 = countriesIdMap.get(code2);
					
					if(c1 == null || c2 == null)
						throw new RuntimeException("ERROR IN DB");
					
					int y = queryResult.getInt("year");
					
					newContiguity = new Contiguity(newId, c1, c2, y);
					contiguitiesIdMap.put(newId, newContiguity);
				}
				
				contiguities.add(newContiguity);
			}
			
			ConnectDB.close(queryResult, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("DAO Error at getContiguities()", sqle);
		}
		
		return contiguities;
	}
}













