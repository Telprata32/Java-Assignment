/* ========================================================================================================================
 * Structure of Transaction table:
 *
 *     ID   ||  Product Name || Type of Trans. ||  Product Quantity || Price per Unit || Purchase Date || Customer ID
 *
 *  CHAR(6) ||   VARCHAR(20) ||   VARCHAR(10)  ||         INT       ||  DECIMAL(6,2)  ||      DATE     ||   CHAR(6)
 *  
=========================================================================================================================*/
package Shopping;

import java.sql.*;
import java.time.LocalDate;

public class Transaction {
	private static int id;
	private String productName, transType;
	private int productQty;
	private double unitPrice;
	private LocalDate purchaseDate;
	

	public Transaction(String productName, String transType, int productQty, double unitPrice, int crID) throws ClassNotFoundException, SQLException{
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Statement variable to execute queries
		Statement smt = con.createStatement();

		// Check if the table is empty first, if the table is empty start the ID from 1
		// If it is not empty, take ID from the last row and then increment by 1
		ResultSet rs1 = smt.executeQuery("Select exists (select 1 from Transaction)"); //returns true if records exists and false if otherwise
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
		
		this.productName = productName;
		this.transType = transType;
		this.unitPrice = unitPrice;
		this.productQty = productQty;
		this.purchaseDate = LocalDate.now(); // Retrieve current date and store into the transaction record
		
		// Convert the id back into the required String format "TR0001" , same for the customer ID
		String idString = "TR" + String.format("%04d", id);
		String idString2 = "CR" + String.format("%04d", crID);
		
		// Store all the attributes into the database
		// Prepare the query and then set the values for each parameter left as a "?"
		PreparedStatement prsmt = con.prepareStatement("Insert into Transaction Values (?,?,?,?,?,?,?)");
		prsmt.setString(1, idString); // 1st parameter = ID
		prsmt.setString(2,this.productName); // 2nd parameter = Name
		prsmt.setString(3,this.transType); // 3rd parameter = Product Type
		prsmt.setInt(4, this.productQty); // 3rd parameter = Product quantity
		prsmt.setDouble(5, this.unitPrice); // 4th parameter = Product price per unit
		prsmt.setString(6, this.purchaseDate.toString()); // 5th parameter = Transaction date
		prsmt.setString(7, idString2); // 6th parameter = Customer ID (foreign key)
		
		// After preparing the query, execute it
		prsmt.executeUpdate();
	
		con.close(); // Close the database connection
	}
}
