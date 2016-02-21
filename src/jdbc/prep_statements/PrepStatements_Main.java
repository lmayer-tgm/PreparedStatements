package jdbc.prep_statements;

import joptsimple.OptionException;;

/**
 * Provides only start point of application
 * @author pkomon, lmayer
 * @version 20160218.1
 */
public class PrepStatements_Main {

	public static void main(String[] args) {
		CommandLineParser parser = new CommandLineParser();
		DBConnection conn = null;
		try{
			parser.parse(args);
			conn = new DBConnection(parser.getArgumentOf(CommandLineParser.HOSTNAME_OPTION[0]), CommandLineParser.DBNAME_OPTION[0], 
					CommandLineParser.USERNAME_OPTION[0], CommandLineParser.PASSWORD_OPTION[0]); 
			// do smth. with it...
		} catch(OptionException exc){
			System.out.println(exc.getMessage());
		} finally {
			// cleanup
		}
	}
}
