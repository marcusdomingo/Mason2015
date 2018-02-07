import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.sql.*;

public class Project1_Part4_mdomingo
{
	private JFrame frame;
	private String inputSsn;
	
	//New Employee values
	private JTextField Fname;
	private JTextField Minit;
	private JTextField Lname;
	private JTextField Ssn;
	private JTextField Bdate;
	private JTextField Address;
	private JTextField Sex;
	private JTextField Salary;
	private JTextField Superssn;
	private JTextField Dno;
	
	//Project hours
	private JTextField Hours1;
	private JTextField Hours2;
	private JTextField Hours3;
	private JTextField Hours4;
	private JTextField Hours5;
	private JTextField Hours6;
	
	//Dependent check boxes
	public static JCheckBox yesBox;
	public static JCheckBox noBox;
	
	//Dependent values
	private JTextField DepName;
	private JTextField DepSex;
	private JTextField DepBdate;
	private JTextField DepRelationship;
	
	//Dependent arrays
	private String DependentName[];
	private String DependentSex[];
	private String DependentBdate[];
	private String DependentRelationship[];
	
	public Project1_Part4_mdomingo()
	{
		frame = new JFrame();
		DependentName = null;
		DependentSex = null;
		DependentBdate = null;
		DependentRelationship = null;
	}
	
	public void askForSsn()
	{
		inputSsn = JOptionPane.showInputDialog(frame, "Please enter a manager SSN: ",
				"Manager SSN Required", JOptionPane.QUESTION_MESSAGE);
	}
	
	public void badSsn()
	{
		JOptionPane.showMessageDialog(null, "The provided SSN doesn't have authorization.\nGoodbye.",
				"Unauthorized Access", JOptionPane.ERROR_MESSAGE);
	}
	
	public int checkSsn() throws SQLException, IOException
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException x)
		{
			System.out.println("Driver could not be loaded");
		}
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", 
				"mdomingo", "phusee");
		String query = "SELECT Mgrssn FROM department WHERE Mgrssn="+getSsn();
		Statement s = conn.createStatement();
		ResultSet r = s.executeQuery(query);
		if(r.next())
		{
			s.close();
			conn.close();
			return 1;
		}
		
		s.close();
		conn.close();
		return 0;
	}
	
	public void newEmployee() throws SQLException
	{
		Fname = new JTextField(15);
		Minit = new JTextField(1);
		Lname = new JTextField(15);
		Ssn = new JTextField(9);
		Bdate = new JTextField(10);
		Address = new JTextField(20);
		Sex = new JTextField(1);
		Salary = new JTextField(8);
		Superssn = new JTextField(9);
		Dno = new JTextField(1);
		
		JPanel myPanel = new JPanel(new BorderLayout(5,5));
		JPanel labels = new JPanel(new GridLayout(0,1,2,2));
		JPanel controls = new JPanel(new GridLayout(0,1,2,2));
		
		labels.add(new JLabel("First name:", SwingConstants.RIGHT));
		controls.add(Fname);
		labels.add(new JLabel("Middle initial:", SwingConstants.RIGHT));
		controls.add(Minit);
		labels.add(new JLabel("Last name:", SwingConstants.RIGHT));
		controls.add(Lname);
		labels.add(new JLabel("Ssn(xxxxxxxxx):", SwingConstants.RIGHT));
		controls.add(Ssn);
		labels.add(new JLabel("Birth date(YYYY-MM-DD):", SwingConstants.RIGHT));
		controls.add(Bdate);
		labels.add(new JLabel("Address:", SwingConstants.RIGHT));
		controls.add(Address);
		labels.add(new JLabel("Sex(M for male, F for female, or O for other):", SwingConstants.RIGHT));
		controls.add(Sex);
		labels.add(new JLabel("Salary(example: 40000.50):", SwingConstants.RIGHT));
		controls.add(Salary);
		labels.add(new JLabel("Manager Ssn(xxxxxxxxx):", SwingConstants.RIGHT));
		controls.add(Superssn);
		labels.add(new JLabel("Department #:", SwingConstants.RIGHT));
		controls.add(Dno);
		
		myPanel.add(labels, BorderLayout.WEST);
		myPanel.add(controls,BorderLayout.CENTER);
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException x)
		{
			System.out.println("Driver could not be loaded");
		}
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", 
				"mdomingo", "phusee");
		
		String oldSsn, mgrSsn, depNum;
		int loopHolder1 = 0, loopHolder2 = 0, loopHolder3 = 0, loopHolder4 = 0, loopHolder5 = 0, loopHolder6 = 0;
		int result = JOptionPane.CANCEL_OPTION;
		
		while(!((loopHolder1 == 1) && (loopHolder2 == 1) && (loopHolder3 == 1) && (loopHolder4  == 1) && (loopHolder5  == 1) && (loopHolder6  == 1)))
		{
			result = JOptionPane.showConfirmDialog(null, myPanel, 
					"Please Enter All Fields", JOptionPane.OK_CANCEL_OPTION);
			
			if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
			{
				conn.close();
				JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
						"Terminated", JOptionPane.WARNING_MESSAGE);
				System.exit(1);
			}
			
			if(Fname.getText().trim().length() < 1)
			{
				JOptionPane.showMessageDialog(null, "Must enter a first name.", "Bad Name", JOptionPane.ERROR_MESSAGE);
				loopHolder1 = 0;
			}
			else
				loopHolder1 = 1;
			
			if(Lname.getText().trim().length() < 1)
			{
				JOptionPane.showMessageDialog(null, "Must enter a last name.", "Bad Name", JOptionPane.ERROR_MESSAGE);
				loopHolder2 = 0;
			}
			else
				loopHolder2 = 1;
			
			if(Ssn.getText().trim().length() == 9)
			{
				try
				{
					int d = Integer.valueOf(Ssn.getText().trim());
				
					oldSsn = "SELECT Ssn FROM employee WHERE Ssn="+Ssn.getText().trim();
					Statement s = conn.createStatement();
					ResultSet r = s.executeQuery(oldSsn);
					if(r.next())
					{
						JOptionPane.showMessageDialog(null, "The ssn '" + Ssn.getText().trim() + "' is already in use. Try again.", "Bad Ssn", JOptionPane.ERROR_MESSAGE);
						loopHolder3 = 0;
					}
					else
						loopHolder3 = 1;
					
					s.close();
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Ssn format is incorrect. Try again.", "Bad Ssn", JOptionPane.ERROR_MESSAGE);
					loopHolder3 = 0;
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The ssn needs to be 9 digits. Try again.", "Bad Ssn", JOptionPane.ERROR_MESSAGE);
				loopHolder3 = 0;
			}
			
			if(Superssn.getText().trim().length() == 9)
			{
				try
				{
					int d = Integer.valueOf(Superssn.getText().trim());
					
					mgrSsn = "SELECT Mgrssn FROM department WHERE Mgrssn="+Superssn.getText().trim();
					Statement s = conn.createStatement();
					ResultSet r = s.executeQuery(mgrSsn);
					if(r.next())
					{
						loopHolder4 = 1;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The ssn '" + Superssn.getText().trim() + "' is not a manager ssn. Try again.", "Bad Ssn", JOptionPane.ERROR_MESSAGE);
						loopHolder4 = 0;
					}
					
					s.close();
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Manager ssn format is incorrect. Try again.", "Bad Ssn", JOptionPane.ERROR_MESSAGE);
					loopHolder3 = 0;
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The manager ssn needs to be 9 digits. Try again.", "Bad Ssn", JOptionPane.ERROR_MESSAGE);
				loopHolder4 = 0;
			}
			
			if(Dno.getText().trim().length() > 0)
			{
				depNum = "SELECT Dnumber FROM department WHERE dnumber="+Integer.parseInt(Dno.getText().trim());
				Statement s = conn.createStatement();
				ResultSet r = s.executeQuery(depNum);
				if(r.next())
				{
					loopHolder5 = 1;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The department #" + Dno.getText().trim() + " is not a valid department. Try again.", "Bad Department", JOptionPane.ERROR_MESSAGE);
					loopHolder5 = 0;
				}
				
				s.close();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Must enter a department #.", "Bad Department", JOptionPane.ERROR_MESSAGE);
				loopHolder5 = 0;
			}
			
			if(Bdate.getText().trim().length() < 1)
			{
				JOptionPane.showMessageDialog(null, "Must enter a birth date.", "Bad Birth Date", JOptionPane.ERROR_MESSAGE);
				loopHolder6 = 0;
			}
			else
			{
				try
				{
					Date d = Date.valueOf(Bdate.getText().trim());
					loopHolder6 = 1;
					
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Birth date format is incorrect. Try again.", "Bad Birth Date", JOptionPane.ERROR_MESSAGE);
					loopHolder6 = 0;
				}
			}
			
			if(Fname.getText().trim().length() > 15)
				Fname.setText(Fname.getText().trim().substring(0, 15));
			if(Minit.getText().trim().length() < 1)
				Minit.setText("/");
			if(Lname.getText().trim().length() > 15)
				Lname.setText(Lname.getText().trim().substring(0, 15));
			if(Address.getText().trim().length() < 1)
				Address.setText("/");
			if(Address.getText().trim().length() > 30)
				Address.setText(Address.getText().trim().substring(0, 30));
			if(Sex.getText().trim().length() < 1)
				Sex.setText("/");
			if(Salary.getText().trim().length() < 1)
				Salary.setText("0");
			if(Salary.getText().trim().length() > 10)
				Salary.setText(Salary.getText().trim().substring(0, 10));
		}
		
		conn.close();
		
		if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
		{
			JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
					"Terminated", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
	}
	
	public void employeeProjects()
	{	
		Hours1 = new JTextField(15);
		Hours2 = new JTextField(15);
		Hours3 = new JTextField(15);
		Hours4 = new JTextField(15);
		Hours5 = new JTextField(15);
		Hours6 = new JTextField(15);
			
		JPanel myPanel = new JPanel(new BorderLayout(5,5));
		JPanel labels = new JPanel(new GridLayout(0,1,2,2));
		JPanel controls = new JPanel(new GridLayout(0,1,2,2));
		
		labels.add(new JLabel("", SwingConstants.RIGHT));
		controls.add(new JLabel("Please enter the hours for each project. Enter a 0 if no hours."));
		labels.add(new JLabel("", SwingConstants.RIGHT));
		controls.add(new JLabel("Hours must NOT exceed 40 hours."));
		
		labels.add(new JLabel("ProductX Hours:", SwingConstants.RIGHT));
		controls.add(Hours1);
		labels.add(new JLabel("ProductY Hours:", SwingConstants.RIGHT));
		controls.add(Hours2);
		labels.add(new JLabel("ProductZ Hours:", SwingConstants.RIGHT));
		controls.add(Hours3);
		labels.add(new JLabel("Computerization Hours:", SwingConstants.RIGHT));
		controls.add(Hours4);
		labels.add(new JLabel("Reorganization Hours:", SwingConstants.RIGHT));
		controls.add(Hours5);
		labels.add(new JLabel("Newbenefits Hours:", SwingConstants.RIGHT));
		controls.add(Hours6);
			
		myPanel.add(labels, BorderLayout.WEST);
		myPanel.add(controls,BorderLayout.CENTER);
			
		double totHours = -1;
			
		while(totHours < 0 || totHours > 40)
		{
			totHours = -1;
			int result = JOptionPane.CANCEL_OPTION;
			int loopHolder1 = 0, loopHolder2 = 0, loopHolder3 = 0, loopHolder4 = 0, loopHolder5 = 0, loopHolder6 = 0;
			
			while(!((loopHolder1 == 1) && (loopHolder2 == 1) && (loopHolder3 == 1) && (loopHolder4  == 1) && (loopHolder5  == 1) && (loopHolder6  == 1)))
			{
				result = JOptionPane.showConfirmDialog(null, myPanel, 
						"Project Hours", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
				{
					JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
							"Terminated", JOptionPane.WARNING_MESSAGE);
					System.exit(1);
				}
				
				if(Hours1.getText().trim().length() < 1)
					Hours1.setText("0");
				else
				{
					try
					{
						Double d = Double.valueOf(Hours1.getText().trim());
						loopHolder1 = 1;
						
						if(d < 0)
						{
							JOptionPane.showMessageDialog(null, "ProductX hours are negative. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
							loopHolder1 = 0;
						}
						
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "ProductX hours not entered correctly. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
						loopHolder1 = 0;
					}
				}
				
				if(Hours2.getText().trim().length() < 1)
					Hours2.setText("0");
				else
				{
					try
					{
						Double d = Double.valueOf(Hours2.getText().trim());
						loopHolder2 = 1;
						
						if(d < 0)
						{
							JOptionPane.showMessageDialog(null, "ProductY hours are negative. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
							loopHolder2 = 0;
						}
						
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "ProductY hours not entered correctly. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
						loopHolder2 = 0;
					}
				}
				
				if(Hours3.getText().trim().length() < 1)
					Hours3.setText("0");
				else
				{
					try
					{
						Double d = Double.valueOf(Hours3.getText().trim());
						loopHolder3 = 1;
						
						if(d < 0)
						{
							JOptionPane.showMessageDialog(null, "ProductZ hours are negative. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
							loopHolder3 = 0;
						}
						
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "ProductZ hours not entered correctly. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
						loopHolder3 = 0;
					}
				}
				
				if(Hours4.getText().trim().length() < 1)
					Hours4.setText("0");
				else
				{
					try
					{
						Double d = Double.valueOf(Hours4.getText().trim());
						loopHolder4 = 1;
						
						if(d < 0)
						{
							JOptionPane.showMessageDialog(null, "Computerization hours are negative. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
							loopHolder4 = 0;
						}
						
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Computerization hours not entered correctly. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
						loopHolder4 = 0;
					}
				}
				
				if(Hours5.getText().trim().length() < 1)
					Hours5.setText("0");
				else
				{
					try
					{
						Double d = Double.valueOf(Hours5.getText().trim());
						loopHolder5 = 1;
						
						if(d < 0)
						{
							JOptionPane.showMessageDialog(null, "Reorganization hours are negative. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
							loopHolder5 = 0;
						}
						
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Reorganization hours not entered correctly. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
						loopHolder5 = 0;
					}
				}
				
				if(Hours6.getText().trim().length() < 1)
					Hours6.setText("0");
				else
				{
					try
					{
						Double d = Double.valueOf(Hours6.getText().trim());
						loopHolder6 = 1;
						
						if(d < 0)
						{
							JOptionPane.showMessageDialog(null, "Newbenefits hours are negative. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
							loopHolder6 = 0;
						}
						
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Newbenefits hours not entered correctly. Try again.", "Bad Hours", JOptionPane.ERROR_MESSAGE);
						loopHolder6 = 0;
					}
				}
			}
				if(result == JOptionPane.OK_OPTION)
				{		
					totHours += 1;
					totHours += Double.parseDouble(Hours1.getText().trim());
					totHours += Double.parseDouble(Hours2.getText().trim());
					totHours += Double.parseDouble(Hours3.getText().trim());
					totHours += Double.parseDouble(Hours4.getText().trim());
					totHours += Double.parseDouble(Hours5.getText().trim());
					totHours += Double.parseDouble(Hours6.getText().trim());
				}
				else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
				{
					JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
							"Terminated", JOptionPane.WARNING_MESSAGE);
					System.exit(1);
				}
				
				if(totHours < 0 || totHours > 40)
					JOptionPane.showMessageDialog(null, "Total hours entered was: '" + totHours +"'.\n"
							+ "Must be in the range of 0 to 40 hours.", "Bad Entry", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void empDependents()
	{
		Listener listener = new Listener();
		
		JPanel myPanel = new JPanel(new BorderLayout(5,5));
		JPanel labels = new JPanel(new GridLayout(1,1,2,2));
		JPanel boxes = new JPanel(new GridLayout(1,2,2,2));
		yesBox = new JCheckBox("Yes");
		noBox = new JCheckBox("No");
		yesBox.addItemListener(listener);
		noBox.addItemListener(listener);
		labels.add(new JLabel("Does the employee have any dependents?"));
		boxes.add(yesBox);
		boxes.add(noBox);
		
		myPanel.add(labels, BorderLayout.WEST);
		myPanel.add(boxes, BorderLayout.CENTER);
		
		int result1 = JOptionPane.OK_OPTION;
		
		while(!yesBox.isSelected() && !noBox.isSelected() && result1 == JOptionPane.OK_OPTION)
		{
			result1 = JOptionPane.showConfirmDialog(null, myPanel, 
				"Please Enter All Fields", JOptionPane.DEFAULT_OPTION);
			
			if(!yesBox.isSelected() && !noBox.isSelected() && result1 == JOptionPane.OK_OPTION)
				JOptionPane.showMessageDialog(null, "Please choose an option.", "Must Choose", JOptionPane.WARNING_MESSAGE);
				
		}
		
		if(result1 == JOptionPane.OK_OPTION)
		{
			if(yesBox.isSelected())
			{
				
				int numDependents = -1;
				while(numDependents < 1)
				{
					try
					{
						numDependents = Integer.parseInt(JOptionPane.showInputDialog(frame, 
								"Please enter the number of dependents for the new Employee (min is 1):", 
								"Number of Dependents?", JOptionPane.QUESTION_MESSAGE));
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
								"Terminated", JOptionPane.WARNING_MESSAGE);
						System.exit(1);
					}
					
					if(numDependents < 1)
							JOptionPane.showMessageDialog(null, "Number of dependents invalid. Please try again.", 
									"Bad Entry", JOptionPane.WARNING_MESSAGE);
				}
				
				DependentName = new String[numDependents];
				DependentSex = new String[numDependents];
				DependentBdate = new String[numDependents];
				DependentRelationship = new String[numDependents];
				
				DepName = new JTextField(15);
				DepSex = new JTextField(1);
				DepBdate = new JTextField(10);
				DepRelationship = new JTextField(8);
				
				JPanel myPanel1 = new JPanel(new BorderLayout(5,5));
				JPanel labels1 = new JPanel(new GridLayout(0,1,2,2));
				JPanel controls1 = new JPanel(new GridLayout(0,1,2,2));
				
				labels1.add(new JLabel("First Name:", SwingConstants.RIGHT));
				controls1.add(DepName);
				labels1.add(new JLabel("Sex:", SwingConstants.RIGHT));
				controls1.add(DepSex);
				labels1.add(new JLabel("Birth date(YYYY-MM-DD):", SwingConstants.RIGHT));
				controls1.add(DepBdate);
				labels1.add(new JLabel("Relationship:", SwingConstants.RIGHT));
				controls1.add(DepRelationship);
				
				myPanel1.add(labels1, BorderLayout.WEST);
				myPanel1.add(controls1, BorderLayout.CENTER);
				
				for(int i = 0; i < numDependents; i++)
				{
					int loopHolder1 = 0, loopHolder2 = 0;
					int result2 = JOptionPane.CANCEL_OPTION;
					
					while(!((loopHolder1 == 1) && (loopHolder2 == 1)))
					{
						result2 = JOptionPane.showConfirmDialog(null, myPanel1, 
								"For Dependent " + (i+1), JOptionPane.OK_CANCEL_OPTION);
						
						if (result2 == JOptionPane.CANCEL_OPTION || result2 == JOptionPane.CLOSED_OPTION)
						{
							JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
									"Terminated", JOptionPane.WARNING_MESSAGE);
							System.exit(1);
						}
						
						if(DepName.getText().trim().length() < 1)
						{
							JOptionPane.showMessageDialog(null, "Must enter a first name.", "Bad Name", JOptionPane.ERROR_MESSAGE);
							loopHolder1 = 0;
						}
						else
							loopHolder1 = 1;
						
						if(DepBdate.getText().trim().length() < 1)
						{
							JOptionPane.showMessageDialog(null, "Must enter a birth date.", "Bad Birth Date", JOptionPane.ERROR_MESSAGE);
							loopHolder2 = 0;
						}
						else
						{
							try
							{
								Date d = Date.valueOf(DepBdate.getText().trim());
								loopHolder2 = 1;
								
							}catch(Exception e)
							{
								JOptionPane.showMessageDialog(null, "Birth date format is incorrect. Try again.", "Bad Birth Date", JOptionPane.ERROR_MESSAGE);
								loopHolder2 = 0;
							}
						}
						
						if(DepName.getText().trim().length() > 15)
							DepName.setText(DepName.getText().trim().substring(0, 15));
						if(DepSex.getText().trim().length() < 1)
							DepSex.setText("/");
						if(DepRelationship.getText().trim().length() < 1)
							DepRelationship.setText("/");
						if(DepRelationship.getText().trim().length() > 8)
							DepRelationship.setText(DepRelationship.getText().trim().substring(0, 8));
					}
					if(result2 == JOptionPane.OK_OPTION)
					{
						DependentName[i] = DepName.getText();
						DependentSex[i] = DepSex.getText();
						DependentBdate[i] = DepBdate.getText();
						DependentRelationship[i] = DepRelationship.getText();
					
						DepName.setText("");
						DepSex.setText("");
						DepBdate.setText("");
						DepRelationship.setText("");
					}
					else if(result2 == JOptionPane.CANCEL_OPTION || result2 == JOptionPane.CLOSED_OPTION)
					{
						JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
								"Terminated", JOptionPane.WARNING_MESSAGE);
						System.exit(1);
					}
				}
			}
		}
		else if(result1 == JOptionPane.CANCEL_OPTION || result1 == JOptionPane.CLOSED_OPTION)
		{
			JOptionPane.showMessageDialog(null, "Entry was canceled or closed.",
					"Terminated", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
	}
	
	public void updateDatabase() throws SQLException, IOException
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException x)
		{
			System.out.println("Driver could not be loaded");
		}
		
		Double h1 = Double.parseDouble(Hours1.getText().trim());
		Double h2 = Double.parseDouble(Hours2.getText().trim());
		Double h3 = Double.parseDouble(Hours3.getText().trim());
		Double h4 = Double.parseDouble(Hours4.getText().trim());
		Double h5 = Double.parseDouble(Hours5.getText().trim());
		Double h6 = Double.parseDouble(Hours6.getText().trim());
		
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", 
					"mdomingo", "phusee");
			String addEmployee = "INSERT INTO employee(Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Superssn, Dno) VALUES(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement prepS = conn.prepareStatement(addEmployee);
			prepS.setString(1, Fname.getText().trim());
			prepS.setString(2, Character.toString(Minit.getText().trim().charAt(0)));
			prepS.setString(3, Lname.getText().trim());
			prepS.setString(4, Ssn.getText().trim());
			prepS.setDate(5, Date.valueOf(Bdate.getText().trim()));
			prepS.setString(6, Address.getText().trim());
			prepS.setString(7, Character.toString(Sex.getText().trim().charAt(0)));
			prepS.setDouble(8, Double.parseDouble(Salary.getText().trim()));
			prepS.setString(9, Superssn.getText().trim());
			prepS.setInt(10, Integer.parseInt(Dno.getText().trim()));
			prepS.executeUpdate();
			if(h1 > 0 || h2 > 0 || h3 > 0 || h4 > 0 || h5 > 0 || h6 > 0)
			{
				String addProject = "INSERT INTO works_on(Essn, Pno, Hours) VALUES(?,?,?)";
				prepS = conn.prepareStatement(addProject);
				if(h1 > 0)
				{
					prepS.setString(1, Ssn.getText().trim());
					prepS.setInt(2, 1);
					prepS.setDouble(3, h1);
					prepS.executeUpdate();
				}
				if(h2 > 0)
				{
					prepS.setString(1, Ssn.getText().trim());
					prepS.setInt(2, 2);
					prepS.setDouble(3, h2);
					prepS.executeUpdate();
				}
				if(h3 > 0)
				{
					prepS.setString(1, Ssn.getText().trim());
					prepS.setInt(2, 3);
					prepS.setDouble(3, h3);
					prepS.executeUpdate();
				}
				if(h4 > 0)
				{
					prepS.setString(1, Ssn.getText().trim());
					prepS.setInt(2, 10);
					prepS.setDouble(3, h4);
					prepS.executeUpdate();
				}
				if(h5 > 0)
				{
					prepS.setString(1, Ssn.getText().trim());
					prepS.setInt(2, 20);
					prepS.setDouble(3, h5);
					prepS.executeUpdate();
				}
				if(h6 > 0)
				{
					prepS.setString(1, Ssn.getText().trim());
					prepS.setInt(2, 30);
					prepS.setDouble(3, h6);
					prepS.executeUpdate();
				}
			}
			
			
			if(DependentName != null)
			{
				String addDependent = "INSERT INTO dependent(Essn, Dependent_name, Sex, Bdate, Relationship) "
						+ "VALUES(?,?,?,?,?)";
				prepS = conn.prepareStatement(addDependent);
				
				for(int i = 0; i < DependentName.length; i++)
				{	
					prepS.setString(1, Ssn.getText().trim());
					prepS.setString(2, DependentName[i].trim());
					prepS.setString(3, Character.toString(DependentSex[i].trim().charAt(0)));
					prepS.setDate(4, Date.valueOf(DependentBdate[i].trim()));
					prepS.setString(5, DependentRelationship[i].trim());
					prepS.executeUpdate();
				}
			}
			
			prepS.close();
			conn.close();
		
		}catch(SQLException e){}
		
	}
	
	public void printReport() throws SQLException, IOException
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException x)
		{
			System.out.println("Driver could not be loaded");
		}
		
		JPanel myPanel = new JPanel(new BorderLayout(5,5));
		JPanel labelsleft = new JPanel(new GridLayout(0,1,2,2));
		JPanel labelsright = new JPanel(new GridLayout(0,1,2,2));
		String fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno, pname, hours, depname, relationship;
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", 
				"mdomingo", "phusee");
		String queryEmp = "SELECT Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Superssn, Dno FROM employee WHERE Ssn="+Ssn.getText();
		String queryProj = "SELECT Pname, Hours FROM works_on, project WHERE Pno=Pnumber AND Essn="+Ssn.getText();
		String queryDependents = "SELECT Dependent_name, Sex, Bdate, Relationship FROM dependent WHERE Essn="+Ssn.getText();
		Statement s = conn.createStatement();
		ResultSet r = s.executeQuery(queryEmp);
		labelsleft.add(new JLabel("", SwingConstants.RIGHT));
		labelsright.add(new JLabel("<html><b>New Employee Information</b></html>")).setFont(new Font("serif", Font.PLAIN, 14));
		labelsleft.add(new JLabel("First name: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Middle initial: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Last name: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Ssn: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Birth date: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Address: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Sex: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Salary: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Manager Ssn: ", SwingConstants.RIGHT));
		labelsleft.add(new JLabel("Department #: ", SwingConstants.RIGHT));
		while(r.next())
		{
			fname = r.getString(1);
			minit = r.getString(2);
			lname = r.getString(3);
			ssn = r.getString(4);
			bdate = r.getString(5).substring(0, 10);
			address = r.getString(6);
			sex = r.getString(7);
			salary = r.getString(8);
			superssn = r.getString(9);
			dno = r.getString(10);
			
			labelsright.add(new JLabel(fname));
			labelsright.add(new JLabel(minit));
			labelsright.add(new JLabel(lname));
			labelsright.add(new JLabel(ssn));
			labelsright.add(new JLabel(bdate));
			labelsright.add(new JLabel(address));
			labelsright.add(new JLabel(sex));
			labelsright.add(new JLabel(salary));
			labelsright.add(new JLabel(superssn));
			labelsright.add(new JLabel(dno));
			
		}
		
		r = s.executeQuery(queryProj);
		labelsleft.add(new JLabel("", SwingConstants.RIGHT));
		labelsright.add(new JLabel("<html><b>Assigned Projects</b></html>")).setFont(new Font("serif", Font.PLAIN, 14));
		while(r.next())
		{
			pname = r.getString(1);
			hours = r.getString(2);
			
			labelsleft.add(new JLabel("Project name: ", SwingConstants.RIGHT));
			labelsleft.add(new JLabel("Hours: ", SwingConstants.RIGHT));
			labelsright.add(new JLabel(pname));
			labelsright.add(new JLabel(hours));
		}
		
		r = s.executeQuery(queryDependents);
		labelsleft.add(new JLabel("", SwingConstants.RIGHT));
		labelsright.add(new JLabel("<html><b>Dependents</b></html>")).setFont(new Font("serif", Font.PLAIN, 14));
		while(r.next())
		{
			depname = r.getString(1);
			sex = r.getString(2);
			bdate = r.getString(3).substring(0, 10);
			relationship = r.getString(4);
			
			labelsleft.add(new JLabel("First name: ", SwingConstants.RIGHT));
			labelsleft.add(new JLabel("Sex: ", SwingConstants.RIGHT));
			labelsleft.add(new JLabel("Birth date: ", SwingConstants.RIGHT));
			labelsleft.add(new JLabel("Relationship: ", SwingConstants.RIGHT));
			labelsright.add(new JLabel(depname));
			labelsright.add(new JLabel(sex));
			labelsright.add(new JLabel(bdate));
			labelsright.add(new JLabel(relationship));
		}
		
		myPanel.add(labelsleft, BorderLayout.WEST);
		myPanel.add(labelsright, BorderLayout.CENTER);
		
		int result2 = JOptionPane.showConfirmDialog(null, myPanel, 
				"New Employee Report", JOptionPane.DEFAULT_OPTION);
			
		s.close();
		conn.close();
	}
	
	static class Listener implements ItemListener
	{	
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JCheckBox source = (JCheckBox) e.getSource();
			
			if(source.isSelected())
			{
				if(source == yesBox)
					noBox.setEnabled(false);
				else
					yesBox.setEnabled(false);
			}
			else
			{
				if(source == yesBox)
					noBox.setEnabled(true);
				else
					yesBox.setEnabled(true);
			}
		}
	}
	
	public String getSsn()
	{
		return this.inputSsn;
	}
	
	public static void main(String [] args) throws SQLException, IOException
	{
		Project1_Part4_mdomingo gui = new Project1_Part4_mdomingo();
		
		gui.askForSsn();
		
		if(gui.checkSsn() != 1)
		{
			gui.badSsn();
			System.exit(0);
		}
		
		gui.newEmployee();
		gui.employeeProjects();
		gui.empDependents();
		gui.updateDatabase();
		gui.printReport();
	}

}
