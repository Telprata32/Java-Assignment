// Packages
package Shopping;

import java.util.Scanner;
import java.sql.*;

public class Supplier {
	private int suppID;
  	private String suppName;
  	private String suppPhone;
  	
  	//Prepare the scanner
  	Scanner input = new Scanner(System.in);
  	  
  	// Class' constructor
  	public Supplier()throws ClassNotFoundException, SQLException{
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Statement variable to execute queries
		Statement smt = con.createStatement();
		
		// Prompt user to enter the supplier's particulars
		System.out.print("Supplier's Name: ");
		this.suppName = input.nextLine();
		System.out.print("Supplier's Phone Number: ");
		this.suppPhone = input.nextLine();
		
		// After setting the particulars store the particular's into the database
		// Prepare the prepareStatement for the query
		PreparedStatement psmt = con.prepareStatement("Insert into supplier (name,phone_number) values (?,?)");			
		psmt.setString(1, this.suppName);
		psmt.setString(2,this.suppPhone);
	}
  	
  	public void display(){
  	    System.out.println("Supplier ID: " + suppID + "\nSupplyer Name: " + suppName);
  	}
}
  
