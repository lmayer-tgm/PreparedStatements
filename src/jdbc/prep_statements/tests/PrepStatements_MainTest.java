/**
 * 
 */
package jdbc.prep_statements.tests;

import static org.junit.Assert.*;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.mockito.MockitoAnnotations;

/**
 * Test cases for Main class
 * @author lmayer
 *
 */
public class PrepStatements_MainTest {

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
//		System.setOut(new PrintStream(outContent));
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test method for {@link jdbc.prep_statements.PrepStatements_Main#main(java.lang.String[])}.
	 */
	@Test
	public void testMain() {
		
	}

}
