/**
 * 
 */
package jdbc.prep_statements.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jdbc.prep_statements.CommandLineParser;
import joptsimple.OptionException;

/**
 * Unit tests for methods of CommandLineParser class
 * @author pkomon
 * @version 20160221.1
 */
public class CLIParserTest {

	/*didn't know how to test for console output; looked it up on stackoverflow*/
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/*basically everything is just printed to this streams instead of stdout;
	 *  therefore it can be tested easily*/
	
	/**
	 * Called before each test, used to redirect all content, written to stdout to another
	 * output stream (it sets System.out to another output stream then stdout)
	 */
	@Before
	public void setUpStreams(){
		System.setOut(new PrintStream(outContent));
	}
	
	/**
	 * Called after each test, resets System.out to stdout
	 */
	@After
	public void cleanUpStreams(){
		System.setOut(null);
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CommandLineParser#parse(java.lang.String[])}.
	 * Should throw an exception because not all require were arguments were suppled
	 */
	@Test(expected=OptionException.class)
	public void testParseArgsMissing() {
		CommandLineParser parser = new CommandLineParser();
		String[] args = {"--host", "host_name"}; // arguments missing -> should lead to error
		parser.parse(args);
	}
	
	/**
	 * Test method for {@link jdbc.prep_statements.CommandLineParser#parse(java.lang.String[])}.
	 * If all arguments were supplied, no exception should be thrown
	 */
	@Test
	public void testParseArgsSupplied() {
		CommandLineParser parser = new CommandLineParser();
		String[] args = {"--host", "host_name", "--dbname", "name", "--username", "user", "--password", "pw"}; // arguments here ->  no error
		parser.parse(args);
	}
	
	/**
	 * Test method for {@link jdbc.prep_statements.CommandLineParser#parse(java.lang.String[])}.
	 * If all "help" option was supplied, no exception should be thrown
	 */
	@Test
	public void testParseHelp() {
		CommandLineParser parser = new CommandLineParser();
		String[] args = {"--help"}; // help option here -> no error
		parser.parse(args);
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CommandLineParser#printHelp()}.
	 * Should print the descriptions of the options and arguments
	 */
	@Test
	public void testPrintHelp() {
		CommandLineParser parser = new CommandLineParser();
		String[] args = {"--help"}; // arguments missing -> should lead to error
		parser.parse(args);
		String output = outContent.toString();
		Assert.assertTrue(output.contains("--host")); //didnt test ALL of the content, that should be printed out...
		Assert.assertTrue(output.contains("server to connect to"));
		Assert.assertTrue(output.contains("<database_name>"));
		Assert.assertTrue(output.contains("Description"));
		Assert.assertTrue(output.contains("--username <username>"));
		Assert.assertTrue(output.contains("prints this")); //...but that should be enough

	}

	/**
	 * Test method for {@link jdbc.prep_statements.CommandLineParser#getArgumentOf(java.lang.String)}.
	 * Gets the values of the required arguments
	 */
	@Test
	public void testGetArgumentOf() {
		String hostname = "some_host", dbname = "some_db", username = "some_user", password = "super_secret";
		CommandLineParser parser = new CommandLineParser();
		String[] args = {"--host", hostname, "--dbname", dbname, "--username", username, "--password", password};
		parser.parse(args);
		Assert.assertEquals(parser.getArgumentOf("host"), hostname);
		Assert.assertEquals(parser.getArgumentOf("dbname"), dbname);
		Assert.assertEquals(parser.getArgumentOf("username"), username);
		Assert.assertEquals(parser.getArgumentOf("password"), password);				
	}

}
