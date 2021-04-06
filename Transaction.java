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

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

public class Transaction {
	private static int id;
	private String transType;
	private int productQty;
	

	public static void recordTransaction(String transType, int productQty,int OrderID, int ProductID) throws ClassNotFoundException, SQLException{
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
				
		// Statement variable to execute queries
		Statement smt = con.createStatement();

		// Store all the data into the database
		// Prepare the query and then set the values for each parameter left as a "?"
		PreparedStatement prsmt = con.prepareStatement("Insert into Transaction (transaction_type,Quantity,'Order ID','Product ID') Values (?,?,?,?)");
		prsmt.setString(1,transType); // 1st parameter = Transaction Type
		prsmt.setInt(2,productQty); // 2nd parameter = Product quantity
		prsmt.setInt(3,OrderID); // 3rd parameter = Order ID connected to the Order table
		prsmt.setInt(4, ProductID); // 4th parameter = Product ID connected to the Product table
		
		// After preparing the query, execute it
		prsmt.executeUpdate();
	
		con.close(); // Close the database connection
	}

	// Class constructor
	public Transaction(String transType, int productQty, int OrderID, int ProductID)throws ClassNotFoundException, SQLException{
		// Assign the variables to this class instance
		this.transType = transType;
		this.productQty = productQty;

		// Store into database
		recordTransaction(transType, productQty, OrderID, ProductID);	
	}
}
