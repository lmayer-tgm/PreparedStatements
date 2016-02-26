package jdbc.prep_statements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * tests various CRUD operations on DB
 * 
 * @author pkomon, lmayer
 * @version 20160218.1
 */
public class CRUD_Test {
	private static final String INSERT_STATEMENT = "INSERT INTO person VALUES(?, ?, ?)";
	private static final String SELECT_STATEMENT = "SELECT * FROM person WHERE nummer = ?";
	private static final String UPDATE_STATEMENT = "UPDATE person SET vorname = ? WHERE nummer = ?";
	private static final String DELETE_STATEMENT = "DELETE FROM person WHERE nummer = ?";
	
	private PreparedStatement insert, select, update, delete;
	private DBConnection con;
	private boolean logging;
	
	/**
	 * creates an crud test object
	 * uses given connection to create four prepared statements (create, read, update, delete)
	 * @param con established connection
	 * @param logging determines if logging is enabled or not
	 */
	public CRUD_Test(DBConnection con, boolean logging) {
		this.con = con;
		this.logging = logging;
		insert = con.prepareStatement(INSERT_STATEMENT);
		select = con.prepareStatement(SELECT_STATEMENT);
		update = con.prepareStatement(UPDATE_STATEMENT);
		delete = con.prepareStatement(DELETE_STATEMENT);
	}
	
	/**
	 * executes a select query for all columns 
	 * @param nummer the nummer attribute of the row to selected
	 * @return a result set of the executed query
	 * 			returns null if query failed
	 */
	public ResultSet selectPerson(int nummer){
		try {
			select.setInt(1, nummer);
			if(logging) System.out.println(select.toString());
			return select.executeQuery();
		} catch(SQLException e){
			handleException(e);
		}
		return null;
	}
	/**
	 * inserts new values into the person table
	 * @param nummer the nummer attribute of the row to insert
	 * @param vorname the vorname attribute of the row to insert
	 * @param nachname the nachname attribute of the row to insert
	 */
	public void insertPerson(int nummer, String vorname, String nachname){
		try{
			insert.setInt(1, nummer);
			insert.setString(2, vorname);
			insert.setString(3, nachname);
			if(logging) System.out.println(insert.toString());
			insert.execute();
		}catch(SQLException e){
			handleException(e);
		}
	}
	/**
	 * updates a row in the person table
	 * @param nummer of row to be updated
	 * @param vorname of row to be update
	 */
	public void updatePerson(int nummer, String vorname){
		try{
			update.setString(1, vorname);
			update.setInt(2, nummer);
			if(logging) System.out.println(update.toString());
			update.executeUpdate();
		}catch(SQLException e){
			handleException(e);
		}
	}
	/**
	 * deletes a row in the person table
	 * @param nummer of row to be deleted
	 */
	public void deletePerson(int nummer){
		try{
			delete.setInt(1, nummer);
			if(logging) System.out.println(delete.toString());
			delete.executeUpdate();
		}catch(SQLException e){
			handleException(e);
		}
	}
	
	/**
	 * Prints error message and quits application with error code -1
	 * @param e the exception to print stack trace from
	 */
	private void handleException(Exception e){
		System.err.println("Error while executing prepared statement");
		e.printStackTrace();
		con.cleanup();
		System.exit(-1);
	}
}