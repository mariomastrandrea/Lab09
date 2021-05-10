package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO 
{
	public List<Country> loadAllCountries() 
	{
		String sqlQuery = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try 
		{
			Connection connection = ConnectDB.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet queryResult = statement.executeQuery();

			while (queryResult.next()) 
			{
				System.out.format("%d %s %s\n", queryResult.getInt("ccode"), 
						queryResult.getString("StateAbb"), queryResult.getString("StateNme"));
			}
			
			queryResult.close();
			statement.close();
			connection.close();
			return result;
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) 
	{
		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}
}
