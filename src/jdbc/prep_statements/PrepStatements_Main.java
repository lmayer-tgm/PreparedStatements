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
		CommandLineParser parser = new CommandLineParser();
		DBConnection conn = null;
		try{
			parser.parse(args); 
			conn = new DBConnection(parser.getArgumentOf(CommandLineParser.HOSTNAME.get(0)), parser.getArgumentOf(CommandLineParser.DBNAME.get(0)), 
					parser.getArgumentOf(CommandLineParser.USERNAME.get(0)), parser.getArgumentOf(CommandLineParser.PASSWORD.get(0)));
			CRUD_Test test = new CRUD_Test(conn);
			for (int i = 1000; i < 11000; i++) {
				test.insertPerson(i, "vorname"+i, "nachname"+i);
			}
			for (int i = 1000; i < 11000; i++) {
				ResultSet set = test.selectPerson(i);
			}
			for (int i = 1000; i < 11000; i++) {
				test.updatePerson(i, ("vorname"+i).toUpperCase());
			}
			for (int i = 1000; i < 11000; i++) {
				test.deletePerson(i);
			}
			
			// do smth. with it...
		} catch(OptionException exc){
			System.out.println(exc.getMessage());
		} finally {
			// cleanup
		}
	}
}
