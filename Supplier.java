/* ===============================================
 * Structure of Supplier table:
 *
 *    sId   ||      name     ||   phone_number ||  
 *
 *  CHAR(6) ||   VARCHAR(50) ||   VARCHAR(11)  || 
 *  
==================================================*/
package shoptrack;

/*public class supplier extends product/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
/**
 *
 * @author User
 */

public class supplier{
    
    private String suppName;
    private String suppPhoneNumber;
    private int suppID;
    
    Connection con = null;
    Statement stmt = null;
    
    //insert supplier details
    public void insertSupplier(int suppID, String suppName, String suppPhoneNumber)throws ClassNotFoundException, SQLException{
        this.suppID = suppID;
        this.suppName = suppName;
        this.suppPhoneNumber = suppPhoneNumber;
        
        try{
            Class.forName("org.mariadb.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoptrack", "root", "");
           stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");

            String insert = "INSERT IGNORE INTO `supplier`(`sId`, `name`, `phone_number`) VALUES ("+suppID+", '" + suppName + "', '"+suppPhoneNumber + "')";
            stmt.executeUpdate(insert);

            stmt.close();
            con.close();
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
    }
    
    //Get supplier id
    public int getSupplierID(String name){
        try{
            Class.forName("org.mariadb.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoptrack", "root", "");
            stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");

            String Read = "SELECT sID FROM 'supplier'" + " WHERE name = '" + name + "'";
            ResultSet rs = stmt.executeQuery(Read);
            int id = 0;
            while (rs.next()){
                id = rs.getInt("sID");return id;
            }
            rs.close();
            return id;
            
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
        return 0;
    }

    // Retrieved supplier Name base on supplier ID
    public String getPrdSupplier(int sID){
        String storeDetails = "";
        try{
            Class.forName("org.mariadb.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/shoptrack", "root", "");
            stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM `product`");

            String Read = "SELECT * FROM `supplier` WHERE sId = '" + sID + "'";
            ResultSet rs = stmt.executeQuery(Read);
            
            while (rs.next()){
                storeDetails = rs.getString("name");    
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
