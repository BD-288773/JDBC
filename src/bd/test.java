package bd;

public class test {

	public static void main(String[] args) {
		
		OraConn oconn = new OraConn("sidoruk","ssidoruk","@ora3.elka.pw.edu.pl:1521:ora3inf");
		
		oconn.open();
		String s=oconn.getLog();
		System.out.println(s + "\n");
		//oconn.close();
		s=oconn.getLog();
		System.out.println(s + "\n");
		oconn.setUser_name("ssidoruk");
		oconn.open();
		oconn.close();
		s=oconn.getLog();
		System.out.println(s);
		
	}

}
