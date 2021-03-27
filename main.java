package Shopping;

//imports
import java.util.Scanner;
import java.time.LocalDate;
import java.sql.*; // Import all mysql packages

public class main {

	// Function to check if customer exists in the database if not create one and then store into the database
	public static int checkCustomer(String name) throws ClassNotFoundException, SQLException{
		// Prepare the id to return to main class
		int id;
		
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
					
		// Statement variable to execute queries
		Statement smt = con.createStatement();
			
		// Prepare a scanner
		Scanner inScan = new Scanner(System.in);

		// Check if the table is empty first, if the table is empty start the ID from 1
		// If it is not empty, take ID from the last row and then increment by 1
		ResultSet rs1 = smt.executeQuery("Select exists (select 1 from customer)"); //returns true if records exists and false if otherwise
		rs1.next(); // read the first line
			
		// Use an if else to decide 
		if (rs1.getBoolean(1)){
			// Now check if the name entered by the used is already found in the table
			PreparedStatement prst = con.prepareStatement("select exists (Select * from Customer where Name=?)");
			prst.setString(1,name); // set the name

			ResultSet rs2 = prst.executeQuery(); // Execute the query and receive returned result set 
			rs2.next(); // go to the first row of the result set

			// Check if the result set returns a true or not
			if(rs2.getBoolean(1)){
				PreparedStatement prst2 = con.prepareStatement("Select * from Customer where Name=?");
				prst2.setString(1,name); // set the name

				ResultSet rs3 = prst.executeQuery(); // Execute the query and receive returned result set 
				rs3.next(); // go to the first row of the result set

				// Retrieve all the customer's particulars  
				id = Integer.parseInt(rs3.getString(1).substring(2)); // Retrieve id and convert substring with numbers into Integer first
				
			}
			else{
				// First time for this customer
				// Prompt user to enter details for the first time
				System.out.print("Enter your phone number: ");
				String phoneNum = inScan.nextLine();
				System.out.print("Enter your address: ");
				String address = inScan.nextLine();

				// Grab the latest ID from the ID from the database and increment it by one
				ResultSet rs = smt.executeQuery("select ID from Customer order by ID DESC LIMIT 1"); // Get the last row from the Transaction table
				rs.next(); // Move the results set pointer to the first row first
				id = Integer.parseInt(rs.getString(1).substring(2)); // Obtain the ID from the last row (latest Transaction ID)

				id++; // Increment the id by 1

				// Create the class instance and store it into the database
				Customers cusTemp = new Customers(id,name,address,phoneNum);
			}

		}else{
			// Create a the first record in the table
			// Prompt user to enter details for the first time
			System.out.print("Enter your phone number: ");
			String phoneNum = inScan.nextLine();
			System.out.print("Enter your address: ");
			String address = inScan.nextLine();
			id = 1;

			// Create the class instance and store it into the database
			Customers cusTemp = new Customers(id,name,address,phoneNum);
		}
		// Return customer's id for the transaction	
		return id;
	}
	
	// Function to return the price of the product
	public static double returnPrice(int prodNum) { // prodNum = Product number according to the menu list of products (used for switch statement)

		// Switch statement
		switch (prodNum) {

		case 1:
			return 30.45;

		case 2:
			return 26.75;

		case 3:
			return 15.00;

		case 4:
			return 4.35;

		default:
			return 4.35;
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
		
		// Statement variable to execute queries
		Statement smt = con.createStatement();
		
		
		// Prepare needed variables and objects
		int usChoice /* user's selection from the menu*/, prodQty = 0 /* User selected quantity of product*/; 
		Scanner inScan = new Scanner(System.in); // Scanner for user input
		String[] productList = {"Toothpaste X","Toothpaste Y", "Pencil Case", "Faber Blue Pen", "Faber Red Pen"};
		String cusName; // Customer's name
		
		// Register the customer
		System.out.print("Enter your name: ");
		cusName = inScan.nextLine();		

		// Prompt a menu to the user to select a product to purchase
		System.out.println("Welcome user, please select a product to record it's purchase\n");

		// =========== Begin Main Operation ===================//
		// Deploy in a while loop
		while (true) {

			for(int i=0; i<productList.length; i++) {
				System.out.println((i+1) + ". " + productList[i] + "\tRM " + returnPrice(i+1));
			}
			System.out.println("\nEnter 0 to settle purchase");

			// Receive user's input
			System.out.print("\nPunch in product or settle purchase: ");
			usChoice = inScan.nextInt();
			inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line

			// Prompt user to enter quantity of the product, perform only if the person did not choose to settle the purchase
			if (usChoice != 0) {
				System.out.print("Quantity of product: ");
				prodQty = inScan.nextInt();
				inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
			}

			// Calculate the total sum for each product purchase if the user hasn't chosen to settle purchase
			if (usChoice == 0) {
				break;
			} else {
				// Create transaction instance/object here, the creation of the object will be stored into the database
				Transaction transIn = new Transaction(productList[usChoice-1], prodQty, returnPrice(usChoice), checkCustomer(cusName));
			}

		}
		
		inScan.close(); // close the scanner
		con.close(); // Close the database connection
	}

}
