/* =================================================================
 * Structure of Customer table:
 *
 *     ID   ||  Customer Name  ||  Phone Number  || 	Address
 *
 *  CHAR(6) ||     VARCHAR     ||  VARCHAR(14)   ||  	VARCHAR(150)
 *  
====================================================================*/
package Shopping;

import java.sql.*;

public class Customers {
	private int id;
	private String name;
	private String address;
	private String phoneNum;

	// Function to store new customers into the database
	public static void storeCustomers( String name, String address, String phoneNum)throws ClassNotFoundException, SQLException {
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Prepare the query and then set the values for each parameter left as a "?"
		PreparedStatement prsmt = con.prepareStatement("Insert into Customer (Name,phone_number,address) Values (?,?,?)");
		prsmt.setString(1,name); // 2nd parameter = Name
		prsmt.setString(2,phoneNum); // 3rd parameter = Customer's phone number
		prsmt.setString(3,address); // 3th parameter = Cutomer's address

		// After preparing the query, execute it
		prsmt.execute();

		con.close(); // Close the database connection
	}

	// Constructor to instantiate the class instance when the record already exists in the database
	public Customers(int id){
			
	}
}

