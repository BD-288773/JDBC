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
			
			
			try (Statement statement = oconn.getConn().createStatement();) {
				String query = "SELECT * FROM EMPLOYEES";
				ResultSet resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
				employees.add(rs2Employee(resultSet));
				}
			}
			
			catch (SQLException ex ) { this.ex=ex; return null; }
			
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
				emp.setCommisionpct(resultSet.getFloat(col++));
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
			String query = String.format("UPDATE EMPLOYEES SET "
			+ "FIRST_NAME = '%s', LAST_NAME = '%s', "
			+ "EMAIL = '%s', PHONE_NUMBER = '%s', "
			+ "HIRE_DATE = DATE '%s', JOB_ID = '%s'"
			+ "SALARY = %f, COMMISSION_PCT = %f,"
			+ "MANAGER_ID = %d, DEPARTMENT_ID = %d"
			+ "WHERE EMPLOYEE_ID = %d",
			emp.getFirstName(),	emp.getLastName(),
			emp.getEmail(), emp.getPhoneNumber(),
			DateTimeFormatter.ofPattern("yy-MM-dd").format(emp.getHireDate()),
			emp.getJobId(), emp.getSalary(),
			emp.getCommisionpct(), emp.getManagerId(),
			emp.getDepartmentId(), emp.getEmployeeId());
			int affectedRows = statement.executeUpdate(query);

			System.out.println(emp.getSalary());
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
			
			Employee e=this.getEmployeeByEmployeeID(emp.getEmployeeId(), oconn);
			
			boolean y=false;
			
			while(y==false)
			{
				System.out.println("ads");
				if(e==null)
				{
					y = true;
					break;
				}
				else
				{
					System.out.println("ADS");
					emp.setEmployeeId(emp.getEmployeeId()+1);
					System.out.println("Adsaa");
					e=this.getEmployeeByEmployeeID(emp.getEmployeeId(), oconn);	
				}
				
				System.out.println(emp.getEmployeeId());
			}
						
			String query = "INSERT INTO EMPLOYEES VALUES("
			+ emp.getEmployeeId() + ", '"
			+ emp.getFirstName() + "', '" + emp.getLastName() + "', '"
			+ emp.getEmail() + "', '" + emp.getPhoneNumber() + "', to_date('"
			+ hireDate + "', 'yyyyMMdd'), '" + emp.getJobId() + "',"
			+ emp.getSalary() + "," + emp.getCommisionpct() + ","
			+ emp.getManagerId() + "," + emp.getDepartmentId() + ")";
			int affectedRows = statement.executeUpdate(query);
			oconn.getConn().commit();
			return affectedRows;
			}
			catch (SQLException ex ) { this.ex = ex; return 0; }
			}
		
		public Employee getEmployeeByEmployeeID(int id, OraConn oconn) {
			
			Employee emp = new Employee();
			
			try (Statement statement =oconn.getConn().createStatement();) {
				String query = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID  = " + id;
				ResultSet resultSet = statement.executeQuery(query);
				
				resultSet.next();
				emp = rs2Employee(resultSet);
				
				if(emp==null)
					return null;
			}
			
			catch (SQLException ex ) { this.ex=ex; return null;}
			return emp;
		}
		
		public int delEmployee(Employee emp, OraConn oconn) {
			try (Statement statement =
			oconn.getConn().createStatement(); ) {
				
			Employee e = this.getEmployeeByEmployeeID(emp.getEmployeeId(), oconn);
			
			if(e==null)
			{
				return 0;
			}
			
			String query = "DELETE FROM EMPLOYEES WHERE "
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
