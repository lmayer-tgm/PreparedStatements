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
	
	public static final List<String> HOSTNAME = Arrays.asList("hostname","host","h");
	public static final List<String> DBNAME = Arrays.asList("databasename","database","dbname","db");
	public static final List<String> USERNAME = Arrays.asList("username","user","u");
	public static final List<String> PASSWORD = Arrays.asList("password","pw");
	public static final List<String> PORT = Arrays.asList("port","p");
	public static final List<String> HELP = Arrays.asList("help","?");
	public static final List<String> OPERATIONS = Arrays.asList("operations","op");
	public static final List<String> VERBOSE = Arrays.asList("verbose","v");
	public static final List<String> AMOUNT = Arrays.asList("amount","a");
											 /*option description					argument description*/
	public static final String[] HOSTNAME_DESC = {"server to connect to", 					"ip_or_hostname"};
	public static final String[] DBNAME_DESC = {	"name of db to connect to", 				"database_name"};
	public static final String[] USERNAME_DESC = {"username of user used for connection", 	"username"};
	public static final String[] PASSWORD_DESC = {"password of user used for connection", 	"password"};
	public static final String[] PORT_DESC = {	"port, used for connection to db server", 	"port"};
	public static final String[] OPERATIONS_DESC = {"operations to do executed in"
			+ "	the order provided: c...insertr...select,u...update,d...delete","operations_string"};
	public static final String[] AMOUNT_DESC = {	"the amount of test rows", 					"amount_of_rows"};

	public static final String VERBOSE_DESC = "enables verbose logging (all statements and results of queries)"
			+ "\nit may work faster without logging";

	private static final int DEFAULT_PORT = 5432;
	private static final String DEFAULT_OPS = "crud";
	private static final int DEFAULT_AMOUNT = 10000;

	private OptionParser parser;
	private OptionSet set;

	/**
	 * Creates a new CommandLineParser object, accepting various options
	 */
	public CommandLineParser() {
		parser = new OptionParser();
		parser.acceptsAll(HELP, "prints this help message").forHelp();
		parser.acceptsAll(HOSTNAME, HOSTNAME_DESC[0]).withRequiredArg().describedAs(HOSTNAME_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(DBNAME, DBNAME_DESC[0]).withRequiredArg().describedAs(DBNAME_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(USERNAME, USERNAME_DESC[0]).withRequiredArg().describedAs(USERNAME_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(PASSWORD, PASSWORD_DESC[0]).withRequiredArg().describedAs(PASSWORD_DESC[1]).ofType(String.class).required();
		parser.acceptsAll(PORT, PORT_DESC[0]).withOptionalArg().describedAs(PORT_DESC[1]).ofType(Integer.class).defaultsTo(DEFAULT_PORT);
		parser.acceptsAll(VERBOSE, VERBOSE_DESC);
		parser.acceptsAll(OPERATIONS, OPERATIONS_DESC[0]).withOptionalArg().describedAs(OPERATIONS_DESC[1]).ofType(String.class).defaultsTo(DEFAULT_OPS);
		parser.acceptsAll(AMOUNT, AMOUNT_DESC[0]).withOptionalArg().describedAs(AMOUNT_DESC[1]).ofType(Integer.class).defaultsTo(DEFAULT_AMOUNT);
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
		if(set.has(HELP.get(0))){
			printHelp();
			System.exit(0);
		}
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
	 * wrapper for optionset_obj.valueOf(..) method
	 * returns the supplied argument to the handed option (if there was an argument supplied)
	 * @param option the option, which's argument is requested
	 * @return argument supplied to a specific option
	 */
	public String getArgumentOf(String option){
		return ""+set.valueOf(option);
	}
	
	/**
	 * wrapper for optionset_obj.has(..) method
	 * checks if options was supplied
	 * @param option the name of the option (e.g. "-v")
	 * @return true if option was supplied, false otherwise
	 */
	public boolean hasOption(String option){
		return set.has(option);
	}
	
	
}
