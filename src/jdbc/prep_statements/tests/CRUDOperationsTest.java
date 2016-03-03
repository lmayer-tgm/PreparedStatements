package jdbc.prep_statements.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import jdbc.prep_statements.CRUDOperations;
import jdbc.prep_statements.DBConnection;

/**
 * Test cases for CRUDOperations class
 * @author pkomon, lmayer
 * @version 20160229.2
 *
 */
public class CRUDOperationsTest {
	private static final String INSERT_STATEMENT = "INSERT INTO person VALUES(?, ?, ?)";
	private static final String SELECT_STATEMENT = "SELECT * FROM person WHERE nummer = ?";
	private static final String UPDATE_STATEMENT = "UPDATE person SET vorname = ? WHERE nummer = ?";
	private static final String DELETE_STATEMENT = "DELETE FROM person WHERE nummer = ?";
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Mock private DBConnection con;
	private CRUDOperations crud;
	
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();
	
	/**
	 * create declared mock objects, 
	 * redirect write operations to System.out to outContent
	 */
	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		MockitoAnnotations.initMocks(this);
	}

	/**
	 *  resets System.out to stdout
	 */
	@After
	public void cleanUp() {
		System.setOut(null);
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CRUDOperations#CRUD_Test(jdbc.prep_statements.DBConnection, boolean)}.
	 * tests if all statements are prepared
	 */
	@Test
	public void testCRUD_Test() {
		crud = new CRUDOperations(con, true);
		Mockito.verify(con).prepareStatement(INSERT_STATEMENT);
		Mockito.verify(con).prepareStatement(SELECT_STATEMENT);
		Mockito.verify(con).prepareStatement(UPDATE_STATEMENT);
		Mockito.verify(con).prepareStatement(DELETE_STATEMENT);
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CRUDOperations#selectPerson(int)}.
	 * checks if values were substituted (setVar(index,val)), if the prepared statement was executed and
	 * if logging and error handling is working properly
	 */
	@Test
	public void testSelectPerson() {
		int nummer = 1;
		PreparedStatement select = Mockito.mock(PreparedStatement.class);
		ResultSet resExp = Mockito.mock(ResultSet.class);
		try {
			Mockito.doReturn(resExp).when(select).executeQuery();
		} catch (SQLException e1) {
			Assert.fail();
			e1.printStackTrace();
		}
		Mockito.doReturn(SELECT_STATEMENT).when(select).toString();
		crud = new CRUDOperations(con, null, select, null, null, true);
		ResultSet resAct = crud.selectPerson(nummer);
		try {
			Mockito.verify(select).setInt(1, nummer);
			Mockito.verify(select).executeQuery();
			Assert.assertEquals(resExp, resAct);
		} catch (SQLException e) {
			Assert.fail();
			e.printStackTrace();
		}
		String output = outContent.toString();
		Assert.assertTrue(output.contains(SELECT_STATEMENT));
		try {
			Mockito.doThrow(new SQLException()).when(select).executeQuery();
			exit.expectSystemExitWithStatus(-1);
			crud.selectPerson(nummer);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CRUDOperations#insertPerson(int, java.lang.String, java.lang.String)}.
	 * checks if values were substituted (setVar(index,val)), if the prepared statement was executed and
	 * if logging and error handling is working properly
	 */
	@Test
	public void testInsertPerson() {
		String vname = "vorname", nname = "nachname";
		int nummer = 1;
		PreparedStatement insert = Mockito.mock(PreparedStatement.class);
		Mockito.doReturn(INSERT_STATEMENT).when(insert).toString();
		crud = new CRUDOperations(con, insert, null, null, null, true);
		crud.insertPerson(nummer, vname, nname);
		try {
			Mockito.verify(insert).setInt(1, nummer);
			Mockito.verify(insert).setString(2, vname);
			Mockito.verify(insert).setString(3, nname);
			Mockito.verify(insert).execute();
		} catch (SQLException e) {
			Assert.fail();
			e.printStackTrace();
		}
		String output = outContent.toString();
		Assert.assertTrue(output.contains(INSERT_STATEMENT));
		try {
			Mockito.doThrow(new SQLException()).when(insert).execute();
			exit.expectSystemExitWithStatus(-1);
			crud.insertPerson(nummer, vname, nname);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CRUDOperations#updatePerson(int, java.lang.String)}.
	 * checks if values were substituted (setVar(index,val)), if the prepared statement was executed and
	 * if logging and error handling is working properly
	 */
	@Test
	public void testUpdatePerson() {
		String vname = "vorname";
		int nummer = 1;
		PreparedStatement update = Mockito.mock(PreparedStatement.class);
		Mockito.doReturn(UPDATE_STATEMENT).when(update).toString();
		crud = new CRUDOperations(con, null, null, update, null, true);
		crud.updatePerson(nummer, vname);
		try {
			Mockito.verify(update).setString(1, vname);
			Mockito.verify(update).setInt(2, nummer);
			Mockito.verify(update).executeUpdate();
		} catch (SQLException e) {
			Assert.fail();
			e.printStackTrace();
		}
		String output = outContent.toString();
		Assert.assertTrue(output.contains(UPDATE_STATEMENT));
		try {
			Mockito.doThrow(new SQLException()).when(update).executeUpdate();
			exit.expectSystemExitWithStatus(-1);
			crud.updatePerson(nummer, vname);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Test method for {@link jdbc.prep_statements.CRUDOperations#deletePerson(int)}.
	 * checks if values were substituted (setVar(index,val)), if the prepared statement was executed and
	 * if logging and error handling is working properly
	 */
	@Test
	public void testDeletePerson() {
		int nummer = 1;
		PreparedStatement delete = Mockito.mock(PreparedStatement.class);
		Mockito.doReturn(DELETE_STATEMENT).when(delete).toString();
		crud = new CRUDOperations(con, null, null, null, delete, true);
		crud.deletePerson(nummer);
		try {
			Mockito.verify(delete).setInt(1, nummer);
			Mockito.verify(delete).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
		String output = outContent.toString();
		Assert.assertTrue(output.contains(DELETE_STATEMENT));
		try {
			Mockito.doThrow(new SQLException()).when(delete).executeUpdate();
			exit.expectSystemExitWithStatus(-1);
			crud.deletePerson(nummer);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}	
	}
}
