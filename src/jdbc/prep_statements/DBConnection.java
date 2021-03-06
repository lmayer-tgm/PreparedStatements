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
 * @version 20160229.1
 */
public class DBConnection {
	private Connection con;
	
	/**
	 * Constructor for easy dependency injection (ONLY USED FOR TESTING)
	 * (... so I really need to modify class only for testing it ...?)
	 * @param ds the data source to set the data source field
	 * @param con the connection to set the connection field
	 */
	public DBConnection(Connection con){
		this.con = con;
	}
	
	/**
	 * establishes a new connection
	 * @param host hostname or ip of the host
	 * @param databasename name of the database
	 * @param username username
	 * @param password password
	 */
	public DBConnection(String host, String databasename, String username, String password, int port) {
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(host);
		ds.setDatabaseName(databasename);
		ds.setUser(username);
		ds.setPassword(password);
		ds.setPortNumber(port);
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			System.err.println("Error while establishing connection: ");
			e.printStackTrace();
			cleanup();
			System.exit(-1);
		}
	}
	
	/**
	 * wrapper for prepareStatement method of JDBC's connection class
	 * @param statement the sql statement, used for the prepared statements
	 * @return if an error occurs, the connection is closed, an error printed to stderr and the program exited
	 */
	public PreparedStatement prepareStatement(String statement){
		try {
			return con.prepareStatement(statement);
		} catch (SQLException e) {
			System.err.println("Error while preparing statement:");
			e.printStackTrace();
			cleanup();
			System.exit(-1);
		}
		return null;
	}
	
	/**
	 * closes connection, prints error message, if closing failed
	 */
	public void cleanup(){
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while closing connection:");
			e.printStackTrace();
		}
	}
	
}