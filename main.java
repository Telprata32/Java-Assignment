package Shopping;

//imports
import java.util.Scanner;
import java.time.LocalDate;
import java.sql.*; // Import all mysql packages

public class main {
	// Prepare a scanner
	static Scanner inScan = new Scanner(System.in);

	// Function to check if customer exists in the database if not create one and then store into the database
	// The function then returns the id of the selected customer
	public static int checkCustomer(String name) throws ClassNotFoundException, SQLException{
		
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 

		// Check if the name entered by the used is already found in the table
		PreparedStatement prst = con.prepareStatement("Select * from Customer where Name=?");
		prst.setString(1,name); // set the name

		ResultSet rs2 = prst.executeQuery(); // Execute the query and receive returned result set 
			
		// Use an if else to decide 
		if (rs2.next()){
			//Close inScan and con
			con.close();
			inScan.close();

			// If the user exists then return the user's id to the main class
			return rs2.getInt("Customer ID");
		}
	   	else
		{
			// Fist time customer
			System.out.println("This is your first time here, please enter your credentials\n");
			// Prompt user to enter details for the first time
			System.out.print("Enter your phone number: ");
			String phoneNum = inScan.nextLine();
			System.out.print("Enter your address: ");
			String address = inScan.nextLine();

			// Create the class instance and store it into the database
			Customers.storeCustomers(name,address,phoneNum);
			
			// Check if the name entered by the used is already found in the table
			PreparedStatement prst2 = con.prepareStatement("Select * from Customer where Name=?");
			prst2.setString(1,name); // set the name
	
			ResultSet rs3 = prst.executeQuery(); // Execute the query and receive returned result set 

			//Close inScan and con
			con.close();
			inScan.close();

			rs3.next();
			return rs3.getInt("Customer ID");
		}
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

	// Function to store the transaction into the database
	public static void purchaseProduct(){
		// Initialising variables,etc
		int tranChoice=0; // integer to select transaction type
		int prodQty = 0 /* User selected quantity of product*/; 
		// Prepare a list of transaction types
		String[] tranTypeList = {"Purchase", "Delivery", "Return"}; // Transaction types

		// Prompt user to enter quantity of the product, perform only if the person did not choose to settle the purchase
		System.out.print("Quantity of product: ");
		prodQty = inScan.nextInt();
		inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
		
		// Prompt user to select transaction type
		System.out.println("\nChoose your transaction type: \n");
		for(int i=0; i<tranTypeList.length; i++) {
			System.out.println((i+1) + ". " + tranTypeList[i]) ;
		}
		System.out.print("\n Transaction Type: ");
		tranChoice = inScan.nextInt(); // obtain transaction type
		inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
		
		// Calculate the total sum for each product purchase if the user hasn't chosen to settle purchase
		Transaction transIn = new Transaction( tranTypeList[tranChoice-1], prodQty);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
		
		// Statement variable to execute queries
		Statement smt = con.createStatement();
		
		
		// Prepare needed variables and objects
		int usChoice; /* user's selection from the menu*/ 
		String[] operationList = {"Key in an order for customer","Order products from supplier"};
		String cusName; // Customer's name
		
		// Prompt a menu to the user to select an operation
		System.out.println("\nWelcome user, select an operation\n");

		// =========== Begin Main Operation ===================//
		// Deploy in a while loop
		while (true) {

			for(int i=0; i<operationList.length; i++){
				System.out.println((i+1) + ". " + operationList[i]);			
			}
			System.out.println("\nEnter 0 to terminate program");

			// Receive user's input
			System.out.print("\nOption: ");
			usChoice = inScan.nextInt();
			inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line

			// Use a switch case to select operation
			switch(usChoice){
				
				case 1:
					// Create customer if he/she doesn't exist in the database using the checkCustomer() function
					System.out.print("Enter customer's name: ");
					cusName = inScan.nextLine();
					// Check for customer in the database and store into the database if needed
					int cusID = checkCustomer(cusName); // get the customer's ID to use as a foreign key for the order
					
					// Create an order for the customer, loop while the user still wants to punch in a product purchase
					// Link the customer to the order record
					/* ===================================================================================
					 * Insert function to create an order record in the order table
					 * =====================================================================================*/	
					
					// Now create the transaction and connect 
					// Prompt user with the menu of products		
					ResultSet rsprod = smt.executeQuery("Select * from product"); // Execute query to get all products from the database
					//Display every product for user to choose
					while(rsprod.next()){
						// initialise variable to count the products for numbering
						int i = 1;
						System.out.println(i + ". " + rsprod.getString("name") + "\t" + rsprod.getDouble("price")); // Print one product
						i++;		
					}
					System.out.println("\nEnter 0 it you want to finalize or cancel the order");

					// ========================= Create loop for respective order, to add purchases until ended by user =======================
					while(true){
						// Engage with user to select product 
						System.out.print("\nSelect product or finalize order: ");
						usChoice = inScan.nextInt();
						inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line

						// break the loop when user decides to end the order
						if(usChoice == 0){
							break;
						}

						// Prompt user to enter the quantity of the product
						System.out.print("Quantity of product: ");
						int prodQty = inScan.nextInt();
						inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
						
						// Use the usChoice integer to reference the respective id of the selected product
							
					}
					break;

				case 2:
					
					break;

				default:
					break;
			}

			if(usChoice == 0){
				break;
			}
		}
		
		inScan.close(); // close the scanner
		con.close(); // Close the database connection
	}

}
