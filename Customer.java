public class Customer {
  private int userID;
  private String fName;
  private String lName;
  private String phoneNumber;
  private String emlAddress;
  private int tranID; //extends in transaction class
    
  public void setID (int newID){
      userID = newID;
  }
    
  public int getId (){
      return userID;
  }
    
  public void setFName (String newFName){
      fName = newFName;
  }
    
  public String getFName (){
      return fName;
  }
  
  public void setLName (String newLName){
      lName = newLName;
  }
    
  public String getLName (){
      return lName;
  }
  
  public void setPhoneNumber (int newPNumber){
      phoneNumber = newPNumber;
  }
    
  public int getPNumber (){
      return phoneNumber;
  }
  
  public void setEmail (int newEmail){
      emlAddress = newEmail;
  }
    
  public int getEmail (){
      return emlAddress;
  }
  
  public void setTranID (int newTranID){
      tranID = newTranID;
  }
    
  public int getTranID (){
      return tranID;
  }
  
  public void display(){
      System.out.println("Customer ID: " + userID + "\nFirst Name: " + fName + "\nLast Name: " + lName);
  }
