// Packages
package Shopping;

import java.util.Scanner; //Import Scanner Class
 
public class ProductItem {
    
    private String productName;
    private int productQuantity;
    private double productPrice;
    
    public void setProductName(String pName){
        productName = pName;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public void setProductQuantity(int pQuantity){
        productQuantity = pQuantity;
    }
    
    public int getProductQuantity(){
        return productQuantity;
    }
    
    public void setProductPrice(double pPrice){
        productPrice = pPrice;
    }
    
    public double getProductPrice(){
        return productPrice;
    }
    
	// Class constructor
    public ProductItem(){
        Scanner input = new Scanner(System.in);
        System.out.print("Product Name: ");
		this.productName = input.nextLine();
        System.out.println("How many Product?");
        setProductQuantity(input.nextInt());
        System.out.println("What is your Product Price?");
        setProductPrice(input.nextDouble());

		input.close(); // close the scanner
    }
    
    public void display(){
        System.out.println("");
    }
    
}
