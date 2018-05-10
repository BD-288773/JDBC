package dal;

import java.util.ArrayList;
import java.util.List;

import bd.OraConn;

public class DALTest {

	public static void main(String[] args) {
		
		OraConn oConnection = new OraConn("ssidoruk","ssidoruk","@ora3.elka.pw.edu.pl:1521:ora3inf");
		
		oConnection.open();
		
		List<Employee> employees= new ArrayList<Employee>();
		
		EmployeesDAL dal = new EmployeesDAL();
		
		employees = dal.getEmployees(oConnection);
		
		int i=0;
		
		while(i < employees.size()) {
			System.out.println(employees.get(i).toString());
			i++;
		}
		
		i--;
		System.out.println("\n\n");
		
		Employee e = null;
		
		e = dal.getEmployeeByEmployeeID(196, oConnection);
		
		if(e!=null) {
			System.out.println(e.toString());
			}
		
		System.out.println("\n\n");
		
		//dal.delEmployee(e, oConnection);
		
		//employees = dal.getEmployees(oConnection);
		
		//i=0;
		
		//while(i < employees.size()) {
		//	System.out.println(employees.get(i).toString());
		//	i++;
		//}
		
		//i--;
		
		//System.out.println("\n\n");
				
		if(e!=null)
		{
			e.setSalary(3500);
			System.out.println(e.getSalary());
		}
		
		//dal.insertEmployee(e, oConnection);
		
		dal.updateEmployee(e, oConnection);
		
		
		employees = dal.getEmployees(oConnection);
		
		i=0;
		
		while(i < employees.size()) {
			System.out.println(employees.get(i).toString());
			i++;
		}
		
		System.out.println("\n\n");
		
		if(e!=null) {
			System.out.println(e.toString());
			}
		
		oConnection.close();
	}

}
