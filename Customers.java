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

	public Customers(int id, String name, String address, String phoneNum)throws ClassNotFoundException, SQLException {
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Instantiate attributes
		this.name = name;
		this.phoneNum = phoneNum;
		this.address = address;
		this.id = id;

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

		// ID Accessor
		public int getID(){
			return this.id;
		}
}

