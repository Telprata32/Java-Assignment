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
            System.out.println("\nProduct List: \n"); // Print header of the list
            while (rs.next()){
                //assign database column data to variable
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                
                //print out product column data information
                System.out.println("Product " + rs.getInt("pId") + ": " + productName + "\t" + "RM" + String.format("%.2f", productPrice) + "\n");
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
    
}
