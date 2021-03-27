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

import com.mysql.cj.protocol.Resultset;

public class Customers {
	private static int id;
	private String name;
	private String address;
	private String phoneNum;

	public Customers(String name, String address, String phoneNum)throws ClassNotFoundException, SQLException {
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Statement variable to execute queries
		Statement smt = con.createStatement();

		// Check if the table is empty first, if the table is empty start the ID from 1
		// If it is not empty, take ID from the last row and then increment by 1
		ResultSet rs1 = smt.executeQuery("Select exists (select 1 from customer)"); //returns 1 if records exists and 0 if otherwise
		rs1.next(); // read the first line
		
		// Use an if else to decide 
		if (rs1.getBoolean(1)){
			// Grab the latest ID from the ID from the database and increment it by one
			ResultSet rs = smt.executeQuery("select ID from Transaction order by ID DESC LIMIT 1"); // Get the last row from the Transaction table
			rs.next(); // Move the results set pointer to the first row first
			id = Integer.parseInt(rs.getString(1).substring(2)); // Obtain the ID from the last row (latest Transaction ID)

			id++; // Increment the id by 1
		}else{
			id = 1;
		}

		this.name = name;
		this.phoneNum = phoneNum;
		this.address = address;

		// Convert the id back into the required String format "TR0001";
		String idString = "CR" + String.format("%04d", id);
		
		// Store all the attributes into the database

		// Prepare the query and then set the values for each parameter left as a "?"
		PreparedStatement prsmt = con.prepareStatement("Insert into Customer Values (?,?,?,?)");
		prsmt.setString(1, idString); // 1st parameter = ID
		prsmt.setString(2,this.name); // 2nd parameter = Name
		prsmt.setString(3, this.phoneNum); // 3rd parameter = Customer's phone number
		prsmt.setString(4, this.address); // 3th parameter = Cutomer's address

		// After preparing the query, execute it
		prsmt.execute();

		con.close(); // Close the database connection
		}
	}

}
