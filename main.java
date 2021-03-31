package Shopping;

//imports
import java.util.Scanner;
import java.time.LocalDate;
import java.sql.*; // Import all mysql packages

public class main {

	// Function to check if customer exists in the database if not create one and then store into the database
	public static int checkCustomer(String name) throws ClassNotFoundException, SQLException{
		
		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
					
		// Prepare a scanner
		Scanner inScan = new Scanner(System.in);

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

	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		// Before everything secure a connection to the mysql database first 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection con; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825"); 
		
		// Statement variable to execute queries
		Statement smt = con.createStatement();
		
		
		// Prepare needed variables and objects
		int usChoice,tranChoice=0 /* user's selection from the menu*/, prodQty = 0 /* User selected quantity of product*/; 
		Scanner inScan = new Scanner(System.in); // Scanner for user input
		String[] productList = {"Toothpaste X","Toothpaste Y", "Pencil Case", "Faber Blue Pen", "Faber Red Pen"};
		String[] tranTypeList = {"Purchase", "Delivery", "Return"}; // Transaction types
		String cusName; // Customer's name
		
		// Register the customer
		System.out.print("Enter your name: ");
		cusName = inScan.nextLine();		

		// Prompt a menu to the user to select a product to purchase
		System.out.println("\nWelcome user, please select a product to record it's purchase\n");

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
				
				// Prompt user to select transaction type
				System.out.println("\nChoose your transaction type: \n");
				for(int i=0; i<tranTypeList.length; i++) {
					System.out.println((i+1) + ". " + tranTypeList[i]) ;
				}
				System.out.print("\n Transaction Type: ");
				tranChoice = inScan.nextInt(); // obtain transaction type
				inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
			}

			// Calculate the total sum for each product purchase if the user hasn't chosen to settle purchase
			if (usChoice == 0) {
				break;
			} else {
				// Create transaction instance/object here, the creation of the object will be stored into the database
				Transaction transIn = new Transaction(productList[usChoice-1], tranTypeList[tranChoice-1], prodQty, returnPrice(usChoice), checkCustomer(cusName));
			}

		}
		
		inScan.close(); // close the scanner
		con.close(); // Close the database connection
	}

}
