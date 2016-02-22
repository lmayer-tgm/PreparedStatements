package jdbc.prep_statements;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionException;
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
	
	public static List<String> HOSTNAME = Arrays.asList("hostname","host","h");
	public static List<String> DBNAME = Arrays.asList("databasename","dbname","db");
	public static List<String> USERNAME = Arrays.asList("username","user","u");
	public static List<String> PASSWORD = Arrays.asList("password","pw");
	public static List<String> PORT = Arrays.asList("port","p");
	public static List<String> HELP = Arrays.asList("help","?");
											 /*option description					argument description*/
	public static String[] HOSTNAME_DESC = {"server to connect to", 					"ip_or_hostname"};
	public static String[] DBNAME_DESC = {	"name of db to connect to", 				"database_name"};
	public static String[] USERNAME_DESC = {"username of user used for connection", 	"username"};
	public static String[] PASSWORD_DESC = {"password of user used for connection", 	"password"};
	public static String[] PORT_DESC = {	"port, used for connection to db server", 	"port"};
	private static int DEFAULT_PORT = 5432;

	private OptionParser parser;
	private OptionSet set;

	public CommandLineParser() {
		parser = new OptionParser();
		parser.acceptsAll(HELP, "prints this help message").forHelp();
		parser.acceptsAll(HOSTNAME, HOSTNAME_DESC[0]).withRequiredArg().describedAs(HOSTNAME_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(DBNAME, DBNAME_DESC[0]).withRequiredArg().describedAs(DBNAME_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(USERNAME, USERNAME_DESC[0]).withRequiredArg().describedAs(USERNAME_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(PASSWORD, PASSWORD_DESC[0]).withRequiredArg().describedAs(PASSWORD_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(PORT, PORT_DESC[0]).withOptionalArg().describedAs(PORT_DESC[1]).ofType(Integer.class).defaultsTo(DEFAULT_PORT);

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
		if(set.has(HELP.get(0)))
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
	public String getArgumentOf (String option){
		return ""+set.valueOf(option);
	}
	
	
}
