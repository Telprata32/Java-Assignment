package Shopping;

import java.time.LocalDate;

public class Transaction {
	private static int id;
	private String productName;
	private int productQty;
	private double unitPrice;
	private LocalDate purchaseDate;

	public Transaction(String productName, int productQty, double unitPrice) {

		// Grab the id from the id from the database and increment it by one
		// i++; wait for database before uncommenting
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.productQty = productQty;
		this.purchaseDate = LocalDate.now(); // Retrieve current date and store into the transaction record
	}
}
