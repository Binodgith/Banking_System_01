package Utility;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConnector {
    private static final  String ClassUrl = "com.mysql.cj.jdbc.Driver";
    private static final String ConnectionUrl= "jdbc:mysql://localhost:3306/firstdatabase";
    private final String Userid= "root";
    private final String Password= "pass";
//    private static DBConnector connector= null;
//
    public DBConnector(){
        try{
            Class.forName(ClassUrl);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public Connection getConnection() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(ConnectionUrl, Userid, Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
