import java.io.*;
import java.sql.*;

public class Project1_Part2_mdomingo
{
	public static void main(String [] args) throws SQLException, IOException
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException x)
		{
			System.out.println("Driver could not be loaded");
		}
		
		String lname;
		int ssn;
		double hours;
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", "mdomingo", "phusee");
		String query1 = "SELECT Lname, Ssn "
				+ "FROM employee, department "
				+ "WHERE Dno=Dnumber AND Dname='Research'";
		String query2 = "SELECT DISTINCT Lname, Ssn, Hours "
				+ "FROM employee, dept_locations, project, works_on "
				+ "WHERE Dno=Dnumber AND Dlocation='Houston' AND Pname='ProductZ' AND Dnumber=Dnum AND Ssn=Essn AND Pno=Pnumber";
		Statement s = conn.createStatement();
		System.out.println("Query 1:");
		ResultSet r = s.executeQuery(query1);
		while(r.next())
		{
			lname = r.getString(1);
			ssn = r.getInt(2);
			System.out.printf("%-10s %d\n",lname, ssn);
		}
		
		System.out.println();
		System.out.println("Query 2:");
		r = s.executeQuery(query2);
		while(r.next())
		{
			lname = r.getString(1);
			ssn = r.getInt(2);
			hours = r.getDouble(3);
			System.out.printf("%-10s %-10d %.2f\n", lname, ssn, hours);
		}
		
		s.close();
		conn.close();
	}

}
