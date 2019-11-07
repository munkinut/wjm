/*
 * Created on 11-May-2005
 *
 */
package uk.co.indigo.play.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * MySQLTest
 * 
 * Demonstrates how to connect to MySQL and how to iterate over
 * an arbitrary number of columns.  The table columns can be 
 * altered on the fly with no impact on the code.
 * 
 * @author milbuw
 *
 */
public class MySQLTest {

	public MySQLTest() {
		super();
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			// connect
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=root");
			System.out.println("Connection to localhost/test succeeded");
			System.out.println();
			// execute query
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select * from test");
			// acquire meta data and interrogate for column info
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			int row = 0;
			int column = 0;
			String columnName;
			String columnValue;
			// iterate over the rows
			while (rs.next()) {
				column = 1;
				row++;
				System.out.print("Row " + row + " : ");
				// iterate over the columns
				if (columns > 0) {
					while (column <= columns) {
						// interrogate meta data for more column info
						columnName = rsmd.getColumnName(column);
						columnValue = rs.getString(columnName);
						System.out.print(columnName + "=" + columnValue + " ");
						column++;
					}
				}
				System.out.println();
			}
			// finished - close up
			con.close();
			System.out.println();
			System.out.println("Connection closed");
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		catch (InstantiationException ie) {
			ie.printStackTrace();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}
