package Shopping;

//imports
import java.util.Scanner;

public class main {

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

	public static void main(String[] args) {

		// Prepare needed variables and objects
		int usChoice, prodQty; // user's selection from the menu
		double sumPurchase = 0; // Total amount of money spent for one transaction
		Scanner inScan = new Scanner(System.in); // Scanner for user input

		// Prompt a menu to the user to select a product to purchase
		System.out.println("Welcome user, please select a product to record it's purchase\n");

		// =========== Begin Main Operation ===================//
		// Deploy in a while loop
		while (true) {

			System.out.println("1. Toothpaste X\tRM 30.45");
			System.out.println("2. Toothpaste Y\tRM 26.75");
			System.out.println("3. Pencil Case\tRM 15.00");
			System.out.println("4. Faber Blue Pen\tRM4.35");
			System.out.println("5. Faber red Pen\tRM4.35");
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

			// Calculate the total if the user hasn't chosen to settle purchase
			if (usChoice == 0) {
				break;
			} else {

				sumPurchase += prodQty * returnPrice(usChoice);
			}

		}

		inScan.close(); // close the scanner
	}

}
