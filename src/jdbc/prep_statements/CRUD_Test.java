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

	private PreparedStatement insert, select, update, delete;
	private String table = "person";
	
	/**
	 * creates an crud test object
	 * uses given connection to create four prepared statements (create, read, update, delete)
	 * @param con established connection
	 */
	public CRUD_Test(DBConnection con) {
		insert = con.prepareStatement("INSERT INTO " + table + " VALUES(?, ?, ?)");
		select = con.prepareStatement("SELECT * FROM " + table + " WHERE nummer = ?");
		update = con.prepareStatement("UPDATE " + table + " SET vorname = ? WHERE nummer = ?");
		delete = con.prepareStatement("DELETE FROM " + table + " WHERE nummer = ?");
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
			System.out.println(select.toString());
			return select.executeQuery();
		} catch(SQLException e){
			e.printStackTrace();
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
		System.out.println(insert.toString());
			insert.execute();
		}catch(SQLException e){
			e.printStackTrace();
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
			System.out.println(update.toString());
			update.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * deletes a row in the person table
	 * @param nummer of row to be deleted
	 */
	public void deletePerson(int nummer){
		try{
			delete.setInt(1, nummer);
			System.out.println(delete.toString());
			delete.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}