/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 *
 * @author Oscar Alvarez
 */
public class database {
    private static final String DATABASE = "servicesUTN";
   
    private static final String HOST = "jdbc:mysql://localhost:3306/" + DATABASE;
    private static final String USER = "root";
    private static final String PASS = "carmona11";
    //CONEXION A LA BASE
    public static Connection getConnection() {
            Connection con = null;
            try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    HOST , USER  , PASS
            );
            }catch (Exception e ){
                System.out.print(String.valueOf(e));}
            return con;
            }
            
    
    public static ResultSet getTable (String consulta){
        Connection cn = getConnection();
        Statement st;
        ResultSet datos = null;
        try{
          st = cn.createStatement();
          datos= st.executeQuery(consulta);
          
        }
        catch (Exception e){
        System.out.print(e.toString());
        }
        return datos;
        }   
    
}
