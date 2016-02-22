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

	public static int MIN = 1000;
	public static int MAX = 11000;
	
	public static void main(String[] args) {
		CommandLineParser parser = new CommandLineParser();
		DBConnection conn = null;
		try{
			parser.parse(args); 
			conn = new DBConnection(parser.getArgumentOf(CommandLineParser.HOSTNAME.get(0)), parser.getArgumentOf(CommandLineParser.DBNAME.get(0)), 
					parser.getArgumentOf(CommandLineParser.USERNAME.get(0)), parser.getArgumentOf(CommandLineParser.PASSWORD.get(0)));
			CRUD_Test test = new CRUD_Test(conn);
			//testing prep statement for insert
			for (int i = MIN; i < MAX; i++) {
				test.insertPerson(i, "vorname"+i, "nachname"+i);
			}
			//testing prep statement for select
			for (int i = MIN; i < MAX; i++) {
				ResultSet set = test.selectPerson(i);
				set.next();
				System.out.println("result row: "+set.getString(1) + ", " + set.getString(2) + ", " + set.getString(3));
			}
			//testing prep statement for update
			for (int i = MIN; i < MAX; i++) {
				test.updatePerson(i, ("vorname"+i).toUpperCase());
			}
			//testing prep statement for delete
			for (int i = MIN; i < MAX; i++) {
				test.deletePerson(i);
			}
		} catch(OptionException exc){
			System.out.println(exc.getMessage());
		} catch (SQLException e) {
			System.err.println("JDBC error:");
			e.printStackTrace();
		} finally {
			conn.cleanup();
		}
	}
}
