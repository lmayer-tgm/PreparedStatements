package jdbc.prep_statements;

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
	public DBConnection(String host, String databasename, String username, String password) {
		ds = new PGSimpleDataSource(); // Neue PostgreSQL
										// Datenquelle
		ds.setServerName(host); // IP-Adresse von Debian
		ds.setDatabaseName(databasename); // Datenbank
		ds.setUser(username); // Datenbankuser
		ds.setPassword(password); // Datenbankpasswort
		// Verbindung herstellen
	}
	public void executeQuery(){
		
	}
}