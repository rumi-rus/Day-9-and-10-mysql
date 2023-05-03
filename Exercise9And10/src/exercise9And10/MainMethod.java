package exercise9And10;
import java.util.*;
import java.sql.*;

public class MainMethod {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
        Scanner sc=new Scanner(System.in);
        Scanner scint=new Scanner(System.in);
        int ch;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstDB","root","")) {
                Statement stmt = con.createStatement();
              	      
                 String sql = "CREATE TABLE EmployeesAndSalary" +
                          "(empId INTEGER not NULL, " +
                          " empName VARCHAR(255), " +
                          " salary INTEGER, " + 
                          " PRIMARY KEY ( empId ))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");   	  
             
        do {
        	System.out.println("-------------------------------------------");
        	System.out.println("1.Insert");
        	System.out.println("2.Select All Employee");
        	System.out.println("3.Select Employee with id ");
        	System.out.println("4.Select Employee with id using called procedure ");
        	System.out.println("5.Delete");
        	System.out.println("6.Update");
        	System.out.println("7.Maximum Salary");
        	System.out.println("Enter your choice");
        	ch=scint.nextInt();
        	System.out.println("-------------------------------------------");
        	switch(ch){
        	
            case 1:
          	 
          		   sql = "INSERT INTO EmployeesAndSalary (empId,empName,salary) VALUES (?, ?, ?)";
              	  
              	  PreparedStatement preparedstatement = con.prepareStatement(sql);
              	  System.out.println("Enter the empno:");
              	  int empNo=scint.nextInt();
              	  
              	  System.out.println("Enter the empnname:");
              	  String empName=sc.nextLine();
              	  
              	  System.out.println("Enter the Salary:");
              	  int empSalary=scint.nextInt();
              	  
              	  preparedstatement.setInt(1, empNo);
              	  preparedstatement.setString(2, empName);
              	  preparedstatement.setInt(3, empSalary);
              	  
              	   
              	  int rowsInserted = preparedstatement.executeUpdate();
              	  if (rowsInserted > 0) {
              	      System.out.println("\nA new employee was inserted successfully..");
              	  }
          		     
            break;
            
            case 2:
            	sql = "SELECT * FROM EmployeesAndSalary";
            	
               
            	stmt=con.createStatement();
            	ResultSet result = stmt.executeQuery(sql);
            	 
            	int count = 0;
            	 
            	while (result.next()){
            	    int id=result.getInt("empId");
            	    String fullname = result.getString("empName");
            	    String salary = result.getString("salary");
            	 
            	   
            	    System.out.println(id+" "+fullname+" "+ salary);
            	}
            	            	
            break;
            
            case 3:
            	System.out.println("Enter the id to select the employee" );
            	int id=scint.nextInt();
                sql = "SELECT * FROM EmployeesAndSalary WHERE empId="+id;
            	
            	
                stmt=con.createStatement();
            	 result = stmt.executeQuery(sql);
       
            	while (result.next()){
            	    if(result.getInt("empId")==id) {
            	    String fullname = result.getString("empName");
            	    String salary = result.getString("salary");
            	    System.out.println(id+" "+fullname+" "+ salary);
            	}
            	    else {
            	    	System.out.println("\nNo such employee with id "+id);
            	    }
            	}
            break;	
            
            case 4:
            	System.out.println("Enter the id to select the employee " );
            	int x=scint.nextInt();
            	CallableStatement callStatement =con.prepareCall("{call get_all(?)}");
            	callStatement.setInt(1,x);
            	result=callStatement.executeQuery();
            	while(result.next())
            	{
            		if(result.getInt("empId")==x) {
            		id=result.getInt("empId");
            		String fullname = result.getString("empName");
            	    String salary = result.getString("salary");
            	    System.out.println(id+" "+fullname+" "+ salary);
            		}
            	}
            	boolean res=callStatement.execute();
            	if (!res ) {
            		System.out.println("\nNo such user...");
         		}
            	
            break;
            
            case 5:
            	System.out.println("Enter the id to delete the employee" );
            	id=scint.nextInt();
                sql = "DELETE FROM EmployeesAndSalary WHERE empId=? ";
            	
                preparedstatement = con.prepareStatement(sql);
                preparedstatement.setInt(1,id);
            	
            	 int rowsDeleted = preparedstatement.executeUpdate();
         		if (rowsDeleted > 0) {
         		    System.out.println("\nAn existing employee was deleted successfully!");
         		}
         		else {
         			System.out.println("\nNo such employee");
         		}
            	
            break;	
            
            case 6:
            	System.out.println("Enter the id to update the employee" );
            	id=scint.nextInt();
                sql = "SELECT * FROM EmployeesAndSalary WHERE empId="+id;
            	
            	 stmt = con.createStatement();
            	 result = stmt.executeQuery(sql);
       
            	while (result.next()){
            	    if(result.getInt("empId")==id) {
            	    String fullname = result.getString("empName");
            	    String salary = result.getString("salary");
            	    System.out.println(id+" "+fullname+" "+ salary);
            	
            	    
            	    System.out.println("What to update?");
                	System.out.println("1.Employee name");
                	System.out.println("2.Employee Salary");
                	int option=scint.nextInt();
                	switch (option) {
                	case 1:
                		System.out.println("Enter new name to update: ");
                		String newName=sc.nextLine();
                		sql="UPDATE EmployeesAndSalary SET empName = ? WHERE empId = ?";
                		
                		PreparedStatement pstatement = con.prepareStatement(sql);
                		pstatement.setString(1, newName);
                		pstatement.setInt(2, id);
                		int rowsUpdated = pstatement.executeUpdate();
                		if (rowsUpdated > 0) {
                		    System.out.println("\nAn existing user was updated successfully!");
                		}
                		else {
                 			System.out.println("\nNo such employee");
                 		}
                		break;
                		
                	case 2:
                		System.out.println("Enter new salary to update: ");
                		int newSalary=scint.nextInt();
                		sql="UPDATE EmployeesAndSalary SET salary = ? WHERE empId = ?";
                		preparedstatement = con.prepareStatement(sql);
                		preparedstatement.setInt(1, newSalary);
                		preparedstatement.setInt(2, id);
                		rowsUpdated = preparedstatement.executeUpdate();
                		if (rowsUpdated > 0) {
                		    System.out.println("\nAn existing user was updated successfully!");
                		}
                		else {
                 			System.out.println("\nNo such employee");
                 		}
                		break;
                	}
            	    }
            	    else {
            	    	System.out.println("\nNo such employee with id "+id);
            	    }
            	 }
            break;	
            case 7:
            	sql = "SELECT MAX(salary),empName FROM EmployeesAndSalary order by empName";
            	preparedstatement = con.prepareStatement(sql);
            	result = preparedstatement.executeQuery();

            	while (result.next()){
            	    
            	    String fullname = result.getString("empName");
            	    int salary = result.getInt("MAX(salary)");
            	    //int eid=result.getInt("empId");
            	    System.out.println(fullname+" "+ salary);
            	}
            	            	
            break;
            } 		

        }while(ch!=0);
      }  
      catch(SQLException ex) {
         ex.printStackTrace();
      }
   }
}
