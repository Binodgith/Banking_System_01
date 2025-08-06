package Utility;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConnector {
    private static final  String ClassUrl = "com.mysql.cj.jdbc.Driver";
    private static final String ConnectionUrl= "jdbc:mysql://localhost:3306/firstdatabase";
    private String Userid= "root";
    private String Password= "pass";
    private static DBConnector connector= null;

    DBConnector(){
        try{
            Class.forName(ClassUrl);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static DBConnector getInstance(){
        if(connector==null){
            connector= new DBConnector();
        }
        return connector;
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
