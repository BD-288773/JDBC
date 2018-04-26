package bd;

import java.lang.Class;
import java.sql.*;

public class OraConn {
	
	private String user_name;
	private String password;
	private String domain;
	private String error_name;
	private int error_number;
	private Connection conn;
	private String log;
	
	public OraConn(String u_name, String pass, String dom) {
		
		user_name = u_name;
		password = pass;
		domain = "jdbc:oracle:thin:" + dom;
		error_name = "";
		error_number = 0;
		log = "";
		
	}
	
	public void open(){
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		
		catch (ClassNotFoundException ex) {
			
			error_number = 1;
			error_name = "Oracle JDBC driver not found";
			String info = new String("Error number: " + error_number + "; " + error_name +"\n");
			log+=info;
		}
		
		log += "Oracle JDBC driver registered.\n";
		
		
		try {
					
			conn = DriverManager.getConnection(domain, user_name, password);
		}
		
		catch (SQLException ex) {
			error_number = 2;
			error_name = "Connection error" + ex.getMessage();
			String info = new String("Error number: " + error_number + "; " + error_name +"\n");
			log+=info;
		}
		
		log += "Connection established\n";

	}
	
	public void close() {
		
		try {
			conn.close();
		}
		
		catch(SQLException ex) {
			error_number = 3;
			error_name = "Unnable to close connection.\n";
			String info = new String("Error number: " + error_number + "; " + error_name +"\n");
			log+=info;
		}
		
		log += "Connection closed\n";
		
		conn =null;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getError_name() {
		return error_name;
	}

	public int getError_number() {
		return error_number;
	}

	public Connection getConn() {
		return conn;
	}

	public String getLog() {
		return log;
	}

}
