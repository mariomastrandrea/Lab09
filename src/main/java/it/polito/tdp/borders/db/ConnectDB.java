package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectDB 
{
	private static final String jdbcURL = "jdbc:mariadb://localhost/countries";
	private static final HikariDataSource dataSource;
	private static final String username = "root";
	private static final String password = "root";

	static
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcURL);
		config.setUsername(username);
		config.setPassword(password);
		
		// MySQL config
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		dataSource = new HikariDataSource(config);
	}
	
	public static Connection getConnection() 
	{
		try
		{	
			return dataSource.getConnection();
		}
		catch (SQLException sqle) 
		{
			System.err.println("DB Connection error at: " + jdbcURL);
			throw new RuntimeException("DB Connection error at: " + jdbcURL, sqle);
		}
	}

	public static void close(AutoCloseable... resources) throws SQLException
	{
		for(var resource : resources)
		{
			try
			{
				resource.close();
			}
			catch(Exception e)
			{
				throw new SQLException("Error closing resource " + resource.toString(), e);
			}
		}
	}

}
