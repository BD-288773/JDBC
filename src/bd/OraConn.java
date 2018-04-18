package bd;

import java.lang.Class;
import java.sql.*;

public class OraConn {

	public static void main(String[] args) {
		
		Connection c;
		c=OraConn.open();
	}
	
	public static Connection open(){
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		
		catch (ClassNotFoundException ex) {
			System.out.println("Brak sterownika Oracle JDBC.");
			System.exit(0);
		}
		
		System.out.println("Sterownik Oracle JDBC zosta³ zarejestrowany.");
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf", "ssidoruk", "ssidoruk");
		}
		
		catch (SQLException ex) {
			System.out.println("B³¹d po³¹czenia" + ex.getMessage());
			System.exit(0);
		}
		
		System.out.println("Otwarto po³¹czenie");		
		return connection;

	}
	
	public static Connection close(Connection connection) {
		
		try {
			connection.close();
		}
		
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Zamkniêto po³¹czenie");
		return null;
	}

}
