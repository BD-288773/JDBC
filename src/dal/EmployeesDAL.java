package dal;

import java.util.*;

import bd.OraConn;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class EmployeesDAL {
		private SQLException ex;
		
		public EmployeesDAL() {}
		
		public List<Employee> getEmployees(OraConn oconn) {
			
			List<Employee> employees= new ArrayList<Employee>();
			
			try (Statement statement =oconn.getConn().createStatement();) {
				String query = "SELECT * FROM EMPLOYEES";
				ResultSet resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
				employees.add(rs2Employee(resultSet));
				}
			}
			
			catch (SQLException ex ) { System.out.println(ex); }
			return employees;
		}
		
		private Employee rs2Employee(ResultSet resultSet) {
			
			Employee emp = null;
			try {
				int col = 1;
				emp = new Employee(resultSet.getInt(col++));
				emp.setFirstName(resultSet.getNString(col++));
				emp.setLastName(resultSet.getNString(col++));
				emp.setEmail(resultSet.getNString(col++));
				emp.setPhoneNumber(resultSet.getString(col++));
				emp.setHireDate(resultSet.getDate(col++).toLocalDate());
				emp.setJobId(resultSet.getNString(col++));
				emp.setSalary(resultSet.getInt(col++));
				col++;
				emp.setManagerId(resultSet.getInt(col++));
				emp.setDepartmentId(resultSet.getInt(col++));

				
			}
			catch (SQLException ex ) {
				this.ex = ex;
				}
			
			return emp;
			
		}
		
		public int updateEmployee(Employee emp, OraConn oconn) {
			try (Statement statement =
			oconn.getConn().createStatement(); ) {
			DateTimeFormatter dtf =
			DateTimeFormatter.ofPattern("yyyyMMdd");
			String hireDate = dtf.format(emp.getHireDate());
			String query = "UPDATE EMPLOYEES SET "
			+ "FIRS_NAME = '" + emp.getFirstName() + "', "
			+ "LAST_NAME = '" + emp.getLastName() + "', "
			+ "EMAIL = '" + emp.getEmail() + "', "
			+ "PHONE_NUMBER = '" + emp.getPhoneNumber() + "', "
			+ "HIRE_DATE = to_date('" + hireDate + "', 'yyyyMMdd') , "
			+ "JOB_ID = '" + emp.getJobId() + "', "
			+ "SALARY = '" + emp.getSalary() + "', "
			+ "MANAGER_ID = '" + emp.getManagerId() + "', "
			+ "DEPARTMENT_ID = '" + emp.getDepartmentId() + "', "
			+ "WHERE "
			+ "EMPLOYEE_ID = " + emp.getEmployeeId();
			int affectedRows = statement.executeUpdate(query);
			oconn.getConn().commit();
			return affectedRows;
			}
			catch (SQLException ex ) { this.ex = ex; return 0; }
			}
		
		public int insertEmployee(Employee emp, OraConn oconn) {
			try (Statement statement =
			oconn.getConn().createStatement(); ) {
			DateTimeFormatter dtf =
			DateTimeFormatter.ofPattern("yyyyMMdd");
			String hireDate = dtf.format(emp.getHireDate());
			String query = "INSERT INTO EMPLOYEES VALUES("
			+ emp.getEmployeeId() + ","
			+ emp.getFirstName() + "," + emp.getLastName() + ","
			+ emp.getEmail() + "," + emp.getPhoneNumber() + ","
			+ hireDate + "," + emp.getJobId() + ","
			+ emp.getSalary() + "," + emp.getManagerId() + ","
			+ emp.getDepartmentId() + "); ";
			int affectedRows = statement.executeUpdate(query);
			oconn.getConn().commit();
			return affectedRows;
			}
			catch (SQLException ex ) { this.ex = ex; return 0; }
			}
		
		public Employee getEmployeeByEmployeeID(int id, OraConn oconn) {
			
			Employee emp = new Employee();
			
			try (Statement statement =oconn.getConn().createStatement();) {
				String query = "SELECT * FROM EMPLOYEES";
				ResultSet resultSet = statement.executeQuery(query);
				int rs_id = 0;
				while (resultSet.next()) {
					
					rs_id = getID(resultSet);
					if(rs_id == id)
						emp = rs2Employee(resultSet);
				}
			}
			
			catch (SQLException ex ) { System.out.println(ex); }
			return emp;
		}
		
		private int getID(ResultSet resultSet) {
			
			int id=0;
			try {
				id = resultSet.getInt(1);
			}
			catch (SQLException ex ) {
				this.ex = ex;
				}
			
			return id;
			
		}
		
		public int delEmployee(Employee emp, OraConn oconn) {
			try (Statement statement =
			oconn.getConn().createStatement(); ) {
			String query = "UPDATE FROM EMPLOYEES WHERE "
			+ "EMPLOYEE_ID = " + emp.getEmployeeId();
			int affectedRows = statement.executeUpdate(query);
			oconn.getConn().commit();
			return affectedRows;
			}
			catch (SQLException ex ) { this.ex = ex; return 0; }
			}

		public SQLException getEx() {
			return ex;
		}
}
