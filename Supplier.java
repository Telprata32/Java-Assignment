// Packages
package Shopping;

import java.util.Scanner;
import java.sql.*;

public class Supplier extends Person {
  	//Prepare the scanner
  	Scanner input = new Scanner(System.in);
  	  
	// Function to store into database
  	public void recordSupplier(String suppName, String suppPhone)throws ClassNotFoundException, SQLException{
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Prepare the prepareStatement for the query
		PreparedStatement psmt = con.prepareStatement("Insert into supplier (name,phone_number) values (?,?)");			
		psmt.setString(1, suppName);
		psmt.setString(2,suppPhone);

		// Execute the query
		psmt.executeUpdate();
	}

  	// Class' constructor
	public Supplier(String suppName, String suppPhone) throws ClassNotFoundException, SQLException{
		// Call the parent's constructor
		super(suppName, suppPhone);

		// Call function to store into the database
		recordSupplier(suppName,suppPhone);		
	}

	// Accessor to get object's id
	public int getSuppID(){
		return this.ID;
	}
}
