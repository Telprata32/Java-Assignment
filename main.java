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
		PreparedStatement prst = con.prepareStatement("Select * from customer where Name=?");
		prst.setString(1,name); // set the name

		ResultSet rs2 = prst.executeQuery(); // Execute the query and receive returned result set 
			
		// Use an if else to decide 
		if (rs2.next()){
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
			PreparedStatement prst2 = con.prepareStatement("Select * from customer where Name=?");
			prst2.setString(1,name); // set the name
	
			ResultSet rs3 = prst.executeQuery(); // Execute the query and receive returned result set 

			rs3.next();
			return rs3.getInt("Customer ID");
			
		}
	}
	
	// Function to store the transaction into the database
	public static void purchaseProduct(int proID, int OrderID){
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
		System.out.print("\nTransaction Type: ");
		tranChoice = inScan.nextInt(); // obtain transaction type
		inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line

		try {
			// Create transaction and store it
			Transaction transIn = new Transaction(tranTypeList[tranChoice-1], prodQty, OrderID, proID);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ce){
			ce.printStackTrace();
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
		int usChoice; /* user's selection from the menu*/ 
		String[] operationList = {"Key in an order for a customer","Order products from supplier","See customer's order's", "List Products from specific supplier"};
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
					// Create the latest OrderID and tie this entire batch of purchases to the OrderID
					Ordering.saveOrder(cusID); // pass the customer ID into the class method
					
					// Create an object instance of the latest recorded order
					Ordering order1 = new Ordering();
					
					// Now create the transaction 
					// Prompt user with the menu of products		
					ProductItem prod1 = new ProductItem();
					prod1.getProducts();
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
						else{
							// Use the usChoice integer to reference the respective id of the selected product
							purchaseProduct(usChoice, order1.getId()); // Tie puchase to specific product ID
						}
					}
					break;

				case 2:
					// Prompt user to chooses a product to get supplier particulars for
					System.out.println("Choose a product to order\n");

					//Display every product for user to choose, with reference to their respective supplier's in one table
					ResultSet rsprod1 = smt.executeQuery("SELECT product.pId, product.name, supplier.name, supplier.phone_number FROM `supplier` INNER join product on supplier.sId = product.supplier_id ORDER BY product.pId ASC"); // Execute query to get all products from the database
					// initialise variable to count the products for numbering
					int j = 1;
					while(rsprod1.next()){
						System.out.println(j + ". " + rsprod1.getString(2)); // Print one product
						j++;		
					}
					rsprod1.beforeFirst();
					// obtain user's choice
					System.out.print("Product to order: ");
					usChoice = inScan.nextInt();
					inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
					
					// Move to the specific row according to user's choice
					for(int i=0; i<usChoice; i++){
						rsprod1.next();
					}

					// Print the supplier's details
					System.out.println("\nSupplier's details: \n");
					System.out.println("Name: " + rsprod1.getString(3));
					System.out.println("Contact Number: " + rsprod1.getString(4));
					break;

				case 3:
					// List out the customers for the user to pick to check the orders
					Customers.listCustomers();	

					// Prompt user to select customer 
					System.out.println("\nSelect customer: ");
					usChoice = inScan.nextInt(); // Take selected customer's ID
					inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line
					
					// Move to the specific row of the Customer resultset and obtain the customer's ID
					ResultSet cusRs = smt.executeQuery("Select * from customer");
					for(int i=0; i<usChoice; i++){
						cusRs.next();
					}
					usChoice = cusRs.getInt(1);

					// List out the orders of the customer
					// Prepare a query statement to return all orders including the transaction details of every customers
					PreparedStatement prodPrs = con.prepareStatement("SELECT `Orders`.`Order ID`, Orders.Date, product.pId, product.name, product.price, transaction.Quantity,transaction.transaction_type FROM Orders inner join transaction on `Orders`.`Order ID`=`transaction`.`Order  ID` inner join product on `transaction`.`Product ID`= product.pId where `Orders`.`Customer ID`=?");
					prodPrs.setInt(1,usChoice); // Set to the user chosen customer ID
					
					// ResultSet to store returned results from the prepared query statement above
					ResultSet transRs = prodPrs.executeQuery();
					
					// Print the results and categorize according to orders
					int orderNum = 0; // previous transaction's orderID
					while(transRs.next()){
						// If the current order number is not equal to previous order number (orderNum), print the following
						if(orderNum!=transRs.getInt(1)){
							System.out.println("\nOrder " + transRs.getInt(1) + "  (" + transRs.getDate("Date") + "): \n"); //Header for each set of orders
							System.out.println("Product Name\tPrice\tQuantity\tTransaction Type");
						}
						
						// Print the purchases 
						System.out.println(transRs.getString("name") + "\t" + transRs.getDouble("price") + "\t" + transRs.getInt("Quantity") + "\t" + transRs.getString("transaction_type"));

						// Update the orderNum for next iteration to reference
						orderNum = transRs.getInt(1);
					}

					prodPrs.close();
					break;

				case 4:
					// List out the suppliers
					ResultSet supRs = smt.executeQuery("Select * from supplier");
					//Print
					System.out.println("Suppliers: \n");
					int k=1;
					while(supRs.next()){
						System.out.println(k + ". " + supRs.getString("name") + "\t" + supRs.getString("phone_number"));
					}
					supRs.beforeFirst(); // Reset the pointer of the resultSet

					// Prompt user to select the supplier
					System.out.print("\nSupplier select: ");
					usChoice = inScan.nextInt(); // Receive input
					inScan.nextLine(); // so that for the next inScan.nextLine, it won't take in an empty line

					// Move to the specific row of the Customer resultset and obtain the customer's ID
					for(int i=0; i<usChoice; i++){
						supRs.next();
					}
					usChoice = supRs.getInt(1);
					supRs.beforeFirst(); // Reset the pointer of the resultSet

					//Print out all the products supplied by the supplier
					PreparedStatement supPsm = con.prepareStatement("Select * from product where supplier_id=?");
					supPsm.setInt(1,usChoice);
					supRs = supPsm.executeQuery();

					int i=1;
					while(supRs.next()){
						System.out.println(i + ". " + supRs.getString("name") + "\t" + supRs.getString("price"));
						i++;
					}
					supPsm.close();
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
