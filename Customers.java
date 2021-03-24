/* =================================================================
 * Structure of Transaction table:
 *
 *     ID   ||  Customer Name  ||  Phone Number  || 	Address
 *
 *  CHAR(6) ||     VARCHAR     ||  VARCHAR(14)   ||  	VARCHAR(150)
 *  
====================================================================*/

package Shopping;

public class Customers {
	private static int id;
	private String name;
	private String address;
	private String phoneNum;

	public Customers(String name, String address, String phoneNum) {

		// Check if the table is empty first, if the table is empty start the ID from 1
		// If it is not empty, take ID from the last row and then increment by 1
		// Grab the id from the database and increment it by one
		// i++; wait for database before uncommenting
		this.name = name;
		this.phoneNum = phoneNum;
		this.address = address;
	}

}
