package jdbc.prep_statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * Class, representing a DB connection, provides methods for connecting and CRUD
 * operations
 * 
 * @author pkomon, lmayer
 * @version 20160218.2
 */
public class DBConnection {
	private PGSimpleDataSource ds;
	private Connection con;
	
	/**
	 * establishes a new connection
	 * @param host hostname or ip of the host
	 * @param databasename name of the database
	 * @param username username
	 * @param password password
	 */
	public DBConnection(String host, String databasename, String username, String password) {
		ds = new PGSimpleDataSource(); // Neue PostgreSQL
										// Datenquelle
		ds.setServerName(host); // IP-Adresse von Debian
		ds.setDatabaseName(databasename); // Datenbank
		ds.setUser(username); // Datenbankuser
		ds.setPassword(password); // Datenbankpasswort
		// Verbindung herstellen
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * wrapper for prepareStatement method of JDBC's connection class
	 * @param statement
	 * @return
	 */
	public PreparedStatement prepareStatement(String statement){
		try {
			return con.prepareStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO improve error handling...
		}
		return null;
	}
	
}