/**
 * 
 */
package jdbc.prep_statements.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.postgresql.ds.PGSimpleDataSource;

import jdbc.prep_statements.DBConnection;

/**
 * Test cases for DBConnection class
 * @author pkomon
 * @version 20160229.1
 */
public class DBConnectionTest {
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Mock private Connection con;
	@Mock private PGSimpleDataSource ds;
	private DBConnection dbCon;
	
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();
	
	/**
	 * create declared mock objects, 
	 * inject mock objects into the DBConnection object used for testing, 
	 * redirect write operations to System.err to errContent
	 */
	@Before
	public void setUp() {
		System.setErr(new PrintStream(errContent));
		MockitoAnnotations.initMocks(this);
		dbCon = new DBConnection(ds, con);
	}
	
	/**
	 *  resets System.err to stderr
	 */
	@After
	public void cleanUp() {
		System.setErr(null);
	}

	/**
	 * Test method for {@link jdbc.prep_statements.DBConnection#DBConnection(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 * No idea how to test this constructor...
	 * because of these lines: 
	 * 	ds = new PGSimpleDataSource;
	 * 	con = ds.getConnection();
	 */
	@Test
	public void testDBConnection() {
		
	}

	/**
	 * Test method for {@link jdbc.prep_statements.DBConnection#prepareStatement(java.lang.String)}.
	 * Tests if con.prepareStatement(string) is actually called
	 */
	@Test
	public void testPrepareStatement() {
		String query = "SELECT * FROM person";
		PreparedStatement ps = Mockito.mock(PreparedStatement.class);
		Mockito.when(ps.toString()).thenReturn(query);
		PreparedStatement res = null;
		try {
			Mockito.when(con.prepareStatement(query)).thenReturn(ps);
			res = dbCon.prepareStatement(query);
			Mockito.verify(con).prepareStatement(query); //was con.prepareStatement(string) called?
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertEquals(query, res.toString());
		try {
			Mockito.when(con.prepareStatement(query)).thenThrow(new SQLException());
			exit.expectSystemExitWithStatus(-1);
			res = dbCon.prepareStatement(query); //should through exception and exit with status -1
			Assert.fail();
		} catch (SQLException e) {
			try {
				String output = errContent.toString();
				Assert.assertTrue(output.contains("Error while preparing statement:"));
				Mockito.verify(con).prepareStatement(query);
				Mockito.verify(con).close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				Assert.fail();
			}
		}
	}

	/**
	 * Test method for {@link jdbc.prep_statements.DBConnection#cleanup()}.
	 * Tests if connection.close() is called
	 */
	@Test
	public void testCleanup() {
		dbCon.cleanup();
		try {
			Mockito.verify(con).close(); //was con.close() called?
			Mockito.doThrow(new SQLException()).when(con).close();
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
		dbCon.cleanup(); //should now go catch the exception thrown by con.close() and output an error msg
		String output = errContent.toString();
		Assert.assertTrue(output.contains("Error while closing connection:"));
	}
}
