package jdbc.prep_statements;

import java.sql.ResultSet;
import java.sql.SQLException;

import joptsimple.OptionException;;

/**
 * Provides only the start point of the application
 * @author pkomon, lmayer
 * @version 20160218.1
 */
public class PrepStatements_Main {
	
	public static void main(String[] args) {
		int min = 1000, max;
		CommandLineParser parser = new CommandLineParser();
		DBConnection conn = null;
		try{
			parser.parse(args); 
			conn = new DBConnection(parser.getArgumentOf(CommandLineParser.HOSTNAME.get(0)), parser.getArgumentOf(CommandLineParser.DBNAME.get(0)), 
					parser.getArgumentOf(CommandLineParser.USERNAME.get(0)), parser.getArgumentOf(CommandLineParser.PASSWORD.get(0)),
					Integer.parseInt(parser.getArgumentOf(CommandLineParser.PORT.get(0))));
			CRUD_Test test = new CRUD_Test(conn, parser.hasOption(CommandLineParser.VERBOSE.get(0)));
			max = min + Integer.parseInt(parser.getArgumentOf(CommandLineParser.AMOUNT.get(0)));
			startTest(test, min, max, parser.hasOption(CommandLineParser.VERBOSE.get(0)),parser.getArgumentOf(CommandLineParser.OPERATIONS.get(0)));
		} catch(OptionException exc){
			System.out.println(exc.getMessage()+"\nFor help supply -? or --help");
		} catch (SQLException e) {
			System.err.println("JDBC error:");
			e.printStackTrace();
		} finally {
			if(conn!=null)
				conn.cleanup();
		}
	}

	/**
	 * starts test consisting of insert,select,update,select,delete in this order
	 * @param test the crud_test object, that provides methods for prepared statement execution
	 * @param min the id of the first row to be selected
	 * @param max the id of the last row to be selected
	 * @param logging determines if output of query results to stdout is enabled
	 * @param operations the various prepared operations to perform (each operation is triggered by a char)
	 * 		 			 c...insert,r...select,u...update,d...delete
	 * @throws SQLException
	 */
	private static void startTest(CRUD_Test test, int min, int max, boolean logging, String operations) throws SQLException{
		for(int i = 0; i < operations.length(); ++i)
			switch (operations.charAt(i)) {
				case 'c':
					insert(test,min,max);
				break;
				case 'r':
					select(test,min,max,logging);
				break;
				case 'u':
					update(test,min,max);
				break;
				case 'd':
					delete(test,min,max);
				break;
				default:
			}
	}
	
	/**
	 * testing prep statement for select
	 * @param test the crud_test object, that provides methods for prepared statement execution
	 * @param min the id of the first row to be selected
	 * @param max the id of the last row to be selected
	 * @param logging determines if output of query results to stdout is enabled
	 * @throws SQLException
	 */
	private static void select(CRUD_Test test, int min, int max, boolean logging) throws SQLException{
		ResultSet set = null;
		for (int i = min; i < max; i++) {
			set = test.selectPerson(i);
			set.next();
			if(logging) System.out.println("result row: "+set.getString(1) + ", " + set.getString(2) + ", " + set.getString(3));
		}
		set.close();
	}
	
	/**
	 * testing prep statement for insert
	 * @param test the crud_test object, that provides methods for prepared statement execution
	 * @param min the id of the first row to be selected
	 * @param max the id of the last row to be selected
	 * @throws SQLException
	 */
	private static void insert(CRUD_Test test, int min, int max) throws SQLException{
		for (int i = min; i < max; i++) 
			test.insertPerson(i, "vorname"+i, "nachname"+i);
	}
	
	/**
	 * testing prep statement for update
	 * @param test the crud_test object, that provides methods for prepared statement execution
	 * @param min the id of the first row to be selected
	 * @param max the id of the last row to be selected
	 * @throws SQLException
	 */
	private static void update(CRUD_Test test, int min, int max) throws SQLException{
		for (int i = min; i < max; i++)
			test.updatePerson(i, ("vorname"+i).toUpperCase());
	}
	
	/**
	 * testing prep statement for delete
	 * @param test the crud_test object, that provides methods for prepared statement execution
	 * @param min the id of the first row to be selected
	 * @param max the id of the last row to be selected
	 * @throws SQLException
	 */
	private static void delete(CRUD_Test test, int min, int max) throws SQLException{
		for (int i = min; i < max; i++) 
			test.deletePerson(i);
	}
	
}
