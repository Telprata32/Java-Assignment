package Shopping;

public class Customers {
	private static int id;
	private String name;
	private String address;
	private String phoneNum;

	public Customers(String name, String address, String phoneNum) {

		// Grab the id from the id from the database and increment it by one
		// i++; wait for database before uncommenting
		this.name = name;
		this.phoneNum = phoneNum;
		this.address = address;
	}

}
