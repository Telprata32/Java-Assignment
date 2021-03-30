// Packages
package Shopping;

import java.util.Scanner;
import java.sql.*;

public class Supplier {
  private int suppID;
  private String suppName;
  private String suppPhone;
  private String Address;
  
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
	
	// Check if the table is empty first, if the table is empty start the ID from 1
	// If it is not empty, take ID from the last row and then increment by 1
	ResultSet rs1 = smt.executeQuery("Select exists (select 1 from Supplier)"); //returns true if records exists and false if otherwise
	rs1.next(); // read the first line

	// If the query returns a true then continue from the last ID in the table
	// If false, then restart ID count
	if(rs1.getBoolean(1)){
		// Grab the last ID from the table and increment it by one
		ResultSet rs2 = smt.executeQuery("Select ID from Supplier order by ID DESC limit 1"); //This query will return the last row of the table
		rs2.next(); // move to the first row of the result set
		this.suppID = Integer.parseInt(rs2.getString(1).substring(2));

		this.suppID++;
	}else{
		this.suppID = 1;	
	}
	
	// Prompt user to enter the supplier's particulars
	System.out.print("Supplier's Name: ");
	this.suppName = input.nextLine();
	System.out.print("Supplier's Phone Number: ");
	this.suppPhone = input.nextLine();
	System.out.print("Supplier's Address");
	this.Address = input.nextLine();
	
	// After setting the particulars store the particular's into the database
	// Prepare the prepareStatement for the query
	PreparedStatement psmt = con.prepareStatement("Insert into Supplier values (?,?,?,?)");			
  }
  
  public void display(){
      System.out.println("Supplier ID: " + suppID + "\nSupplyer Name: " + suppName);
  }
}
  
