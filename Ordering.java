package Shopping;

// Imports
import java.time.LocalDate;
import java.sql.*;

public class Ordering {
	private int orderID;
	private LocalDate curTime;


	// Constructor instantiates a  class instance with the latest OrderID from the database table
	public Ordering() throws SQLException, ClassNotFoundException{
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
		Statement smt = con.createStatement();
		
		// Get the ID of the order record that has just been added into the database
		ResultSet ordRs = smt.executeQuery("Select `Order ID` from Orders order by `Order ID` DESC limit 1"); // Obtain the last row from the Orders table
		ordRs.next();
		this.orderID = ordRs.getInt(1);
	}
	
	// Function to create an Order record in the database table
	public static void saveOrder(int customerID) throws ClassNotFoundException, SQLException{
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 

		// Prepare the statement to insert record into the database table
		PreparedStatement prsm = con.prepareStatement("Insert into Orders (Date,`Customer ID`) values (?,?)");
		// Store the current time into a variable
		LocalDate dtNow = LocalDate.now();
		Date curDate = Date.valueOf(dtNow); // Convert LocalDate variable into Date format variable
		// Set the parameters for the SQL Query
		prsm.setDate(1, curDate);
		prsm.setInt(2, customerID);
		// Execute the query
		prsm.executeUpdate();
	}
	
	// Accessor to return the order's ID 
	public int getId(){
		return this.orderID;
	}
}
