import java.util.Scanner; //Import Scanner Class
 
public class ProductItem extends Supplier{
    
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
    
    public void setProductPrice(int pPrice){
        productPrice = pPrice;
    }
    
    public int getProductPrice(){
        return productPrice;
    }
    
    public void getInfo(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the Supplier Name?");
        setSupplierName(input.next());
        System.out.println("What is your Product Name?");
        setProductName(input.nextLine());
        scan.nextLine();
        System.out.println("How many Product?");
        setProductQuantity(input.nextInteger());
        System.out.println("What is your Product Price?");
        setProductPrice(input.nextDouble());
    }
    
    public void display(){
        System.out.println("");
    }
    
}
