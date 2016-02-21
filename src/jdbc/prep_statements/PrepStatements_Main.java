package jdbc.prep_statements;

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
			conn = new DBConnection(parser.getArgumentOf(CommandLineParser.HOSTNAME.get(0)), CommandLineParser.DBNAME.get(0), 
					CommandLineParser.USERNAME.get(0), CommandLineParser.PASSWORD.get(0)); 
			// do smth. with it...
		} catch(OptionException exc){
			System.out.println(exc.getMessage());
		} finally {
			// cleanup
		}
	}
}
