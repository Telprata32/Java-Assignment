package Shopping;

public class Supplier {
  private int suppID;
  private String suppName;
  private String suppProduct;
  private int suppQuantity; //thinking
  private int productID; //extends in transaction class
    
  public void setSupplierID (int newSuppID){
      suppID = newSuppID;
  }
    
  public int getSupplierID (){
      return suppID;
  }
    
  public void setSupplierName (String newSuppName){
      suppName = newSuppName;
  }
    
  public String getSupplierName (){
      return suppName;
  }
  
  public void setproductID (int newProID){
      productID = newProID;
  }
    
  public int getProductID (){
      return productID;
  }
  
  public void display(){
      System.out.println("Supplier ID: " + suppID + "\nSupplyer Name: " + suppName);
  }
}
  
