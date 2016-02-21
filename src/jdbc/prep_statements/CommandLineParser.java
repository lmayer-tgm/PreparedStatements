package jdbc.prep_statements;

import java.io.IOException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Class representing a command line parser, wrapping some methods of the used
 * CLI parsing library
 *  - 4 required options:
 *   "--host <hostname> --dbname <dbname> --username <username> --password <password>"
 *  also accepts "help" to print out a help message
 * 
 * @author pkomon, lmayer
 * @version 20160221.2
 */
public class CommandLineParser {
											 /*option name		option description					argument description*/
	public static String[] HOSTNAME_OPTION = {	"host", 	"server to connect to", 				"ip_or_hostname"};
	public static String[] DBNAME_OPTION = {	"dbname", 	"name of db to connect to", 			"database_name"};
	public static String[] USERNAME_OPTION = {	"username", "username of user used for connection", "username"};
	public static String[] PASSWORD_OPTION = {	"password", "password of user used for connection", "password"};
	public static String HELP = "help";

	private OptionParser parser;
	private OptionSet set;

	public CommandLineParser() {
		parser = new OptionParser();
		parser.accepts(HELP, "prints this help message").forHelp();
		parser.accepts(HOSTNAME_OPTION[0], HOSTNAME_OPTION[1]).withRequiredArg().describedAs(HOSTNAME_OPTION[2]).ofType(String.class).required();
		parser.accepts(DBNAME_OPTION[0], DBNAME_OPTION[1]).withRequiredArg().describedAs(DBNAME_OPTION[2]).ofType(String.class).required();
		parser.accepts(USERNAME_OPTION[0], USERNAME_OPTION[1]).withRequiredArg().describedAs(USERNAME_OPTION[2]).ofType(String.class).required();
		parser.accepts(PASSWORD_OPTION[0], PASSWORD_OPTION[1]).withRequiredArg().describedAs(PASSWORD_OPTION[2]).ofType(String.class).required();
	}

	/**
	 * parses an array of strings, prints help message, if help option was supplied
	 * the parsed options are accessible over the getter methods
	 * if required options were missing, an OptionException is thrown
	 * @param toParse the array of strings to parse
	 * @throws OptionException if required options were missing
	 */
	public void parse(String[] toParse) {
		set = parser.parse(toParse);
		if(set.has(HELP))
			printHelp();
	}
	
	/**
	 * prints help message to stdout
	 * if write operation is impossible (whyever...) and an exception occurs
	 * its stack trace will be written to stderr and the applications exits with error code -1 
	 */
	public void printHelp(){
		try {
			parser.printHelpOn(System.out);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * returns the supplied argument to the handed option (if there was an argument supplied)
	 * @param option the option, which's argument is requested
	 * @return argument supplied to a specific option
	 */
	public String getArgumentOf(String option){
		return (String) set.valueOf(option);
	}
}
