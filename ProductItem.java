/* ==============================================================
 * Structure of Product table:
 *
 *    pId   ||      name     ||      price     ||  supplier_id ||
 *
 *  INT(11) ||   VARCHAR(50) ||   VARCHAR(11)  ||    INT(11)   ||
 *  
=================================================================*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shopping;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class ProductItem{
    
    private int pID;
    private String pName;
    private double pPrice;
    
    private int suppID;
    Connection con;
    Statement stmt;
    
    public int getPID(){
        return this.pID;
    }
    
    
    //insert product
    public void insertProducts( String pName, double pPrice, int suppID)throws ClassNotFoundException, SQLException{
        /*I do some changes at database part, which is the name column. I changes it into a unique index*/
        
        this.pName = pName;
        this.pPrice = pPrice;
        
        //driver mariadb connector
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825");
        stmt = con.createStatement();
        //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");
        
        //mysql syntax for insert data
        String insert = "INSERT IGNORE INTO `product`(`pId`, `name`, `price`, `supplier_id`) VALUES (" + "NULL" + ", '" + pName + "', '" + pPrice + "', '"+ suppID + "')";
        stmt.executeUpdate(insert);
        
        //closing statement
        stmt.close();
        //closing connection
        con.close();
    }
   
    //this method is to display all the product!
    public int getProducts(){
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825");
            stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");
            
            //assign all record into resultset
            String Read = "SELECT * FROM `product`";
            ResultSet rs = stmt.executeQuery(Read);
            
            //read through every line at the resultset
            while (rs.next()){
                //assign database column data to variable
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                
                //print out product column data information
                System.out.println("Product " + rs.getInt("pId") + ": " + productName + "\t" + productPrice + "\n");
            }
            rs.close();
        
        //catch SQL and syntax error
        }catch (SQLException se){
           se.printStackTrace(); 
        }catch (Exception e){
            e.printStackTrace();
        //closing connection
        }finally{
            try{
                if (con != null) con.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
        return 0;
    }
    
    //Get product ID
    public ArrayList<Integer> checkProduct(){
        ArrayList<Integer> array = new ArrayList<>();
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825");
            stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");

            String Read = "SELECT * FROM `product`";
            ResultSet rs = stmt.executeQuery(Read);
            int id = 0;
            array = new ArrayList<>();
            while (rs.next()){
                int productID = rs.getInt("pId");
                array.add(productID);
                
            }
            rs.close();
            return array;
            
        }catch (SQLException se){
           se.printStackTrace();
           
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (con != null) con.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
        return array;
    }
    
    //Retrieved product details
    public String[] getCusProducts(int pID){
        String[] storeDetails = new String[4];
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shoptrack", "rahim", "himeez225825");
            stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");

            String Read = "SELECT * FROM `product` WHERE pId = '" + pID + "'";
            ResultSet rs = stmt.executeQuery(Read);
            
            while (rs.next()){
                int productID = rs.getInt("pId");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                int supplierID = rs.getInt("supplier_id");
                storeDetails[0] = String.valueOf(productID);
                storeDetails[1] = productName;
                storeDetails[2] = String.valueOf(productPrice);
                storeDetails[3] = String.valueOf(supplierID);
            }
            rs.close();
            return storeDetails;
            
        }catch (SQLException se){
           se.printStackTrace();
           
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (con != null) con.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
        return storeDetails;
    }
}
